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
		matrix.translate(position.negate());
		return matrix;
	}
	
	public void update() {
		if(KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
			this.position.add(0f, 0f, 0.2f);
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
