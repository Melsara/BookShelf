package eu.escapeadvisor.bookshelf;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import eu.escapeadvisor.bookshelf.data.BookshelfContract.BookshelfEntry;
import eu.escapeadvisor.bookshelf.data.BookshelfDbHelper;

public class MainActivity extends AppCompatActivity {

    private BookshelfDbHelper mDbHelper;
    private Toast insertToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRandomBook();
            }
        });

        mDbHelper = new BookshelfDbHelper(this);
        displayDbInfo();

    }

    public void displayDbInfo() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String sortOrder = BaseColumns._ID + " ASC";
        Cursor cursor = db.query(BookshelfEntry.TABLE_NAME, null, null, null, null, null, sortOrder);
        TextView displayView = findViewById(R.id.tv_catalog);
        String currentIsBookString = "";

        try {
            int idColumnIndex = cursor.getColumnIndex(BookshelfEntry._ID);
            int productNameColumnIndex = cursor.getColumnIndex(BookshelfEntry.COLUMN_PROD_PRODUCTNAME);
            int isBookColumnIndex = cursor.getColumnIndex(BookshelfEntry.COLUMN_PROD_ISBOOK);
            int titleColumnIndex = cursor.getColumnIndex(BookshelfEntry.COLUMN_PROD_TITLE);
            int authorColumnIndex = cursor.getColumnIndex(BookshelfEntry.COLUMN_PROD_AUTHOR);
            int priceColumnIndex = cursor.getColumnIndex(BookshelfEntry.COLUMN_PROD_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookshelfEntry.COLUMN_PROD_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(BookshelfEntry.COLUMN_PROD_SUPPLIERNAME);
            int supplierPhoneNumberColumnIndex = cursor.getColumnIndex(BookshelfEntry.COLUMN_PROD_SUPPLIERPHONENUMBER);

            displayView.setText("The products table contains " + cursor.getCount() + " products.\n\n");
            displayView.append(BookshelfEntry._ID + " - " +
                    BookshelfEntry.COLUMN_PROD_PRODUCTNAME + " - " +
                    BookshelfEntry.COLUMN_PROD_ISBOOK + " - " +
                    BookshelfEntry.COLUMN_PROD_TITLE + " - " +
                    BookshelfEntry.COLUMN_PROD_AUTHOR + " - " +
                    BookshelfEntry.COLUMN_PROD_PRICE + " - " +
                    BookshelfEntry.COLUMN_PROD_QUANTITY + " - " +
                    BookshelfEntry.COLUMN_PROD_SUPPLIERNAME + " - " +
                    BookshelfEntry.COLUMN_PROD_SUPPLIERPHONENUMBER + "\n");

            while (cursor.moveToNext()) {
                int currentId = cursor.getInt(idColumnIndex);
                String currentProductName = cursor.getString(productNameColumnIndex);
                int currentIsBook = cursor.getInt(isBookColumnIndex);
                String currentTitle = cursor.getString(titleColumnIndex);
                String currentAuthor = cursor.getString(authorColumnIndex);
                float currentPrice = cursor.getFloat(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                String currentSupplierPhoneNumber = cursor.getString(supplierPhoneNumberColumnIndex);

                switch (currentIsBook) {
                    case 0:
                         currentIsBookString = getString(R.string.not_a_book);
                         break;

                    case 1:
                        currentIsBookString = getString(R.string.is_a_book);

                }

                displayView.append("\n" + currentId + " - " +
                currentProductName + " - " +
                currentIsBookString + " - " +
                currentTitle + " - " +
                currentAuthor + " - " +
                currentPrice + " - " +
                currentQuantity + " - " +
                currentSupplierName + " - " +
                currentSupplierPhoneNumber);

            }

        } finally {
            cursor.close();
        }

    }

    public void insertRandomBook() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookshelfEntry.COLUMN_PROD_PRODUCTNAME, getString(R.string.dummy_productname));
        values.put(BookshelfEntry.COLUMN_PROD_ISBOOK, BookshelfEntry.ISBOOK_YES);
        values.put(BookshelfEntry.COLUMN_PROD_TITLE, getString(R.string.dummy_title));
        values.put(BookshelfEntry.COLUMN_PROD_AUTHOR, getString(R.string.dummy_author));
        values.put(BookshelfEntry.COLUMN_PROD_PRICE, "9.99");
        values.put(BookshelfEntry.COLUMN_PROD_QUANTITY, "9999");
        values.put(BookshelfEntry.COLUMN_PROD_SUPPLIERNAME, getString(R.string.dummy_supplierName));
        values.put(BookshelfEntry.COLUMN_PROD_SUPPLIERPHONENUMBER, getString(R.string.dummy_supplierPhoneNumber));

        long newRowID = db.insert(BookshelfEntry.TABLE_NAME, null, values);
        Log.v("MainActivity", "New row ID is " + newRowID);
        if (newRowID == -1) {
            insertToast.makeText(this, getString(R.string.insert_failure), Toast.LENGTH_LONG).show();
        } else {
            insertToast.makeText(this, getString(R.string.insert_success) + newRowID, Toast.LENGTH_LONG).show();
        }
        displayDbInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}
