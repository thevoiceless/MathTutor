package csci422.lwm.mathtutor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ProgressHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "problemProgress.db";
	private static final int SCHEMA_VERSION = 1;
			
	public ProgressHelper(Context context) {
		super(context, DB_NAME, null, SCHEMA_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE problems (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "value1 INTEGER, value2 INTEGER, answer INTEGER, " 
				+ "type INTEGER, correct INTEGER);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
	}
	
	public void storeProblem(MathProblemGenerator problem, Boolean correct) {
		ContentValues cv = new ContentValues();
		
		cv.put("value1", problem.getValue1());
		cv.put("value2", problem.getValue2());
		cv.put("answer", problem.getAnswer());
		cv.put("type", problem.getProblemType());
		cv.put("correct", correct);
		
		getWritableDatabase().insert("problems", "value1", cv);
	}
	
	public int numberOfStoredProblems() {
		Cursor c = getReadableDatabase().rawQuery("SELECT * FROM problems", null);
		return c.getCount();
	}
	
	public int numberOfCorrectProblems() {
		Cursor c = getReadableDatabase().rawQuery("SELECT * FROM problems WHERE correct=1", null);
		return c.getCount();
	}

}
