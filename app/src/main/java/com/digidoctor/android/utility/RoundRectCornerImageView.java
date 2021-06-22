package com.digidoctor.android.utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;


public class RoundRectCornerImageView extends androidx.appcompat.widget.AppCompatImageView {
    public RoundRectCornerImageView(@NonNull Context context) {
        super(context);
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        float radius = 0.1f;
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        RoundedBitmapDrawable rid = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        rid.setCornerRadius(bitmap.getWidth() * radius);
        super.setImageDrawable(rid);
    }
}
