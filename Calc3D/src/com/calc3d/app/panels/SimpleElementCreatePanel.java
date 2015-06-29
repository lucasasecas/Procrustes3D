package com.calc3d.app.panels;

import com.calc3d.app.analysis.DialogConfiguration;
import com.calc3d.app.elements.simpleelements.SimpleElement;

public interface SimpleElementCreatePanel {
	boolean drawable = false;
	int PROJECTION_PANEL = 1;
	
	public boolean isDrawable();
	public boolean isValidInput();
	public DialogConfiguration getConfiguration();
	public void setName(String name);

}
