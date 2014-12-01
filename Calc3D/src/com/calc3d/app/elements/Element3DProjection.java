package com.calc3d.app.elements;

import java.util.ArrayList;

import org.ejml.simple.SimpleMatrix;

import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.LandmarkSimpleElement;
import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementCollection;
import com.calc3d.math.Vector3D;

public class Element3DProjection extends Element3DCollection {

	
	public static int ROBUSTO = 0;
	public static int CM = 1;
	public int typeOp = 0;
	private ArrayList<org.apache.commons.math3.geometry.euclidean.threed.Vector3D> _projections; 
	
	public Element3DProjection(){
		super();
	}
	
	public Element3DProjection(ComposeSimpleElement projection) {
		ArrayList<LandmarkSimpleElement> elems = (ArrayList<LandmarkSimpleElement>) projection.getAllElements();
		for(int i=0; i<elems.size(); i++){
			LandmarkSimpleElement elem = elems.get(i);
			double[] coords = elem.getCoords();
			Element3DPoint point = new Element3DPoint(new Vector3D(coords[0]*0.001, coords[1]*0.001, coords[2]*0.001));
			point.setName(elem.getName()+"_Projection");
			point.setText(elem.getName());
			this.add(point);
		}
	}

	public void setTypeOperation(int op){
		typeOp = op;
	}

	@Override
	public String getDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTypeOp() {
		return typeOp;
	}
	
	@Override
	public Vector3D getMaxBound(){
		double maxX, maxY, maxZ;
		maxX = maxY = maxZ = 0;
		for(int i=0; i<elements.size(); i++){
			Vector3D point = elements.get(i).getMaxBound();
			maxX = maxX < point.getX() ? point.getX() : maxX;
			maxY = maxY < point.getY() ? point.getY() : maxY;
			maxZ = maxZ < point.getZ() ? point.getZ() : maxZ;
		}
		return new Vector3D(maxX, maxY, maxZ);
	}
	
	@Override
	public Vector3D getMinBound(){
		double minX, minY, minZ;
		minX = minY = minZ = 0;
		for(int i=0; i<elements.size(); i++){
			Vector3D point = elements.get(i).getMaxBound();
			minX = minX > point.getX() ? point.getX() : minX;
			minY = minY > point.getY() ? point.getY() : minY;
			minZ = minZ > point.getZ() ? point.getZ() : minZ;
		}
		return new Vector3D(minX, minY, minZ);
	}

	public void populateProjections(
			ArrayList<org.apache.commons.math3.geometry.euclidean.threed.Vector3D> projections,
			Element3D selected) {
		_projections = projections;
		ArrayList<Element3D> elems = ((Element3D)selected).getContainedElements();
		for(int i=0; i<projections.size(); i++){
			org.apache.commons.math3.geometry.euclidean.threed.Vector3D vector = projections.get(i);
			Element3D elem = elems.get(i);
			Element3DPoint point = new Element3DPoint(new Vector3D(vector.getX(), vector.getY(), vector.getZ()));
			point.setName(elem.getName()+"_Projection");
			point.setText("");
			this.add(point);
		}
		
		
	}
	
	@Override
	public ArrayList<Element3D> getContainedElements(){
		return elements;
	}
	
	

}
