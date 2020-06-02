import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.dcm4che2.data.DicomObject;

import com.jogamp.common.nio.Buffers;
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
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;
import com.kdevn.image.util.DcmUtils;
import com.kdevn.image.util.DiskUtils;

public class CubeTexture3D2 implements GLEventListener {
	private GLU glu = new GLU();
	private float xrot, yrot, zrot;
	private int texture;

	private JPanel topPnl;
	private JButton resetBtn;

	private static GLJPanel glcanvas;

	private int width;
	private int height;
	private int depth;
	private List<DicomObject> axialList;

	/*
	 * GL_NO_ERROR ：（0）当前无错误值 GL_INVALID_ENUM ：（1280）仅当使用非法枚举参数时，如果使用该参数有指定环境，则返回
	 * GL_INVALID_OPERATION GL_INVALID_VALUE ：（1281）仅当使用非法值参数时，如果使用该参数有指定环境，则返回
	 * GL_INVALID_OPERATION GL_INVALID_OPERATION ：（1282）命令的状态集合对于指定的参数非法。
	 * GL_STACK_OVERFLOW ：（1283）压栈操作超出堆栈大小。 GL_STACK_UNDERFLOW ：（1284）出栈操作达到堆栈底部。
	 * GL_OUT_OF_MEMORY ：（1285）不能分配足够内存时。 GL_INVALID_FRAMEBUFFER_OPERATION
	 * ：（1286）当操作未准备好的真缓存时。 GL_CONTEXT_LOST ：（1287）由于显卡重置导致 OpenGL context 丢失。
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
		gl.glBindTexture(GL2ES2.GL_TEXTURE_3D, texture);
		gl.glBegin(GL2ES3.GL_QUADS);
		
		for (int i=0; i<depth; i++)
		{
			gl.glTexCoord3f(0.0f, 0.0f, i*1f/depth); gl.glVertex3f(-1.0f, -1.0f,  depth*1f/width - i*2f/width);
	        gl.glTexCoord3f(1.0f, 0.0f, i*1f/depth); gl.glVertex3f( 1.0f, -1.0f,  depth*1f/width - i*2f/width);
	        gl.glTexCoord3f(1.0f, 1.0f, i*1f/depth); gl.glVertex3f( 1.0f,  1.0f,  depth*1f/width - i*2f/width);
	        gl.glTexCoord3f(0.0f, 1.0f, i*1f/depth); gl.glVertex3f(-1.0f,  1.0f,  depth*1f/width - i*2f/width);
		}
		
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
		gl.glClearColor(1f, 0f, 0f, 0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		// gl.glEnable(GL.GL_TEXTURE_2D);
		gl.glEnable(GL2ES2.GL_TEXTURE_3D);
		try {

			int[] textureIds = new int[1];
			gl.glGenTextures(1, textureIds, 0);
			texture = textureIds[0];

			int[] sizes = loadDicomImageData();
			width = sizes[0];
			height = sizes[1];
			depth = sizes[2]; // axialList.size()

			DcmUtils du = new DcmUtils();
			gl.glBindTexture(GL2ES3.GL_TEXTURE_3D, texture);
			gl.glActiveTexture(GL4.GL_TEXTURE0);
			System.out.println("loadTextureArray func 0: " + gl.glGetError());

			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_REPLACE);
			gl.glTexParameteri(GL2.GL_TEXTURE_3D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_BORDER);
			gl.glTexParameteri(GL2.GL_TEXTURE_3D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_BORDER);
			gl.glTexParameteri(GL2.GL_TEXTURE_3D, GL2.GL_TEXTURE_WRAP_R, GL2.GL_CLAMP_TO_BORDER);
			gl.glTexParameteri(GL2.GL_TEXTURE_3D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
			gl.glTexParameteri(GL2.GL_TEXTURE_3D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);

			gl.glTexImage3D(GL2ES3.GL_TEXTURE_3D, 0, GL.GL_RGB, width, height, depth, 0, GL.GL_RGB, GL.GL_UNSIGNED_BYTE, null);
			System.out.println("loadTextureArray func 1: " + gl.glGetError());

			// http://forum.jogamp.org/How-do-I-load-texture-array-in-jogl-td4039107.html#a4039110
			for (int k = 0; k < depth; k++) {
				BufferedImage bi = du.dcmToBufferedImage(axialList.get(k));
				ImageUtil.flipImageVertically(bi);
				TextureData textureData = AWTTextureIO.newTextureData(gl.getGLProfile(), bi, false);
				// byte[] pixels8 = du.getPixels8(axialList.get(k));
				gl.glTexSubImage3D(GL2ES3.GL_TEXTURE_3D, 0, 0, 0, k, width, height, 1, GL2.GL_RGB, GL2.GL_UNSIGNED_BYTE, textureData.getBuffer());
			}
			System.out.println("loadTextureArray func 2: " + gl.glGetError());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
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

		CubeTexture3D2 app = new CubeTexture3D2();
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
		System.out.println("load time: " + ((lo2 - lo1) / 1000f));

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

	private List<BufferedImage> loadDataJpg() {
		File dir = new File("testdata/00323583_Abd_anonymous");
		File[] files = doSort(dir);

		List<BufferedImage> list = new ArrayList();
		for (int i = 0; i < files.length; i++) {
			// System.out.println(files[i].getName());
			try {
				BufferedImage bi = ImageIO.read(files[i]);
				list.add(bi);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	private File[] doSort(File dir) {
		File[] files = dir.listFiles();
		Arrays.sort(files, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				String s1 = o1.getName().substring(1, o1.getName().length() - 4);
				String s2 = o2.getName().substring(1, o2.getName().length() - 4);
				int n1 = Integer.parseInt(s1);
				int n2 = Integer.parseInt(s2);
				return n1 - n2;
			}

		});

		return files;
	}

	public CubeTexture3D2() {
		super();
		topPnl = new JPanel();
		resetBtn = new JButton("Reset");
		topPnl.add(resetBtn);

		final GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		CubeTexture3D2.glcanvas = new GLJPanel(capabilities);
		CubeTexture3D2.glcanvas.addGLEventListener(this);
		CubeTexture3D2.glcanvas.setSize(400, 400);
		CubeTexture3D2.glcanvas.setPreferredSize(new Dimension(400, 400));

		MouseAdapter ml = new CustomMouseAdapter();
		CubeTexture3D2.glcanvas.addMouseListener(ml);
		CubeTexture3D2.glcanvas.addMouseMotionListener(ml);

		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				xrot = 0f;
				yrot = 0f;
				zrot = 0f;

				CubeTexture3D2.glcanvas.repaint();
			}
		});

	}

	public void showFrame() {
		final JFrame frame = new JFrame("Textured Cube");
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(topPnl, BorderLayout.NORTH);
		frame.getContentPane().add(CubeTexture3D2.glcanvas, BorderLayout.CENTER);
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

		@Override
		public void mousePressed(MouseEvent e) {
			px = e.getX();
			py = e.getY();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			dx = e.getX();
			dy = e.getY();

			float delta = 0.8f;
			GLJPanel c = (GLJPanel) e.getSource();
			if (Math.abs(dx - px) > Math.abs(dy - py)) {
				if ((dx - px) > 0) {
					yrot += delta;
				} else {
					yrot -= delta;
				}
			} else {
				if ((dy - py) > 0) {
					zrot -= delta;
				} else {
					zrot += delta;
				}
			}

			c.repaint();
		}
	}
}