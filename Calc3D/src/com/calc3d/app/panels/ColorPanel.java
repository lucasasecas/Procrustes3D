
package com.calc3d.app.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;

/**
 * Customer color editor.
 * @author William Bittle
 * @version 1.0.1
 * @since 1.0.0
 */
public class ColorPanel extends JPanel {
	/** The version id */
	private static final long serialVersionUID = -2158431496206909435L;

	/** The selected color */
	private Color color;
	
	/** The red slider label */
	private JLabel lblRed;
	
	/** The green slider label */
	private JLabel lblGreen;
	
	/** The blue slider label */
	private JLabel lblBlue;
	
	/** The alpha slider label */
	private JLabel lblAlpha;
	
	/** The slider for the red component */
	private JSliderWithTextField sldRed;
	
	/** The slider for the green component */
	private JSliderWithTextField sldGreen;
	
	/** The slider for the blue component */
	private JSliderWithTextField sldBlue;
	
	/** The slider for the alpha component */
	private JSliderWithTextField sldAlpha;
	
	/** The color preview panel */
	private JPanel pnlPreview;
	
	private JPanel pnlColor;
	
	private JPanel pnlButtons;
	/**
	 * Creates a color editor panel.
	 * @param initialColor the color to edit
	 */
	public ColorPanel(Color initialColor) {
		this(initialColor, true);
	}
	
	/**
	 * Creates a color editor panel.
	 * @param initialColor the color to edit
	 * @param showAlpha true if alpha should be included
	 */
	public ColorPanel(Color initialColor, boolean showAlpha) {
		this.color = initialColor;
		
		// setup the labels
		this.lblRed = new JLabel(Messages.getString("panel.color.red"), Icons.ABOUT, SwingConstants.LEFT);
		this.lblGreen = new JLabel(Messages.getString("panel.color.green"), Icons.ABOUT, SwingConstants.LEFT);
		this.lblBlue = new JLabel(Messages.getString("panel.color.blue"), Icons.ABOUT, SwingConstants.LEFT);
		this.lblAlpha = new JLabel(Messages.getString("panel.color.alpha"), Icons.ABOUT, SwingConstants.LEFT);
		
		this.lblRed.setToolTipText(Messages.getString("panel.color.red.tooltip"));
		this.lblGreen.setToolTipText(Messages.getString("panel.color.green.tooltip"));
		this.lblBlue.setToolTipText(Messages.getString("panel.color.blue.tooltip"));
		this.lblAlpha.setToolTipText(Messages.getString("panel.color.alpha.tooltip"));
		
		this.lblAlpha.setVisible(showAlpha);
		
		// setup the color sliders
		this.sldRed   = new JSliderWithTextField(0, 255, initialColor.getRed());
		this.sldGreen = new JSliderWithTextField(0, 255, initialColor.getGreen());
		this.sldBlue  = new JSliderWithTextField(0, 255, initialColor.getBlue());
		this.sldAlpha = new JSliderWithTextField(0, 255, showAlpha ? initialColor.getAlpha() : 255);
		
		this.sldRed.setMajorTickSpacing(51);
		this.sldRed.setMinorTickSpacing(17);
		this.sldRed.setPaintTicks(true);
		this.sldRed.setPaintLabels(true);
		
		this.sldGreen.setMajorTickSpacing(51);
		this.sldGreen.setMinorTickSpacing(17);
		this.sldGreen.setPaintTicks(true);
		this.sldGreen.setPaintLabels(true);
		
		this.sldBlue.setMajorTickSpacing(51);
		this.sldBlue.setMinorTickSpacing(17);
		this.sldBlue.setPaintTicks(true);
		this.sldBlue.setPaintLabels(true);
		
		this.sldAlpha.setMajorTickSpacing(51);
		this.sldAlpha.setMinorTickSpacing(17);
		this.sldAlpha.setPaintTicks(true);
		this.sldAlpha.setPaintLabels(true);
		this.sldAlpha.setVisible(showAlpha);
		
		this.sldRed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				// update the local color store
				JSliderWithTextField source = (JSliderWithTextField)event.getSource();
				// save the new color
				color = new Color(source.getValue(), color.getGreen(), color.getBlue(), color.getAlpha());
				// set the color of the preview panel
				Color dc = new Color(color.getRed(), color.getGreen(), color.getBlue(),color.getAlpha());
				pnlColor.setBackground(dc);
			}
		});
		this.sldGreen.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				// update the local color store
				JSliderWithTextField source = (JSliderWithTextField)event.getSource();
				// save the new color
				color = new Color(color.getRed(), source.getValue(), color.getBlue(), color.getAlpha());
				// set the color of the preview panel
				Color dc = new Color(color.getRed(), color.getGreen(), color.getBlue(),color.getAlpha());
				pnlColor.setBackground(dc);
			}
		});
		this.sldBlue.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				// update the local color store
				JSliderWithTextField source = (JSliderWithTextField)event.getSource();
				// save the new color
				color = new Color(color.getRed(), color.getGreen(), source.getValue(), color.getAlpha());
				// set the color of the preview panel
				Color dc = new Color(color.getRed(), color.getGreen(), color.getBlue(),color.getAlpha());
				pnlColor.setBackground(dc);
			}
		});
		this.sldAlpha.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				// update the local color store
				JSliderWithTextField source = (JSliderWithTextField)event.getSource();
				// save the new color
				color = new Color(color.getRed(), color.getGreen(), color.getBlue(), source.getValue());
				// set the color of the preview panel
				Color dc = new Color(color.getRed(), color.getGreen(), color.getBlue(),color.getAlpha());
				pnlColor.setBackground(dc);
				
			}
		});
		
		this.sldRed.setColumns(3);
		this.sldGreen.setColumns(3);
		this.sldBlue.setColumns(3);
		this.sldAlpha.setColumns(3);
		
		// setup the preview
		this.pnlPreview = new JPanel();
		
		Dimension s1 = new Dimension(300, 50);
		// update the preview (we dont display the alpha component)
		Color dc = new Color(initialColor.getRed(), initialColor.getGreen(), initialColor.getBlue());
		pnlColor=new JPanel();
		this.pnlColor.setBackground(dc);
		this.pnlColor.setOpaque(true);
		this.pnlPreview.setOpaque(true);
		this.pnlPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		this.pnlPreview.setMinimumSize(s1);
		this.pnlPreview.setPreferredSize(s1);
		this.pnlPreview.setLayout(new BorderLayout());
		
		JButton btnPlus=new JButton("+");
		btnPlus.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			updateColor(color.darker());
			}
		});
		
		
		JButton btnMinus=new JButton("-");
		btnMinus.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
			updateColor(color.brighter());
			}
		});
		pnlButtons=new JPanel();
		pnlButtons.setLayout(new BorderLayout());
		
		pnlButtons.add(btnPlus,BorderLayout.NORTH);
		pnlButtons.add(btnMinus,BorderLayout.SOUTH);
		pnlPreview.add(pnlColor);
		pnlPreview.add(pnlButtons,BorderLayout.EAST);
		// setup the layout
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		
		layout.setAutoCreateContainerGaps(true);
		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
				layout.createSequentialGroup()
				.addGroup(
						layout.createParallelGroup()
						.addGroup(
								layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup()
										.addComponent(this.lblRed)
										.addComponent(this.lblGreen)
										.addComponent(this.lblBlue)
										.addComponent(this.lblAlpha))
								.addGroup(
										layout.createParallelGroup()
										.addComponent(this.sldRed)
										.addComponent(this.sldGreen)
										.addComponent(this.sldBlue)
										.addComponent(this.sldAlpha)))
						.addComponent(this.pnlPreview)));
		
		layout.setVerticalGroup(
				layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup()
						.addComponent(this.lblRed)
						.addComponent(this.sldRed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(layout.createParallelGroup()
						.addComponent(this.lblGreen)
						.addComponent(this.sldGreen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(layout.createParallelGroup()
						.addComponent(this.lblBlue)
						.addComponent(this.sldBlue, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(layout.createParallelGroup()
						.addComponent(this.lblAlpha)
						.addComponent(this.sldAlpha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addComponent(this.pnlPreview));
	}
	
	/**
	 * Returns the selected color.
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}
	
	private void updateColor(Color newColor){
		// save the new color
		color=newColor;
		sldRed.setValue(color.getRed());
		sldGreen.setValue(color.getGreen());
		sldBlue.setValue(color.getBlue());
		sldAlpha.setValue(color.getAlpha());
	}
}