import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.AWTGLReadBufferUtil;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import com.kdevn.glsl.GlslUtils;
import com.kdevn.image.util.ImageStoreUtils;

public class CubeTexture2 implements GLEventListener {
    // public static DisplayMode dm, dm_old;
    private GLU glu = new GLU();
    private float xrot,yrot,zrot;
    private int texture;
    
    private static int idx = 0;
    
    @Override
    public void display(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();                       // Reset The View
        gl.glTranslatef(0f, 0f, -5.0f);
        gl.glRotatef(xrot, 1.0f, 1.0f, 1.0f);
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
        gl.glBegin(GL2ES3.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
        // Back Face
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
        // Top Face
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
        // Bottom Face
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
        // Right face
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f( 1.0f,  1.0f,  1.0f);
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f( 1.0f, -1.0f,  1.0f);
        // Left Face
        gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-1.0f, -1.0f,  1.0f);
        gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f,  1.0f);
        gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-1.0f,  1.0f, -1.0f);
        gl.glEnd();
        gl.glFlush();
        //change the speeds here
        xrot+=.1f;
        yrot+=.1f;
        zrot+=.1f;
        
        
        // https://github.com/sgothel/jogl/blob/master/src/test/com/jogamp/opengl/test/junit/jogl/util/texture/TestTexture01AWT.java
        BufferedImage bi = new AWTGLReadBufferUtil(gl.getGLProfile(), false).readPixelsToBufferedImage(gl, true);
        try {
			ImageStoreUtils.toJpg(bi, new File("/home/wf/myjpg/" + (idx++) + ".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    @Override
    public void dispose(GLAutoDrawable drawable) {
        // method body
    }
    @Override
    public void init(GLAutoDrawable drawable) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl.glClearColor(0f, 0f, 0f, 0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        gl.glEnable(GL.GL_TEXTURE_2D);
        try
        {
            // Style--1
            //            File im = new File("/home/wf/mygit/test/prj_jogl/resources/images/lena.jpg");
            //            Texture t = TextureIO.newTexture(im, true);
            //            texture= t.getTextureObject(gl);

            int[] textureIds = new int[1];
            gl.glGenTextures(1, textureIds, 0);
            texture = textureIds[0];

            // Style--2,
            //            InputStream is1 = GlslUtils.getUrl("images/smile_1448px.png").openStream();
            //            TextureData textureData = TextureIO.newTextureData(gl.getGLProfile(), is1, false, "png");

            //            gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
            //            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
            //            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            //            // *.png -- RGBA,  *.jpg -- RGB
            //            gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, textureData.getWidth(), textureData.getHeight(), 0, GL.GL_RGBA,
            //                    GL.GL_UNSIGNED_BYTE, textureData.getBuffer());
            //            gl.glGenerateMipmap(GL.GL_TEXTURE_2D);

            // Style--3
            InputStream is1 = GlslUtils.getUrl("images/lena.jpg").openStream();
            TextureData textureData = TextureIO.newTextureData(gl.getGLProfile(), is1, false, "jpg");

            gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
            gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
            // *.png -- RGBA,  *.jpg -- RGB
            gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB, textureData.getWidth(), textureData.getHeight(), 0, GL.GL_RGB,
                    GL.GL_UNSIGNED_BYTE, textureData.getBuffer());
            gl.glGenerateMipmap(GL.GL_TEXTURE_2D);

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        final GL2 gl = drawable.getGL().getGL2();
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
    public static void main(String[] args) {
        final GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        // The canvas
        final GLCanvas glcanvas = new GLCanvas(capabilities);
        CubeTexture2 r = new CubeTexture2();
        glcanvas.addGLEventListener(r);
        glcanvas.setSize(400, 400);
        final JFrame frame = new JFrame ("Textured Cube");
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        final FPSAnimator animator = new FPSAnimator(glcanvas, 300,true );
        animator.start();
    }
}