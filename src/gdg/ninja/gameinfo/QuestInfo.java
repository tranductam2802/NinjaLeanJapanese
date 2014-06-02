package gdg.ninja.gameinfo;

public class QuestInfo {
	private int questId;
	private int imgId;
	private String answer;
	private int questStt;

	public int getQuestId() {
		return questId;
	}

	public void setQuestId(int questId) {
		this.questId = questId;
	}

	public int getImgId() {
		return imgId;
	}

	public void setImgId(int imgId) {
		this.imgId = imgId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public int getQuestStt() {
		return questStt;
	}

	public void setQuestStt(int questStt) {
		this.questStt = questStt;
	}

	public QuestInfo(int questId, int imgId, String answer, int questStt) {
		this.questId = questId;
		this.imgId = imgId;
		this.answer = answer;
		this.questStt = questStt;
	}
}