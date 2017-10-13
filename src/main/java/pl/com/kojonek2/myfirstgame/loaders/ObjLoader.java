package pl.com.kojonek2.myfirstgame.loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.mokiat.data.front.parser.IOBJParser;
import com.mokiat.data.front.parser.OBJDataReference;
import com.mokiat.data.front.parser.OBJFace;
import com.mokiat.data.front.parser.OBJMesh;
import com.mokiat.data.front.parser.OBJModel;
import com.mokiat.data.front.parser.OBJNormal;
import com.mokiat.data.front.parser.OBJObject;
import com.mokiat.data.front.parser.OBJParser;
import com.mokiat.data.front.parser.OBJTexCoord;
import com.mokiat.data.front.parser.OBJVertex;

import pl.com.kojonek2.myfirstgame.util.FileUtils;

public class ObjLoader {

	private List<Float> vertices = new ArrayList<>();
	private List<Integer> indices = new ArrayList<>();
	private List<Float> textureCordinats = new ArrayList<>();
	
	public ObjLoader(String filePath) {
		if(!filePath.endsWith(".obj")) {
			System.err.println("ObjLoader - invalid extension of file: " + filePath);
			return;
		}		
		File file = FileUtils.getFile(filePath);
		try(InputStream in = new FileInputStream(file)) {
			IOBJParser parser = new OBJParser();
			OBJModel model = parser.parse(in);
			
			List<OBJVertex> verticesTemp = new ArrayList<>();
			int test = 0;
			
			for(OBJObject object : model.getObjects()) {
				for(OBJMesh mesh : object.getMeshes()) {
					for(OBJFace face : mesh.getFaces()) {
						
						if(face.getReferences().size() != 3) {
							System.err.println("ObjLoader invalid number of vertices for face. It should have 3 vertices");
							return;
						}
						
						for(OBJDataReference reference : face.getReferences()) {
							OBJVertex vertex = model.getVertex(reference);
							OBJNormal normal = model.getNormal(reference);
							OBJTexCoord textureCords = model.getTexCoord(reference);
							int indexInList = verticesTemp.indexOf(vertex);
				
							if (indexInList != -1 && textureCords.u == this.textureCordinats.get(indexInList * 2) && textureCords.v == this.textureCordinats.get(indexInList * 2 + 1)) {
								this.indices.add(indexInList);
							} else {
								verticesTemp.add(vertex);
								this.vertices.add(vertex.x);
								this.vertices.add(vertex.y);
								this.vertices.add(vertex.z);
								this.indices.add(verticesTemp.size() - 1);
								this.textureCordinats.add(textureCords.u);
								this.textureCordinats.add(1 - textureCords.v);
							}
						}
					}
				}
			}
			System.out.println("test " + test);
			System.out.println("test2 " + verticesTemp.size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public float[] getVertices() {
		float[] array = new float[this.vertices.size()];
		for(int i = 0; i < this.vertices.size(); i++) {
			array[i] = this.vertices.get(i);
		}
		return array;
	}
	
	public int[] getIndices() {
		int[] array = new int[this.indices.size()];
		for(int i = 0; i < this.indices.size(); i++) {
			array[i] = this.indices.get(i);
		}
		return array;
	}
	
	public float[] getTextureCordinates() {
		float[] array = new float[this.textureCordinats.size()];
		for(int i = 0; i < this.textureCordinats.size(); i++) {
			array[i] = this.textureCordinats.get(i);
		}
		return array;
	}
}

