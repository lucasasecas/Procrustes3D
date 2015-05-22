package com.calc3d.app.analysis;

import java.util.ArrayList;

import org.ejml.simple.SimpleMatrix;

import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.DistanceSimpleElement;
import com.calc3d.app.elements.simpleelements.SampleSimpleElement;
import com.example.Algorithms.distances.DistanceCalculator;
import com.example.Algorithms.distances.LeastDistancedistanceDistance;
import com.example.Algorithms.distances.MediumRepitedDistances;

public class DistanceCalculatorAdapter {

	DistanceConfiguration configuration;
	
	public DistanceCalculatorAdapter(DistanceConfiguration configuration) {
		this.configuration = configuration;
	}

	public ComposeSimpleElement calculate(
			ArrayList<SampleSimpleElement> specimens) {
		
		String prefix = configuration.getType() == configuration.MIN_SQR_DISTANCE ? "PD_" : "RD_";
		
		ArrayList<SimpleMatrix> matArray = new ArrayList<SimpleMatrix>();
		for(int j=0; j<specimens.size(); j++){
			matArray.add(new SimpleMatrix(specimens.get(j).toMatrix()));
		}
		
		DistanceCalculator calc = this.getCalculator();
		double[][] result  = calc.calculate(matArray);
		ComposeSimpleElement list = new ComposeSimpleElement(prefix + "distance");
		ComposeSimpleElement values = new ComposeSimpleElement("values");
		for(int i=0; i<specimens.size(); i++){
			for(int j=i; j<specimens.size();j++){
				if(j!=i){
					DistanceSimpleElement dist = new DistanceSimpleElement(specimens.get(i), specimens.get(j), result[i][j]);
					dist.setName("dist-"+i+"-to-"+j);
					values.addElement(dist);
				}
			}
		}
		list.addElement(values);
		ComposeSimpleElement containerList = new ComposeSimpleElement("distances");
		containerList.addElement(list);
		return list;
	}

	private DistanceCalculator getCalculator() {
		if(configuration.getType() == configuration.MIN_SQR_DISTANCE){
			return new LeastDistancedistanceDistance();
		}
		if(configuration.getType() == configuration.ROB_DISTANCE){
			return new MediumRepitedDistances();
		}
		return null;
	}

}
