package com.calc3d.app.panels;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.calc3d.app.dialogs.ColorDialog;
import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DCollection;
import com.calc3d.app.elements.Element3DMesh;
import com.calc3d.app.elements.Element3DPoint;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import javax.swing.SwingConstants;

import java.awt.Label;
import java.awt.List;

import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.util.ArrayList;

import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
/**
 * Landmark graphic panel editor
 * @author Lucas Marquez (lucasasecas@gmail.com)
 * @version 1.0.0
 * @since 	1.0.0
 */
public class Element3DPane extends JPanel implements ActionListener, Element3DPanelEditor{
	private JTextField textFieldLabel;
	private JLabel lblLabel;
	private JLabel lblRadious;
	private JLabel lblBorderColor;
	private JLabel lblBorderWidth;
	private JLabel lblBackColor;
	private JSpinner spinnerBorderWidth;
	private JSpinner spinnerRadious;
	private JButton btnSelectBorderColor;
	private JButton btnSelectBackColor;
	private ArrayList<Element3D> elements;
	private JComboBox comboBox;
	private ArrayList<Element3DPointModel> pointModels;
	private JComboBox lmComboBox;
	private ArrayList<Element3DCollectionModel> configModels;
	private Element3DPointModel currentPModel;
	private JPanel panel;
	private Element3DCollectionModel currentModel;
	private JCheckBox chckbxShow;
	
	
	public Element3DPane(){
		this(false);
	}
	public Element3DPane(boolean editable) {
		
		this.configModels = new ArrayList<Element3DCollectionModel>();
		
		lblLabel = new JLabel("Label");
		
		lblRadious = new JLabel("Radious");
		
		lblBorderWidth = new JLabel("Border width");
		
		lblBorderColor = new JLabel("Border Color");
		
		lblBackColor = new JLabel("Back Color");
		
		spinnerBorderWidth = new JSpinner();
		spinnerBorderWidth.setEnabled(editable);
		
		spinnerRadious = new JSpinner();
		spinnerRadious.setEnabled(editable);
		
		textFieldLabel = new JTextField();
		textFieldLabel.setColumns(10);
		textFieldLabel.setEnabled(editable);
		
		
		btnSelectBorderColor = new JButton("Select");
		btnSelectBorderColor.addActionListener(this);
		btnSelectBorderColor.setActionCommand("bodercolor");
		btnSelectBorderColor.setEnabled(editable);
		btnSelectBackColor = new JButton("Select");
		btnSelectBackColor.addActionListener(this);
		btnSelectBackColor.setActionCommand("backcolor");
		btnSelectBackColor.setEnabled(editable);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 4));
		
		JLabel lblConfiguration = new JLabel("Configuration");
		
		comboBox = new JComboBox();
		comboBox.addItem("Select a config");
		comboBox.addItem("---------------");
		comboBox.addActionListener(this);
		comboBox.setActionCommand("changedelement");
		comboBox.setEnabled(editable);
		
		
		this.elements = new ArrayList<Element3D>();
		
		JLabel lblLandmark = new JLabel("Landmark");
		
		lmComboBox = new JComboBox();
		lmComboBox.addActionListener(this);
		lmComboBox.setActionCommand("changedelement");
		lmComboBox.setEnabled(editable);
		lmComboBox.addItem("All");
		
		chckbxShow = new JCheckBox("Show");
		chckbxShow.setEnabled(editable);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblConfiguration)
							.addGap(18)
							.addComponent(comboBox, 0, 293, Short.MAX_VALUE)
							.addGap(64))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblLandmark)
									.addGap(37)
									.addComponent(lmComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 112, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(lblBorderWidth)
											.addComponent(lblLabel)
											.addComponent(lblRadious))
										.addGap(18)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(textFieldLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(chckbxShow))
											.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(spinnerRadious, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(spinnerBorderWidth, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))))
									.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(lblBorderColor)
											.addComponent(lblBackColor))
										.addGap(18)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(btnSelectBackColor)
											.addComponent(btnSelectBorderColor))
										.addGap(18)
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))))
							.addContainerGap(209, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblConfiguration)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLandmark)
						.addComponent(lmComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLabel)
						.addComponent(textFieldLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxShow))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(spinnerRadious, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblRadious))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblBorderWidth)
						.addComponent(spinnerBorderWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBorderColor)
								.addComponent(btnSelectBorderColor))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblBackColor)
								.addComponent(btnSelectBackColor)))
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(81, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
		
		
	}
	
	
	public Element3DPane(Element3DCollection elements){
		this(elements==null?false:true);
		
		if(elements!=null)
			this.addElements(elements);
	}
		
	public Element3DPane(Element3DCollection collection, int indexSelected) {
		this(collection);
		this.comboBox.setSelectedIndex(indexSelected+2);
//		this.changeElement(indexSelected+2, 0);
		
	}
	public void addElements(Element3DCollection elements){
//		ArrayList<Element3D> subElements = elements.getContainedElements();
//		for(Element3D elem : subElements){
//			if(elem instanceof Element3DCollection){
//				this.addConfigurations((Element3DCollection)elem);
//			}else{
//				Element3DCollection aux = new Element3DCollection();
//				aux.add(elem);
//				aux.setName(elem.getName());
//				this.addConfigurations(aux);
//			}
//			
//			
//		}
	}
	
	private void addConfigurations(Element3DCollection element) {
		this.elements.add(element);
		this.comboBox.addItem(element.getName());
		this.configModels.add(new Element3DCollectionModel(element));
		for(Element3D elem : element.getContainedElements()){
			this.lmComboBox.addItem(elem.getName());
		}
	}


	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String command = arg0.getActionCommand();
		if(command == "changedelement"){
			this.changeElement(this.comboBox.getSelectedIndex(), this.lmComboBox.getSelectedIndex());
		}
		if(command=="backcolor"){
			
			Color color = ColorDialog.show(this, panel.getBackground(), false);
			
			if(currentPModel==null){
				currentModel.setFillColor(color);
			}else
				currentPModel.setFillColor(color);
			
			this.updateForm();		
		}
		
	}
	
	private void changeElement(int indexElem, int indexLandmark){
		if(indexElem<2) return;
		this.savePreviousModel();
		indexElem-=2;
		Element3DCollectionModel model = this.configModels.get(indexElem);
		currentModel = model;
		
		boolean allLM = indexLandmark == 0 ? true: false;
		if(!allLM){
			indexLandmark--;
			Element3DPointModel pModel = model.getPoint(indexLandmark);
			currentPModel = pModel;			
		}else{
			currentPModel = null;
		}
		
		
		
		updateForm();
		
		//this.textFieldLabel.setText(arg0);
	}
	

	


	private void updateForm() {
		if(currentPModel == null){
				this.spinnerRadious.setValue(currentModel.getRadius());
				this.textFieldLabel.setEnabled(false);
				this.chckbxShow.setEnabled(false);
				this.panel.setBackground(currentModel.getFillColor());
		}else{
			this.spinnerRadious.setValue(currentPModel.getRadious());
			this.textFieldLabel.setText(currentPModel.getLabel());
			this.panel.setBackground(currentPModel.getBackColor());
			this.textFieldLabel.setEnabled(true);
			this.chckbxShow.setEnabled(true);
			this.panel.setBackground(currentPModel.getFillColor());
		}
	}


	private void savePreviousModel(){
		if(currentModel==null) return;
		if(currentPModel == null){
			currentModel.setRadious((int) spinnerRadious.getValue());
			currentModel.setBackColor(panel.getBackground());
			currentModel.setFillColor(panel.getBackground());
		}else{
			currentPModel.setRadious((int) spinnerRadious.getValue());
			currentPModel.setLabel(textFieldLabel.getText());
			currentPModel.setBackColor(panel.getBackground());
		}
	}


	public class Element3DPointModel {
		String id;
		String label;
		int radious;
		Color backColor;
		Color borderColor;
		
		boolean dirty;
		private Color fillColor;
		public Element3DPointModel(Element3DPoint point){
			id = point.getName();
			this.label = point.getText();
			this.radious = point.getRadius();
			this.backColor = point.getBackColor();
			this.fillColor = point.getFillColor();
			this.borderColor = point.getLineColor();
			this.dirty=false;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public int getRadious() {
			return radious;
		}
		public void setRadious(int radious) {
			this.radious = radious;
		}
		public Color getBackColor() {
			return backColor;
		}
		public void setBackColor(Color backColor) {
			this.backColor = backColor;
		}
		public Color getBorderColor() {
			return borderColor;
		}
		public void setBorderColor(Color borderColor) {
			this.borderColor = borderColor;
		}
		public boolean isDirty() {
			return dirty;
		}
		public void setDirty(boolean dirty) {
			this.dirty = dirty;
		}
		
		public Color getFillColor(){
			return this.fillColor;
		}
		
		public void setFillColor(Color c){
			this.fillColor = c;
		}

	}
	
	public class Element3DCollectionModel implements InputMethodListener{
		Element3DCollection element;
		ArrayList<Element3DPointModel> points;
		int radious;
		
		boolean dirty = false;
		private Color fillColor;
		private Color backColor;
		public Element3DCollectionModel(Element3DCollection element){
			this.element = element;
			this.radious = element.getContainedElements().size()!=0?((Element3DPoint)element.getContainedElements().get(0)).getRadius():0;
			this.backColor = element.getBackColor();
			this.fillColor = element.getFillColor();
			this.points = new ArrayList<Element3DPointModel>();
			for(Element3D point : element.getContainedElements()){
				this.addPoint((Element3DPoint) point);
			}
		}
		
		public Color getFillColor() {
			return this.fillColor;
		}

		public void setFillColor(Color fill) {
			if(this.fillColor.getRed()!=fill.getRed() || this.fillColor.getGreen()!=fill.getGreen()
					|| this.fillColor.getBlue()!=fill.getBlue()){
				for(Element3DPointModel point : this.points){
					point.setFillColor(fill);
				}
				this.fillColor=fill;
			}
			
			
		}

		public int getRadius() {
			return this.radious;
		}

		public void setBackColor(Color background) {
			if(this.backColor.getRed()!=background.getRed() || this.backColor.getGreen()!=background.getGreen()
					|| this.backColor.getBlue()!=background.getBlue()){
				for(Element3DPointModel point : this.points){
					point.setBackColor(background);
				}
				this.backColor=background;
			}
			
		}

		public void setRadious(int value) {
			if(this.radious!=value){
				this.radious=value;
				for(Element3DPointModel point : this.points){
					point.setRadious(value);
				}
				this.dirty=true;
			}
		}

		public Element3DPointModel getPoint(int indexLandmark) {
			return this.points.get(indexLandmark);
		}

		public void addPoint(Element3DPoint point){
			points.add(new Element3DPointModel(point));
		}
		
		public ArrayList<Element3DPointModel> getPoints(){
			return this.points;
		}
		
		public void setPoint(int index, Element3DPointModel point){
			this.points.set(index, point);
		}

		@Override
		public void caretPositionChanged(InputMethodEvent event) {
			int a =0;
			
		}

		@Override
		public void inputMethodTextChanged(InputMethodEvent event) {
			int b =0;
		}

		public void save() {
			ArrayList<Element3D> contPoints = element.getContainedElements();
			for(int i=0; i<points.size(); i++){
				Element3DPoint p3D = (Element3DPoint) contPoints.get(i);
				Element3DPointModel pModel = points.get(i);
				p3D.setRadius(pModel.getRadious());
				p3D.setText(pModel.getLabel());
				p3D.setBackColor(pModel.getBackColor());
				p3D.setFillColor(pModel.getBackColor());
				
			}
			
		}
		
		
		
	}

	@Override
	public Element3D getGeneratedElement() {
		
		return null;
	}



	@Override
	public void saveResult() {
		this.savePreviousModel();
		for(int i=0; i<configModels.size(); i++){
			Element3DCollectionModel config = configModels.get(i);
			config.save();
		}
		
	}

}
