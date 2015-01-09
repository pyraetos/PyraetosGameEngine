package net.pyraetos;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import net.pyraetos.util.Sys;

public class Camera{

	//Position
	private float x;
	private float y;
	private float z;
	
	//Orientation
	private float pitch;
	private float yaw;
	private float roll;
	
	//Scope
	private float fov;
	private float aspect;
	private float nearClip;
	private float farClip;
	
	public Camera(float aspect){
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.pitch = 0;
		this.yaw = 90f;
		this.roll = 0;
		this.fov = 70f;
		this.aspect = aspect;
		this.nearClip = 0.3f;
		this.farClip = 1000f;
		update();
	}

	private void update(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(fov, aspect, nearClip, farClip);
		glMatrixMode(GL_MODELVIEW);
		
	}
	
	public void view(){
		glRotatef(pitch, 1, 0, 0);
		glRotatef(yaw, 0, 1, 0);
		glRotatef(roll, 0, 0, 1);
		glTranslatef(x, y, z);
	}
	
	public void move(float magnitude){
		double ryaw = yaw * Math.PI / 180d;
		double dz = magnitude * Math.cos(ryaw);
		double dx = -magnitude * Math.sin(ryaw);
		z += dz;
		x += dx;
	}
	
	public void ascend(float magnitude){
		y += -magnitude;
	}
	
	public void rotate(float dtheta){
		setYaw(yaw - 90f + dtheta);
	}
	
	public float getX(){
		return -x;
	}

	public void setX(float x){
		this.x = -x;
	}

	public float getY(){
		return -y;
	}

	public void setY(float y){
		this.y = -y;
	}

	public float getZ(){
		return -z;
	}

	public void setZ(float z){
		this.z = -z;
	}

	public float getPitch(){
		return pitch;
	}

	public void setPitch(float pitch){
		this.pitch = pitch;
	}

	public float getYaw(){
		return yaw - 90f;
	}

	public void setYaw(float yaw){
		this.yaw = (float)(Sys.simplifyAngled(yaw) + 90f);
	}

	public float getRoll(){
		return roll;
	}

	public void setRoll(float roll){
		this.roll = roll;
	}

	public float getFieldOfView(){
		return fov;
	}

	public void setFieldOfView(float fov){
		this.fov = fov;
		update();
	}

	public float getAspectRatio(){
		return aspect;
	}

	public void setAspectRatio(float aspect){
		this.aspect = aspect;
		update();
	}

	public float getNearClip(){
		return nearClip;
	}

	public void setNearClip(float nearClip){
		this.nearClip = nearClip;
		update();
	}

	public float getFarClip(){
		return farClip;
	}

	public void setFarClip(float farClip){
		this.farClip = farClip;
		update();
	}
}
