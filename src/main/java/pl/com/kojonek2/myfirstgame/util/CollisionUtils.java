package pl.com.kojonek2.myfirstgame.util;

import org.joml.Vector3f;

import pl.com.kojonek2.myfirstgame.collision.RayCast;
import pl.com.kojonek2.myfirstgame.collision.ProbableRayCollision;

public class CollisionUtils {

	private CollisionUtils() {
	}

	public static ProbableRayCollision getRayAndBoxCollision(Vector3f boxMinCorner, Vector3f boxMaxCorner, RayCast ray) {
		//https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-box-intersection
		//code from
		Vector3f rayStart = ray.getStartPoint();
		Vector3f direction = ray.getDirection();
		
		float tMin = (boxMinCorner.x - rayStart.x) / direction.x;
		float tMax = (boxMaxCorner.x - rayStart.x) / direction.x;

		if (tMin > tMax) {
			float temp = tMin;
			tMin = tMax;
			tMax = temp;
		}

		float tyMin = (boxMinCorner.y - rayStart.y) / direction.y;
		float tyMax = (boxMaxCorner.y - rayStart.y) / direction.y;

		if (tyMin > tyMax) {
			float temp = tyMin;
			tyMin = tyMax;
			tyMax = temp;
		}

		if ((tMin > tyMax) || (tyMin > tMax)) {
			return new ProbableRayCollision(ray, false, tMin);
		}

		if (tyMin > tMin) {
			tMin = tyMin;
		}

		if (tyMax < tMax) {
			tMax = tyMax;
		}

		float tzMin = (boxMinCorner.z - rayStart.z) / direction.z;
		float tzMax = (boxMaxCorner.z - rayStart.z) / direction.z;

		if (tzMin > tzMax) {
			float temp = tzMin;
			tzMin = tzMax;
			tzMax = temp;
		}
		
		if ((tMin > tzMax) || (tzMin > tMax)) {
			return new ProbableRayCollision(ray, false, tMin);
		}
		
		if (tzMin > tMin) {
			tMin = tzMin;
		}
		
		if (tzMax < tMax) {
			tMax = tzMax;
		}
		
		if(tMin > ray.getLength() || tMin < 0) {
			return new ProbableRayCollision(ray, false, tMin);
		}
		
		return new ProbableRayCollision(ray, true, tMin);
	}

}
