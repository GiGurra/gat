package se.gigurra.gat.util

import Edit.EditCls
import javax.media.opengl.GL2ES3

class VertexArrayObject(gl: GL2ES3, enableNow: Boolean) {

  val glId = Array(0).edit(gl.glGenVertexArrays(1, _, 0))(0)

  if (enableNow) enable()

  def enable() { gl.glBindVertexArray(glId) }
  def disable() { gl.glBindVertexArray(0) }
  def dispose() { gl.glDeleteVertexArrays(1, Array(glId), 0) }

}
