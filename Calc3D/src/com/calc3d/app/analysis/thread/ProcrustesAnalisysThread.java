package com.calc3d.app.analysis.thread;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.ejml.simple.SimpleMatrix;

import com.calc3d.app.CopyOfGui;
import com.calc3d.app.analysis.ProcrustesCalculatorAdapter;
import com.calc3d.app.elements.simpleelements.ComposeSimpleElement;
import com.calc3d.app.elements.simpleelements.SampleSimpleElement;
import com.example.Algorithms.ProcrustesCalculator;

public class ProcrustesAnalisysThread extends Thread {

	
	private JFrame currentWindow;
	private ProcrustesCalculatorAdapter calculator;
	private ArrayList<SampleSimpleElement> elems;
	private int tabIndex;

	public ProcrustesAnalisysThread(JFrame window, ProcrustesCalculatorAdapter calculator, ArrayList<SampleSimpleElement> specimens, int indexTab){
		this.currentWindow = window;
		this.calculator = calculator;
		this.elems = specimens;
		this.tabIndex = indexTab;
	}
	
	@Override
	public void run(){
		ComposeSimpleElement result = calculator.calculate(elems);
		((CopyOfGui)currentWindow).addProcrustesAnalisys(result, calculator.getConfiguration(), this.tabIndex);
	}
}
