package com.calc3d.app.export;

public class ExporterFactory {

	public static IExporter getExporter(int type) {
		switch(type){
		case ExportConfiguration.TPS_TYPE:
			return new TpsExporter();
		}
		return null;
	}
	

}
