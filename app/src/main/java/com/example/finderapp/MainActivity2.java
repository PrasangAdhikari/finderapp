package com.example.finderapp;

import static com.example.finderapp.R.id.homeMenu;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {
    FrameLayout frameLayout;
    NavigationBarView navigationBarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //assigning frame layout resource file to show fragments
        frameLayout=(FrameLayout) findViewById(R.id.FragmentContainer);
        //assigning bottom navigation menu
        navigationBarView= (BottomNavigationView) findViewById(R.id.BottomNavigationView);
        Menu menuNav=navigationBarView.getMenu();
        //setting the default fragment as home fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainer,new homeFragment()).commit();

        //calling the bottom navigation method when we click on any menu item

        navigationBarView.setOnItemSelectedListener(NavigationMethod);
    }
    private  NavigationBarView.OnItemSelectedListener NavigationMethod=
            new NavigationBarView.OnItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {
                    //assigning fragment as null
                    Fragment fragment = null;
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
                    //sets the selected Fragment into the frame layout

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
            Intent intent=new Intent(MainActivity2.this,StartingActivity.class);
            startActivity(intent);

        }

    }
}

