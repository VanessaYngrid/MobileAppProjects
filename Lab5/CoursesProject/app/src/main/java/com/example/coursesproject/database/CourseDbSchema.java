package com.example.coursesproject.database;

public class CourseDbSchema {

    public static final class CourseTable
    {
        public static final String NAME="course"; //CourseTable.NAME

        public static final class Cols
        {
            public static final String COURSE_NO="course_no";
            public static final String COURSE_NAME="course_name";
            public static final String MAX_ENRL="max_enrl";
            public static final String CREDITS="credits";
        }
    }
}
