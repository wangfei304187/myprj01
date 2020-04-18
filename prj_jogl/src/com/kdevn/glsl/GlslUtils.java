/**
 * Copyright 2014 Bowing Medical Technologies Co., Ltd.
 */
package com.kdevn.glsl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.ImageIcon;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES2;

/**
 * @author root 2020年2月6日 下午3:27:32
 *
 */
public class GlslUtils
{
    public static int genVAO(GL2 gl)
    {
        int[] VAO = new int[1];
        gl.glGenVertexArrays(1, VAO, 0);
        return VAO[0];
    }

    public static int genVBO(GL2 gl)
    {
        int[] VBO = new int[1];
        gl.glGenBuffers(1, VBO, 0);
        return VBO[0];
    }

    public static int genEBO(GL2 gl)
    {
        int[] EBO = new int[1];
        gl.glGenBuffers(1, EBO, 0);
        return EBO[0];
    }

    public static int createVertexShader(GL2 gl, String vertexShaderSource)
    {
        int vertexShaderId = gl.glCreateShader(GL2ES2.GL_VERTEX_SHADER);
        String[] sourceArr = new String[] { vertexShaderSource };
        gl.glShaderSource(vertexShaderId, sourceArr.length, sourceArr, null);
        gl.glCompileShader(vertexShaderId);

        GlslUtils.checkShaderCompileVertex(gl, vertexShaderId);

        return vertexShaderId;
    }

    public static int createFragmentShader(GL2 gl, String fragmentShaderSource)
    {
        int fragmentShaderId = gl.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER);
        String[] sourceArr = new String[] { fragmentShaderSource };
        gl.glShaderSource(fragmentShaderId, sourceArr.length, sourceArr, null);
        gl.glCompileShader(fragmentShaderId);

        GlslUtils.checkShaderCompileFragment(gl, fragmentShaderId);

        return fragmentShaderId;
    }

    public static int createProgram(GL2 gl, int vertexShaderId, int fragmentShaderId)
    {
        int shaderProgram = gl.glCreateProgram();
        gl.glAttachShader(shaderProgram, vertexShaderId);
        gl.glAttachShader(shaderProgram, fragmentShaderId);
        gl.glLinkProgram(shaderProgram);

        GlslUtils.checkProgramLink(gl, shaderProgram);

        return shaderProgram;
    }

    private static void checkShaderCompileVertex(GL2 gl, int vertexShadlerId)
    {
        int[] compiled = new int[1];
        gl.glGetShaderiv(vertexShadlerId, GL2ES2.GL_COMPILE_STATUS, compiled, 0);
        boolean success = compiled[0] != 0;
        if (!success)
        {
            int[] logLength = new int[1];
            gl.glGetShaderiv(vertexShadlerId, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetShaderInfoLog(vertexShadlerId, logLength[0], (int[]) null, 0, log, 0);
            System.err.println("ERROR::SHADER::VERTEX::COMPILATION_FAILED, " + new String(log));
        }
        else
        {
            System.out.println("vertexShader complie SUCCESS");
        }
    }

    private static void checkShaderCompileFragment(GL2 gl, int fragmentShaderId)
    {
        int[] compiled = new int[1];
        gl.glGetShaderiv(fragmentShaderId, GL2ES2.GL_COMPILE_STATUS, compiled, 0);
        boolean success = compiled[0] != 0;
        if (!success)
        {
            int[] logLength = new int[1];
            gl.glGetShaderiv(fragmentShaderId, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetShaderInfoLog(fragmentShaderId, logLength[0], (int[]) null, 0, log, 0);
            System.err.println("ERROR::SHADER::FRAGMENT::COMPILATION_FAILED\n" + new String(log));
        }
        else
        {
            System.out.println("fragmentShader complie SUCCESS");
        }
    }

    private static void checkProgramLink(GL2 gl, int shaderProgramId)
    {
        int[] compiled = new int[1];
        gl.glGetProgramiv(shaderProgramId, GL2ES2.GL_LINK_STATUS, compiled, 0);
        boolean success = compiled[0] != 0;
        if (!success)
        {
            int[] logLength = new int[1];
            gl.glGetProgramiv(shaderProgramId, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetProgramInfoLog(shaderProgramId, logLength[0], (int[]) null, 0, log, 0);
            System.err.println("ERROR::SHADER::PROGRAM::LINKING_FAILED\n" + new String(log));
        }
        else
        {
            System.out.println("shaderProgram complie SUCCESS");
        }
    }

    public static ImageIcon getImageIcon(String relativePath, String callerInfo)
    {
        URL url = GlslUtils.getUrl(relativePath);

        if (url != null)
        {
            return new ImageIcon(url);
        }
        throw new RuntimeException("Failed to create ImageIcon from " + relativePath + " in " + callerInfo);
    }

    public static final URL getUrl(String fileName)
    {
        URL url = ClassLoader.getSystemClassLoader().getResource(fileName);

        if (url == null)
        {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            url = cl.getResource(fileName);
        }
        return url;
    }
    
    public static String loadGlslSource(String fileName)
    {
    	URL url = getUrl(fileName);
    	BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder sb = new StringBuilder(200);
			String line = br.readLine();
			while (line != null)
			{
				sb.append(line).append("\n");
				line = br.readLine();
			}
			String outStr = sb.toString();
			System.out.println(outStr);
			return outStr;
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		} finally {
			if (br != null)
			{
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }
}
