import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class BayesModel<K,V> {
	
	ConcurrentHashMap<K, Category<V>> categories;
	Feature<V> allFeatures;
	
	BayesModel() {
		newCategories();
		newAllFeatures();
	}
	
	void newCategories() {
		this.categories = new ConcurrentHashMap<K, Category<V>>();
	}
	
	void newAllFeatures() {
		this.allFeatures = new Feature<V>();
	}
	
	List<K> getCategories() {
		return Collections.list(categories.keys());
	}
	
	void addFeatureAndCategory(K category, V feature) {
		Category<V> cate = categories.get(category);
		if( cate == null ) {
			cate = new Category<V>();
		}
		cate.addFeature(feature);
		categories.put(category, cate);
	}
	
	void addFeatures(K category, Collection<V> features) {
		for(V feature : features) {
			allFeatures.addFeature(feature);
			addFeatureAndCategory(category, feature);
		}
	}
	
	int countFeaturePerCategory(K category, V feature) {
		return categories.get(category).countFeature(feature);
	}
	
	int countTotalFeaturePerCategory(K category) {
		return categories.get(category).countTotalFeatures();
	}
	
	int countTotalCategories() {
		return categories.size();
	}
	
	int countAllFeatures() {
		return allFeatures.countTotalFeatures();
	}
	
	float probCategory(K category) {
		float a = (float)countTotalFeaturePerCategory(category);
		float b = (float)countAllFeatures();
		return a/b;
	}
	
	float probFeaturePerCategory(K category, V feature, float d) {
		float a = (float)countFeaturePerCategory(category,feature) + 0.0001f;
		float b = (float)countTotalFeaturePerCategory(category) + 0.0001f*d;
		return a/b;
	}
	
	float probFeaturesPerCategory(K category, Collection<V> features) {
		int d = features.size();
		float ret = 1.0f;
		for(V feature : features) {
			ret *= probFeaturePerCategory(category, feature, (float)d);
		}
		return ret;
	}
}
