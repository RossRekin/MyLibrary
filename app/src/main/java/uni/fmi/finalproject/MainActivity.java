package uni.fmi.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DbHelper dbHelper;
    EditText bookNameET,bookAuthorET,bookIsbnET;
    Button addBookB,listBooksB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookNameET=findViewById(R.id.bookNameEditText);
        bookAuthorET=findViewById(R.id.bookAuthorEditText);
        bookIsbnET=findViewById(R.id.bookIsbnEditText);
        addBookB=findViewById(R.id.addBookButton);
        listBooksB=findViewById(R.id.listBooksButton);

        addBookB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName=bookNameET.getText().toString();
                String bookAuthor=bookAuthorET.getText().toString();
                String bookIsbn=bookIsbnET.getText().toString();

                if (bookName.length()==0 || bookAuthor.length()==0 ||bookIsbn.length()==0){
                    Toast.makeText(MainActivity.this, "There are empty fields.", Toast.LENGTH_SHORT).show();
                }else{
                    Book book = new Book(bookName,bookAuthor,bookIsbn);
                    dbHelper.createBook(book);
                    Toast.makeText(MainActivity.this,"Book Added Successfully",Toast.LENGTH_LONG).show();

                }
            }
        });
        listBooksB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewBooksActivity.class);
                startActivity(intent);
            }
        });
        dbHelper = new DbHelper(this);

    }





}