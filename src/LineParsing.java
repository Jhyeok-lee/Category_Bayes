import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class LineParsing {
	
	List<String> parse;
	Integer mallNum;
	Integer category;
	
	LineParsing(String line) {
		String str = StringUtils.remove(line, "[");
		str = StringUtils.remove(str, "]");
		str = StringUtils.remove(str, "/");
		str = StringUtils.remove(str, ">");
		str = StringUtils.remove(str, "-");
		String s[] = StringUtils.split(str);
		category = new Integer(s[1]);
		mallNum = new Integer(s[2]);
		parse = Arrays.asList(s);
	}
	
	/*
	LineParsing(String line) {
		List<String> t1 = Arrays.asList(StringUtils.split(line));
		ArrayList<String> t2 = new ArrayList<String>();
		t2.addAll(t1);
		parse = new ArrayList<String>();
		for(int i=0; i<t2.size()-1; i++) {
			String s = t2.get(i);
			s = StringUtils.remove(s, "/Noun");
			s = StringUtils.remove(s, "/Number");
			s = StringUtils.remove(s, "/Alpha");
			parse.add(s);
		}
		String c = t2.get(t2.size()-1);
		c = StringUtils.remove(c, "/Number");
		String s[] = StringUtils.split(c, ',');
		category = new Integer( s[1] );
	}
	*/
	
	
	Integer getCategory() {
		return category;
	}
	
	Integer getMallNum() {
		return mallNum;
	}
	
	ArrayList<String> getFeatures() {
		ArrayList<String> ret = new ArrayList<String>();
		for(String feature : parse) {
			if( StringUtils.isNumeric(feature) == false ) {
				ret.add(feature);
			}
		}
		return ret;
	}
}
