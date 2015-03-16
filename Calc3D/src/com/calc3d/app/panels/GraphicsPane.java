package com.calc3d.app.panels;

import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;

import org.jdesktop.swingx.JXTreeTable;

import com.calc3d.app.CopyOfGui;
import com.calc3d.app.customSelectionListener;
import com.calc3d.app.CopyOfGui.Element3DTreeTableModel;
import com.calc3d.app.elements.Element3D;
import com.calc3d.renderer.Canvas3D;

public class GraphicsPane extends JPanel {
	private JXTreeTable treeTableElem3d;

	public GraphicsPane(ArrayList<Element3D> list, Canvas3D newCanvas, CopyOfGui listener) {
		
		treeTableElem3d = new JXTreeTable(listener.new Element3DTreeTableModel(list, newCanvas));
        treeTableElem3d.setSize(120, 120);
        treeTableElem3d.setPreferredScrollableViewportSize(new Dimension(120, 120));
        treeTableElem3d.setAutoscrolls(true);
        treeTableElem3d.addMouseListener(listener);
        treeTableElem3d.setRootVisible(false);
        treeTableElem3d.setVisible(true);
        
        treeTableElem3d.addTreeSelectionListener(new customSelectionListener(newCanvas));
        
        JScrollPane paneScrollPane = new JScrollPane(treeTableElem3d);
        paneScrollPane.setVerticalScrollBarPolicy(
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        paneScrollPane.setPreferredSize(new Dimension(250, 155));
        paneScrollPane.setMinimumSize(new Dimension(10, 10));
        
        
		JSplitPane pneSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, newCanvas, paneScrollPane);
		pneSplit.setResizeWeight(1);
		// setup the layout
		pneSplit.setOneTouchExpandable(true);
		
		this.add(pneSplit);

	}
	

}
