package pl.com.kojonek2.myfirstgame.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import pl.com.kojonek2.myfirstgame.Camera;
import pl.com.kojonek2.myfirstgame.util.MatrixUtils;

public class TexturedModelShader extends ShaderProgram {
	private int transformationMatrixLocation;
	private int projectionMatrixLocation;
	private int viewMatrixLocation;
	
	private FloatBuffer reusableMatrixBuffer = BufferUtils.createFloatBuffer(16);

	protected TexturedModelShader() {
		super("shaders/textured_model_shader.vert", "shaders/textured_model_shader.frag");
		this.transformationMatrixLocation = this.getUnfiormLocation("transformation_matrix");
		this.projectionMatrixLocation = this.getUnfiormLocation("projection_matrix");
		this.viewMatrixLocation = this.getUnfiormLocation("view_matrix");
		this.loadProjectionMatrix(MatrixUtils.getProjectionMatrix());
	}

	@Override
	protected void bindAttributes() {
		glBindAttribLocation(this.programID, 0, "position");
		glBindAttribLocation(this.programID, 1, "textureCords");
	}

	//** IMPORTANT ENABLE SHADER BEFORE USING IT */
	public void loadTransformationMatrix(Matrix4f matrix) {
		this.reusableMatrixBuffer.clear();
		matrix.get(this.reusableMatrixBuffer);
		glUniformMatrix4fv(this.transformationMatrixLocation, false, this.reusableMatrixBuffer);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		this.start();
		this.reusableMatrixBuffer.clear();
		matrix.get(this.reusableMatrixBuffer);
		glUniformMatrix4fv(this.projectionMatrixLocation, false, this.reusableMatrixBuffer);
		this.stop();
	}
	
	public void loadViewMatrix(Camera camera) {
		this.start();
		this.reusableMatrixBuffer.clear();
		Matrix4f matrix = camera.getViewMatrix();
		matrix.get(this.reusableMatrixBuffer);
		glUniformMatrix4fv(this.viewMatrixLocation, false, this.reusableMatrixBuffer);
		this.stop();
	}
}
