package com.calc3d.app.models;

import java.util.ArrayList;

public class DatasetModel {
	ArrayList<AnalysisModel> analysisList;
	ArrayList<DatasetElement> elements;
	
	public DatasetModel(){
		analysisList = new ArrayList<AnalysisModel>();
		elements = new ArrayList<DatasetElement>();
	}
	
	public void addAnalysis(AnalysisModel analysis){
		this.analysisList.add(analysis);
	}
	
	public void addElement(DatasetElement element){
		this.elements.add(element);
	}
	
}
