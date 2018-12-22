package com.example.yingding.fragmentnavihostexamples;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.net.Uri;
import android.os.Bundle;

import com.example.yingding.fragmentnavihostexamples.ui.main.MainViewModel;
import com.example.yingding.fragmentnavihostexamples.ui.main.PageViewFragment;

public class MainActivity extends AppCompatActivity implements PageViewFragment.OnFragmentInteractionListener{

    PageViewFragment mPageViewFragment;
    ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mActionBar = getSupportActionBar();

        final MainViewModel mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.titleLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                mActionBar.setTitle(s);
            }
        });

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
