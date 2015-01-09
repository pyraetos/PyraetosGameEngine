package net.pyraetos;

import java.util.HashSet;
import java.util.Set;

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
	public static final float NEAR_CLIP = 0f;
	public static final float FAR_CLIP = 1000f;
	
	private Set<Object3D> objects;
	
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
	}
	
	private void initGame(){
		objects = new HashSet<Object3D>();
		Object3D object = new Object3D("models/cube.obj");
		object.setScale(.25f, .25f, .25f);
		object.getMesh().setWireframe(true);
		objects.add(object);
	}
	
	private void loop(){
		while(!Display.isCloseRequested()){
			update();
			render();
		}
	}
	
	private float x = -10f;
	private float fx;
	
	private void update(){
		Input.update();
		x = x > 9.7f ? -10f : x + .001f;
		fx = 2f * (float)Math.sin(x);
	}
	
	private void render(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		for(Object3D object : objects){
			object.rotate(.0001f, 0.0001f, .0001f);
			object.setTranslation(x, fx, 10f);
			object.render();
		}
		Display.update();
	}
	
	private void close(){
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
	
}
