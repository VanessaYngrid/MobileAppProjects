package com.example.coursesproject.database;

import static com.example.coursesproject.database.CourseDbSchema.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coursesproject.model.Course;

import java.util.ArrayList;

public class CourseBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION=1;
    private static final String DATABASE_NAME="courseBase.db";

    public CourseBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    //The table is going to be created locally (in the mobile phone)
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CourseTable.NAME + "(" + CourseTable.Cols.COURSE_NO + ", " +
                        CourseTable.Cols.COURSE_NAME + ", " +
                        CourseTable.Cols.MAX_ENRL + ", " +
                        CourseTable.Cols.CREDITS + ") "
                );
    }

    //The table won't be created every time (That's why we have the method onUpgrade - verificar)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private ContentValues getContentValues(Course course){

        ContentValues values = new ContentValues();

        values.put(CourseTable.Cols.COURSE_NO, course.getCourse_no());
        values.put(CourseTable.Cols.COURSE_NAME, course.getCourse_name());
        values.put(CourseTable.Cols.MAX_ENRL, course.getMax_enrl());
        values.put(CourseTable.Cols.CREDITS, course.credits);

        return values;
    }

    public void addNewCourse(Course course){

        //writing data into database
        SQLiteDatabase db = this.getWritableDatabase();

        //creating values from ContentValues (method)
        ContentValues values = getContentValues(course);

        //Insert values into table row
        db.insert(CourseTable.NAME, null, values);

        //Close the database
        db.close();
    }

    //To read from the table created
    public ArrayList<Course> readCourses(){

        //Reading data from the database
        SQLiteDatabase db = this.getReadableDatabase();

        //Create the Cursor variable
        Cursor cursorCourse = db.rawQuery( "SELECT * FROM " + CourseTable.NAME, null);

        //Create ArrayList
        ArrayList<Course> courseModalArrayList = new ArrayList<>();

        //Move the cursor to the first position
        if(cursorCourse.moveToFirst())
        {
            do {
                courseModalArrayList.add(new Course(cursorCourse.getString(0), cursorCourse.getString(1),
                        cursorCourse.getInt(2)));

            }while(cursorCourse.moveToNext());
        }

        //Close the cursor and return
        cursorCourse.close();
        return courseModalArrayList;
    }

    public void updateCourse(Course course)
    {
        String course_noString = course.getCourse_no();
        //Creating values from ContentValues
        ContentValues values = getContentValues(course);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(CourseTable.NAME, values, CourseTable.Cols.COURSE_NO + "=?" , new String[]{course_noString});

        //The "?" is the parameter (replace with the parameter --> new String[]{course_noString})

    }
}
