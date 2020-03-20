package com.kdevn.opengl;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class OpenglTest02
{
    private int width = 800;
    private int height = 600;

    static FPSAnimator animator;

    /**
     * 纹理理标号
     */
    private int texture_int;

    private final GLU glu = new GLU();

    public void doInit(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();

        System.err.println("Chosen GLCapabilities: " + drawable.getChosenGLCapabilities());
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        System.err.println("GL_VENDOR: " + gl.glGetString(GL.GL_VENDOR));
        System.err.println("GL_RENDERER: " + gl.glGetString(GL.GL_RENDERER));
        System.err.println("GL_VERSION: " + gl.glGetString(GL.GL_VERSION));

        gl.glClearColor(0.0f, 0.0f, 0.0f, 1f); // 设置背景颜色
        // gl.glViewport(0, 0, 100, 100); // 视点大小,一个视口时候没必要用这个
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, 100, 0.0, 100.0); // 使坐标系统出现在GL里，此时屏幕中最左面是坐标0，右面是100，最下0，嘴上100

        gl.glEnable(GL.GL_TEXTURE_2D);// 开启纹理
        texture_int = genTexture(gl);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture_int);// 绑定纹理
        // TextureReader.Texture texture_A = null;
        TextureData texture_A = null;
        try
        {
            // 得到图片所对应的纹理
            InputStream is = this.getClass().getResourceAsStream("lena.png");
            texture_A = TextureIO.newTextureData(gl.getGLProfile(), is, false, "png");
            // texture_A = TextureIO.newTextureData(gl.getGLProfile(), is, 304, 319, false, "png");
        }
        catch (final IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        makeRGBTexture(gl, glu, texture_A, GL.GL_TEXTURE_2D, false);
        /**
         * 下面的两行告诉OpenGL在显示图像时，当它比放大得原始的纹理大
         * ( GL_TEXTURE_MAG_FILTER )或缩小得比原始得纹理小
         * ( GL_TEXTURE_MIN_FILTER )时OpenGL采用的滤波方式。
         * 通常这两种情况下我都采用 GL_LINEAR 。这使得纹理从很远处到离屏幕很
         * 近时都平滑显示。使用 GL_LINEAR 需要CPU和显卡做更多的运算。
         * 如果您的机器很慢，您也许应该采用 GL_NEAREST 。过滤的纹理在放大的时候，
         * 看起来斑驳的很『译者注：马赛克啦』。您也可以结合这两种滤波方式。
         * 在近处时使用 GL_LINEAR ，远处时 GL_NEAREST 。
         */
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
        // TODO
        System.out.println(texture_A.getWidth() + "," + texture_A.getHeight());

    }

    public void doDisplay(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT); // 填充背景颜色

        gl.glColor3f(255.0f, 255.0f, 255.0f); // 设置GL的画图颜色，也就是画刷的颜色

        /**
         * 如果您在您的场景中使用多个纹理，您应该使用来
         * glBindTexture(GL_TEXTURE_2D, texture_int[ 所使用纹理对应的数字 ])
         * 选择要绑定的纹理。当您想改变纹理时，应该绑定新的纹理。
         * 有一点值得指出的是，您不能在 glBegin() 和 glEnd() 之间绑定纹理，
         * 必须在 glBegin() 之前或 glEnd() 之后绑定。
         */
        gl.glBindTexture(GL.GL_TEXTURE_2D, texture_int);

        gl.glBegin(GL2ES3.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);// 左下
        gl.glVertex2f(0f, 0f);
        gl.glTexCoord2f(1.0f, 0.0f);// 右下
        gl.glVertex2f(100f, 0f);
        gl.glTexCoord2f(1.0f, 1.0f);// 右上
        gl.glVertex2f(100f, 100f);
        gl.glTexCoord2f(0.0f, 1.0f);// 左上
        gl.glVertex2f(0f, 100f);
        gl.glEnd();

        gl.glColor3f(10.0f, 0.0f, 0.0f); // 设置GL的画图颜色，也就是画刷的颜色

        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(5, 5);
        gl.glVertex2d(95, 5);
        gl.glEnd();

        gl.glFlush();

        gl.glViewport(0, 0, width, height);
    }

    public static void main(String[] args)
    {
        OpenglTest02 t = new OpenglTest02();

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
                OpenglTest02.animator.stop();
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

        OpenglTest02.animator = new FPSAnimator(pnl, 60, true);
        SwingUtilities.invokeLater(

                new Runnable()
                {

                    @Override
                    public void run()
                    {

                        OpenglTest02.animator.start(); // 开始动画线程
                    }

                }

                );

        frame.getContentPane().add(pnl, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    /**
     * 申请纹理
     */
    private int genTexture(final GL gl)
    {
        final int[] tmp = new int[1];
        gl.glGenTextures(1, tmp, 0);
        return tmp[0];
    }

    /**
     * 生成纹理
     */
    private void makeRGBTexture(final GL gl, final GLU glu, final TextureData img, final int target,
            final boolean mipmapped)
    {
        if (mipmapped)
        {
            /**
             * 生成纹理，gluBuild2DMipmaps() 和 glTexImage2D() 两个都是生成纹理的。
             * 两者的区别就是：
             * 使用glTexImage2D()时所采用的位图文件分辨率必须为：64×64、128×128、256×256三种格式，如果其他大小则会出现绘制不正常。
             * gluBuild2DMipmaps()支持任意分辨率位图文件。
             */
            glu.gluBuild2DMipmaps(target, GL.GL_RGB8, img.getWidth(), img.getHeight(), GL.GL_RGB, GL.GL_UNSIGNED_BYTE,
                    img.getBuffer());
        }
        else
        {

            /**
             * 下面一行告诉OpenGL此纹理是一个2D纹理 ( GL_TEXTURE_2D )。
             * 参数“0”代表图像的详细程度，通常就由它为零去了。
             * 参数三是数据的成分数。因为图像是由红色数据，绿色数据，蓝色数据三种组分组成。
             * img.getWidth() 是纹理的宽度。如果您知道宽度，您可以在这里填入，
             * 但计算机可以很容易的为您指出此值。
             * img.getHeight() 是纹理的高度。参数零是边框的值，一般就是“0”。
             * GL_RGB 告诉OpenGL图像数据由红、绿、蓝三色数据组成。
             * GL_UNSIGNED_BYTE 意味着组成图像的数据是无符号字节类型的。
             * img.getPixels()告诉OpenGL纹理数据的来源。此例中指向存放在 TextureImage[0] 记录中的数据。
             */
            gl.glTexImage2D(target, 0, 3, img.getWidth(), img.getHeight(), 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE,
                    img.getBuffer());

        }
    }

}
