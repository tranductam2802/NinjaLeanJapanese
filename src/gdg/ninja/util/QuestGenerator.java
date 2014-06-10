package gdg.ninja.util;

import gdg.nat.R;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;

public class QuestGenerator {
	private Context context;

	private Context getContext() {
		return context;
	}

	public QuestGenerator(Context context) {
		this.context = context;
	}

	public boolean isValidAnswer(String answer) {
		Resources resources = getContext().getResources();
		int maxAnswer = resources.getInteger(R.integer.max_number_answer);
		int length = answer.length();
		return (length < 0 || length > maxAnswer) ? false : true;
	}

	public List<String> getAnswer(String answer) {
		List<String> result = new ArrayList<String>();
		int dataLength = answer.length();
		for (int i = 0; i < dataLength; i++) {
			result.add(answer.substring(i, i + 1));
		}
		return result;
	}

	public List<String> getQuest(String answer, int level) {
		List<String> result = new ArrayList<String>();
		List<Integer> listIndex = new ArrayList<Integer>();
		Resources resources = getContext().getResources();
		String[] dict = resources.getStringArray(R.array.dict_ja);
		int answerLength = answer.length();
		int dictLength = dict.length;
		for (int i = 0; i < answerLength; i++) {
			String subString = answer.substring(i, i + 1);
			result.add(subString);
			for (int j = 0; j < dictLength; j++) {
				if (subString.equals(dict[j])) {
					listIndex.add(j);
					break;
				}
			}
		}
		int maxQuest = resources.getInteger(R.integer.max_number_quest);
		if (level < 1)
			level = 1;
		if (level > maxQuest - answerLength)
			level = maxQuest - answerLength;
		int index = 0;
		for (Integer answerIndex : listIndex) {
			index += answerIndex;
		}
		for (int i = 0; i < level; i++) {
			boolean isGenerate = true;
			while (isGenerate) {
				isGenerate = false;
				index = 2 * (index + i * 10) % dictLength;
				String dictItem = dict[index];
				for (String resultItem : result) {
					if (resultItem.equals(dictItem))
						isGenerate = true;
				}
			}
			result.add(dict[index]);
		}
		return result;
	}
}