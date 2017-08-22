package pl.com.kojonek2.myfirstgame.blocks;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import pl.com.kojonek2.myfirstgame.graphics.TextureCubeMap;
import pl.com.kojonek2.myfirstgame.util.MatrixUtils;

public class Block {

	private TextureCubeMap texture;
	private Vector3f position;
	
	public Block(Vector3f position, TextureCubeMap texture) {
		this.texture = texture;
		this.position = position;
	}
	
	public Matrix4f getTransformationMatrix() {
		return MatrixUtils.getTransformationMatrix(this.position, 0f, 0f, 0f, 1f);
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public TextureCubeMap getTexture() {
		return texture;
	}
	
}
