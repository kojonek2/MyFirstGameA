package pl.com.kojonek2.myfirstgame.world;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3i;

import pl.com.kojonek2.myfirstgame.blocks.BlockCube;
import pl.com.kojonek2.myfirstgame.graphics.TextureCubeMap;
import pl.com.kojonek2.myfirstgame.util.MapUtils;

public class World {
	
	private Map<Vector3i, Chunk> loadedChunks = new HashMap<>();
	
	public Map<TextureCubeMap, List<BlockCube>> getBlocksToRender() {
		Map<TextureCubeMap, List<BlockCube>> result = new HashMap<>();
		this.loadedChunks.forEach((Vector3i position, Chunk chunk) -> {
			Map<TextureCubeMap, List<BlockCube>> partialResult = chunk.getBlocksToRender();
			MapUtils.mergeMaps(result, partialResult);
		});
		return result;
	}
	
	/** 
	 * Returns null when block is not loaded!
	 * 
	 * @return block at given position
	 */
	public BlockCube getBlock(Vector3i position) {
		if(position.y < 0 && position.y > 128) {
			System.err.println("World : getBlock - requested block with wrong cordinats");
			System.exit(-1);
		}
		
		int blockX = position.x % 16;
		int blockZ = position.z % 16;
		if(blockX < 0) {
			blockX += 16; //Because -5 % 16 gives -5, but I want to get 11
		}
		if(blockZ < 0) {
			blockZ += 16;
		}
		
		int chunkX = position.x - blockX;
		int chunkZ = position.z - blockZ;
		Vector3i chunkPosition = new Vector3i(chunkX, 0, chunkZ);
		if(!this.loadedChunks.containsKey(chunkPosition)) {
			return null;
		}
		
		return this.loadedChunks.get(chunkPosition).getBlock(blockX, position.y, blockZ);
	}
	
	public void test() {
		Chunk chunk = new Chunk(this , new Vector3i(0, 0, -16));
		chunk.generateChunk(3);
		this.loadedChunks.put(new Vector3i(0, 0, -16), chunk);
		
		Chunk chunk1 = new Chunk(this, new Vector3i(0, 0, 0));
		chunk1.generateChunk(2);
		this.loadedChunks.put(new Vector3i(0, 0, 0), chunk1);
	}
}
