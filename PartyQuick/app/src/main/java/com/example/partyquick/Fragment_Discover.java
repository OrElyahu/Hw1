package com.example.partyquick;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Fragment_Discover extends Fragment {

    private RecyclerView LST_parties;

    private ArrayList<Party> parties;
    private Adapter_Party adapter_party;
    private AppCompatActivity activity;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        findViews(view);
        parties = DataManager.generateParties();
        adapter_party = new Adapter_Party(activity, parties);
        LST_parties.setLayoutManager(new GridLayoutManager(activity,1));
        LST_parties.setHasFixedSize(true);
        LST_parties.setItemAnimator(new DefaultItemAnimator());
        LST_parties.setAdapter(adapter_party);

        adapter_party.setPartyItemClickListener(new Adapter_Party.PartyItemClickListener() {
            @Override
            public void partyItemClicked(Party party, int position) {
                Toast.makeText(activity, party.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }





    private void findViews(View view) {
        LST_parties = view.findViewById(R.id.LST_parties);
    }
}
