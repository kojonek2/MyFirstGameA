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
import pl.com.kojonek2.myfirstgame.graphics.Texture2D;

public class VaoModel {
	
	private int vaoID;
	private List<Integer> vbos = new ArrayList<>();
	private int numberOfIndices;
	private Texture2D texture;
	private ShaderProgram shader;
	
	public VaoModel(float[] vertices, int[] indices, float[] textureCords, Texture2D texture, ShaderProgram shader) {
		this.texture = texture;
		this.shader = shader;
		this.generateVao();
		this.loadVertices(vertices);
		this.loadIndices(indices);
		this.loadTextureCords(textureCords);
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
	
	private void loadTextureCords(float[] textureCords) {
		int vbo = glGenBuffers();
		this.vbos.add(vbo);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		FloatBuffer buffer = (FloatBuffer) BufferUtils.createFloatBuffer(textureCords.length).put(textureCords).flip();
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void render() {
		this.shader.start();
		this.bindVao();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, this.texture.getID());
		glDrawElements(GL_TRIANGLES, this.numberOfIndices, GL_UNSIGNED_INT, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		this.unBindVao();
		this.shader.stop();
	}

	public void bindVao() {
		glBindVertexArray(this.vaoID);
	}
	
	public void unBindVao() {
		glBindVertexArray(this.vaoID);
	}
	
	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}

	public void setShader(ShaderProgram shader) {
		this.shader = shader;
	}
	
	public void cleanUp() {
		glDeleteVertexArrays(this.vaoID);
		for(int vbo : this.vbos) {
			glDeleteBuffers(vbo);
		}
	}
}
 