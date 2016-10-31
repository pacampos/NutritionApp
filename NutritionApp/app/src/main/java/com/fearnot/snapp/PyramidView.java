package com.fearnot.snapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.fearnot.snapp.R;

/**
 * Created by phoenixcampos01 on 10/13/16.
 */

public class PyramidView extends View {
    private Paint mPaint;
    private int pyramidThickness;
    private float firstPortion = 0f;
    private float secondPortion = 0f;
    private float thirdPortion = 0f;
    private float fourthPortion = 0f;
    private float fifthPortion = 0f;

    public PyramidView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();
        pyramidThickness=2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /* we want to the draw the outside of the pyramid always, because it will always be there
            no matter what
         */
        drawOuterTriangle(canvas);
        drawInnerLines(canvas);
        if(firstPortion > 0f){
            drawFirstSection(canvas);
        }

        if(secondPortion > 0f){
            drawSecondSection(canvas);
        }

        if(thirdPortion > 0f){
            drawThirdSection(canvas);
        }

        if(fourthPortion > 0f){
            drawFourthSection(canvas);
        }

        if(fifthPortion > 0f){
            drawFifthSection(canvas);
        }



        drawFirstLabel(canvas);
        drawSecondLabel(canvas);
        drawThirdLabel(canvas);
        drawFourthLabel(canvas);
        drawFifthLabel(canvas);
        drawGrainImage(canvas);
        drawVeggieImage(canvas);
        drawFruitImage(canvas);
        drawDairyImage(canvas);
        drawMeatImage(canvas);
    }

    private void drawOuterTriangle(Canvas canvas){

        mPaint.setStrokeWidth(dpToPixels(pyramidThickness));
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        Path outerPath=new Path();

        outerPath.moveTo(getLeft()+(getWidth()/2), 0);

        /* draw outside triangle */
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        outerPath.lineTo(getLeft(), heightOfTriangle);
        outerPath.lineTo(getRight(), heightOfTriangle);
        outerPath.close();

        canvas.drawPath(outerPath, mPaint);
    }

    private void drawInnerLines(Canvas canvas){
        mPaint.setColor(Color.WHITE);

        Path path = new Path();
        /* this is a calculation based on the width of the view to get an equilateral triangle */
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));

        /* creating inner white triangle */
        path.moveTo(getLeft()+(getWidth()/2), dpToPixels(pyramidThickness*2));
        path.lineTo(getLeft()+dpToPixels(pyramidThickness*2), heightOfTriangle-dpToPixels(pyramidThickness));
        path.lineTo(getRight()-dpToPixels(pyramidThickness*2), heightOfTriangle-dpToPixels(pyramidThickness));
        path.lineTo(getLeft()+(getWidth()/2), dpToPixels(pyramidThickness*2));

        /* now we draw the outlines of the sections of the triangle from left to right */
        /* draw first section of triangle */
        path.moveTo(getLeft()+(getWidth()/5), heightOfTriangle-dpToPixels(pyramidThickness));
        path.lineTo(getLeft()+(getWidth()/2), dpToPixels(pyramidThickness*2));
        /* draw second section of triangle */
        path.moveTo(getLeft()+(2*(getWidth()/5)), heightOfTriangle-dpToPixels(pyramidThickness));
        path.lineTo(getLeft()+(getWidth()/2), dpToPixels(pyramidThickness*2));
        /* draw third section of triangle */
        path.moveTo(getLeft()+(3*(getWidth()/5)), heightOfTriangle-dpToPixels(pyramidThickness));
        path.lineTo(getLeft()+(getWidth()/2), dpToPixels(pyramidThickness*2));
        /* draw fourth section of triangle */
        path.moveTo(getLeft()+(4*(getWidth()/5)), heightOfTriangle-dpToPixels(pyramidThickness));
        path.lineTo(getLeft()+(getWidth()/2), dpToPixels(pyramidThickness*2));
        /* draw fifth section of triangle */
        path.moveTo(getLeft()+(5*(getWidth()/5))-dpToPixels(pyramidThickness*2), heightOfTriangle-dpToPixels(pyramidThickness));
        path.lineTo(getLeft()+(getWidth()/2), dpToPixels(pyramidThickness*2));

        canvas.drawPath(path,mPaint);
    }

    private void drawFirstSection(Canvas canvas){
        mPaint.setColor(Color.argb(255,255,165,0));
        mPaint.setStyle(Paint.Style.FILL);

        Path path=new Path();
        /* this is a calculation based on the width of the view to get an equilateral triangle */
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));

        float triangleTipX = getLeft()+(getWidth()/2)-dpToPixels(pyramidThickness*2);
        float triangleTipY = dpToPixels(pyramidThickness*6);

        float bottomLeftEdgeX = getLeft()+dpToPixels(pyramidThickness*3);
        float bottomLeftEdgeY = (heightOfTriangle-dpToPixels(pyramidThickness+1));

        float bottomRightEdgeX=getLeft()+(getWidth()/5)-dpToPixels(pyramidThickness-1) ;
        float bottomRightEdgeY = (heightOfTriangle-dpToPixels(pyramidThickness+1));

        /* we use a generalization of the midpoint formula to find the approriate coordinate along this
            line segment
         */

        float percentage;
        if(firstPortion >1f){
            percentage=1f;
        }

        else{
            percentage=firstPortion;
        }
        float progressBottomLeftEdgeX = triangleTipX + ((bottomLeftEdgeX-triangleTipX)*percentage);
        float progressBottomLeftEdgeY = triangleTipY + ((bottomLeftEdgeY-triangleTipY)*percentage);

        float progressBottomRightEdgeX= findXOnLine(triangleTipX, triangleTipY, bottomRightEdgeX, bottomRightEdgeY, progressBottomLeftEdgeY);

         /* draw first section of triangle */
        path.moveTo(triangleTipX,  triangleTipY);
        path.lineTo( progressBottomLeftEdgeX, progressBottomLeftEdgeY);
        path.lineTo(progressBottomRightEdgeX, progressBottomLeftEdgeY) ;
        path.close();
        canvas.drawPath(path,mPaint);
    }

    private void drawSecondSection(Canvas canvas){
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);

        Path path=new Path();
        /* this is a calculation based on the width of the view to get an equilateral triangle */
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));


        float triangleTipX = getLeft()+(getWidth()/2)-dpToPixels(pyramidThickness);
        float triangleTipY = dpToPixels(pyramidThickness*6);

        float bottomLeftEdgeX =getLeft()+(getWidth()/5)+dpToPixels(pyramidThickness);
        float bottomLeftEdgeY = (heightOfTriangle-dpToPixels(pyramidThickness+1));

        float bottomRightEdgeX=getLeft()+(2*(getWidth()/5))-dpToPixels(pyramidThickness-1) ;
        float bottomRightEdgeY = (heightOfTriangle-dpToPixels(pyramidThickness+1));

        /* we use a generalization of the midpoint formula to find the approriate coordinate along this
            line segment
         */
        float percentage;
        if(secondPortion >1f){
            percentage=1f;
        }

        else{
            percentage=secondPortion;
        }
        float progressBottomLeftEdgeX = triangleTipX + ((bottomLeftEdgeX-triangleTipX)*percentage);
        float progressBottomLeftEdgeY = triangleTipY + ((bottomLeftEdgeY-triangleTipY)*percentage);


        float progressBottomRightEdgeX= findXOnLine(triangleTipX, triangleTipY, bottomRightEdgeX, bottomRightEdgeY, progressBottomLeftEdgeY);

         /* draw second section of triangle */
        path.moveTo(triangleTipX,  triangleTipY);
        path.lineTo( progressBottomLeftEdgeX, progressBottomLeftEdgeY);
        path.lineTo(progressBottomRightEdgeX, progressBottomLeftEdgeY) ;
        path.close();
        canvas.drawPath(path,mPaint);
    }

    private void drawThirdSection(Canvas canvas){
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);

        Path path=new Path();
        /* this is a calculation based on the width of the view to get an equilateral triangle */
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));


        float triangleTipX = getLeft()+(getWidth()/2);
        float triangleTipY = dpToPixels(pyramidThickness*6);

        float bottomLeftEdgeX =getLeft()+(2*(getWidth()/5))+dpToPixels(pyramidThickness);
        float bottomLeftEdgeY = (heightOfTriangle-dpToPixels(pyramidThickness+1));

        float bottomRightEdgeX=getLeft()+(3*(getWidth()/5))-dpToPixels(pyramidThickness) ;
        float bottomRightEdgeY = (heightOfTriangle-dpToPixels(pyramidThickness+1));

        /* we use a generalization of the midpoint formula to find the approriate coordinate along this
            line segment
         */
        float percentage;
        if(thirdPortion >1f){
            percentage=1f;
        }

        else{
            percentage=thirdPortion;
        }
        float progressBottomLeftEdgeX = triangleTipX + ((bottomLeftEdgeX-triangleTipX)*percentage);
        float progressBottomLeftEdgeY = triangleTipY + ((bottomLeftEdgeY-triangleTipY)*percentage);

        float progressBottomRightEdgeX= findXOnLine(triangleTipX, triangleTipY, bottomRightEdgeX, bottomRightEdgeY, progressBottomLeftEdgeY);

         /* draw third section of triangle */
        path.moveTo(triangleTipX,  triangleTipY);
        path.lineTo( progressBottomLeftEdgeX, progressBottomLeftEdgeY);
        path.lineTo(progressBottomRightEdgeX, progressBottomLeftEdgeY) ;
        path.close();

        canvas.drawPath(path,mPaint);
    }

    private void drawFourthSection(Canvas canvas){
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);

        Path path=new Path();
        /* this is a calculation based on the width of the view to get an equilateral triangle */
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));

        float triangleTipX = getLeft()+(getWidth()/2)+dpToPixels(pyramidThickness);
        float triangleTipY = dpToPixels(pyramidThickness*6);

        float bottomLeftEdgeX =getLeft()+(3*(getWidth()/5))+dpToPixels(pyramidThickness);
        float bottomLeftEdgeY = (heightOfTriangle-dpToPixels(pyramidThickness+1));

        float bottomRightEdgeX=getLeft()+(4*(getWidth()/5))-dpToPixels(pyramidThickness);
        float bottomRightEdgeY =  (heightOfTriangle-dpToPixels(pyramidThickness+1));

        /* we use a generalization of the midpoint formula to find the approriate coordinate along this
            line segment
         */

        float percentage;
        if(fourthPortion >1f){
            percentage=1f;
        }

        else{
            percentage=fourthPortion;
        }
        float progressBottomLeftEdgeX = triangleTipX + ((bottomLeftEdgeX-triangleTipX)*percentage);
        float progressBottomLeftEdgeY = triangleTipY + ((bottomLeftEdgeY-triangleTipY)*percentage);

        float progressBottomRightEdgeX= findXOnLine(triangleTipX, triangleTipY, bottomRightEdgeX, bottomRightEdgeY, progressBottomLeftEdgeY);

         /* draw fourth section of triangle */
        path.moveTo(triangleTipX,  triangleTipY);
        path.lineTo( progressBottomLeftEdgeX, progressBottomLeftEdgeY);
        path.lineTo(progressBottomRightEdgeX, progressBottomLeftEdgeY) ;
        path.close();

        canvas.drawPath(path,mPaint);
    }

    private void drawFifthSection(Canvas canvas){
        mPaint.setColor(Color.argb(255,75,0,130));
        mPaint.setStyle(Paint.Style.FILL);

        Path path=new Path();
        /* this is a calculation based on the width of the view to get an equilateral triangle */
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));


        float triangleTipX = getLeft()+(getWidth()/2)+dpToPixels(pyramidThickness);
        float triangleTipY = dpToPixels(pyramidThickness*6);

        float bottomLeftEdgeX =getLeft()+(4*(getWidth()/5))+dpToPixels(pyramidThickness);
        float bottomLeftEdgeY = (heightOfTriangle-dpToPixels(pyramidThickness+1));

        float bottomRightEdgeX=getLeft()+(5*(getWidth()/5))-dpToPixels(pyramidThickness+4);
        float bottomRightEdgeY =  (heightOfTriangle-dpToPixels(pyramidThickness+1));
        /* we use a generalization of the midpoint formula to find the approriate coordinate along this
            line segment
         */
        float percentage;
        if(fifthPortion >1f){
            percentage=1f;
        }

        else{
            percentage=fifthPortion;
        }
        float progressBottomLeftEdgeX = triangleTipX + ((bottomLeftEdgeX-triangleTipX)*percentage);
        float progressBottomLeftEdgeY = triangleTipY + ((bottomLeftEdgeY-triangleTipY)*percentage);

        float progressBottomRightEdgeX= findXOnLine(triangleTipX, triangleTipY, bottomRightEdgeX, bottomRightEdgeY, progressBottomLeftEdgeY);

         /* draw fifth section of triangle */
        path.moveTo(getLeft()+(getWidth()/2)+dpToPixels(pyramidThickness*2),  triangleTipY);
        path.lineTo( progressBottomLeftEdgeX, progressBottomLeftEdgeY);
        path.lineTo(progressBottomRightEdgeX, progressBottomLeftEdgeY) ;
        path.close();

        canvas.drawPath(path,mPaint);
    }


    private void drawFirstLabel(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        mPaint.setColor(Color.argb(255,255,165,0));
        mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();

        float left = getLeft()+dpToPixels(pyramidThickness);
        float right = getLeft()+(getWidth()/5);
        float top =  (heightOfTriangle+dpToPixels(pyramidThickness*2));
        float bottom = (heightOfTriangle+dpToPixels(pyramidThickness*15));
        canvas.drawRect(left, top, right, bottom, mPaint);

        left = getLeft()+dpToPixels(pyramidThickness*2);
        top = (heightOfTriangle+dpToPixels(pyramidThickness*10));
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(dpToPixels(9));
        canvas.drawText("GRAINS", left,top, mPaint);
    }

    private void drawSecondLabel(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();

        float left = getLeft()+(getWidth()/5)+dpToPixels(pyramidThickness);
        float right = getLeft()+(2*(getWidth()/5));
        float top =  (heightOfTriangle+dpToPixels(pyramidThickness*2));
        float bottom = (heightOfTriangle+dpToPixels(pyramidThickness*15));
        canvas.drawRect(left, top, right, bottom, mPaint);

        left = getLeft()+(getWidth()/5)+dpToPixels(pyramidThickness*2);
        top = (heightOfTriangle+dpToPixels(pyramidThickness*10));
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(dpToPixels(9));
        canvas.drawText("VEGGIES", left,top, mPaint);
    }

    private void drawThirdLabel(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();

        float left = getLeft()+(2*(getWidth()/5))+dpToPixels(pyramidThickness);
        float right = getLeft()+(3*(getWidth()/5));
        float top =  (heightOfTriangle+dpToPixels(pyramidThickness*2));
        float bottom = (heightOfTriangle+dpToPixels(pyramidThickness*15));
        canvas.drawRect(left, top, right, bottom, mPaint);

        left = getLeft()+(2*(getWidth()/5))+dpToPixels(pyramidThickness*3) ;
        top = (heightOfTriangle+dpToPixels(pyramidThickness*10));
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(dpToPixels(9));
        canvas.drawText("FRUITS", left,top, mPaint);
    }

    private void drawFourthLabel(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();

        float left = getLeft()+(3*(getWidth()/5))+dpToPixels(pyramidThickness);
        float right = getLeft()+(4*(getWidth()/5));
        float top =  (heightOfTriangle+dpToPixels(pyramidThickness*2));
        float bottom = (heightOfTriangle+dpToPixels(pyramidThickness*15));
        canvas.drawRect(left, top, right, bottom, mPaint);

        left = getLeft()+(3*(getWidth()/5))+dpToPixels(pyramidThickness*4) ;
        top = (heightOfTriangle+dpToPixels(pyramidThickness*10));
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(dpToPixels(9));
        canvas.drawText("DAIRY", left,top, mPaint);
    }

    private void drawFifthLabel(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        mPaint.setColor(Color.argb(255,75,0,130));
        mPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();

        float left = getLeft()+(4*(getWidth()/5))+dpToPixels(pyramidThickness);
        float right = getLeft()+(5*(getWidth()/5));
        float top =  (heightOfTriangle+dpToPixels(pyramidThickness*2));
        float bottom = (heightOfTriangle+dpToPixels(pyramidThickness*15));
        canvas.drawRect(left, top, right, bottom, mPaint);

        left = getLeft()+(4*(getWidth()/5))+dpToPixels(pyramidThickness*4) ;
        top = (heightOfTriangle+dpToPixels(pyramidThickness*10));
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(dpToPixels(9));
        canvas.drawText("MEAT", left,top, mPaint);
    }

    private void drawGrainImage(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        float left = getLeft()+(getWidth()/20);
        float top =  (heightOfTriangle-dpToPixels(pyramidThickness*15));
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.toast);

        canvas.drawBitmap(Bitmap.createScaledBitmap(largeIcon, (3*getWidth())/20, (3*getWidth())/20, false), left, top, mPaint);
    }

    private void drawVeggieImage(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        float left = getLeft()+(getWidth()/5);
        float top =  (heightOfTriangle-dpToPixels(pyramidThickness*20));
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.tomato);

        canvas.drawBitmap(Bitmap.createScaledBitmap(largeIcon, (5*getWidth())/20, (5*getWidth())/20, false), left, top, mPaint);
    }

    private void drawFruitImage(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        float left = getLeft()+(2*(getWidth()/5));
        float top =  (heightOfTriangle-dpToPixels(pyramidThickness*20));
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.pineapple);

        canvas.drawBitmap(Bitmap.createScaledBitmap(largeIcon, (5*getWidth())/20, (5*getWidth())/20, false), left, top, mPaint);
    }

    private void drawMeatImage(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        float left = getLeft()+(4*(getWidth()/5));
        float top =  (heightOfTriangle-dpToPixels(pyramidThickness*15));
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.turkey);

        canvas.drawBitmap(Bitmap.createScaledBitmap(largeIcon, (3*getWidth())/20, (3*getWidth())/20, false), left, top, mPaint);
    }

    private void drawDairyImage(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        float left = getLeft()+(3*(getWidth()/5));
        float top =  (heightOfTriangle-dpToPixels(pyramidThickness*15));
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.milk);

        canvas.drawBitmap(Bitmap.createScaledBitmap(largeIcon, getWidth()/6, getWidth()/6, false), left, top, mPaint);
    }

    private void drawFirstPercent(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        if(firstPortion > 1f){
            mPaint.setColor(Color.RED);
        }

        float bottomLeftEdgeX = getLeft()+dpToPixels(pyramidThickness*3);
        float bottomLeftEdgeY = (heightOfTriangle+dpToPixels(pyramidThickness*15));


        mPaint.setTextSize(dpToPixels(10));
        canvas.drawText(String.valueOf((int) (firstPortion*100))+"%",bottomLeftEdgeX,bottomLeftEdgeY,mPaint);
    }

    private void drawSecondPercent(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        if(secondPortion > 1f){
            mPaint.setColor(Color.RED);
        }

        float bottomLeftEdgeX =getLeft()+(getWidth()/5)+dpToPixels(pyramidThickness);
        float bottomLeftEdgeY = (heightOfTriangle+dpToPixels(pyramidThickness*15));

        mPaint.setTextSize(dpToPixels(10));
        canvas.drawText(String.valueOf((int) (secondPortion*100))+"%",bottomLeftEdgeX,bottomLeftEdgeY,mPaint);

    }

    private void drawThirdPercent(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        if(thirdPortion > 1f){
            mPaint.setColor(Color.RED);
        }

        float bottomLeftEdgeX =getLeft()+(2*(getWidth()/5))+dpToPixels(pyramidThickness);
        float bottomLeftEdgeY = (heightOfTriangle+dpToPixels(pyramidThickness*15));

        mPaint.setTextSize(dpToPixels(10));
        canvas.drawText(String.valueOf((int) (thirdPortion*100))+"%",bottomLeftEdgeX,bottomLeftEdgeY,mPaint);
    }

    private void drawFourthPercent(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        if(fourthPortion > 1f){
            mPaint.setColor(Color.RED);
        }

        float bottomLeftEdgeX =getLeft()+(3*(getWidth()/5))+dpToPixels(pyramidThickness);
        float bottomLeftEdgeY = (heightOfTriangle+dpToPixels(pyramidThickness*15));

        mPaint.setTextSize(dpToPixels(10));
        canvas.drawText(String.valueOf((int) (fourthPortion*100))+"%",bottomLeftEdgeX,bottomLeftEdgeY,mPaint);

    }

    private void drawFifthPercent(Canvas canvas){
        Float heightOfTriangle = new Float(getTop()+(getWidth()/2)*(Math.sqrt(3)));
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        if(fifthPortion > 1f){
            mPaint.setColor(Color.RED);
        }

        float bottomLeftEdgeX =getLeft()+(4*(getWidth()/5))+dpToPixels(pyramidThickness);
        float bottomLeftEdgeY = (heightOfTriangle+dpToPixels(pyramidThickness*15));

        mPaint.setTextSize(dpToPixels(10));
        canvas.drawText(String.valueOf((int) (fifthPortion*100))+"%",bottomLeftEdgeX,bottomLeftEdgeY,mPaint);
    }

    private float dpToPixels(int dp){
        DisplayMetrics dm = getResources().getDisplayMetrics() ;
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    public void setFirstPortion(float firstPortion) {
        this.firstPortion = firstPortion;
    }

    public void setSecondPortion(float secondPortion) {
        this.secondPortion = secondPortion;
    }

    public void setThirdPortion(float thirdPortion) {
        this.thirdPortion = thirdPortion;
    }

    public void setFourthPortion(float fourthPortion) {
        this.fourthPortion = fourthPortion;
    }

    public void setFifthPortion(float fifthPortion) {
        this.fifthPortion = fifthPortion;
    }

    private float findXOnLine(float startPointX, float startPointY, float endPointX, float endPointY, float knownYCoord ){
        float slope = (endPointY-startPointY)/(endPointX-startPointX);
        return (knownYCoord-startPointY+slope*startPointX)/slope;
    }

}
