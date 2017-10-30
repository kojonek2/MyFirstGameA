package pl.com.kojonek2.myfirstgame.collision;

import org.joml.Vector3f;
import org.joml.Vector4f;

import pl.com.kojonek2.myfirstgame.Camera;
import pl.com.kojonek2.myfirstgame.util.MatrixUtils;

public class RayCast {

	private Vector3f startPoint;
	private Vector3f endPoint;
	private Vector3f direction;
	
	private float length;

	//** Ray casts form the center of the screen */
	public RayCast(Camera camera, float length) {
		this.startPoint = camera.getEyePosisition();
		this.length = length;
		
		Vector4f clipCoords = new Vector4f(0f, 0f, -1f, 1f);
		clipCoords.mul(MatrixUtils.getProjectionMatrix().invert());
		Vector4f eyeCoords = new Vector4f(clipCoords.x, clipCoords.y, -1f, 0f);
		Vector4f worldCoords = eyeCoords.mul(camera.getViewMatrix().invert());
		Vector3f ray = new Vector3f(worldCoords.x, worldCoords.y, worldCoords.z);
		ray.normalize().mul(length);
		endPoint = new Vector3f();
		startPoint.add(ray, endPoint);
		
		this.direction = new Vector3f();
		endPoint.sub(startPoint, direction);
		direction.normalize();
	}

	public Vector3f getStartPoint() {
		return this.startPoint;
	}

	public Vector3f getEndPoint() {
		return this.endPoint;
	}
	
	public Vector3f getDirection() {
		return this.direction;
	}
	
	public float getLength() {
		return this.length;
	}
	
}
