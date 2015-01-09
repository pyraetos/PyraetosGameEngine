package net.pyraetos;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.util.HashMap;
import java.util.Map;

import net.pyraetos.util.Data;
import net.pyraetos.util.Matrix;
import net.pyraetos.util.Vector;
import net.pyraetos.util.Sys;
import net.pyraetos.util.graphics.GraphicsUtil;

public class Shader{
	
	private int pointer;
	private Map<String, Integer> uniforms;
	
	public Shader(){
		pointer = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
	}
	
	public void addVertexShader(String shader){
		addShader(Data.load(shader), GL_VERTEX_SHADER);
	}
	
	public void addGeometryShader(String shader){
		addShader(Data.load(shader), GL_GEOMETRY_SHADER);
	}
	
	public void addFragmentShader(String shader){
		addShader(Data.load(shader), GL_FRAGMENT_SHADER);
	}
	
	private void addShader(String code, int type){
		int shader = glCreateShader(type);
		glShaderSource(shader, code);
		glCompileShader(shader);
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0){
			Sys.error("Syntax error in shader!");
			System.exit(1);
		}
		glAttachShader(pointer, shader);
	}
	
	public void linkShaders(){
		glLinkProgram(pointer);
		if(glGetProgrami(pointer, GL_LINK_STATUS) == 0){
			Sys.error("Linking error in shader!");
			System.exit(1);
		}
	}
	
	public void bind(){
		glUseProgram(pointer);
	}

	public void addUniform(String uniform){
		int ptr = glGetUniformLocation(pointer, uniform);
		if(ptr == -1){
			Sys.error("Uniform \"" + uniform + "\" does not exist!");
			System.exit(1);
		}
		uniforms.put(uniform, ptr);
	}
	
	public void setUniform(String name, int value){
		glUniform1i(uniforms.get(name), value);
	}

	public void setUniform(String name, float value){
		glUniform1f(uniforms.get(name), value);
	}
	
	public void setUniform(String name, double value){
		setUniform(name, (float)value);
	}
	
	public void setUniform(String name, Vector<Float> value){
		glUniform3f(uniforms.get(name), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform4(String name, Vector<Float> value){
		glUniform4f(uniforms.get(name), value.getX(), value.getY(), value.getZ(), value.get(3));
	}
	
	public void setUniform(String name, Matrix<Float> matrix){
		glUniformMatrix3(uniforms.get(name), false, GraphicsUtil.toBuffer(matrix));
	}
}