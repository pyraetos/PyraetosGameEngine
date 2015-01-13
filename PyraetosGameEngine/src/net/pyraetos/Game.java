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

	public static final int WIDTH = 750;
	public static final int HEIGHT = 550;
	public static final String TITLE = "Game";
	public static final float ASPECT = (float)WIDTH / (float)HEIGHT;
	public static final float FIELD_OF_VIEW = 70f * ((float)Math.PI / 180f);
	public static final FloatVector X_AXIS = new FloatVector(1f, 0f, 0f);
	public static final FloatVector Y_AXIS = new FloatVector(0f, 1f, 0f);
	public static final FloatVector Z_AXIS = new FloatVector(0f, 0f, 1f);
	public static final float NEAR_CLIP = 0f;
	public static final float FAR_CLIP = 1000f;
	public static final int MAX_FPS = 1000;
	public static final long FRAME_TIME = (long)(1d / (double)MAX_FPS * 1000000000d);
	public static final float SPEED = .05f;
	
	private Set<Object3D> objects;
	
	private static Camera camera;
	
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
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
			Keyboard.create();
			Mouse.create();
		} catch(LWJGLException e){
			e.printStackTrace();
		}
		glClearColor(0f, 0f, 0f, 0f);
		//glFrontFace(GL_CW);
		//glCullFace(GL_BACK);
		//glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		//glEnable(GL_FRAMEBUFFER_SRGB);
		camera = new Camera();
	}
	
	private void initGame(){
		objects = new HashSet<Object3D>();
		Object3D small = new Object3D("models/cube.obj");
		small.setTranslation(0, 0, 15f);
		small.setScale(.6f, .6f, .6f);
		objects.add(small);
		Object3D big = new Object3D("models/cube.obj");
		big.setTranslation(-6f, 3f, 18f);
		objects.add(big);
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
		if(Input.isKeyDown(Keyboard.KEY_W))
			camera.move(0f, 0f, SPEED);
		if(Input.isKeyDown(Keyboard.KEY_S))
			camera.move(0f, 0f, -SPEED);
		if(Input.isKeyDown(Keyboard.KEY_A))
			camera.move(-SPEED, 0f, 0f);
		if(Input.isKeyDown(Keyboard.KEY_D))
			camera.move(SPEED, 0f, 0f);
		if(Input.isKeyDown(Keyboard.KEY_SPACE))
			camera.move(0f, SPEED, 0f);
		if(Input.isKeyDown(Keyboard.KEY_LSHIFT))
			camera.move(0f, -SPEED, 0f);
		for(Object3D object : objects){
			object.rotate(0.001f, 0.001f, 0.001f);
		}
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
	
	private void close(){
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
	
}
