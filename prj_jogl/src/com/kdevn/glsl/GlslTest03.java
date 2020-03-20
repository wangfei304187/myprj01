package com.kdevn.glsl;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.swing.JFrame;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;

// Ref: https://www.cnblogs.com/zhxmdefj/p/11192408.html
// Ref: https://www.bilibili.com/video/av57654623?p=10
public class GlslTest03
{
    private static int width = 800;
    private static int height = 800;

    private GLU glu = new GLU();
    private float xrot,yrot,zrot;

    private int shaderProgram;
    private int vertexShaderId;
    private int fragmentShaderId;

    private int vao;
    private int vbo;
    private int ebo;

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
    float[] vertices ={
            0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 1.0f
    };

    //    int indices[] =
    //        { 1, 5, 7, 7, 3, 1, 0, 2, 6, 6, 4, 0, 0, 1, 3, 3, 2, 0, 7, 5, 4, 4, 6, 7, 2, 3, 7, 7, 6, 2, 1, 0, 4, 4,
    //                5, 1 };

    int indices[] = {
            1, 5, 7, 3,
            0, 2, 6, 4,
            0, 1, 3, 2,
            7, 5, 4, 6,
            2, 3, 7, 6,
            0, 4, 5, 1
    };

    /*@formatter:on*/

    public void doDispose(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();

        gl.glUseProgram(0);
        gl.glDeleteBuffers(1, new int[] { vbo }, 0); // Release VBO, color and vertices, buffer GPU memory.
        gl.glDeleteBuffers(1, new int[] { ebo }, 0);
        gl.glDetachShader(shaderProgram, vertexShaderId);
        gl.glDeleteShader(vertexShaderId);
        gl.glDetachShader(shaderProgram, fragmentShaderId);
        gl.glDeleteShader(fragmentShaderId);
        gl.glDeleteProgram(shaderProgram);
    }

    public void doReshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        GL2 gl = drawable.getGL().getGL2();
        // gl.glViewport(0, 0, GlslTest03.width, GlslTest03.height);
        if(height <=0)
        {
            height =1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void doInit(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();

        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        //gl.glEnable(GL.GL_TEXTURE_2D);

        vertexShaderId = GlslUtils.createVertexShader(gl, vertexShaderSource);

        fragmentShaderId = GlslUtils.createFragmentShader(gl, fragmentShaderSource);

        shaderProgram = GlslUtils.createProgram(gl, vertexShaderId, fragmentShaderId);

        // 连接后删除
        // gl.glDeleteShader(vertexShaderId);
        // gl.glDeleteShader(fragmentShaderId);

        vao = GlslUtils.genVAO(gl);
        vbo = GlslUtils.genVBO(gl);
        ebo = GlslUtils.genEBO(gl);

        // loadVertex(gl, vao, vbo, ebo, vertices);
    }

    public void doDisplay(GLAutoDrawable drawable)
    {
        System.out.println("doDisplay");
        GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();                       // Reset The View
        gl.glTranslatef(0f, 0f, -5.0f);
        gl.glRotatef(xrot, 1.0f, 1.0f, 1.0f);
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);

        loadVertex(gl, vao, vbo, ebo, vertices);

        gl.glUseProgram(shaderProgram);
        // gl.glDrawArrays(GL2ES3.GL_QUADS, 0, 4);
        gl.glDrawElements(GL2ES3.GL_QUADS, indices.length, GL.GL_UNSIGNED_INT, 0);

        // gl.glViewport(0, 0, GlslTest03.width, GlslTest03.height);

        //change the speeds here
        xrot+=.1f;
        yrot+=.1f;
        zrot+=.1f;
    }

    /**
     * @param gl
     */
    private void loadVertex(GL2 gl, int vao, int vbo, int ebo, float[] vertices)
    {
        // 1. 绑定VAO
        gl.glBindVertexArray(vao);

        // /////// BEGIN attribute pointer 0 --> VBO 1 ///////////

        // 2. 把顶点数组复制到缓冲中供OpenGL使用
        // Select the VBO, GPU memory data, to use for vertices
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo);
        gl.glBindBuffer(GL.GL_ELEMENT_ARRAY_BUFFER, ebo);

        // 3.
        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertices);
        // transfer data to VBO, this perform the copy of data from CPU -> GPU memory
        // float -> 4 bytes
        gl.glBufferData(GL.GL_ARRAY_BUFFER, vertices.length * 4, vertexBuffer, GL.GL_STATIC_DRAW);
        IntBuffer indicesBuffer = Buffers.newDirectIntBuffer(indices);
        gl.glBufferData(GL.GL_ELEMENT_ARRAY_BUFFER, indices.length * 4, indicesBuffer, GL.GL_STATIC_DRAW);

        // 4. tell GPU the value strut in VBO
        // Associate Vertex attribute 0 with the last bound VBO
        /*@formatter:off*/
        gl.glVertexAttribPointer(0 /* the vertex attribute */,
                3, /* size */
                GL.GL_FLOAT,
                false /* normalized? */,
                0 /* stride */,
                0 /* The bound VBO data offset */);
        /*@formatter:on*/
        gl.glEnableVertexAttribArray(0); // layout (location = 0) ...

        // /////// ENDDD attribute pointer 0 --> VBO 1 ///////////

        // /////// BEGIN attribute pointer 1 --> VBO 2 (position part) ///////////
        // TODO
        // /////// ENDDD attribute pointer 1 --> VBO 2 (position part) ///////////

        // /////// BEGIN attribute pointer 2 --> VBO 2 (color part) ///////////
        // TODO
        // /////// ENDDD attribute pointer 2 --> VBO 2 (color part) ///////////

        // /////// BEGIN attribute pointer 3 --> VBO 3 ///////////
        // TODO
        // /////// ENDDD attribute pointer 3 --> VBO 3 ///////////
    }

    // /////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args)
    {
        GlslTest03 t = new GlslTest03();

        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLJPanel pnl = new GLJPanel(capabilities);

        pnl.addGLEventListener(new GLEventListener()
        {

            @Override
            public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
            {
                t.doReshape(drawable, x, y, width, height);
            }

            @Override
            public void init(GLAutoDrawable drawable)
            {
                t.doInit(drawable);
            }

            @Override
            public void dispose(GLAutoDrawable drawable)
            {
                t.doDispose(drawable);
            }

            @Override
            public void display(GLAutoDrawable drawable)
            {
                t.doDisplay(drawable);
            }
        });

        final JFrame frame = new JFrame("Glsl Test02");
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent windowevent)
            {
                frame.dispose();
                System.exit(0);
            }
        });
        frame.addComponentListener(new ComponentListener()
        {

            @Override
            public void componentShown(ComponentEvent e)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void componentResized(ComponentEvent e)
            {
                GlslTest03.width = frame.getWidth();
                GlslTest03.height = frame.getHeight();
            }

            @Override
            public void componentMoved(ComponentEvent e)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void componentHidden(ComponentEvent e)
            {
                // TODO Auto-generated method stub

            }
        });

        frame.getContentPane().add(pnl, BorderLayout.CENTER);
        frame.setSize(GlslTest03.width, GlslTest03.height);
        frame.setVisible(true);
    }
}