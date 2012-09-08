package com.samcoles.specimenhunter.provider;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.samcoles.specimenhunter.R;

public class SpecimenHunterDatabaseAdapter {
	
	private static final String TAG = "SpecimenHunterDatabaseAdapter";
	
	public static final String KEY_CAPTURES_ROWID = "_id";
	public static final String KEY_CAPTURES_TITLE = "title";
	public static final String KEY_CAPTURES_SPECIES = "species";
	public static final String KEY_CAPTURES_PHOTO = "photo";
	public static final String KEY_CAPTURES_POUNDS = "pounds";
	public static final String KEY_CAPTURES_OUNCES = "ounces"; 
	public static final String KEY_CAPTURES_DRAMS = "drams"; 
	public static final String KEY_CAPTURES_COMMENT = "comment";
	public static final String KEY_CAPTURES_CENTIGRAMS = "centigrams";
	
	public static final String KEY_SPECIES_ROWID = "_id";
	public static final String KEY_SPECIES_NAME = "name";
	public static final String KEY_SPECIES_DESCRIPTION = "description";
	public static final String KEY_SPECIES_BAITS = "baits";
	public static final String KEY_SPECIES_METHOD = "method";
	public static final String KEY_SPECIES_EDITABLE = "editable";
	
	public static final String KEY_TARGETS_ROWID = "_id";
	public static final String KEY_TARGETS_SPECIES = "species";
	public static final String KEY_TARGETS_POUNDS = "pounds";
	public static final String KEY_TARGETS_OUNCES = "ounces";
	public static final String KEY_TARGETS_DRAMS = "drams";	
	public static final String KEY_TARGETS_CENTIGRAMS = "centigrams";
	
	public static final String KEY_METADATA_METADATA = "metadata";
	public static final String METADATA_CENTIGRAMS_CONVERTED = "centigramsconverted";
	
	
	private static final String DATABASE_NAME = "shdata";
	private static final String CAPTURES_TABLE = "fishcaptures";
	private static final String SPECIES_TABLE = "fishspecies";
	private static final String TARGETS_TABLE = "fishtargets";
	private static final String METADATA_TABLE = "dbmetadata";
	private static final int DATABASE_VERSION = 6;
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	private final Context mContext;
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		
		Context mContext;

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			mContext = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i(TAG, "onCreate() called on DatabaseHelper");
			buildDatabase(1, db);
			buildDatabase(2, db);
			buildDatabase(3, db);
			buildDatabase(4, db);
			buildDatabase(5, db);
			buildDatabase(6, db);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {			
			int upgradeCount = newVersion - oldVersion;			
			int i = 1;
			do {
				buildDatabase(oldVersion + i, db);
				i++;
			} while (i <= upgradeCount);
		}	
		
		private void buildDatabase(int version, SQLiteDatabase db) {
			Log.i(TAG, "buildDatabase() - building database version " + version);
			
			BufferedReader insertIn = null;
			switch (version) {			
			case 1:
				insertIn = new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(R.raw.databasecreate)));
				break;
			case 2:
				insertIn = new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(R.raw.databasecreate2)));
				break;
			case 3:
				insertIn = new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(R.raw.databasecreate3)));
				break;
			case 4:
				insertIn = new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(R.raw.databasecreate4)));
				break;
			case 5:
				insertIn = new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(R.raw.databasecreate5)));
				break;
			case 6:
				insertIn = new BufferedReader(new InputStreamReader(mContext.getResources().openRawResource(R.raw.databasecreate6)));
				break;
			default:
				break;
			}
			
			String databaseInsertStatement = null;
			try {
                while((databaseInsertStatement = insertIn.readLine()) != null) {
                    db.execSQL(databaseInsertStatement);            
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
			Log.i(TAG, "buildDatabase() - database version " + version + " built");
		}

	}
	
	public SpecimenHunterDatabaseAdapter(Context context) {
		mContext = context;
	}
	
	public SpecimenHunterDatabaseAdapter open() throws SQLException {
		mDbHelper = new DatabaseHelper(mContext);
		mDb = mDbHelper.getWritableDatabase();
		
		//database version 6 introduces centigrams column to captures and targets
		//convert all values if not already converted...
		if(!isConvertedToCentigrams()) {
			convertToCentigrams();
		}
		
		return this;
	}
	
	public void close() {
		mDbHelper.close();
	}
	
	public boolean createCapture(String title, long speciesId, String photo, int centigrams, String comment) {
		ContentValues capture = new ContentValues();
		capture.put(KEY_CAPTURES_TITLE, title);
		capture.put(KEY_CAPTURES_SPECIES, speciesId);
		capture.put(KEY_CAPTURES_PHOTO, photo);
		capture.put(KEY_CAPTURES_CENTIGRAMS, centigrams);
		if(comment != null) {
			capture.put(KEY_CAPTURES_COMMENT, comment);
		}
		
		return mDb.insert(CAPTURES_TABLE, null, capture) > 0;
	}
	
	public long fetchLastInsertedId() {
		String sql = "SELECT last_insert_rowid() FROM " + CAPTURES_TABLE;
		SQLiteStatement statement = mDb.compileStatement(sql);
		return statement.simpleQueryForLong();		
	}
		
	public boolean updateCapture(long id, String title, long speciesId, String photo, int centigrams, String comment) {
		ContentValues capture = new ContentValues();
		capture.put(KEY_CAPTURES_TITLE, title);
		capture.put(KEY_CAPTURES_SPECIES, speciesId);
		capture.put(KEY_CAPTURES_PHOTO, photo);
		capture.put(KEY_CAPTURES_CENTIGRAMS, centigrams);
		if(comment != null) {
			capture.put(KEY_CAPTURES_COMMENT, comment);
		}			
		return mDb.update(CAPTURES_TABLE, capture, KEY_CAPTURES_ROWID + "=" + id, null) > 0;
	}
	
	public static final int SORT_NONE = 0;
	public static final int SORT_TITLE = 1;
	public static final int SORT_SPECIES = 2;
	public static final int SORT_WEIGHT = 3;
	
	public Cursor fetchAllCaptures(int sortCode) {
		switch (sortCode) {
		case SORT_TITLE:
			return mDb.rawQuery("SELECT * FROM " + CAPTURES_TABLE 
							+ " ORDER BY " + KEY_CAPTURES_TITLE + " COLLATE NOCASE;", null);
		case SORT_SPECIES:
			return mDb.rawQuery("SELECT * FROM " + CAPTURES_TABLE
							+ " ORDER BY " + KEY_CAPTURES_SPECIES + ";" , null);
		case SORT_WEIGHT:
			return mDb.rawQuery("SELECT * FROM " + CAPTURES_TABLE
							+ " ORDER BY " + KEY_CAPTURES_CENTIGRAMS + " DESC;", null);
		case SORT_NONE:
		default:
			return mDb.query(CAPTURES_TABLE, new String[] { KEY_CAPTURES_ROWID, KEY_CAPTURES_TITLE, KEY_CAPTURES_SPECIES, KEY_CAPTURES_PHOTO, KEY_CAPTURES_CENTIGRAMS, KEY_CAPTURES_COMMENT },
					null, null, null, null, null);
		}
	}
	
	public Cursor fetchCapture(long id) {
		Cursor c = mDb.query(CAPTURES_TABLE, new String[] { KEY_CAPTURES_ROWID, KEY_CAPTURES_TITLE, KEY_CAPTURES_SPECIES, KEY_CAPTURES_PHOTO, KEY_CAPTURES_CENTIGRAMS, KEY_CAPTURES_COMMENT },
				KEY_CAPTURES_ROWID + "=" + id, null, null, null, null); 
		if(c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	public boolean deleteCapture(long id) {
		Cursor c = fetchCapture(id);
		String filepath = c.getString(c.getColumnIndexOrThrow(SpecimenHunterDatabaseAdapter.KEY_CAPTURES_PHOTO));
		File photo = new File(filepath);	
		photo.delete();
		return mDb.delete(CAPTURES_TABLE, KEY_CAPTURES_ROWID + "=" + id, null) > 0;
	}
	
	public Cursor fetchAllSpecies() {
		Cursor c = mDb.rawQuery("SELECT * FROM " + SPECIES_TABLE 
				+ " ORDER BY " + KEY_SPECIES_NAME + " COLLATE NOCASE;", null);	
		c.moveToFirst();
		return c;
	}
	
	public Cursor fetchSpecies(long id) {
		Cursor c = mDb.query(SPECIES_TABLE, new String[] { KEY_SPECIES_ROWID, KEY_SPECIES_NAME, KEY_SPECIES_DESCRIPTION, KEY_SPECIES_BAITS, KEY_SPECIES_METHOD, KEY_SPECIES_EDITABLE },
				KEY_SPECIES_ROWID + "=" + id, null, null, null, null); 
		if(c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	public boolean createSpecies(String name, String description, String baits, String methods) {
		ContentValues species = new ContentValues();
		species.put(KEY_SPECIES_NAME, name);
		if(description != null)
			species.put(KEY_SPECIES_DESCRIPTION, description);
		if(baits != null)
			species.put(KEY_SPECIES_BAITS, baits);
		if(methods != null)
			species.put(KEY_SPECIES_METHOD, methods);
		species.put(KEY_SPECIES_EDITABLE, 1);
		
		return mDb.insert(SPECIES_TABLE, null, species) > 0;		
	}
	
	public boolean updateSpecies(long id, String name, String description, String baits, String methods) {
		ContentValues species = new ContentValues();
		species.put(KEY_SPECIES_NAME, name);
		if(description != null)
			species.put(KEY_SPECIES_DESCRIPTION, description);
		if(baits != null)
			species.put(KEY_SPECIES_BAITS, baits);
		if(methods != null)
			species.put(KEY_SPECIES_METHOD, methods);
		
		return mDb.update(SPECIES_TABLE, species, KEY_SPECIES_ROWID + "=" + id, null) > 0;		
	}
	
	//FIXME this code looks dodgy and needs testing
	public boolean deleteSpecies(long id) {
		boolean result = true;
		
		//delete all captures of this species
		Cursor c = fetchCapturesOfSpecies(id);
		while(c.getCount() > 0) {
			
			int captureId = c.getInt(c.getColumnIndexOrThrow(KEY_CAPTURES_ROWID));
			
			if(!deleteCapture(captureId))
				result = false;
			
			c.close();
			c = fetchCapturesOfSpecies(id);
		}
		c.close();
		
		if(!deleteTargetsofSpecies(id))
			result = false;
		
		//delete the species
		if(mDb.delete(SPECIES_TABLE, KEY_SPECIES_ROWID + "=" + id, null) < 0)
			result = false;
		
		return result;
	}

	private Cursor fetchCapturesOfSpecies(long speciesId) {		
		Cursor c = mDb.query(CAPTURES_TABLE, new String[] { KEY_CAPTURES_ROWID, KEY_CAPTURES_TITLE, KEY_CAPTURES_SPECIES, KEY_CAPTURES_PHOTO, KEY_CAPTURES_CENTIGRAMS, KEY_CAPTURES_COMMENT },
				KEY_CAPTURES_SPECIES + "=" + speciesId, null, null, null, null); 		
		if(c != null) {
			c.moveToFirst();
		}		
		return c;
	}
	
	public boolean isSpeciesEditable(long speciesId) {
		String sql = "SELECT " + KEY_SPECIES_EDITABLE + " FROM " + SPECIES_TABLE + " WHERE " + KEY_SPECIES_ROWID + " = " + speciesId;
		SQLiteStatement statement = mDb.compileStatement(sql);
		return statement.simpleQueryForLong() > 0;		
	}
	
	public String fetchSpeciesName(long name) {
		String sql = "SELECT " + KEY_SPECIES_NAME + " FROM " + SPECIES_TABLE + " WHERE _id = " + name;
		SQLiteStatement statement = mDb.compileStatement(sql);
		return statement.simpleQueryForString();
	}

	public long fetchCapturesCount(long speciesId) {
		String sql = "SELECT COUNT(*) FROM " + CAPTURES_TABLE + " WHERE " + KEY_CAPTURES_SPECIES + "=" + speciesId;
		SQLiteStatement statement = mDb.compileStatement(sql);
		return statement.simpleQueryForLong();
	}

	public Cursor fetchPB(int speciesId) {
		String sql = "SELECT * FROM " + CAPTURES_TABLE 
				+ " WHERE " + KEY_CAPTURES_SPECIES + "=" + speciesId + " ORDER BY "
				+ KEY_CAPTURES_CENTIGRAMS + " DESC LIMIT 1";
		Cursor c = mDb.rawQuery(sql, null);
		if(c != null) {
			c.moveToFirst();
		}
		return c;
	}
	
	public Cursor fetchAllPBs() {	
		String sql = "SELECT * " +
					"FROM fishcaptures AS C1 " +
					"WHERE centigrams = " +
					"(SELECT MAX(centigrams) " +
					"FROM fishcaptures AS C2 " +
					"WHERE C2.species = c1.species) " +
					"ORDER BY centigrams DESC;";
		
		Cursor c = mDb.rawQuery(sql, null);
		if(c != null) {
			c.moveToFirst();
		}
		return c;
	}

	
	private boolean createTarget(int species, int centigrams) {
		ContentValues target = new ContentValues();
		target.put(KEY_TARGETS_SPECIES, species);
		target.put(KEY_TARGETS_CENTIGRAMS, centigrams);
		
		return mDb.insert(TARGETS_TABLE, null, target) > 0;
	}
	
	public boolean deleteTarget(long id) {
		return mDb.delete(TARGETS_TABLE, KEY_TARGETS_ROWID + "=" + id, null) > 0;
	}
	
	private boolean deleteTargetsofSpecies(long speciesId) {
		return mDb.delete(TARGETS_TABLE, KEY_TARGETS_SPECIES + "=" + speciesId, null) > 0;		
	}
	
	
	
	private Cursor fetchAllTargetsImperial() {
		return mDb.query(TARGETS_TABLE, new String[] { KEY_TARGETS_ROWID, KEY_TARGETS_SPECIES, KEY_TARGETS_POUNDS, KEY_TARGETS_OUNCES, KEY_TARGETS_DRAMS },
				null, null, null, null, null);
	}
	
	private Cursor fetchAllCapturesImperial() {
		return mDb.query(CAPTURES_TABLE, new String[] { KEY_CAPTURES_ROWID, KEY_CAPTURES_TITLE, KEY_CAPTURES_SPECIES, KEY_CAPTURES_PHOTO, KEY_CAPTURES_POUNDS, KEY_CAPTURES_OUNCES, KEY_CAPTURES_DRAMS, KEY_CAPTURES_COMMENT },
				null, null, null, null, null);
	}
	
	private boolean isConvertedToCentigrams() {
		Cursor c = mDb.query(METADATA_TABLE, new String[] { KEY_METADATA_METADATA },
				KEY_METADATA_METADATA + "='" + METADATA_CENTIGRAMS_CONVERTED + "'", null, null, null, null); 		
		if(c != null) {
			c.moveToFirst();
			return true;
		}		
		return false;
	}
	
	private boolean convertToCentigrams() {
		
		Cursor captures = fetchAllCapturesImperial();
		if(captures == null) return false;
		captures.moveToFirst();
		while(!captures.isAfterLast()) {			
			int id = captures.getInt(captures.getColumnIndexOrThrow(KEY_CAPTURES_ROWID));
			int pounds = captures.getInt(captures.getColumnIndexOrThrow(KEY_CAPTURES_POUNDS));
			int ounces = captures.getInt(captures.getColumnIndexOrThrow(KEY_CAPTURES_OUNCES));
			int drams = captures.getInt(captures.getColumnIndexOrThrow(KEY_CAPTURES_DRAMS));
			
			MetricWeight metricWeight = new MetricWeight(pounds, ounces, drams);
			int centigrams = metricWeight.getCentigrams();
			
			ContentValues updateValues = new ContentValues();
			updateValues.put(KEY_CAPTURES_CENTIGRAMS, centigrams);	
			mDb.update(CAPTURES_TABLE, updateValues, KEY_CAPTURES_ROWID + "=" + id, null);			
			captures.moveToNext();
		}
		captures.close();
		
		Cursor targets = fetchAllTargetsImperial();
		if(targets == null) return false;
		targets.moveToFirst();
		while(!targets.isAfterLast()) {
			int id = targets.getInt(targets.getColumnIndexOrThrow(KEY_TARGETS_ROWID));
			int pounds = targets.getInt(targets.getColumnIndexOrThrow(KEY_TARGETS_POUNDS));
			int ounces = targets.getInt(targets.getColumnIndexOrThrow(KEY_TARGETS_OUNCES));
			int drams = targets.getInt(targets.getColumnIndexOrThrow(KEY_TARGETS_DRAMS));
			
			MetricWeight metricWeight = new MetricWeight(pounds, ounces, drams);
			int centigrams = metricWeight.getCentigrams();
			
			ContentValues updateValues = new ContentValues();
			updateValues.put(KEY_TARGETS_CENTIGRAMS, centigrams);
			mDb.update(TARGETS_TABLE, updateValues, KEY_TARGETS_ROWID + "=" + id, null);
			targets.moveToNext();			
		}
		targets.close();
		
		ContentValues metadata = new ContentValues();
		metadata.put(KEY_METADATA_METADATA, METADATA_CENTIGRAMS_CONVERTED);
		mDb.insert(METADATA_TABLE, null, metadata);		
		
		return true;
	}
	
}
















