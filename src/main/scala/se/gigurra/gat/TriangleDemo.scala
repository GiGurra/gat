package se.gigurra.gat

import com.jogamp.opengl.util.PMVMatrix
import com.jogamp.opengl.util.glsl.ShaderState

import javax.media.opengl.GL
import javax.media.opengl.GL2ES3
import javax.media.opengl.GLAutoDrawable
import javax.media.opengl.GLUniformData
import se.gigurra.gat.util.Model
import se.gigurra.gat.util.ShaderUtil

class TriangleDemo(gl: GL2ES3) {

  // For bouncy triangle
  var t0: Double = System.currentTimeMillis()
  var theta: Double = 0.0
  var s: Double = 0.0

  val transform = new PMVMatrix()
  val glStateMgr = new ShaderState()
  val shaderProgram = ShaderUtil.buildProgramFromFile(gl, "shaders/vertexShader.c", "shaders/fragmentShader.c")
  val transformationUniform = new GLUniformData("uniform_Transformation", 4, 4, transform.glGetMvMatrixf())

  glStateMgr.attachShaderProgram(gl, shaderProgram, true)

  val model = new Model(gl, glStateMgr, shaderProgram) {
    setVertexVbo("attribute_Position", Array(0.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f))
    setColorVbo("attribute_Color", Array(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.9f))
  }.seal()

  def reshape(drawable: GLAutoDrawable, x: Int, y: Int, w: Int, h: Int) {
    drawable.getGL().glViewport((w - h) / 2, 0, h, h)
  }

  def display(drawable: GLAutoDrawable) {

    val t1: Double = System.currentTimeMillis()
    theta += (t1 - t0) * 0.005f
    t0 = t1
    s = Math.sin(theta)
    transform.glLoadIdentity()
    transform.glRotatef(30f * s.toFloat, 1.0f, 0.0f, 1.0f)

    gl.glClearColor(1, 0, 1, 0.5f)
    gl.glClear(GL.GL_STENCIL_BUFFER_BIT | GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT)

    glStateMgr.uniform(gl, transformationUniform)

    model.draw()

  }

  def dispose(drawable: GLAutoDrawable) {
  }

}