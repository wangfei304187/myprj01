import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.dcm4che2.data.DicomObject;

import com.jogamp.common.nio.Buffers;
import com.jogamp.nativewindow.util.PixelFormat;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES1;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.fixedfunc.GLLightingFunc;
import com.jogamp.opengl.fixedfunc.GLMatrixFunc;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import com.kdevn.glsl.GlslUtils;
import com.kdevn.image.util.DcmUtils;
import com.kdevn.image.util.DiskUtils;
import com.kdevn.image.util.PixelUtils;

public class CubeTexture5_BufferedImage implements GLEventListener {
	private GLU glu = new GLU();
	private float xrot, yrot, zrot;
	private int texture;

	private JPanel topPnl;
	private JButton resetBtn;

	private static GLJPanel glcanvas;

	private List<DicomObject> axialList;

	/*
	 * GL_NO_ERROR ：（0）当前无错误值 GL_INVALID_ENUM
	 * ：（1280）仅当使用非法枚举参数时，如果使用该参数有指定环境，则返回 GL_INVALID_OPERATION GL_INVALID_VALUE
	 * ：（1281）仅当使用非法值参数时，如果使用该参数有指定环境，则返回 GL_INVALID_OPERATION
	 * GL_INVALID_OPERATION ：（1282）命令的状态集合对于指定的参数非法。 GL_STACK_OVERFLOW
	 * ：（1283）压栈操作超出堆栈大小。 GL_STACK_UNDERFLOW ：（1284）出栈操作达到堆栈底部。 GL_OUT_OF_MEMORY
	 * ：（1285）不能分配足够内存时。 GL_INVALID_FRAMEBUFFER_OPERATION ：（1286）当操作未准备好的真缓存时。
	 * GL_CONTEXT_LOST ：（1287）由于显卡重置导致 OpenGL context 丢失。
	 */

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity(); // Reset The View
		gl.glTranslatef(0f, 0f, -5.0f);
		gl.glRotatef(xrot, 1.0f, 1.0f, 1.0f);
		gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);
		gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
		gl.glBegin(GL2ES3.GL_QUADS);
		// Front Face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		// Back Face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		// Top Face
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		// Bottom Face
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		// Right face
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, -1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(1.0f, -1.0f, 1.0f);
		// Left Face
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, -1.0f);
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-1.0f, -1.0f, 1.0f);
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, 1.0f);
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-1.0f, 1.0f, -1.0f);
		gl.glEnd();
		gl.glFlush();
		// change the speeds here
		// xrot += .1f;
		// yrot += .1f;
		// zrot += .1f;
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
		// gl.glEnable(GL2.GL_TEXTURE_3D);
		try {
			// Style--1
			// File im = new
			// File("/home/wf/mygit/test/prj_jogl/resources/images/lena.jpg");
			// Texture t = TextureIO.newTexture(im, true);
			// texture= t.getTextureObject(gl);

			int[] textureIds = new int[1];
			gl.glGenTextures(1, textureIds, 0);
			texture = textureIds[0];

			// Style--2,
			// InputStream is1 =
			// GlslUtils.getUrl("images/smile_1448px.png").openStream();
			// TextureData textureData =
			// TextureIO.newTextureData(gl.getGLProfile(), is1, false, "png");
			// gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
			// gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
			// GL.GL_NEAREST);
			// gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
			// GL.GL_LINEAR);
			// // *.png -- RGBA, *.jpg -- RGB
			// gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA,
			// textureData.getWidth(), textureData.getHeight(), 0, GL.GL_RGBA,
			// GL.GL_UNSIGNED_BYTE, textureData.getBuffer());
			// gl.glGenerateMipmap(GL.GL_TEXTURE_2D);

			// Style--3
			// InputStream is1 =
			// GlslUtils.getUrl("images/lena.jpg").openStream();
			// TextureData textureData = TextureIO.newTextureData(
			// gl.getGLProfile(), is1, false, "jpg");
			// gl.glBindTexture(GL.GL_TEXTURE_2D, texture);
			// gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
			// GL.GL_NEAREST);
			// gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
			// GL.GL_LINEAR);
			// // *.png -- RGBA, *.jpg -- RGB
			// gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB,
			// textureData.getWidth(), textureData.getHeight(), 0,
			// GL.GL_RGB, GL.GL_UNSIGNED_BYTE, textureData.getBuffer());
			// gl.glGenerateMipmap(GL.GL_TEXTURE_2D);

			int[] sizes = loadDicomImageData();
			int width = sizes[0];
			int height = sizes[1];
			int depth = sizes[2]; // axialList.size()

			// dicom image --> BufferedImage --> textureData
			DcmUtils du = new DcmUtils();
			BufferedImage bi = du.dcmToBufferedImage(axialList.get(30));
			ImageUtil.flipImageVertically(bi);
			TextureData textureData = AWTTextureIO.newTextureData(
					gl.getGLProfile(), bi, false);
			
			gl.glBindTexture(GL2.GL_TEXTURE_2D, texture);
			System.out.println("loadTextureArray func 0: " + gl.glGetError());

			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
					GL.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
					GL.GL_LINEAR);
//			 gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_LUMINANCE,
//					textureData.getWidth(), textureData.getHeight(), 0,
//					GL.GL_LUMINANCE, GL4.GL_BYTE, textureData.getBuffer());
			gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB,
					textureData.getWidth(), textureData.getHeight(), 0,
					GL.GL_RGB, GL.GL_UNSIGNED_BYTE, textureData.getBuffer());
			gl.glGenerateMipmap(GL.GL_TEXTURE_2D);
			System.out.println("loadTextureArray func 1: " + gl.glGetError());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width,
			int height) {
		final GL2 gl = drawable.getGL().getGL2();
		if (height <= 0) {
			height = 1;
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

		CubeTexture5_BufferedImage app = new CubeTexture5_BufferedImage();
		app.showFrame();
	}

	private int[] loadDicomImageData() {
		String seriesDirStr = "testdata/1"; // slice count: 69
		// String seriesDirStr = "testdata/2"; // slice count: 354
		long lo1 = System.currentTimeMillis();
		try {
			axialList = DiskUtils.readDcmFromDisk(new File(seriesDirStr));
		} catch (IOException e) {
			e.printStackTrace();
		}
		long lo2 = System.currentTimeMillis();
		System.out.println("load time: " + (lo2 - lo1) / 1000f);

		DcmUtils du = new DcmUtils();
		DicomObject firstDcmObj = axialList.get(0);
		int axialRows = du.getRows(firstDcmObj); // 512
		int axialCols = du.getColumns(firstDcmObj); // 512
		float axialST = du.getSliceThickness(firstDcmObj);
		float axialPixelSpacingH = du.getPixelSpacingH(firstDcmObj); // axialPixelSpacingV/H
		float axialPixelSpacingV = du.getPixelSpacingV(firstDcmObj);

		int axialSliceCnt = axialList.size();
		System.out.println("axialList size: " + axialList.size()); // 69

		return new int[] { axialCols, axialRows, axialSliceCnt };
	}

	public CubeTexture5_BufferedImage() {
		super();
		topPnl = new JPanel();
		resetBtn = new JButton("Reset");
		topPnl.add(resetBtn);

		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		glcanvas = new GLJPanel(capabilities);
		glcanvas.addGLEventListener(this);
		glcanvas.setSize(400, 400);
		glcanvas.setPreferredSize(new Dimension(400, 400));

		MouseAdapter ml = new CustomMouseAdapter();
		glcanvas.addMouseListener(ml);
		glcanvas.addMouseMotionListener(ml);

		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				xrot = 0f;
				yrot = 0f;
				zrot = 0f;

				glcanvas.repaint();
			}
		});

	}

	public void showFrame() {
		final JFrame frame = new JFrame("Textured Cube");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(topPnl, BorderLayout.NORTH);
		frame.getContentPane().add(glcanvas, BorderLayout.CENTER);
		frame.setSize(frame.getContentPane().getPreferredSize());
		frame.setVisible(true);
		// final FPSAnimator animator = new FPSAnimator(glcanvas, 300, true);
		// animator.start();
	}

	class CustomMouseAdapter extends MouseAdapter {
		int px;
		int py;
		int dx;
		int dy;

		public void mousePressed(MouseEvent e) {
			px = e.getX();
			py = e.getY();
		}

		public void mouseDragged(MouseEvent e) {
			dx = e.getX();
			dy = e.getY();

			float delta = 0.8f;
			GLJPanel c = (GLJPanel) e.getSource();
			if (Math.abs(dx - px) > Math.abs(dy - py)) {
				if (dx - px > 0) {
					yrot += delta;
				} else {
					yrot -= delta;
				}
			} else {
				if (dy - py > 0) {
					zrot -= delta;
				} else {
					zrot += delta;
				}
			}

			c.repaint();
		}
	}
}