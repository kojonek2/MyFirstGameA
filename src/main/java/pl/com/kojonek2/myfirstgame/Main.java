package pl.com.kojonek2.myfirstgame;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import pl.com.kojonek2.myfirstgame.util.ShaderUtils;

public class Main implements Runnable {

	private int height = 720;
	private int width = 1280;
	private String title = "My first game";

	private Thread renderingThread;
	private long window;

	public void start() {
		this.renderingThread = new Thread(this);
		this.renderingThread.start();
	}

	@Override
	public void run() {

		this.init();
		this.loop();

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
		
		float[] vertices = new float[] {
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0f,
				0.5f, 0.5f, 0f,
				0f, 1f, 0f
		};
		FloatBuffer verticesBuff = (FloatBuffer) BufferUtils.createFloatBuffer(vertices.length).put(vertices).flip();
		
		int[] indices = new int[] {
				0, 1, 3,
				3, 1, 2,
				4, 0, 3
		};
		IntBuffer indicesBuff = (IntBuffer) BufferUtils.createIntBuffer(indices.length).put(indices).flip();
		
		int shader = ShaderUtils.load("shaders/shader.vert", "shaders/shader.frag");
		ShaderUtils.bindAtributes(shader, 0, "position");
		
		int vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		int vbo1ID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo1ID);
		glBufferData(GL_ARRAY_BUFFER, verticesBuff, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, true, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		int vbo2ID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo2ID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuff, GL_STATIC_DRAW);
		
		glBindVertexArray(0);
		
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
			this.render(shader, vaoID, indices.length);

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
	
	public void render(int shader, int vao, int count) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glUseProgram(shader);
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_INT, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		glUseProgram(0);
	}
	
	public void update(double deltaTime) {
		
	}

	public static void main(String[] args) {
		new Main().start();
	}

}
