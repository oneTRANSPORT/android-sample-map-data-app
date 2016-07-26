package com.interdigital.android.samplemapdataapp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;

import com.interdigital.android.samplemapdataapp.MapsActivity;

import java.util.ArrayList;

public class MicroGraphView extends View {

    private Paint paint = new Paint();
    private SparseIntArray values;
    private ArrayList<Float> xs = new ArrayList<>();
    private ArrayList<Float> ys = new ArrayList<>();

    public MicroGraphView(Context context) {
        this(context, null, 0);
    }

    public MicroGraphView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MicroGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialise();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (values != null) {
            float w = 80 * MapsActivity.density;
            float h = 16 * MapsActivity.density;
            Log.i("MicroGraphView", "w x h = " + w + " x " + h); // Weird.
            float startTime = values.keyAt(0);
            float endTime = values.keyAt(values.size() - 1);
            float interval = endTime - startTime;
            int minY = Integer.MAX_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (int i = 0; i < values.size(); i++) {
                int y = values.valueAt(i);
                if (minY > y) {
                    minY = y;
                }
                if (maxY < y) {
                    maxY = y;
                }
            }
            float yRange = maxY - minY;
            xs.clear();
            ys.clear();
            for (int i = 0; i < values.size(); i++) {
                xs.add((values.keyAt(i) - startTime) * w * 0.7f / interval + w * 0.15f);
                ys.add(h - ((values.valueAt(i) - minY) * h * 0.6f / yRange + h * 0.2f));
            }
//            values = null;  TODO    Put this back?
        }

        if (xs.size() > 0) {
            for (int i = 0; i < xs.size() - 1; i++) {
                canvas.drawLine(xs.get(i), ys.get(i), xs.get(i + 1), ys.get(i + 1), paint);
            }
        }
    }

    public void setValues(SparseIntArray values) {
        this.values = values;
        invalidate();
    }

    private void initialise() {
        paint.setAntiAlias(true);
        paint.setDither(false);
        paint.setStrokeWidth(2);
        paint.setColor(0xff0000c0);
    }
}
