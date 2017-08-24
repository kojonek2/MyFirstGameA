package pl.com.kojonek2.myfirstgame.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.joml.Vector3f;
import org.joml.Vector3i;

import pl.com.kojonek2.myfirstgame.blocks.BlockCube;
import pl.com.kojonek2.myfirstgame.graphics.TextureCubeMap;
import pl.com.kojonek2.myfirstgame.set.Textures;

public class Chunk {
	
	//** position of block in the corner z- x- */
	//ignore y 
	private Vector3i position;
	private BlockCube[][][] blocks = new BlockCube[16][128][16];
	
	public Chunk(Vector3i position) {
		if(position.x % 16 != 0 || position.y % 16 != 0) {
			System.err.println("Wrong cordinats of chunk");
			System.exit(-1);
		}
		this.position = position;
	}
	
	public void generateChunk(int height) {
		for(int x = 0; x < 16; x++) {
			for(int y = 0; y < height; y++) {
				for(int z = 0; z < 16; z++) {
					int random = ThreadLocalRandom.current().nextInt(2);
					if(random == 0) {
						this.blocks[x][y][z] = new BlockCube(new Vector3f(x + this.position.x, y, z + this.position.z), Textures.TEST_TEXTURE_0);
					} else {
						this.blocks[x][y][z] = new BlockCube(new Vector3f(x + this.position.x, y, z + this.position.z), Textures.TEST_TEXTURE_1);
					}
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
