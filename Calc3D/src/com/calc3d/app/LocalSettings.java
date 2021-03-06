package com.calc3d.app;

import java.awt.Color;

import com.calc3d.geometry3d.Box3D;

public class LocalSettings {
public  double minX=-5;
public  double maxX=5;
public  double minY=-5;
public  double maxY=5;
public  double minZ=-5;
public  double maxZ=5;

public  Box3D mappedClipBox=new Box3D(-1,1,-1,1,-1,1);
/*
 * Gui Settings
 */
public  String lookandFeel;
public  boolean fileToolbarVisible=true;
public  boolean objectToolbarVisible=true;
public  boolean statusbarVisible=true;
public  boolean tipofDayEnabled=true;
public  boolean splashScreenEnabled=true;

/*
 * Renderer Settings
 */
public   boolean antiAliasingEnabled=false;
public   boolean perspectiveEnabled=true;
public   boolean steroscopyEnabled=false;
public   int steroscopicMode=2;
public   boolean fogEnabled=true;
public   boolean light1Enabled=true;
public   boolean light2Enabled=false;
public   boolean light3Enabled=false;
public  Color backgroundColor=Color.black;//new Color(230,230,240);//new Color(0,35,7);

/*
 * bOX sETTINGS
 */
public  boolean boxVisible=true;
public  boolean gridsVisible=false;
public  boolean planesVisible=false;
public  boolean labelsVisible=true;
public  boolean rulersVisible=true;
public  boolean ticksVisible=true;
public  boolean showMajorGrids,showMinorGrids;
public  double divisions=5;
public  double subdivisions=3;

/*
 * Axis Settings
 */
public  boolean xAxisVisible=true;
public  boolean yAxisVisible=true;
public  boolean zAxisVisible=true;
public  boolean xyGridVisible=false;
public  Box3D axesBox=new Box3D(-5,5,-5,5,-5,5);
public  int axisTicks=5;
public  int axisWidth=2;
public  Color axisColor=Color.black;//new Color(132,145,135).brighter();
public  Color planeColor=Color.black;//new Color(190,240,220);
public  Color gridColor=planeColor.darker();

public  double fov=50;
private boolean mouseInteractionAviable;

public LocalSettings(Preferences preferences) {
	this();
	this.saveSettings(preferences);
}

public LocalSettings() {
}

/**
 * maps clipcoordiantes to value between minx and maxX
 */
public  double mapCliptoX(double x){
	return minX+(maxX-minX)*(x-mappedClipBox.getMinX())/(mappedClipBox.getWidth());
}

/**
 * maps clipcoordiantes to value between minx and maxX
 */
public  double mapCliptoY(double y){
	return minY+(maxY-minY)*(y-mappedClipBox.getMinY())/(mappedClipBox.getHeight());
}

/**
 * maps clipcoordiantes to value between minx and maxX
 */
public  double mapCliptoZ(double z){
	return minZ+(maxZ-minZ)*(z-mappedClipBox.getMinZ())/(mappedClipBox.getDepth());
}
/**
 * maps x (value between -1 and 1) to value between minx and maxX
 */
public  double mapX(double x){
	return minX+(maxX-minX)*(x-mappedClipBox.getMinX())/(mappedClipBox.getWidth());
}
/**
 * maps y (value between -1 and 1) to value between minx and maxX
 */
public  double mapY(double y){
	return minY+(maxY-minY)*(y-mappedClipBox.getMinY())/(mappedClipBox.getHeight());
}
/**
 * maps z (value between -1 and 1) to value between minx and maxX
 */
public  double mapZ(double z){
	return minZ+(maxZ-minZ)*(z-mappedClipBox.getMinZ())/(mappedClipBox.getDepth());
}

/**
 * maps z(value between minz to max z) to value between -1 to 1
 */
public  double inverseMapZ(double z){
	return mappedClipBox.getMinZ()+mappedClipBox.getDepth()*(z-minZ)/(maxZ-minZ);
}
/**
 * maps y(value between minz to max z) to value between -1 to 1
 */
public  double inverseMapY(double y){
	return mappedClipBox.getMinY()+mappedClipBox.getHeight()*(y-minY)/(maxY-minY);
}
/**
 * maps x(value between minz to max z) to value between -1 to 1
 */
public  double inverseMapX(double x){
	return mappedClipBox.getMinX()+mappedClipBox.getWidth()*(x-minX)/(maxX-minX);
}


public  void recalculateClip(){
	double max=1;
	max=Math.max(Math.abs(minX), Math.abs(maxX));
	max=Math.max(max, Math.abs(minY));
	max=Math.max(max, Math.abs(maxY));
	max=Math.max(max, Math.abs(minZ));
	max=Math.max(max, Math.abs(maxZ));
	mappedClipBox=new Box3D(minX/max,maxX/max,minY/max,maxY/max,minZ/max,maxZ/max);
}

public  void saveSettings(Preferences preferences){
	minX=preferences.getClipBox().getMinX();
	maxX=preferences.getClipBox().getMaxX();
	minY=preferences.getClipBox().getMinY();
	maxY=preferences.getClipBox().getMaxY();
	minZ=preferences.getClipBox().getMinZ();
	maxZ=preferences.getClipBox().getMaxZ();

	divisions=preferences.getDivisions();
	subdivisions=preferences.getSubDivisions();
	showMajorGrids=preferences.isGridsVisible();
	showMinorGrids=preferences.isGridsVisible();
	backgroundColor=preferences.getBackColor();

	boxVisible=preferences.isBoxVisible();
	gridsVisible=preferences.isGridsVisible();
	planesVisible=preferences.isPlanesVisible();
	labelsVisible=preferences.isLabelsVisible();
	rulersVisible=preferences.isTicksVisible();
	ticksVisible=preferences.isTicksVisible();
	xAxisVisible=preferences.isxAxisVisible();
	yAxisVisible=preferences.isyAxisVisible();
	zAxisVisible=preferences.iszAxisVisible();
	xyGridVisible=preferences.isXyGridVisible();
	axesBox=preferences.getAxesBox();
	axisTicks=preferences.getAxisTicks();
	axisWidth=preferences.getAxisWidth();
	axisColor=preferences.getAxisColor();
	
	lookandFeel=preferences.getLookandFeel();
	fileToolbarVisible=preferences.isFileToolbarVisible();
	objectToolbarVisible=preferences.isObjectToolbarVisible();
	statusbarVisible=preferences.isStatusbarVisible();
	tipofDayEnabled=preferences.isTipofDayEnabled();
	splashScreenEnabled=preferences.isSplashScreenEnabled();
	
	antiAliasingEnabled=preferences.isAntiAliasingEnabled();
	perspectiveEnabled=preferences.isPerspectiveEnabled();
	steroscopyEnabled=preferences.isSteroscopyEnabled();
	steroscopicMode=preferences.getSteroscopicMode();
	backgroundColor=preferences.getBackColor();
	fogEnabled=preferences.isFogEnabled();
	light1Enabled=preferences.isLight1Enabled();
	light2Enabled=preferences.isLight2Enabled();
	light3Enabled=preferences.isLight3Enabled();
	this.mouseInteractionAviable = preferences.getMouseInteractionAviable();
	recalculateClip();
}

public  Preferences getSettings(){
	Preferences preferences=new Preferences();
	preferences.setLookandFeel(lookandFeel);

	preferences.setFileToolbarVisible(fileToolbarVisible);
	preferences.setObjectToolbarVisible(objectToolbarVisible);
	preferences.setStatusbarVisible(statusbarVisible);
    preferences.setTipofDayEnabled(tipofDayEnabled);
    preferences.setSplashScreenEnabled(splashScreenEnabled);
    
    preferences.setClipBox(new Box3D(minX,maxX,minY,maxY,minZ,maxZ));
    
    preferences.setAntiAliasingEnabled(antiAliasingEnabled);
    preferences.setPerspectiveEnabled(perspectiveEnabled);
    preferences.setSteroscopyEnabled(steroscopyEnabled);
    preferences.setSteroscopicMode(steroscopicMode);
  
    preferences.setFogEnabled(fogEnabled); 
    preferences.setLight1Enabled(light1Enabled); 
    preferences.setLight2Enabled(light2Enabled); 
    preferences.setLight3Enabled(light3Enabled); 
      
    preferences.setxAxisVisible(xAxisVisible);
    preferences.setyAxisVisible(yAxisVisible);
    preferences.setzAxisVisible(zAxisVisible);
            
    preferences.setAxesBox(axesBox);
    preferences.setAxisColor(axisColor);
    preferences.setAxisWidth(axisWidth);
    preferences.setAxisTicks(axisTicks);
    preferences.setBackColor(backgroundColor);
	
    preferences.setGridsVisible(gridsVisible);
    preferences.setLabelsVisible(labelsVisible);
    preferences.setBoxVisible(boxVisible);
    preferences.setPlanesVisible(planesVisible);
    preferences.setTicksVisible(ticksVisible);
    preferences.setDivisions((int)divisions);
    preferences.setSubDivisions((int)subdivisions);
   
    return preferences;
}

/**
 * @return the clipBox
 */
public  Box3D getClipBox() {
	return new Box3D(minX,maxX,minY,maxY,minZ,maxZ);
}


}
