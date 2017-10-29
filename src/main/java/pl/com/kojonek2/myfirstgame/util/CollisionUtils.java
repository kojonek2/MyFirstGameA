package pl.com.kojonek2.myfirstgame.util;

import org.joml.Vector3f;

import pl.com.kojonek2.myfirstgame.world.RayCast;

public class CollisionUtils {

	private CollisionUtils() {
	}

	public static boolean isRayAndBoxColliding(Vector3f boxMinCorner, Vector3f boxMaxCorner, RayCast ray) {
		//https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-box-intersection
		//code from
		Vector3f rayStart = ray.getStartPoint();
		Vector3f rayEnd = ray.getEndPoint();
		Vector3f direction = new Vector3f();
		rayEnd.sub(rayStart, direction);
		direction.normalize();
		
		float tmin = (boxMinCorner.x - rayStart.x) / direction.x;
		float tmax = (boxMaxCorner.x - rayStart.x) / direction.x;

		if (tmin > tmax) {
			float temp = tmin;
			tmin = tmax;
			tmax = temp;
		}

		float tymin = (boxMinCorner.y - rayStart.y) / direction.y;
		float tymax = (boxMaxCorner.y - rayStart.y) / direction.y;

		if (tymin > tymax) {
			float temp = tymin;
			tymin = tymax;
			tymax = temp;
		}

		if ((tmin > tymax) || (tymin > tmax)) {
			return false;
		}

		if (tymin > tmin) {
			tmin = tymin;
		}

		if (tymax < tmax) {
			tmax = tymax;
		}

		float tzmin = (boxMinCorner.z - rayStart.z) / direction.z;
		float tzmax = (boxMaxCorner.z - rayStart.z) / direction.z;

		if (tzmin > tzmax) {
			float temp = tzmin;
			tzmin = tzmax;
			tzmax = temp;
		}
		
		if ((tmin > tzmax) || (tzmin > tmax)) {
			return false;
		}
		
		if (tzmin > tmin) {
			tmin = tzmin;
		}
		
		if (tzmax < tmax) {
			tmax = tzmax;
		}
		
		if(tmin > ray.getLength()) {
			return false;
		}
		return true;
	}

}
