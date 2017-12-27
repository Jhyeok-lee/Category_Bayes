import java.util.ArrayList;
import java.util.List;

public class Classify implements Runnable {
	
	BayesModel<String, String> model;
	ArrayList<String> data;
	
	Classify(BayesModel<String,String> model, ArrayList<String> data) {
		this.model = model;
		this.data = data;
	}
	
	void classify() {
		
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
