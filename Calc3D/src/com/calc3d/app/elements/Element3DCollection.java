package com.calc3d.app.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import com.calc3d.geometry3d.Clip;
import com.calc3d.geometry3d.Element;
import com.calc3d.geometry3d.ElementCollection;

public class Element3DCollection extends Element3D implements Collection {
	
	ArrayList<Element3D> elements = new ArrayList<Element3D>();

	public Element3DCollection(){}
	
	/**
	 * @param elements
	 */
	public Element3DCollection(ArrayList<Element3D> elements) {
		super();
		this.elements = elements;
	}

	@Override
	public String getDefinition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element createElement() {
		ElementCollection ec = new ElementCollection();
		for(int i=0; i<elements.size(); i++){
			ec.addElement(elements.get(i).getElement());
		}
		this.elementContainer = true;
		return ec;
	}

	@Override
	public Element createElement(Clip clip) {
		ElementCollection ec = new ElementCollection();
		if(T!=null)ec.transform(T);
		for(int i=0; i<elements.size(); i++){
			if(elements.get(i).isVisible())
				ec.addElement(elements.get(i).createElement(clip));
		}
		this.elementContainer = true;
		
		Element elem = clip.getClippedElement(ec);
		return elem;
	}

	@Override
	public boolean add(Object e) {
		elements.add((Element3D)e);
		return true;
	}

	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public ArrayList<Element3D> getContainedElements(){
		return elements;
	}
	
	@Override 
	public void setVisible(boolean visible){
		super.setVisible(visible);
		for(int i=0; i<elements.size(); i++){
			elements.get(i).setVisible(visible);
		}
	}
	
//	@Override
//	public Vector2D calculateCentroid(){
//		double[] center = new double[2];
//        for(Element3D elem : elements) {
//        	Vector2D auxCentroid = elem.calculateCentroid();
//        	center[0] += auxCentroid.getX();
//        	center[1] += auxCentroid.getY();
//        }
//        return new Vector2D(center[0] / elements.size(), center[1]/elements.size());
//
//	}
	
	@Override
	public void select(boolean b){
		for(int i=0; i<elements.size(); i++){
			elements.get(i).select(b);
		}
	}
	
	
	
}
