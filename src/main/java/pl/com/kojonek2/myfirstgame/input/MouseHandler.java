package pl.com.kojonek2.myfirstgame.input;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseHandler extends GLFWCursorPosCallback{

	private IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
	private IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
	
	private long currentTime;
	private long lastTestTime = System.nanoTime();
	private double rate = 1_000_000_000.0 / 60.0;
	private double deltaTime = 0.0;
	private double delta = 0.0;
	
	private int xDisplacment = 0;
	private int yDisplacment = 0;
	
	private static int lastMoveX;
	private static int lastMoveY;
	
	@Override
	public void invoke(long window, double xpos, double ypos) {
		this.currentTime = System.nanoTime();
		this.deltaTime = currentTime - lastTestTime;
		this.delta += deltaTime / rate;
		this.lastTestTime = currentTime;
		
		this.heightBuffer.clear();
		this.widthBuffer.clear();
		glfwGetWindowSize(window, widthBuffer, heightBuffer);
		int centerX = this.widthBuffer.get(0) / 2;
		int centerY = this.heightBuffer.get(0) / 2;
		
		this.xDisplacment += xpos - centerX;
		this.yDisplacment += centerY - ypos;
		
		if(this.delta >= 1) {
			lastMoveX = this.xDisplacment;
			lastMoveY = this.yDisplacment;
			
			this.xDisplacment = 0;
			this.yDisplacment = 0;
			this.delta = 0;
		}
		glfwSetCursorPos(window, centerX, centerY);
	}

	public static int getLastMoveX() {
		return MouseHandler.lastMoveX;
	}
	
	public static int getLastMoveY() {
		return MouseHandler.lastMoveY;
	}

	public static void setLastMoveX(int lastMoveX) {
		MouseHandler.lastMoveX = lastMoveX;
	}

	public static void setLastMoveY(int lastMoveY) {
		MouseHandler.lastMoveY = lastMoveY;
	}
	
	
}
