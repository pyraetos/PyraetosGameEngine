package net.pyraetos;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.newdawn.slick.opengl.TextureLoader;

import net.pyraetos.util.Data;

public class Texture{

	public String dir;
	public int pointer;
	
	public Texture(String dir){
		try{
			pointer = TextureLoader.getTexture("png", Data.getStream(dir)).getTextureID();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void bind(){
		glBindTexture(GL_TEXTURE_2D, pointer);
	}
	
}
