package com.calc3d.app.analysis;

import com.calc3d.app.Globalsettings;
import com.calc3d.app.Preferences;
import com.calc3d.app.elements.Element3D;

public class DialogConfiguration {

	String tabTitle;
	Preferences graphPreferences = Globalsettings.getSettings(); 
	public void setTabTitle(String tabTitle) {
		this.tabTitle = tabTitle;
	}
	
	public String getTabTitle(){
		return this.tabTitle;
	}
	
	public Preferences getGraphPreferences(){
		return graphPreferences;
	}
	
	public void setGraphPreferences(Preferences preferences){
		this.graphPreferences = preferences;
	}

}
