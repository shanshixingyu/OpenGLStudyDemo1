package com.yf.OpenGLStudyDemo1;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 8/31 0031.
 */
public class OpenGLSurfaceView extends GLSurfaceView {

    private OpenGLRender mOpenGLRender;
    private float mPreX = 0f;
    private float mPreY = 0f;
    private float mTouchX = 0f;
    private float mTouchY = 0f;

    private static final float TOUCH_DISTANCE_2_ANGLE_SCALE = 180f / 320f;

    public OpenGLSurfaceView(Context context) {
        this(context, null);
    }

    public OpenGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        mOpenGLRender = new OpenGLRender();
        setRenderer(mOpenGLRender);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchX = event.getX();
        mTouchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float detaX = mTouchX - mPreX;
                float detaY = mTouchY - mPreY;
                if (mOpenGLRender != null && mOpenGLRender.getSixStart() != null) {
                    SixStart sixStart = mOpenGLRender.getSixStart();
                    sixStart.setXAngle(sixStart.getXAngle() + detaY * TOUCH_DISTANCE_2_ANGLE_SCALE);
                    sixStart.setYAngle(sixStart.getYAngle() + detaX * TOUCH_DISTANCE_2_ANGLE_SCALE);
                }
                break;
            default:
        }
        mPreX = mTouchX;
        mPreY = mTouchY;

        return true;
    }

    private class OpenGLRender implements GLSurfaceView.Renderer {

        private SixStart mSixStart;

        @Override
        public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
            GLES20.glClearColor(1f, 1f, 1f, 1f);
            // GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
            mSixStart = new SixStart(0.5f, 0.2f, OpenGLSurfaceView.this);
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);
            float ratio = (float) width / height;
            MatrixUtils.setOrthoM(-ratio, ratio, -1, 1, 1, 10);
            // MatrixUtils.setFrustumM(-ratio, ratio, -1, 1, 1, 10);
            MatrixUtils.setCamera(0, 0, 3, 0, 0, 0, 0, 1, 0);
        }

        @Override
        public void onDrawFrame(GL10 gl10) {
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
            if (mSixStart != null) {
                mSixStart.drawSelf();
            }
        }

        public SixStart getSixStart() {
            return mSixStart;
        }
    }

}
