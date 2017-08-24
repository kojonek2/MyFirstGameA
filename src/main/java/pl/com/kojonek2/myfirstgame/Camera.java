package pl.com.kojonek2.myfirstgame;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import pl.com.kojonek2.myfirstgame.input.KeyboardHandler;
import pl.com.kojonek2.myfirstgame.input.MouseHandler;


public class Camera {
	private Vector3f position = new Vector3f(0f, 0.5f, 1f);
	private float xRotation = 0, yRotation = 0;
	
	private Matrix4f reusableMatrix = new Matrix4f();
	
	public Camera() {
	}
	
	public Matrix4f getViewMatrix() {
		this.reusableMatrix.rotation((float) Math.toRadians(-this.xRotation), new Vector3f(1f, 0f, 0f));
		this.reusableMatrix.rotate((float) Math.toRadians(-this.yRotation), new Vector3f(0f, 1f, 0f));
		this.reusableMatrix.translate(new Vector3f(-this.position.x, -this.position.y, -this.position.z));
		return this.reusableMatrix;
	}
	
	/** 
	 * Generates normalized Vector, which represents direction where camera is looking, on plane xz.
	 * @param offset offset of yRotation in degrees
	 * @return forward vector
	 */
	public Vector3f getForwardVector(float offset) {
		float x = (float) -Math.sin(Math.toRadians(this.yRotation + offset));
		float z = (float) -Math.cos(Math.toRadians(this.yRotation + offset));
		return new Vector3f(x, 0f, z);
	}
	
	public void update() {
		if(KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
			this.position.add(this.getForwardVector(0f).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
			this.position.add(this.getForwardVector(180f).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
			this.position.add(this.getForwardVector(90f).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
			this.position.add(this.getForwardVector(270f).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_SPACE)) {
			this.position.add(new Vector3f(0f, 1f, 0f).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
			this.position.add(new Vector3f(0f, -1f, 0f).mul(0.1f));
		}
		
		int yDelta = MouseHandler.getLastMoveX();
		if(yDelta != 0) {
			MouseHandler.setLastMoveX(0);
		}
		this.yRotation -= ((float) yDelta) * 0.2f;
		this.clampYRotation();
		
		int xDelta = MouseHandler.getLastMoveY();
		if(xDelta != 0) {
			MouseHandler.setLastMoveY(0);
		}
		this.xRotation += ((float) xDelta) * 0.2f;
		this.clampXRotation();
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}
	
	public float getXRotation() {
		return xRotation;
	}
	
	public void setXRotation(float xRotation) {
		this.xRotation = xRotation;
	}
	
	public float getYRotation() {
		return yRotation;
	}
	
	public void setYRotation(float yRotation) {
		this.yRotation = yRotation;
	}
	
	private void clampXRotation() {
		if(this.xRotation > 360f) {
			this.xRotation -= 360f;
		} else if (this.xRotation < 0f) {
			this.xRotation += 360f;
		}
	}
	
	private void clampYRotation() {
		if(this.yRotation > 360f) {
			this.yRotation -= 360f;
		} else if (this.yRotation < 0f) {
			this.yRotation += 360f;
		}
	}
}
