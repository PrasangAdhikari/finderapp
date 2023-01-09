package com.example.finderapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.finderapp.Model.Model;
import com.example.finderapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class FoundItemAdapter extends FirebaseRecyclerAdapter<Model,FoundItemAdapter.Viewholder>{
    public FoundItemAdapter (@NonNull FirebaseRecyclerOptions<Model>options){
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FoundItemAdapter.Viewholder holder, int position, @NonNull Model model) {
        Context context=holder.itemName.getContext();
//for loading found items into recycler view
        holder.itemName.setText(model.getItemName());
        holder.itemColor.setText(model.getItemColor());
        holder.itemLocation.setText(model.getItemLocation());
        holder.itemPlace.setText(model.getItemPlace());
        holder.userName.setText(model.getUserName());
        holder.userPhoneNumber.setText(model.getUserPhoneNumber());
        holder.sendFoundNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userId=model.getUserId();
                String key= FirebaseDatabase.getInstance().getReference().child("notification").push().getKey();
                HashMap<String,Object> sendDetails=new HashMap<>();

                //getting user details from GoogleSignin
                String userName= GoogleSignIn.getLastSignedInAccount(view.getContext()).getDisplayName();
                String gmail=GoogleSignIn.getLastSignedInAccount(view.getContext()).getEmail();

                //adding details to hashmap
                sendDetails.put("userName",userName);
                sendDetails.put("gmailId",gmail);
                sendDetails.put("itemName",model.getItemName());
                sendDetails.put("itemLocation",model.getItemLocation());
                sendDetails.put("message","that item is mine");

                //adding the notification to firebase
                FirebaseDatabase.getInstance().getReference().child("notifications").child(userId)
                        .child(key).updateChildren(sendDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull  @NotNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    //toast to show the message
                                    Toast.makeText(context, "Item Found Notification Sent", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    @NonNull
    @Override
    public FoundItemAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //the data objects are inflated into the xml file single_data_item
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_data_file,parent,false);


        return new FoundItemAdapter.Viewholder(view);
    }

    // we need view holder to hold each object form recyclerview and to show it in recyclerview
    class Viewholder extends RecyclerView.ViewHolder{
        TextView itemName,itemColor,itemLocation,itemPlace,userName,userPhoneNumber;
        Button sendFoundNotificationBtn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            //assigning the address of the materials
            itemName=(TextView) itemView.findViewById(R.id.ItemNameTxt);
            itemColor=(TextView) itemView.findViewById(R.id.ItemColorTxt);
            itemLocation=(TextView) itemView.findViewById(R.id.ItemLocationTxt);
            itemPlace=(TextView) itemView.findViewById(R.id.ItemPlaceTxt);
            userName=(TextView) itemView.findViewById(R.id.UserNameTxt);
            userPhoneNumber=(TextView) itemView.findViewById(R.id.UserPhoneNumberTxt);
            sendFoundNotificationBtn=(Button) itemView.findViewById(R.id.SendNotificationBtn);
        }

    }
}
