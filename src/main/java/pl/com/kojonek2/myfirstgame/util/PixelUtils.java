package pl.com.kojonek2.myfirstgame.util;

public class PixelUtils {

	private PixelUtils() {
	}

	public static int[] convertARGBToRGBA(int[] pixels) {
		int[] data = new int[pixels.length];
		for(int i = 0; i < pixels.length; i++) {
			int alpha = (pixels[i] & 0xff000000) >> 24;
			int red = (pixels[i] & 0xff0000) >> 16;
			int green = (pixels[i] & 0xff00) >> 8;
			int blue = (pixels[i] & 0xff);
			
			data[i] = alpha << 24 | blue << 16 | green << 8 | red;
		}
		return data;
	}
}
