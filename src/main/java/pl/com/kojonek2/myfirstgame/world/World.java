package pl.com.kojonek2.myfirstgame.world;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Vector3f;
import org.joml.Vector3i;

import pl.com.kojonek2.myfirstgame.blocks.BlockCube;
import pl.com.kojonek2.myfirstgame.collision.RayCast;
import pl.com.kojonek2.myfirstgame.collision.ProbableRayCollision;
import pl.com.kojonek2.myfirstgame.graphics.TextureCubeMap;
import pl.com.kojonek2.myfirstgame.util.CollisionUtils;
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
	 * Returns null when block is not loaded or block doeasn't exist!
	 * 
	 * @return block at given positiond
	 */
	public BlockCube getBlock(int x, int y, int z) {
		if(y < 0 || y > 127) {
//			System.err.println("World : getBlock - requested block with wrong cordinats");
//			System.exit(-1);
			return null;
		}
		
		int blockX = x % 16;
		int blockZ = z % 16;
		if(blockX < 0) {
			blockX += 16; //Because -5 % 16 gives -5, but I want to get 11
		}
		if(blockZ < 0) {
			blockZ += 16;
		}
		
		int chunkX = x - blockX;
		int chunkZ = z - blockZ;
		Vector3i chunkPosition = new Vector3i(chunkX, 0, chunkZ);
		if(!this.loadedChunks.containsKey(chunkPosition)) {
			return null;
		}
		return this.loadedChunks.get(chunkPosition).getBlock(blockX, y, blockZ);
	}
	
	public BlockCube getBlock(Vector3i position) {
		return this.getBlock(position.x, position.y, position.z);
	}
	
	//** returns null when no block is found! */ 
	public BlockCube getBlockPointedByRay(RayCast ray) {
		int length = (int) Math.ceil(ray.getLength());
		Vector3f rayStart = ray.getStartPoint();
		Vector3i center = new Vector3i(Math.round(rayStart.x), (int) Math.floor(rayStart.y), Math.round(rayStart.z));
		float record = Float.MAX_VALUE;
		BlockCube recordBlock = null;
		
		Vector3f boxMinCorner = new Vector3f();
		Vector3f boxMaxCorner = new Vector3f();
		
		for(int xOffset = -length; xOffset <= length; xOffset++) {
			for(int yOffset = -length; yOffset <= length; yOffset++) {
				for(int zOffset = -length; zOffset <= length; zOffset++) {
					boxMinCorner.set(center.x + xOffset - 0.5f, center.y + yOffset, center.z + zOffset - 0.5f);
					boxMaxCorner.set(center.x + xOffset + 0.5f, center.y + yOffset + 1f, center.z + zOffset + 0.5f);
					ProbableRayCollision collision = CollisionUtils.getRayAndBoxCollision(boxMinCorner, boxMaxCorner, ray);
					if(collision.isColliding()) {
						BlockCube block = this.getBlock(center.x + xOffset, center.y + yOffset, center.z + zOffset);
						if(block != null && block.isSolid()) {
							float dist = rayStart.distanceSquared(collision.getCollisionPoint());
							if(dist < record) {
								record = dist;
								recordBlock = block;
							}
						}
						
					}
					
				}
			}
		}
		return recordBlock;
	}
	
	/** returns null when no side it hit */
	public Direction getHitSide(BlockCube block, RayCast ray) {
		Vector3i blockPosition = block.getPosition(); 
		Vector3f boxMinCorner = new Vector3f(blockPosition.x - 0.5f, blockPosition.y, blockPosition.z - 0.5f);
		Vector3f boxMaxCorner = new Vector3f(blockPosition.x + 0.5f, blockPosition.y + 1f, blockPosition.z + 0.5f);
		ProbableRayCollision collision = CollisionUtils.getRayAndBoxCollision(boxMinCorner, boxMaxCorner, ray);
		
		if(!collision.isColliding()) {
			System.err.println("World:getHitSide -- none of the sides are hit");
			return null;
		}
		
		if(collision.getCollisionPoint().x < blockPosition.x + Direction.WEST.getX() * 0.5 + 0.001f &&
				collision.getCollisionPoint().x > blockPosition.x + Direction.WEST.getX() * 0.5 - 0.001f) {
			return Direction.WEST;
		}
		if(collision.getCollisionPoint().x < blockPosition.x + Direction.EAST.getX() * 0.5 + 0.001f &&
				collision.getCollisionPoint().x > blockPosition.x + Direction.EAST.getX() * 0.5 - 0.001f) {
			return Direction.EAST;
		}
		if(collision.getCollisionPoint().z < blockPosition.z + Direction.NORTH.getZ() * 0.5 + 0.001f &&
				collision.getCollisionPoint().z > blockPosition.z + Direction.NORTH.getZ() * 0.5 - 0.001f) {
			return Direction.NORTH;
		}
		if(collision.getCollisionPoint().z < blockPosition.z + Direction.SOUTH.getZ() * 0.5 + 0.001f &&
				collision.getCollisionPoint().z > blockPosition.z + Direction.SOUTH.getZ() * 0.5 - 0.001f) {
			return Direction.SOUTH;
		}
		if(collision.getCollisionPoint().y < blockPosition.y + Direction.UP.getY() + 0.001f &&
				collision.getCollisionPoint().y > blockPosition.y + Direction.UP.getY() - 0.001f) {
			return Direction.UP;
		}
		if(collision.getCollisionPoint().y < blockPosition.y + 0.001f &&
				collision.getCollisionPoint().y > blockPosition.y - 0.001f) {
			return Direction.DOWN;
		}
		System.err.println("World:getHitSide -- none of the sides are hit");
		System.exit(-1);
		return null;
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
