package com.calc3d.app.reports;

import java.util.ArrayList;

import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.LandmarkSimpleElement;
import com.calc3d.app.elements.simpleelements.SampleSimpleElement;
import com.calc3d.app.elements.simpleelements.SimpleElement;
import com.calc3d.app.resources.Messages;

public class ProcrustesFitDetalier implements DataDetailer {

	@Override
	public String getDetails(SimpleElement data) {
		String report = "";
		ComposeSimpleElement dataset = (ComposeSimpleElement) data;
		ComposeSimpleElement specimens = (ComposeSimpleElement)dataset.getElementByKey(Messages.getString("config.specimens"));
		SampleSimpleElement consensus = (SampleSimpleElement)dataset.getElementByKey(Messages.getString("config.consensus"));
		
		if(specimens!= null){
			report+= "Superimposed Coordinates"+'\n';
			for(int i=0; i<specimens.size(); i++){
				ComposeSimpleElement sample = (ComposeSimpleElement)specimens.getContainedElement(i);
				report += "----------------------------------------------------------------------------------------------------------------------------------------------"+'\n';
				report += "Specimen: " + sample.getName()+'\n';
				report += "----------------------------------------------------------------------------------------------------------------------------------------------"+'\n';
				ArrayList<LandmarkSimpleElement> landmarks = (ArrayList<LandmarkSimpleElement>) sample.getAllElements();
				for(int j=0; j<landmarks.size(); j++){
					LandmarkSimpleElement landmark = landmarks.get(j);
					report += landmark.getCoords()[0] + "		" + landmark.getCoords()[1] + "		" +
							landmark.getCoords()[2]+'\n';
					
				}
			}
		}
		
		if(consensus != null){
			report += "----------------------------------------------------------------------------------------------------------------------------------------------"+'\n';
			report += "Consensus: " + consensus.getName()+'\n';
			report += "----------------------------------------------------------------------------------------------------------------------------------------------"+'\n';
			
			ArrayList<LandmarkSimpleElement> landmarks = (ArrayList<LandmarkSimpleElement>) consensus.getAllElements();
			for(int j=0; j<landmarks.size(); j++){
				LandmarkSimpleElement landmark = landmarks.get(j);
				report += landmark.getCoords()[0] + "		" + landmark.getCoords()[1] + "		" +
						landmark.getCoords()[2]+'\n';
				
			}
		}
		return report;
	}

}
