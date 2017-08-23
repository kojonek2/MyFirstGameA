package pl.com.kojonek2.myfirstgame.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;

import pl.com.kojonek2.myfirstgame.blocks.BlockCube;
import pl.com.kojonek2.myfirstgame.graphics.TextureCubeMap;
import pl.com.kojonek2.myfirstgame.set.Textures;

public class Chunk {
	
	//** position of block in the corner z- x- */
	private int xPosition, zPosition;
	private BlockCube[][][] blocks = new BlockCube[16][128][16];
	
	public Chunk(int xPosition, int zPosition) {
		if(xPosition % 16 != 0 || zPosition % 16 != 0) {
			System.err.println("Wrong cordinats of chunk");
			return;
		}
		this.xPosition = xPosition;
		this.zPosition = zPosition;
	}
	
	public void generateChunk() {
		for(int x = 0; x < 16; x++) {
			for(int y = 0; y < 3; y++) {
				for(int z = 0; z < 16; z++) {
					this.blocks[x][y][z] = new BlockCube(new Vector3f(x + this.xPosition, y, z + this.zPosition), Textures.TEST_TEXTURE);
				}
			}
		}
	}
	
	public Map<TextureCubeMap, List<BlockCube>> getBlocksToRender() {
		Map<TextureCubeMap, List<BlockCube>> result = new HashMap<>();
		for(int x = 0; x < 16; x++) {
			for(int y = 0; y < 3; y++) {
				for(int z = 0; z < 16; z++) {
					BlockCube block = this.blocks[x][y][z];
					if(block != null) {
						if(result.containsKey(block.getTexture())) {
							result.get(block.getTexture()).add(block);
						} else {
							List<BlockCube> list = new ArrayList<BlockCube>();
							list.add(block);
							result.put(block.getTexture(), list);
						}
					}
				}
			}
		}
		return result;
	}
}
