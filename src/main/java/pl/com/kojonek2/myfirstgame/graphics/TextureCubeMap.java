package pl.com.kojonek2.myfirstgame.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.joml.Vector2i;
import org.lwjgl.BufferUtils;

import pl.com.kojonek2.myfirstgame.util.FileUtils;
import pl.com.kojonek2.myfirstgame.util.PixelUtils;

public class TextureCubeMap {

	private int width, height; //height and width of single part texture2d;
	private int id;
	
	public TextureCubeMap(String combinedTexturePath) {
		this.id = this.load(combinedTexturePath);
	}
	
	public TextureCubeMap(String rightPath, String leftPath, String topPath, String bottomPath, String backPath, String frontPath) {
		this.id = this.load(new String[] {
				rightPath,
				leftPath,
				topPath,
				bottomPath,
				backPath,
				frontPath
		});
	}
	
	private int load(String path) {
		BufferedImage image = null;
		int[] pixels = null;
		try {
			image = ImageIO.read(FileUtils.getFile(path));
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
		
		this.width = image.getWidth() / 4;
		this.height = image.getHeight() / 4;
		if(this.height != this.width) {
			return -1;
		}
		
		int texture = glGenTextures();
		glBindTexture(GL_TEXTURE_CUBE_MAP, texture);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		List<Vector2i> cordinats = this.generateStartingCordinats();
		for(int i = 0; i < cordinats.size(); i++) {
			pixels = new int[this.width * this.height];
			image.getRGB(cordinats.get(i).x, cordinats.get(i).y, this.width, this.height, pixels, 0, this.width);
			int[] data = PixelUtils.convertARGBToRGBA(pixels);
			
			IntBuffer buffer = (IntBuffer) BufferUtils.createIntBuffer(data.length).put(data).flip();
			glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		}
		
		return texture;
	}
	
	private int load(String[] paths) {
		int texture = glGenTextures();
		glBindTexture(GL_TEXTURE_CUBE_MAP, texture);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		for(int i = 0; i < paths.length; i++) {
		int[] pixels = null;
		BufferedImage image = null;
		try {
			image = ImageIO.read(FileUtils.getFile(paths[i]));
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}

		if(this.width != 0 && this.height != 0 && (this.width != image.getWidth() || this.height  != image.getHeight())) {
			return -1;
		}
		
		this.width = image.getWidth();
		this.height = image.getHeight();
		
			pixels = new int[this.width * this.height];
			image.getRGB(0, 0, this.width, this.height, pixels, 0, this.width);
			int[] data = PixelUtils.convertARGBToRGBA(pixels);
			
			IntBuffer buffer = (IntBuffer) BufferUtils.createIntBuffer(data.length).put(data).flip();
			glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
		}
		
		return texture;
	}
	
	private List<Vector2i> generateStartingCordinats() {
		List<Vector2i> result = new ArrayList<>();
		result.add(new Vector2i(this.width * 2, this.height)); //right
		result.add(new Vector2i(0, this.height)); //left
		result.add(new Vector2i(this.width, 0)); //top
		result.add(new Vector2i(this.width, this.height * 2)); //bottom
		result.add(new Vector2i(this.width * 3, this.height)); //back
		result.add(new Vector2i(this.width, this.height)); //front
		return result;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getID() {
		return id;
	}
	
}
