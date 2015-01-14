package net.pyraetos;

import net.pyraetos.util.graphics.FloatVector;

public class Vertex{

	public static final int SIZE = 5;
	private FloatVector point;
	private FloatVector textureCoordinates;
	
	public Vertex(FloatVector point){
		this.point = point;
		textureCoordinates = new FloatVector(0f, 0f);
	}
	
	public Vertex(FloatVector point, FloatVector textureCoordinates){
		this.point = point;
		this.textureCoordinates = textureCoordinates;
	}
	
	public Vertex(float x, float y, float z){
		this(new FloatVector(x, y, z));
	}
	
	public FloatVector getVector(){
		return point;
	}
	
	public FloatVector getTextureCoordinates(){
		return textureCoordinates;
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
