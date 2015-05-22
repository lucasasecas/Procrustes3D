package com.calc3d.app.elements.simpleelements;

public class DistanceSimpleElement extends SimpleElement {

	private SampleSimpleElement element2;
	private SampleSimpleElement element1;
	private static String namePrefix = "dist-";
	
	double distance;

	public DistanceSimpleElement(SampleSimpleElement sampleSimpleElement,
			SampleSimpleElement sampleSimpleElement2, double d) {
		this.distance = d;
		this.element1 = sampleSimpleElement;
		this.element2 = sampleSimpleElement2;
		
		this.description = distance + "";
	}
	
	public double getDistance(){
		return distance;
	}
	
	public SampleSimpleElement getElementA(){
		return element1;
	}
	
	public SampleSimpleElement getElementB(){
		return element2;
	}

		

}
