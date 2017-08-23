package pl.com.kojonek2.myfirstgame.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.com.kojonek2.myfirstgame.VaoModel;
import pl.com.kojonek2.myfirstgame.blocks.Block;

public class Renderer {

	private Map<TextureCubeMap, List<Block>> blocks = new HashMap<>();
	private VaoModel blocksVao;
	private BasicShader shader;
	
	public Renderer(VaoModel blocksVao, BasicShader shader) {
		this.blocksVao = blocksVao;
		this.shader = shader;
	}
	
	public void addBlockToRender(Block block) {
		if(this.blocks.containsKey(block.getTexture())) {
			this.blocks.get(block.getTexture()).add(block);
			return;
		}
		List<Block> list = new ArrayList<Block>();
		list.add(block);
		this.blocks.put(block.getTexture(), list);
	}
	
	public void removeBlockFromRender(Block block) {
		List<Block> list = this.blocks.get(block.getTexture());
		list.remove(block);
		if(list.size() <= 0) {
			this.blocks.remove(block.getTexture());
		}
	}
	
	public void render() {
		this.shader.start();
		this.blocksVao.bindVao();
		glEnableVertexAttribArray(0);
		glActiveTexture(GL_TEXTURE0);
		for(TextureCubeMap texture : this.blocks.keySet()) {
			glBindTexture(GL_TEXTURE_CUBE_MAP, texture.getID());
			for(Block block : this.blocks.get(texture)) {
				this.shader.loadTransformationMatrix(block.getTransformationMatrix());
				glDrawElements(GL_TRIANGLES, this.blocksVao.getNumberOfIndices(), GL_UNSIGNED_INT, 0);
			}
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
