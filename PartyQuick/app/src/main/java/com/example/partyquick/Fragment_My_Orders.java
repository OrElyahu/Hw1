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

public class Fragment_My_Orders extends Fragment {

    private RecyclerView LST_orders;

    private ArrayList<Party> orders;
    private Adapter_Party adapter_party;
    private AppCompatActivity activity;

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        findViews(view);
        orders = DataManager.generateParties();
        adapter_party = new Adapter_Party(activity, orders);
        LST_orders.setLayoutManager(new GridLayoutManager(activity,1));
        LST_orders.setHasFixedSize(true);
        LST_orders.setItemAnimator(new DefaultItemAnimator());
        LST_orders.setAdapter(adapter_party);

        adapter_party.setPartyItemClickListener(new Adapter_Party.PartyItemClickListener() {
            @Override
            public void partyItemClicked(Party party, int position) {
                Toast.makeText(activity, party.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }





    private void findViews(View view) {

        LST_orders = view.findViewById(R.id.LST_parties);
    }
}
