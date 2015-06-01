package com.calc3d.app.elements.simpleelements;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import com.calc3d.app.elements.Element3D;

public abstract class SimpleElement implements Serializable{

	String name;
	ImageIcon icon;
	String description;
	ArrayList<String> actions;

	public SimpleElement(String name2) {
		this.name = name2;
		this.description = name2;
		this.actions = new ArrayList<String>();
	}

	public SimpleElement() {
		this.actions = new ArrayList<String>();
	}

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
	
	public void addAction(String action){
		boolean eixtst = false;
		for(String val : actions){
			if(val == action)
				return;
		}
		actions.add(action);
	}
	
	public String[] getAllActions(){
		String[] arrayActions = new String[actions.size()];
		arrayActions = actions.toArray(arrayActions);
		return arrayActions;
	}
 
}
