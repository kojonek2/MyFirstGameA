package pl.com.kojonek2.myfirstgame.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import pl.com.kojonek2.myfirstgame.Camera;

public class BasicShader extends ShaderProgram {
	
	private int transformationMatrixLocation;
	private int projectionMatrixLocation;
	private int viewMatrixLocation;

	public BasicShader() {
		super("shaders/shader.vert", "shaders/shader.frag");
		this.transformationMatrixLocation = this.getUnfiormLocation("transformation_matrix");
		this.projectionMatrixLocation = this.getUnfiormLocation("projection_matrix");
		this.viewMatrixLocation = this.getUnfiormLocation("view_matrix");
	}

	@Override
	protected void bindAttributes() {
		glBindAttribLocation(this.programID, 0, "position");
		glBindAttribLocation(this.programID, 1, "texture_cord");
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		this.start();
		FloatBuffer buffer = (FloatBuffer) BufferUtils.createFloatBuffer(16);
		matrix.get(buffer);
		glUniformMatrix4fv(this.transformationMatrixLocation, false, buffer);
		this.stop();
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		this.start();
		FloatBuffer buffer = (FloatBuffer) BufferUtils.createFloatBuffer(16);
		matrix.get(buffer);
		glUniformMatrix4fv(this.projectionMatrixLocation, false, buffer);
		this.stop();
	}
	
	public void loadViewMatrix(Camera camera) {
		this.start();
		FloatBuffer buffer = (FloatBuffer) BufferUtils.createFloatBuffer(16);
		Matrix4f matrix = camera.getViewMatrix();
		matrix.get(buffer);
		glUniformMatrix4fv(this.viewMatrixLocation, false, buffer);
		this.stop();
	}
}
