package pl.com.kojonek2.myfirstgame.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapUtils {

	private MapUtils(){
	}
	
	public static <K, V>  Map<K, List<V>> mergeMaps(Map<K, List<V>> destination, Map<K, List<V>> mapToMerge) {
		for(K k : mapToMerge.keySet()) {
			List<V> list;
			if(destination.containsKey(k)) {
				list = destination.get(k);
			} else {
				list = new ArrayList<>();
				destination.put(k, list);
			}
			for(V v : mapToMerge.get(k)) {
				list.add(v);
			}
		}
		return destination;
	}
}
