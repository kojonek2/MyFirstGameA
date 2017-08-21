package pl.com.kojonek2.myfirstgame;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import pl.com.kojonek2.myfirstgame.input.KeyboardHandler;


public class Camera {
	private Vector3f position = new Vector3f(0f, 0f, 5f);
	private float xRotation = 0, yRotation = 0;
	
	public Camera() {
	}
	
	public Matrix4f getViewMatrix() {
		Matrix4f matrix = new Matrix4f().rotation((float) Math.toRadians(-this.xRotation), new Vector3f(1f, 0f, 0f));
		matrix.rotate((float) Math.toRadians(-this.yRotation), new Vector3f(0f, 1f, 0f));
		matrix.translate(new Vector3f(-this.position.x, -this.position.y, -this.position.z));
		return matrix;
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
		if(KeyboardHandler.isKeyDown(GLFW_KEY_UP)) {
			this.xRotation += 1f;
			this.clampXRotation();
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_DOWN)) {
			this.xRotation += -1f;
			this.clampXRotation();
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT)) {
			this.yRotation += 1f;
			this.clampYRotation();
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_RIGHT)) {
			this.yRotation += -1f;
			this.clampYRotation();
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_SPACE)) {
			this.position.add(0f, 0.2f, 0f);
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
			this.position.add(0f, -0.2f, 0f);
		}
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
	
	public void clampXRotation() {
		if(this.xRotation > 360f) {
			this.xRotation -= 360f;
		} else if (this.xRotation < 0f) {
			this.xRotation += 360f;
		}
	}
	
	public void clampYRotation() {
		if(this.yRotation > 360f) {
			this.yRotation -= 360f;
		} else if (this.yRotation < 0f) {
			this.yRotation += 360f;
		}
	}
}
