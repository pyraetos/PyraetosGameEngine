package net.pyraetos.util.graphics;

import net.pyraetos.util.Vector;

public class FloatVector extends Vector<Float>{

	public FloatVector(Float... elements){
		super(elements);
	}

	public FloatVector multiply(float scalar){
		FloatVector result = new FloatVector();
		for(int i = 0; i < size; i++)
			result.add(scalar * get(i));
		return result;
	}
	
	public FloatVector add(FloatVector b){
		FloatVector result = new FloatVector();
		for(int i = 0; i < Math.min(size, b.size); i++)
			result.pushBack(get(i) + b.get(i));
		return result;
	}
	
	public FloatVector add(Float... b){
		return add(new FloatVector(b));
	}
	
	public FloatVector add(float scalar){
		FloatVector result = new FloatVector();
		for(int i = 0; i < size; i++)
			result.pushBack(get(i) + scalar);
		return result;
	}
	
	public float dot(FloatVector b){
		float c = 0f;
		for(int i = 0; i < Math.min(size, b.size); i++)
			c += get(i) * b.get(i);
		return c;
	}
	
	//public FloatVector cross(FloatVector b){
		
	//}
	
	public FloatVector toUnitVector(){
		FloatVector result = new FloatVector();
		float magnitude = magnitude();
		for(int i = 0; i < size; i++)
			result.pushBack(get(i) / magnitude);
		return result;
	}
	
	public float magnitude(){
		float inner = 0;
		for(int i = 0; i < size; i++){
			inner += Math.pow(get(i), 2);
		}
		return (float)Math.sqrt(inner);
	}
	
}
