package com.calc3d.app.analysis.thread;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

import com.calc3d.app.CopyOfGui;
import com.calc3d.app.analysis.ProcrustesCalculatorAdapter;
import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.SampleSimpleElement;

public class ProcrustesAnalisysWorker extends javax.swing.SwingWorker<ComposeSimpleElement, Void> implements Observer{

	private JFrame currentWindow;
	private ProcrustesCalculatorAdapter calculator;
	private ArrayList<SampleSimpleElement> elems;
	private int tabIndex;
	private JProgressBar progressBar;

	public ProcrustesAnalisysWorker(JFrame window, ProcrustesCalculatorAdapter calculator, ArrayList<SampleSimpleElement> specimens
			, int indexTab, JProgressBar proBar){
		this.currentWindow = window;
		this.calculator = calculator;
		this.elems = specimens;
		this.tabIndex = indexTab;
		this.progressBar = proBar;
		calculator.addSubscriber(this);
	}
	
	@Override
	protected ComposeSimpleElement doInBackground() throws Exception {
		ComposeSimpleElement result = null;
		try{
			result = calculator.calculate(elems);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	protected void done(){
		try {
			this.progressBar.setValue(100);
			((CopyOfGui)currentWindow).addProcrustesAnalisys(this.get(), calculator.getConfiguration(), this.tabIndex);
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		this.progressBar.setValue( ((Double)arg1).intValue() );
		
	}
	
	

	
}
