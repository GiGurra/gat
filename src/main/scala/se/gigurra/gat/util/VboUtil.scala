package se.gigurra.gat.util

import com.jogamp.opengl.util.GLArrayDataServer
import com.jogamp.opengl.util.glsl.ShaderState

import Edit.EditCls
import javax.media.opengl.GL
import javax.media.opengl.GL2ES2
import javax.media.opengl.GL2ES3

object VboUtil {

  def createVertexVbo(gl: GL2ES2, name: String, coords: Array[Float]): GLArrayDataServer = {
    GLArrayDataServer.createGLSL(name, 3, GL.GL_FLOAT, false, 0, GL.GL_STATIC_DRAW).edit { vbo =>
      vbo.put(BufferUtil.createBuffer(coords))
      vbo.seal(gl, true)
    }
  }

  def createVertexVbo(gl: GL2ES2, name: String): GLArrayDataServer = {
    GLArrayDataServer.createGLSL(name, 3, GL.GL_FLOAT, false, 0, GL.GL_DYNAMIC_DRAW)
  }

  def createColorVbo(gl: GL2ES2, name: String, colors: Array[Float]): GLArrayDataServer = {
    GLArrayDataServer.createGLSL(name, 4, GL.GL_FLOAT, false, 0, GL.GL_STATIC_DRAW).edit { vbo =>
      vbo.put(BufferUtil.createBuffer(colors))
      vbo.seal(gl, true)
    }
  }

  def createColorVbo(gl: GL2ES2, name: String): GLArrayDataServer = {
    GLArrayDataServer.createGLSL(name, 4, GL.GL_FLOAT, false, 0, GL.GL_DYNAMIC_DRAW)
  }

  def attach(gl: GL2ES2, shaderState: ShaderState, vbos: Seq[GLArrayDataServer]) {
    for (vbo <- vbos) {
      vbo.bindBuffer(gl, true)
      shaderState.enableVertexAttribArray(gl, vbo)
      shaderState.vertexAttribPointer(gl, vbo)
      vbo.bindBuffer(gl, false)
    }
  }

  def createVAO(gl: GL2ES3, enable: Boolean): VertexArrayObject = {
    new VertexArrayObject(gl, enable)
  }

}
