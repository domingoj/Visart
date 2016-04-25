package com.fundamentals.visart.utils;

import android.content.Context;

/**
 * Created by jermiedomingo on 3/26/16.
 */
public class PixelDpConverter {

    public static float dpFromPx(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}
