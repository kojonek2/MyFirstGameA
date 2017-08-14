package pl.com.kojonek2.myfirstgame.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {
	
	private FileUtils() {
	}
	
	static String loadAsString(String path) {
		try  {
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
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
	
}
