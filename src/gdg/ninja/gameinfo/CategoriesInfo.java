package gdg.ninja.gameinfo;

import java.util.ArrayList;
import java.util.List;

public class CategoriesInfo {
	private int cateId;
	private String imgPath;
	private String cateName;
	private String cateDesc;
	private int cateStt;
	private List<QuestInfo> listQuest;

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public int getCateId() {
		return cateId;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateDesc(String cateDesc) {
		this.cateDesc = cateDesc;
	}

	public String getCateDesc() {
		return cateDesc;
	}

	public void setStt(int stt) {
		this.cateStt = stt;
	}

	public int getStt() {
		return cateStt;
	}

	public void setListQuest(List<QuestInfo> listQuest) {
		this.listQuest = listQuest;
	}

	public List<QuestInfo> getListQuest() {
		return listQuest;
	}

	public void addListQuest(QuestInfo quest) {
		if (listQuest == null)
			return;
		this.listQuest.add(quest);
	}

	public void reCalculateStt(){
		int sumSttOfAllQuest = 0;
		for(QuestInfo x : listQuest){
			sumSttOfAllQuest += x.getQuestStt();
		}
		cateStt = sumSttOfAllQuest / listQuest.size();
	}

	public CategoriesInfo(String imgPath, String categoriesName,
			String categoriesDesc, int stt) {
		this.imgPath = imgPath;
		this.cateName = categoriesName;
		this.cateDesc = categoriesDesc;
		this.cateStt = stt;
		this.listQuest = new ArrayList<QuestInfo>();
	}

	public CategoriesInfo() {
	}

	public CategoriesInfo(int cateId, String imgPath, String categoriesName,
			String categoriesDesc, int stt) {
		this.cateId = cateId;
		this.imgPath = imgPath;
		this.cateName = categoriesName;
		this.cateDesc = categoriesDesc;
		this.cateStt = stt;
		this.listQuest = new ArrayList<QuestInfo>();
	}
}