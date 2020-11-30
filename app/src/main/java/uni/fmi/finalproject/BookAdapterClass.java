package uni.fmi.finalproject;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Book book = books.get(position);

        holder.bookNameTV.setText(book.getName());
        holder.bookAuthorTV.setText(book.getAuthor());
        holder.bookIsbnTV.setText(book.getIsbn());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView bookNameTV;
        TextView bookAuthorTV;
        TextView bookIsbnTV;
        Button editB;
        Button deleteB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bookNameTV =itemView.findViewById(R.id.listNameEditText);
            bookAuthorTV =itemView.findViewById(R.id.listAuthorEditText);
            bookIsbnTV =itemView.findViewById(R.id.listIsbnEditText);
            editB= itemView.findViewById(R.id.editBookButton);
            deleteB= itemView.findViewById(R.id.deleteBookButton);
        }
    }
}
