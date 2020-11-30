package uni.fmi.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewBooksActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_books);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        dbHelper =  new DbHelper(this);
        ArrayList<Book> booksList = dbHelper.getBooks();

        if (booksList.size()>0){
            BookAdapterClass bookAdapterClass = new BookAdapterClass(booksList,ViewBooksActivity.this);
            recyclerView.setAdapter(bookAdapterClass);
        }else{
            Toast.makeText(this,"There aren't any books",Toast.LENGTH_LONG).show();
        }


    }
}