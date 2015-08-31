package com.yf.OpenGLStudyDemo1;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 8/31 0031.
 */
public class SixStart {

    private static final int ONE_ANGLE = 60;
    private static final int HALF_ANGLE = 30;

    private int mVertexCount = 0;

    public SixStart(float bigRadius, float smallRadius) {
        initVertex(bigRadius, smallRadius);
        initShader();
    }

    private void initVertex(float bigRadius, float smallRadius) {

        List<Float> vertexList = new ArrayList<Float>();
        for (float angle = 0; angle < 360; angle += ONE_ANGLE) {
            //每个60度有6=3*2个点,也就18=6*3个值

            //第一个三角形
            vertexList.add(0f);
            vertexList.add(0f);
            vertexList.add(0f);

            vertexList.add((float) (bigRadius * Math.cos(Math.toRadians(angle))));
            vertexList.add((float) (bigRadius * Math.sin(Math.toRadians(angle))));
            vertexList.add(0f);

            vertexList.add((float) (smallRadius * Math.cos(Math.toRadians(angle + HALF_ANGLE))));
            vertexList.add((float) (smallRadius * Math.sin(Math.toRadians(angle + HALF_ANGLE))));
            vertexList.add(0f);

            //第二个三角形
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

        ByteBuffer vertextByteBuffer=ByteBuffer.allocate()



    }


    private void initShader() {

    }

    public void drawSelf() {

    }


}
