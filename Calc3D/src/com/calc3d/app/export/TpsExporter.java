package com.calc3d.app.export;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.LandmarkSimpleElement;
import com.calc3d.app.elements.simpleelements.SampleSimpleElement;
import com.calc3d.app.elements.simpleelements.SimpleElement;

public class TpsExporter implements IExporter {

	@Override
	public void export(SimpleElement element, String source) {
		File file = new File(source);
		FileWriter fw;
		
			try {
				if (!file.exists()) {
					file.createNewFile();
				}
				fw  = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				ComposeSimpleElement specimens = (ComposeSimpleElement) ((ComposeSimpleElement)element).getElementByKey("specimens");
				for(SimpleElement elem : specimens.getAllElements()){
					SampleSimpleElement sample = (SampleSimpleElement) elem;
					int sampleDimension = sample.getDimension();
					fw.write("LM=" + sample.getAllElements().size() + '\n');
					for(SimpleElement landmark : sample.getAllElements()){
						fw.write(sampleDimension ==3?
								((LandmarkSimpleElement)landmark).toString() + '\n':
								((LandmarkSimpleElement)landmark).toString2D()+ '\n');
					}
					fw.write("ID="+sample.getName() + '\n');
					
				}
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		

	}
}
