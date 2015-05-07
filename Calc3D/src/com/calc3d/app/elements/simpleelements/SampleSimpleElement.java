package com.calc3d.app.elements.simpleelements;

import java.util.ArrayList;
import java.util.Set;

import org.ejml.simple.SimpleMatrix;

public class SampleSimpleElement extends ComposeSimpleElement {

	public static String NAME_PREFIX = "lm-";

	public SampleSimpleElement(String name, SimpleMatrix mat) {
		super(name);
		ArrayList<SimpleElement> landmarks = new ArrayList<SimpleElement>();
		for(int i=0; i<mat.numRows(); i++){
			LandmarkSimpleElement lm = new LandmarkSimpleElement(this.NAME_PREFIX +i);
			lm.addCoordinate(mat.extractVector(true, i).getMatrix().getData());
			super.addElement(lm);
		}
		
	}

	public SampleSimpleElement() {
		super();
	}

	public SampleSimpleElement(String name) {
		super(name);
	}

	public double[][] toMatrix() {
		Set<String> keys = this.elements.keySet();
		double[][] elems = new double[this.elements.size()][3];
		int counter = 0;
		ArrayList<SimpleElement> elemsList = new ArrayList<SimpleElement>(elements.values());
		for(int i=0; i<elements.size(); i++){
			elems[i] = ((LandmarkSimpleElement) elemsList.get(i)).getCoords();	
		}
		return elems;
	}

	

	
}
