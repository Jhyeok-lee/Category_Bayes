
public class Category<V> {

	String categoryName;
	Feature<V> features;
	
	Category() {
		newFeature();
	}
		
	void newFeature() {
		this.features = new Feature<V>();
	}
		
	void addFeature(V feature) {
		features.addFeature(feature);
	}
	
	int countTotalFeatures() {
		return this.features.countTotalFeatures();
	}
	
	int countFeature(V feature) {
		Integer count = this.features.countFeature(feature);
		return count==null ? 0 : count.intValue();
	}
}
