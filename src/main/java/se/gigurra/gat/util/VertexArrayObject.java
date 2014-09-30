package se.gigurra.gat.util;

import javax.media.opengl.GL2ES3;

public class VertexArrayObject {

	public VertexArrayObject(final GL2ES3 gl, final boolean enable) {
		int[] arr = new int[1];
		m_gl = gl;
		m_gl.glGenVertexArrays(1, arr, 0);
		m_object = arr[0];

		if (enable) {
			enable();
		}

	}

	public void enable() {
		m_gl.glBindVertexArray(m_object);
	}

	public void disable() {
		m_gl.glBindVertexArray(0);
	}

	public void dispose() {
		int[] arr = { m_object };
		m_gl.glDeleteVertexArrays(1, arr, 0);
	}

	public int object() {
		return m_object;
	}

	private int m_object;
	private GL2ES3 m_gl;
}
