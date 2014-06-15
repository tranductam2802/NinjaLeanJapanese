package gdg.ninja.util;

import gdg.nat.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;

// TODO: Done class
public class QuestGenerator {
	private Context context;

	private final String TSU = "‚Á";
	private final String A = "‚ ";
	private final String I = "‚¢";
	private final String U = "‚¤";
	private final String E = "‚¦";
	private final String O = "‚¨";
	private final String YA = "‚á";
	private final String YU = "‚ã";
	private final String YO = "‚å";
	private final String KI = "‚«";
	private final String SHI = "‚µ";
	private final String CHI = "‚¿";
	private final String NI = "‚É";
	private final String HI = "‚Ð";
	private final String MI = "‚Ý";
	private final String RI = "‚è";
	private final String GI = "‚¬";
	private final String JI = "‚¶";
	private final String DI = "‚À";
	private final String BI = "‚Ñ";
	private final String PI = "‚Ò";

	private Context getContext() {
		return context;
	}

	public QuestGenerator(Context context) {
		this.context = context;
	}

	public List<String> getAnswer(String answer) {
		// TODO: Refactoring.
		List<String> result = new ArrayList<String>();
		int dataLength = answer.length();
		for (int i = 0; i < dataLength; i++) {
			result.add(answer.substring(i, i + 1));
		}
		return result;
	}

	/**
	 * Generate quest item. If quest item generated is tsu, check any consonant
	 * item. If that is ya, yu or yo so that it need i columns character.
	 */
	public List<String> getQuest(String answer, int level) {
		// TODO: Refactoring.
		// Get answer tag
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
		// Get quest tag
		int index = 0;
		for (Integer answerIndex : listIndex) {
			index += answerIndex;
		}
		for (int i = 0; i < level; i++) {
			boolean isGenerate = true;
			index = 2 * (index + i * 10) % dictLength;
			while (isGenerate) {
				isGenerate = false;
				String dictItem = dict[index];
				// Check unique
				for (String resultItem : result) {
					if (resultItem.equals(dictItem))
						isGenerate = true;
				}
				// Check tsu
				// TODO; Thieu chu cung, thieu ya yu yo
				if (dict[index].equals(TSU)) {
					boolean isTsu = true;
					for (String item : result) {
						if (!item.equals(A) && !item.equals(I)
								&& !item.equals(U) && !item.equals(E)
								&& !item.equals(O)) {
							isTsu = false;
							break;
						}
					}
					if (isTsu)
						isGenerate = true;
				}
				// Check ya yu yo
				// TODO: thieu chu cung
				if (dict[index].equals(YA) || dict[index].equals(YU)
						|| dict[index].equals(YO)) {
					boolean isTsu = true;
					for (String item : result) {
						if (item.equals(KI) || item.equals(SHI)
								|| item.equals(CHI) || item.equals(NI)
								|| item.equals(HI) || item.equals(MI)
								|| item.equals(RI) || item.equals(GI)
								|| item.equals(JI) || item.equals(DI)
								|| item.equals(BI) || item.equals(PI)) {
							isTsu = false;
							break;
						}
					}
					if (isTsu)
						isGenerate = true;
				}
				index = (index + 2) % dictLength;
			}
			result.add(dict[index]);
		}
		// Sort
		Collections.sort(result, new Comparator<String>() {
			@Override
			public int compare(String lhs, String rhs) {
				return lhs.compareTo(rhs);
			}
		});
		return result;
	}
}