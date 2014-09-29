package se.gigurra.gat;

import javax.media.opengl.GL2ES2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLUniformData;

import se.gigurra.gat.util.FileUtil;
import se.gigurra.gat.util.ShaderUtil;
import se.gigurra.gat.util.VboUtil;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.GLArrayDataServer;
import com.jogamp.opengl.util.PMVMatrix;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import com.jogamp.opengl.util.glsl.ShaderState;

public class GatTest implements GLEventListener {

	static final int COLOR_IDX = 0;
	static final int VERTICES_IDX = 1;
	static int width = 1280;
	static int height = 720;

	final String vertexShaderCode = FileUtil.file2String("shaders/vertexShader.c");
	final String fragmentShaderCode = FileUtil.file2String("shaders/fragmentShader.c");
	final ShaderState shaderState = new ShaderState();
	final ShaderProgram shaderProgram = new ShaderProgram();

	// For bouncy triangle
	private double t0 = System.currentTimeMillis();
	private double theta;
	private double s;

	GLArrayDataServer vertexVbo;
	GLArrayDataServer colorVbo;
	GLUniformData transformationUniform;

	final PMVMatrix transformation = new PMVMatrix();

	public static void main(String[] s) {
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2ES2));
		caps.setBackgroundOpaque(false);

		GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("Raw GL2ES2 Demo");
		glWindow.setSize(width, height);
		glWindow.setUndecorated(false);
		glWindow.setPointerVisible(true);
		glWindow.setVisible(true);
		glWindow.addGLEventListener(new GatTest());

		Animator animator = new Animator();
		animator.add(glWindow);
		animator.start();
	}

	public void init(GLAutoDrawable drawable) {

		GL2ES2 gl = drawable.getGL().getGL2ES2();

		shaderProgram.add(ShaderUtil.buildVertexShader(gl, vertexShaderCode));
		shaderProgram.add(ShaderUtil.buildFragmentShader(gl, fragmentShaderCode));
		shaderProgram.link(gl, null);
		
		shaderState.attachShaderProgram(gl, shaderProgram, true);
		transformationUniform = new GLUniformData("uniform_Transformation", 4, 4, transformation.glGetMvMatrixf());
		vertexVbo = VboUtil.createVertexVbo(gl, "attribute_Position", 0.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f);
		colorVbo = VboUtil.createColorVbo(gl, "attribute_Color", 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.9f);
		VboUtil.attach(gl, shaderState, vertexVbo, colorVbo);

	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int z, int h) {
		width = z;
		height = h;
		GL2ES2 gl = drawable.getGL().getGL2ES2();
		gl.glViewport((width - height) / 2, 0, height, height);
	}

	public void display(GLAutoDrawable drawable) {
		// Update variables used in animation
		double t1 = System.currentTimeMillis();
		theta += (t1 - t0) * 0.005f;
		t0 = t1;
		s = Math.sin(theta);

		// Get gl
		GL2ES2 gl = drawable.getGL().getGL2ES2();

		// Clear screen
		gl.glClearColor(1, 0, 1, 0.5f); // Purple
		gl.glClear(GL2ES2.GL_STENCIL_BUFFER_BIT | GL2ES2.GL_COLOR_BUFFER_BIT | GL2ES2.GL_DEPTH_BUFFER_BIT);

		// Transformation matrix
		transformation.glMatrixMode(PMVMatrix.GL_MODELVIEW);
		transformation.glLoadIdentity();
		transformation.glRotatef(30f * (float) s, 1.0f, 0.0f, 1.0f);
		
		shaderState.uniform(gl, transformationUniform);
		shaderState.enableVertexAttribArray(gl, vertexVbo);
		shaderState.enableVertexAttribArray(gl, colorVbo);
		gl.glDrawArrays(GL2ES2.GL_TRIANGLES, 0, 3);
		shaderState.disableVertexAttribArray(gl, colorVbo);
		shaderState.disableVertexAttribArray(gl, vertexVbo);
	}

	public void dispose(GLAutoDrawable drawable) {
		System.exit(0);
	}

}
