package com.yf.OpenGLStudyDemo1;

import android.opengl.Matrix;

/**
 * Created by Administrator on 8/31 0031.
 */
public class MatrixUtils {

    private static float[] mOrthoMatrixArray = new float[16];
    private static float[] mCameraMatrixArray = new float[16];

    /**
     * 设置为正交投影
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
     * 设置摄像机的摆放
     * @param cx 摄像机的x坐标
     * @param cy 摄像机的y坐标
     * @param cz 摄像机的z坐标
     * @param tx 摄像机的目标点x
     * @param ty 摄像机的目标点y
     * @param tz 摄像机的目标点z
     * @param upx 摄像机UP向量x分量
     * @param upy 摄像机UP向量y分量
     * @param upz 摄像机UP分量z分量
     */
    public static void setCamera(float cx, float cy, float cz, float tx, float ty, float tz, float upx, float upy, float upz) {
        Matrix.setLookAtM(mCameraMatrixArray, 0, cx, cy, cz, tx, ty, tz, upx, upy, upz);
    }

}
