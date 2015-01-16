package net.pyraetos;

import net.pyraetos.util.graphics.FloatMatrix;
import net.pyraetos.util.graphics.FloatVector;

public class Object3D{

	private Mesh mesh;
	private Shader shader;
	private Material material;
	private FloatVector translation;
	private FloatVector rotation;
	private FloatVector scale;
	
	public Object3D(Material material){
		this.material = material;
		mesh = new Mesh();
		init();
	}
	
	public Object3D(String meshDir){
		this.material = new Material();
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
		shader.addUniform("transformation");
		shader.addUniform("projection");
		shader.addUniform("tint");
	}
	
	public Mesh getMesh(){
		return mesh;
	}
	
	public Shader getShader(){
		return shader;
	}
	
	private FloatMatrix getTransformation(){
		FloatMatrix a = FloatMatrix.translation(translation).multiply(Game.getCamera().getTranslationMatrix());
		FloatMatrix b = FloatMatrix.rotation(rotation);
		FloatMatrix c = FloatMatrix.scale(scale);
		FloatMatrix d = Game.getCamera().getRotationMatrix();
		return d.multiply(a).multiply(b).multiply(c);
	}
	
	public void render(){
		shader.bind();
		shader.setUniform4("transformation", getTransformation());
		shader.setUniform4("projection", Game.getProjection());
		shader.setUniform("tint", material.getTint());
		material.bindTexture();
		mesh.render();
	}
	
	public void setMaterial(Material material){
		this.material = material;
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
	
	public Material getMaterial(){
		return material;
	}
	
	public Texture getTexture(){
		return material.getTexture();
	}
	
	public FloatVector getTint(){
		return material.getTint();
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
