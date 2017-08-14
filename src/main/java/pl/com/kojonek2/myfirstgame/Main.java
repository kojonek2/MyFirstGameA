package pl.com.kojonek2.myfirstgame;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main implements Runnable {

	private int height = 720;
	private int width = 1280;

	private boolean running = false;
	private Thread renderingThread;
	private long window;

	public void start() {
		this.running = true;
		this.renderingThread = new Thread(this);
		this.renderingThread.start();
	}

	@Override
	public void run() {
		
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(this.window);
		glfwDestroyWindow(this.window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public static void main(String[] args) {

	}

}
