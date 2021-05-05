package com.example.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<News> list;
    private OnItemClickListener itemClickListener;

    public NewsAdapter(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void addList(List<News> news){
        list = news;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);

        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News news = list.get(position);
        holder.tvTitle.setText(news.getTitle());
        holder.tvAuthor.setText(news.getAuthor());
        holder.tvDate.setText(news.getDate());
        holder.tvSection.setText(news.getSection());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvSection;
        TextView tvTitle;
        TextView tvDate;
        TextView tvAuthor;
        OnItemClickListener itemClickListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener itemClickListener) {
            super(itemView);
            tvSection = itemView.findViewById(R.id.tv_section);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(getAdapterPosition());
        }
    }

}
