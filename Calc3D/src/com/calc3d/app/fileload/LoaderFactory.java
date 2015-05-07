package com.calc3d.app.fileload;

import com.calc3d.app.exceptions.InvalidDatasetFileException;
import com.calc3d.app.resources.Messages;

public class LoaderFactory {
	
	public static FileLoader create(String ext) throws InvalidDatasetFileException{
		if(ext.endsWith(".tps")){
			return new TpsLoader();
		}
		if(ext.endsWith(".nts") || ext.endsWith(".NTS")){
			return new NtsLoader();
		}
		if(ext.endsWith(".txt")){
			return new MorphologikaLoader();
		}
		throw new InvalidDatasetFileException(Messages.getString("exception.datasetfile"));
		
	};
	
}
