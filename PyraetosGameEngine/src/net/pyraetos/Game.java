package net.pyraetos;

import java.util.HashSet;
import java.util.Set;

import net.pyraetos.util.Sys;
import net.pyraetos.util.graphics.FloatVector;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Game{

	/*
	 * TODO:
	 * Shader stuff
	 * Input stuff
	 */
	
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	public static final String TITLE = "Game";
	public static final float FIELD_OF_VIEW = 70f * ((float)Math.PI / 180f);
	public static final FloatVector X_AXIS = new FloatVector(1f, 0f, 0f);
	public static final FloatVector Y_AXIS = new FloatVector(0f, 1f, 0f);
	public static final FloatVector Z_AXIS = new FloatVector(0f, 0f, 1f);
	public static final float NEAR_CLIP = 1.5f;
	public static final float FAR_CLIP = 1000f;
	public static final int MAX_FPS = 1000;
	public static final long FRAME_TIME = (long)(1d / (double)MAX_FPS * 1000000000d);
	public static final float MOVEMENT_SPEED = .05f;
	public static final float ROTATION_SPEED = .003f;
	
	private Set<Object3D> objects;
	
	private static Camera camera;
	public static float aspectRatio;
	public static int width;
	public static int height;
	
	public static void main(String[] args){
		new Game();
	}
	
	public Game(){
		initGL();
		initGame();
		loop();
		close();
	}
	
	private void initGL(){
		Display.setTitle(TITLE);
		try{
			width = DEFAULT_WIDTH;
			height = DEFAULT_HEIGHT;
			aspectRatio = (float)width / (float)height;
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch(LWJGLException e){
			e.printStackTrace();
		}
		glClearColor(0f, 0f, 0f, 0f);
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		//glEnable(GL_FRAMEBUFFER_SRGB);
		camera = new Camera();
	}
	
	private void initGame(){
		objects = new HashSet<Object3D>();
		for(float i = 0; i < 2 * Math.PI; i+=(2 * Math.PI) / 18f){
			Object3D o = new Object3D("models/cube.obj");
			o.setTranslation((float)Math.cos(i) * 20, 0, (float)Math.sin(i) * 20);
			o.setScale(.6f, .6f, .6f);
			o.getMesh().setWireframe(true);
			objects.add(o);
		}
	}
	
	private void loop(){
		long lastFrame = 0;
		long lag = 0;
		while(true){
			long startTime = Sys.time();
			lag += lastFrame;
			boolean render = false;
			while(lag > FRAME_TIME){
				render = true;
				lag -= FRAME_TIME;
				if(Display.isCloseRequested())
					return;
				update();
			}
			if(render)
				render();
			else
				Sys.sleep(1);
			lastFrame = Sys.time() - startTime;
		}
	}
	
	private void update(){
		Input.update();
		updateMovement();
		updateRotation();
		for(Object3D object : objects){
			object.rotate(0.001f, 0.001f, 0.001f);
		}
	}
	
	private void updateMovement(){
		if(Input.isKeyDown(Keyboard.KEY_W))
			camera.move(0f, 0f, MOVEMENT_SPEED);
		if(Input.isKeyDown(Keyboard.KEY_S))
			camera.move(0f, 0f, -MOVEMENT_SPEED);
		if(Input.isKeyDown(Keyboard.KEY_A))
			camera.move(-MOVEMENT_SPEED, 0f, 0f);
		if(Input.isKeyDown(Keyboard.KEY_D))
			camera.move(MOVEMENT_SPEED, 0f, 0f);
		if(Input.isKeyDown(Keyboard.KEY_SPACE))
			camera.move(0f, MOVEMENT_SPEED, 0f);
		if(Input.isKeyDown(Keyboard.KEY_LSHIFT))
			camera.move(0f, -MOVEMENT_SPEED, 0f);
	}
	
	private void updateRotation(){
		if(Input.isKeyDown(Keyboard.KEY_UP))
			camera.rotate(-ROTATION_SPEED, 0f, 0f);
		if(Input.isKeyDown(Keyboard.KEY_DOWN))
			camera.rotate(ROTATION_SPEED, 0f, 0f);
		if(Input.isKeyDown(Keyboard.KEY_LEFT))
			camera.rotate(0f, -ROTATION_SPEED, 0f);
		if(Input.isKeyDown(Keyboard.KEY_RIGHT))
			camera.rotate(0f, ROTATION_SPEED, 0f);
	}
	
	private void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		for(Object3D object : objects)
			object.render();
		Display.update();
	}
	
	public static Camera getCamera(){
		return camera;
	}
	
	public static float getAspectRatio(){
		return aspectRatio;
	}
	
	public static int getWidth(){
		return width;
	}
	
	public static int getHeight(){
		return height;
	}
	
	private void close(){
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
	
}
