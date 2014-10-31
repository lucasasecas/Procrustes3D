package com.example.utils;

import java.util.ArrayList;

import org.apache.commons.math3.exception.ZeroException;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealMatrixFormat;
import org.ejml.data.Complex64F;
import org.ejml.ops.NormOps;
import org.ejml.ops.SingularOps;
import org.ejml.simple.SimpleEVD;
import org.ejml.simple.SimpleMatrix;

import com.example.Algorithms.CustomMatrixUtils;
import com.example.Algorithms.MatrixConstructor;

public class CommonUtils {
	
	public static double medland(SimpleMatrix matrix, int type){
		
		//System.out.println("medland: ");
//		matrix.print();
		int f = matrix.numRows();
		SimpleMatrix b = MatrixConstructor.create(0, f*(f-1)/2, 1);
		SimpleMatrix aux = MatrixConstructor.create(0, f, 1);
		int k=0;
		for(int i=0; i<f; i++){
			aux.set(i, 0, matrix.extractVector(true, i).normF());
			for(int j=i+1; j<f; j++){
				b.set(k, 0, 
						matrix.extractVector(true, i)
						.minus(matrix.extractVector(true, j))
						.normF()
						);
				k++;
			}
		}
		switch(type){
		case 1:
//			System.out.println("Retorna: ");
//			System.out.println(CustomMatrixUtils.median(aux.getMatrix().getData()));
			return CustomMatrixUtils.median(aux.getMatrix().getData());
		case 2:
//			System.out.println("Retorna: ");
//			System.out.println(CustomMatrixUtils.median(b.getMatrix().getData()));
			return CustomMatrixUtils.median(b.getMatrix().getData());
		default:
//			System.out.println("Retorna: ");
//			System.out.println(aux.elementSum());
			return aux.elementSum();
		}
	}

	public static SimpleMatrix spatialmed(ArrayList<SimpleMatrix> m) {
//		System.out.println("entra spatialmed: ");
//		System.out.println("m: " + m);
		int n = m.get(0).numRows();
		int p = m.get(0).numCols();
		int r = m.size();
		
		SimpleMatrix A = MatrixConstructor.create(0, r, p);
		SimpleMatrix M = MatrixConstructor.create(0, n, p);
		
		SimpleMatrix tdemu = MatrixConstructor.create(0, 1, p);
		SimpleMatrix rdemu = MatrixConstructor.create(0, 1, p);
		double gamagama = 0;
		double sensor = 0;
		
		SimpleMatrix s = MatrixConstructor.create(1, 1, r);
		SimpleMatrix aux = MatrixConstructor.create(0, 1, p);
		SimpleMatrix auxant = MatrixConstructor.create(0, 1, p);
		
		for(int i=0; i<n; i++){
			for(int k=0; k<r; k++){
				A.insertIntoThis(k, 0, 
						m.get(k)
						.extractVector(true, i));
			}
			aux = new SimpleMatrix(CustomMatrixUtils.Mean(A));
			int h =1;
			while (aux.minus(auxant).normF() > 0.000000001 && (h<=1000)){
				for(int k=0; k<r; k++){
					if(NormOps.normP2(aux.minus(A.extractVector(true, k)).getMatrix()) != 0 ){
						s.set(0, k, 1/NormOps.normP2(
								aux.minus(
										A.extractVector(true, k)).getMatrix()));
					}
					else{
						sensor = 1;
						s.set(0, k, 0);
					}
					
				}
				auxant = aux;
				tdemu = s.mult(A).divide(s.elementSum());
				rdemu = s.mult(A);
				double divAux = 0.1;
				try{
					divAux = sensor/rdemu.normF();
				}catch(ArithmeticException e){
					divAux = 0.01;
				}
				gamagama = Math.min(1, sensor/rdemu.normF());
				aux = tdemu.scale(1-gamagama)
						.plus(aux.scale(gamagama));
				h++;
			}
			M.insertIntoThis(i, 0, aux);
			A = MatrixConstructor.create(0, r, p);
			tdemu = MatrixConstructor.create(0, 1, p);
			rdemu = MatrixConstructor.create(0, 1, p);
			gamagama = 0;
			sensor = 0;
			s = MatrixConstructor.create(1, 1, r);
			aux = MatrixConstructor.create(0, 1, p);
			auxant = MatrixConstructor.create(0, 1, p);
		}
//		System.out.println("Retorna "+ M);
		return M;
	}

	public static double medianarep(SimpleMatrix A) {
	//	System.out.println("entra medianarep: ");
	//	System.out.println(A);
		SimpleMatrix ASIND = CustomMatrixUtils.sindiagonal(A);
		int f = A.numRows();
		SimpleMatrix aux = MatrixConstructor.create(0, f, 1);
		for(int i=0; i<f; i++){
			aux.set(i, 0, CustomMatrixUtils.median(ASIND.extractVector(true, i).getMatrix().getData()));
		}
//		System.out.println("retorna "+ CustomMatrixUtils.median(aux.getMatrix().getData()));
		return CustomMatrixUtils.median(aux.getMatrix().getData());
	}

	public static SimpleMatrix escala(SimpleMatrix X, SimpleMatrix Y) {
//		System.out.println("entra a escala: ");
//		System.out.println("X = " + X);
//		System.out.println("Y = "+ Y);
		int f = X.numRows();
		SimpleMatrix P = MatrixConstructor.create(1, f, f);
		for(int i=0; i<f; i++){
			for(int j=i+1; j<f; j++){
				P.set(i, j, 
						Y.extractVector(true, i).minus(
								Y.extractVector(true, j)).normF()
							/ X.extractVector(true, i).minus(
									X.extractVector(true, j)).normF());
				P.set(j, i, P.get(i, j));
			}
			
		}
//		System.out.println("Retorna " + P);
		return P;
	}

	public static SimpleMatrix estimorot3D(SimpleMatrix x, SimpleMatrix y,
			int i, int j) {
//		System.out.println("entra a estimorot3D");
//		System.out.println("x = " + x);
//		System.out.println("y = " + y);
//		System.out.println("i = " + i);
//		System.out.println("j = "  + j);
		int dim = x.numCols();
		SimpleMatrix A = MatrixConstructor.create(0, dim, dim);
		SimpleMatrix B = MatrixConstructor.create(0, dim, dim);
		SimpleMatrix v = MatrixConstructor.create(0, 1, dim);
		SimpleMatrix w = MatrixConstructor.create(0, 1, dim);
		SimpleMatrix a3 = MatrixConstructor.create(0, 1, dim);
		SimpleMatrix b3 = MatrixConstructor.create(0, 1, dim);
		SimpleMatrix R;
		if(i==j){
//			System.out.println("retorna " + SimpleMatrix.identity(3));
			return SimpleMatrix.identity(3);
		}
		
		A.insertIntoThis(0, 0, 
				x.extractVector(true, j).minus(x.extractVector(true, i)).divide(
						x.extractVector(true, j).minus(x.extractVector(true, i)).normF()));
		B.insertIntoThis(0, 0, 
				y.extractVector(true, j).minus(y.extractVector(true, i)).divide(
						y.extractVector(true, j).minus(y.extractVector(true, i)).normF()));
		double[][] resultCrossproduct1 = new double[1][3];
		double[][] resultCrossproduct2 = new double[1][3];
		resultCrossproduct1[0] = CustomMatrixUtils.crossProduct(
				A.extractVector(true, 0).getMatrix().getData()
				, x.extractVector(true, j).getMatrix().getData());
		resultCrossproduct2[0] = CustomMatrixUtils.crossProduct(
				B.extractVector(true, 0).getMatrix().getData()
				,y.extractVector(true, j).getMatrix().getData());
		v = new SimpleMatrix(resultCrossproduct1);
		w = new SimpleMatrix(resultCrossproduct2);
		double normav = v.normF();
		double normaw = w.normF();
		if(normav != 0 && normaw != 0){
			A.insertIntoThis(1, 0, v.divide(normav));
			B.insertIntoThis(1, 0, w.divide(normaw));
			resultCrossproduct1[0] = CustomMatrixUtils.crossProduct(
					A.extractVector(true, 0).getMatrix().getData()
					, A.extractVector(true, 1).getMatrix().getData());
			a3 =  new SimpleMatrix(resultCrossproduct1);
			A.insertIntoThis(2, 0, a3.divide(a3.normF()));
			resultCrossproduct1[0] = CustomMatrixUtils.crossProduct(
					B.extractVector(true, 0).getMatrix().getData()
					, B.extractVector(true, 1).getMatrix().getData());
			b3 =  new SimpleMatrix(resultCrossproduct1);
			B.insertIntoThis(2, 0, b3.divide(b3.normF()));
			R = A.transpose().mult(B);
			
		}
		else if(normav == 0 && normaw == 0){
			resultCrossproduct1[0] = CustomMatrixUtils.crossProduct(
					A.extractVector(true, 0).getMatrix().getData()
					, B.extractVector(true, 0).getMatrix().getData());
			v = new SimpleMatrix(resultCrossproduct1);
			w = v;
			normav = v.normF();
			if(normav == 0){
				R = SimpleMatrix.identity(3);
			}
			else{
				A.insertIntoThis(1, 0, v.divide(normav));
				B.insertIntoThis(1, 0, A.extractVector(true, 1));
				resultCrossproduct1[0] = CustomMatrixUtils.crossProduct(
						A.extractVector(true, 0).getMatrix().getData()
						, A.extractVector(true, 1).getMatrix().getData());
				a3 = new SimpleMatrix(resultCrossproduct1);
				A.insertIntoThis(2, 0, a3.divide(a3.normF()));
				
				resultCrossproduct1[0] = CustomMatrixUtils.crossProduct(
						B.extractVector(true, 0).getMatrix().getData()
						, B.extractVector(true, 1).getMatrix().getData());
				b3 = new SimpleMatrix(resultCrossproduct1);
				B.insertIntoThis(2, 0, b3.divide(b3.normF()));
				R = A.transpose().mult(B);
			}
			
		}
		else
			R = SimpleMatrix.identity(3);
//		System.out.println("Retorna " + R);
		return R;
	}

	public static SimpleMatrix ejerot(SimpleMatrix r) {
		SimpleMatrix aux = MatrixConstructor.create(0, 1, 3);
		int rows = r.numRows(),
				cols = r.numCols();
		double[][] mat = new double[rows][cols];
		for(int i=0; i<rows; i++){
			for(int j=0; j<cols; j++){
				mat[i][j] = r.get(i,j);
			}
		}
		Array2DRowRealMatrix matrix = new Array2DRowRealMatrix (mat);
		EigenDecomposition eigen = new EigenDecomposition(matrix);

		try{
			double[] eigenReals = eigen.getRealEigenvalues();
			for(int i=0; i<eigenReals.length; i++){
				if(eigen.getImagEigenvalue(i)==0 && eigenReals[i]==1 && eigenReals[i]>0.5){
					double[] vals = eigen.getV().getColumn(i);
					aux.setRow(0, 0, vals[0], vals[1], vals[2]);
					break;
				
				}
			}
			

			if(aux.get(0, 2)<0){
				
				aux = aux.scale(-1);
			}
	//		System.out.println(aux);
		}catch(Exception e){
			System.err.println(e);
			System.err.println(r);
        }
		return aux;
	}

	public static double angurot(SimpleMatrix r) {
//		System.out.println(r);
		
		double vAux = (r.trace()-1)/2;
		if(Math.abs(vAux) >1)
			vAux = (int)vAux;
		double v = Math.acos(vAux);
		double PIMinus =(Math.PI * (-1));
		if(v < PIMinus){
			v += 2 * Math.PI; 
		}
		else if(v>Math.PI)
			v -= 2*Math.PI;
//		System.out.println(v);
		return v;
	}

	public static SimpleMatrix matrizRot3D(SimpleMatrix eje, double tita) {
	//	System.out.println(eje);
	//	System.out.println(tita);
		SimpleMatrix AF = MatrixConstructor.create(0, 3, 3);
		SimpleMatrix Bloque = MatrixConstructor.create(0, 3, 3);
		SimpleMatrix R = MatrixConstructor.create(0, 3, 3);
		
		Bloque.set(2,2,1);
		Bloque.insertIntoThis(0, 0, matrizRot(tita));
		SimpleMatrix V = CustomMatrixUtils.nullSpace(eje);
		AF.insertIntoThis(2, 0, eje);
		SimpleMatrix transpose = V.transpose();
		SimpleMatrix subMatrix = V.transpose().extractMatrix(0, 2, 0, 2);
		AF.insertIntoThis(0, 0, V.transpose());
//	System.out.println(AF.transpose().mult(Bloque).mult(AF));
		R = AF.transpose().mult(Bloque).mult(AF);
		return R;
	}

	public static SimpleMatrix matrizRot(double tita) {
//		System.out.println(tita);
		double[][] H = new double[][]
				{{Math.cos(tita), Math.sin(tita)},{-Math.sin(tita), Math.cos(tita)}};
//		System.out.println(H);
		return new SimpleMatrix(H);
	}
	
	public static Object[] rot3D(SimpleMatrix x, SimpleMatrix y) {
//		System.out.println(x);
//		System.out.println(y);
		SimpleMatrix eje = MatrixConstructor.create(0, 1, 3);
//		eje=zeros(1,3); % el eje de la rotación será un vector fila 
//		tita=0;
		double tita = 0;
//		f=size(X,1);  % # landmarks
		int f = x.numRows();
//		c=size(X,2);  % dim del espacio
		int c = x.numCols();
//		% n=f*(f-1)/2;  % # de combnaciones de landmarks tomados de a 2
//
//		R=zeros(c,c); % almacén de la rotación
		SimpleMatrix R = MatrixConstructor.create(0, c, c); 
//		E=zeros(f,c,f-1); % se guardarán los ejes de cada rot. La elección de las dimensiones es para visualizarlas, el último ind es j
		ArrayList<SimpleMatrix> E = MatrixConstructor.create(0,f-1, c, f);
//		ejes=zeros(f,c);  % se guardarán las medianas por col
		SimpleMatrix ejeaux = MatrixConstructor.create(0, 1, f);
		
		SimpleMatrix ejes = MatrixConstructor.create(0, f, c);
//		ejfinal=zeros(1,c);
		SimpleMatrix ejfinal = MatrixConstructor.create(0, 1, c);
//		Teta=zeros(f,f); % se guardarán los ángulos de cada rotación (ojo con la orientación)
		SimpleMatrix Teta = MatrixConstructor.create(0, f, f);
//
//
//		for i=1:f
		for(int i=0; i<f; i++){
//		    for j=1:f-1
			for(int j=0; j<f;j++){
//		        if i<=j
				if(i<j){

					R = CommonUtils.estimorot3D(x, y, i, j);
					E.get(i).insertIntoThis(j-1, 0, ejerot(R));
					Teta.set(i, j,angurot(R));
				}
//		        else
				else if(i>j){

					R = CommonUtils.estimorot3D(x, y, i, j);
					E.get(i).insertIntoThis(j, 0, CommonUtils.ejerot(R));
					Teta.set(i, j,CommonUtils.angurot(R));
				}
			}
		}

		SimpleMatrix v = MatrixConstructor.create(0, 1, 3);
//		for i=1:f
		for(int i=0; i<f-1; i++){
//		   for k=1:3     
			for(int k=0; k<3; k++){
//		       v(1,k)=median(E(i,k,:)); % en cada fila de "ejes" está el eje mediano respecto a j
				double[] auxVals = new double[E.size()];
				System.out.println(i);
				for(int t=0; t<E.size(); t++)
					auxVals[t] = E.get(t).get(i, k);
				v.set(0, k, 
						CustomMatrixUtils.median(auxVals));
//		   end;
			}
			int h=0;
			while(v.normF()<0.001 && h<f-1){
				if(E.get(i).get(h,0)<0){
					E.get(i).insertIntoThis(h, 0,
							E.get(i).extractVector(true, h).scale(-1));
				}
				h++;
				for(int k=0; k<3; k++){
					double[] auxVals = new double[E.size()];
					for(int t=0; t<E.size(); t++)
						auxVals[t] = E.get(t).get(i, k);
					v.set(0, k, 
							CustomMatrixUtils.median(auxVals));
				}
			}
		
//		ejes(i,:)00=v/norm(v,'fro');

			ejes.insertIntoThis(i, 0, v.divide( v.normF() == 0 ? 1 : v.normF()  ));
//			for(int t=0; t<ejes.numCols(); t++)
//				ejes.set(t);
			v = MatrixConstructor.create(0, 1, 3);
//		%disp(['el eje mediano de las rotaciones partiendo del landmark ',num2str(i),' es:']);
//		%disp(ejes(i,:));
//		v=zeros(1,3);
			
//		end;
			}
//
//		w=zeros(1,3); % vector auxiliar
		SimpleMatrix w = MatrixConstructor.create(0, 1, 3);
//		for k=1:3
		for(int k=0; k<3; k++)
//			w(1,k)=median(ejes(:,k)); % en cada fila de "ejes" está el eje mediano respecto a j
			w.set(0, k, 
					CustomMatrixUtils.median(ejes.extractVector(false, k).getMatrix().getData()
							)
					);
//		        
//		end;
//		w=w/norm(w,'fro');
		double auxNorm = w.normF(); 
		w=auxNorm==0? w :w.divide(auxNorm);

		return new Object[]{w, CommonUtils.medianarep(Teta)};
	}

	public static SimpleMatrix spatialMedRaw(SimpleMatrix m) {
		int n = m.numRows();
		int p = m.numCols();
		SimpleMatrix A = m.copy();
		SimpleMatrix w = MatrixConstructor.create(1, 1, n);
		SimpleMatrix s = MatrixConstructor.create(0, 1, n);
		SimpleMatrix aux = CustomMatrixUtils.Mean(A);
		SimpleMatrix auxAnt = MatrixConstructor.create(0, 1, p);
		
		int h=1;
		
//		aux = new SimpleMatrix(CustomMatrixUtils.Mean(A));
//		int h =1;
		while ((aux.minus(auxAnt).normF()) > 0.000000001 && (h<=1000)){
			for(int i=0; i<n; i++){
				s.set(i, w.get(i) / 
						NormOps.normP2(aux.minus(A.extractVector(true, i)).getMatrix()));
			}
			auxAnt = aux.copy();
			//TODO revisar con Sebastian
			aux = s.mult(A).divide(s.elementSum());
			h++;
		}
		

		return aux;
	}

	public static SimpleMatrix anguloAfin(SimpleMatrix x, SimpleMatrix y) {
		int r = x.numRows(),
			c = x.numCols();
		
		SimpleMatrix T = MatrixConstructor.create(0, r, r);
		SimpleMatrix oy = MatrixConstructor.create(0, 1, c);
		SimpleMatrix ox = MatrixConstructor.create(0, 1, c);
		double ax=0,
			   rx=0,
			   ry=0,
			   ay=0;
		for(int i=0; i<r; i++){
			for(int j=i+1; j<r; j++){
				oy = y.extractVector(true, j).minus(y.extractVector(true, i));
				ox = x.extractVector(true, j).minus(x.extractVector(true, i));
				ay = Math.atan2(oy.get(0,0), oy.get(0,1));
				ax = Math.atan2(ox.get(0,0), ox.get(0,1));
				ry = Math.hypot(oy.get(0,0), oy.get(0,1));
				rx = Math.hypot(ox.get(0,0), ox.get(0,1));
				double t = ay-ax;
				if(t>Math.PI)
					t=t-2*Math.PI;
				else if(t<=-Math.PI)
					t=t+2*Math.PI;
				T.set(i,j, t);
				T.set(j,i,t);
			}
		}
		
		return T;
	}

			


}
