package com.calc3d.app;

import javax.swing.JLabel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.SimpleElement;

public class LeftTableSelectionListener implements TreeSelectionListener {

	JLabel editorPane;
	
	public LeftTableSelectionListener(JLabel label){
		editorPane = label;
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent event) {
		Object obj = event.getNewLeadSelectionPath().getLastPathComponent();
		if(obj instanceof SimpleElement){
			  editorPane.setText(commonUtils.getobject3DInfoHTML(((SimpleElement) obj)));
			  editorPane.updateUI();
			
		}

	}

}
