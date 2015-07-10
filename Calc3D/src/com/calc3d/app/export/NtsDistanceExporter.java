package com.calc3d.app.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.DistanceSimpleElement;
import com.calc3d.app.elements.simpleelements.LandmarkSimpleElement;
import com.calc3d.app.elements.simpleelements.SampleSimpleElement;
import com.calc3d.app.elements.simpleelements.SimpleElement;
import com.procrustes.Utils.Commons;

public class NtsDistanceExporter implements IExporter {

	@Override
	public void export(SimpleElement element, String source) {
		File file = new File(source);
		FileWriter fw;
		
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			int z=0;
			fw  = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			ComposeSimpleElement distances = (ComposeSimpleElement) ((ComposeSimpleElement)element).getElementByKey("values");
			String ids = getIds(distances);
			String matrix = matrixToString(distances);
			String header = "2 \t"+(int)Commons.countOfElementsByDistances(distances.getAllElements().size())+"L\t"+(int)Commons.countOfElementsByDistances(distances.getAllElements().size())+"\t0";
			fw.write(header+'\n');
			fw.write(ids+'\n');
			fw.write(matrix);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private String matrixToString(ComposeSimpleElement distances) {
		ArrayList<SimpleElement> dists = (ArrayList<SimpleElement>) distances.getAllElements();
		double cantElements = com.procrustes.Utils.Commons.countOfElementsByDistances(dists.size());

		String result = "";
		
		for(int i=0; i<cantElements; i++){
			 
			for(int j=0; j<=i; j++){
				int k = Commons.productoria((int) cantElements-2, j)+i-1;
				
				if(i==j){
					result += "0.0000\n";
				}else{
					System.out.println(k);
					result += ((DistanceSimpleElement) dists.get(k)).getDistance() + "\t";
				}
				
			}
		}
		return result;
	}

	private String getIds(ComposeSimpleElement distances) {
		ArrayList<SimpleElement> dists = (ArrayList<SimpleElement>) distances.getAllElements(); 
		String ids ="", firstId;
		int counter = 0;
		DistanceSimpleElement first = (DistanceSimpleElement) dists.get(0);
		firstId = first.getElementA().getName();
		ids +=first.getElementA().getName() + "\t ";
		while(first.getElementA().getName().equals(firstId)){
			ids += first.getElementB().getName() + "\t";
			first = (DistanceSimpleElement) dists.get(counter++);
		}
		return ids;
	}

}
