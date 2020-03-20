import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureTest01 implements GLEventListener
{
    private Texture earthTexture;

    public static void main(String[] args)
    {
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);
        final Frame frame = new Frame("Texture Test");
        frame.setSize(700, 700);
        frame.add(canvas);
        frame.setVisible(true);
        // by default, an AWT Frame doesn't do anything when you click
        // the close button; this bit of code will terminate the program when
        // the window is asked to close
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                frame.dispose();
                System.exit(0);
            }
        });
        canvas.addGLEventListener(new TextureTest01());
    }

    @Override
    public void display(GLAutoDrawable arg0)
    {
        update();
        render(arg0);
    }

    private void update()
    {
        // TODO Auto-generated method stub
    }

    private void render(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glEnable(GL.GL_TEXTURE_2D);

        gl.glBegin(GL.GL_TRIANGLES);
        earthTexture.bind(gl);
        // Begin drawing triangle sides
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        // Set colour to red
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(0.0f, 1.0f, 1.0f);
        // Top vertex
        gl.glTexCoord2f(-1.0f, -2.0f);
        gl.glVertex3f(-1.0f, -1.0f, 0.0f);
        // Bottom left vertex
        gl.glTexCoord2f(1.0f, -2.0f);
        gl.glVertex3f(1.0f, -1.0f, 0.0f);
        // Bottom right vertex
        gl.glEnd();
    }

    @Override
    public void dispose(GLAutoDrawable arg0)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void init(GLAutoDrawable arg0)
    {
        GL2 gl = arg0.getGL().getGL2();
        // Load texture.

        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        try
        {
            String path = "images/lena.png";
            URL url = ClassLoader.getSystemClassLoader().getResource(path);
            if (url == null)
            {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                url = cl.getResource(path);
            }

            InputStream is = url.openStream();
            final TextureData textureData = TextureIO.newTextureData(gl.getGLProfile(), is, false, "png");
            earthTexture = TextureIO.newTexture(gl, textureData);
        }
        catch (IOException exc)
        {
            exc.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3, int arg4)
    {
        // TODO Auto-generated method stub
    }
}