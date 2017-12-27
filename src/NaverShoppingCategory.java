import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class NaverShoppingCategory {

	final static String trainingProductPath = "data_product_100000.dms";
	final static String validationPath = "data_product_100000.dms";
	//final static String trainingProductPath = "test.txt";
	//final static String validationPath = "test.txt";
	final static String trainingCategoryPath = "data_category.dms";
	final String classifyPath = "";
	static BayesModel<String, String> model;
	static HashMap<Integer, String> categories;
	
	static void init() {
		model = new BayesModel<String, String>();
	}
	
	static void training(int numThreads) throws IOException {
		ArrayList<String> featureLines = new ArrayList<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(trainingProductPath));
			String line = "";
			while( line != null ) {
				line = br.readLine();
				if( line == null )
					break;
				featureLines.add(line);
			}
			br.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		categories = new HashMap<Integer, String>();
		try {
			br = new BufferedReader(new FileReader(trainingCategoryPath));
			String line = "";
			while( line != null ) {
				line = br.readLine();
				if( line == null )
					break;
				String[] s = line.split("\t");
				categories.put(new Integer(s[0]), s[1]);
			}
			br.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		for(int i=0; i<featureLines.size()*0.9; i++) {
			LineParsing l = new LineParsing(featureLines.get(i));
			int categoryNum = l.getCategory();
			ArrayList<String> features = l.getFeatures();
			String category = categories.get(categoryNum);
			model.addFeatures(category, features);
		}
	}
	
	static void classify() throws IOException {
		ArrayList<String> featureLines = new ArrayList<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(validationPath));
			String line = "";
			while( line != null ) {
				line = br.readLine();
				if( line == null )
					break;
				featureLines.add(line);
			}
			br.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int correct = 0;
		for(int i=(int) (featureLines.size()*0.9); i<featureLines.size(); i++) {
			LineParsing l = new LineParsing(featureLines.get(i));
			int realCategory = l.getCategory();
			ArrayList<String> features = l.getFeatures();
			List<String> categoryCand = model.getCategories();
			List<Prob> prob = new ArrayList<Prob>();
			for(String category : categoryCand) {
				float p = model.probFeaturesPerCategory(category, features);
				prob.add(new Prob(category,p));
			}
			Collections.sort(prob, new Comparator<Prob>() {

				@Override
				public int compare(Prob o1, Prob o2) {
					if( o1.p > o2.p ) {
						return 1;
					}
					else if( o1.p < o2.p ) {
						return -1;
					}
					return 0;
				}
			});
			Collections.reverse(prob);
			
			if(categories.get(realCategory) == prob.get(0).category )
				correct++;
			
			System.out.println(i + " " + "real : " + categories.get(realCategory)
					+ " " + "predict : " + prob.get(0).category + " " + prob.get(0).p);
		}
		
		System.out.println("Accuracy : " + (float)correct/((float)featureLines.size()*0.1));
		System.out.println(model.countAllFeatures());
	}
	
	public static void main(String[] args) throws IOException {
		init();
		training(1);
		classify();
	}
}

class Prob {
	String category;
	float p;
	
	Prob(String category, float p) {
		this.category = category;
		this.p = p;
	}
}
