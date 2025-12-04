//code thu thach 5 - hoatd
//start
package com.example.exercise1_team;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>
        implements Filterable {

    // list đang hiển thị
    private final List<Category> categories;
    // list đầy đủ để filter
    private final List<Category> categoriesFull;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
        this.categoriesFull = new ArrayList<>(categories);
    }

    // gọi khi load xong dữ liệu từ API để cập nhật listFull
    public void setData(List<Category> newData) {
        categories.clear();
        categories.addAll(newData);

        categoriesFull.clear();
        categoriesFull.addAll(newData);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        if (category == null) return;

        holder.txtName.setText(category.getCategory());

        Glide.with(holder.itemView.getContext())
                .load(category.getImageUrl())
                .into(holder.imageCate);
    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    // ========== SEARCH FILTER ==========
    @Override
    public Filter getFilter() {
        return filter;
    }

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Category> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(categoriesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Category c : categoriesFull) {
                    if (c == null) continue;
                    String name = c.getName() == null ? "" : c.getName();
                    String cat = c.getCategory() == null ? "" : c.getCategory();

                    // search theo ProductName hoặc Category
                    if (name.toLowerCase().contains(filterPattern)
                            || cat.toLowerCase().contains(filterPattern)) {
                        filteredList.add(c);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            categories.clear();
            //noinspection unchecked
            categories.addAll((List<Category>) results.values);
            notifyDataSetChanged();
        }
    };

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        ImageView imageCate;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtCategoryName);
            imageCate = itemView.findViewById(R.id.image_cate);
        }
    }
}
//end
