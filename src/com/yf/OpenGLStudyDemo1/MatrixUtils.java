package com.yf.OpenGLStudyDemo1;

import android.opengl.Matrix;

/**
 * Created by Administrator on 8/31 0031.
 */
public class MatrixUtils {

    private static float[] mOrthoMatrixArray = new float[16];
    private static float[] mCameraMatrixArray = new float[16];

    /**
     * ����Ϊ����ͶӰ
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
     * ����������İڷ�
     * @param cx �������x����
     * @param cy �������y����
     * @param cz �������z����
     * @param tx �������Ŀ���x
     * @param ty �������Ŀ���y
     * @param tz �������Ŀ���z
     * @param upx �����UP����x����
     * @param upy �����UP����y����
     * @param upz �����UP����z����
     */
    public static void setCamera(float cx, float cy, float cz, float tx, float ty, float tz, float upx, float upy, float upz) {
        Matrix.setLookAtM(mCameraMatrixArray, 0, cx, cy, cz, tx, ty, tz, upx, upy, upz);
    }

}
