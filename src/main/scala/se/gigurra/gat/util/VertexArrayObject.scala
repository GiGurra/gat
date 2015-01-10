package se.gigurra.gat.util

import javax.media.opengl.GL2ES3

class VertexArrayObject(gl: GL2ES3, enableNow: Boolean) {

  val glId = Some(Array(0)).map { arr => gl.glGenVertexArrays(1, arr, 0); arr(0) }.get

  if (enableNow) enable()

  def enable() { gl.glBindVertexArray(glId) }
  def disable() { gl.glBindVertexArray(0) }
  def dispose() { gl.glDeleteVertexArrays(1, Array(glId), 0) }

}
