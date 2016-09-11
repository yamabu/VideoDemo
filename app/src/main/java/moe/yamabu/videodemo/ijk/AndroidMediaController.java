/*
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package moe.yamabu.videodemo.ijk;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AndroidMediaController extends MediaController implements IMediaController {
    private ActionBar mActionBar;
    private SeekBar mSeekBar;
    private float xInAll;
    private int xMocked;

    public AndroidMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AndroidMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
        initView(context);
    }

    public AndroidMediaController(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {

    }

    public void setSupportActionBar(@Nullable ActionBar actionBar) {
        mActionBar = actionBar;
        if (isShowing()) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }

    @Override
    public void show() {
        super.show();
        if (mActionBar != null)
            mActionBar.show();
    }

    @Override
    public void hide() {
        super.hide();
        if (mActionBar != null)
            mActionBar.hide();
        for (View view : mShowOnceArray)
            view.setVisibility(View.GONE);
        mShowOnceArray.clear();
    }

    //----------
    // Extends
    //----------
    private ArrayList<View> mShowOnceArray = new ArrayList<View>();

    public void showOnce(@NonNull View view) {
        mShowOnceArray.add(view);
        view.setVisibility(View.VISIBLE);
        show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        SeekBar seekBar = getSeekBar();
        if (seekBar == null) {
            return super.dispatchTouchEvent(ev);
        }
        int action = ev.getAction();
        //按下
        if (action == MotionEvent.ACTION_DOWN) {
            xInAll = ev.getX();
            xMocked=getMockedX(seekBar);
            Log.d("TouchTest","xInAll= "+xInAll+" xMocked="+xMocked );
            MotionEvent newEv = MotionEvent.obtain(ev.getDownTime(), ev.getEventTime(), ev.getAction(), xMocked, ev.getY(), ev.getMetaState());
            seekBar.onTouchEvent(newEv);
        }else {
            int newX= ((int) ((ev.getX() - xInAll) / 3))+xMocked;
            Log.d("TouchTest","xInAll= "+xInAll+" newX="+newX );
            MotionEvent newEv = MotionEvent.obtain(ev.getDownTime(), ev.getEventTime(), ev.getAction(), newX, ev.getY(), ev.getMetaState());
            seekBar.onTouchEvent(newEv);
        }
        Log.d("dispatchTouchEvent", ev.toString() + "  media controller ");
        return super.dispatchTouchEvent(ev);
    }

    private int getMockedX(SeekBar seekBar) {
        float per = ((float) seekBar.getProgress()) / seekBar.getMax();
        int width = seekBar.getWidth();
        int paddingLeft = seekBar.getPaddingLeft();
        int paddingRight = seekBar.getPaddingRight();
        int available = width - paddingLeft - paddingRight;
        return paddingLeft + ((int) (available * per));
    }

    public SeekBar getSeekBar() {
        if (mSeekBar == null) {
            try {
                Field mProgressField = MediaController.class.getDeclaredField("mProgress");
                mProgressField.setAccessible(true);
                Object o = mProgressField.get(this);
                if (o instanceof SeekBar) {
                    mSeekBar = (SeekBar) o;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mSeekBar;
    }
}
