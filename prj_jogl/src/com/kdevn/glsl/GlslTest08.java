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
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import javax.swing.JFrame;

import org.dcm4che2.data.DicomObject;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.kdevn.image.util.DcmUtils;
import com.kdevn.image.util.DiskUtils;

// Ref: https://www.cnblogs.com/zhxmdefj/p/11192408.html
// Ref: https://www.bilibili.com/video/av57654623?p=10
public class GlslTest08 {
	private int width = 800;
	private int height = 800;
	
	private float xrot, yrot, zrot;

	private int programId;
	private int vertexShaderId;
	private int fragmentShaderId;

	private int vao;
	private int vbo;
	private int texId;

	/* @formatter:off */
	private String filename = "08";
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
	
//	float[] vertices = { 
//			0.0f, 0.0f, 0.0f, 
//			1.0f, 0.0f, 0.0f, 
//			1.0f, 1.0f, 0.0f, 
//			0.0f, 1.0f, 0.0f,
//
//			0, 1, /*绑定的纹理顶点上下互换*/
//			1, 1,
//			1, 0, 
//		    0, 0
//	};
	
//  0:  0.0f, 0.0f, 0.0f, 
//  1:  0.0f, 0.0f, 1.0f, 
//  2:  0.0f, 1.0f, 0.0f, 
//  3:  0.0f, 1.0f, 1.0f,
//  4:  1.0f, 0.0f, 0.0f, 
//  5:  1.0f, 0.0f, 1.0f, 
//  6:  1.0f, 1.0f, 0.0f, 
//  7:  1.0f, 1.0f, 1.0f 
	float[] vertices = { 
			0.0f, 0.0f, 1.0f,   1.0f, 0.0f, 1.0f,   1.0f, 1.0f, 1.0f,   0.0f, 1.0f, 1.0f, // 前
			0.0f, 0.0f, 0.0f,   0.0f, 1.0f, 0.0f,   1.0f, 1.0f, 0.0f,   1.0f, 0.0f, 0.0f, // 后
			0.0f, 0.0f, 0.0f,   0.0f, 0.0f, 1.0f,   0.0f, 1.0f, 1.0f,   0.0f, 1.0f, 0.0f, // 左
			1.0f, 1.0f, 1.0f,   1.0f, 0.0f, 1.0f,   1.0f, 0.0f, 0.0f,   1.0f, 1.0f, 0.0f, // 右
			0.0f, 1.0f, 0.0f,   0.0f, 1.0f, 1.0f,   1.0f, 1.0f, 1.0f,   1.0f, 1.0f, 0.0f, // 上
			0.0f, 0.0f, 1.0f,   0.0f, 0.0f, 0.0f,   1.0f, 0.0f, 0.0f,   1.0f, 0.0f, 1.0f  // 下
	};
	/* @formatter:on */

	private DcmUtils du = new DcmUtils();
	private List<DicomObject> axialList;
	private int currIndex = 0;
	
	private int imgWidth;
	private int imgHeight;
	private int imgDepth;
	private float wc;
	private float ww;
	private int rescaleSlope;
	private int rescaleIntercept;
	private float sliceThickness;
	private float sliceSpacing;
	private float fovX, fovY, fovZ, fovMax;
	private short[] pixelData; // imgWidth * imgHeight
	private short[] allData; // imgWidth * imgHeight * depth
	
	private float texSliceLocation = 0f;
	private float texSliceThickness = 0.05f;

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
		
		texId = GlslUtils.genTexture(gl);

		// ------------------------------------------------

		loadDicomImageData();
		DicomObject dcmObj = axialList.get(0);
		imgWidth = du.getColumns(dcmObj);
		imgHeight = du.getRows(dcmObj);
		imgDepth = axialList.size();
		wc = du.getWC(dcmObj);
		ww = du.getWW(dcmObj);
		tmpWC = wc;
		tmpWW = ww;
		rescaleSlope = du.getRescaleSlope(dcmObj);
		rescaleIntercept = du.getRescaleIntercept(dcmObj);
		sliceThickness = du.getSliceThickness(dcmObj);
		sliceSpacing = du.getSpacingBetweenSlice(dcmObj);
	    fovX = (float)du.getFOV(dcmObj);
	    fovY = fovX;
	    fovZ = sliceSpacing * (imgDepth - 1);
	    fovMax = Math.max(Math.max(fovX, fovY), fovZ);
	    System.out.println("sliceThickness=" + sliceThickness);
	    System.out.println("sliceSpacing=" + sliceSpacing);
	    System.out.println("fovX=" + fovX + ", fovY=" + fovY + ", fovZ=" + fovZ + ", fovMax=" + fovMax);
		// --------------------------------------------
		
		long lo1 = System.currentTimeMillis();
		allData = new short[imgWidth * imgHeight * imgDepth];
		for(int i=0; i<imgDepth; i++)
		{
			short[] data = du.getPixels16(axialList.get(i));
			if (data.length != imgWidth * imgHeight)
			{
				throw new IllegalArgumentException("data length is error, expect: " + (imgWidth * imgHeight));
			}
			System.arraycopy(data, 0, allData, i * data.length, data.length);
		}
		long lo2 = System.currentTimeMillis();
		System.out.println("arraycopy, time=" + (lo2-lo1)/1000f + "s");
	}

	public void doDisplay(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		if (gl == this.gl) {
			// System.out.println("doDisplay, true");
		} else {
			System.out.println("doDisplay, false");
		}
		gl.glClearColor(0.2f, 0.3f, 0.3f, 1.0f);
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);

		loadVertex(gl, vao, vbo, vertices);

//		gl.glRotatef(xrot, 1.0f, 1.0f, 1.0f);
//		gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f);
//		gl.glRotatef(zrot, 0.0f, 0.0f, 1.0f);
		
		gl.glViewport(0, 0, width, height);
	}

	/**
	 * @param gl
	 */
	private void loadVertex(GL2 gl, int vao, int vbo, float[] vertices) {
		//////////////////////////// BEGIN TEXTURE/////////////////////////
		gl.glUseProgram(programId);
		
//		gl.glBindTexture(GL2.GL_TEXTURE_2D, texId);
//		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
//		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
//		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
//		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);

//		pixelData = du.getPixels16(axialList.get(currIndex));
//		gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_R16, imgWidth, imgHeight, 0, GL2.GL_RED, GL2.GL_UNSIGNED_SHORT,
//		        ShortBuffer.wrap(pixelData));
		
		 gl.glBindTexture(GL2.GL_TEXTURE_3D, texId);
         gl.glTexParameteri(GL2.GL_TEXTURE_3D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
         gl.glTexParameteri(GL2.GL_TEXTURE_3D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
         gl.glTexParameteri(GL2.GL_TEXTURE_3D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP_TO_EDGE);
         gl.glTexParameteri(GL2.GL_TEXTURE_3D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP_TO_EDGE);
         gl.glTexParameteri(GL2.GL_TEXTURE_3D, GL2.GL_TEXTURE_WRAP_R, GL2.GL_CLAMP_TO_EDGE);
         gl.glTexImage3D(GL2.GL_TEXTURE_3D, 0, GL2.GL_R16, imgWidth, imgHeight, imgDepth, 0, GL2.GL_RED, GL2.GL_UNSIGNED_SHORT,
                 ShortBuffer.wrap(allData));
         
		gl.glUniform1i(gl.glGetUniformLocation(programId, "tex3D"), 0);
		gl.glUniform1f(gl.glGetUniformLocation(programId, "wc"), tmpWC);
		gl.glUniform1f(gl.glGetUniformLocation(programId, "ww"), tmpWW);
		gl.glUniform1f(gl.glGetUniformLocation(programId, "rescaleSlope"), rescaleSlope);
		gl.glUniform1f(gl.glGetUniformLocation(programId, "rescaleIntercept"), rescaleIntercept);
		System.out.println("in loadVertex: wc=" + wc + ", ww=" + ww + ", " + rescaleSlope + ", " + rescaleIntercept);

		Vector3f target = new Vector3f();
		Vector3f camera = new Vector3f();
		Vector3f cameraUp = new Vector3f();
		camera.set(0.0f, 0.0f, 2.0f);
		cameraUp.set(0.0f, 1.0f, 0.0f);
		Matrix4f model = new Matrix4f().translate(-0.5f, -0.5f, -0.5f);
		Matrix4f mvp = new Matrix4f(model);
		Matrix4f view = new Matrix4f().lookAt(
				camera.x, camera.y, camera.z, 
				target.x, target.y, target.z, 
				cameraUp.x, cameraUp.y, cameraUp.z
				);
		Matrix4f projection = new Matrix4f().ortho(
				-1, 1, 
				-1, 1,
				-4.0f, 4.0f
				);

		mvp.mulLocal(view);
		mvp.mulLocal(projection);

		float[] mvpArray = new float[16];
		mvp.get(mvpArray);
		gl.glUniformMatrix4fv(gl.glGetUniformLocation(programId, "mvp"), 1, false, mvpArray, 0);
		
        gl.glUniform3f(gl.glGetUniformLocation(programId, "scale"), 1, 1, 1);
        Vector3f cameraDireciton = new Vector3f(camera).normalize();
        gl.glUniform3f(gl.glGetUniformLocation(programId, "cameraDirection"), cameraDireciton.x, cameraDireciton.y, cameraDireciton.z);
        gl.glUniform1f(gl.glGetUniformLocation(programId, "texSliceLocation"), texSliceLocation);
        gl.glUniform1f(gl.glGetUniformLocation(programId, "texSliceThickness"), texSliceThickness);
		
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
		gl.glVertexAttribPointer(0 /* the vertex attribute */, 
				3, /* size */
		        GL.GL_FLOAT, 
		        false /* normalized? */, 
		        0 /* stride */, 
		        0 /* The bound VBO data offset */
		);
		gl.glEnableVertexAttribArray(0); // layout (location = 0) ...

//		gl.glVertexAttribPointer(1 /* the vertex attribute */, 
//				2, /* size */
//		        GL.GL_FLOAT, 
//		        false /* normalized? */, 
//		        2 * 4 /* stride */, 
//		        12 * 4 /* The bound VBO data offset */
//		);
//		gl.glEnableVertexAttribArray(1); // layout (location = 1) ...
		/* @formatter:on */
		// /////// ENDDD attribute pointer 0 --> VBO 1 ///////////

		gl.glDrawArrays(GL2ES3.GL_QUADS, 0, 4*6);
	}

	// /////////////////////////////////////////////////////////////////////////////////////

	public static void main(String[] args) {
		GlslTest08 t = new GlslTest08();
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

		final JFrame frame = new JFrame("Glsl Test08");
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
		
//		MouseAdapter ml2 = new CustomMouseAdapter();
//		pnl.addMouseListener(ml2);
//		pnl.addMouseMotionListener(ml2);

		frame.getContentPane().add(pnl, BorderLayout.CENTER);
		frame.setSize(width, height);
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
			 
			 //-->
//			 RenderEvent.mCenterOffset.add(new Vector3f(mCameraPosition).normalize().mul(
//		                offset * mTexSliceIncrement * mTexIncrementCoef))
			 if (e.getUnitsToScroll() > 0)
			 {
				 texSliceLocation += texSliceThickness;
			 }
			 else
			 {
				 texSliceLocation -= texSliceThickness;
			 }
			 //<--
			 
			 
			 refresh();
		}

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