package com.example.bookpublishingproject.database;

public class BookPubDbSchema {

    public static final class BookTable{
        public static final String NAME="book";

        public static final class Cols{
            public static final String BOOK_ID="b_id";
            public static final String BOOK_AUTHOR="b_author";
            public static final String BOOK_TITLE="b_title";
            public static final String BOOK_ISBN="b_isbn";
            public static final String BOOK_TYPE="b_type";
            public static final String BOOK_PRICE="b_price";
            public static final String PUBLISHER_ID="p_id";
        }
    }

    public static final class PublisherTable{
        public static final String NAME="publisher";

        public static final class Cols{
            public static final String PUBLISHER_ID="p_id";
            public static final String PUBLISHER_NAME="p_name";
            public static final String PUBLISHER_ADDRESS="p_address";
        }
    }

    public static final class ChapterTable{
        public static final String NAME="chapter";

        public static final class Cols{
            public static final String BOOK_ID="b_id";
            public static final String CHAPTER_NO="c_no";
            public static final String CHAPTER_TITLE="c_tile";
            public static final String CHAPTER_PRICE="c_price";
        }
    }

}
