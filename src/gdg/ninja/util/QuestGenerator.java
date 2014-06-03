package gdg.ninja.util;

import gdg.nat.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QuestGenerator {
	private HashMap<String, String> mDict = new HashMap<String, String>();

	public QuestGenerator() {
		String[] en = App.getContext().getResources()
				.getStringArray(R.array.dict_en);
		String[] ja = App.getContext().getResources()
				.getStringArray(R.array.dict_ja);
		int length = en.length;
		for (int i = 0; i < length; i++) {
			mDict.put(en[i], ja[i]);
		}
	}

	public List<String> getAnswer(String rawData) {
		int length = rawData.length();
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length - i; j++) {
				String subString = rawData.substring(i, i + j);
				if (mDict.containsKey(subString)) {
					result.add(mDict.get(subString));
					break;
				}
			}
		}
		return result;
	}
}