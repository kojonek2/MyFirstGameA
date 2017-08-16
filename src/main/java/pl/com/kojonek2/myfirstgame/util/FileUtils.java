package pl.com.kojonek2.myfirstgame.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import pl.com.kojonek2.myfirstgame.Main;

public class FileUtils {
	
	private FileUtils() {
	}
	
	/**
	 * 
	 * @param path path inside resources folder
	 * @return File content
	 */
	public static String loadAsString(String path) {
		try  {
			BufferedReader reader = new BufferedReader(new FileReader(getFile(path)));
			StringBuilder builder = new StringBuilder();
			String buffer = "";
			
			while((buffer = reader.readLine()) != null) {
				builder.append(buffer);
				builder.append("\n");
			}
			
			reader.close();
			return builder.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}	
	
	public static File getFile(String path) {
		ClassLoader classLoader = Main.class.getClassLoader();
		return new File(classLoader.getResource(path).getFile());
	}
}
