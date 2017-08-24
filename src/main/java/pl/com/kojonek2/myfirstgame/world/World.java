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

	public void test() {
		Chunk chunk = new Chunk(new Vector3i(0, 0, -16));
		chunk.generateChunk(3);
		this.loadedChunks.put(new Vector3i(0, 0, -16), chunk);
		
		Chunk chunk1 = new Chunk(new Vector3i(0, 0, 0));
		chunk1.generateChunk(2);
		this.loadedChunks.put(new Vector3i(0, 0, 0), chunk1);
	}
}
