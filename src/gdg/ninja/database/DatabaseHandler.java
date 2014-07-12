package gdg.ninja.database;

import gdg.nat.R;
import gdg.ninja.gameinfo.CategoriesInfo;
import gdg.ninja.gameinfo.QuestInfo;
import gdg.ninja.util.App;
import gdg.ninja.util.DbUtils;
import gdg.ninja.util.NLog;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "LearnJapaneseDB";

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
	private static final String QUEST_DEFINITION = "QuestDefinition";

	Context mContext;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, App.getContext().getResources()
				.getInteger(R.integer.databaseVersion));
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// get initiate db sql file path from resource
		Resources resources = mContext.getResources();
		String initiateDbFilePath = resources.getString(R.string.initiateDbFilePath);
		
		// create default tables
		try {
			DbUtils.executeSqlScript(mContext, db, initiateDbFilePath);
		} catch (IOException e) {
			NLog.i("Initiate Database Failed: " + e.getMessage());
		}

		db.setVersion(1);
		onUpgrade(db, 1, resources.getInteger(R.integer.databaseVersion));
	}

	// TODO: implement onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Resources resources = mContext.getResources();
		for (int i = oldVersion + 1; i <= newVersion; i++) {
			String[] updateQueries = null;
			switch (i) {
				case 2:
					break;
				case 3:
					break;
			}
		}
	}

	/*
	 * Creating a Category
	 */
	public long createCategory(CategoriesInfo cate) {
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
	public long createQuestion(QuestInfo quest, String categoryName) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(QUEST_KEY, quest.getAnswer());
		values.put(QUEST_IMG_PATH, quest.getImgPath());
		values.put(QUEST_STT, quest.getQuestStt());
		values.put(CAT_ID, categoryName);
		values.put(QUEST_DEFINITION, quest.getDefinition());

		// insert row
		long question_id = db.insert(TABLE_QUESTION, null, values);

		return question_id;
	}

	/*
	 * Get all category
	 */
	public List<CategoriesInfo> getAllCategory() {
		List<CategoriesInfo> cateList = new ArrayList<CategoriesInfo>();

		String selectQuery = "SELECT * FROM " + TABLE_CATEGORY + " ORDER BY "
				+ CAT_ID;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				CategoriesInfo cateInfo = new CategoriesInfo();
				cateInfo.setCateId(c.getInt(c.getColumnIndex(CAT_ID)));
				cateInfo.setCateName(c.getString(c.getColumnIndex(CAT_NAME)));
				cateInfo.setCateDesc(c.getString(c.getColumnIndex(CAT_DES)));
				cateInfo.setImgPath(c.getString(c.getColumnIndex(CAT_IMG_PATH)));
				cateInfo.setStt(c.getInt(c.getColumnIndex(CAT_STT)));
				cateInfo.setListQuest(getAllQuestByCategoryName(cateInfo
						.getCateName()));

				cateList.add(cateInfo);
			} while (c.moveToNext());
		}

		return cateList;
	}

	/*
	 * Get all Quest by Category Name
	 */
	public List<QuestInfo> getAllQuestByCategoryName(String category_name) {
		List<QuestInfo> quests = new ArrayList<QuestInfo>();

		String selectQuery = "SELECT * FROM " + TABLE_QUESTION + " WHERE "
				+ CAT_NAME + " = '" + category_name + "' ORDER BY " + QUEST_ID;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if (c.moveToFirst()) {
			do {
				QuestInfo quest = new QuestInfo();
				quest.setQuestId(c.getInt(c.getColumnIndex(QUEST_ID)));
				try {
					quest.setAnswer(c.getString(c.getColumnIndex(QUEST_KEY)));
				} catch (InvalidKeyException e) {
					NLog.e("Invalid quest answer"
							+ c.getString(c.getColumnIndex(QUEST_KEY)));
					continue;
				}
				quest.setImgPath(c.getString(c.getColumnIndex(QUEST_IMG_PATH)));
				quest.setQuestStt(c.getInt(c.getColumnIndex(QUEST_STT)));
				quest.setDefinition(c.getString(c
						.getColumnIndex(QUEST_DEFINITION)));

				quests.add(quest);
			} while (c.moveToNext());
		}

		return quests;
	}

	/*
	 * Update score for a quest by it's Id
	 */
	public int updateScoreByQuestId(int questId, int valueOfScore) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(QUEST_STT, valueOfScore);

		return db.update(TABLE_QUESTION, values, QUEST_ID + " = ?",
				new String[] { String.valueOf(questId) });
	}

	/*
	 * Update stt for a category by it's Id
	 */
	public int updateCateSttByCategoryId(int cateId, int newStt) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(CAT_STT, newStt);

		return db.update(TABLE_CATEGORY, values, CAT_ID + " = ?",
				new String[] { String.valueOf(cateId) });
	}
}
