package com.yf.OpenGLStudyDemo1;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 8/31 0031.
 */
public class SixStart {

    private static final String TAG = SixStart.class.getName();

    private static final int ONE_ANGLE = 60;
    private static final int HALF_ANGLE = 30;

    private int mVertexCount = 0;
    private FloatBuffer mVertexFloatBuffer = null;
    private FloatBuffer mColorFloatBuffer = null;

    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    private float[] mMatrixArray = new float[16];

    private int mProgram;

    private float yAngle = 0; //绕y轴旋转的角度
    private float xAngle = 0; //绕x轴旋转的角度

    public SixStart(float bigRadius, float smallRadius, OpenGLSurfaceView openGLSurfaceView) {
        initVertex(bigRadius, smallRadius);
        initShader(openGLSurfaceView);
    }

    private void initVertex(float bigRadius, float smallRadius) {

        List<Float> vertexList = new ArrayList<Float>();
        for (float angle = 0; angle < 360; angle += ONE_ANGLE) {

            //第一个三角型的三个顶点
            vertexList.add(0f);
            vertexList.add(0f);
            vertexList.add(0f);

            vertexList.add((float) (bigRadius * Math.cos(Math.toRadians(angle))));
            vertexList.add((float) (bigRadius * Math.sin(Math.toRadians(angle))));
            vertexList.add(0f);

            vertexList.add((float) (smallRadius * Math.cos(Math.toRadians(angle + HALF_ANGLE))));
            vertexList.add((float) (smallRadius * Math.sin(Math.toRadians(angle + HALF_ANGLE))));
            vertexList.add(0f);

            // 第二个三角形的三个顶点
            vertexList.add(0f);
            vertexList.add(0f);
            vertexList.add(0f);

            vertexList.add((float) (smallRadius * Math.cos(Math.toRadians(angle + HALF_ANGLE))));
            vertexList.add((float) (smallRadius * Math.sin(Math.toRadians(angle + HALF_ANGLE))));
            vertexList.add(0f);

            vertexList.add((float) (bigRadius * Math.cos(Math.toRadians(angle + ONE_ANGLE))));
            vertexList.add((float) (bigRadius * Math.sin(Math.toRadians(angle + ONE_ANGLE))));
            vertexList.add(0f);
        }

//        Log.i(TAG, "获得的顶点信息：" + vertexList);

        mVertexCount = vertexList.size() / 3;
//        Log.i(TAG, "获得的顶点个数：" + mVertexCount);
        float[] mVertexArray = new float[vertexList.size()];
        for (int i = 0; i < vertexList.size(); i++) {
            mVertexArray[i] = vertexList.get(i);
        }

        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertexList.size() * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());
        mVertexFloatBuffer = vertexByteBuffer.asFloatBuffer();
        mVertexFloatBuffer.put(mVertexArray);
        mVertexFloatBuffer.position(0);

        float[] mColorArray = new float[mVertexCount * 4];
        for (int i = 0; i < mVertexCount; i++) {
            if (i % 3 == 0) {// 六角星中间白色
                mColorArray[i * 4] = 1f;
                mColorArray[i * 4 + 1] = 1f;
                mColorArray[i * 4 + 2] = 1f;
                mColorArray[i * 4 + 3] = 1f;
            } else {// 六角星周边淡蓝色
                mColorArray[i * 4] = 0.45f;
                mColorArray[i * 4 + 1] = 0.75f;
                mColorArray[i * 4 + 2] = 0.75f;
                mColorArray[i * 4 + 3] = 1f;
            }
        }
        ByteBuffer colorByteBuffer = ByteBuffer.allocateDirect(mColorArray.length * 4);
        colorByteBuffer.order(ByteOrder.nativeOrder());
        mColorFloatBuffer = colorByteBuffer.asFloatBuffer();
        mColorFloatBuffer.put(mColorArray);
        mColorFloatBuffer.position(0);

    }

    private void initShader(OpenGLSurfaceView openGLSurfaceView) {
        String vertexShaderSource =
                ShaderUtils.readSourceFromAssetsDirectory("vertex.sh", openGLSurfaceView.getResources());
        if (vertexShaderSource == null) {
            throw new NullPointerException("读取顶点着色器源码失败");
        }

        String fragmentShaderSource =
                ShaderUtils.readSourceFromAssetsDirectory("fragment.sh", openGLSurfaceView.getResources());
        if (fragmentShaderSource == null) {
            throw new NullPointerException("读取片元着色器源码失败");
        }

        mProgram = ShaderUtils.createProgram(vertexShaderSource, fragmentShaderSource);
        if (mProgram == 0) {
            throw new IllegalStateException("创建程序失败");
        }

        mMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "fromPosition");
        mColorHandle = GLES20.glGetAttribLocation(mProgram, "fromColor");

    }

    public void drawSelf() {
        GLES20.glUseProgram(mProgram);
        Matrix.setRotateM(mMatrixArray, 0, 0, 1, 0, 0);
        Matrix.translateM(mMatrixArray, 0, 0, 0, 1);
        Matrix.rotateM(mMatrixArray, 0, yAngle, 0, 1, 0);
        Matrix.rotateM(mMatrixArray, 0, xAngle, 1, 0, 0);

        GLES20.glUniformMatrix4fv(mMVPMatrixHandle, 1, false, MatrixUtils.getFinalMatrixArray(mMatrixArray), 0);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 3 * 4, mVertexFloatBuffer);
        GLES20.glVertexAttribPointer(mColorHandle, 4, GLES20.GL_FLOAT, false, 4 * 4, mColorFloatBuffer);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(mColorHandle);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, mVertexCount);
    }
}
