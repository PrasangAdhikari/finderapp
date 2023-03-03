package com.example.finderapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finderapp.Adapters.NotificationAdapter;
import com.example.finderapp.Model.Model;
import com.example.finderapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {
    RecyclerView recyclerView;
    NotificationAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
      View view=inflater.inflate(R.layout.fragment_notification, container, false);
      //assigning the recycler view to show all the notifications
        recyclerView=(RecyclerView) view.findViewById(R.id.NotificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        String userId= GoogleSignIn.getLastSignedInAccount(getActivity()).getId();

        //Firebase recycler options to get the data from firebase database using model class and reference
        FirebaseRecyclerOptions<Model>options=
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("notification").child(userId),Model.class)
                        .build();

        //setting adapter to recycler view
        adapter=new NotificationAdapter(options);
        recyclerView.setAdapter(adapter);
        return view;
    }
    @Override
    public  void onStart()
    {
        super.onStart();
        //starts listening for data from firebase when this fragment starts
        adapter.startListening();
    }
    @Override
    public  void onStop()
    {
        super.onStop();
        //stops listening for data from firebase
        adapter.stopListening();
    }
}