package com.yf.OpenGLStudyDemo1;

import android.content.res.Resources;
import android.opengl.GLES20;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by hwz on 2015-09-01.
 */
public class ShaderUtils {

    private static final String TAG = "ShaderUtils";

    public static String readSourceFromAssetsDirectory(String fileName, Resources resources) {
        String result = null;
        if (fileName == null || resources == null)
            return result;

        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;

        try {
            inputStream = resources.getAssets().open(fileName);
            byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            result = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
            result = result.replace("\\r\\n", "\n");
        } catch (IOException e) {
            e.printStackTrace();
            result = null;
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public static int createProgram(String vertexSource, String fragmentSource) {
        if (vertexSource == null || fragmentSource == null)
            return 0;

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0)
            return 0;
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (fragmentShader == 0)
            return 0;

        int program = GLES20.glCreateProgram();
        if (program != 0) {
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttachShader vertexShader");
            GLES20.glAttachShader(program, fragmentShader);
            checkGlError("glAttachShader fragmentShader");
            GLES20.glLinkProgram(program);

            int[] linkResult = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkResult, 0);
            if (linkResult[0] != GLES20.GL_TRUE) {
                Log.e(TAG, "Could not link program: ");
                Log.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }

        return program;
    }

    /**
     * 加载着色器
     *
     * @param shaderType 着色器类型：GLES20.GL_VERTEX_SHADER / GLES20.GL_FRAGMENT_SHADER
     * @param source
     * @return
     */
    private static int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0) {
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compileResult = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileResult, 0);
            if (compileResult[0] == 0) {
                Log.e(TAG, "Shader加载失败 " + shaderType);
                Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }

        return shader;
    }

    /**
     * 检查每一步操作是否有错误的方法
     */
    public static void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error);
        }
    }
}
