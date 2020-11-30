package uni.fmi.finalproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BookAdapterClass extends RecyclerView.Adapter<BookAdapterClass.ViewHolder>{

    ArrayList<Book> books = new ArrayList<>();
    Context context;
    DbHelper dbHelper;

    public BookAdapterClass(ArrayList<Book> books, Context context) {
        this.books = books;
        this.context = context;
        this.dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.book_item_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Book book = books.get(position);

        holder.bookNameET.setText(book.getName());
        holder.bookAuthorET.setText(book.getAuthor());
        holder.bookIsbnET.setText(book.getIsbn());

        holder.editB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName= holder.bookNameET.getText().toString();
                String bookAuthor= holder.bookAuthorET.getText().toString();
                String bookIsbn= holder.bookIsbnET.getText().toString();
                Book newBook = new Book(book.getId(),bookName,bookAuthor,bookIsbn);
                dbHelper.updateBook(newBook);
                notifyDataSetChanged();
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

        holder.deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteBook(book);
                books.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView bookNameET;
        TextView bookAuthorET;
        TextView bookIsbnET;
        Button editB;
        Button deleteB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookNameET =itemView.findViewById(R.id.listNameEditText);
            bookAuthorET =itemView.findViewById(R.id.listAuthorEditText);
            bookIsbnET =itemView.findViewById(R.id.listIsbnEditText);
            editB= itemView.findViewById(R.id.editBookButton);
            deleteB= itemView.findViewById(R.id.deleteBookButton);
        }
    }
}
