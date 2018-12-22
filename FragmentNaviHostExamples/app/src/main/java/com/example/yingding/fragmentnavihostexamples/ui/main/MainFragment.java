package com.example.yingding.fragmentnavihostexamples.ui.main;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yingding.fragmentnavihostexamples.R;
import com.example.yingding.fragmentnavihostexamples.ui.base.BaseFragment;

public class MainFragment extends BaseFragment {

    private MainViewModel mViewModel;
    // private final static String mFragmentTitle = MainFragment.class.getSimpleName();

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected String TAG() {
        return MainFragment.class.getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Setup any handles to view objects here
        Button transitionButton = view.findViewById(R.id.button);
        transitionButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainFragment_to_pageViewFragment, null));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.setTitle(TAG());
    }


}
