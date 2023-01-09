package com.example.finderapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.finderapp.Adapters.FoundItemAdapter;
import com.example.finderapp.Adapters.NotificationAdapter;
import com.example.finderapp.Model.Model;
import com.example.finderapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchItemFragment extends Fragment {
    RecyclerView recyclerView;
    FoundItemAdapter adapter;
    EditText searchItem;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchItemFragment newInstance(String param1, String param2) {
        SearchItemFragment fragment = new SearchItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_item, container, false);

        //assigning the recycler view to show all the found items
        recyclerView = (RecyclerView) view.findViewById(R.id.LostItemRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //edit text to take input for searching
        searchItem = (EditText) view.findViewById(R.id.LostItemSearchEditTxt);
        //search implementation
        searchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String searchtxt = searchItem.getText().toString().trim();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>().setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("foundItems").orderByChild("itemName").startAt(searchtxt).endAt(searchtxt
                                        + "\uf8ff"), Model.class).build();
                adapter = new FoundItemAdapter(options);
                adapter.startListening();
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String searchtxt = searchItem.getText().toString().trim();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>().
                                setQuery(FirebaseDatabase.getInstance().getReference().
                                        child("foundItems").orderByChild("itemName").startAt(searchtxt)
                                        .endAt(searchtxt + "\uf8ff"), Model.class).build();
                adapter = new FoundItemAdapter(options);
                adapter.startListening();
                recyclerView.setAdapter(adapter);

            }


            @Override
            public void afterTextChanged(Editable editable) {
                String charSequence = editable.toString();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>().
                                setQuery(FirebaseDatabase.getInstance().getReference().
                                        child("foundItems").orderByChild("itemName").startAt(charSequence)
                                        .endAt(charSequence + "\uf8ff"), Model.class).build();
                adapter = new FoundItemAdapter(options);
                adapter.startListening();
                recyclerView.setAdapter(adapter);
            }


        });
        //getting admin id from gmail
        String adminId = GoogleSignIn.getLastSignedInAccount(getContext()).getId();

        //firebase recycler options to get the data from firebase data base using model class
        //and reference
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>().
                        setQuery(FirebaseDatabase.getInstance().getReference().
                                child("foundItems"), Model.class).build();

        //setting adapter to recycler view
        adapter = new FoundItemAdapter(options);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //starts listening for data from firebase when this fragment starts
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        //stops listening for data from firebase
        adapter.stopListening();
    }
}


