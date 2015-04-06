package com.calc3d.app.elements;

import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.SimpleElement;

public class Element3DFactory {

	public final static int PROJECTION_ELEMENT = 0;
	public final static int DISTANCE_ELEMENT = 1;
	public final static int PROCRUSTES_ELEMENT = 2;
	public final static int DATASET_ELEMENT = 3;
	


	public static Element3D generate(SimpleElement simpleElement,
			int elementType) {
		if(simpleElement==null)
			return null;
		if(elementType == PROJECTION_ELEMENT){
			return new Element3DProjection((ComposeSimpleElement) simpleElement);
		}
		if(elementType == DISTANCE_ELEMENT){
			return null;
		}
		if(elementType == PROCRUSTES_ELEMENT){
			return new Element3DDataSet((ComposeSimpleElement) simpleElement);
		}
		if(elementType == DATASET_ELEMENT){
			return new Element3DDataSet((ComposeSimpleElement) simpleElement);
		}
		return null;
	}
}
