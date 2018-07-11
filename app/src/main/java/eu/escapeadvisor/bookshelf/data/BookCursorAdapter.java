package eu.escapeadvisor.bookshelf.data;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import eu.escapeadvisor.bookshelf.data.BookshelfContract.BookshelfEntry;

import eu.escapeadvisor.bookshelf.R;

public class BookCursorAdapter extends CursorAdapter {

    public BookCursorAdapter (Context context, Cursor c) {
        super (context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvName = view.findViewById(R.id.name);
        int productNameColumnIndex = cursor.getColumnIndex(BookshelfEntry.COLUMN_PROD_PRODUCTNAME);
        String productName = cursor.getString(productNameColumnIndex);
        tvName.setText(productName);

        TextView tvPrice =  view.findViewById(R.id.price);
        int priceColumnIndex = cursor.getColumnIndex(BookshelfEntry.COLUMN_PROD_PRICE);
        String productPrice = cursor.getString(priceColumnIndex);
        tvPrice.setText(productPrice);

        TextView tvQuantity = view.findViewById(R.id.quantity);
        int quantityColumnIndex =cursor.getColumnIndex(BookshelfEntry.COLUMN_PROD_QUANTITY);
        String productQuantity = cursor.getString(quantityColumnIndex);
        tvQuantity.setText(productQuantity);

    }

}
