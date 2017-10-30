package pl.com.kojonek2.myfirstgame.world;

public enum Direction {
	
	UP(0, 1, 0),
	DOWN(0, -1, 0),
	WEST(-1, 0, 0),
	EAST(1, 0, 0),
	NORTH(0, 0, -1),
	SOUTH(0, 0, 1);
	
	
	private int x, y, z;
	
	private Direction(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getZ() {
		return this.z;
	}
	
	public static Direction[] getAll() {
		return new Direction[]{UP, DOWN, WEST, EAST, NORTH, SOUTH};
	}
}
