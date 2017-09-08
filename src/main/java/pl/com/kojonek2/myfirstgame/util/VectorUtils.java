package pl.com.kojonek2.myfirstgame.util;

import org.joml.Vector3f;

public class VectorUtils {
	
	private VectorUtils() {
	}
	
	/** 
	 * Generates normalized Vector, which represents direction where camera/player is looking, on plane xz.
	 * @param offset offset of yRotation in degrees
	 * @param yRotation rotation of yAxis
	 * @return forward vector
	 */
	public static Vector3f getForwardVector(float offset, float yRotation) {
		float x = (float) -Math.sin(Math.toRadians(yRotation + offset));
		float z = (float) -Math.cos(Math.toRadians(yRotation + offset));
		return new Vector3f(x, 0f, z);
	}
	
}
