package pl.com.kojonek2.myfirstgame.world;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import pl.com.kojonek2.myfirstgame.blocks.BlockCube;
import pl.com.kojonek2.myfirstgame.input.KeyboardHandler;
import pl.com.kojonek2.myfirstgame.input.MouseHandler;
import pl.com.kojonek2.myfirstgame.util.MatrixUtils;
import pl.com.kojonek2.myfirstgame.util.VectorUtils;

public class Player {
	
	/** y coordinate on feet level  */
	private Vector3f position;
	private Vector3f velocity = new Vector3f();
	private float xRotation = 0, yRotation = 0;
	
	private World world;
	
	public Player(World world, Vector3f position) {
		this.position = position;
		this.world = world;
	}
	
	public void update(boolean updateRotation) {
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
		
		if(this.isStandingOnGround()) {
			this.velocity.set(this.velocity.x, 0f, this.velocity.z);
			this.position.set(this.position.x, (float) Math.floor(this.position.y - 0.001f) + 1f, this.position.z);
		} else {
			this.velocity.add(0f, -0.01f, 0f);
			if(this.velocity.y < -0.03f) {
				this.velocity.set(this.velocity.x, -0.03f, this.velocity.z);
			}
		}
		
		this.position.add(this.velocity);
		
		if(!updateRotation) {
			return;
		}
		
		int yDelta = MouseHandler.getLastMoveX();
		this.yRotation -= ((float) yDelta) * 0.2f;
		this.clampYRotation();
			
		int xDelta = MouseHandler.getLastMoveY();
		this.xRotation += ((float) xDelta) * 0.2f;
		this.clampXRotation();
	}
	
	private boolean isStandingOnGround() {
		int x = Math.round(this.position.x);
		int y = (int) (this.position.y - 0.001);
		int z = Math.round(this.position.z);
		BlockCube block = this.world.getBlock(new Vector3i(x, y, z));
		if(block != null && block.isSolid()) {
			if(block.getPosition().y + 1 >= this.position.y) {
				return true;
			}
		}
		return false;
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
	
	public Matrix4f getTransformationMatrix() {
		return MatrixUtils.getTransformationMatrix(new Vector3f(this.position.x, this.position.y, this.position.z), 0f, this.yRotation, 0f, 1f);
	}
}
