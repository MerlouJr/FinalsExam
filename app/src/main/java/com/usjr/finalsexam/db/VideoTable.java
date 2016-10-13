package com.usjr.finalsexam.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.usjr.finalsexam.entity.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoTable {

    /**
     * Inner class that defines video table contents.
     */
    public static abstract class VideoEntry implements BaseColumns {
        public static final String TABLE_NAME        = "Video";
        public static final String COL_TITLE         = "Title";
        public static final String COL_DESCRIPTION   = "Description";
        public static final String COL_THUMBNAIL_URL = "ThumbnailUrl";
    }

    private static final String CREATE_TABLE = "CREATE TABLE " + VideoEntry.TABLE_NAME + " (" +
            VideoEntry._ID + " TEXT PRIMARY KEY, " +
            VideoEntry.COL_TITLE + " TEXT, " +
            VideoEntry.COL_DESCRIPTION + " TEXT, " +
            VideoEntry.COL_THUMBNAIL_URL + " TEXT)";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + VideoEntry.TABLE_NAME;

    private static final String SELECT_QUERY = "SELECT * FROM " + VideoEntry.TABLE_NAME;

    public static String createTableQuery() {
        return CREATE_TABLE;
    }

    public static String dropTableQuery() {
        return DROP_TABLE;
    }

    private static ContentValues createValuesFromVideo(Video video) {
        ContentValues values = new ContentValues();
        values.put(VideoEntry._ID, video.getId());
        values.put(VideoEntry.COL_TITLE, video.getTitle());
        values.put(VideoEntry.COL_DESCRIPTION, video.getDescription());
        values.put(VideoEntry.COL_THUMBNAIL_URL, video.getThumbnailUrl());
        return values;
    }

    private static Video createVideoFromCursor(Cursor cursor) {
        // TODO: Implement this method
        createVideoFromCursor(cursor);
        return null;
    }

    public static long insertVideo(Context context, Video video) {
        SQLiteDatabase db = null;

        try {
            ContentValues values = createValuesFromVideo(video);
            db = DbHandler.getInstance(context).getWritableDatabase();
            return db.insert(VideoEntry.TABLE_NAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return -1;
    }

    public static int deleteAllVideos(Context context) {
        if (getVideoCount(context) <= 0) {
            return -1;
        }

        SQLiteDatabase db = null;

        try {
            db = DbHandler.getInstance(context).getWritableDatabase();
            return db.delete(VideoEntry.TABLE_NAME, null, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }

        return -1;
    }

    public static int getVideoCount(Context context) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        int count = 0;

        try {
            db = DbHandler.getInstance(context).getReadableDatabase();
            cursor = db.rawQuery(SELECT_QUERY, null);
            count = cursor.getCount();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }

        return count;
    }

    public static List<Video> getAllVideos(Context context) {
        List<Video> videos = new ArrayList<>();
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            // TODO: Implement retrieval of all video items from the database
            db = DbHandler.getInstance(context).getReadableDatabase();
            cursor = db.rawQuery(SELECT_QUERY, null);

            cursor.moveToFirst();
            while(cursor.isAfterLast() == false) {
                    cursor.getColumnIndex(SELECT_QUERY);
                cursor.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }

        return videos;
    }
}
