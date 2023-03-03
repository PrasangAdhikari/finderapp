package com.example.finderapp.Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
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

public class AddFoundItemFragment extends Fragment {
    EditText foundItemNameEditTxt,foundItemColorEditTxt,foundItemLocationEditTxt,foundItemPlaceEditTxt,foundItemUserEditTxt,foundItemPhoneNumberEditTxt;
    Button addItemBtn;
    private static final String ARG_PARAM1="param1";
    private static final String ARG_PARAM2="param2";
    private String mParam1;
    private String mParam2;
    public AddFoundItemFragment()
    {

    }
    public static AddFoundItemFragment newInstance(String param1,String param2)
    {
        AddFoundItemFragment fragment =new AddFoundItemFragment();
        Bundle args=new Bundle();
        args.putString(ARG_PARAM1,param1);
        args.putString(ARG_PARAM2,param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null)
        {
            mParam1=getArguments().getString(ARG_PARAM1);
            mParam2=getArguments().getString(ARG_PARAM2);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_found_item, container, false);
        //assigning all the addresses of the android materials
        foundItemNameEditTxt = (EditText) view.findViewById(R.id.foundItemName);
        foundItemColorEditTxt = (EditText) view.findViewById(R.id.foundItemColor);
        foundItemLocationEditTxt = (EditText) view.findViewById(R.id.foundItemLocation);
        foundItemPlaceEditTxt = (EditText) view.findViewById(R.id.foundItemPlace);
        foundItemUserEditTxt = (EditText) view.findViewById(R.id.foundItemUserName);
        foundItemPhoneNumberEditTxt = (EditText) view.findViewById(R.id.foundItemPhoneNumber);
        addItemBtn = (Button) view.findViewById(R.id.AddfoundItemBtn);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting the text from the editText
                String ItemName = foundItemNameEditTxt.getText().toString();
                String ItemColor = foundItemColorEditTxt.getText().toString();
                String ItemLocation = foundItemLocationEditTxt.getText().toString();
                String ItemPlace = foundItemPlaceEditTxt.getText().toString();
                String UserName = foundItemUserEditTxt.getText().toString();
                String UserPhoneNumber = foundItemPhoneNumberEditTxt.getText().toString();

                //checking for empty fields
                if (ItemName.isEmpty() || ItemColor.isEmpty() || ItemLocation.isEmpty() || ItemPlace.isEmpty()
                        || UserName.isEmpty() || UserPhoneNumber.isEmpty()) {
                    Toast.makeText(getContext(), "Please Enter All Details", Toast.LENGTH_SHORT).show();
                } else {
                    //calling the methods to add data to firebase
                    addItem(ItemName, ItemColor, ItemLocation, ItemPlace, UserName, UserPhoneNumber);
                }
            }
        });
        return view;
    }

    private void addItem( String itemName,String itemColor,String itemLocation,String itemPlace,
                          String userName,String userPhoneNumber) {
        //Hashmap to store job details
        HashMap<String ,Object>item_details=new HashMap<>();
        //getting user id
        String userId= GoogleSignIn.getLastSignedInAccount(getContext()).getId();
        //generating unique key
        String key= FirebaseDatabase.getInstance().getReference().child("foundItems").push().getKey();

        //adding found items details to hashmap
        item_details.put("itemName",itemName);
        item_details.put("itemColor",itemColor);
        item_details.put("itemLocation",itemLocation);
        item_details.put("itemPlace",itemPlace);
        item_details.put("userName",userName);
        item_details.put("userPhoneNumber",userPhoneNumber);
        item_details.put("userId",userId);

        //adding found items details to firebase
        FirebaseDatabase.getInstance().getReference().child("foundItems").child(key)
                .updateChildren(item_details).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull  @org.jetbrains.annotations.NotNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            //setting the edittext to blank after successfully adding the data to firebase
                            Toast.makeText(getContext(),"Item Details Added Successfully ",Toast.LENGTH_SHORT).show();
                            foundItemNameEditTxt.setText("");
                            foundItemColorEditTxt.setText("");
                            foundItemLocationEditTxt.setText("");
                            foundItemPlaceEditTxt.setText("");
                            foundItemUserEditTxt.setText("");
                            foundItemPhoneNumberEditTxt.setText("");

                        }
                    }
                });


    }
}