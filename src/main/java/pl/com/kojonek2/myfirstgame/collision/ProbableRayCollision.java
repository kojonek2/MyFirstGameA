package pl.com.kojonek2.myfirstgame.collision;

import org.joml.Vector3f;

public class ProbableRayCollision {
	
	private boolean isColliding = false;
	private Vector3f collisionPoint;
	
	/**
	 * 
	 * @param ray rayCast
	 * @param isColliding true when collision occurs
	 * @param t constant by which direction vector will be multiplied to get collision point displacement form start vector
	 */
	public ProbableRayCollision(RayCast ray, boolean isColliding, float t) {
		this.isColliding = isColliding;
		if(isColliding) {
			Vector3f displacement = new Vector3f();
			this.collisionPoint = new Vector3f();
			ray.getDirection().mul(t, displacement);
			ray.getStartPoint().add(displacement, this.collisionPoint);
		}
	}
	
	public boolean isColliding() {
		return isColliding;
	}
	
	public Vector3f getCollisionPoint() {
		return this.collisionPoint;
	}
}