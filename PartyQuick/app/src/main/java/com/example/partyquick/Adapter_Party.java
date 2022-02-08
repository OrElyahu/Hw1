package com.example.partyquick;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;


public class Adapter_Party extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private ArrayList<Party> parties = new ArrayList<>();
    private PartyItemClickListener partyItemClickListener;

    public Adapter_Party(Activity activity, ArrayList<Party> parties) {
        this.activity = activity;
        this.parties = parties;
    }

    public Adapter_Party setPartyItemClickListener(PartyItemClickListener partyItemClickListener) {
        this.partyItemClickListener = partyItemClickListener;
        return this;
    }

    @Override
    public RecyclerView.ViewHolder
    onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_party_small, viewGroup, false);
        return new PartyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        PartyViewHolder partyViewHolder = (PartyViewHolder) holder;
        Party party = getItem(position);
        //Log.d("pttt", "position= " + position);

        partyViewHolder.party_LBL_title.setText(position + "\n" + party.getTitle());
        partyViewHolder.party_LBL_location.setText(party.getLocation());
//        int h = party.getDuration() / 60;
//        int m = party.getDuration() % 60;
//        String hh = h < 10 ? "0" + h : "" + h;
//        String mm = m < 10 ? "0" + m : "" + m;
        partyViewHolder.party_LBL_date.setText(party.getDate().toString());


//        Glide
//                .with(activity)
//                .load(party.getImage())
//                .into(partyViewHolder.movie_IMG_image);
//
//        if (party.isFavorite()) {
//            partyViewHolder.movie_IMG_favorite.setImageResource(R.drawable.ic_heart_filled);
//        } else  {
//            partyViewHolder.movie_IMG_favorite.setImageResource(R.drawable.ic_heart_empty);
//        }
//
//        float rating = party.getRating();
//        rating /= 20;
//        partyViewHolder.movie_RTNG_stars.setRating(rating);
    }

    @Override
    public int getItemCount() {
        return parties.size();
    }

    private Party getItem(int position) {
        return parties.get(position);
    }

    public interface PartyItemClickListener {
        void partyItemClicked(Party party, int position);
    }

    public class PartyViewHolder extends RecyclerView.ViewHolder {

        public AppCompatImageView party_IMG;
        public MaterialTextView party_LBL_title;
        public MaterialTextView party_LBL_location;
        public MaterialTextView party_LBL_date;

        public PartyViewHolder(final View itemView) {
            super(itemView);
            this.party_IMG = itemView.findViewById(R.id.party_IMG);
            this.party_LBL_title = itemView.findViewById(R.id.party_LBL_title);
            this.party_LBL_location = itemView.findViewById(R.id.party_LBL_location);
            this.party_LBL_date = itemView.findViewById(R.id.party_LBL_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    partyItemClickListener.partyItemClicked(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });
        }
    }
}
