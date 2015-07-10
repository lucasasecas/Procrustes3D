package com.calc3d.app.reports;

import java.lang.reflect.Array;
import java.util.ArrayList;




import java.util.Arrays;

import org.ejml.simple.SimpleMatrix;

import com.calc3d.app.elements.simpleelements.*;
import com.calc3d.app.resources.Messages;

public class DistancesDetailer implements DataDetailer{
	
	
	
	
	@Override
	public String getDetails(SimpleElement data) {
		String report = "";
		ComposeSimpleElement distanceElement = (ComposeSimpleElement) data;
		ArrayList<DistanceSimpleElement> dist = (ArrayList<DistanceSimpleElement>) ((ComposeSimpleElement) distanceElement.getElementByKey(Messages.getString("config.distances.values"))).getAllElements();
		int distSizes = dist.size();
		int numberOfElements = this.getCountOfElements(dist.size());
		String names = "";
		double[][] distArrayMat = new double[numberOfElements][numberOfElements];
		int count = 0; 
		report += "Specimens: "+numberOfElements+'\n';
		report += "Distance Matrix: \n";
			
		for(int i=0; i<numberOfElements; i++){
			for(int j=0; j<=i; j++){
				if(i!=j){
					DistanceSimpleElement distance = dist.remove(0); 
					report+=distance.getDistance()+"	";
//					distArrayMat[i][j] = distance.getDistance();
//					distArrayMat[j][i] = distance.getDistance();
				}
				else{
					report += "0.0000\n";
//					distArrayMat[i][j] = 0;
//					distArrayMat[j][i] = 0;
				}
			}
		}



		return report+"\n\n\n";
	}

	private int getCountOfElements(int size) {
		int result = 0;
		boolean cont = true;
		while(cont){
			if((result*(result-1))/2 == size)
				cont=false;
			else
				result++;
		}
		return result;
	}

}
