package com.kdevn.opengl;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;

public class OpenglTest01
{
    private int width = 800;
    private int height = 600;

    /*@formatter:off*/
    float vertices[] = {
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.0f, 0.5f, 0.0f
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

    }

    public void doDisplay(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glViewport(0, 0, width, height);
    }

    public static void main(String[] args)
    {
        OpenglTest01 t = new OpenglTest01();

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

        final JFrame frame = new JFrame("Opengl Test");
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
