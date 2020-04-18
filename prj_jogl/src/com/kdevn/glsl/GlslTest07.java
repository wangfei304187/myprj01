package com.kdevn.glsl;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import javax.swing.JFrame;

import org.dcm4che2.data.DicomObject;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GL2GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLException;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import com.kdevn.image.util.DcmUtils;
import com.kdevn.image.util.DiskUtils;
import com.kdevn.image.util.ImageUtils;

// Ref: https://www.cnblogs.com/zhxmdefj/p/11192408.html
// Ref: https://www.bilibili.com/video/av57654623?p=10
public class GlslTest07 {
	private int width = 800;
	private int height = 600;

	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;

	private int vao;
	private int vbo;
	private int[] textureIds = new int[1];

	/* @formatter:off */
	private String filename = "07";
	private String vertexShaderSource = GlslUtils.loadGlslSource("shader/" + filename + ".vert");
	private String fragmentShaderSource = GlslUtils.loadGlslSource("shader/" + filename + ".frag");
	/* @formatter:on */

	// 顶点数据
	/* @formatter:off */
	// (posX, poxY, posZ, texCoordX, texCoordY)
//	float[] vertices = { 
//			0.0f, 0.0f, 0.0f, 
//			1.0f, 0.0f, 0.0f, 
//			1.0f, 1.0f, 0.0f, 
//			0.0f, 1.0f, 0.0f,
//
//	        0, 0, 
//	        1, 0, 
//	        1, 1, 
//	        0, 1 
//	};
	float[] vertices = { 
			0.0f, 0.0f, 0.0f, 
			1.0f, 0.0f, 0.0f, 
			1.0f, 1.0f, 0.0f, 
			0.0f, 1.0f, 0.0f,

			0, 1, /*绑定的纹理顶点上下互换*/
			1, 1,
			1, 0, 
		    0, 0
	};
	/* @formatter:on */

	private DcmUtils du = new DcmUtils();
	private List<DicomObject> axialList;
	private int currIndex = 0;
	
	private int imgWidth;
	private int imgHeight;
	private float wc;
	private float ww;
	private int rescaleSlope;
	private int rescaleIntercept;
	private short[] pixelData;

	private float tmpWC;
	private float tmpWW;

	private GLJPanel pnl;

	private GL2 gl;
	
	public void doDispose(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();

		gl.glUseProgram(0);
		gl.glDeleteBuffers(1, new int[] { vbo }, 0); // Release VBO, color and vertices, buffer GPU memory.
		gl.glDetachShader(programId, vertexShaderId);
		gl.glDeleteShader(vertexShaderId);
		gl.glDetachShader(programId, fragmentShaderId);
		gl.glDeleteShader(fragmentShaderId);
		gl.glDeleteProgram(programId);
	}

	public void doReshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO
		this.width = width;
		this.height = height;
		GL2 gl = drawable.getGL().getGL2();
		gl.glViewport(x, y, width, height);
	}

	public void doInit(GLAutoDrawable drawable) {
		gl = drawable.getGL().getGL2();

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
		
		gl.glGenTextures(1, textureIds, 0);

		// ------------------------------------------------

		loadDicomImageData();
		DicomObject dcmObj = axialList.get(0);
		imgWidth = du.getColumns(dcmObj);
		imgHeight = du.getRows(dcmObj);
		wc = du.getWC(dcmObj);
		ww = du.getWW(dcmObj);
		tmpWC = wc;
		tmpWW = ww;
		rescaleSlope = du.getRescaleSlope(dcmObj);
		rescaleIntercept = du.getRescaleIntercept(dcmObj);

		System.out.println("imgWidth=" + imgWidth + ", imgHeight=" + imgHeight);
		System.out.println("wc=" + wc + ", ww=" + ww + ", rescaleSlope=" + rescaleSlope + ", rescaleIntercept="
		        + rescaleIntercept);

//        wc = (float) ((wc - rescaleIntercept) / rescaleSlope);
//        ww = (float) (ww / rescaleSlope);
//        System.out.println("[After]: wc=" + wc + ", ww=" + ww);

		//pixelData = du.getPixels16(dcmObj);

		// --------------------------------------------
	}

	public void doDisplay(GLAutoDrawable drawable) {
		// System.out.println("doDisplay");
		GL2 gl = drawable.getGL().getGL2();
		if (gl == this.gl) {
			System.out.println("doDisplay, true");
		} else {
			System.out.println("doDisplay, false");
		}
		gl.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		loadVertex(gl, vao, vbo, vertices);

		gl.glViewport(0, 0, width, height);
	}

	/**
	 * @param gl
	 */
	private void loadVertex(GL2 gl, int vao, int vbo, float[] vertices) {
		//////////////////////////// BEGIN TEXTURE/////////////////////////
//        TextureData textureData = null;
//        try
//        {
//            InputStream is1 = GlslUtils.getUrl("images/lena.jpg").openStream();
//            textureData = TextureIO.newTextureData(gl.getGLProfile(), is1, false, "jpg");
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }

		gl.glUseProgram(programId);
		
//		gl.glGenTextures(1, textureIds, 0);
		gl.glBindTexture(GL.GL_TEXTURE_2D, textureIds[0]);
//		gl.glActiveTexture(GL.GL_TEXTURE0);

		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);

		pixelData = du.getPixels16(axialList.get(currIndex));
		gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_R16, imgWidth, imgHeight, 0, GL2.GL_RED, GL2.GL_UNSIGNED_SHORT,
		        ShortBuffer.wrap(pixelData));

		gl.glUniform1i(gl.glGetUniformLocation(programId, "ourTexture1"), 0);

		gl.glUniform1f(gl.glGetUniformLocation(programId, "wc"), tmpWC);
		gl.glUniform1f(gl.glGetUniformLocation(programId, "ww"), tmpWW);
		gl.glUniform1f(gl.glGetUniformLocation(programId, "rescaleSlope"), rescaleSlope);
		gl.glUniform1f(gl.glGetUniformLocation(programId, "rescaleIntercept"), rescaleIntercept);
		System.out.println("in loadVertex: wc=" + wc + ", ww=" + ww + ", " + rescaleSlope + ", " + rescaleIntercept);

		gl.glActiveTexture(GL.GL_TEXTURE0);

		//////////////////////////// ENDDD TEXTURE/////////////////////////

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
		/* @formatter:off */
		gl.glVertexAttribPointer(0 /* the vertex attribute */, 3, /* size */
		        GL.GL_FLOAT, false /* normalized? */, 3 * 4 /* stride */, 0 /* The bound VBO data offset */
		);
		gl.glEnableVertexAttribArray(0); // layout (location = 0) ...

		gl.glVertexAttribPointer(1 /* the vertex attribute */, 2, /* size */
		        GL.GL_FLOAT, false /* normalized? */, 2 * 4 /* stride */, 12 * 4 /* The bound VBO data offset */
		);
		gl.glEnableVertexAttribArray(1); // layout (location = 1) ...
		/* @formatter:on */
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

		gl.glDrawArrays(GL2ES3.GL_QUADS, 0, 4);
	}

	// /////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) {
		GlslTest07 t = new GlslTest07();
		t.start();
	}

	public void start() {
		GLProfile profile = GLProfile.getDefault();
		GLCapabilities capabilities = new GLCapabilities(profile);
		pnl = new GLJPanel(capabilities);

		pnl.addGLEventListener(new GLEventListener() {

			@Override
			public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
				doReshape(drawable, x, y, width, height);
			}

			@Override
			public void init(GLAutoDrawable drawable) {
				doInit(drawable);
			}

			@Override
			public void dispose(GLAutoDrawable drawable) {
				doDispose(drawable);
			}

			@Override
			public void display(GLAutoDrawable drawable) {
				doDisplay(drawable);
			}
		});

		final JFrame frame = new JFrame("Glsl Test07");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowevent) {
				frame.dispose();
				System.exit(0);
			}
		});

		MouseAdapter ml = new MyMouseListener();
		pnl.addMouseListener(ml);
		pnl.addMouseMotionListener(ml);
		pnl.addMouseWheelListener(ml);

		frame.getContentPane().add(pnl, BorderLayout.CENTER);
		frame.setSize(800, 600);
		frame.setVisible(true);
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

	public void refresh() {
		pnl.repaint();
	}

	private class MyMouseListener extends MouseAdapter {
		private Point p1 = new Point(0, 0);
		private Point p2 = new Point(0, 0);
		private int deltaX;
		private int deltaY;

		@Override
		public void mousePressed(MouseEvent e) {

			if ((e.getModifiers() & InputEvent.BUTTON2_MASK) == 0) {
				// 没有按下鼠标中建，返回
				return;
			}

			p1.x = e.getX();
			p1.y = e.getY();
			p2.x = p1.x;
			p2.y = p1.y;
		}

		@Override
		public void mouseDragged(MouseEvent e) {

			if ((e.getModifiers() & InputEvent.BUTTON2_MASK) == 0) {
				// 没有按下鼠标中建，返回
				return;
			}

			p2.x = e.getX();
			p2.y = e.getY();

			deltaX = p2.x - p1.x;
			deltaY = p2.y - p1.y;

//            if (Math.abs(deltaX) > Math.abs(deltaY))
//            {
//            	deltaY = 0;
//            }
//            else
//            {
//            	deltaX = 0;
//            }

			tmpWC = wc + deltaX;
	    	tmpWW = ww + deltaY;
	    	
	    	refresh();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			wc = tmpWC;
			ww = tmpWW;
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			 if (e.getUnitsToScroll() > 0) // OR e.getWheelRotation() > 0
			 {
				 currIndex++;
			 }
			 else
			 {
				 currIndex--;
			 }
			 
			 if (currIndex < 0)
			 {
				 currIndex = 0;
			 }
			 
			 if (currIndex > axialList.size()-1)
			 {
				 currIndex = axialList.size()-1;
			 }
			 
			 refresh();
		}

	}

	public static final int sizeof(final GL gl, final int tmp[], final int bytesPerPixel, int width, int height,
	        int depth, final boolean pack) {
		int rowLength = 0;
		int skipRows = 0;
		int skipPixels = 0;
		int alignment = 1;
		int imageHeight = 0;
		int skipImages = 0;

		if (pack) {
			alignment = glGetInteger(gl, GL.GL_PACK_ALIGNMENT, tmp); // es2, es3, gl3
			if (gl.isGL2ES3()) {
				rowLength = glGetInteger(gl, GL2ES3.GL_PACK_ROW_LENGTH, tmp); // es3, gl3
				skipRows = glGetInteger(gl, GL2ES3.GL_PACK_SKIP_ROWS, tmp); // es3, gl3
				skipPixels = glGetInteger(gl, GL2ES3.GL_PACK_SKIP_PIXELS, tmp); // es3, gl3
				if (depth > 1 && gl.isGL2GL3()
				        && gl.getContext().getGLVersionNumber().compareTo(GLContext.Version1_2) >= 0) {
					imageHeight = glGetInteger(gl, GL2GL3.GL_PACK_IMAGE_HEIGHT, tmp); // gl3, GL_VERSION_1_2
					skipImages = glGetInteger(gl, GL2GL3.GL_PACK_SKIP_IMAGES, tmp); // gl3, GL_VERSION_1_2
				}
			}
		} else {
			alignment = glGetInteger(gl, GL.GL_UNPACK_ALIGNMENT, tmp); // es2, es3, gl3
			if (gl.isGL2ES3()) {
				rowLength = glGetInteger(gl, GL2ES2.GL_UNPACK_ROW_LENGTH, tmp); // es3, gl3
				skipRows = glGetInteger(gl, GL2ES2.GL_UNPACK_SKIP_ROWS, tmp); // es3, gl3
				skipPixels = glGetInteger(gl, GL2ES2.GL_UNPACK_SKIP_PIXELS, tmp); // es3, gl3
				if (depth > 1 && (gl.isGL3ES3() || (gl.isGL2GL3()
				        && gl.getContext().getGLVersionNumber().compareTo(GLContext.Version1_2) >= 0))) {
					imageHeight = glGetInteger(gl, GL2ES3.GL_UNPACK_IMAGE_HEIGHT, tmp);// es3, gl3, GL_VERSION_1_2
					skipImages = glGetInteger(gl, GL2ES3.GL_UNPACK_SKIP_IMAGES, tmp); // es3, gl3, GL_VERSION_1_2
				}
			}
		}

		// Try to deal somewhat correctly with potentially invalid values
		width = Math.max(0, width);
		height = Math.max(1, height); // min 1D
		depth = Math.max(1, depth); // min 1 * imageSize
		skipRows = Math.max(0, skipRows);
		skipPixels = Math.max(0, skipPixels);
		alignment = Math.max(1, alignment);
		skipImages = Math.max(0, skipImages);

		imageHeight = (imageHeight > 0) ? imageHeight : height;
		rowLength = (rowLength > 0) ? rowLength : width;

		int rowLengthInBytes = rowLength * bytesPerPixel;
		int skipBytes = skipPixels * bytesPerPixel;

		switch (alignment) {
		case 1:
			break;
		case 2:
		case 4:
		case 8: {
			// x % 2n == x & (2n - 1)
			int remainder = rowLengthInBytes & (alignment - 1);
			if (remainder > 0) {
				rowLengthInBytes += alignment - remainder;
			}
			remainder = skipBytes & (alignment - 1);
			if (remainder > 0) {
				skipBytes += alignment - remainder;
			}
		}
			break;
		default:
			throw new GLException("Invalid alignment " + alignment
			        + ", must be 2**n (1,2,4,8). Pls notify the maintainer in case this is our bug.");
		}

		/**
		 * skipImages, depth, skipPixels and skipRows are static offsets.
		 *
		 * skipImages and depth are in multiples of image size.
		 *
		 * skipBytes and rowLengthInBytes are aligned
		 *
		 * rowLengthInBytes is the aligned byte offset from line n to line n+1 at the
		 * same x-axis position.
		 */
		return skipBytes + // aligned skipPixels * bpp
		        (skipImages + depth - 1) * imageHeight * rowLengthInBytes + // aligned whole images
		        (skipRows + height - 1) * rowLengthInBytes + // aligned lines
		        width * bytesPerPixel; // last line
	}

	private static final int glGetInteger(final GL gl, final int pname, final int[] tmp) {
		gl.glGetIntegerv(pname, tmp, 0);
		return tmp[0];
	}
}