package com.calc3d.app.analysis;

import java.util.ArrayList;

import org.ejml.simple.SimpleMatrix;

import com.calc3d.app.elements.dataset.DataSet;
import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.SampleSimpleElement;
import com.calc3d.app.elements.simpleelements.SimpleElement;
import com.example.Algorithms.CM;
import com.example.Algorithms.IProcrustesCalculator;
import com.example.Algorithms.Robusto;
import com.example.loaders.PCEntity;
import com.procrustes.Utils.Commons;

public class ProcrustesCalculatorAdapter  {

	AnalysisConfiguration configuration;
	public ProcrustesCalculatorAdapter() {
	}
	
	public void setConfiguration(AnalysisConfiguration configuration2) {
		this.configuration = configuration2;
	}

	public ComposeSimpleElement calculate(ArrayList<SampleSimpleElement> elems) {
		IProcrustesCalculator calculator = getCalculator();
		ArrayList<SimpleMatrix> elements = new ArrayList<SimpleMatrix>(); 
		for(int i=0; i<elems.size(); i++){
			SampleSimpleElement entity = elems.get(i);			
			elements.add(new SimpleMatrix(entity.toMatrix()));
		}
		ArrayList<SimpleMatrix> result = calculator.execute(elements);
		ComposeSimpleElement dataset = new ComposeSimpleElement(configuration.getName());

		dataset.addElement(Commons.toPCEntity(result,elems));
		if(result.size()!= 0){
			SimpleMatrix consensus = result.get(result.size()-1);
			dataset.addElement(new SampleSimpleElement("consensus", consensus));
		}
		return dataset;

	}

	private IProcrustesCalculator getCalculator() {
		if(configuration.getType() == AnalysisConfiguration.MIN_SQUARES_FIT)
			return new CM();
		else if(configuration.getType() == AnalysisConfiguration.ROBUST_FIT)
			return new Robusto();
		return null;
	}



}
