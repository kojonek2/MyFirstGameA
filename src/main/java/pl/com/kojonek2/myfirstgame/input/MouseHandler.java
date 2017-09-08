package pl.com.kojonek2.myfirstgame.input;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MouseHandler extends GLFWCursorPosCallback{

	private IntBuffer heightBuffer = BufferUtils.createIntBuffer(1);
	private IntBuffer widthBuffer = BufferUtils.createIntBuffer(1);
	
	private int xDisplacment = 0;
	private int yDisplacment = 0;
	
	private static int lastMoveX;
	private static int lastMoveY;
	
	@Override
	public void invoke(long window, double xpos, double ypos) {
		
		this.heightBuffer.clear();
		this.widthBuffer.clear();
		glfwGetWindowSize(window, widthBuffer, heightBuffer);
		int centerX = this.widthBuffer.get(0) / 2;
		int centerY = this.heightBuffer.get(0) / 2;
		
		this.xDisplacment += xpos - centerX;
		this.yDisplacment += centerY - ypos;
		
		glfwSetCursorPos(window, centerX, centerY);
	}
	
	public void update() {
		lastMoveX = this.xDisplacment;
		lastMoveY = this.yDisplacment;
			
		this.xDisplacment = 0;
		this.yDisplacment = 0;
	}

	public static int getLastMoveX() {
		return MouseHandler.lastMoveX;
	}
	
	public static int getLastMoveY() {
		return MouseHandler.lastMoveY;
	}

}
