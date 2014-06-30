package gdg.ninja.database;

import gdg.nat.R;
import gdg.ninja.gameinfo.CategoriesInfo;
import gdg.ninja.gameinfo.QuestInfo;
import gdg.ninja.util.NLog;

import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "NinjaDB";
	
	private static final String TABLE_CATEGORY = "CATEGORY";
	private static final String TABLE_QUESTION = "QUESTION";
	
	// Column for CATEGORY table
	private static final String CAT_STT = "CatStt";
	private static final String CAT_DES = "CatDes";
	private static final String CAT_NAME = "CatName";
	private static final String CAT_ID = "CatID";
	private static final String CAT_IMG_PATH = "CatImgPath";
	
	// Column for QUESTION table
	private static final String QUEST_STT = "QuestStt";
	private static final String QUEST_KEY = "QuestKey";
	private static final String QUEST_ID = "QuestId";
	private static final String QUEST_IMG_PATH = "QuestImgPath";
	
	// Create CATEGORY table
	private static final String CREATE_CATEGORY_TABLE = "CREATE TABLE "
			+ TABLE_CATEGORY + "(" + CAT_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + CAT_NAME
			+ " CHAR(50) NOT NULL UNIQUE," + CAT_DES + " TEXT," + CAT_IMG_PATH
			+ " TEXT," + CAT_STT + " INT(1) DEFAULT (0))";
	
	private static final String CREATE_QUESTION_TABLE = "CREATE TABLE "
			+ TABLE_QUESTION + "(" + QUEST_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT," + QUEST_KEY
			+ " CHAR( 50 ) NOT NULL UNIQUE, " + QUEST_STT
			+ " INT( 1 ) DEFAULT (0), " + CAT_NAME
			+ " CHAR(50) NOT NULL REFERENCES CATEGORY ( " + CAT_NAME + "),"
			+ QUEST_IMG_PATH + " NOT NULL)";
	
	Context mContext;

	public DatabaseHandler(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mContext = context;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db){
		
		// create default tables
		db.execSQL(CREATE_CATEGORY_TABLE);
		db.execSQL(CREATE_QUESTION_TABLE);
		
		// insert default values
		Resources resources = mContext.getResources();
		
		String[] defaultCategoryQueries = resources
				.getStringArray(R.array.defaultCategoryQueries);
		String[] defaultQuestionQueries = resources
				.getStringArray(R.array.defaultQuestionQueries);
		
		for(String string : defaultCategoryQueries){
			db.execSQL(string);
		}
		
		for(String string : defaultQuestionQueries){
			db.execSQL(string);
		}
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_CATEGORY);
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_QUESTION);
		
		// create new tables
		onCreate(db);
	}
	
	/*
	 * Creating a Category
	 */
	public long createCategory(CategoriesInfo cate){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(CAT_NAME, cate.getCateName());
		values.put(CAT_DES, cate.getCateDesc());
		values.put(CAT_STT, cate.getStt());
		
		// insert row
		long category_id = db.insert(TABLE_CATEGORY, null, values);
		
		// return categoryID which is auto increment
		return category_id;

	}
	
	/*
	 * Creating a Question Param: quest for new question, categoryName is the
	 * name of category to insert new question into
	 */
	public long createQuestion(QuestInfo quest, String categoryName){
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(QUEST_KEY, quest.getAnswer());
		values.put(QUEST_IMG_PATH, quest.getImgPath());
		values.put(QUEST_STT, quest.getQuestStt());
		values.put(CAT_ID, categoryName);
		
		// insert row
		long question_id = db.insert(TABLE_QUESTION, null, values);
		
		return question_id;
	}
	
	/*
	 * Get all category
	 */
	public List<CategoriesInfo> getAllCategory(){
		List<CategoriesInfo> cateList = new ArrayList<CategoriesInfo>();
		
		String selectQuery = "SELECT * FROM " + TABLE_CATEGORY + " ORDER BY "
				+ CAT_ID;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if(c.moveToFirst()){
			do{
				CategoriesInfo cateInfo = new CategoriesInfo();
				cateInfo.setCateId(c.getInt(c.getColumnIndex(CAT_ID)));
				cateInfo.setCateName(c.getString(c.getColumnIndex(CAT_NAME)));
				cateInfo.setCateDesc(c.getString(c.getColumnIndex(CAT_DES)));
				cateInfo.setImgPath(c.getString(c.getColumnIndex(CAT_IMG_PATH)));
				cateInfo.setStt(c.getInt(c.getColumnIndex(CAT_STT)));
				cateInfo.setListQuest(getAllQuestByCategoryName(cateInfo
						.getCateName()));
				
				cateList.add(cateInfo);
			}while(c.moveToNext());
		}
		
		return cateList;
	}
	
	public List<QuestInfo> getAllQuestByCategoryName(String category_name){
		List<QuestInfo> quests = new ArrayList<QuestInfo>();
		
		String selectQuery = "SELECT * FROM " + TABLE_QUESTION + " WHERE "
				+ CAT_NAME + " = '" + category_name + "' ORDER BY " + QUEST_ID;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if(c.moveToFirst()){
			do{
				QuestInfo quest = new QuestInfo();
				quest.setQuestId(c.getInt(c.getColumnIndex(QUEST_ID)));
				try{
					quest.setAnswer(c.getString(c.getColumnIndex(QUEST_KEY)));
				}catch(InvalidKeyException e){
					NLog.e("Invalid quest answer"
							+ c.getString(c.getColumnIndex(QUEST_KEY)));
					continue;
				}
				quest.setImgPath(c.getString(c.getColumnIndex(QUEST_IMG_PATH)));
				quest.setQuestStt(c.getInt(c.getColumnIndex(QUEST_STT)));
				
				quests.add(quest);
			}while(c.moveToNext());
		}
		
		return quests;
	}
}
