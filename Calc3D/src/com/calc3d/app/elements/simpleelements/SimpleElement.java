package com.calc3d.app.elements.simpleelements;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

import org.jdesktop.swingx.action.ActionContainerFactory;

import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.actions.SimpleElementAction;

public abstract class SimpleElement implements Serializable{

	String name;
	ImageIcon icon;
	String description;
	ArrayList<SimpleElementAction> actions;
	int dimension = 3;

	public SimpleElement(String name2) {
		this.name = name2;
		this.description = name2;
		this.actions = new ArrayList<SimpleElementAction>();
	}

	public SimpleElement() {
		this.actions = new ArrayList<SimpleElementAction>();
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
	
	public void addAction(SimpleElementAction action){
		boolean eixtst = false;
		for(SimpleElementAction val : actions){
			if(val.getName() == action.getName())
				return;
		}
		actions.add(action);
	}
	
	public SimpleElementAction[] getAllActions(){
		SimpleElementAction[] arrayActions = new SimpleElementAction[actions.size()];
		arrayActions = actions.toArray(arrayActions);
		return arrayActions;
	}

	public SimpleElement[] getSubElements() {
		SimpleElement[] elems = new SimpleElement[1];
		elems[0] = this;
		return elems;
	}
	
	public void setDimension(int d){
		this.dimension = d;
	}
	
	public boolean is3D(){
		return this.dimension==3;
	}
 
}
