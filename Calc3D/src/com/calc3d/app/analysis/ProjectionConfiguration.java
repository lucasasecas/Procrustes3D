package com.calc3d.app.analysis;

import java.awt.Color;

import com.calc3d.app.Preferences;

public class ProjectionConfiguration extends DialogConfiguration {

	public static final int LEAST_SQR_PROJETION = 0;
	public static final int ROBUST_PROJECTION = 1;
	int type = 0;
	private int dimentions=2;
	private String name =""; 
	
	public ProjectionConfiguration(int i) {
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public void setDimensions(int i) {
		this.dimentions = i;
		
	}

	public int getDimensions() {
		return dimentions;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	@Override
	public Preferences getGraphPreferences(){
		this.graphPreferences.setBoxVisible(true);
		this.graphPreferences.setMousInteractionAviable(this.dimentions==2?false:true);
		this.graphPreferences.setXyGridVisible(true);
		this.graphPreferences.setzAxisVisible(this.dimentions==2?false:true);
		this.graphPreferences.setAxisColor(Color.black);
//		this.graphPreferences.setxAxisVisible(true);
//		this.graphPreferences.setyAxisVisible(true);
//		this.graphPreferences.setzAxisVisible(true);
		return graphPreferences;
	}

}
