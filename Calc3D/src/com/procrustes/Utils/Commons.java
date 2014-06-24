package com.procrustes.Utils;

import java.awt.Color;
import java.util.ArrayList;

import org.ejml.simple.SimpleMatrix;

import com.calc3d.app.Globalsettings;
import com.calc3d.app.Preferences;
import com.calc3d.app.elements.Element3D;
import com.calc3d.app.elements.Element3DEntity;
import com.calc3d.app.elements.Element3DPoint;
import com.calc3d.geometry3d.Box3D;
import com.calc3d.math.Vector3D;
import com.example.loaders.Landmark;
import com.example.loaders.PCEntity;
import com.procrustes.dataContainer.ProcrustesResult;

public class Commons {

	public static double[] getRowFromMatrix(SimpleMatrix m, int pos){
		double[] row = new double[m.numCols()];
		for(int i=0; i<m.numCols(); i++)
			row[i] = m.get(pos, i);
		return row;
	}

	public static ArrayList<Element3D> resultToElement3D(
			ProcrustesResult result) {
		ArrayList<PCEntity> entities = result.getTransformations();
		ArrayList<Element3D> list = new ArrayList<Element3D>();
		ArrayList<Landmark> consensus = result.getResult();
//		supuesta entidad
		Element3DEntity entity = new Element3DEntity();
		entity.setName("Result");
		//dibujo resultado
		for(int i=0; i<consensus.size(); i++){
			String name = "Result-LM" + i;
			double[] coords = consensus.get(i).getCoordinates();
			double zCoord = coords.length == 3 ? coords[2] : 0;
			Element3DPoint point = new Element3DPoint(coords[0], coords[1], zCoord);
			point.setFillColor(Color.WHITE);
			point.setLineColor(Color.WHITE);
			point.setRadius(4);
			point.setText("");
			point.setName(name);
			//list.add(point);
			entity.addPoint(point);
//			list.add(point);
		}
		for(int i=0; i<entities.size(); i++){
			Element3DEntity especimen = new Element3DEntity();
			
			especimen.setName("Especimen "+ i);
			ArrayList<Landmark> lms = entities.get(i).getLandmarks();
			int R = 128 + (int) (Math.random() * 128);
			int G = 128 + (int) (Math.random() * 128);
			int B = 128 + (int) (Math.random() * 128);
			Color entityColor = new Color(R, G, B); 
			for(int j=0; j<lms.size(); j++){
				String name = "E" + i + "-LM" + j;
				double[] coords = lms.get(j).getCoordinates();
				double zCoord = coords.length == 3 ? coords[2] : 0;
				Element3DPoint point = new Element3DPoint(coords[0], coords[1], zCoord);
				point.setFillColor(entityColor);
				point.setLineColor(entityColor);
				point.setText("");
				point.setRadius(3);
				point.setName(name);
				especimen.addPoint(point);
			}
			especimen.setBackColor(entityColor);
			especimen.setFillColor(entityColor);
			especimen.setLineColor(entityColor);
			list.add(especimen);
		}
		list.add(entity);
		return list;
	}

	public static Preferences setPreferences(ArrayList<Element3D> list) {
		Preferences preferences = Globalsettings.getSettings();
		double maxX, maxY, maxZ, minX, minY, minZ;
		maxX = maxY = maxZ = minX = minY = minZ = 0;
		for(int i=0; i<list.size() -1; i++){
 			Vector3D maxVal =  list.get(i).getMaxBound();
			Vector3D minVal = list.get(i).getMinBound();
			maxX = maxX < maxVal.getX() ? maxVal.getX() : maxX;
			maxY = maxY < maxVal.getY() ? maxVal.getY() : maxY;
			maxZ = maxZ < maxVal.getZ() ? maxVal.getZ() : maxZ;
			minX = minX > minVal.getX() ? minVal.getX() : minX;
			minY = minY > minVal.getY() ? minVal.getY() : minY;
			minZ = minZ > minVal.getZ() ? minVal.getZ() : minZ;
		}
		double min, max;
		max = Math.max(Math.max(maxX, maxY), maxZ);
		min = Math.min(Math.min(minX, minY), minZ);
		Box3D axesBox = new Box3D(min, max, min, max, min, max);
		preferences.setAxesBox(axesBox);
		preferences.setClipBox(axesBox);
		return preferences;
		
	}
}