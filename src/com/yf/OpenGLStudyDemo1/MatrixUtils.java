package com.yf.OpenGLStudyDemo1;

import android.opengl.Matrix;

import java.util.Arrays;

/**
 * Created by Administrator on 8/31 0031.
 */
public class MatrixUtils {

    private static float[] mOrthoMatrixArray = new float[16];
    private static float[] mCameraMatrixArray = new float[16];

    private static float[] mMVPMatrixArray = new float[16];

    /**
     * 设置为正交投影
     *
     * @param left
     * @param right
     * @param bottom
     * @param top
     * @param near
     * @param far
     */
    public static void setOrthoM(float left, float right, float bottom, float top, float near, float far) {
        Matrix.orthoM(mOrthoMatrixArray, 0, left, right, bottom, top, near, far);

    }

    /**
     * 设置摄像头的摆放
     *
     * @param cx  摄像头坐标的x
     * @param cy  摄像头坐标的y
     * @param cz  摄像头坐标的z
     * @param tx  摄像头朝向的目标点坐标的x
     * @param ty  摄像头朝向的目标点坐标的y
     * @param tz  摄像头朝向的目标点坐标的z
     * @param upx 摄像头up向量的x分量
     * @param upy 摄像头up向量的y分量
     * @param upz 摄像头up向量的z分量
     */
    public static void setCamera(float cx, float cy, float cz, float tx, float ty, float tz, float upx, float upy, float upz) {
        Matrix.setLookAtM(mCameraMatrixArray, 0, cx, cy, cz, tx, ty, tz, upx, upy, upz);
    }

    public static float[] getFinalMatrixArray(float[] spec) {
        if (mMVPMatrixArray == null || mMVPMatrixArray.length != 16) {
            mMVPMatrixArray = new float[16];
        } else {
            Arrays.fill(mMVPMatrixArray, 0);
        }

        Matrix.multiplyMM(mMVPMatrixArray, 0, mCameraMatrixArray, 0, spec, 0);
        Matrix.multiplyMM(mMVPMatrixArray, 0, mOrthoMatrixArray, 0, mMVPMatrixArray, 0);

        return mMVPMatrixArray;
    }


}
