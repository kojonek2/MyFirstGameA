package pl.com.kojonek2.myfirstgame.util;

import org.joml.Vector3f;

public class CollisionUtils {

	private CollisionUtils() {
	}

	public static boolean isRayAndBoxColliding(Vector3f boxMinCorner, Vector3f boxMaxCorner, Vector3f rayStart,
			Vector3f rayEnd) {
		float fractionInsideStart = 0f, fractionInsideEnd = 1f;

		float rayXMin, rayXMax;
		if (rayStart.x > rayEnd.x) {
			rayXMin = rayEnd.x;
			rayXMax = rayStart.x;
		} else {
			rayXMin = rayStart.x;
			rayXMax = rayEnd.x;
		}
		if (rayXMin != rayXMax) {
			fractionInsideStart = (boxMinCorner.x - rayXMin) / (rayXMax - rayXMin);
			fractionInsideEnd = (boxMaxCorner.x - rayXMin) / (rayXMax - rayXMin);
		} else if (rayXMax < boxMinCorner.x || rayXMax > boxMaxCorner.x) {
			return false;
		}

		float rayYMin, rayYMax;
		if (rayStart.y > rayEnd.y) {
			rayYMin = rayEnd.y;
			rayYMax = rayStart.y;
		} else {
			rayYMin = rayStart.y;
			rayYMax = rayEnd.y;
		}
		if (rayYMin != rayYMax) {
			fractionInsideStart = Math.max(fractionInsideStart, (boxMinCorner.y - rayYMin) / (rayYMax - rayYMin));
			fractionInsideEnd = Math.min(fractionInsideEnd, (boxMaxCorner.y - rayYMin) / (rayYMax - rayYMin));
		} else if (rayYMax < boxMinCorner.y || rayYMax > boxMaxCorner.y) {
			return false;
		}

		float rayZMin, rayZMax;
		if (rayStart.z > rayEnd.z) {
			rayZMin = rayEnd.z;
			rayZMax = rayStart.z;
		} else {
			rayZMin = rayStart.z;
			rayZMax = rayEnd.z;
		}
		if (rayZMin != rayZMax) {
			fractionInsideStart = Math.max(fractionInsideStart, (boxMinCorner.z - rayZMin) / (rayZMax - rayZMin));
			fractionInsideEnd = Math.min(fractionInsideEnd, (boxMaxCorner.z - rayZMin) / (rayZMax - rayZMin));
		} else if (rayZMax < boxMinCorner.z || rayZMax > boxMaxCorner.z) {
			return false;
		}
//		System.out.println("fractionInsideStart: " + fractionInsideStart);
//		System.out.println("fractionInsideEnd: " + fractionInsideEnd);
		return fractionInsideStart <= fractionInsideEnd;
	}

}
