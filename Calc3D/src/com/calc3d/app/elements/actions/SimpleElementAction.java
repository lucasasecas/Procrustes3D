package com.calc3d.app.elements.actions;

import com.calc3d.app.CopyOfGui;
import com.calc3d.app.elements.simpleelements.SimpleElement;

public class SimpleElementAction {
	String name;
	String actionCommand;
	CopyOfGui model;
	SimpleElement element;
	
	public SimpleElementAction(String name, String actionCommand, CopyOfGui gui, SimpleElement element){
		this.name = name;
		this.actionCommand = actionCommand;
		this.model = gui;
		this.element = element;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getActionCommand() {
		return actionCommand;
	}

	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}

	public CopyOfGui getModel() {
		return model;
	}

	public void setModel(CopyOfGui model) {
		this.model = model;
	}
	
	public boolean isEnabled(){
		return true;
	}
	

}
