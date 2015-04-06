package com.calc3d.app.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXTreeTable;

import com.calc3d.app.CopyOfGui;
import com.calc3d.app.customSelectionListener;
import com.calc3d.app.CopyOfGui.Element3DTreeTableModel;
import com.calc3d.app.dialogs.EditElement3DDialog;
import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DCollection;
import com.calc3d.engine3d.Scene3D;
import com.calc3d.renderer.Canvas3D;

public class GraphicsPane extends JPanel implements MouseListener {
	private JXTreeTable treeTableElem3d;
	private CopyOfGui listener;
	
	private JScrollPane paneScrollPane;
	private JProgressBar progressBar;
	private JSplitPane pneSplitStatusBar;
	private JSplitPane pneSplit;
	
	private Canvas3D canvas;

	/**
	 * @wbp.parser.constructor
	 */
	public GraphicsPane(ArrayList<Element3D> list, Canvas3D newCanvas, CopyOfGui listener) {
		
		treeTableElem3d = new JXTreeTable(listener.new Element3DTreeTableModel(list, newCanvas));
        treeTableElem3d.setSize(120, 120);
        treeTableElem3d.setPreferredScrollableViewportSize(new Dimension(120, 120));
        treeTableElem3d.setAutoscrolls(true);
        treeTableElem3d.addMouseListener(this);
        treeTableElem3d.setRootVisible(false);
        treeTableElem3d.setVisible(true);
        
        treeTableElem3d.addTreeSelectionListener(new customSelectionListener(newCanvas));
        
        paneScrollPane = new JScrollPane(treeTableElem3d);
        paneScrollPane.setVerticalScrollBarPolicy(
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        paneScrollPane.setPreferredSize(new Dimension(250, 155));
        paneScrollPane.setMinimumSize(new Dimension(10, 10));
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(250,10 ));
        progressBar.setStringPainted(true);
        pneSplitStatusBar = new JSplitPane(JSplitPane.VERTICAL_SPLIT, paneScrollPane, progressBar);
        pneSplitStatusBar.setResizeWeight(1);
		setLayout(new BorderLayout(0, 0));
		
		pneSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, newCanvas, pneSplitStatusBar);
		pneSplit.setResizeWeight(1);
		
		
		// setup the layout
		pneSplit.setOneTouchExpandable(true);

		this.add(pneSplit);
		
		this.canvas  = newCanvas;

	}
	
public GraphicsPane(CopyOfGui listener) {
		
 
		this.listener = listener;
        paneScrollPane = new JScrollPane();
        paneScrollPane.setVerticalScrollBarPolicy(
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        paneScrollPane.setPreferredSize(new Dimension(250, 155));
        paneScrollPane.setMinimumSize(new Dimension(10, 10));
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(250,10 ));
        progressBar.setStringPainted(true);
        pneSplitStatusBar = new JSplitPane(JSplitPane.VERTICAL_SPLIT, paneScrollPane, progressBar);
        pneSplitStatusBar.setResizeWeight(1);
		setLayout(new BorderLayout(0, 0));
		
		pneSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JPanel(), pneSplitStatusBar);
		pneSplit.setResizeWeight(1);
		
		
		// setup the layout
		pneSplit.setOneTouchExpandable(true);
		
		
		
		
		this.add(pneSplit);
		  this.updateUI();

	}

public void addElements3D(ArrayList<Element3D> elementsList, Canvas3D canvas) {
	  treeTableElem3d = new JXTreeTable(listener.new Element3DTreeTableModel(elementsList, canvas));
	  treeTableElem3d.setSize(250, 900);
	  treeTableElem3d.setPreferredScrollableViewportSize(new Dimension(250, 900));
	  treeTableElem3d.setAutoscrolls(true);
	  treeTableElem3d.addMouseListener(this);
	  treeTableElem3d.setRootVisible(false);
	  treeTableElem3d.setVisible(true);
	  treeTableElem3d.addTreeSelectionListener(new customSelectionListener(canvas));
      
	  
	  paneScrollPane.setViewportView(treeTableElem3d);
	  pneSplit.setLeftComponent(canvas);
	  this.canvas = canvas;
	  
	  this.updateUI();
	  
//  
	
}

public JProgressBar getProgressBar() {
	return progressBar;
}

public JXTreeTable getTreeTable(){
	return treeTableElem3d;
}

@Override
public void mouseClicked(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent e) {
	if (e.getClickCount()==2){
		if(this.treeTableElem3d == null ) return;
		int i = this.treeTableElem3d.getSelectedRow();
		Element3D selectedNode = (Element3D) this.treeTableElem3d.getPathForRow(i).getLastPathComponent();
		Element3DTreeTableModel model = (Element3DTreeTableModel) this.treeTableElem3d.getTreeTableModel();
		Element3DCollection root = (Element3DCollection) model.rootElements.get(0);
		String title = EditElement3DDialog.show(SwingUtilities.getWindowAncestor(this), root, i, " ");
		Scene3D scene = this.canvas.getSceneManager().createScene(true);
		this.canvas.setScene(scene);
		this.canvas.refresh();	
	}
	
}

@Override
public void mouseReleased(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}
	

}
