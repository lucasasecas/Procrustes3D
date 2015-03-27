package com.example.Algorithms;

import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.util.Pair;
import org.ejml.simple.SimpleMatrix;

import com.calc3d.log.Logger;
import com.example.utils.CommonUtils;

public class Robusto extends  ProcrustesCalculator{

	@Override
	public ArrayList<SimpleMatrix> execute(ArrayList<SimpleMatrix> entitiesMat) {
		
		int numRows = entitiesMat.get(0).numRows();
		int numCols = entitiesMat.get(0).numCols();
		int numEntities = entitiesMat.size();
		
		SimpleMatrix res = MatrixConstructor.create(0, numRows, numEntities);
		SimpleMatrix resme = MatrixConstructor.create(0, 1, numEntities);
		SimpleMatrix mejora = MatrixConstructor.create(0, numRows + 1, numEntities);
		double restot = 0;
		
		SimpleMatrix auxres = MatrixConstructor.create(0, numRows, numEntities);
		SimpleMatrix auxresme = MatrixConstructor.create(0,  1, numEntities);
		
		SimpleMatrix Y = MatrixConstructor.create(0, numRows, numCols);
		SimpleMatrix W =  MatrixConstructor.create(0, numRows, numCols);
		ArrayList<SimpleMatrix> R = MatrixConstructor.create(0, numRows, numCols, numEntities);
		ArrayList<SimpleMatrix> X = MatrixConstructor.create(0, numRows, numCols, numEntities);
		ArrayList<SimpleMatrix> Aux = MatrixConstructor.create(0, numRows, numCols, numEntities);
		
		SimpleMatrix E = MatrixConstructor.create(0, numRows, numRows);
		SimpleMatrix A = MatrixConstructor.create(0, numRows, numRows);
		
		ArrayList<SimpleMatrix> H = MatrixConstructor.create(0, numCols, numCols, numEntities);
		SimpleMatrix p = MatrixConstructor.create(1, 1, numEntities);
		
		SimpleMatrix T = MatrixConstructor.create(0, numRows, numCols);
		
		X = entitiesMat;
		
		double s =0;
		
		for (int k=0; k<numEntities; k++){
			s = CommonUtils.medland(X.get(k), 2);
			s = s==0 ? 0.01 : s;
			X.set(k, X.get(k).divide(s));
			//X.get(k).print();
		}
		
		Y = CommonUtils.spatialmed(X);
		for(int k=0; k<numEntities; k++){
			for(int i=0; i<numRows; i++){
				SimpleMatrix resMatY = Y.extractVector(true,i);
				SimpleMatrix resMatX =X.get(k).extractVector(true, i); 
						
				res.set(i,k,
						resMatY
						.minus(resMatX)
						.normF()
						);
			}
			resme.set(0,k,
					CustomMatrixUtils.median(
							res.extractVector(false, k).getMatrix().getData()
							)
					);
			resme.set(0, k, res.extractVector(false, k).elementSum());
		}
		
		
		double residual = resme.elementSum();
		double residualant = 0;
		int z = 1;
		double tol = 0.0001;
		for(int i=0; i<X.size(); i++)
			Aux.set(i, X.get(i).copy());
		SimpleMatrix conteo = MatrixConstructor.create(0, numRows +1, numEntities);
		SimpleMatrix conteomed = MatrixConstructor.create(0, 1, numEntities);

		double lastProgressStatus = 0;
		int counter =0;
		while(z<=20 && CommonUtils.medland(Y.minus(W), 1) > tol){
			int total=20*numEntities;
    		for(int k=0; k<numEntities; k++){   
    			int b = (int) Math.pow(10, z-1);
    			
    			counter++;
    			double newProgressStatus =(counter*100/total);
    			System.out.println(newProgressStatus + "%");
    			if(lastProgressStatus != newProgressStatus)
    				this.setChanged();
    			lastProgressStatus = newProgressStatus;
    			notifyObservers(newProgressStatus);
    			
//    			Logger.getLogger("info").info(Math.floor(z*(k+1)*100/total) + " % \n");
    			if(k==24 && z==4){
    				int a =0;
    			}

    			E = CommonUtils.escala(Aux.get(k), Y);
    			double ro = CommonUtils.medianarep(E);
    			
    			Aux.set(k, Aux.get(k).scale(ro));
    			SimpleMatrix eje = MatrixConstructor.create(0, 1, 3);
    			double tita = 0;
    			Object[] solArray = CommonUtils.rot3D(Aux.get(k), Y);
    			tita = (double)solArray[1];
    			eje = (SimpleMatrix)solArray[0];
    			SimpleMatrix auxMatRot = CommonUtils.matrizRot3D(eje, tita);
    				H.set(k, auxMatRot);
    			//auxMatRot.print();
    			Aux.set(k, Aux.get(k)
    					.mult(H.get(k)));
    			SimpleMatrix prueba = Aux.get(k);
    			for(int i=0; i<prueba.numRows(); i++){
    				if(prueba.get(i, 2) != 0.0){
    					//System.out.println(prueba);
    				}
    			}
    			//System.out.println(Aux.get(k));
    			T = Y.minus(Aux.get(k));
    			SimpleMatrix t = MatrixConstructor.create(0, 1, numCols);
    			t = CustomMatrixUtils.median(T);
    			SimpleMatrix AuxWithOnes = MatrixConstructor.create(1, numRows, 1).mult(t);
    			Aux.get(k).set(Aux.get(k).plus(AuxWithOnes));
    			for(int i=0; i<numRows; i++){
//        
    				auxres.set(i, k, 
    						Y.extractVector(true, i)
    						.minus(Aux.get(k).extractVector(true, i))
    						.normF());
    				if(auxres.get(i, k) <= res.get(i, k))
    					conteo.set(i, k, 1);
    				else 
    					conteo.set(i, k, 0);
    			}

    			auxresme.set(0, k, CustomMatrixUtils.median(
    					auxres.extractVector(false, k).getMatrix().getData()
    					)
					);
    		}
    		
    		for(int k=0; k<numEntities; k++){
    			if (auxresme.get(0, k)<=resme.get(0, k)){
    				X.set(k, Aux.get(k));
    				conteomed.set(0, k, 1);
    			}
    			else
    				conteomed.set(0, k, 0);
    		}
    		SimpleMatrix auxConteoRow = MatrixConstructor.create(0, 1, numEntities);
    		conteo.insertIntoThis(numRows, 0, auxConteoRow);
//   		conteo(f+1,:)=100*sum(conteo)/f;
    		for(int e=0; e<conteo.numCols(); e++){
    			double contSum = conteo.extractVector(false, e).elementSum() ;
    			double auxConteoval =  contSum * 100 / numRows;
    			conteo.set(numRows,e, auxConteoval);
    		}
    		for (int k=0; k<numEntities; k++){ 
            	for(int i=0; i<numRows; i++)
            		res.set(i, k, Y.extractVector(true, i)
            				.minus(X.get(k).extractVector(true, i))
            				.normF());
            	resme.set(0, k, CustomMatrixUtils.median(
            			res.extractVector(false, k).getMatrix().data
            			)
        			);
    		}
    		W = Y.copy();
    		Y = CommonUtils.spatialmed(X);
    		z++;
		}
////
//		R = null;
////R(:,:,1:r)=X; % se devuelve, en las primeras r fetas, las superposiciones
		int a = 0;
//		for(int i=0; i<numEntities; )
////%R(:,:,r+1)=Y; % se guarda la consenso en el último lugar TODO
//disp(['Residuales finales']);
//disp(medland(Y-W,1)); 
//%disp(['Residualant: ',num2str(residualant),' Residual: ',num2str(residual)]);
//disp(['iteraciones: ',num2str(z)]);
//disp(['La matriz consenso robusta final es:']);
//disp(Y);
		////System.out.println("La matriz consenso final es: ");
		////System.out.println(Y);
//%disp(['Los individuos pos-ajuste robusto son:']);
//%disp(X);
//
//toc
		
		
		
		X.add(Y);
		return X;
	}

	public Pair<SimpleMatrix, SimpleMatrix> rob2D(SimpleMatrix x, SimpleMatrix y) {
		int f = x.numRows(),
			c = 2;
		
		SimpleMatrix H = MatrixConstructor.create(0, f, c);
		SimpleMatrix T = MatrixConstructor.create(0, f, c);
		SimpleMatrix t = MatrixConstructor.create(0, 1, c);
		
		SimpleMatrix E = CommonUtils.escala(x, y);
		double ro = CommonUtils.medianarep(E);
		
		x = x.scale(Math.sqrt(ro));
		y = y.scale(1/Math.sqrt(ro));
		E = CommonUtils.escala(x,y);
		ro = CommonUtils.medianarep(E);
		
		SimpleMatrix U = CommonUtils.anguloAfin(x,y);
		double a = CommonUtils.medianarep(U);
		H = CommonUtils.matrizRot(a);
		T = x.minus(y);
		t = CustomMatrixUtils.median(t);
		SimpleMatrix AuxWithOnes = MatrixConstructor.create(1, f, 1).mult(t);
		x.set(x.plus(AuxWithOnes));
		return new Pair<SimpleMatrix, SimpleMatrix>(x, y);
		
	}

	private SimpleMatrix matrizRot3D(SimpleMatrix eje, double tita) {
		//TODO
		return null;
	}



	


}
