package pl.com.kojonek2.myfirstgame.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import pl.com.kojonek2.myfirstgame.util.FileUtils;

public class ShaderProgram {

	public static ShaderProgram STANDARD = new ShaderProgram("shaders/shader.vert", "shaders/shader.frag").bindAttribute(0, "position").bindAttribute(1, "texture_cord");
	
	private int programID;
	private int vertexID;
	private int fragmentID;
	
	private ShaderProgram() {
	}
	
	private ShaderProgram(String vertPath, String fragPath) {
		this.load(vertPath, fragPath);
	}
	
	private ShaderProgram bindAttribute(int attribute, String name) {
		glBindAttribLocation(programID, attribute, name);
		glLinkProgram(programID);
		glValidateProgram(programID);
		return this;
	}
	
	private void load(String vertPath, String fragPath) {
		String vert = FileUtils.loadAsString(vertPath);
		String frag = FileUtils.loadAsString(fragPath);
		this.create(vert, frag);
	}
	
	private void create(String vert, String frag) {
		this.programID = glCreateProgram();
		this.vertexID = glCreateShader(GL_VERTEX_SHADER);
		this.fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(this.vertexID, vert);
		glShaderSource(this.fragmentID, frag);
		
		glCompileShader(vertexID);
		if(glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile vertex shader!");
			System.err.println(glGetShaderInfoLog(vertexID));
		}
		
		glCompileShader(this.fragmentID);
		if(glGetShaderi(this.fragmentID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Failed to compile fragment shader!");
			System.err.println(glGetShaderInfoLog(this.fragmentID));
		}
		
		glAttachShader(this.programID, this.vertexID);
		glAttachShader(this.programID, this.fragmentID);
		glLinkProgram(this.programID);
		glValidateProgram(this.programID);
	}
	
	public void start() {
		glUseProgram(this.programID);
	}
	
	public void stop() {
		glUseProgram(0);
	}
	
	public void cleanUp() {
		this.stop();
		glDetachShader(this.programID, this.vertexID);
		glDetachShader(this.programID, this.fragmentID);
		glDeleteShader(this.vertexID);
		glDeleteShader(this.fragmentID);
		glDeleteProgram(this.programID);
	}

	public int getProgramID() {
		return programID;
	}

	public int getVertexID() {
		return vertexID;
	}

	public int getFragmentID() {
		return fragmentID;
	}
}
