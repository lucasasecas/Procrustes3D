package com.calc3d.app.dialogs;

import javax.swing.JDialog;

import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DCollection;
import com.calc3d.app.panels.Element3DPane;
import com.calc3d.app.panels.Element3DPanelEditor;
import com.calc3d.app.panels.GraphicsPane;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditElement3DDialog extends JDialog implements ActionListener{
	private Element3DCollection colletcion;
	private Element3DPanelEditor panel;
	private JButton btnCancel;
	private JButton btnAcept;
	private boolean canceled = false; 
	public EditElement3DDialog(Window owner, Element3DCollection collection, int indexSelected,String tabTitle){
		super(owner, "Edit elem", ModalityType.APPLICATION_MODAL);
		this.colletcion = collection;
		this.panel = new Element3DPane(collection, indexSelected);
		this.panel.setTabTitle(tabTitle);

		
		getContentPane().add((Component) panel, BorderLayout.CENTER);
		
		JPanel buttonsPnl = new JPanel();
		FlowLayout flowLayout = (FlowLayout) buttonsPnl.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(buttonsPnl, BorderLayout.SOUTH);
		
		btnAcept = new JButton("Accept");
		btnAcept.setHorizontalAlignment(SwingConstants.LEFT);
		buttonsPnl.add(btnAcept);
		btnAcept.addActionListener(this);
		btnAcept.setActionCommand("accept");
		
		btnCancel = new JButton("Cancel");
		buttonsPnl.add(btnCancel);
		btnCancel.addActionListener(this);
		btnCancel.setActionCommand("cancel");
		this.pack();
	}
	
	public final static String show(Window window,Element3DCollection root, int indexSelected, String tabTitle){
		EditElement3DDialog dialog = new EditElement3DDialog(window, root, indexSelected, tabTitle);
		dialog.setLocationRelativeTo(window);
		dialog.setVisible(true);
		return dialog.getPanel().getTabTitle();
		
		
		
	}

	private Element3DPane getPanel() {
		return (Element3DPane) panel;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		
		if(command=="accept"){
			this.panel.saveResult();
			this.setVisible(false);
			this.canceled  = false;
		}else if(command=="cancel"){
			this.setVisible(false);
			this.canceled=true;
		}
		
	}

}
