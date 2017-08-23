package pl.com.kojonek2.myfirstgame;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.IntBuffer;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import pl.com.kojonek2.myfirstgame.graphics.Renderer;
import pl.com.kojonek2.myfirstgame.graphics.ShaderProgram;
import pl.com.kojonek2.myfirstgame.input.KeyboardHandler;
import pl.com.kojonek2.myfirstgame.set.Vaos;
import pl.com.kojonek2.myfirstgame.world.Chunk;

public class Main implements Runnable {

	public static int height = 720;
	public static int width = 1280;
	private String title = "My first game";
	
	private GLFWKeyCallback keyHandler;

	private Thread renderingThread;
	private long window;
	
	private Camera camera;
	private Renderer renderer;
	private Chunk chunk;
	
	public void start() {
		this.renderingThread = new Thread(this);
		this.renderingThread.start();
	}

	@Override
	public void run() {

		this.init();
		this.loop();
		this.cleanUp();
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(this.window);
		glfwDestroyWindow(this.window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");
		
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		// Create the window
		this.window = glfwCreateWindow(Main.width, Main.height, this.title, NULL, NULL);
		if (this.window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");
		
		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(this.window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}

		glfwMakeContextCurrent(this.window);
		// Disable v-sync
		glfwSwapInterval(0);
		glfwShowWindow(this.window);
		
		//Callback////////////////////
		glfwSetFramebufferSizeCallback(this.window, (long windowID, int width, int height) ->  glViewport(0, 0, width, height));
		
		this.keyHandler = new KeyboardHandler();
		KeyboardHandler.window = this.window;
		glfwSetKeyCallback(this.window, this.keyHandler);
		/////////////////////////////
	}

	public void loop() {
		GL.createCapabilities();
		
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		
		long currentTime = System.nanoTime();
		long lastTime = currentTime;
		double deltaTime = 0.0;
		double rate = 1_000_000_000 / 60.0;
		double delta = 0.0;
		
		this.glInit();
		
		while (!glfwWindowShouldClose(this.window)) {
			
			currentTime = System.nanoTime();
			deltaTime = currentTime - lastTime;
			delta += deltaTime / rate;
			lastTime = currentTime;
			
			while(delta >= 1.0) {
				this.update(deltaTime);
				
				updates++;
				delta--;
			}
			
			frames++;		
			this.render();

			//frame counter in title
			if(System.currentTimeMillis() - timer > 1000) {
				glfwSetWindowTitle(this.window, this.title + " | " + updates + " :ups " + frames + " :fps");
				updates = 0;
				frames = 0;
				timer += 1000;
			}
			
			glfwSwapBuffers(this.window);

			glfwPollEvents();
		}
	}
	
	public void glInit() {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		this.renderer = new Renderer(Vaos.CUBE_VAO, ShaderProgram.STANDARD);
		this.camera = new Camera();
		this.renderer.loadViewMatrix(this.camera);
		this.chunk = new Chunk(0, 0);
		this.chunk.generateChunk();
	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		this.renderer.render(this.chunk.getBlocksToRender());
	}
	
	public void update(double deltaTime) {
		this.camera.update();
		if(KeyboardHandler.isKeyDown(GLFW_KEY_BACKSPACE)) {
			this.camera.setPosition(new Vector3f(0f, 2.8f, 0f));
			this.camera.setXRotation(0f);
			this.camera.setYRotation(0f);
		}
		this.renderer.loadViewMatrix(this.camera);
	}

	public void cleanUp() {
		renderer.cleanUp();
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
	
}
