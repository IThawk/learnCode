/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.tools.jconsole;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.net.URI;

import javax.accessibility.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import static java.awt.BorderLayout.*;
import static sun.tools.jconsole.Resources.*;
import static sun.tools.jconsole.Utilities.*;

@SuppressWarnings("serial")
public class AboutDialog extends InternalDialog {

    private static final Color textColor     = new Color(87,   88,  89);
    private static final Color bgColor       = Color.white;
    private static final Color borderColor   = Color.black;

    private Icon mastheadIcon =
	new MastheadIcon(getText("Help.AboutDialog.masthead.title"));

    private static AboutDialog aboutDialog;

    private JLabel statusBar;
    private Action closeAction;

    public AboutDialog(JConsole jConsole) {
	super(jConsole, Resources.getText("Help.AboutDialog.title"), false);

	setAccessibleDescription(this,
				 getText("Help.AboutDialog.accessibleDescription"));
	setDefaultCloseOperation(HIDE_ON_CLOSE);
	setResizable(false);
	JComponent cp = (JComponent)getContentPane();

	createActions();

	JLabel mastheadLabel = new JLabel(mastheadIcon) {
            public void paint(Graphics g) {
                super.paint(g);
                // draw a gray line on the bottom of the label.
                Color oc = g.getColor();
                g.setColor(new Color(192,192,192));
                g.drawLine(0,this.getHeight()-1, this.getWidth(), this.getHeight()-1);
                g.setColor(null);
            }
        };
	setAccessibleName(mastheadLabel,
			  getText("Help.AboutDialog.masthead.accessibleName"));

	JPanel mainPanel = new TPanel(0, 0);
	mainPanel.add(mastheadLabel, NORTH);

	String jConsoleVersion = Version.getVersion();
	String vmName = System.getProperty("java.vm.name");
	String vmVersion = System.getProperty("java.vm.version");
        String linkText = getText("Help.AboutDialog.userGuideLink.linkText");
	String urlStr = getText("Help.AboutDialog.userGuideLink.url");
	if (isBrowseSupported()) {
	    urlStr = "<a style='color:#FF0000; text-decoration:none; font-weight:bold' href=\"" + urlStr + "\">" + linkText + "</a>";
	}

	JPanel infoAndLogoPanel = new JPanel(new BorderLayout(10, 10));
	infoAndLogoPanel.setBackground(bgColor);
	
	String colorStr = String.format("%06x", textColor.getRGB() & 0xFFFFFF);
	JEditorPane helpLink = new JEditorPane("text/html",
				"<html><font color=#"+ colorStr + ">" +
			getText("Help.AboutDialog.jConsoleVersion", jConsoleVersion) +
		"<p>" + getText("Help.AboutDialog.javaVersion", (vmName +", "+ vmVersion)) +
                (isBrowseSupported()? "<p>" + urlStr :
  		    "<p>" + getText("Help.AboutDialog.userGuideLink", urlStr)) +
						 "</html>");
	helpLink.setOpaque(false);
	helpLink.setEditable(false);
	helpLink.setForeground(textColor);
	mainPanel.setBorder(BorderFactory.createLineBorder(borderColor));
	infoAndLogoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	helpLink.addHyperlinkListener(new HyperlinkListener() {
	    public void hyperlinkUpdate(HyperlinkEvent e) {
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
		    browse(e.getDescription());
		}
	    }
	});
	infoAndLogoPanel.add(helpLink, NORTH);

	ImageIcon sunLogoIcon = new ImageIcon(getClass().getResource("resources/about-OracleLogo.png"));
	JLabel sunLogo = new JLabel(sunLogoIcon, JLabel.LEADING);

	JButton closeButton = new JButton(closeAction);

	JPanel bottomPanel = new TPanel(0, 0);
	JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
	buttonPanel.setOpaque(false);

	mainPanel.add(infoAndLogoPanel, CENTER);
	cp.add(bottomPanel, SOUTH);

	infoAndLogoPanel.add(sunLogo, SOUTH);

	buttonPanel.setBorder(new EmptyBorder(2, 12, 2, 12));
	buttonPanel.add(closeButton);
	bottomPanel.add(buttonPanel, NORTH);

	statusBar = new JLabel(" ");
	bottomPanel.add(statusBar, SOUTH);

	cp.add(mainPanel, NORTH);

	pack();
	setLocationRelativeTo(jConsole);
	Utilities.updateTransparency(this);
    }

    public void showDialog() {
	statusBar.setText(" ");
	setVisible(true);
	try {
	    // Bring to front of other dialogs
	    setSelected(true);
	} catch (PropertyVetoException e) {
	    // ignore
	}
    }

    private static AboutDialog getAboutDialog(JConsole jConsole) {
	if (aboutDialog == null) {
	    aboutDialog = new AboutDialog(jConsole);
	}
	return aboutDialog;
    }
    
    static void showAboutDialog(JConsole jConsole) {
	getAboutDialog(jConsole).showDialog();
    }
    
    static void browseUserGuide(JConsole jConsole) {
	getAboutDialog(jConsole).browse(getText("Help.AboutDialog.userGuideLink.url"));
    }

    static boolean isBrowseSupported() {
	return (Desktop.isDesktopSupported() &&
		Desktop.getDesktop().isSupported(Desktop.Action.BROWSE));
    }

    void browse(String urlStr) {
	try {
	    Desktop.getDesktop().browse(new URI(urlStr));
	} catch (Exception ex) {
	    showDialog();
	    statusBar.setText(ex.getLocalizedMessage());
	    if (JConsole.isDebug()) {
		ex.printStackTrace();
	    }
	}
    }

    private void createActions() {
	closeAction = new AbstractAction(getText("Close")) {
	    public void actionPerformed(ActionEvent ev) {
		setVisible(false);
		statusBar.setText("");
	    }
	};
    }

    private static class TPanel extends JPanel {
	TPanel(int hgap, int vgap) {
	    super(new BorderLayout(hgap, vgap));
	    setOpaque(false);
	}
    }
}
