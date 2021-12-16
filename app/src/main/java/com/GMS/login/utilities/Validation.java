package com.GMS.login.utilities;

import android.content.Context;
import android.content.res.ColorStateList;

import com.GMS.R;

public class Validation {
    public static boolean validate(boolean condition, com.google.android.material.textfield.TextInputLayout field, String errorText) {
        if (condition) {
            field.setError(errorText);
            field.requestFocus();
            return false;
        } else {
            field.setError("");
            return true;
        }
    }

    public static boolean validate(Context context,
                                   boolean condition,
                                   com.google.android.material.textfield.TextInputLayout field,
                                   String errorText,
                                   String helperText) {
        if (condition) {
            field.setError(errorText);
            field.requestFocus();
            return false;
        } else {
            field.setError("");
            field.setHelperText(helperText);
            field.setHelperTextColor(ColorStateList.valueOf(context.getResources().getColor(R.color.Light_See_Green)));
            return true;
        }
    }
}
