import javax.swing.JFrame;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

public class Triangledepthtest implements GLEventListener{
    private GLU glu = new GLU();
    private float rtri =0.0f;
    @Override
    public void display( GLAutoDrawable drawable ) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel( GLLightingFunc.GL_SMOOTH );
        gl.glClearColor( 0f, 0f, 0f, 0f );
        gl.glClearDepth( 1.0f );
        gl.glEnable( GL.GL_DEPTH_TEST );
        gl.glDepthFunc( GL.GL_LEQUAL );
        gl.glHint( GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST );
        // Clear The Screen And The Depth Buffer
        gl.glClear( GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
        gl.glLoadIdentity();  // Reset The View
        gl.glTranslatef( -0.5f,0.0f,-6.0f );  // Move the triangle
        gl.glRotatef( rtri, 0.0f, 1.0f, 0.0f );
        gl.glBegin( GL.GL_TRIANGLES );
        //drawing triangle in all dimentions
        //front
        gl.glColor3f( 1.0f, 0.0f, 0.0f ); // Red
        gl.glVertex3f( 1.0f, 2.0f, 0.0f ); // Top
        gl.glColor3f( 0.0f, 1.0f, 0.0f ); // Green
        gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Left
        gl.glColor3f( 0.0f, 0.0f, 1.0f ); // Blue
        gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Right)
        //right
        gl.glColor3f( 1.0f, 0.0f, 0.0f );
        gl.glVertex3f( 1.0f, 2.0f, 0.0f ); // Top
        gl.glColor3f( 0.0f, 0.0f, 1.0f );
        gl.glVertex3f( 1.0f, -1.0f, 1.0f ); // Left
        gl.glColor3f( 0.0f, 1.0f, 0.0f );
        gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Right
        //left
        gl.glColor3f( 1.0f, 0.0f, 0.0f );
        gl.glVertex3f( 1.0f, 2.0f, 0.0f ); // Top
        gl.glColor3f( 0.0f, 1.0f, 0.0f );
        gl.glVertex3f( 1.0f, -1.0f, -1.0f ); // Left
        gl.glColor3f( 0.0f, 0.0f, 1.0f );
        gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Right
        //top
        gl.glColor3f( 0.0f, 1.0f, 0.0f );
        gl.glVertex3f( 1.0f, 2.0f, 0.0f ); // Top
        gl.glColor3f( 0.0f, 0.0f, 1.0f );
        gl.glVertex3f( -1.0f, -1.0f, -1.0f ); // Left
        gl.glColor3f( 0.0f, 1.0f, 0.0f );
        gl.glVertex3f( -1.0f, -1.0f, 1.0f ); // Right
        gl.glEnd(); // Done Drawing 3d  triangle (Pyramid)
        gl.glFlush();
        rtri +=0.2f;
    }
    @Override
    public void dispose( GLAutoDrawable drawable ) {
        //method body
    }
    @Override
    public void init( GLAutoDrawable drawable  ) {
        final GL2 gl = drawable.getGL().getGL2();
        gl.glShadeModel( GLLightingFunc.GL_SMOOTH );
        gl.glClearColor( 0f, 0f, 0f, 0f );
        gl.glClearDepth( 1.0f );
        gl.glEnable( GL.GL_DEPTH_TEST );
        gl.glDepthFunc( GL.GL_LEQUAL );
        gl.glHint( GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST );
    }
    @Override
    public void reshape( GLAutoDrawable drawable, int x, int y, int width, int height ) {
        final GL2 gl = drawable.getGL().getGL2();
        if( height <=0 )
        {
            height =1;
        }
        final float h = ( float ) width / ( float ) height;
        gl.glViewport( 0, 0, width, height );
        gl.glMatrixMode( GLMatrixFunc.GL_PROJECTION );
        gl.glLoadIdentity();
        glu.gluPerspective( 45.0f, h, 1.0, 20.0 );
        gl.glMatrixMode( GLMatrixFunc.GL_MODELVIEW );
        gl.glLoadIdentity();
    }
    public static void main( String[] args ) {
        // TODO Auto-generated method stub
        final GLProfile profile = GLProfile.get( GLProfile.GL2 );
        GLCapabilities capabilities = new GLCapabilities( profile );
        // The canvas
        final GLCanvas glcanvas = new GLCanvas( capabilities );
        Triangledepthtest triangledepthtest = new Triangledepthtest();
        glcanvas.addGLEventListener( triangledepthtest );
        glcanvas.setSize( 400, 400 );
        final JFrame frame = new JFrame ( "3d  Triangle (solid)" );
        frame.getContentPane().add(glcanvas);
        frame.setSize( frame.getContentPane().getPreferredSize() );
        frame.setVisible( true );
        final FPSAnimator animator = new FPSAnimator( glcanvas, 300,true );
        animator.start();
    }
}