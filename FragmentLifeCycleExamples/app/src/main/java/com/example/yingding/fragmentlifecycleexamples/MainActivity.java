package com.example.yingding.fragmentlifecycleexamples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.example.yingding.fragmentlifecycleexamples.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity implements PageViewFragment.OnFragmentInteractionListener{

    PageViewFragment mPageViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
              // The Navigation Host is doing the rendering of the view
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, MainFragment.newInstance())
//                    .commitNow();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // do some thing
    }
}
