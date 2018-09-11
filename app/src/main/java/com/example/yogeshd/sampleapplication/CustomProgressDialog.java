package com.example.yogeshd.sampleapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;

/**
 * Created by yogeshd on 9/11/2018.
 */

public class CustomProgressDialog
{
    private static ProgressDialog pd;

    /**
     * This method shows progress dialog (after dismissing existing on screen)
     *
     * @param context
     * @param title
     * @param message
     */
    public static void show(Context context, @Nullable String title, String message) {
        dismiss();
        pd = new ProgressDialog(context);
        pd.setMessage(message);
        pd.setTitle(title);
        pd.setCancelable(false);
        pd.show();
    }

    /**
     * This method dismisses progress dialog shown on screen
     */
    public static void dismiss() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd = null;
        }
    }


}
