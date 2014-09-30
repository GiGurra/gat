package se.gigurra.gat.util;

import javax.media.opengl.GL2ES2;

import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

public class ShaderUtil {

	public static ShaderCode buildShader(final GL2ES2 gl, final int shaderType, final String sourceCode) {
		ShaderCode vertexShader = new ShaderCode(shaderType, 1, new String[][] { { sourceCode } });
		vertexShader.compile(gl);
		return vertexShader;
	}

	public static ShaderCode buildVertexShader(final GL2ES2 gl, final String sourceCode) {
		return buildShader(gl, GL2ES2.GL_VERTEX_SHADER, sourceCode);
	}

	public static ShaderCode buildVertexShaderFromFile(final GL2ES2 gl, final String filePath) {
		return buildVertexShader(gl, FileUtil.file2String(filePath));
	}
	
	public static ShaderCode buildFragmentShader(final GL2ES2 gl, final String sourceCode) {
		return buildShader(gl, GL2ES2.GL_FRAGMENT_SHADER, sourceCode);
	}

	public static ShaderCode buildFragmentShaderFromFile(final GL2ES2 gl, final String filePath) {
		return buildFragmentShader(gl, FileUtil.file2String(filePath));
	}
	
	public static ShaderProgram buildProgram(final GL2ES2 gl, final ShaderCode... shaders) {

		final ShaderProgram shaderProgram = new ShaderProgram();
		for (final ShaderCode shader : shaders) {
			shaderProgram.add(shader);
		}

		if (!shaderProgram.link(gl, System.err)) {
			throw new RuntimeException("Compiling shader failed");
		}

		return shaderProgram;
	}

	public static ShaderProgram buildProgramFromFile(final GL2ES2 gl, final String vertexShaderFilePath, final String fragmentShaderFilePath) {
		 return buildProgram(gl, buildVertexShaderFromFile(gl, vertexShaderFilePath), buildFragmentShaderFromFile(gl, fragmentShaderFilePath));
	}
	
}
