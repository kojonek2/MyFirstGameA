package pl.com.kojonek2.myfirstgame.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.List;

import pl.com.kojonek2.myfirstgame.VaoModel;
import pl.com.kojonek2.myfirstgame.blocks.Block;

public class Renderer {

	private List<Block> blocks = new ArrayList<>();
	private VaoModel blocksVao;
	private BasicShader shader;
	
	public Renderer(VaoModel blocksVao, BasicShader shader) {
		this.blocksVao = blocksVao;
		this.shader = shader;
	}
	
	public void addBlockToRender(Block block) {
		blocks.add(block);
	}
	
	public void removeBlockFromRender(Block block) {
		blocks.remove(block);
	}
	
	public void render() {
		this.shader.start();
		this.blocksVao.bindVao();
		glEnableVertexAttribArray(0);
		glActiveTexture(GL_TEXTURE0);
		for(Block block : this.blocks) {
			glBindTexture(GL_TEXTURE_CUBE_MAP, block.getTexture().getID());
			this.shader.loadTransformationMatrix(block.getTransformationMatrix());
			glDrawElements(GL_TRIANGLES, this.blocksVao.getNumberOfIndices(), GL_UNSIGNED_INT, 0);
			glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
		}
		glDisableVertexAttribArray(0);
		this.blocksVao.unBindVao();
		this.shader.stop();
	}
	
	public void cleanUp() {
		this.blocksVao.cleanUp();
	}
}
