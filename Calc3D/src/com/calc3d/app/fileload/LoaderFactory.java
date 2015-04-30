package com.calc3d.app.fileload;

public class LoaderFactory {
	
	public static FileLoader create(String ext){
		if(ext.endsWith(".tps")){
			return new TpsLoader();
		}
		if(ext.endsWith(".nts") || ext.endsWith(".NTS")){
			return new NtsLoader();
		}
		if(ext.endsWith(".txt")){
			return new MorphologikaLoader();
		}
		
		return null;
	};
	
}
