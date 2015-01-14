package net.pyraetos.util.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import net.pyraetos.Vertex;
import net.pyraetos.util.Matrix;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.*;

public abstract class GraphicsUtil{
	
	public static String getOpenGLVersion(){
		return glGetString(GL_VERSION);
	}
	
	public static FloatBuffer toBuffer(Vertex[] vertices){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.SIZE);
		for(Vertex v : vertices){
			buffer.put(v.getX());
			buffer.put(v.getY());
			buffer.put(v.getZ());
			buffer.put(v.getTextureCoordinates().getX());
			buffer.put(v.getTextureCoordinates().getY());
		}
		buffer.flip();
		return buffer;
	}
	
	public static IntBuffer toBuffer(int... values){
		IntBuffer buffer = BufferUtils.createIntBuffer(values.length);
		for(int i : values){
			buffer.put(i);
		}
		buffer.flip();
		return buffer;
	}
	
	public static FloatBuffer toBuffer(Matrix<Float> matrix){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(matrix.getWidth() * matrix.getHeight());
		for(int i = 0; i < matrix.getWidth(); i++)
			for(int j = 0; j < matrix.getHeight(); j++)
				buffer.put(matrix.get(i, j));
		buffer.flip();
		return buffer;
	}
	
	public static int[] toArray(List<Integer> l){
		int[] array = new int[l.size()];
		for(int i = 0; i < l.size(); i++)
			array[i] = l.get(i);
		return array;
	}
}
