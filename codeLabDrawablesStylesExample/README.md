# Introduction

this repository is the solution template of [Drawables, styples, and themes codelab](https://codelabs.developers.google.com/codelabs/android-training-drawables-styles-and-themes/#0), the code is adapter to CoordinatorLayout and ConstraintLayout with Legacy Layouts (LinearLayout and RelativeLayout) by the author of this repository.

## Tipps

### 1.Remove the shadow under the actionbar

Reference:

* https://stackoverflow.com/questions/12246388/remove-shadow-below-actionbar
* https://stackoverflow.com/a/27203224

XML Style change for androidx AppCompat
```xml
    <style name="AppTheme.AppBarOverlay" parent="ThemeOverlay.AppCompat.Dark.ActionBar">
        <!-- remove shadow below action bar -->
        <!-- <item name="android:elevation">0dp</item> -->
        <!-- Support library compatibility -->
        <item name="elevation">0dp</item>
    </style>
```

