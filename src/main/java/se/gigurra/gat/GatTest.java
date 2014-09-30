package se.gigurra.gat;

import javax.media.opengl.GL2ES2;
import javax.media.opengl.GL2ES3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLUniformData;

import se.gigurra.gat.util.ShaderUtil;
import se.gigurra.gat.util.VboUtil;
import se.gigurra.gat.util.VertexArrayObject;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.GLArrayDataServer;
import com.jogamp.opengl.util.PMVMatrix;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import com.jogamp.opengl.util.glsl.ShaderState;

public class GatTest implements GLEventListener {

	final PMVMatrix transform = new PMVMatrix();
	final ShaderState glStateMgr = new ShaderState();
	ShaderProgram shaderProgram;

	// For bouncy triangle
	private double t0 = System.currentTimeMillis();
	private double theta;
	private double s;

	GLUniformData transformationUniform;
	GLArrayDataServer vertexVbo;
	GLArrayDataServer colorVbo;
	VertexArrayObject vao;

	public static void main(String[] s) {
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2ES2));
		caps.setBackgroundOpaque(false);

		GLWindow glWindow = GLWindow.create(caps);
		glWindow.setTitle("Raw GL2ES2 Demo");
		glWindow.setSize(1280, 720);
		glWindow.setUndecorated(false);
		glWindow.setPointerVisible(true);
		glWindow.setVisible(true);
		glWindow.addGLEventListener(new GatTest());

		Animator animator = new Animator();
		animator.add(glWindow);
		animator.start();
	}

	public void init(GLAutoDrawable drawable) {

		GL2ES3 gl = drawable.getGL().getGL2ES3();

		shaderProgram = ShaderUtil.buildProgramFromFile(gl, "shaders/vertexShader.c", "shaders/fragmentShader.c");
		glStateMgr.attachShaderProgram(gl, shaderProgram, true);

		transformationUniform = new GLUniformData("uniform_Transformation", 4, 4, transform.glGetMvMatrixf());

		vao = VboUtil.createVAO(gl, true);
		vertexVbo = VboUtil.createVertexVbo(gl, "attribute_Position", 0.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f);
		colorVbo = VboUtil.createColorVbo(gl, "attribute_Color", 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.9f);
		VboUtil.attach(gl, glStateMgr, vertexVbo, colorVbo);
		vao.disable();

	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
		GL2ES2 gl = drawable.getGL().getGL2ES2();
		gl.glViewport((w - h) / 2, 0, h, h);
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
		transform.glMatrixMode(PMVMatrix.GL_MODELVIEW);
		transform.glLoadIdentity();
		transform.glRotatef(30f * (float) s, 1.0f, 0.0f, 1.0f);

		glStateMgr.uniform(gl, transformationUniform);

		vao.enable();
		gl.glDrawArrays(GL2ES2.GL_TRIANGLES, 0, 3);
		vao.disable();

	}

	public void dispose(GLAutoDrawable drawable) {
		System.exit(0);
	}

}
