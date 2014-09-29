package se.gigurra.gat.util;

import javax.media.opengl.GL2ES2;

import com.jogamp.opengl.util.glsl.ShaderCode;

public class ShaderUtil {

	public static ShaderCode buildShader(final GL2ES2 gl, final int shaderType, final String sourceCode) {
		ShaderCode vertexShader = new ShaderCode(shaderType, 1, new String[][] { { sourceCode } });
		vertexShader.compile(gl);
		return vertexShader;
	}

	public static ShaderCode buildVertexShader(final GL2ES2 gl, final String sourceCode) {
		return buildShader(gl, GL2ES2.GL_VERTEX_SHADER, sourceCode);
	}

	public static ShaderCode buildFragmentShader(final GL2ES2 gl, final String sourceCode) {
		return buildShader(gl, GL2ES2.GL_FRAGMENT_SHADER, sourceCode);
	}

}
