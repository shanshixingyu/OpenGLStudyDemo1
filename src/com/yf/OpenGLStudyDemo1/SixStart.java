package com.yf.OpenGLStudyDemo1;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 8/31 0031.
 */
public class SixStart {

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

    private float yAngle = 0;// ��y����ת�ĽǶ�
    private float xAngle = 0;// ��z����ת�ĽǶ�

    public SixStart(float bigRadius, float smallRadius, OpenGLSurfaceView openGLSurfaceView) {
        initVertex(bigRadius, smallRadius);
        initShader(openGLSurfaceView);
    }

    private void initVertex(float bigRadius, float smallRadius) {

        List<Float> vertexList = new ArrayList<Float>();
        for (float angle = 0; angle < 360; angle += ONE_ANGLE) {
            // ÿ��60����6=3*2����,Ҳ��18=6*3��ֵ

            // ��һ��������
            vertexList.add(0f);
            vertexList.add(0f);
            vertexList.add(0f);

            vertexList.add((float) (bigRadius * Math.cos(Math.toRadians(angle))));
            vertexList.add((float) (bigRadius * Math.sin(Math.toRadians(angle))));
            vertexList.add(0f);

            vertexList.add((float) (smallRadius * Math.cos(Math.toRadians(angle + HALF_ANGLE))));
            vertexList.add((float) (smallRadius * Math.sin(Math.toRadians(angle + HALF_ANGLE))));
            vertexList.add(0f);

            // �ڶ���������
            vertexList.add(0f);
            vertexList.add(0f);
            vertexList.add(0f);

            vertexList.add((float) (bigRadius * Math.cos(Math.toRadians(angle + HALF_ANGLE))));
            vertexList.add((float) (bigRadius * Math.sin(Math.toRadians(angle + HALF_ANGLE))));
            vertexList.add(0f);

            vertexList.add((float) (smallRadius * Math.cos(Math.toRadians(angle + ONE_ANGLE))));
            vertexList.add((float) (smallRadius * Math.sin(Math.toRadians(angle + ONE_ANGLE))));
            vertexList.add(0f);
        }

        mVertexCount = vertexList.size() / 3;
        float[] mVertexArray = new float[vertexList.size()];
        for (int i = 0; i < vertexList.size(); i++) {
            mVertexArray[i] = vertexList.get(i);
        }

        ByteBuffer vertextByteBuffer = ByteBuffer.allocateDirect(vertexList.size() * 4);
        vertextByteBuffer.order(ByteOrder.nativeOrder());
        mVertexFloatBuffer = vertextByteBuffer.asFloatBuffer();
        mVertexFloatBuffer.put(mVertexArray);
        mVertexFloatBuffer.position(0);

        float[] mColorArray = new float[mVertexCount * 4];
        for (int i = 0; i < mVertexCount; i++) {
            // ������һ��
            if (i % 3 == 0) {// �м��ɫ
                mColorArray[i * 4] = 1f;
                mColorArray[i * 4 + 1] = 1f;
                mColorArray[i * 4 + 2] = 1f;
                mColorArray[i * 4 + 3] = 1f;
            } else {// �Աߵ���ɫ
                mColorArray[i * 4] = 0.45f;
                mColorArray[i * 4 + 1] = 0.75f;
                mColorArray[i * 4 + 2] = 0.75f;
                mColorArray[i * 4 + 3] = 0;
            }
        }
        ByteBuffer colorByteBuffer = ByteBuffer.allocateDirect(mColorArray.length * 4);
        colorByteBuffer.order(ByteOrder.nativeOrder());
        mColorFloatBuffer = colorByteBuffer.asFloatBuffer();
        mColorFloatBuffer.put(mColorArray);
        mVertexFloatBuffer.position(0);

    }

    private void initShader(OpenGLSurfaceView openGLSurfaceView) {
        String vertexSharderSource =
            ShaderUtils.readSourceFromAssetsDirectory("vertex.sh", openGLSurfaceView.getResources());
        if (vertexSharderSource == null) {
            throw new NullPointerException("��ȡ������ɫ������ʧ��");
        }

        String fragmentSharderSource =
            ShaderUtils.readSourceFromAssetsDirectory("fragment.sh", openGLSurfaceView.getResources());
        if (fragmentSharderSource == null) {
            throw new NullPointerException("��ȡƬԪ��ɫ������ʧ��");
        }

        mProgram = ShaderUtils.createProgram(vertexSharderSource, fragmentSharderSource);
        if (mProgram == 0) {
            throw new IllegalStateException("��������ʧ��");
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

    }
}
