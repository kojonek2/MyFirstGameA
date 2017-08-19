package pl.com.kojonek2.myfirstgame.graphics;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

public class BasicShader extends ShaderProgram {
	
	private int transformationMatrixLocation;

	public BasicShader() {
		super("shaders/shader.vert", "shaders/shader.frag");
		this.transformationMatrixLocation = this.getUnfiormLocation("transformationMatrix");
	}

	@Override
	protected void bindAttributes() {
		glBindAttribLocation(this.programID, 0, "position");
		glBindAttribLocation(this.programID, 1, "texture_cord");
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		FloatBuffer buffer = (FloatBuffer) BufferUtils.createFloatBuffer(16);
		matrix.get(buffer);
		glUniformMatrix4fv(this.transformationMatrixLocation, false, buffer);
	}
}
