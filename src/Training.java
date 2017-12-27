import java.util.HashMap;

public class Training<K,V> implements Runnable {
	
	BayesModel<K,V> model;
	HashMap<K, V> categories;
	HashMap<K, V> features;
	
	Training(BayesModel<K,V> model, HashMap<K,V> categories,
			HashMap<K,V> features) {
		this.model = model;
		this.categories = categories;
		this.features = features;
	}
		
	void learn() {
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}