package se.gigurra.gat.util

import scala.collection.mutable.ArrayBuffer

import com.jogamp.opengl.util.GLArrayDataServer
import com.jogamp.opengl.util.glsl.ShaderProgram
import com.jogamp.opengl.util.glsl.ShaderState

import javax.media.opengl.GL
import javax.media.opengl.GL2ES3

class Model(
  val gl: GL2ES3,
  val glStateMgr: ShaderState,
  val shaderProgram: ShaderProgram) {
  val vao = new VertexArrayObject(gl, true)
  val vbos = new ArrayBuffer[GLArrayDataServer]
  var nVertices = 0
  var isSealed = false

  protected def setVertexVbo(attributeName: String, vertices: Float*): Model = {
    nVertices = vertices.length / 3
    vbos += VboUtil.createVertexVbo(gl, attributeName, vertices: _*)
    this
  }

  protected def setColorVbo(attributeName: String, colors: Float*): Model = {
    vbos += VboUtil.createColorVbo(gl, attributeName, colors: _*)
    this
  }

  def seal(): Model = {
    if (!isSealed) {
      isSealed = true
      VboUtil.attach(gl, glStateMgr, vbos: _*)
      vao.disable()
    }
    this
  }

  def use(f: => Unit) {
    shaderProgram.useProgram(gl, true)
    vao.enable()
    f
    vao.disable()
  }

  def draw(primType: Int = GL.GL_TRIANGLES) {
    use {
      gl.glDrawArrays(primType, 0, nVertices)
    }
  }
  
  def dispose() {
    vao.dispose()
    for (vbo <- vbos) {
      vbo.destroy(gl)
    }
  }

}