package com.dimazatolokin.films.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dimazatolokin.films.R;
import com.dimazatolokin.films.Utils;
import com.dimazatolokin.films.model.models.Film;

import java.util.ArrayList;
import java.util.List;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.VH> {

    private Context context;
    private List<Film> items = new ArrayList<>();
    private OnItemClickListener clickListener;

    public FilmsAdapter(Context context, OnItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }

    public void setItems(List<Film> items) {
        this.items = items == null ? new ArrayList<Film>() : items;
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_main_films_list, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.apply(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder {

        private ImageView imgImage;
        private TextView tvRating;
        private TextView tvReleaseYear;
        private TextView tvTitle;
        private TextView tvGenre;
        private View cardItemBox;
        private ImageView imgBookmark;

        public VH(View itemView) {
            super(itemView);

            imgImage = itemView.findViewById(R.id.imgImage);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvReleaseYear = itemView.findViewById(R.id.tvReleaseYear);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            cardItemBox = itemView.findViewById(R.id.cardItemBox);
            imgBookmark = itemView.findViewById(R.id.imgBookmark);
        }

        public void apply(final int position) {

            Film film = items.get(position);

            Glide.with(context)
                    .load(film.getImage())
                    .into(imgImage);
            tvRating.setText(String.valueOf(film.getRating()));
            tvReleaseYear.setText(String.valueOf(film.getReleaseYear()));
            tvTitle.setText(film.getTitle());
            tvGenre.setText(Utils.genresFromListToStr(film.getGenres()));
            cardItemBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(position);
                }
            });
            imgBookmark.setImageDrawable(context.getDrawable(film.isInBookmark() ?
                    R.drawable.baseline_turned_in_24 : R.drawable.baseline_turned_in_not_24));
        }
    }

    public interface OnItemClickListener {

        void onItemClick(int position);
    }
}
