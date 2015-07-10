package com.calc3d.app.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.calc3d.app.analysis.DialogConfiguration;
import com.calc3d.app.analysis.DistanceConfiguration;
import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.SimpleElement;
import com.calc3d.app.resources.Messages;
import com.calc3d.utils.PrefixTextField;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DistancePanel extends JPanel implements SimpleElementCreatePanel, ActionListener{
	
	public JRadioButton robRdBtn;
	public JRadioButton minSqeRdBtn;
	private PrefixTextField textField;
	
	public DistancePanel() {
		this.robRdBtn = new JRadioButton(Messages.getString("dialog.distance.rd"));
		robRdBtn.addActionListener(this);
		robRdBtn.setActionCommand("rdbRD");
		this.robRdBtn.setSelected(false);
		this.minSqeRdBtn = new JRadioButton(Messages.getString("dialog.distance.pd"));
		minSqeRdBtn.addActionListener(this);
		minSqeRdBtn.setActionCommand("rdbLSD");
		this.minSqeRdBtn.setSelected(false);
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(minSqeRdBtn);
		btnGroup.add(robRdBtn);
		
		JLabel lblName = new JLabel("Name:");
		
		textField = new PrefixTextField("");
		textField.setEnabled(false);
		textField.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(robRdBtn)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(lblName)
								.addGap(18)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE))
							.addComponent(minSqeRdBtn, Alignment.LEADING)))
					.addContainerGap(116, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(22)
					.addComponent(minSqeRdBtn)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(robRdBtn)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(189, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
	}

	@Override
	public boolean isDrawable() {
		
		return true;
	}

	@Override
	public boolean isValidInput() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public void setName(String name){
		super.setName(name);
		textField.setText(name);
	}

	@Override
	public DialogConfiguration getConfiguration() {
		DistanceConfiguration configuration = new DistanceConfiguration(robRdBtn.isSelected() ? 0 : 1);
		configuration.setTabTitle(this.textField.getFullText());
		return configuration;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch(command){
		case "rdbLSD":
			textField.setEnabled(true);
			textField.setPrefix("lsD");
			break;
		case "rdbRD":
			textField.setEnabled(true);
			textField.setPrefix("rD");
		}
		textField.updateUI();
	}
}
