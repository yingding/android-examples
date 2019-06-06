/*
Copyright 2017 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.example.android.wearable.navaction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import androidx.wear.widget.drawer.WearableActionDrawerView;
import androidx.wear.widget.drawer.WearableNavigationDrawerView;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.android.wearable.navaction.SectionFragment.Section;

/**
 * This class originally stems from the google codelab. It is modified by the author and further adopted to androidx lib and objects.
 *
 * A further manually reselect of section is added to the onResume() method by the author to demonstrate the capability to programmatically
 * set a section fragment other than the default section.
 *
 * @Auther: Yingding Wang
 *
 * Reference from
 * https://developer.android.com/training/wearables/ui/ui-nav-actions
 */
public class MainActivity extends WearableActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final Section DEFAULT_SECTION = Section.Sun;
    private static final int LOADED_SECTION_INDEX = Section.valueOf(Section.Earth.name()).ordinal();

    private WearableNavigationDrawerView mWearableNavigationDrawer;
    private WearableActionDrawerView mWearableActionDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Top navigation drawer
        mWearableNavigationDrawer =
                (WearableNavigationDrawerView) findViewById(R.id.top_navigation_drawer);
        NavigationAdapter navigationAdapter = new NavigationAdapter(this);
        mWearableNavigationDrawer.setAdapter(navigationAdapter);
        mWearableNavigationDrawer.addOnItemSelectedListener(navigationAdapter);

        // Peeks navigation drawer on the top.
        mWearableNavigationDrawer.getController().peekDrawer();

        final SectionFragment sunSection = SectionFragment.getSection(DEFAULT_SECTION);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, sunSection)
                .commit();

        // Bottom action drawer
        mWearableActionDrawer = (WearableActionDrawerView) findViewById(R.id.bottom_action_drawer);
        // Peeks action drawer on the bottom.
        // Todo: the peeked action draw doesn't hide
        // mWearableActionDrawer.getController().peekDrawer();

        mWearableActionDrawer.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                // close the action drawer when a menuItem is selected and the appropriate action is triggered.
                mWearableActionDrawer.getController().closeDrawer();
                switch (menuItem.getItemId()) {
                    case R.id.action_edit:
                        Toast.makeText(
                                MainActivity.this,
                                R.string.action_edit_todo,
                                Toast.LENGTH_SHORT)
                                .show();
                        return true;
                    case R.id.action_share:
                        Toast.makeText(
                                MainActivity.this,
                                R.string.action_share_todo,
                                Toast.LENGTH_SHORT)
                                .show();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // set the current selected item other than the default sectionFragment
        // this is called in onResume() since the ui widget and all resource shalled be loaded ahead in onCreate() method.
        mWearableNavigationDrawer.setCurrentItem(LOADED_SECTION_INDEX, false);
    }

    private final class NavigationAdapter
            extends WearableNavigationDrawerView.WearableNavigationDrawerAdapter implements WearableNavigationDrawerView.OnItemSelectedListener {

        private final Context mContext;
        private Section mCurrentSection = DEFAULT_SECTION;

        NavigationAdapter(final Context context) {
            mContext = context;
        }

        @Override
        public String getItemText(int index) {
            return mContext.getString(SectionFragment.Section.values()[index].titleRes);
        }

        @Override
        public Drawable getItemDrawable(int index) {
            return mContext.getDrawable(SectionFragment.Section.values()[index].drawableRes);
        }

        /**
         * OnItemSelectedListener must be added to the WearableNaviationDrawerView explicitly.
         *
         * This OnItemSelectedListener can either be implemented by the activity or by the private NavigationAdapter.
         *
         * @param pos
         */
        @Override
        public void onItemSelected(int pos) {
            SectionFragment.Section selectedSection = SectionFragment.Section.values()[pos];

            // Only replace the fragment if the section is changing.
            if (selectedSection == mCurrentSection) {
                return;
            }
            mCurrentSection = selectedSection;

            final SectionFragment sectionFragment = SectionFragment.getSection(selectedSection);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, sectionFragment)
                    .commit();

            // No actions are available for the settings specific fragment,
            // so the drawer is closed and also locked.
            // For all other SelectionFragments, the action drawer is available and unlocked.
            if (selectedSection == SectionFragment.Section.Settings) {
                mWearableActionDrawer.getController().closeDrawer();
                mWearableActionDrawer.setIsLocked(true);
                //lockDrawerClosed();
            } else {
                mWearableActionDrawer.setIsLocked(false); // unlockDrawer();
            }
        }

        @Override
        public int getCount() {
            return SectionFragment.Section.values().length;
        }
    }
}
