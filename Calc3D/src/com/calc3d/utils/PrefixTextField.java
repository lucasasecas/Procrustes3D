package com.calc3d.utils;

import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class PrefixTextField extends JTextField {
    private String prefix;
    private JLabel label;

    public PrefixTextField(String prefix) {
        this.prefix = prefix;
        label = new JLabel("");
    }

    @Override
    protected void paintComponent(Graphics g) {
    	
        int w = SwingUtilities.computeStringWidth(
                getFontMetrics(getFont()), prefix);
        setMargin(new Insets(3, 3 + w, 3, 3));
        super.paintComponent(g);
        SwingUtilities.paintComponent(g, label, this.getParent(),
                2 + 3, 0, getWidth(), getHeight());
    }

	public void setPrefix(String string) {
		String fullText = label.getText();
		String subString = fullText.substring(prefix.length());
		label.setText(subString);
		System.out.println(label.getText());
		this.prefix = string + "_";
		this.label.setText(prefix + label.getText());
		
	}
	
	public String getFullText(){
		return label.getText() + super.getText();
	}
	
	public void setText(String text){
			super.setText(text);
	}

}
