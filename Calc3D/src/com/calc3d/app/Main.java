package com.calc3d.app;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.ejml.simple.SimpleMatrix;

public class Main {

	public static void main(String[] args) {
	
		double[][] mat  = new double[][]{
			{0, 0, 0, 1},
			{0, 1, 0, 0},
			{1, 0, 0, 0},
			{0, 0, 1, 0}
			
		};
		Array2DRowRealMatrix matirx = new Array2DRowRealMatrix(mat);
		EigenDecomposition eigen = new EigenDecomposition(matirx);
		
		double[] imag =eigen.getImagEigenvalues();
		double[] real =eigen.getRealEigenvalues();
		
		RealMatrix D = eigen.getV();
		double[][] a = D.getData();
		int b =0;

	}

}
