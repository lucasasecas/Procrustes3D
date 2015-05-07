package com.calc3d.app.panels;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.calc3d.app.analysis.DatasetConfiguration;
import com.calc3d.app.analysis.DialogConfiguration;
import com.calc3d.app.fileload.FileLoader;
import com.calc3d.app.resources.Messages;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

import java.awt.Choice;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;

public class DatasetPane extends JPanel implements SimpleElementCreatePanel, ActionListener {
	private JTextField src;
	private JLabel lblName;
	private JLabel lblLocation;
	private JButton btnBrowse;
	private JTextField textField;
	
	
	public DatasetPane() {
		
		lblName = new JLabel(Messages.getString("panel.dataset.caption.tabname"));
		
		lblLocation = new JLabel(Messages.getString("panel.dataset.caption.datasetlocation"));
		
		src = new JTextField();
		src.setColumns(10);
		
		btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(this);
		btnBrowse.setActionCommand("browse");
		
		textField = new JTextField();
		textField.setColumns(10);
		src.setEditable(false);
		src.setEnabled(false);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLocation)
						.addComponent(lblName))
					.addGap(55)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(src, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBrowse))
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(43, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLocation)
						.addComponent(src, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse))
					.addContainerGap(228, Short.MAX_VALUE))
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

	@Override
	public DialogConfiguration getConfiguration() {
		DatasetConfiguration config = new DatasetConfiguration();
		config.setName(this.textField.getText());
		config.setSrc(this.src.getText());
		config.setTabTitle(this.textField.getText());
		
		return config;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command=arg0.getActionCommand();
		switch(command){
		case "browse":
			try{
				String path = FileLoader.getFileName(false, "", null, this);
				this.src.setText(path);
				String name = this.textField.getText();
				if(this.textField.getText().isEmpty() || this.textField.getText()==null){
					this.textField.setText(path.substring(path.lastIndexOf("\\")+1, path.lastIndexOf(".")));
				}
			
			}catch(Exception e){
				e.printStackTrace();
			}
			break;
		}
		
		
	}
	

}
