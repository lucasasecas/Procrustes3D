package com.example.Algorithms;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.ejml.simple.SimpleMatrix;

public abstract class ProcrustesCalculator extends Observable {

	public abstract ArrayList<SimpleMatrix> execute(ArrayList<SimpleMatrix> entitiesMat); 
}
