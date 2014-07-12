package gdg.ninja.gameinfo;

import java.security.InvalidKeyException;

public class QuestInfo{
	private int questId;
	private String imgPath;
	private String answer;
	private int questStt;
	private String definition;
	
	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	private final int MAX_ANSWER_LENGTH = 20;
	
	public String getImgPath(){
		return imgPath;
	}
	
	public void setImgPath(String imgPath){
		this.imgPath = imgPath;
	}

	public int getQuestId(){
		return questId;
	}
	
	public void setQuestId(int questId){
		this.questId = questId;
	}
	
	public String getAnswer(){
		return answer;
	}
	
	public void setAnswer(String answer) throws InvalidKeyException{
		if(answer.length() > MAX_ANSWER_LENGTH)
			throw new InvalidKeyException("This answer \"" + answer
					+ "\" had length(" + answer.length() + ") max more than "
					+ MAX_ANSWER_LENGTH);
		this.answer = answer;
	}
	
	public int getQuestStt(){
		return questStt;
	}
	
	public void setQuestStt(int questStt){
		this.questStt = questStt;
	}
	
	public QuestInfo(){
		
	}

	public QuestInfo(int questId, String imgPath, String answer,
			String definition, int questStt) {
		this.questId = questId;
		this.imgPath = imgPath;
		this.answer = answer;
		this.questStt = questStt;
		this.definition = definition;
	}

}