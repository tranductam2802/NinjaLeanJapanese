package gdg.ninja.util;

import android.os.Bundle;

public class FacebookStoryBuilder{
	private final String KEY_TITLE = "name";
	private final String KEY_CAPTION = "caption";
	private final String KEY_DESC = "description";
	private final String KEY_LINK = "link";
	private final String KEY_PIC = "picture";
	
	private String title;
	private String caption;
	private String description;
	private String link;
	private String pictureLink;
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setCaption(String caption){
		this.caption = caption;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setLink(String link){
		this.link = link;
	}
	
	public void setPictureLink(String pictureLink){
		this.pictureLink = pictureLink;
	}
	
	public Bundle create(){
		Bundle postParams = new Bundle();
		if(title.length() > 0) postParams.putString(KEY_TITLE, title);
		if(caption.length() > 0) postParams.putString(KEY_CAPTION, caption);
		if(description.length() > 0)
			postParams.putString(KEY_DESC, description);
		if(link.length() > 0) postParams.putString(KEY_LINK, link);
		if(pictureLink.length() > 0)
			postParams.putString(KEY_PIC, pictureLink);
		return postParams;
	}
}