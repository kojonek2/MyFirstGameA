package pl.com.kojonek2.myfirstgame.blocks;

import org.joml.Vector3f;

public class BlockAir extends BlockCube {

	public BlockAir(Vector3f position) {
		super(position, null);
	}
	
	@Override
	public boolean shouldBeRendered() {
		return false;
	}

}
