package com.calc3d.app.fileload;

import java.awt.font.NumericShaper;
import java.io.BufferedReader;
import java.io.FileReader;

import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.LandmarkSimpleElement;
import com.calc3d.app.elements.simpleelements.SampleSimpleElement;

public class MorphologikaLoader  extends FileLoader{

	int numCols = 0;
	int numSpecimens = 0;
	int dimensions = 2;
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
				if(currentLine.isEmpty()){
					continue;
				}
				if(currentLine.startsWith("[") && currentLine.endsWith("]")){
					int start = currentLine.indexOf("[");
					int end = currentLine.indexOf("]");
					String command = currentLine.substring(start+1,end);
					switch(command){
					case "individuals":
						numSpecimens = Integer.parseInt(br.readLine().trim());
						break;
					case "dimensions":
						dimensions = Integer.parseInt(br.readLine().trim());
						break;
					case "landmarks":
						numLandmarks = Integer.parseInt(br.readLine().trim());
						break;
					case "names":
						labels = br.readLine().trim().split("\\s+");
						break;
					case "rawpoints":
						while(counter<numSpecimens){
							String rawLine = br.readLine().trim();
							if(rawLine.isEmpty())
								continue;
							String name = labels!=null? rawLine.substring(1):"config-"+counter;
							SampleSimpleElement newSpecimen = new SampleSimpleElement(name);
							for(int i=0; i<numLandmarks; i++){
								String[] landmarkCoords = br.readLine().trim().split("\\s+");
								LandmarkSimpleElement landmark = new LandmarkSimpleElement("lm-"+i);
								double[] dCoords = new double[landmarkCoords.length];
								for(int j=0; j<landmarkCoords.length; j++){
									dCoords[j] = Double.parseDouble(landmarkCoords[j]);
								}
								landmark.addCoordinate(dCoords);
								newSpecimen.addElement(landmark);
							}
							entities.addElement(newSpecimen);
							counter++;
						}
					}
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
		doc.setDimension(dimensions);
		return doc;
	}

}
