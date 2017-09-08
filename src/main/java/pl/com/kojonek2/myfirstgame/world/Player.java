package pl.com.kojonek2.myfirstgame.world;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3f;

import pl.com.kojonek2.myfirstgame.input.KeyboardHandler;
import pl.com.kojonek2.myfirstgame.input.MouseHandler;
import pl.com.kojonek2.myfirstgame.util.VectorUtils;

public class Player {
	
	private Vector3f position;
	private float xRotation = 0, yRotation = 0;
	
	public Player(Vector3f position) {
		this.position = position;
	}
	
	public void update() {
		if(KeyboardHandler.isKeyDown(GLFW_KEY_W)) {
			this.position.add(VectorUtils.getForwardVector(0f, this.yRotation).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_S)) {
			this.position.add(VectorUtils.getForwardVector(180f, this.yRotation).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_A)) {
			this.position.add(VectorUtils.getForwardVector(90f, this.yRotation).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_D)) {
			this.position.add(VectorUtils.getForwardVector(270f, this.yRotation).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_SPACE)) {
			this.position.add(new Vector3f(0f, 1f, 0f).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_LEFT_SHIFT)) {
			this.position.add(new Vector3f(0f, -1f, 0f).mul(0.1f));
		}
		
		int yDelta = MouseHandler.getLastMoveX();
		this.yRotation -= ((float) yDelta) * 0.2f;
		this.clampYRotation();
			
		int xDelta = MouseHandler.getLastMoveY();
		this.xRotation += ((float) xDelta) * 0.2f;
		this.clampXRotation();
	}
	
	public Vector3f getPosition() {
		return this.position;
	}
	
	public float getXRotation() {
		return this.xRotation;
	}
	
	public float getYRotation() {
		return this.yRotation;
	}
	
	public void addXRotation(float amount) {
		this.xRotation += amount;
		this.clampXRotation();
	}
	
	public void addYRotation(float amount) {
		this.yRotation += amount;
		this.clampYRotation();
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
