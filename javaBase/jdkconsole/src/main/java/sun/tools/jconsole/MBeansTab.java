/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.tools.jconsole;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.*;
import java.io.*;
import java.util.Set;
import javax.management.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import sun.tools.jconsole.ProxyClient.SnapshotMBeanServerConnection;
import sun.tools.jconsole.inspector.*;

import com.sun.tools.jconsole.JConsoleContext;

@SuppressWarnings("serial")
public class MBeansTab extends Tab implements
        NotificationListener, PropertyChangeListener,
        TreeSelectionListener, TreeWillExpandListener {
    
    private XTree tree;
    private XSheet sheet;
    private XDataViewer viewer;
    
    public static String getTabName() {
        return Resources.getText("MBeans");
    }
    
    public MBeansTab(final VMPanel vmPanel) {
        super(vmPanel, getTabName());
        addPropertyChangeListener(this);
        setupTab();
    }
    
    public XDataViewer getDataViewer() {
        return viewer;
    }
    
    public XTree getTree() {
        return tree;
    }
    
    public XSheet getSheet() {
        return sheet;
    }
    
    @Override
    public void dispose() {
        super.dispose();
        sheet.dispose();
    }
    
    public int getUpdateInterval() {
        return vmPanel.getUpdateInterval();
    }
    
    private void buildMBeanServerView() {
        new SwingWorker<Set<ObjectName>, Void>() {
            @Override
            public Set<ObjectName> doInBackground() {
                // Register listener for MBean registration/unregistration
                //
                try {
                    getMBeanServerConnection().addNotificationListener(
                            MBeanServerDelegate.DELEGATE_NAME,
                            MBeansTab.this,
                            null,
                            null);
                } catch (InstanceNotFoundException e) {
                    // Should never happen because the MBeanServerDelegate
                    // is always present in any standard MBeanServer
                    //
                    if (JConsole.isDebug()) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    if (JConsole.isDebug()) {
                        e.printStackTrace();
                    }
                    vmPanel.getProxyClient().markAsDead();
                    return null;
                }
                // Retrieve MBeans from MBeanServer
                //
                Set<ObjectName> mbeans = null;
                try {
                    mbeans = getMBeanServerConnection().queryNames(null,null);
                } catch (IOException e) {
                    if (JConsole.isDebug()) {
                        e.printStackTrace();
                    }
                    vmPanel.getProxyClient().markAsDead();
                    return null;
                }
                return mbeans;
            }
            @Override
            protected void done() {
                try {
                    // Wait for mbsc.queryNames() result
                    Set<ObjectName> mbeans = get();
                    // Do not display anything until the new tree has been built
                    //
                    tree.setVisible(false);
                    // Cleanup current tree
                    //
                    tree.removeAll();
                    // Add MBeans to tree
                    //
                    tree.addMBeansToView(mbeans);
                    // Display the new tree
                    //
                    tree.setVisible(true);
                } catch (Exception e) {
                    Throwable t = Utils.getActualException(e);
                    if (JConsole.isDebug()) {
                        System.err.println("Problem at MBean tree construction");
                        t.printStackTrace();
                    }
                }
            }
        }.execute();
    }
    
    public MBeanServerConnection getMBeanServerConnection() {
        return vmPanel.getProxyClient().getMBeanServerConnection();
    }

    public SnapshotMBeanServerConnection getSnapshotMBeanServerConnection() {
        return vmPanel.getProxyClient().getSnapshotMBeanServerConnection();
    }

    @Override
    public void update() {
        // Ping the connection to see if it is still alive. At
        // some point the ProxyClient class should centralize
        // the connection aliveness monitoring and no longer
        // rely on the custom tabs to ping the connections.
        //
        try {
            getMBeanServerConnection().getDefaultDomain();
        } catch (IOException ex) {
            vmPanel.getProxyClient().markAsDead();
        }
    }
    
    private void setupTab() {
        // set up the split pane with the MBean tree and MBean sheet panels
        setLayout(new BorderLayout());
        JSplitPane mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplit.setDividerLocation(160);
        mainSplit.setBorder(BorderFactory.createEmptyBorder());
        
        // set up the MBean tree panel (left pane)
        tree = new XTree(this);
        tree.setCellRenderer(new XTreeRenderer());
        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.addTreeSelectionListener(this);
        tree.addTreeWillExpandListener(this);
        tree.addMouseListener(ml);
        JScrollPane theScrollPane = new JScrollPane(
                tree,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JPanel treePanel = new JPanel(new BorderLayout());
        treePanel.add(theScrollPane, BorderLayout.CENTER);
        mainSplit.add(treePanel, JSplitPane.LEFT, 0);
        
        // set up the MBean sheet panel (right pane)
        viewer = new XDataViewer(this);
        sheet = new XSheet(this);
        mainSplit.add(sheet, JSplitPane.RIGHT, 0);
        
        add(mainSplit);
    }
    
    /* notification listener:  handleNotification */
    public void handleNotification(
            final Notification notification, Object handback) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                if (notification instanceof MBeanServerNotification) {
                    ObjectName mbean =
                            ((MBeanServerNotification) notification).getMBeanName();
                    if (notification.getType().equals(
                            MBeanServerNotification.REGISTRATION_NOTIFICATION)) {
                        tree.addMBeanToView(mbean);
                    } else if (notification.getType().equals(
                            MBeanServerNotification.UNREGISTRATION_NOTIFICATION)) {
                        tree.removeMBeanFromView(mbean);
                    }
                }
            }
        });
    }
    
    /* property change listener:  propertyChange */
    public void propertyChange(PropertyChangeEvent evt) {
        if (JConsoleContext.CONNECTION_STATE_PROPERTY.equals(evt.getPropertyName())) {
            boolean connected = (Boolean) evt.getNewValue();
            if (connected) {
                buildMBeanServerView();
            } else {
                sheet.dispose();
            }
        }
    }
    
    /* tree selection listener: valueChanged */
    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node =
                (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
        sheet.displayNode(node);
    }
    
    /* tree mouse listener: mousePressed */
    private MouseListener ml = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getClickCount() == 1) {
                int selRow = tree.getRowForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    TreePath selPath =
                            tree.getPathForLocation(e.getX(), e.getY());
                    DefaultMutableTreeNode node =
                            (DefaultMutableTreeNode) selPath.getLastPathComponent();
                    if (sheet.isMBeanNode(node)) {
                        tree.expandPath(selPath);
                    }
                }
            }
        }
    };
    
    /* tree will expand listener: treeWillExpand */
    public void treeWillExpand(TreeExpansionEvent e)
    throws ExpandVetoException {
        TreePath path = e.getPath();
        if (!tree.hasBeenExpanded(path)) {
            DefaultMutableTreeNode node =
                    (DefaultMutableTreeNode) path.getLastPathComponent();
            if (sheet.isMBeanNode(node) && !tree.hasMetadataNodes(node)) {
                tree.addMetadataNodes(node);
            }
        }
    }
    
    /* tree will expand listener: treeWillCollapse */
    public void treeWillCollapse(TreeExpansionEvent e)
    throws ExpandVetoException {
    }
}
