package com.github.dschreid.learningapp.common;

import android.content.Context;
import android.widget.EditText;

import com.github.dschreid.learningapp.R;

public class TextFieldUtils {
    /**
     * Checks if the Text Field is not empty.
     * If it's empty it will mark it with a corresponding error message
     *
     * @author dschreid
     */
    public static boolean checkAndGiveFeedback(Context context, EditText text) {
        if (text == null) return true;

        if (text.getText() == null || text.getText().toString().isEmpty()) {
            text.setError(context.getString(R.string.input_error_not_empty));
            return true;
        }

        return false; // All Good
    }
}
