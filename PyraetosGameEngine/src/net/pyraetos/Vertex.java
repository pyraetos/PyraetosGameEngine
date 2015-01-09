package net.pyraetos;

import net.pyraetos.util.Vector;

public class Vertex{

	public static final int SIZE = 3;
	private Vector<Float> point;
	
	public Vertex(Vector<Float> point){
		this.point = point;
	}
	
	public Vertex(float x, float y, float z){
		this(new Vector<Float>(x, y, z));
	}
	
	public Vector<Float> getVector(){
		return point;
	}
	
	public float getX(){
		return point.getX();
	}
	
	public float getY(){
		return point.getY();
	}
	
	public float getZ(){
		return point.getZ();
	}
	
	@Override
	public String toString(){
		return point.toString();
	}
}
