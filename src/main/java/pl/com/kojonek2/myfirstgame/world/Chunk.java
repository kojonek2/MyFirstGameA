package pl.com.kojonek2.myfirstgame.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.joml.Vector3i;

import pl.com.kojonek2.myfirstgame.blocks.BlockAir;
import pl.com.kojonek2.myfirstgame.blocks.BlockCube;
import pl.com.kojonek2.myfirstgame.graphics.TextureCubeMap;
import pl.com.kojonek2.myfirstgame.set.Textures;

public class Chunk {
	
	private World world;
	
	//ignore y 
	//** position of block in the corner z- x- */
	private Vector3i position;
	private BlockCube[][][] blocks = new BlockCube[16][128][16];
	private Map<TextureCubeMap, List<BlockCube>> blocksToRender; //TODO: refresh list when blocks are added or removed from the world
	
	public Chunk(World world, Vector3i position) {
		if(position.x % 16 != 0 || position.y % 16 != 0) {
			System.err.println("Wrong cordinats of chunk");
			System.exit(-1);
		}
		this.world = world;
		this.position = position;
	}
	
	public void generateChunk(int height) {
		for(int x = 0; x < 16; x++) {
			for(int y = 0; y < 128; y++) {
				for(int z = 0; z < 16; z++) {
					if(y < height) {
						int random = ThreadLocalRandom.current().nextInt(2);
						if(random == 0) {
							this.blocks[x][y][z] = new BlockCube(new Vector3i(x + this.position.x, y, z + this.position.z), Textures.TEST_TEXTURE_0);
						} else {
							this.blocks[x][y][z] = new BlockCube(new Vector3i(x + this.position.x, y, z + this.position.z), Textures.TEST_TEXTURE_1);
						}
					} else {
						this.blocks[x][y][z] = new BlockAir(new Vector3i(x + this.position.x, y, z + this.position.z));
					}
				}
			}
		}
	}
	
	public BlockCube getBlock(int x, int y, int z) {
		if(x < 0 && x > 16 && y < 0 && y > 127 && z < 0 && z > 16) {
			System.err.println("requested block with wrong cordinats in chunk x: " + this.position.x + " z: " + this.position.z);
			System.exit(-1);
		}
		return this.blocks[x][y][z];
	}
	
	private void fillBlocksToRender() {
		this.blocksToRender = new HashMap<>();
		for(int x = 0; x < 16; x++) {
			for(int y = 0; y < 128; y++) {
				for(int z = 0; z < 16; z++) {
					BlockCube block = this.blocks[x][y][z];
					if(block != null && block.shouldBeRendered() && this.shouldBlockBeRendered(x, y, z)) {
						if(this.blocksToRender.containsKey(block.getTexture())) {
							this.blocksToRender.get(block.getTexture()).add(block);
						} else {
							List<BlockCube> list = new ArrayList<BlockCube>();
							list.add(block);
							this.blocksToRender.put(block.getTexture(), list);
						}
					}
				}
			}
		}
	}
	
	private boolean shouldBlockBeRendered(int x, int y, int z) {
		if(y >= 128 || y <= 0) {
			return true;
		}
		for(Direction direction : Direction.getAll()) {
			int neighbourX = x + direction.getX();
			int neighbourY = y + direction.getY();
			int neighbourZ = z + direction.getZ();
			if(neighbourX < 0 || neighbourX > 15 || neighbourZ < 0 || neighbourZ > 15) {
				BlockCube neighbour = this.world.getBlock(new Vector3i(this.position.x + neighbourX, neighbourY, this.position.z + neighbourZ));
				if(neighbour == null || !neighbour.shouldBeRendered()) {
					return true;
				}
			} else if(!this.blocks[neighbourX][neighbourY][neighbourZ].shouldBeRendered()) {
				return true;
			}
		}
		return false;
	}
	
	public Map<TextureCubeMap, List<BlockCube>> getBlocksToRender() {
		if(this.blocksToRender == null) {
			fillBlocksToRender();
		}
		return this.blocksToRender;
	}
}
