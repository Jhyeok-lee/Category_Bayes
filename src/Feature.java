import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class Feature<V> {
	
	ConcurrentHashMap<V, Integer> features;
	
	Feature() {
		newFeature();
	}
	
	void newFeature() {
		features = new ConcurrentHashMap<V, Integer>();
	}
	
	void addFeature(V feature) {
		Integer count = features.get(feature);
		if( count == null ) {
			features.put(feature, 0);
			count = features.get(feature);
		}
		features.put(feature, count+1);
	}
	
	void addFeatures(Collection<V> features) {
		for(V feature : features) {
			addFeature(feature);
		}
	}
	
	int countTotalFeatures() {
		return this.features.size();
	}
	
	int countFeature(V feature) {
		Integer count = features.get(feature);
		return count==null ? 0 : count.intValue();
	}
}
