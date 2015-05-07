
package com.calc3d.app.fileload;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import com.calc3d.app.elements.dataset.DataSet;
import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.LandmarkSimpleElement;
import com.calc3d.app.elements.simpleelements.SampleSimpleElement;
import com.example.loaders.ILoadedDocument;
import com.example.loaders.Landmark;
import com.example.loaders.PCEntity;
import com.example.loaders.TpsLoadedDocument;

/**
 * 
 * @author Lucas Marquez
 *	this class load tpsfiles
 */

public class TpsLoader extends DataSetLoader{

	int lmCounter = 0;
	int lmCount = 0;
	SampleSimpleElement newSpecimen;
	@Override
	public Object load(String filepath){ 
		BufferedReader br;
		ComposeSimpleElement doc = new ComposeSimpleElement();
		ComposeSimpleElement entities = new ComposeSimpleElement("specimens");
		try{
			String currentLine;
			br = new BufferedReader(new FileReader(filepath));
			String header="";
			int counter=0;
			while((currentLine = br.readLine()) != null){
				currentLine = currentLine.trim();
				if(currentLine.startsWith("LM") || currentLine.startsWith("lm")){
					int numLandmarks = Integer.parseInt(currentLine.split("=")[1]);
					newSpecimen = new SampleSimpleElement("sample-"+counter);
					entities.addElement(newSpecimen);
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
					counter++;
				}else if(currentLine.startsWith("ID") || currentLine.startsWith("id")){
					newSpecimen.setName(currentLine.split("=")[1]);
					newSpecimen.setDescription("Sample " + currentLine.split("=")[1]);
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
		return doc;
	}
	
}
