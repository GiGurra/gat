package se.gigurra.gat.util;

import javax.media.opengl.GL2ES2;

import com.jogamp.opengl.util.GLArrayDataServer;
import com.jogamp.opengl.util.glsl.ShaderState;

public class VboUtil {

	public static GLArrayDataServer createVertexVbo(final GL2ES2 gl, final String name, final float... coords) {
		final GLArrayDataServer vertexVbo = GLArrayDataServer.createGLSL(name, 3, GL2ES2.GL_FLOAT, false, 0, GL2ES2.GL_STATIC_DRAW);
		vertexVbo.put(BufferUtil.createBuffer(coords));
		vertexVbo.seal(gl, true);
		return vertexVbo;
	}

	public static GLArrayDataServer createVertexVbo(final GL2ES2 gl, final String name) {
		return GLArrayDataServer.createGLSL(name, 3, GL2ES2.GL_FLOAT, false, 0, GL2ES2.GL_DYNAMIC_DRAW);
	}

	public static GLArrayDataServer createColorVbo(final GL2ES2 gl, final String name, final float... colors) {
		final GLArrayDataServer vertexVbo = GLArrayDataServer.createGLSL(name, 4, GL2ES2.GL_FLOAT, false, 0, GL2ES2.GL_STATIC_DRAW);
		vertexVbo.put(BufferUtil.createBuffer(colors));
		vertexVbo.seal(gl, true);
		return vertexVbo;
	}

	public static GLArrayDataServer createColorVbo(final GL2ES2 gl, final String name) {
		return GLArrayDataServer.createGLSL(name, 4, GL2ES2.GL_FLOAT, false, 0, GL2ES2.GL_DYNAMIC_DRAW);
	}

	public static void attach(final GL2ES2 gl, final ShaderState shaderState, final GLArrayDataServer... vbos) {
		for (final GLArrayDataServer vbo : vbos) {
			vbo.bindBuffer(gl, true);
			shaderState.vertexAttribPointer(gl, vbo);
			vbo.bindBuffer(gl, false);
		}
	}

}
