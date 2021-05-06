package com.northerly.myfragmentsapp.View.Helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.northerly.myfragmentsapp.View.Listener.MyButtonClickListener;

import java.security.PublicKey;

public class MyButton {
    Context context;
    String text;
    int textSize;
    int imageResId;
    int color;
    MyButtonClickListener listener;
    int pos = 0;
    RectF clickRegion = null;
    Resources resources;

    public MyButton(Context context, String text, int textSize, int imageResId, int color, MyButtonClickListener listener) {
        this.context = context;
        this.text = text;
        this.textSize = textSize;
        this.imageResId = imageResId;
        this.color = color;
        this.listener = listener;
        this.resources = context.getResources();
    }
    public boolean onClick(float x, float y){
        if(clickRegion != null && clickRegion.contains(x,y))
        {
            listener.onClick(pos);
            return true;
        }
        return false;
    }

    public void onDraw(Canvas c, RectF rectF, int pos)
    {
        Paint p = new Paint();
        p.setColor(color);
        c.drawRect(rectF, p);

        p.setColor(Color.WHITE);
        p.setTextSize(textSize);

        Rect r = new Rect();
        float cHeight = rectF.height();
        float cWidth = rectF.width();
        p.setTextAlign(Paint.Align.LEFT);
        p.getTextBounds(text, 0, text.length(), r);

        float x = 0f;
        float y = 0f;
        if(imageResId == 0)
        {
            x = cWidth/2f-r.width()/2f-r.left;
            y = cHeight/2f+r.height()/2f-r.bottom;
            c.drawText(text, rectF.left+x, rectF.top+y, p);
        }
        else {
             Drawable d = ContextCompat.getDrawable(context, imageResId);
            Bitmap bitmap = drawableToBitmap(d);
            c.drawBitmap(bitmap, (rectF.left+rectF.right)/2,(rectF.top+rectF.bottom)/2,p);
        }
        clickRegion = rectF;
        this.pos = pos;
    }
    public Bitmap drawableToBitmap(Drawable d){
        if(d instanceof BitmapDrawable){
            return ((BitmapDrawable) d).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        d.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        d.draw(canvas);

        return bitmap;
    }
}
