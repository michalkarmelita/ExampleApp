package com.michalkarmelita.testapp.picasso;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

import com.michalkarmelita.testapp.R;
import com.michalkarmelita.testapp.dagger.ForActivity;
import com.squareup.picasso.Transformation;

import javax.annotation.Nonnull;
import javax.inject.Inject;

public class RoundedTransformation implements Transformation {

    private final float mBorderSize;
    private final float mCornerRadius;

    @Inject
    public RoundedTransformation(@Nonnull @ForActivity Resources resources) {
        mCornerRadius = resources.getDimension(R.dimen.rounded_transformer_corner_radius);
        mBorderSize = resources.getDimension(R.dimen.rounded_transformer_border_size);
    }

    @Override
    public Bitmap transform(Bitmap source) {
        final Bitmap output = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, source.getWidth(), source.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = mCornerRadius;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(source, rect, rect, paint);

        // draw border
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mBorderSize);
        canvas.drawRoundRect(rectF, mCornerRadius, mCornerRadius, paint);
        //-------------------

        source.recycle();

        return output;
    }

    @Override
    public String key() {
        return  "RoundedTransformation(borderSize=" + mBorderSize + ", cornderRasius= " + mCornerRadius + ")";
    }

}