package eu.escapeadvisor.bookshelf.data;

import android.provider.BaseColumns;

public final class BookshelfContract {

    private BookshelfContract () {

    }

    public static final class BookshelfEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_BOOKS_PRODUCTNAME = "product_name";
        public static final String COLUMN_BOOKS_PRICE = "price";
        public static final String COLUMN_BOOKS_QUANTITY = "quantity";
        public static final String COLUMN_BOOKS_SUPPLIERNAME = "supplier_name";
        public static final String COLUMN_BOOKS_SUPPLIERPHONENUMBER = "supplier_phonenumber";
    }
}
