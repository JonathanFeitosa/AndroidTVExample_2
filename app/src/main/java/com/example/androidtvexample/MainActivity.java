package com.example.androidtvexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

public class MainActivity extends FragmentActivity {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
          //  Fragment fragment = new MainFragment();
         //   getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment)
         // //          .commit();
        }
    }
}
