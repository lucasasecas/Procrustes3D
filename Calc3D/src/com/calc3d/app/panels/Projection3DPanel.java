package com.calc3d.app.panels;

import java.awt.Window;

import com.calc3d.app.analysis.DialogConfiguration;
import com.calc3d.app.analysis.ProjectionConfiguration;
import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DProjection;
import com.calc3d.app.resources.Messages;
import com.calc3d.utils.PrefixTextField;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.FlowLayout;
import java.awt.CardLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JLabel;

import java.awt.GridLayout;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class Projection3DPanel extends JPanel implements SimpleElementCreatePanel, ActionListener {


	private JRadioButton rdbtnRobust;
	private JRadioButton rdbtnLeastSqe;
	private JRadioButton rdbtn2D;
	private JRadioButton rdbtn3D;
	private JLabel lblName;
	private PrefixTextField txtName;


	public Projection3DPanel() {
		
		rdbtnRobust = new JRadioButton(Messages.getString("dialog.projection.rmds"));
		rdbtnRobust.addActionListener(this);
		rdbtnRobust.setActionCommand("rdb2");
		
		rdbtnLeastSqe = new JRadioButton(Messages.getString("dialog.projection.fmds"));
		rdbtnLeastSqe.addActionListener(this);
		rdbtnLeastSqe.setActionCommand("rdb1");
		
		JLabel lblProjectionType = new JLabel("Ordination type:");
		
		JLabel lblDimensions = new JLabel("Dimension:");
		
		rdbtn2D = new JRadioButton("2D");
		rdbtn3D = new JRadioButton("3D");
		
		lblName = new JLabel("Tab Name");
		
		txtName = new PrefixTextField("");
		txtName.setEnabled(false);
		txtName.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblProjectionType)
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
									.addComponent(lblName)
									.addComponent(lblDimensions)))
							.addGap(81)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtn3D)
								.addComponent(rdbtnRobust)
								.addComponent(rdbtnLeastSqe, GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
								.addComponent(rdbtn2D)))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(168)
							.addComponent(txtName, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblProjectionType)
						.addComponent(rdbtnLeastSqe))
					.addGap(3)
					.addComponent(rdbtnRobust)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDimensions)
						.addComponent(rdbtn2D))
					.addGap(4)
					.addComponent(rdbtn3D)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblName))
					.addGap(133))
		);
		setLayout(groupLayout);
		
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(rdbtnRobust);
		bGroup.add(rdbtnLeastSqe);
		
		ButtonGroup b2 = new ButtonGroup();
		b2.add(rdbtn2D);
		b2.add(rdbtn3D);
	}


	public void setName(String name){
		super.setName(name);
		txtName.setText(name);
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

	@Override
	public DialogConfiguration getConfiguration() {
		ProjectionConfiguration configuration = new ProjectionConfiguration(rdbtnLeastSqe.isSelected()?ProjectionConfiguration.LEAST_SQR_PROJETION : ProjectionConfiguration.ROBUST_PROJECTION);
		int a =0;
		int type = this.rdbtnLeastSqe.isSelected() ? ProjectionConfiguration.LEAST_SQR_PROJETION : ProjectionConfiguration.ROBUST_PROJECTION;
		String nName = type==ProjectionConfiguration.LEAST_SQR_PROJETION?Messages.getString("tab.name.prefix.fmds"):Messages.getString("tab.name.prefix.rmds");
				
		configuration.setDimensions(rdbtn2D.isSelected() ? 2 : 3);
		configuration.setName(this.txtName.getFullText());
		return configuration;
	}



	@Override
	public void actionPerformed(ActionEvent ev) {
		String command = ev.getActionCommand();
		switch(command){
		case "rdb1":
			txtName.setEnabled(true);
			txtName.setPrefix(Messages.getString("tab.name.prefix.fmds"));
			break;
		
		case "rdb2":
			txtName.setEnabled(true);
			txtName.setPrefix(Messages.getString("tab.name.prefix.rmds"));
			break;
		
		}
		txtName.updateUI();
		
	}
	
}
