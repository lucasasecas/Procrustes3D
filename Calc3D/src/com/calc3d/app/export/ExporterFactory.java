package com.calc3d.app.export;

public class ExporterFactory {

	public static IExporter getExporter(int type) {
		switch(type){
		case ExportConfiguration.TPS_TYPE:
			return new TpsExporter();
			
		case ExportConfiguration.NTS_DISTANCE_TYPE:
			return new NtsDistanceExporter();
		case ExportConfiguration.NTS_TYPE:
			return new NtsExporter();
			
		}
		return null;
	}
	

}
