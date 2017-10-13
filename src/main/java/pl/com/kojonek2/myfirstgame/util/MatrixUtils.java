package pl.com.kojonek2.myfirstgame.util;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import pl.com.kojonek2.myfirstgame.Main;

public class MatrixUtils {

	private MatrixUtils() {
	}
	
	public static Matrix4f getTransformationMatrix(Vector3i translation, float xRotation, float yRotation, float zRotation, float scale) {
		return MatrixUtils.getTransformationMatrix(translation.x, translation.y, translation.z, xRotation, yRotation, zRotation, scale);
	}
	
	public static Matrix4f getTransformationMatrix(Vector3f translation, float xRotation, float yRotation, float zRotation, float scale) {
		return MatrixUtils.getTransformationMatrix(translation.x, translation.y, translation.z, xRotation, yRotation, zRotation, scale);
	}
	
	private static Matrix4f getTransformationMatrix(float x, float y, float z, float xRotation, float yRotation, float zRotation, float scale) {
		Matrix4f matrix = new Matrix4f().translation(x, y, z);
		matrix.rotate((float) Math.toRadians(xRotation), new Vector3f(1f, 0f, 0f));
		matrix.rotate((float) Math.toRadians(yRotation), new Vector3f(0f, 1f, 0f));
		matrix.rotate((float) Math.toRadians(zRotation), new Vector3f(0f, 0f, 1f));
		matrix.scale(scale);
		return matrix;
	}
	
	public static Matrix4f getProjectionMatrix() {
		Matrix4f matrix = new Matrix4f().perspective((float) Math.toRadians(70), (float) Main.width / (float) Main.height, 0.01f, 1000f);
		return matrix;
	}
}
