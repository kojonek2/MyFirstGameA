package pl.com.kojonek2.myfirstgame.set;

import pl.com.kojonek2.myfirstgame.graphics.Texture2D;
import pl.com.kojonek2.myfirstgame.graphics.TextureCubeMap;

public class Textures {

		public static final TextureCubeMap TEST_TEXTURE_0 = new TextureCubeMap("textures/test_texture.png");
		public static final TextureCubeMap TEST_TEXTURE_1 = new TextureCubeMap("textures/test.png", "textures/test.png", "textures/test.png", "textures/test.png", "textures/test.png", "textures/test.png");
	
		public static final Texture2D PLAYER_TEXTURE = new Texture2D("textures/steve.png");
		
		private Textures() {
		}
}
