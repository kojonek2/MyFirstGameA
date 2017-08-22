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

import pl.com.kojonek2.myfirstgame.graphics.BasicShader;
import pl.com.kojonek2.myfirstgame.graphics.ShaderProgram;
import pl.com.kojonek2.myfirstgame.graphics.Texture2D;
import pl.com.kojonek2.myfirstgame.input.KeyboardHandler;
import pl.com.kojonek2.myfirstgame.util.MatrixUtils;

public class Main implements Runnable {

	public static int height = 720;
	public static int width = 1280;
	private String title = "My first game";
	
	private GLFWKeyCallback keyHandler;

	private Thread renderingThread;
	private long window;
	
	private float[] vertices;
	private int[] indices;
	private float[] textureCords;
	
	private BasicShader shader;
	private VaoModel object;
	private Texture2D texture;
	private Camera camera;

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
		
		this.vertices = new float[] {
				-0.5f, 1f, 0.5f,  //0
				-0.5f, 0f, 0.5f, //1
				0.5f, 0f, 0.5f,  //2
				0.5f, 1f, 0.5f,   //3
				-0.5f, 1f, -0.5f,  //4
				-0.5f, 0f, -0.5f, //5
				0.5f, 0f, -0.5f,  //6
				0.5f, 1f, -0.5f,   //7
		};
		this.indices = new int[] {
				0, 1, 3,
				3, 1, 2,
				0, 4, 1,
				4, 5, 1,
				3, 6, 7,
				3, 2, 6,
				4, 7, 5,
				7, 6, 5,
				0, 7, 4,
				0, 3, 7,
				1, 5, 6,
				1, 6, 2
		};
		this.textureCords = new float[] {
				0f, 0f,
				0f, 1f,
				1f, 1f,
				1f, 0f,
				0.2f, 0.2f,
				0.2f, 0.2f,
				0.2f, 0.2f,
				0.2f, 0.2f
		};
		this.camera = new Camera();
		this.shader = ShaderProgram.STANDARD;
		this.shader.loadProjectionMatrix(MatrixUtils.getProjectionMatrix());
		this.shader.loadTransformationMatrix(MatrixUtils.getTransformationMatrix(new Vector3f(0f, 0f, 0f), 0f, 0f, 0f, 1f));
		this.shader.loadViewMatrix(this.camera);
		this.texture = new Texture2D("textures/test.png");
		this.object = new VaoModel(this.vertices, this.indices, this.textureCords, this.texture, this.shader);
	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		this.object.render();
	}
	public void update(double deltaTime) {
		this.camera.update();
		if(KeyboardHandler.isKeyDown(GLFW_KEY_BACKSPACE)) {
			this.camera.setPosition(new Vector3f(0f, 2.8f, 0f));
			this.camera.setXRotation(0f);
			this.camera.setYRotation(0f);
		}
		this.shader.loadViewMatrix(this.camera);
	}

	public void cleanUp() {
		object.cleanUp();
		shader.cleanUp();
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
	
}
