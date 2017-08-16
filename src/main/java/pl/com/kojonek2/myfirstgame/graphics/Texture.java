package pl.com.kojonek2.myfirstgame.graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import pl.com.kojonek2.myfirstgame.util.FileUtils;

public class Texture {

	private int width, height;
	private int id;
	
	public Texture(String path) {
		this.id = this.load(path);
	}
	
	private int load(String path) {
		int[] pixels = null;
		try {
			BufferedImage image = ImageIO.read(FileUtils.getFile(path));
			this.width = image.getWidth();
			this.height = image.getHeight();
			pixels = new int[this.width * this.height];
			image.getRGB(0, 0, this.width, this.height, pixels, 0, this.width);
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		
		int[] data = new int[this.width * this.height];
		for(int i = 0; i < this.width * this.height; i++) {
			int alpha = (pixels[i] & 0xff000000) >> 24;
			int red = (pixels[i] & 0xff0000) >> 16;
			int green = (pixels[i] & 0xff00) >> 8;
			int blue = (pixels[i] & 0xff);
			
			data[i] = alpha << 24 | blue << 16 | green << 8 | red;
		}
		
		int texture = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture);
		//disable anti-aliasing
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		IntBuffer buffer = (IntBuffer) BufferUtils.createIntBuffer(data.length).put(data).flip();
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		
		glBindTexture(GL_TEXTURE_2D, 0);
		return texture;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getID() {
		return this.id;
	}
}
