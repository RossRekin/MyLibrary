package uni.fmi.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.TableRow;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class DbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 2;
    public static final String DB_NAME = "mylibrary.sqlite";
    public static final String ERROR_TAG = "MyErrorTag";


    public static final String TABLE_USER = "user";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_USERNAME = "username";
    public static final String USER_COLUMN_PASSWORD = "password";

    public static final String TABLE_BOOK = "book";
    public static final String BOOK_COLUMN_ID = "id";
    public static final String BOOK_COLUMN_NAME = "name";
    public static final String BOOK_COLUMN_AUTHOR = "author";
    public static final String BOOK_COLUMN_ISBN = "isbn";



    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "('" +
            USER_COLUMN_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + USER_COLUMN_USERNAME + "' VARCHAR(40) NOT NULL UNIQUE," +
            "'" + USER_COLUMN_PASSWORD + "' VARCHAR(40) NOT NULL," +
            "'" + USER_COLUMN_EMAIL + "' VARCHAR(40) NOT NULL UNIQUE)";

    public static final String CREATE_TABLE_BOOK = "CREATE TABLE " + TABLE_BOOK + "('" +
            BOOK_COLUMN_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + BOOK_COLUMN_NAME + "' VARCHAR(40) NOT NULL," +
            "'" + BOOK_COLUMN_AUTHOR + "' VARCHAR(40) NOT NULL," +
            "'" + BOOK_COLUMN_ISBN + "' VARCHAR(13) NOT NULL UNIQUE)";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_BOOK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_BOOK);
        onCreate(db);
    }

    public boolean userRegister(User user){
        SQLiteDatabase db = null;

        try{
            db = getWritableDatabase();

            ContentValues cv = new ContentValues();

            cv.put(USER_COLUMN_USERNAME, user.getUsername());
            cv.put(USER_COLUMN_PASSWORD, user.getPassword());
            cv.put(USER_COLUMN_EMAIL,user.getEmail());
            return db.insert(TABLE_USER,null,cv) != -1;
        }catch (SQLException e){
            Log.wtf(ERROR_TAG,e.getMessage());
        }finally {
            if (db!=null) db.close();
        }
        return false;
    }

    public boolean isLoginSuccessful(String username,String password){
        SQLiteDatabase db =null;
        Cursor c =null;

        try{
            db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_USER +
                    " WHERE " + USER_COLUMN_USERNAME + " = '" + username + "' AND " +
                    USER_COLUMN_PASSWORD + " = '" + password + "'";

            c=db.rawQuery(query,null);
            return c.moveToFirst();
        }catch (SQLException e){
            Log.wtf(ERROR_TAG, e.getMessage());
        }finally {
            if (db !=null){
                if(c!=null) c.close();
                db.close();
            }
        }

        return false;
    }

    public boolean createBook(Book book){
        SQLiteDatabase db = null;

        try{
            db = getWritableDatabase();

            ContentValues cv = new ContentValues();

            cv.put(BOOK_COLUMN_NAME, book.getName());
            cv.put(BOOK_COLUMN_AUTHOR, book.getAuthor());
            cv.put(BOOK_COLUMN_ISBN,book.getIsbn());
            return db.insert(TABLE_BOOK,null,cv) != -1;
        }catch (SQLException e){
            Log.wtf(ERROR_TAG,e.getMessage());
        }finally {
            if (db!=null) db.close();
        }
        return false;
    }

    public ArrayList<Book> getBooks(){
        String query= "SELECT * FROM "+ TABLE_BOOK;
        ArrayList<Book> books= new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    String id = cursor.getString(0);
                    String name = cursor.getString(1);
                    String author = cursor.getString(2);
                    String isbn = cursor.getString(3);
                    Book currentBook = new Book(id,name,author,isbn);
                    books.add(currentBook);

                }while (cursor.moveToNext());
                cursor.close();
            }
        }catch (SQLException e){
            Log.wtf(ERROR_TAG, e.getMessage());
        }
        finally {
            if (db!=null) db.close();
        }

        return books;
    }

    public void updateBook(Book book){
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(BOOK_COLUMN_NAME, book.getName());
            cv.put(BOOK_COLUMN_AUTHOR, book.getAuthor());
            cv.put(BOOK_COLUMN_ISBN,book.getIsbn());
            db.update(TABLE_BOOK,cv,BOOK_COLUMN_ID + " = ?",new String[]{String.valueOf(book.getId())});
        }catch (SQLException e){
            Log.wtf(ERROR_TAG,e.getMessage());
        }finally {
            if (db!=null) db.close();
        }

    }

    public void deleteBook(Book book){
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.delete(TABLE_BOOK,BOOK_COLUMN_ID + " = ? ",new String[]{String.valueOf(book.getId())});
        }catch (SQLException e){
            Log.wtf(ERROR_TAG,e.getMessage());
        }finally {
            if (db!=null) db.close();
        }
    }

}
