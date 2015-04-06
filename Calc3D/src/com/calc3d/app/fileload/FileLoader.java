package com.calc3d.app.fileload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.calc3d.app.analysis.DialogConfiguration;

public abstract class FileLoader {

	private static String lastDirectory;
	private  JFileChooser fileChoser;
	
	
	public static String getFileName(boolean save,String ext,String title, JPanel window) {
	    final JFileChooser fileChooser=new JFileChooser();
	    fileChooser.setDialogTitle(title);
	    fileChooser.setCurrentDirectory(new File(lastDirectory==null?System.getProperty("user.dir"):lastDirectory));
	    int result;
	    if (save) result=fileChooser.showSaveDialog(window);
	    else result=fileChooser.showOpenDialog(window);
	    if (result==JFileChooser.APPROVE_OPTION) {
	        String fileName=fileChooser.getSelectedFile().getPath();
	        lastDirectory=fileName;
	        // it also works if the next line is commented out!
	        lastDirectory=lastDirectory.substring(0,lastDirectory.lastIndexOf(File.separatorChar));
	        if (save && fileName.indexOf('.')==-1) fileName+=ext;
	        if (save && (new File(fileName)).exists())
	            if (JOptionPane.showConfirmDialog(window,"File "+fileName+" already exists. Overwrite?","Warning",
	                JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE)==JOptionPane.NO_OPTION)
	                return null;
	        return fileName;
	    }
	    else return null;
	}
	
	 
	public abstract Object load(String path);
}
