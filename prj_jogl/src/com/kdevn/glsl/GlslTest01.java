package com.kdevn.glsl;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.FloatBuffer;

import javax.swing.JFrame;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

// Ref: https://www.cnblogs.com/zhxmdefj/p/11192408.html
public class GlslTest01
{
    private static int width = 1000;
    private static int height = 800;

    private int shaderProgram;
    private int vertexShader;
    private int fragmentShader;

    /*@formatter:off*/
    private String vertexShaderSource = "#version 330 core\n"
            + "layout (location = 0) in vec3 aPos;\n"
            + "void main()\n"
            + "{\n"
            + "   gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n"
            + "}";
    private String fragmentShaderSource = "#version 330 core\n"
            + "out vec4 FragColor;\n"
            +  "void main()\n"
            +  "{\n"
            +  "   FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n"
            +  "}\n";
    /*@formatter:on*/

    // 顶点数据
    /*@formatter:off*/
    //    float vertices[] = {
    //            -0.5f, -0.5f, 0.0f,
    //            0.5f, -0.5f, 0.0f,
    //            0.0f, 0.5f, 0.0f
    //    };

    float[] vertices ={
            0.0f, 0.0f, 0.0f,
            //            0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f,
            //            0.0f, 1.0f, 1.0f,
            //1.0f, 0.0f, 0.0f,
            //            1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f
            //            1.0f, 1.0f, 1.0f
    };
    /*@formatter:on*/

    public void doInit(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();

        System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));

        vertexShader = gl.glCreateShader(GL2ES2.GL_VERTEX_SHADER);
        String[] sourceArr = new String[] { vertexShaderSource };
        gl.glShaderSource(vertexShader, sourceArr.length, sourceArr, null);
        gl.glCompileShader(vertexShader);

        // 检查顶点着色器是否编译错误
        checkShaderCompileVertex(gl);

        // 片段着色器
        fragmentShader = gl.glCreateShader(GL2ES2.GL_FRAGMENT_SHADER);
        sourceArr = new String[] { fragmentShaderSource };
        gl.glShaderSource(fragmentShader, sourceArr.length, sourceArr, null);
        gl.glCompileShader(fragmentShader);

        // 检查片段着色器是否编译错误
        checkShaderCompileFragment(gl);

        // 连接着色器
        shaderProgram = gl.glCreateProgram();
        gl.glAttachShader(shaderProgram, vertexShader);
        gl.glAttachShader(shaderProgram, fragmentShader);
        gl.glLinkProgram(shaderProgram);

        // 检查片段着色器是否编译错误
        checkProgramLink(gl);

        // 连接后删除
        gl.glDeleteShader(vertexShader);
        gl.glDeleteShader(fragmentShader);
    }

    public void doDisplay(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        int[] VBO = new int[1];
        gl.glGenBuffers(1, VBO, 0);
        int[] VAO = new int[1];
        gl.glGenVertexArrays(1, VAO, 0);

        // 初始化代码
        // 1. 绑定VAO
        gl.glBindVertexArray(VAO[0]);
        // 2. 把顶点数组复制到缓冲中供OpenGL使用
        // Select the VBO, GPU memory data, to use for vertices
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, VBO[0]);
        int numBytes = vertices.length * 4;
        FloatBuffer fbVertices = Buffers.newDirectFloatBuffer(vertices);
        // transfer data to VBO, this perform the copy of data from CPU -> GPU memory
        gl.glBufferData(GL.GL_ARRAY_BUFFER, numBytes, fbVertices, GL.GL_STATIC_DRAW);

        // 3. 设置顶点属性指针
        // Associate Vertex attribute 0 with the last bound VBO
        /*@formatter:off*/
        gl.glVertexAttribPointer(0 /* the vertex attribute */,
                3,
                GL.GL_FLOAT,
                false /* normalized? */,
                0 /* stride */,
                0 /* The bound VBO data offset */);
        /*@formatter:on*/
        gl.glEnableVertexAttribArray(0);

        gl.glUseProgram(shaderProgram);
        // gl.glBindVertexArray(VAO[0]);
        gl.glDrawArrays(GL.GL_TRIANGLES, 0, 3);

        gl.glViewport(0, 0, GlslTest01.width, GlslTest01.height);
    }

    private void checkProgramLink(GL2 gl)
    {
        int[] compiled = new int[1];
        gl.glGetProgramiv(shaderProgram, GL2ES2.GL_LINK_STATUS, compiled, 0);
        boolean success = compiled[0] != 0;
        if (!success)
        {
            int[] logLength = new int[1];
            gl.glGetProgramiv(shaderProgram, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetProgramInfoLog(shaderProgram, logLength[0], (int[]) null, 0, log, 0);
            System.out.println("ERROR::SHADER::PROGRAM::LINKING_FAILED\n" + new String(log));
        }
        else
        {
            System.out.println("shaderProgram complie SUCCESS");
        }
    }

    private void checkShaderCompileFragment(GL2 gl)
    {
        int[] compiled = new int[1];
        gl.glGetShaderiv(fragmentShader, GL2ES2.GL_COMPILE_STATUS, compiled, 0);
        boolean success = compiled[0] != 0;
        if (!success)
        {
            int[] logLength = new int[1];
            gl.glGetShaderiv(fragmentShader, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetShaderInfoLog(fragmentShader, logLength[0], (int[]) null, 0, log, 0);
            System.out.println("ERROR::SHADER::FRAGMENT::COMPILATION_FAILED\n" + new String(log));
        }
        else
        {
            System.out.println("fragmentShader complie SUCCESS");
        }
    }

    private void checkShaderCompileVertex(GL2 gl)
    {
        int[] compiled = new int[1];
        gl.glGetShaderiv(vertexShader, GL2ES2.GL_COMPILE_STATUS, compiled, 0);
        boolean success = compiled[0] != 0;
        if (!success)
        {
            int[] logLength = new int[1];
            gl.glGetShaderiv(vertexShader, GL2ES2.GL_INFO_LOG_LENGTH, logLength, 0);

            byte[] log = new byte[logLength[0]];
            gl.glGetShaderInfoLog(vertexShader, logLength[0], (int[]) null, 0, log, 0);
            System.out.println("ERROR::SHADER::VERTEX::COMPILATION_FAILED, " + new String(log));
        }
        else
        {
            System.out.println("vertexShader complie SUCCESS");
        }
    }

    public static void main(String[] args)
    {
        GlslTest01 t = new GlslTest01();

        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLJPanel pnl = new GLJPanel(capabilities);

        pnl.addGLEventListener(new GLEventListener()
        {

            @Override
            public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
            {
            }

            @Override
            public void init(GLAutoDrawable drawable)
            {
                t.doInit(drawable);
            }

            @Override
            public void dispose(GLAutoDrawable drawable)
            {
            }

            @Override
            public void display(GLAutoDrawable drawable)
            {
                t.doDisplay(drawable);
            }
        });

        final JFrame frame = new JFrame("Glsl Test");
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowevent)
            {
                frame.dispose();
                System.exit(0);
            }
        });

        frame.getContentPane().add(pnl, BorderLayout.CENTER);
        frame.setSize(GlslTest01.width, GlslTest01.height);
        frame.setVisible(true);
    }
}