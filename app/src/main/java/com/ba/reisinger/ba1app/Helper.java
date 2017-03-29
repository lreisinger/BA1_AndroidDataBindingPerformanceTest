package com.ba.reisinger.ba1app;

import android.app.Activity;
import android.os.SystemClock;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Lukas on 29.03.2017.
 */

public class Helper {

    public static void testUIDelay_async(final Activity activity){

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() { //non-ui thread
                final boolean isDataBinding = activity instanceof DataBindingActivity;
                long startTime_SetText = 0;

                Log.i("performance", "== " + activity.getLocalClassName() + " Test ==");

                //alle<r TextViews holen
                final CustomTextView[] views = Helper.getAllTextViews(activity);


                Runnable changeTextViewsTask = new Runnable() {
                    @Override
                    public void run() {
                        if(!isDataBinding) {
                            Helper.changeTextViews(views);
                        }
                        else {
                            ((DataBindingActivity) activity).model.setText(Helper.getNextText());
                        }
                    }
                };


                //Timestamp bevor TextView upgedated werden
                startTime_SetText = System.currentTimeMillis();//set start time
                Log.i("performance", "Timestamp setText:              " + startTime_SetText);

                //Change All TextViews on UI Thread:
                activity.runOnUiThread(changeTextViewsTask);

                //Wait until TextViews are updated
                SystemClock.sleep(800);

                //Measure maximum Delay
                long finishedDrawing = Helper.getLatestTextViewUpdate(views, startTime_SetText); //minValue sodass vor dieser zeit keine zeiten von textview genommen werden
                Log.i("performance", "Timestamp latest TextView Draw: " + finishedDrawing);
                long delta = finishedDrawing-startTime_SetText;

                Log.i("performance", "Delta: " + delta + " ms");
                Log.i("performance", " ");
            }
        });
        th.start();
    }


    private static boolean isFirstText = true;
    public static String getNextText()
    {
        String text;
        if(!isFirstText) {
            text = Model.text1;
        }
        else {
            text = Model.text2;
        }
        isFirstText = !isFirstText;
        return text;
    }

    public static long getLatestTextViewUpdate(CustomTextView[] views, long beginSetText) {
        long latest = 0;
        for(CustomTextView v : views)
        {
            long lastdrawn = v.getLastDrawn();

            if(lastdrawn > beginSetText) { //nur wenn nach beginn gedrawed wurde

                if (lastdrawn > latest) {
                    latest = lastdrawn;
                }
            }
        }
        return latest;
    }

    public static void changeTextViews(CustomTextView[] views)
    {
        String text = Helper.getNextText();
        for (CustomTextView v : views)
        {
            v.setText(text);
        }
    }

    public static CustomTextView[] getAllTextViews(Activity activity){
        ArrayList<CustomTextView> viewsList = new ArrayList<>();

        Field[] fs = activity.getClass().getDeclaredFields();
        for (Field field : fs)
        {
            try {
                Object o = field.get(activity);

                if(o instanceof CustomTextView) {
                    viewsList.add((CustomTextView) o);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        CustomTextView[] viewsArray = new CustomTextView[viewsList.size()];
        viewsArray = viewsList.toArray(viewsArray);
        return viewsArray;
    }


}