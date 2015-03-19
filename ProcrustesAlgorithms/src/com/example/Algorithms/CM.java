package com.example.Algorithms;

import java.util.ArrayList;

import org.ejml.ops.CommonOps;
import org.ejml.simple.SimpleMatrix;
import org.ejml.simple.SimpleSVD;
import org.omg.CORBA.portable.IndirectionException;

public class CM extends ProcrustesCalculator{ 

	

	public ArrayList<SimpleMatrix> proc2012cm(ArrayList<SimpleMatrix> entitiesMat){
		
		int numRows = entitiesMat.get(0).numRows();
		int numCols = entitiesMat.get(0).numCols();
		int numEntities = entitiesMat.size();

		ArrayList<SimpleMatrix> X = MatrixConstructor.create(0, numRows, numCols, numEntities);
		SimpleMatrix t = MatrixConstructor.create(0,numEntities, numCols);
		ArrayList<SimpleMatrix> J = MatrixConstructor.create(0,numCols, numCols, numEntities);
		SimpleMatrix ro = MatrixConstructor.create(1, 1, numEntities);
		SimpleMatrix n = MatrixConstructor.create(0, 1, numEntities);
		ArrayList<SimpleMatrix> C = MatrixConstructor.create(0, numCols, numCols, numEntities);
		ArrayList<SimpleMatrix> U = MatrixConstructor.create(0, numCols, numCols, numEntities);
		ArrayList<SimpleMatrix> V = MatrixConstructor.create(0, numCols, numCols, numEntities);
		ArrayList<SimpleMatrix> H = MatrixConstructor.create(0, numCols, numCols, numEntities);
		SimpleMatrix p = MatrixConstructor.create(1, 1, numEntities);
		SimpleMatrix T = MatrixConstructor.create(0, 1, numCols);
		SimpleMatrix Y = MatrixConstructor.create(0, numRows, numCols);
		ArrayList<SimpleMatrix> X0 = MatrixConstructor.create(0, numRows, numCols, numEntities);
		double SCD, SCE = 0;
		
		double sumatrazas = 0;
		for(int i = 0; i < entitiesMat.size(); i++){
//			X(:,:,k)=B(:,:,k);  % carga de datos
			X.get(i).set(entitiesMat.get(i));
//		    t(k,:)=ones(1,f)*X(:,:,k)/f;
			t.insertIntoThis(i, 0, 
					MatrixConstructor.create(1, 1, numRows)
					.mult(X.get(i).divide(numRows))
					.extractVector(true, 0)
					);
//		    T=T+t(k,:)/r;   % es la traslación promedio, necesaria en MANOVA (línea 36)
			T.set(T.plus(
					t.extractVector(true, i)
					.divide(numEntities)
					));
//		    X(:,:,k)=centrar(X(:,:,k));
			X.get(i).set(centrar(X.get(i)));
//		    X0(:,:,k)=X(:,:,k);
			X0.get(i).set(X.get(i));

//		    sumatrazas=sumatrazas+trace(X(:,:,k)'*X(:,:,k));
			sumatrazas += X.get(i).transpose()
					.mult(X.get(i))
					.trace();
		}
		for(int i=0; i<numEntities; i++){
			SCE += numRows * Math.pow(T.minus(t.extractVector(true, i))
					.normF(), 2); 
		}
		SCD = sumatrazas;
		
		/**
		 * O(n)
		 */
		for(int i = 0; i<numEntities; i++){
//			X(:,:,k)=X(:,:,k)/sqrt(sumatrazas/r);  
			X.get(i).set(X.get(i).divide(Math.sqrt(sumatrazas/numEntities)));
//			Y=Y+X(:,:,k)/r; % calculo la config. promedio inicial
			Y.set(Y.plus(
					X.get(i).divide(numEntities)
					));
//			J(:,:,k)=eye(c);% inicializo el "almacén rotación"
			J.get(i).set((new SimpleMatrix(CommonOps.identity(numCols))));
//		      p(1,k)=1;
			p.set(0,i,1);
//		      ro(1,k)=1;%qrt(sumatrazas/r);
			ro.set(0, i, 1);
		}
//		residual=r*(1-trace(Y*Y'));
		double residual = numEntities * (1 - 
				Y.mult(Y.transpose()).trace());
		double residualLat = 0;
		System.out.println("SCE: "+ SCE);
		System.out.println("Residual inicial: " + residual);
		int z = 0;
		while(Math.abs(residual-residualLat)>0.0001 && z<=100){
			SimpleMatrix auxX;
			System.out.println("Iteracion nro: " + z);
			residualLat = residual;
			residual = 0;
			sumatrazas = 0;
			double N2 = 0;
//			W=zeros(f,c); % matriz aux. para calcular el promedio
			SimpleMatrix W = MatrixConstructor.create(0, numRows, numCols);
			for(int i=0; i<numEntities; i++){
//				C(:,:,k)=Y'*X(:,:,k);    % la matriz a descomponer en SVD
				C.get(i).set(
						Y.transpose().mult(X.get(i))
						);
//				[U(:,:,k),S(:,:,k),V(:,:,k)]=svd(C(:,:,k));   % hallo la SVD de la matriz Y'*Ak}
				SimpleSVD svd = C.get(i).svd(false);
//				H(:,:,k)=V(:,:,k)*U(:,:,k)';
				H.get(i).set(
						svd.getV()
						.mult(svd.getU().transpose())
						);
//				J(:,:,k)=J(:,:,k)*H(:,:,k);                   % actualizo el "almacén rot"
				J.get(i).set(J.get(i).mult(H.get(i)));
//				X(:,:,k)=X(:,:,k)*H(:,:,k);   % se rotan las matrices
				auxX = X.get(i);
				auxX.set(X.get(i).mult(H.get(i)));
//				n(1,k)=trace(X(:,:,k)'*X(:,:,k));   % calculo la norma (al cuadrado) de Ak
				n.set(0,i, X.get(i).transpose().mult(X.get(i)).trace() );
//				sumatrazas=sumatrazas+n(1,k);                 % contendrá la suma de trazas
				sumatrazas += n.get(0,i);
//				W=W+X(:,:,k)/r;
				W.set(W.plus(X.get(i).divide(numEntities)));
			}
			
//			Y=W; % actualizo el promedio
//			W=zeros(f,c);
//			residualant=residual;
//			residual=r*(1-trace(Y*Y'));
//			disp(['Residual luego de la rotación: ',num2str(residual)]);
			Y.set(W);
			W = MatrixConstructor.create(0, numRows, numCols);
			residualLat = residual;
			residual = numEntities * (1 - 
						Y.mult(Y.transpose()).trace());
			System.out.println("residual luego de la rotacion: " + residual);
			for(int i=0; i<numEntities; i++){
//				p(1,k)=sqrt(p(1,k)*trace(Y'*X(:,:,k))/(trace(X(:,:,k)*X(:,:,k)')*trace(Y'*Y)));      % es el factor de escala        
				double factEscala = (double) Math.sqrt(
						p.get(0,i) * // p(1,k)  * X(:,:,k)') * trace(Y'*Y))
						Y.transpose().mult(X.get(i)).trace() / //trace(Y'*X(:,:,k)) 
						(X.get(i).mult(X.get(i).transpose()).trace() * // trace(X(:,:,k) * X(:,:,k)')
						Y.transpose().mult(Y).trace()) //trace(Y'*Y))
						);
				p.set(0, i, factEscala);
				System.out.println("ro"+i+":"+p.get(0, i));
//			      ro(1,k)=ro(1,k)*p(1,k);          % actualizo el "almacén escala"
				ro.set(0,i, ro.get(0,i) * p.get(0,i));
//				N2=N2+n(1,k)*p(1,k)^2;       % N2 contendrá la suma de las trazas por los ro2
				N2 += (double)n.get(0, i) * (double)Math.pow(p.get(0, i), 2);
//				X(:,:,k)=p(1,k)*X(:,:,k);              % actualizo la config.
				X.get(i).set(X.get(i).scale(p.get(0, i)));
//				W=W+X(:,:,k)/r
				W.set(W.plus(X.get(i).divide(numEntities)));
			}
			Y.set(W);
			System.out.println("Valor de Y: " + Y.toString());
			residualLat =  residual;
			residual = numEntities * (1 - 
					Y.mult(Y.transpose()).trace());
			System.out.println("Suma de trazas: "+ sumatrazas);
			System.out.println("Suma de trazas multiplicadas por los ro2: " + N2);    
			System.out.println("Restricción: " + sumatrazas + " = " + N2);   
			System.out.println("Residual luego dilatación: " + residual);
			System.out.println("");
			z++;
		}
		System.out.println("");
		System.out.println("Se logró el consenso máximo");
		System.out.println("El Residual es: " + residual);
		System.out.println("Residual anterior " + residualLat);
		System.out.println("La Matriz consenso final es: " + Y.toString());
		
//		I1=eye(f,f); %matr. ident. para individuos
//		I2=eye(r,r); %matriz ident. para grupos
//		consin=zeros(1,f);
//		resin=zeros(1,f);
//		totin=zeros(1,f);
//		totgru=zeros(1,r);
//		resgru=zeros(1,r);
		SimpleMatrix I1 = new SimpleMatrix(CommonOps.identity(numRows, numRows));
		SimpleMatrix I2 = new SimpleMatrix(CommonOps.identity(numEntities, numEntities));
		SimpleMatrix consin = MatrixConstructor.create(0, 1, numRows);
		SimpleMatrix resin = MatrixConstructor.create(0, 1, numRows);
		SimpleMatrix totin = MatrixConstructor.create(0, 1, numRows);
		SimpleMatrix totgru = MatrixConstructor.create(0, 1, numEntities);
		SimpleMatrix resgru = MatrixConstructor.create(0, 1, numEntities);
		
		for(int i=0; i<numRows; i++){
//			consin(1,i)=r*I1(i,:)*Y*Y'*I1(:,i);
			consin.set(0, i, 
					I1.extractVector(true, i)
					.mult(Y)
					.mult(Y.transpose())
					.mult(I1.extractVector(false, i))
					.get(0) * numEntities
					);
			for(int j=0; j<numEntities; j++){
//				L=X(:,:,k)-Y;
				SimpleMatrix L = X.get(j).minus(Y);
//				totin(1,i)=totin(1,i)+I1(i,:)*X(:,:,k)*X(:,:,k)'*I1(:,i);
				totin.set(0, i, totin.get(0, i) +
						I1.extractVector(true, i)
						.mult(X.get(j))
						.mult(X.get(j).transpose())
						.mult(I1.extractVector(false, i))
						.get(0)
						);
//				resin(1,i)=resin(1,i)+I1(i,:)*L*L'*I1(:,i);
				resin.set(0, i, resin.get(0, i) + 
							I1.extractVector(true, i)
							.mult(L)
							.mult(L.transpose())
							.mult(I1.extractVector(false, i))
							.get(0)
						);
			}
		}
		for(int i=0; i<numEntities; i++){
			totgru.set(0, i, Math.pow(p.get(0, i), 2) * n.get(0, i));
			SimpleMatrix D = X.get(i).minus(Y);
			for(int j=0; j<numRows; j++){
				resgru.set(0, i,
						resgru.get(0, i) + 
						I1.extractVector(true, j)
						.mult(D)
						.mult(D.transpose())
						.mult(I1.extractVector(false, j)).get(0)
						);
				
			}
		}
		SimpleMatrix Indi = MatrixConstructor.create(0, numRows + 1, 4);
		double consindtot = 0;
		double resindtot = 0;
		double indtot = 0;
		for(int i=0; i<numRows; i++){
			Indi.set(i, 0, i);
			Indi.set(i, 1, consin.get(0, i));
			Indi.set(i, 2, resin.get(0, i));
			Indi.set(i, 3, totin.get(0, i));
			consindtot += consin.get(0, i);
			resindtot += resin.get(0, i);
			indtot +=  totin.get(0, i);
		}
		Indi.set(numRows, 1, consindtot);
		Indi.set(numRows, 2, resindtot);
		Indi.set(numRows, 3, indtot);
		
		SimpleMatrix Gru = MatrixConstructor.create(0, numEntities + 1, 3);
		double resgrutot = 0;
		double grutot = 0;
		for(int i=0; i<numEntities; i++){
			Gru.set(i, 0, i);
			Gru.set(i, 1, resgru.get(0, i));
			Gru.set(i, 2, totgru.get(0, i));
			resgrutot += resgru.get(0, i);
			grutot += totgru.get(0, i);
		}
		Gru.set(numEntities, 1, resgrutot);
		Gru.set(numEntities, 2, grutot);
		
		System.out.println("");
		System.out.println("MANOVA");
		System.out.println("");
		System.out.println("SCEntre: " + SCE);
		System.out.println("SCDEntro: " + SCD);
		System.out.println("_________________________________________________________");
		System.out.println("SCDentro - Por individuo");
		System.out.println("_________________________________________________________");
		System.out.println(" Individuo    Cons      Resi      Total");
		System.out.println("_________________________________________________________");
		this.showMatrix(Indi);
		System.out.println(" ");
		System.out.println("SCDentro - Por grupo");
		System.out.println("_________________________________________________________");
		System.out.println("     Grupo	Residual	Total");
		System.out.println("_________________________________________________________");
		this.showMatrix(Gru);
		
		SimpleSVD svd2 = Y.svd();
		SimpleMatrix Id = new SimpleMatrix(CommonOps.identity(svd2.getV().numRows()));
		SimpleMatrix e1 = Id.extractVector(false, 0);
		SimpleMatrix e2 = Id.extractVector(false, 1);
		
		SimpleMatrix a = svd2.getV();
		SimpleMatrix z1 = svd2.getV().mult(e1);
		SimpleMatrix z2 = svd2.getV().mult(e2);
		double[][] zData = new double[z1.numRows()][2];
		for(int i=0; i<z1.numRows(); i++){
			zData[i][0] = z1.get(i, 0);
			zData[i][1] = z2.get(i, 0);
		}
		SimpleMatrix Z = new SimpleMatrix(zData);
		ArrayList<SimpleMatrix> XX = MatrixConstructor.create(0, numRows, 2, numEntities);
		for(int i=0; i<numEntities; i++){
			XX.get(i).set(
					X.get(i).mult(Z)
					);
		}
		
		SimpleSVD svd3 = Y.transpose().mult(Y).svd();
		System.out.println("las dos primeras componentes recuperan: ");
		double a1 = svd3.getW().get(0,0);
		double a2 = svd3.getW().get(1,1);
		double a3 = svd3.getW().trace();
		double res = (a1 + a2) * 100 / a3;
		System.out.println((svd3.getW().get(0,0)  
				+ svd3.getW().get(1,1))
				* 100 / svd3.getW().trace());
		
		ArrayList<SimpleMatrix> result = X;
		result.add(Y);
		return result;
	}

	private SimpleMatrix centrar(SimpleMatrix simpleMatrix) {
		int f = simpleMatrix.numRows();
		int c = simpleMatrix.numCols();
		SimpleMatrix u = MatrixConstructor.create(1, f, 1);
		
		SimpleMatrix aux = new SimpleMatrix(CommonOps.identity(f));
		SimpleMatrix ut = u.transpose();
		SimpleMatrix uxut = u.mult(ut);
		double r = 1.0/ f;
		SimpleMatrix scaled = uxut.scale(r); 
		SimpleMatrix H = aux.minus( u.mult(u.transpose()).scale(r) );
		SimpleMatrix Y = MatrixConstructor.create(0, f, c);
		
		Y.set(H.mult(simpleMatrix));
		
		
		return Y;
		
	}
	
	
	public void showMatrix(SimpleMatrix m){
		for(int i=0; i<m.numRows(); i++){
			for(int j=0; j<m.numCols(); j++){
				System.out.print(m.get(i, j));
				System.out.print("    ");
			}
			System.out.print('\n');
		}
	}

	@Override
	public ArrayList<SimpleMatrix> execute(ArrayList<SimpleMatrix> entitiesMat) {
		return this.proc2012cm(entitiesMat);
	}
	
	
	
	
}
