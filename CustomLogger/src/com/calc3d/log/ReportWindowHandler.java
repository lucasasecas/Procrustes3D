package com.calc3d.log;

import javax.swing.JTextArea;


public class ReportWindowHandler {
	
	private JTextArea textArea;
	
	public ReportWindowHandler( JTextArea textArea){
		this.textArea = textArea;
	}
	
	public ReportWindowHandler(){
		this.textArea = new JTextArea();
	}
	
	public int addMessage(String message){
		int lastLine = textArea.getLineCount();
		textArea.append(message);
		return textArea.getCaretPosition();
		
	}
	
	public int setMessage(String message, int posStart, int posEnd){
		textArea.replaceRange(message+'\n', posStart, posEnd);
		return textArea.getCaretPosition();
	}

}
