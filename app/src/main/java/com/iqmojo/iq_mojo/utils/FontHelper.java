package com.iqmojo.iq_mojo.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.iqmojo.base.utils.ShowLog;

import java.lang.reflect.Field;

/**
 * Created by shubhamlamba on 12/09/17.
 */

public class FontHelper {

    public static void applyFont(final Context context, final View view, final String fontName) {
        try {
            if (view instanceof Button) {
                ((Button) view).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
            } else if (view instanceof EditText)
                ((TextView) view).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
            else if (view instanceof TextView)
                ((TextView) view).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
        } catch (Exception e) {
            ShowLog.e("Font Helper", String.format("Error occured when trying to apply %s font for %s view", fontName, view));
            e.printStackTrace();
        }
    }

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(), fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
