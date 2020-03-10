package com.google.codelabs.mdc.java.shrine;

import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Fragment representing the login screen for Shrine.
 */
public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.shr_login_fragment, container, false);

        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);
        MaterialButton nextButton = view.findViewById(R.id.next_button);


        // Snippet from "Navigate to the next Fragment" section goes here.
        // Set an error if the password is less than 8 cgaracters.
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(getString(R.string.shr_error_password));
                } else {
                    passwordTextInput.setError(null); // Clear the error
                    // login and navigate to another fragment
                    ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false);
                    // false is not adding the current transition (login to productGridFragment into the backstack ),
                    // user can not back to login with back button.

                }
            }
        });

        // Clear the error once more than 8 characters are typed.
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(null); // Clear the error
                }
                return false;
            }
        });

        return view;
    }

    // "isPasswordValid" from "Navigate to the next Fragment" section method goes here
    /*
     * In reality, this will have more complex logic including, but not limited to, actual
     * authentication of the username and password.
     */
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }
}
