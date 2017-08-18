package pl.com.kojonek2.myfirstgame;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import pl.com.kojonek2.myfirstgame.graphics.ShaderProgram;
import pl.com.kojonek2.myfirstgame.graphics.Texture;

public class Main implements Runnable {

	private int height = 720;
	private int width = 1280;
	private String title = "My first game";

	private Thread renderingThread;
	private long window;
	
	private float[] vertices;
	private int[] indices;
	private float[] textureCords;
	
	private ShaderProgram shader;
	private VaoLoader loader;
	private Texture texture;

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
		this.window = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
		if (this.window == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		glfwSetKeyCallback(this.window, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true);
		});

		// Get the thread stack and push a new frame
		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(this.window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}

		// Make the OpenGL context current
		glfwMakeContextCurrent(this.window);
		// Disable v-sync
		glfwSwapInterval(0);
		// Make the window visible
		glfwShowWindow(this.window);
	}

	public void loop() {
		GL.createCapabilities();

		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
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
		this.vertices = new float[] {
				-0.5f, 0.5f, 0f,  //0
				-0.5f, -0.5f, 0f, //1
				0.5f, -0.5f, 0f,  //2
				0.5f, 0.5f, 0f,   //3
		};
		this.indices = new int[] {
				0, 1, 3,
				3, 1, 2,
		};
		this.textureCords = new float[] {
				0f, 0f,
				0f, 1f,
				1f, 1f,
				1f, 0f
		};
		this.shader = ShaderProgram.STANDARD;
		this.loader = new VaoLoader(this.vertices, this.indices, this.textureCords);
		this.texture = new Texture("textures/test.png");
	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		this.shader.start();
		this.loader.bindVao();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, this.texture.getID());
		glDrawElements(GL_TRIANGLES, this.indices.length, GL_UNSIGNED_INT, 0);
		glBindTexture(GL_TEXTURE_2D, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		this.loader.unBindVao();
		this.shader.stop();
	}
	
	public void update(double deltaTime) {
	}

	public void cleanUp() {
		loader.cleanUp();
		shader.cleanUp();
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
	
}
