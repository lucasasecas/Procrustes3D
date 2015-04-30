package com.calc3d.app;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.SimpleElement;
import com.calc3d.app.resources.Messages;

public class LeftTableMenuFactory {

	public static JPopupMenu createMenu(SimpleElement node, MouseListener listener, ActionListener actionListener) {
		JPopupMenu menu = new JPopupMenu();
		
		if(node instanceof ComposeSimpleElement){
			JMenuItem insertWireframeItem = new JMenuItem(Messages.getString("simpleelement.menu.insertWireframe"));
			insertWireframeItem.addActionListener(actionListener);
			insertWireframeItem.setActionCommand("addWireframe");
			menu.add(insertWireframeItem);
		}
		return menu;
	}
	
	

}
