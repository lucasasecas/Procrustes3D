package com.calc3d.app.reports;

import java.util.ArrayList;

import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.LandmarkSimpleElement;
import com.calc3d.app.elements.simpleelements.SimpleElement;

public class ProjectionDetailer implements DataDetailer{

	@Override
	public String getDetails(SimpleElement data) {
		String report="\n";
		ComposeSimpleElement projections = (ComposeSimpleElement) data;
		for(LandmarkSimpleElement lm : (ArrayList<LandmarkSimpleElement>)projections.getAllElements()){
			report +=lm.getName()+"\t"+ lm.toString()+'\n';
		}
		return report+"\n\n\n";
	}

}
