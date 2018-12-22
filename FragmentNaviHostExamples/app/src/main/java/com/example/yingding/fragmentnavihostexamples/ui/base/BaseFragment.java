package com.example.yingding.fragmentnavihostexamples.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.logging.Logger;
import java.util.logging.LoggingPermission;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Reference to the Fragment LifeCycle:
 * https://developer.android.com/guide/components/fragments#Creating
 */
public abstract class BaseFragment extends Fragment {

    protected abstract String TAG();
    private static final boolean IS_LOGGING_ENABLED = true;

    /**
     * Simple wrapper around Log.d
     * @param tag
     * @param message
     */
    protected static void LOGD(final String tag, String message) {
        // https://stackoverflow.com/questions/7948204/does-log-isloggable-returns-wrong-values
        if (IS_LOGGING_ENABLED && Log.isLoggable(tag, Log.INFO)) {
            Log.i(tag, message);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LOGD(TAG(), "onAttach() called!");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LOGD(TAG(), "onCreateView() called!");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LOGD(TAG(), "onViewCreated() called!");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LOGD(TAG(), "onActivityCreated() called!");
    }

    @Override
    public void onStart() {
        super.onStart();
        LOGD(TAG(), "onStart() called!");
    }

    @Override
    public void onResume() {
        super.onResume();
        LOGD(TAG(), "onResume() called!");
    }

    @Override
    public void onPause() {
        super.onPause();
        LOGD(TAG(), "onPause() called!");
    }

    @Override
    public void onStop() {
        super.onStop();
        LOGD(TAG(), "onStop() called!");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LOGD(TAG(), "onDestroyView() called!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LOGD(TAG(), "onDestroy() called!");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LOGD(TAG(), "onDetach() called!");
    }
}
