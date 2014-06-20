package gdg.ninja.util;

import gdg.nat.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;

// TODO: Done class
public class QuestGenerator{
	private Context context;
	
	private final String TSU = "っ";
	private final String IGNORE_TSU = "あいうえおやゆよアイウエオヤユヨ";
	private final String YAYUYO_STRING = "やゆよヤユヨ";
	private final String VALID_YAYUYO = "きしちにひみりぎじぢびぴきキシチニヒミリギジヂビピ";
	
	private Context getContext(){
		return context;
	}
	
	public QuestGenerator(Context context){
		this.context = context;
	}
	
	public List<String> getAnswer(String answer){
		// TODO: Refactoring.
		List<String> result = new ArrayList<String>();
		
		char[] answerCharArray = answer.toCharArray();
		
		// TODO: check for performance
		for(char x : answerCharArray){
			result.add(String.valueOf(x));
		}
		
		// int dataLength = answer.length();
		// for (int i = 0; i < dataLength; i++) {
		// result.add(answer.substring(i, i + 1));
		// }
		return result;
	}

	/**
	 * Generate quest item. If quest item generated is tsu, check any consonant
	 * item. If that is ya, yu or yo so that it need i columns character.
	 */
	public List<String> getQuest(String answer, int level){
		// TODO: Refactoring.
		// Get answer tag
		List<String> result = new ArrayList<String>();
		List<Integer> listIndex = new ArrayList<Integer>();
		Resources resources = getContext().getResources();
		String[] dict = resources.getStringArray(R.array.dict_ja);
		int answerLength = answer.length();
		int dictLength = dict.length;
		for(int i = 0; i < answerLength; i++){
			String subString = answer.substring(i, i + 1);
			result.add(subString);
			for(int j = 0; j < dictLength; j++){
				if(subString.equals(dict[j])){
					listIndex.add(j);
					break;
				}
			}
		}
		// Get quest tag
		int index = 0; // Index is a unique number for each question used for
						// generate frog tag
		for(Integer answerIndex : listIndex){
			index += answerIndex;
		}
		for(int i = 0; i < level; i++){
			boolean isGenerate = true;
			index = 2 * (index + i * 10) % dictLength;
			
			CHECK_IS_GENERATE: while(isGenerate){
				isGenerate = false;
				String dictItem = dict[index];
				// Check unique (not duplicate with the tag which already exist
				// in quest)
				for(String resultItem : result){
					if(resultItem.equals(dictItem)){
						isGenerate = true;
						continue CHECK_IS_GENERATE;
					}
				}

				if(dictItem.equals(TSU)){
					boolean isTsuValid = false;
					for(String item : result){
						if(!IGNORE_TSU.contains(item)){
							isTsuValid = true;
							break;
						}
					}
					if(!isTsuValid){
						isGenerate = true;
						continue CHECK_IS_GENERATE;
					}
				}

				// Check ya yu yo
				if(YAYUYO_STRING.contains(dictItem)){
					boolean isYAYUTOValid = false;
					for(String item : result){
						if(VALID_YAYUYO.contains(item)){
							isYAYUTOValid = true;
							break;
						}
					}
					if(!isYAYUTOValid) isGenerate = true;
				}
				index = (index + 2) % dictLength;
			}
			result.add(dict[index]);
		}
		// Sort
		Collections.sort(result, new Comparator<String>(){
			@Override
			public int compare(String lhs, String rhs){
				return lhs.compareTo(rhs);
			}
		});
		return result;
	}
}