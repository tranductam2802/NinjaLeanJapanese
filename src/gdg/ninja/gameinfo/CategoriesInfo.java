package gdg.ninja.gameinfo;

import java.util.ArrayList;
import java.util.List;

public class CategoriesInfo{
	private int cateId;
	private int imgId;
	private String cateName;
	private String cateDesc;
	private int cateStt;
	private List<QuestInfo> listQuest;
	
	public void setCateId(int cateId){
		this.cateId = cateId;
	}
	
	public int getCateId(){
		return cateId;
	}
	
	public void setImgId(int imgId){
		this.imgId = imgId;
	}
	
	public int getImgId(){
		return imgId;
	}
	
	public void setCateName(String cateName){
		this.cateName = cateName;
	}
	
	public String getCateName(){
		return cateName;
	}
	
	public void setCateDesc(String cateDesc){
		this.cateDesc = cateDesc;
	}
	
	public String getCateDesc(){
		return cateDesc;
	}
	
	public void setStt(int stt){
		this.cateStt = stt;
	}
	
	public int getStt(){
		return cateStt;
	}
	
	public void setListQuest(List<QuestInfo> listQuest){
		this.listQuest = listQuest;
	}
	
	public List<QuestInfo> getListQuest(){
		return listQuest;
	}
	
	public void addListQuest(QuestInfo quest){
		if(listQuest == null) return;
		this.listQuest.add(quest);
	}
	
	public CategoriesInfo(int imgId, String categoriesName,
			String categoriesDesc, int stt){
		this.imgId = imgId;
		this.cateName = categoriesName;
		this.cateDesc = categoriesDesc;
		this.cateStt = stt;
		this.listQuest = new ArrayList<QuestInfo>();
	}
	
	public CategoriesInfo(int cateId, int imgId, String categoriesName,
			String categoriesDesc, int stt){
		this.cateId = cateId;
		this.imgId = imgId;
		this.cateName = categoriesName;
		this.cateDesc = categoriesDesc;
		this.cateStt = stt;
		this.listQuest = new ArrayList<QuestInfo>();
	}
}