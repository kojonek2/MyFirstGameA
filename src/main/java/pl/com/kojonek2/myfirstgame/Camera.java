package pl.com.kojonek2.myfirstgame;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import pl.com.kojonek2.myfirstgame.input.KeyboardHandler;


public class Camera {
	private Vector3f position = new Vector3f(0f, 0f, 5f);
	private float xRotation = 0, yRotation = 0, zRotation = 0;
	
	public Camera() {
	}
	
	public Matrix4f getViewMatrix() {
		Matrix4f matrix = new Matrix4f().rotation((float) Math.toRadians(-this.xRotation), new Vector3f(1f, 0f, 0f));
		matrix.rotate((float) Math.toRadians(-this.yRotation), new Vector3f(0f, 1f, 0f));
		matrix.rotate((float) Math.toRadians(-this.zRotation), new Vector3f(0f, 0f, 1f));
		matrix.translate(new Vector3f(-this.position.x, -this.position.y, -this.position.z));
		return matrix;
	}
	
	public void update() {
		System.out.println(this.yRotation);
		Vector3f forward = new Vector3f(0f, 0f, -0.2f);
		forward.rotateX((float) Math.toRadians(this.xRotation));
		forward.rotateY((float) Math.toRadians(this.yRotation));
		forward.rotateZ((float) Math.toRadians(this.zRotation));
		Vector3f right = new Vector3f(0.2f, 0f, 0f);
		right.rotateX((float) Math.toRadians(this.xRotation));
		right.rotateY((float) Math.toRadians(this.yRotation));
		right.rotateZ((float) Math.toRadians(this.zRotation));
		if(KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
			this.position.add(forward);
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
			this.position.add(new Vector3f(forward).negate());
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
			this.position.add(new Vector3f(right).negate());
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
			this.position.add(right);
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_UP)) {
			this.xRotation += 1f;
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_DOWN)) {
			this.xRotation += -1f;
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT)) {
			this.yRotation += 1f;
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_RIGHT)) {
			this.yRotation += -1f;
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_Q)) {
			this.zRotation += 1f;
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_E)) {
			this.zRotation += -1f;
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
	
	public float getZRotation() {
		return zRotation;
	}
	
	public void setZRotation(float zRotation) {
		this.zRotation = zRotation;
	}
	
}
