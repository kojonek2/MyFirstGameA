package pl.com.kojonek2.myfirstgame.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.List;
import java.util.Map;

import pl.com.kojonek2.myfirstgame.Camera;
import pl.com.kojonek2.myfirstgame.VaoModel;
import pl.com.kojonek2.myfirstgame.blocks.BlockCube;

public class Renderer {

	private VaoModel blocksVao;
	private BasicShader shader;
	
	public Renderer(VaoModel blocksVao, BasicShader shader) {
		this.blocksVao = blocksVao;
		this.shader = shader;
	}
	
	public void render(Map<TextureCubeMap, List<BlockCube>> blocks) {
		this.shader.start();
		this.blocksVao.bindVao();
		glEnableVertexAttribArray(0);
		glActiveTexture(GL_TEXTURE0);
		
		for(TextureCubeMap texture : blocks.keySet()) {
			glBindTexture(GL_TEXTURE_CUBE_MAP, texture.getID());
			
			for(BlockCube block : blocks.get(texture)) {
				this.shader.loadTransformationMatrix(block.getTransformationMatrix());
				glDrawElements(GL_TRIANGLES, this.blocksVao.getNumberOfIndices(), GL_UNSIGNED_INT, 0);
			}
			
			glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
		}
		glDisableVertexAttribArray(0);
		this.blocksVao.unBindVao();
		this.shader.stop();
	}
	
	public void loadViewMatrix(Camera camera) {
		this.shader.loadViewMatrix(camera);
	}
	
	public void cleanUp() {
		this.blocksVao.cleanUp();
		this.shader.cleanUp();
	}
}
