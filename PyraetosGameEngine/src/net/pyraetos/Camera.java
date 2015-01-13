package net.pyraetos;

import static net.pyraetos.Game.*;
import net.pyraetos.util.graphics.FloatVector;

public class Camera{
	
	private FloatVector position;
	private FloatVector forward;
	private FloatVector up;
	
	public Camera(){
		position = new FloatVector(0f, 0f, 0f);
		forward = Z_AXIS;
		up = Y_AXIS;
	}
	
	public FloatVector getLeft(){
		return up.cross(forward).toUnitVector();
	}
	
	public FloatVector getRight(){
		return forward.cross(up).toUnitVector();
	}
	
	public FloatVector getUp(){
		return up;
	}
	
	public FloatVector getDown(){
		return up.multiply(-1f);
	}
	
	public FloatVector getForward(){
		return forward;
	}
	
	public FloatVector getBackward(){
		return forward.multiply(-1f);
	}
	
	public FloatVector getPosition(){
		return position;
	}
	
	public void move(FloatVector v){
		position = position.add(v);
	}
	
	public void move(float x, float y, float z){
		position = position.add(x, y, z);
	}
	
}