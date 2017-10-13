package pl.com.kojonek2.myfirstgame.blocks;

import org.joml.Matrix4f;
import org.joml.Vector3i;

import pl.com.kojonek2.myfirstgame.graphics.TextureCubeMap;
import pl.com.kojonek2.myfirstgame.util.MatrixUtils;

public class BlockCube {

	private TextureCubeMap texture;
	private Vector3i position;
	
	public BlockCube(Vector3i position, TextureCubeMap texture) {
		this.texture = texture;
		this.position = position;
	}
	
	public Matrix4f getTransformationMatrix() {
		return MatrixUtils.getTransformationMatrix(this.position, 0f, 0f, 0f, 1f);
	}
	
	public Vector3i getPosition() {
		return position;
	}

	public void setPosition(Vector3i position) {
		this.position = position;
	}

	public TextureCubeMap getTexture() {
		return texture;
	}
	
	public boolean shouldBeRendered() {
		return true;
	}
	
}
