package com.calc3d.app.elements;

import java.awt.Image;

import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.SimpleElement;
import com.calc3d.app.resources.Icons;
import com.calc3d.app.resources.Messages;

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



	public static String getTypeStr(int elementType) {
		
		if(elementType == PROJECTION_ELEMENT){
			return Messages.getString("common.element.projection")+"s";
		}
		if(elementType == DISTANCE_ELEMENT){
			return Messages.getString("common.element.distance")+"s";
		}
		if(elementType == PROCRUSTES_ELEMENT){
			return Messages.getString("common.element.procrustesanalysis")+"s";
		}
		if(elementType == DATASET_ELEMENT){
			return Messages.getString("common.element.dataset")+"s";
		}
		return null;
		
	}



	public static Image getNewElementIcon(int typeElement) {
		if(typeElement == PROJECTION_ELEMENT){
			return Icons.PROJECTION.getImage();
		}
		if(typeElement == DISTANCE_ELEMENT){
			return Icons.DISTANCE.getImage();
		}
		if(typeElement == PROCRUSTES_ELEMENT){
			return Icons.CM.getImage();
		}
		if(typeElement == DATASET_ELEMENT){
			 return Icons.ADDDATASET.getImage();
		}
		return null;
	}
}
