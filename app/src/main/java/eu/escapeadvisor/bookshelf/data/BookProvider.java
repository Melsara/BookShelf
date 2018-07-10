package eu.escapeadvisor.bookshelf.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BookProvider extends ContentProvider {

    private BookshelfDbHelper mDbHelper;
    private static final int PRODUCTS = 100;
    private static final int PRODUCT_ID = 101;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String LOG_TAG = BookProvider.class.getSimpleName();

    static {
        sUriMatcher.addURI(BookshelfContract.CONTENT_AUTHORITY, BookshelfContract.PATH_PRODUCTS, PRODUCTS);
        sUriMatcher.addURI(BookshelfContract.CONTENT_AUTHORITY, BookshelfContract.PATH_PRODUCTS + "/#", PRODUCT_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new BookshelfDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor cursor = null;
        switch (match) {
            case PRODUCTS:
                cursor = db.query(BookshelfContract.BookshelfEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

                break;

            case PRODUCT_ID:
                selection = BookshelfContract.BookshelfEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(BookshelfContract.BookshelfEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);

                break;

            default:

        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                return BookshelfContract.BookshelfEntry.CONTENT_LIST_TYPE;
            case PRODUCT_ID:
                return BookshelfContract.BookshelfEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
