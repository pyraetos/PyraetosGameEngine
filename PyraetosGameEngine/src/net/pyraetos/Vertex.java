package net.pyraetos;

import net.pyraetos.util.graphics.FloatVector;

public class Vertex{

	public static final int SIZE = 3;
	private FloatVector point;
	
	public Vertex(FloatVector point){
		this.point = point;
	}
	
	public Vertex(float x, float y, float z){
		this(new FloatVector(x, y, z));
	}
	
	public FloatVector getVector(){
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
