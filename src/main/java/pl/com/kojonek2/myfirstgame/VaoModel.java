package pl.com.kojonek2.myfirstgame;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import pl.com.kojonek2.myfirstgame.graphics.ShaderProgram;
import pl.com.kojonek2.myfirstgame.graphics.TextureCubeMap;

public class VaoModel {
	
	private int vaoID;
	private List<Integer> vbos = new ArrayList<>();
	private int numberOfIndices;
	private ShaderProgram shader;
	
	public VaoModel(float[] vertices, int[] indices, ShaderProgram shader) {
		this.shader = shader;
		this.generateVao();
		this.loadVertices(vertices);
		this.loadIndices(indices);
		this.unBindVao();
	}
	
	private void generateVao() {
		this.vaoID = glGenVertexArrays();
		this.bindVao();
	}
	
	private void loadVertices(float[] vertices) {
		int vbo = glGenBuffers();
		this.vbos.add(vbo);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		FloatBuffer buffer = (FloatBuffer) BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip();
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	private void loadIndices(int[] indices) {
		this.numberOfIndices = indices.length;
		int vbo = glGenBuffers();
		this.vbos.add(vbo);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
		IntBuffer buffer = (IntBuffer) BufferUtils.createIntBuffer(indices.length).put(indices).flip();
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
	}
	
	@SuppressWarnings("unused")
	private void loadTextureCords(float[] textureCords) {
		int vbo = glGenBuffers();
		this.vbos.add(vbo);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		FloatBuffer buffer = (FloatBuffer) BufferUtils.createFloatBuffer(textureCords.length).put(textureCords).flip();
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void bindVao() {
		glBindVertexArray(this.vaoID);
	}
	
	public void unBindVao() {
		glBindVertexArray(this.vaoID);
	}
	
	public void setShader(ShaderProgram shader) {
		this.shader = shader;
	}
	
	public int getNumberOfIndices() {
		return this.numberOfIndices;
	}
	
	public void cleanUp() {
		glDeleteVertexArrays(this.vaoID);
		for(int vbo : this.vbos) {
			glDeleteBuffers(vbo);
		}
	}
}
 