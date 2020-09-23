/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.tools.jconsole.inspector;
import sun.tools.jconsole.Plotter;
import javax.swing.JTable;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class XPlotter extends Plotter {
    JTable table;
    public XPlotter(JTable table, 
		    Plotter.Unit unit) {
	super(unit);
	this.table = table;
    }
    public void addValues(long time, long... values) {
	super.addValues(time, values);
	table.repaint();	
    }
}
