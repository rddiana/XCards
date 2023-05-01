package com.example.xcards.domain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xcards.R;
import com.example.xcards.data.CardData;

import java.util.ArrayList;

public class AdapterForRecyclerView extends RecyclerView.Adapter<AdapterForRecyclerView.ViewHolder> {
    ArrayList<CardData> cardsArray;
    Context context;

    public AdapterForRecyclerView(Context context, ArrayList images) {
        this.context = context;
        this.cardsArray = images;
    }

    @NonNull
    @Override
    public AdapterForRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the Layout(Instantiates list_item.xml layout file into View object)
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, parent, false);

        // Passing view to ViewHolder
        AdapterForRecyclerView.ViewHolder viewHolder = new AdapterForRecyclerView.ViewHolder((CardView) view);
        return viewHolder;
    }

    // Binding data to the into specified position
    @Override
    public void onBindViewHolder(@NonNull AdapterForRecyclerView.ViewHolder holder, int position) {
        // TypeCast Object to int type
        CardData displayingCard = cardsArray.get(position);
        holder.cardName.setText(displayingCard.getNameModule());
        holder.cardsCount.setText(displayingCard.getCardsCount());
    }

    @Override
    public int getItemCount() {
        // Returns number of items currently available in Adapter
        return cardsArray.size();
    }

    // Initializing the Views
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cardName;
        TextView cardsCount;

        public ViewHolder(CardView view) {
            super(view);
            cardName = view.findViewById(R.id.textViewName);
            cardsCount = view.findViewById(R.id.textViewCount);
        }
    }
}
