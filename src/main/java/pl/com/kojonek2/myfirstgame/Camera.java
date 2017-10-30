package pl.com.kojonek2.myfirstgame;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import pl.com.kojonek2.myfirstgame.blocks.BlockCube;
import pl.com.kojonek2.myfirstgame.collision.RayCast;
import pl.com.kojonek2.myfirstgame.input.KeyboardHandler;
import pl.com.kojonek2.myfirstgame.input.MouseHandler;
import pl.com.kojonek2.myfirstgame.util.VectorUtils;
import pl.com.kojonek2.myfirstgame.world.Player;
import pl.com.kojonek2.myfirstgame.world.World;


public class Camera {
	private Vector3f position = new Vector3f(0f, 0.5f, 1f);
	private float xRotation = 0, yRotation = 0;
	private CameraMode mode = CameraMode.PLAYERCAM;
	private Player owner;
	private World world;
	
	private Matrix4f reusableMatrix = new Matrix4f();
	
	public Camera(Player player, World world) {
		this.owner = player;
		this.world = world;
	}
	
	public Matrix4f getViewMatrix() {
		if(this.mode == CameraMode.FREECAM) {
			this.reusableMatrix.rotation((float) Math.toRadians(-this.xRotation), new Vector3f(1f, 0f, 0f));
			this.reusableMatrix.rotate((float) Math.toRadians(-this.yRotation), new Vector3f(0f, 1f, 0f));
			this.reusableMatrix.translate(new Vector3f(-this.position.x, -this.position.y, -this.position.z));
		} else if(this.mode == CameraMode.PLAYERCAM) {
			this.reusableMatrix.rotation((float) Math.toRadians(-this.owner.getXRotation()), new Vector3f(1f, 0f, 0f));
			this.reusableMatrix.rotate((float) Math.toRadians(-this.owner.getYRotation()), new Vector3f(0f, 1f, 0f));
			this.reusableMatrix.translate(new Vector3f(-this.owner.getPosition().x, -this.owner.getPosition().y - 1.8f, -this.owner.getPosition().z));
		} else {
			System.err.println("Invalid camera mode!");
			System.exit(-1);
		}
		return this.reusableMatrix;
	}
	
	public Vector3f getEyePosisition() {
		if(this.mode == CameraMode.FREECAM) {
			return new Vector3f(this.position);
		} else if(this.mode == CameraMode.PLAYERCAM) {
			return new Vector3f(this.owner.getPosition()).add(0f, 1.8f, 0f);
		} else {
			System.err.println("Invalid camera mode!");
			System.exit(-1);
			return null;
		}
	}
	
	public CameraMode getMode() {
		return this.mode;
	}
	
	public void setMode(CameraMode mode) {
		if(mode == null) {
			return;
		}
		this.mode = mode;
	}
	
	public void update() {
		BlockCube block = this.world.getBlockPointedByRay(new RayCast(this, 3f));
		if(block == null) {
			System.out.println("NO Collision");
		} else {
			System.out.println("colision x: +" + block.getPosition().x + " y: " + block.getPosition().y + " z: " + block.getPosition().z);
		}
		
		if(this.mode != CameraMode.FREECAM) {
			return;
		}
		
		if(KeyboardHandler.isKeyDown(GLFW_KEY_I)) {
			this.position.add(VectorUtils.getForwardVector(0f, this.yRotation).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_K)) {
			this.position.add(VectorUtils.getForwardVector(180f, this.yRotation).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_J)) {
			this.position.add(VectorUtils.getForwardVector(90f, this.yRotation).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_L)) {
			this.position.add(VectorUtils.getForwardVector(270f, this.yRotation).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_U)) {
			this.position.add(new Vector3f(0f, 1f, 0f).mul(0.1f));
		}
		if(KeyboardHandler.isKeyDown(GLFW_KEY_O)) {
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
	
	public enum CameraMode {
		FREECAM, PLAYERCAM
	}
}
