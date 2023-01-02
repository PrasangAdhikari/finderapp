package com.example.finderapp.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.finderapp.Fragments.AddItemFragment;
import com.example.finderapp.Fragments.NotificationFragment;
import com.example.finderapp.Fragments.ProfileFragment;
import com.example.finderapp.Fragments.SearchItemFragment;
import com.example.finderapp.Fragments.homeFragment;
import com.example.finderapp.R;
import com.example.finderapp.StartingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
FrameLayout frameLayout;
BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //assigning framelayout resource file to show fragments
        frameLayout=(FrameLayout) findViewById(R.id.FragmentContainer);
        //assigning bottomnavigation menu
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.BottomNavigationView);
        Menu menuNav=bottomNavigationView.getMenu();
        //setting the default fragment as homefragment
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,new homeFragment()).commit();
        //calling the bottomnavigationmethod when we click on any menu item

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                   //assigning fragment as null
                    Fragment fragment=null;
                    switch (item.getItemId())
                    {
                        //shows the appropriate fragment by using id as address

                        case R.id.homeMenu:
                            fragment=new homeFragment();
                            break;
                        case R.id.searchMenu:
                            fragment=new SearchItemFragment();
                            break;
                        case R.id.addItemMenu:
                            fragment=new AddItemFragment();
                            break;
                        case R.id.notificationMenu:
                            fragment=new NotificationFragment();
                            break;
                        case R.id.profileMenu:
                            fragment=new ProfileFragment();
                            break;
                    }
                    //sets the selected Fragment into the framelayout
                    getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,fragment)
                            .commit();
                    return true;
                }
            };
    @Override
    protected void onStart()
    {
        super.onStart();
        //checking user already login or not
        FirebaseApp.initializeApp(this);
        FirebaseUser mUser= FirebaseAuth.getInstance().getCurrentUser();
        if (mUser==null) {
            //if user not signed in then we will redirect this activity to login activity
            Intent intent=new Intent(MainActivity.this, StartingActivity.class);
            startActivity(intent);

            }

        }
    }

