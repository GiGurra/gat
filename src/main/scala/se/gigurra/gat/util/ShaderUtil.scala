package se.gigurra.gat.util

import com.jogamp.opengl.util.glsl.ShaderCode
import com.jogamp.opengl.util.glsl.ShaderProgram

import Edit.EditCls
import javax.media.opengl.GL2ES2

object ShaderUtil {

  def buildShader(gl: GL2ES2, shaderType: Int, sourceCode: String): ShaderCode = {
    new ShaderCode(shaderType, 1, Array(Array(sourceCode))).edit(_.compile(gl))
  }

  def buildVertexShader(gl: GL2ES2, sourceCode: String): ShaderCode = {
    buildShader(gl, GL2ES2.GL_VERTEX_SHADER, sourceCode)
  }

  def buildVertexShaderFromFile(gl: GL2ES2, filePath: String): ShaderCode = {
    buildVertexShader(gl, FileUtil.file2String(filePath))
  }

  def buildFragmentShader(gl: GL2ES2, sourceCode: String): ShaderCode = {
    buildShader(gl, GL2ES2.GL_FRAGMENT_SHADER, sourceCode)
  }

  def buildFragmentShaderFromFile(gl: GL2ES2, filePath: String): ShaderCode = {
    buildFragmentShader(gl, FileUtil.file2String(filePath))
  }

  def buildProgram(gl: GL2ES2, shaders: ShaderCode*): ShaderProgram = {

    val shaderProgram = new ShaderProgram()
    for (shader <- shaders)
      shaderProgram.add(shader)

    if (!shaderProgram.link(gl, System.err))
      throw new RuntimeException("Compiling shader failed")

    shaderProgram
  }

  def buildProgramFromFile(
    gl: GL2ES2,
    vertexShaderFilePath: String,
    fragmentShaderFilePath: String): ShaderProgram = {
    buildProgram(
      gl,
      buildVertexShaderFromFile(gl, vertexShaderFilePath),
      buildFragmentShaderFromFile(gl, fragmentShaderFilePath))
  }

}
