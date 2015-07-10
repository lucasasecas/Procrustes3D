package com.calc3d.app.dialogs;

import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.ldap.Rdn;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.calc3d.app.CopyOfGui;
import com.calc3d.app.elements.Element3DFactory;
import com.calc3d.app.elements.simpleelements.SimpleElement;
import com.calc3d.app.export.ExportConfiguration;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;
import javax.swing.JButton;
import javax.swing.JRadioButton;

public class ExportObjectDialog extends JDialog implements ActionListener {
	private JTextField tfFileLocation;
	private JButton btnBrowse;
	private Window window;
	private JRadioButton rdbtnTps;
	private JRadioButton rdbtnNts;
	public  boolean canceled = false;
	private int typeExport;
	private JButton btnAcept;
	private JButton btnCancel;
	private ExportObjectDialog(Window window,SimpleElement elements) {
		super(window, "Export", ModalityType.APPLICATION_MODAL);
		this.setSize(400, 300);
		this.window = window;
		JLabel lblSelectFolder = new JLabel("Select Folder:");
		
		tfFileLocation = new JTextField();
		tfFileLocation.setColumns(10);
		tfFileLocation.setEditable(false);
		tfFileLocation.setEnabled(false);
		
		btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(this);
		btnBrowse.setActionCommand("browse");
		
		rdbtnTps = new JRadioButton("TPS");
		rdbtnTps.addActionListener(this);
		rdbtnTps.setActionCommand("rdTps");
				
		rdbtnNts = new JRadioButton("NTS");
		rdbtnNts.addActionListener(this);
		rdbtnNts.setActionCommand("rdNts");
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNts);
		group.add(rdbtnTps);
		
		JLabel lblDatasetFormat = new JLabel("Dataset Format:");
		
		btnAcept = new JButton("Export");
		btnAcept.addActionListener(this);
		btnAcept.setActionCommand("acept");
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		btnCancel.setActionCommand("cancel");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(22)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSelectFolder)
								.addComponent(tfFileLocation, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
									.addComponent(lblDatasetFormat)
									.addGap(93)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(rdbtnNts)
										.addComponent(rdbtnTps))))
							.addGap(6)
							.addComponent(btnBrowse))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnAcept)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancel)))
					.addContainerGap(122, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSelectFolder)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfFileLocation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBrowse))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDatasetFormat)
						.addComponent(rdbtnTps))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNts)
					.addPreferredGap(ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAcept)
						.addComponent(btnCancel))
					.addContainerGap())
		);
		getContentPane().setLayout(groupLayout);
	}

	
	
	public static ExportConfiguration show(CopyOfGui copyOfGui,
			SimpleElement selected) {
		ExportObjectDialog dialog = new ExportObjectDialog(copyOfGui, selected);
		dialog.setLocationRelativeTo(copyOfGui);
		dialog.setVisible(true);
		if(!dialog.canceled){
			ExportConfiguration config = dialog.getConfiguration();
			dialog.setVisible(false);
			return config;
		}
		return null;
	}



	private ExportConfiguration getConfiguration() {
		ExportConfiguration config = new ExportConfiguration(this.typeExport, this.tfFileLocation.getText());
		return config;
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command=arg0.getActionCommand();
		switch (command) {
		case "browse":
			final JFileChooser fileChooser=new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        this.getExtension(), this.getExtension());
			fileChooser.setFileFilter(filter);
			
			String path = ((CopyOfGui)window).getFileName(true, getExtension(), "Export to...");
			this.tfFileLocation.setText(path);
			break;
		case "cancel":
			this.setVisible(false);
			this.canceled  = true;
			break;
		case "acept":
			this.setVisible(false);
			break;
		case "rdTps":
			this.btnBrowse.setEnabled(true);
			this.typeExport = ExportConfiguration.TPS_TYPE;
			break;
		case "rdNts":
			this.btnBrowse.setEnabled(true);
			this.typeExport = ExportConfiguration.NTS_TYPE;
			break;
		default:
			break;
		}
		
		
	}



	private String getExtension() {
		if(rdbtnNts.isSelected()){
			return ".nts";
		}else if(rdbtnTps.isSelected()){
			return ".tps";
		}
		return "";
	}
}
