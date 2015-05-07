package com.calc3d.app.elements.simpleelements;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import com.calc3d.app.elements.Element3D;

public abstract class SimpleElement implements Serializable{

	String name;
	ImageIcon icon;
	String description;

	public SimpleElement(String name2) {
		this.name = name2;
		this.description = name2;
	}

	public SimpleElement() {}

	public String getName() {
		return name;
	}
	
	public void setName(String nName){
		name = nName;
	}
	
	public void setIcon(ImageIcon icon){
		this.icon = icon;
	}
	
	public ImageIcon getIcon(){
		return icon;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}

}
