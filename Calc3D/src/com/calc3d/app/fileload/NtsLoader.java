package com.calc3d.app.fileload;

import java.io.BufferedReader;
import java.io.FileReader;

import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.LandmarkSimpleElement;
import com.calc3d.app.elements.simpleelements.SampleSimpleElement;

public class NtsLoader extends FileLoader{

	public static final int RECTANGULAR_MATRIX = 0;
	public static final int SYMMETRIC_DISSIMILARITY_MATRIX = 1;
	int numCols = 0;
	int numSpecimens = 0;
	int dimentions = 2;
	int numLandmarks = 0;
	String[] labels = null;
	@Override
	public Object load(String filepath) {
		BufferedReader br;
		ComposeSimpleElement doc = new ComposeSimpleElement();
		ComposeSimpleElement entities = new ComposeSimpleElement("specimens");
		try{
			String currentLine;
			br = new BufferedReader(new FileReader(filepath));
			String header="";
			int counter=0;
			boolean headReaded = false;
			boolean labelsReaded = false;
			int labelsReadedCount = 0;
			while((currentLine = br.readLine()) != null){
				currentLine = currentLine.trim();
				if(currentLine.isEmpty() || currentLine.startsWith("\"")){
					continue;
				}
//				read header header
				if(!currentLine.startsWith("\"") && !headReaded && currentLine.startsWith("1")){
					headReaded=true;
					String[] headerRow = currentLine.split(" ");
					if(headerRow.length == 5)
						dimentions = Integer.parseInt(headerRow[4].split("=")[1]);
					if(headerRow[1].endsWith("L")){
						numSpecimens = Integer.parseInt(headerRow[1].substring(0,headerRow[1].indexOf('L')));
						labels = new String[numSpecimens];
					}else{
						numSpecimens = Integer.parseInt(headerRow[1]);
					}
					numLandmarks = Integer.parseInt(headerRow[2])/dimentions;
					continue;
				}else if(labels != null && !labelsReaded){
					if(currentLine.isEmpty()){
						continue;
					}
					String[] labelsRow = currentLine.split(" ");
					for(int i=0; i<labelsRow.length; i++){
						labels[labelsReadedCount] =  labelsRow[i];
						labelsReadedCount++;
					}
					if(labels.length == numSpecimens)
						labelsReaded=true;
				}else {
					String name = labels==null?"sample-"+counter:labels[counter];
					SampleSimpleElement newSpecimen = new SampleSimpleElement(name);
					entities.addElement(newSpecimen);
					String[] landmarkCoords = currentLine.trim().split("\\s+");
					LandmarkSimpleElement landmark = new LandmarkSimpleElement("lm-"+0);
					double[] dCoords = new double[landmarkCoords.length];
					for(int j=0; j<landmarkCoords.length; j++){
						dCoords[j] = Double.parseDouble(landmarkCoords[j]);
					}
					landmark.addCoordinate(dCoords);
					newSpecimen.addElement(landmark);
					for(int i=1; i<numLandmarks; i++){
						landmarkCoords = br.readLine().trim().split("\\s+");
						landmark = new LandmarkSimpleElement("lm-"+i);
						dCoords = new double[landmarkCoords.length];
						for(int j=0; j<landmarkCoords.length; j++){
							dCoords[j] = Double.parseDouble(landmarkCoords[j]);
						}
						landmark.addCoordinate(dCoords);
						newSpecimen.addElement(landmark);
					}
					counter++;
				}
			}
			if(entities.getAllElements().size()!=0){
				doc.addElement(entities);
			}
			int find =filepath.lastIndexOf('\\') == -1 ? 0 : filepath.lastIndexOf('\\')+1;
			int	lind = filepath.lastIndexOf('.');
			doc.setName( filepath.substring(find, lind));
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
		doc.setDimension(dimentions);
		return doc;
	}

}
