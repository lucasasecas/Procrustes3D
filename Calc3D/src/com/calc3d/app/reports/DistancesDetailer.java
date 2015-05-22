package com.calc3d.app.reports;

import java.lang.reflect.Array;
import java.util.ArrayList;




import java.util.Arrays;

import org.ejml.simple.SimpleMatrix;

import com.calc3d.app.elements.simpleelements.*;

public class DistancesDetailer implements DataDetailer{
	
	
	
	
	@Override
	public String getDetails(SimpleElement data) {
		String report = "";
		ComposeSimpleElement distanceElement = (ComposeSimpleElement) data;
		ArrayList<DistanceSimpleElement> dist = (ArrayList<DistanceSimpleElement>) ((ComposeSimpleElement) distanceElement.getElementByKey("values")).getAllElements();
		int distSizes = dist.size();
		int numberOfElements = this.getCountOfElements(dist.size());
		String names = "";
		double[][] distArrayMat = new double[numberOfElements][numberOfElements];
		int count = 0; 
		for(int i=0; i<numberOfElements; i++){
			names += dist.size()==0?"":dist.get(0).getElementA().getName()+ "   ";
			for(int j=i; j<numberOfElements; j++){
				if(i!=j){
					DistanceSimpleElement distance = dist.remove(0); 
					distArrayMat[i][j] = distance.getDistance();
					distArrayMat[j][i] = distance.getDistance();
				}
				else{
					distArrayMat[i][j] = 0;
					distArrayMat[j][i] = 0;
				}
			}
		}

		report += "Count of elements: "+numberOfElements+'\n';
		report += "Order of elements: \n";
		report += names;
		report += "Distances Matrix: \n";
		String mat = new SimpleMatrix(distArrayMat).toString();
		report += mat;

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
