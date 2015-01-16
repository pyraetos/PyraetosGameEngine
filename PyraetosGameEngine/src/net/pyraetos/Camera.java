package net.pyraetos;

import static net.pyraetos.Game.*;
import net.pyraetos.util.graphics.FloatMatrix;
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
	
	private void rotateX(float pitch){
		FloatVector right = getRight();
		forward = forward.rotate(pitch, right).toUnitVector();
		up = forward.cross(right).toUnitVector();
	}
	
	private void rotateY(float yaw){
		forward = forward.rotate(yaw, up).toUnitVector();
	}
	
	private void rotateZ(float roll){
		up = up.rotate(roll, forward).toUnitVector();
	}
	
	public void rotate(float pitch, float yaw, float roll){
		rotateX(pitch);
		rotateY(yaw);
		rotateZ(roll);
	}
	
	public FloatMatrix getRotationMatrix(){
		FloatMatrix m = FloatMatrix.identity(4);
		FloatVector right = getRight();
		m.set(0, 0, right.getX());	m.set(1, 0, right.getY());	m.set(2, 0, right.getZ());
		m.set(0, 1, up.getX());		m.set(1, 1, up.getY());		m.set(2, 1, up.getZ());
		m.set(0, 2, forward.getX());m.set(1, 2, forward.getY());m.set(2, 2, forward.getZ());
		return m;
	}
	
	public FloatMatrix getTranslationMatrix(){
		FloatMatrix m = FloatMatrix.identity(4);
		m.set(3, 0, -position.getX());
		m.set(3, 1, -position.getY());
		m.set(3, 2, -position.getZ());
		return m;
	}
	
	public FloatVector getRight(){
		return up.cross(forward).toUnitVector();
	}
	
	public FloatVector getLeft(){
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
	
	/**
	 * Moves relative to the camera's orientation
	 */
	public void move(FloatVector v){
		position = position.add(v);
	}
	
	/**
	 * Moves relative to the camera's orientation
	 */
	public void move(float x, float y, float z){
		FloatVector right = getRight();
		float rx = right.getX();
		float ry = right.getY();
		float rz = right.getZ();
		float ux = up.getX();
		float uy = up.getY();
		float uz = up.getZ();
		float fx = forward.getX();
		float fy = forward.getY();
		float fz = forward.getZ();
		position = position.add(rx * x + ux * y + fx * z, ry * x + uy * y + fy * z, rz * x + uz * y + fz * z);
	}
	
	public void moveAbsolute(FloatVector v){
		position = position.add(v);
	}
	
	public void moveAbsolute(float x, float y, float z){
		position = position.add(x, y, z);
	}
}