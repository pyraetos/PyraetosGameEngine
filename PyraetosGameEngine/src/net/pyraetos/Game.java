package net.pyraetos;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import net.pyraetos.util.Sys;
import net.pyraetos.util.graphics.FloatVector;
import net.pyraetos.util.graphics.GraphicsUtil;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class Game{

	/*
	 * TODO:
	 * Input stuff
	 * Matrix to use constructor instead of zeros
	 * Store stuff as matrices instead of vectors
	 */
	
	public static final int DEFAULT_WIDTH = 800;
	public static final int DEFAULT_HEIGHT = 600;
	public static final String TITLE = "Game";
	public static final float FIELD_OF_VIEW = 70f * ((float)Math.PI / 180f);
	public static final FloatVector X_AXIS = new FloatVector(1f, 0f, 0f);
	public static final FloatVector Y_AXIS = new FloatVector(0f, 1f, 0f);
	public static final FloatVector Z_AXIS = new FloatVector(0f, 0f, 1f);
	public static final FloatVector DEFAULT_CLEAR_COLOR = new FloatVector(.5294f, .8078f, .9216f);
	public static final float NEAR_CLIP = 0f;
	public static final float FAR_CLIP = 1000f;
	public static final int MAX_FPS = 1000;
	public static final long FRAME_TIME = (long)(1d / (double)MAX_FPS * 1000000000d);
	public static final float MOVEMENT_SPEED = .025f;
	public static final float ROTATION_SPEED = .002f;
	
	private Set<Object3D> objects;
	
	private static Camera camera;
	private static float aspectRatio;
	private static int width;
	private static int height;
	private static FloatVector projection;
	private static FloatVector clearColor;
	
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
		setClearColor(DEFAULT_CLEAR_COLOR);
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		camera = new Camera();
		calculateProjection();
	}
	
	private void initGame(){
		objects = new HashSet<Object3D>();
		for(float i = 0; i < 2 * Math.PI - .1f; i+=(2 * Math.PI) / 18f){
			Object3D o = new Object3D("models/cube.obj");
			o.setMaterial(new Material(Sys.randomColor()));
			o.setTranslation((float)Math.cos(i) * 20, 0, (float)Math.sin(i) * 20);
			o.setScale(.6f, .6f, .6f);
			o.getMesh().setWireframe(true);
			objects.add(o);
		}
	}
	
	public static void setClearColor(FloatVector color){
		clearColor = color;
		glClearColor(color.getX(), color.getY(), color.getZ(), 1f);
	}
	
	public static void setClearColor(float red, float green, float blue){
		setClearColor(new FloatVector(red, green, blue));
	}
	
	public static void setClearColor(Color color){
		setClearColor(GraphicsUtil.toVector(color));
	}
	
	public static FloatVector getClearColor(){
		return clearColor;
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
	
	private void calculateProjection(){
		float x = ((float)Math.tan(Game.FIELD_OF_VIEW / 2f) * Game.getAspectRatio());
		float y = ((float)Math.tan(Game.FIELD_OF_VIEW / 2f));
		float z = -Game.NEAR_CLIP;
		float depth = Game.FAR_CLIP - Game.NEAR_CLIP;
		projection = new FloatVector(x, y, z, depth);
	}
	
	public static FloatVector getProjection(){
		return projection;
	}
	
	private void close(){
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
	
}
