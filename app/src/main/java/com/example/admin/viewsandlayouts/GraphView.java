package com.example.admin.viewsandlayouts;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Admin on 9/7/2017.
 */

public class GraphView extends View{

    int lineColor;
    int borderColor;
    int xAxisSize = 0;
    int yAxisSize = 0;
    Rect rect;
    Path linePath;


    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GraphView,0,0);
        lineColor = typedArray.getInt(R.styleable.GraphView_lineColor, 0);
        borderColor = typedArray.getInt(R.styleable.GraphView_borderColor, 0);



        typedArray.recycle();
    }

    public void setAttributes(ArrayList<Coordinate> points, int xAxisSize, int yAxisSize){
        this.xAxisSize = xAxisSize;
        this.yAxisSize = yAxisSize;

        this.linePath = getPathFromCoordinates(points);

        invalidate();
        requestLayout();
    }

    private Path getPathFromCoordinates(ArrayList<Coordinate> points){
        Path path = new Path();
        path.moveTo(0, yAxisSize);

        float xMax = 0;
        float yMax = 0;

        //sort the list by increasing x values
        Collections.sort(points, new Comparator<Coordinate>(){
            @Override
            public int compare(Coordinate p1, Coordinate p2) {
                if (p1.getX() < p2.getX()) return -1;
                if (p1.getX() > p2.getX()) return 1;
                return 0;
            }
        });

        //get the max x and y values
        xMax = points.get(points.size()-1).getX();
        for(Coordinate point : points){
            if(point.getY() > yMax){
                yMax = point.getY();
            }
        }

        //add the paths and scale each path as a ratio of the frame size
        for(Coordinate point : points){

            float xOut = ((point.getX()/xMax) * xAxisSize);
            float yOut = ((point.getY()/yMax) * yAxisSize);

            Log.d("Graph:", "getPathFromCoordinates: " + xOut + ", " + yOut);
            path.lineTo(xOut, yAxisSize - yOut);

        }
        return path;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(5);
        p.setColor(borderColor);
        rect = new Rect(0, 0, 0+xAxisSize, 0+yAxisSize);
        canvas.drawRect(rect, p);

        Paint l = new Paint();
        l.setColor(lineColor);
        l.setStyle(Paint.Style.STROKE);
        l.setStrokeWidth(2);

        /*Path path = new Path();
        path.moveTo(0,yAxisSize);
        path.lineTo(100,100);*/
        canvas.drawPath(linePath, l);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(xAxisSize,yAxisSize);
    }

}
