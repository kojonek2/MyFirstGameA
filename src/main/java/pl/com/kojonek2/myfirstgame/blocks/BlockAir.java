package pl.com.kojonek2.myfirstgame.blocks;

import org.joml.Vector3i;

public class BlockAir extends BlockCube {

	public BlockAir(Vector3i position) {
		super(position, null);
	}
	
	@Override
	public boolean shouldBeRendered() {
		return false;
	}

	@Override
	public boolean isSolid() {
		return false;
	}
	
}
