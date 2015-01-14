package net.pyraetos;

import net.pyraetos.util.graphics.FloatMatrix;
import net.pyraetos.util.graphics.FloatVector;

public class Object3D{

	private Mesh mesh;
	private Shader shader;
	private FloatVector translation;
	private FloatVector rotation;
	private FloatVector scale;
	
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
		this.translation = new FloatVector(0f, 0f, 0f);
		this.rotation = new FloatVector(0f, 0f, 0f);
		this.scale = new FloatVector(1f, 1f, 1f);
		shader.addVertexShader("shaders/vshader");
		shader.addFragmentShader("shaders/fshader");
		shader.linkShaders();
		shader.addUniform("translation");
		shader.addUniform("rotation");
		shader.addUniform("scale");
		shader.addUniform("projection");
		shader.addUniform("camera");
		shader.addUniform("cr");
	}
	
	public Mesh getMesh(){
		return mesh;
	}
	
	public Shader getShader(){
		return shader;
	}
	
	private FloatVector getProjection(){
		float x = ((float)Math.tan(Game.FIELD_OF_VIEW / 2f) * Game.getAspectRatio());
		float y = ((float)Math.tan(Game.FIELD_OF_VIEW / 2f));
		float z = -Game.NEAR_CLIP;
		float depth = Game.FAR_CLIP - Game.NEAR_CLIP;
		return new FloatVector(x, y, z, depth);
	}
	
	public void render(){
		shader.bind();
		shader.setUniform("translation", translation);
		shader.setUniform("rotation", FloatMatrix.rotation(rotation.getX(), rotation.getY(), rotation.getZ()));
		shader.setUniform("scale", scale);
		shader.setUniform4("projection", getProjection());
		shader.setUniform("camera", Game.getCamera().getPosition().multiply(-1f));
		shader.setUniform("cr", Game.getCamera().getRotationMatrix());
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
	
	public void setTranslation(FloatVector v){
		translation = v;
	}
	
	public void setTranslation(float x, float y, float z){
		setTranslation(new FloatVector(x, y, z));
	}
	
	public void translate(FloatVector v){
		setTranslation(translation.add(v));
	}
	
	public void translate(float dx, float dy, float dz){
		translate(new FloatVector(dx, dy, dz));
	}
	
	public void setRotation(FloatVector v){
		rotation = v;
	}
	
	public void setRotation(float pitch, float yaw, float roll){
		setRotation(new FloatVector(pitch, yaw, roll));
	}
	
	public void rotate(FloatVector v){
		setRotation(rotation.add(v));
	}
	
	public void rotate(float pitch, float yaw, float roll){
		rotate(new FloatVector(pitch, yaw, roll));
	}
	
	public void setScale(FloatVector v){
		scale = v;
	}
	
	public void setScale(float x, float y, float z){
		setScale(new FloatVector(x, y, z));
	}
	
}
