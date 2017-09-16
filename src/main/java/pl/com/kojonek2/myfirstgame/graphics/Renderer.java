package pl.com.kojonek2.myfirstgame.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.List;
import java.util.Map;

import pl.com.kojonek2.myfirstgame.Camera;
import pl.com.kojonek2.myfirstgame.VaoModel;
import pl.com.kojonek2.myfirstgame.blocks.BlockCube;
import pl.com.kojonek2.myfirstgame.world.Player;

public class Renderer {

	private VaoModel blocksVao;
	private CubeMapShader cubeMapShader;
	private VaoModel playersVao; 
	private TexturedModelShader texturedModelShader;
	
	public Renderer(VaoModel blocksVao, CubeMapShader cubeMapShader, VaoModel playersVao, TexturedModelShader texturedModelShader) {
		this.blocksVao = blocksVao;
		this.cubeMapShader = cubeMapShader;
		this.playersVao = playersVao;
		this.texturedModelShader = texturedModelShader;
	}
	
	public void renderBlocks(Map<TextureCubeMap, List<BlockCube>> blocks) {
		this.cubeMapShader.start();
		this.blocksVao.bindVao();
		glEnableVertexAttribArray(0);
		glActiveTexture(GL_TEXTURE0);
		
		for(TextureCubeMap texture : blocks.keySet()) {
			glBindTexture(GL_TEXTURE_CUBE_MAP, texture.getID());
			
			for(BlockCube block : blocks.get(texture)) {
				this.cubeMapShader.loadTransformationMatrix(block.getTransformationMatrix());
				glDrawElements(GL_TRIANGLES, this.blocksVao.getNumberOfIndices(), GL_UNSIGNED_INT, 0);
			}
			
			glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
		}
		glDisableVertexAttribArray(0);
		this.blocksVao.unBindVao();
		this.cubeMapShader.stop();
	}
	
	public void renderPlayers(Map<Texture2D, List<Player>> players) {
		this.texturedModelShader.start();
		this.playersVao.bindVao();
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glActiveTexture(GL_TEXTURE0);
		
		for(Texture2D texture : players.keySet()) {
			glBindTexture(GL_TEXTURE_2D, texture.getID());
			
			for(Player player : players.get(texture)) {
				this.texturedModelShader.loadTransformationMatrix(player.getTransformationMatrix());
				glDrawElements(GL_TRIANGLES, this.playersVao.getNumberOfIndices(), GL_UNSIGNED_INT, 0);
			}
			
			glBindTexture(GL_TEXTURE_2D, 0);
		}
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		this.playersVao.unBindVao();
		this.texturedModelShader.stop();
	}
	
	public void loadViewMatrix(Camera camera) {
		this.cubeMapShader.loadViewMatrix(camera);
		this.texturedModelShader.loadViewMatrix(camera);
	}
	
	public void cleanUp() {
		this.blocksVao.cleanUp();
		this.cubeMapShader.cleanUp();
		this.texturedModelShader.cleanUp();
	}
}
