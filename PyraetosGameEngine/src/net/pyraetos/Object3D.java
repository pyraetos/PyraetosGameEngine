package net.pyraetos;

import net.pyraetos.util.Vector;
import net.pyraetos.util.graphics.FloatMatrix;
import net.pyraetos.util.graphics.GraphicsUtil;

public class Object3D{

	private Mesh mesh;
	private Shader shader;
	private Vector<Float> translation;
	private Vector<Float> rotation;
	private Vector<Float> scale;
	
	public Object3D(){
		mesh = new Mesh();
		init();
	}
	
	public Object3D(String meshDir){
		mesh = new Mesh(meshDir);
		init();
	}
	
	private void init(){
		shader = new Shader();
		this.translation = new Vector<Float>(0f, 0f, 0f);
		this.rotation = new Vector<Float>(0f, 0f, 0f);
		this.scale = new Vector<Float>(1f, 1f, 1f);
		shader.addVertexShader("shaders/vshader");
		shader.addFragmentShader("shaders/fshader");
		shader.linkShaders();
		shader.addUniform("translation");
		shader.addUniform("rotation");
		shader.addUniform("scale");
		shader.addUniform("projection");
	}
	
	public Mesh getMesh(){
		return mesh;
	}
	
	public Shader getShader(){
		return shader;
	}
	
	private Vector<Float> getProjection(){
		float x = ((float)Math.tan(Game.FIELD_OF_VIEW / 2f) * Game.ASPECT);
		float y = ((float)Math.tan(Game.FIELD_OF_VIEW / 2f));
		float z = -Game.NEAR_CLIP;
		float depth = Game.FAR_CLIP - Game.NEAR_CLIP;
		return new Vector<Float>(x, y, z, depth);
	}
	
	public void render(){
		shader.bind();
		shader.setUniform("translation", translation);
		shader.setUniform("rotation", FloatMatrix.rotation(rotation.getX(), rotation.getY(), rotation.getZ()));
		shader.setUniform("scale", scale);
		shader.setUniform4("projection", getProjection());
		mesh.render();
	}
	
	public float getX(){
		return translation.getX();
	}
	
	public float getY(){
		return translation.getY();
	}
	
	public float getZ(){
		return translation.getZ();
	}
	
	public void setTranslation(Vector<Float> v){
		translation = v;
	}
	
	public void setTranslation(float x, float y, float z){
		setTranslation(new Vector<Float>(x, y, z));
	}
	
	public void translate(Vector<Float> v){
		setTranslation(GraphicsUtil.add(translation, v));
	}
	
	public void translate(float dx, float dy, float dz){
		translate(new Vector<Float>(dx, dy, dz));
	}
	
	public void setRotation(Vector<Float> v){
		rotation = v;
	}
	
	public void setRotation(float pitch, float yaw, float roll){
		setRotation(new Vector<Float>(pitch, yaw, roll));
	}
	
	public void rotate(Vector<Float> v){
		setRotation(GraphicsUtil.add(rotation, v));
	}
	
	public void rotate(float pitch, float yaw, float roll){
		rotate(new Vector<Float>(pitch, yaw, roll));
	}
	
	public void setScale(Vector<Float> v){
		scale = v;
	}
	
	public void setScale(float x, float y, float z){
		setScale(new Vector<Float>(x, y, z));
	}
	
}
