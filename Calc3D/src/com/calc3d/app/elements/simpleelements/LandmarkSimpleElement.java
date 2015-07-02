package com.calc3d.app.elements.simpleelements;

public class LandmarkSimpleElement extends SimpleElement {
	
	public static String NAME_PREFIX = "lm-";
	private double[] coords; 
	public LandmarkSimpleElement(){
		super();
	}
	public LandmarkSimpleElement(String name){
		super(name);
		coords = new double[]{0,0,0};
	}

	public void addCoordinate(double[] data) {
		coords[0] = data[0];
		coords[1] = data[1];
		coords[2] = data.length == 3 ? data[2] : 0;
	}

	public double[] getCoords() {
		return coords;
	}
	
	public String getDescription(){
		return "x: "+coords[0]+"<br>"+
			   "y: "+coords[1]+"<br>"+
			   "z: "+coords[2];
	}
	
	public String toString(){
		return coords[0]+"\t"+coords[1]+"\t"+coords[2];
	}
	
	public String toString2D(){
		return coords[0]+"\t"+coords[1];
	}
	
	

}
