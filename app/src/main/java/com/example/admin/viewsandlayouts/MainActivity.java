package com.example.admin.viewsandlayouts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GraphView myGraph = (GraphView) findViewById(R.id.myGraph);
        ArrayList<Coordinate> points = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i < 1000; i++){
            Coordinate point = new Coordinate(i, rand.nextFloat());
            points.add(point);
        }
        myGraph.setAttributes(points, 800, 400);
    }
}
