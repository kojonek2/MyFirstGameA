package pl.com.kojonek2.myfirstgame.util;

import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class MatrixUtils {

	private MatrixUtils() {
	}
	
	public static Matrix4f getTransformationMatrix(Vector3f translation, float xRotation, float yRotation, float zRotation, float scale) {
		Matrix4f matrix = new Matrix4f().translation(translation.x, translation.y, translation.z);
		matrix.rotate((float) Math.toRadians(xRotation), new Vector3f(1, 0, 0));
		matrix.rotate((float) Math.toRadians(yRotation), new Vector3f(0, 1, 0));
		matrix.rotate((float) Math.toRadians(zRotation), new Vector3f(0, 0, 1));
		matrix.scale(scale);
		return matrix;
	}
}
