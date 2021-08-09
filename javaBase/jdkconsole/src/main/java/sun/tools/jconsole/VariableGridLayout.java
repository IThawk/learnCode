/*
 * %W% %E%
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package sun.tools.jconsole;

import java.awt.*;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class VariableGridLayout extends GridLayout {

    private boolean fillRows, fillColumns;

    public VariableGridLayout(int rows, int cols,
			      int hgap, int vgap,
			      boolean fillRows, boolean fillColumns) {
	super(rows, cols, hgap, vgap);

	this.fillRows    = fillRows;
	this.fillColumns = fillColumns;
    }

    public void setFillRow(JComponent c, boolean b) {
	c.putClientProperty("VariableGridLayout.fillRow", b);
    }

    public void setFillColumn(JComponent c, boolean b) {
	c.putClientProperty("VariableGridLayout.fillColumn", b);
    }

    public boolean getFillRow(JComponent c) {
	Boolean b = (Boolean)c.getClientProperty("VariableGridLayout.fillRow");
	return (b != null) ? b : fillRows;
    }

    public boolean getFillColumn(JComponent c) {
	Boolean b = (Boolean)c.getClientProperty("VariableGridLayout.fillColumn");
	return (b != null) ? b : fillColumns;
    }

    public void layoutContainer(Container parent) {
	Insets insets = parent.getInsets();
	int ncomponents = parent.getComponentCount();
	int nrows = getRows();
	int ncols = getColumns();
	int hgap =  getHgap();
	int vgap =  getVgap();

	if (nrows > 0) {
	    ncols = (ncomponents + nrows - 1) / nrows;
	} else {
	    nrows = (ncomponents + ncols - 1) / ncols;
	}

	// Set heights
	int x;
	int y;
	int nFills = 0;
	boolean[] fills = new boolean[nrows];
	int lastFillRow = -1;
	int nComps = parent.getComponentCount();

	y = insets.top;
	for (int row = 0; row < nrows; row++) {
	    // Find largest minimum height for this row
	    int h = 0;
	    for (int col = 0; col < ncols; col++) {
		if (row * ncols + col < nComps) {
		    Component c = parent.getComponent(row * ncols + col);
		    h = Math.max(h, c.getMinimumSize().height);
		}
	    }
	    // Set heights for this row
	    x = insets.left;
	    for (int col = 0; col < ncols; col++) {
		if (row * ncols + col < nComps) {
		    JComponent c = (JComponent)parent.getComponent(row * ncols + col);
		    int w = c.getWidth();
		    c.setBounds(x, y, w, h);
		    x += w + hgap;
		    if (col == 0 && getFillRow(c)) {
			fills[row] = true;
		    }
		}
	    }
	    y += h + vgap;
	    if (fills[row]) {
		nFills++;
		lastFillRow = row;
	    }
	}

	// Fill heights
	if (nFills > 0 && y < parent.getHeight()) {
	    // How much height to add
	    int hAdd = (parent.getHeight() - y) / nFills;
	    int hAdded = 0;
	    for (int row = 0; row < nrows; row++) {
		if (fills[row]) {
		    if (row == lastFillRow) {
			// Compensate for rounding error
			hAdd = parent.getHeight() - (y+hAdded);
		    }
		    for (int col = 0; col < ncols; col++) {
			if (row * ncols + col < nComps) {
			    Component c = parent.getComponent(row * ncols + col);
			    Rectangle b = c.getBounds();
			    c.setBounds(b.x, b.y + hAdded, b.width, b.height + hAdd);
			}
		    }
		    hAdded += hAdd;
		}	    
	    }
	}

	// Set widths
	nFills = 0;
	fills = new boolean[ncols];
	int lastFillCol = -1;

	x = insets.left;
	for (int col = 0; col < ncols; col++) {
	    // Find largest minimum width for this column
	    int w = 0;
	    for (int row = 0; row < nrows; row++) {
		if (row * ncols + col < nComps) {
		    Component c = parent.getComponent(row * ncols + col);
		    w = Math.max(w, c.getMinimumSize().width);
		}
	    }
	    // Set widths for this column
	    y = insets.top;
	    for (int row = 0; row < nrows; row++) {
		if (row * ncols + col < nComps) {
		    JComponent c = (JComponent)parent.getComponent(row * ncols + col);
		    int h = c.getHeight();
		    c.setBounds(x, y, w, h);
		    y += h + vgap;
		    if (row == 0 && getFillColumn(c)) {
			fills[col] = true;
		    }
		}
	    }
	    x += w + hgap;
	    if (fills[col]) {
		nFills++;
		lastFillCol = col;
	    }
	}

	// Fill widths
	if (nFills > 0 && x < parent.getWidth()) {
	    // How much width to add
	    int wAdd = (parent.getWidth() - x) / nFills;
	    int wAdded = 0;
	    for (int col = 0; col < ncols; col++) {
		if (fills[col]) {
		    if (col == lastFillCol) {
			wAdd = parent.getWidth() - (x+wAdded);
		    }
		    for (int row = 0; row < nrows; row++) {
			if (row * ncols + col < nComps) {
			    Component c = parent.getComponent(row * ncols + col);
			    Rectangle b = c.getBounds();
			    c.setBounds(b.x + wAdded, b.y, b.width + wAdd, b.height);
			}
		    }
		    wAdded += wAdd;
		}
	    }	    
	}
    }

    public Dimension preferredLayoutSize(Container parent) {
	Insets insets = parent.getInsets();
	int ncomponents = parent.getComponentCount();
	int nrows = getRows();
	int ncols = getColumns();
	int hgap =  getHgap();
	int vgap =  getVgap();

	if (nrows > 0) {
	    ncols = (ncomponents + nrows - 1) / nrows;
	} else {
	    nrows = (ncomponents + ncols - 1) / ncols;
	}

	int nComps = parent.getComponentCount();

	int y = insets.top;
	for (int row = 0; row < nrows; row++) {
	    int h = 0;
	    for (int col = 0; col < ncols; col++) {
		if (row * ncols + col < nComps) {
		    Component c = parent.getComponent(row * ncols + col);
		    h = Math.max(h, c.getMinimumSize().height);
		}
	    }
	    y += h + vgap;
	}

	int x = insets.left;
	for (int col = 0; col < ncols; col++) {
	    int w = 0;
	    for (int row = 0; row < nrows; row++) {
		if (row * ncols + col < nComps) {
		    Component c = parent.getComponent(row * ncols + col);
		    w = Math.max(w, c.getMinimumSize().width);
		}
	    }
	    x += w + hgap;
	}
	return new Dimension(x, y);
    }
}
