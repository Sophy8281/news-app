package com.example.newsapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final ArrayList<CategoryModal> categoryModalArrayList;
    private Context context;
    private final CategoryClickInterface categoryClickInterface;

    // Constructor
    public CategoryAdapter(ArrayList<CategoryModal> categoryModalArrayList, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryModalArrayList = categoryModalArrayList;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    // Generated from implement methods of this class
    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv_item,parent,false);
        return new ViewHolder(view);
    }

    // Generated from implement methods of this class
    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

        // Instance of CategoryModal class
        CategoryModal categoryModal = categoryModalArrayList.get(position);
        holder.categoryTV.setText(categoryModal.getCategory());
        Picasso.get().load(categoryModal.getCategoryImageUrl()).into(holder.categoryIV);
        // OnClickListener on the ViewHolder itemView
        holder.itemView.setOnClickListener(v -> categoryClickInterface.onCategoryClick(position));
    }

    // Return categoryModalArrayList size
    @Override
    public int getItemCount() {
        return categoryModalArrayList.size();
    }

    public interface CategoryClickInterface {
        void onCategoryClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Variable declaration
        private final TextView categoryTV;
        private final ImageView categoryIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Variable initialization
            categoryTV = itemView.findViewById(R.id.idTVCategory);
            categoryIV = itemView.findViewById(R.id.idIVCategory);
        }
    }
}
