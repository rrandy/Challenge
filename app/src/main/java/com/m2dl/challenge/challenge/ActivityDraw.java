/*
 * Copyright (c) 2015 Marine Carrara, Akana Mao, Randy Ratsimbazafy
 *
 * This file is part of Tracer c'est gagné.
 *
 * Tracer c'est gagné is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tracer c'est gagné is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Tracer c'est gagné.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.m2dl.challenge.challenge;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ActivityDraw extends ActionBarActivity {

    DrawingView dv;
    private Paint mPaint;
//    private DrawingManager mDrawingManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_draw);

        dv = new DrawingView(this);
//        setContentView(dv);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutDraw);
        linearLayout.addView(dv, 2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_draw, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }

    public class DrawingView extends ImageView {

        public int width;
        public int height;
        private Bitmap mBitmap;
        private Canvas mCanvas;
        private Path mPath;
        private Paint mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;
        List<Path> lmp;
        private int startX;
        private int startY;
        Paint paint = new Paint();
        ArrayList<Line> lines = new ArrayList<Line>();
        String linesCoord = "";

        public void setStyle(Paint p) {
            p.setAntiAlias(true);
            p.setDither(true);
            p.setColor(Color.GREEN);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeJoin(Paint.Join.ROUND);
            p.setStrokeCap(Paint.Cap.ROUND);
            p.setStrokeWidth(12);
        }

        public DrawingView(Context c) {
            super(c);
            context = c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);
            lmp = new ArrayList<>();

            setStyle(paint);

        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            // Fichier photo
            //        File photo = new File(filePath);
            File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");

            // Mettre la photo dans l'imageView
            Uri selectedImage = Uri.fromFile(photo);
            ContentResolver cr = getContentResolver();
            try {
                mBitmap = android.provider.MediaStore.Images.Media
                        .getBitmap(cr, selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCanvas = new Canvas();
            this.setImageBitmap(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

//            canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);

            String COORD_SEP = ",";
            String LINES_SEP = ";";
            linesCoord = "";
            for (Line l : lines) {
                canvas.drawLine(l.startX, l.startY, l.stopX, l.stopY, paint);
                linesCoord += l.startX + COORD_SEP + l.startY + COORD_SEP + l.stopX + COORD_SEP + l.stopY + LINES_SEP ;
            }

            if (lines.size() == 3){
                Intent intent = new Intent(this.getContext(), ActivityGuess.class);
                intent.putExtra("points", linesCoord);
                intent.putExtra("dimX", this.getMeasuredHeight()+"");
                intent.putExtra("dimY", this.getMeasuredWidth()+"");
                startActivity(intent);
            }


            Toast.makeText(context, linesCoord, Toast.LENGTH_LONG).show();


//            canvas.drawPath( mPath,  mPaint);

//            canvas.drawPath( circlePath,  circlePaint);

//            for (Path p : lmp){
//                canvas.drawPath(p, mPaint);
//            }
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
//                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                //                mPath.lineTo(x,y);

                mX = x;
                mY = y;
//                mPath.lineTo(x,y);

                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath, mPaint);
            // kill this so we don't double draw
            lmp.add(new Path(mPath));
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                lines.add(new Line(event.getX(), event.getY()));
                return true;
            } else if ((event.getAction() == MotionEvent.ACTION_MOVE ||
                    event.getAction() == MotionEvent.ACTION_UP) &&
                    lines.size() > 0) {
                Line current = lines.get(lines.size() - 1);
                current.stopX = event.getX();
                current.stopY = event.getY();
                invalidate();
                return true;
            } else {
                return false;
            }

//            float x = event.getX();
//            float y = event.getY();
//
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    touch_start(x, y);
//                    invalidate();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    touch_move(x, y);
//                    invalidate();
//                    break;
//                case MotionEvent.ACTION_UP:
//                    touch_up();
//                    invalidate();
//                    break;
//            }
//            return true;
        }
    }

    class Line {
        float startX, startY, stopX, stopY;

        public Line(float startX, float startY, float stopX, float stopY) {
            this.startX = startX;
            this.startY = startY;
            this.stopX = stopX;
            this.stopY = stopY;
        }

        public Line(float startX, float startY) { // for convenience
            this(startX, startY, startX, startY);
        }
    }



}

