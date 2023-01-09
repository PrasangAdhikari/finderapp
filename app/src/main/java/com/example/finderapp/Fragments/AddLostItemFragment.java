package com.example.finderapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finderapp.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddLostItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddLostItemFragment extends Fragment {
    EditText lostItemNameEditTxt,lostItemColorEditTxt,lostItemLocationEditTxt,lostItemPlaceEditTxt,
    lostItemUserEditTxt,lostItemPhoneNumberEditTxt;
    Button addItemBtn;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddLostItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddLostItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddLostItemFragment newInstance(String param1, String param2) {
        AddLostItemFragment fragment = new AddLostItemFragment();
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
        View view=inflater.inflate(R.layout.fragment_add_lost_item, container, false);
        //assigning all the addresses of the android materials

        lostItemNameEditTxt=(EditText) view.findViewById(R.id.LostItemName);
        lostItemColorEditTxt=(EditText) view.findViewById(R.id.LostItemColor);
        lostItemLocationEditTxt=(EditText) view.findViewById(R.id.LostItemLocation);
        lostItemPlaceEditTxt=(EditText) view.findViewById(R.id.LostItemPlace);
        lostItemUserEditTxt=(EditText) view.findViewById(R.id.LostItemUserName);
        lostItemPhoneNumberEditTxt=(EditText) view.findViewById(R.id.LostItemPhoneNumber);
        addItemBtn=(Button) view.findViewById(R.id.AddLostItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getting text from edittext fields to add data to firebase
                String ItemName=lostItemNameEditTxt.getText().toString();
                String ItemColor=lostItemColorEditTxt.getText().toString();
                String ItemLocation=lostItemLocationEditTxt.getText().toString();
                String ItemPlace=lostItemPlaceEditTxt.getText().toString();
                String UserName=lostItemUserEditTxt.getText().toString();
                String UserPhoneNumber=lostItemPhoneNumberEditTxt.getText().toString();

                //checking for empty field
                if (ItemName.isEmpty()||ItemColor.isEmpty()||ItemLocation.isEmpty()||
                ItemPlace.isEmpty()||UserName.isEmpty()||UserPhoneNumber.isEmpty())
                {
                    Toast.makeText(getContext(), "please enter all details", Toast.LENGTH_SHORT).show();

                }
                else {
                    //calling the method to add data to firebase
                    addItem(ItemName,ItemColor,ItemLocation,ItemPlace,UserName,UserPhoneNumber);

                }

            }
        });
        return view;
    }
    private void addItem(String itemName,String itemColor,String itemLocation,
    String itemPlace,String userName,String userPhoneNumber)
    {
        //hashmap to store lost items details
        HashMap<String,Object>item_details=new HashMap<>();
        //getting user id
        String userId= GoogleSignIn.getLastSignedInAccount(getContext()).getId();
        //generating unique key
        String key= FirebaseDatabase.getInstance().getReference().child("lostItems").push().getKey();
        //adding lost items details to hashmap
        item_details.put("itemName",itemName);
        item_details.put("itemColor",itemColor);
        item_details.put("itemLocation",itemLocation);
        item_details.put("itemPlace",itemPlace);
        item_details.put("userName",userName);
        item_details.put("userPhoneNumber",userPhoneNumber);
        item_details.put("userId",userId);

        //adding lost items details to firebase
        FirebaseDatabase.getInstance().getReference().child("lostItems")
                .child(key).updateChildren(item_details).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            //setting the edit text to blank after successfully adding the data to firebase
                            Toast.makeText(getContext(),"Item Details added Successfully",Toast.LENGTH_SHORT).show();
                            lostItemNameEditTxt.setText("");
                            lostItemColorEditTxt.setText("");
                           lostItemLocationEditTxt.setText("");
                           lostItemPlaceEditTxt.setText("");
                            lostItemUserEditTxt.setText("");
                            lostItemPhoneNumberEditTxt.setText("");
                        }
                    }
                });
    }
}
