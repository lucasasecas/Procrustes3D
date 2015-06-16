package com.calc3d.app.elements;

import java.awt.Color;
import java.util.ArrayList;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.ejml.simple.SimpleMatrix;


import com.calc3d.app.elements.simpleelements.LandmarkSimpleElement;
import com.calc3d.app.elements.simpleelements.SampleSimpleElement;
import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementCollection;
import com.calc3d.geometry3d.ElementPoint;
import com.calc3d.math.Vector3D;
import com.calc3d.utils.ColorUtils;
import com.calc3d.utils.IMatrixable;

public class Element3DEntity extends Element3DCollection implements IMatrixable<double[][]>{

	private Element _entityElement;
	private ArrayList<Element3DPoint> _points;
 	private SampleSimpleElement sample;
	public Element3DEntity(){
		_points = new ArrayList<Element3DPoint>();
	}
	
	public Element3DEntity(ArrayList<Element3D> points){
				elements = points;
	}
	
	
	
	public Element3DEntity(SimpleMatrix simpleMatrix) {
		int R = 128 + (int) (Math.random() * 128);
		int G = 128 + (int) (Math.random() * 128);
		int B = 128 + (int) (Math.random() * 128);
		Color entityColor = new Color(R, G, B); 
		this.setFillColor(entityColor);
		this.setLineColor(entityColor);
		_points = new ArrayList<Element3DPoint>();
		for(int i=0; i<simpleMatrix.numRows(); i++){
			try {
				Element3DPoint point =new Element3DPoint(new Vector3D(simpleMatrix.extractVector(true, i).getMatrix().getData()));
				point.setBackColor(entityColor);
				point.setFillColor(entityColor);
				_points.add(point);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Element3DEntity(SampleSimpleElement sample) {
		this(new SimpleMatrix(sample.toMatrix()));
		this.setName(sample.getName());
		Color entityColor = ColorUtils.getRandomBackgroundColor(Color.WHITE); 
		this.setFillColor(entityColor);
		this.setLineColor(entityColor);
		_points = new ArrayList<Element3DPoint>();
		ArrayList<LandmarkSimpleElement> lms =  (ArrayList<LandmarkSimpleElement>) sample.getAllElements();
		for(int i=0; i<lms.size(); i++){
			try {
				Element3DPoint point = new Element3DPoint(new Vector3D(lms.get(i).getCoords()));
				point.setName(lms.get(i).getName());
				point.setBackColor(entityColor);
				point.setFillColor(entityColor);
				_points.add(point);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public String getDefinition() {
		
		return "entidad";
	}

	@Override
	public Element createElement() {
		ElementCollection ec = new ElementCollection();
		for(int i=0; i<_points.size(); i++){
			ec.addElement(_points.get(i).getElement());
		}
		this.elementContainer = true;
		return ec;
	}

	@Override
	public Element createElement(Clip clip) {
		
		ElementCollection ec = new ElementCollection();
		try{
		if(T!=null)ec.transform(T);
		for(int i=0; i<_points.size(); i++){
			if(! _points.get(i).isVisible()) continue;
			Vector3D p = _points.get(i).getPoint();
			Vector3D tmpPoint = new Vector3D(settings.inverseMapX(p.getX()),settings.inverseMapY(p.getY()),settings.inverseMapZ(p.getZ()));
			ElementPoint npoint = new ElementPoint(tmpPoint); 
			npoint.setBackColor(_points.get(i).getBackColor());
			npoint.setFillColor(_points.get(i).getFillColor());
			npoint.setRadius(_points.get(i).getRadius());
			npoint.setText(_points.get(i).getText()==null?"":_points.get(i).getText());
			ec.addElement(npoint);
		}
		this.elementContainer = true;
		}catch(NullPointerException ne){
			ne.printStackTrace();
		}
		Element elem = clip.getClippedElement(ec);
		return elem;
	}
	
	public void addPoint(Element3DPoint p){
		_points.add(p);
	}
	
	@Override
	public Vector3D getMaxBound(){
		double maxX, maxY, maxZ;
		maxX = maxY = maxZ = Double.NEGATIVE_INFINITY;
		for(int i=0; i<_points.size(); i++){
			Vector3D point = _points.get(i).getPoint();
			maxX = maxX < point.getX() ? point.getX() : maxX;
			maxY = maxY < point.getY() ? point.getY() : maxY;
			maxZ = maxZ < point.getZ() ? point.getZ() : maxZ;
		}
		return new Vector3D(maxX, maxY, maxZ);
	}
	
	@Override
	public Vector3D getMinBound(){
		double minX, minY, minZ;
		minX = minY = minZ = Double.POSITIVE_INFINITY;
		for(int i=0; i<_points.size(); i++){
			Vector3D point = _points.get(i).getPoint();
			minX = minX > point.getX() ? point.getX() : minX;
			minY = minY > point.getY() ? point.getY() : minY;
			minZ = minZ > point.getZ() ? point.getZ() : minZ;
		}
		return new Vector3D(minX, minY, minZ);
	}
	
	@Override
	public ArrayList<Element3D> getContainedElements(){
		ArrayList<Element3D> list = new ArrayList<Element3D>();
		for(int i =0; i<_points.size(); i++){
			list.add(_points.get(i));
		}
		return list;
	}
	
	@Override 
	public void setVisible(boolean visible){
		super.setVisible(visible);
		for(int i=0; i<_points.size(); i++){
			_points.get(i).setVisible(visible);
		}
	}

	@Override
	public double[][] toMatrix() {
		double[][] aux = new double[_points.size()][3];
		for(int i=0; i<_points.size(); i++){
			aux[i] = _points.get(i).getPoint().getArray();
		}
		return aux;
	}

//	@Override
//	public Vector2D calculateCentroid(){
//		double[] center = new double[2];
//        for(Element3DPoint elem : _points) {
//        	center[0] += elem.getPoint().getX();
//        	center[1] += elem.getPoint().getY();
//        }
//        return new Vector2D(center[0] / _points.size(), center[1]/_points.size());
//	}
	
	public void select(boolean b){
		for(Element3DPoint point : _points){
			point.select(b);
		}
	}


	
	
	
}
