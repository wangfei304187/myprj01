package com.kdevn.glsl;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.swing.JFrame;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

// Ref: https://www.cnblogs.com/zhxmdefj/p/11192408.html
// Ref: https://www.bilibili.com/video/av57654623?p=10
public class GlslTest06
{
    private int width = 800;
    private int height = 600;

    private int programId;
    private int vertexShaderId;
    private int fragmentShaderId;

    private int vao;
    private int vbo;

    /*@formatter:off*/
//    private String vertexShaderSource = "#version 330 core\n"
//            + "layout (location = 0) in vec3 aPos;\n"
//            + "layout (location = 1) in vec2 aTexCoord;\n"
//            + "out vec2 TexCoord;\n"
//            + "void main()\n"
//            + "{\n"
//            + "   gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n"
//            + "   TexCoord=aTexCoord;\n"
//            + "}";
//    private String fragmentShaderSource = "#version 330 core\n"
//            //+ "out vec4 FragColor;\n"
//            + "in vec2 TexCoord;\n"
//            + "uniform sampler2D ourTexture1;"
//            + "uniform sampler2D ourTexture2;"
//            +  "void main()\n"
//            +  "{\n"
//			+  "   gl_FragColor = texture(ourTexture1, TexCoord) + texture(ourTexture2, TexCoord);\n"
//            +  "}\n";
    private String vertexShaderSource = GlslUtils.loadGlslSource("shader/06.vert");
    private String fragmentShaderSource = GlslUtils.loadGlslSource("shader/06.frag");
    /*@formatter:on*/

    // 顶点数据
    /*@formatter:off*/
    // (posX, poxY, posZ, texCoordX, texCoordY)
    float[] vertices ={
            0.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            
            0, 0,
            1, 0,
            1, 1,
            0, 1
    };

    /*@formatter:on*/

    public void doDispose(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();

        gl.glUseProgram(0);
        gl.glDeleteBuffers(1, new int[] { vbo }, 0); // Release VBO, color and vertices, buffer GPU memory.
        gl.glDetachShader(programId, vertexShaderId);
        gl.glDeleteShader(vertexShaderId);
        gl.glDetachShader(programId, fragmentShaderId);
        gl.glDeleteShader(fragmentShaderId);
        gl.glDeleteProgram(programId);
    }

    public void doReshape(GLAutoDrawable drawable, int x, int y, int width, int height)
    {
        // TODO
    }

    public void doInit(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();

        System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));

        vertexShaderId = GlslUtils.createVertexShader(gl, vertexShaderSource);

        fragmentShaderId = GlslUtils.createFragmentShader(gl, fragmentShaderSource);

        programId = GlslUtils.createProgram(gl, vertexShaderId, fragmentShaderId);

        // 连接后删除
        gl.glDeleteShader(vertexShaderId);
        gl.glDeleteShader(fragmentShaderId);

        vao = GlslUtils.genVAO(gl);
        vbo = GlslUtils.genVBO(gl);
    }

    public void doDisplay(GLAutoDrawable drawable)
    {
        // System.out.println("doDisplay");
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        loadVertex(gl, vao, vbo, vertices);

        gl.glUseProgram(programId);
        // gl.glDrawArrays(GL.GL_TRIANGLES, 0, 3);
         gl.glDrawArrays(GL2ES3.GL_QUADS, 0, 4);

        gl.glViewport(0, 0, width, height);
    }

    /**
     * @param gl
     */
    private void loadVertex(GL2 gl, int vao, int vbo, float[] vertices)
    {
        ////////////////////////////BEGIN TEXTURE/////////////////////////
        TextureData textureData = null;
        TextureData textureData2 = null;
        try
        {
            InputStream is1 = GlslUtils.getUrl("images/lena.jpg").openStream();
            textureData = TextureIO.newTextureData(gl.getGLProfile(), is1, false, "jpg");
            InputStream is2 = GlslUtils.getUrl("images/smile_1448px.png").openStream();
            textureData2 = TextureIO.newTextureData(gl.getGLProfile(), is2, false, "png");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        int[] textureIds = new int[2];
        gl.glGenTextures(2, textureIds, 0);

        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIds[0]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        // *.png -- RGBA,  *.jpg -- RGB
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, textureData.getWidth(), textureData.getHeight(), 0, GL.GL_RGB,
                GL.GL_UNSIGNED_BYTE, textureData.getBuffer());
        gl.glGenerateMipmap(GL.GL_TEXTURE_2D);

        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIds[1]);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        // *.png -- RGBA,  *.jpg -- RGB
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, textureData2.getWidth(), textureData2.getHeight(), 0,
                GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, textureData2.getBuffer());
        gl.glGenerateMipmap(GL.GL_TEXTURE_2D);

        // gl.glUseProgram(programId);
        gl.glUniform1i(gl.glGetUniformLocation(programId, "ourTexture1"), 0);
        gl.glUniform1i(gl.glGetUniformLocation(programId, "ourTexture2"), 1);

        gl.glActiveTexture(GL.GL_TEXTURE0);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIds[0]);

        gl.glActiveTexture(GL.GL_TEXTURE1);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textureIds[1]);
        
        ////////////////////////////ENDDD TEXTURE/////////////////////////

        // 1. 绑定VAO
        gl.glBindVertexArray(vao);

        // /////// BEGIN attribute pointer 0 --> VBO 1 ///////////
        // 2. 把顶点数组复制到缓冲中供OpenGL使用
        // Select the VBO, GPU memory data, to use for vertices
        gl.glBindBuffer(GL.GL_ARRAY_BUFFER, vbo);

        // 3.
        FloatBuffer vertexBuffer = Buffers.newDirectFloatBuffer(vertices);
        // transfer data to VBO, this perform the copy of data from CPU -> GPU memory
        // float -> 4 bytes
        gl.glBufferData(GL.GL_ARRAY_BUFFER, vertices.length * 4, vertexBuffer, GL.GL_STATIC_DRAW);

        // 4. tell GPU the value strut in VBO
        // Associate Vertex attribute 0 with the last bound VBO
        /*@formatter:off*/
        gl.glVertexAttribPointer(0 /* the vertex attribute */,
                3, /* size */
                GL.GL_FLOAT,
                false /* normalized? */,
                3 * 4 /* stride */,
                0 /* The bound VBO data offset */
                );
        gl.glEnableVertexAttribArray(0); // layout (location = 0) ...

        gl.glVertexAttribPointer(1 /* the vertex attribute */,
                2, /* size */
                GL.GL_FLOAT,
                false /* normalized? */,
                2 * 4 /* stride */,
                12 * 4 /* The bound VBO data offset */
                );
        gl.glEnableVertexAttribArray(1); // layout (location = 1) ...
        /*@formatter:on*/
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
        GlslTest06 t = new GlslTest06();

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

        final JFrame frame = new JFrame("Glsl Test05");
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
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}