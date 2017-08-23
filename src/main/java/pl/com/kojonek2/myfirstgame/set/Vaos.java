package pl.com.kojonek2.myfirstgame.set;

import pl.com.kojonek2.myfirstgame.VaoModel;

public class Vaos {
	
	private static final float[] cubeVertices = new float[] {
			-0.5f, 1f, 0.5f,  //0
			-0.5f, 0f, 0.5f, //1
			0.5f, 0f, 0.5f,  //2
			0.5f, 1f, 0.5f,   //3
			-0.5f, 1f, -0.5f,  //4
			-0.5f, 0f, -0.5f, //5
			0.5f, 0f, -0.5f,  //6
			0.5f, 1f, -0.5f,   //7
	};
	private static final int[] cubeIndices = new int[] {
			0, 1, 3,
			3, 1, 2,
			0, 4, 1,
			4, 5, 1,
			3, 6, 7,
			3, 2, 6,
			4, 7, 5,
			7, 6, 5,
			0, 7, 4,
			0, 3, 7,
			1, 5, 6,
			1, 6, 2
	};

	public static final VaoModel CUBE_VAO = new VaoModel(cubeVertices, cubeIndices);
	
	private Vaos(){
	}
}
