package se.gigurra.gat

import com.jogamp.newt.opengl.GLWindow
import com.jogamp.opengl.util.Animator
import com.jogamp.opengl.util.PMVMatrix
import com.jogamp.opengl.util.glsl.ShaderProgram
import com.jogamp.opengl.util.glsl.ShaderState

import javax.media.opengl.GL
import javax.media.opengl.GLAutoDrawable
import javax.media.opengl.GLCapabilities
import javax.media.opengl.GLEventListener
import javax.media.opengl.GLProfile
import javax.media.opengl.GLUniformData
import javax.media.opengl.fixedfunc.GLMatrixFunc
import se.gigurra.gat.util.Model
import se.gigurra.gat.util.ShaderUtil

object Gat {

  def main(args: Array[String]) {
    val caps = new GLCapabilities(GLProfile.get(GLProfile.GL2ES2))
    caps.setBackgroundOpaque(false)

    val glWindow = GLWindow.create(caps)
    glWindow.setTitle("Raw GL2ES2 Demo")
    glWindow.setSize(1280, 720)
    glWindow.setUndecorated(false)
    glWindow.setPointerVisible(true)
    glWindow.setVisible(true)
    glWindow.addGLEventListener(new Gat)

    val animator = new Animator()
    animator.add(glWindow)
    animator.start()
  }

}

class Gat extends GLEventListener {

  val transform = new PMVMatrix()
  val glStateMgr = new ShaderState()
  var shaderProgram: ShaderProgram = null

  // For bouncy triangle
  var t0: Double = System.currentTimeMillis()
  var theta: Double = 0.0
  var s: Double = 0.0

  var transformationUniform: GLUniformData = null
  var model: Model = null

  override def init(drawable: GLAutoDrawable) {

    val gl = drawable.getGL().getGL2ES3()

    shaderProgram = ShaderUtil.buildProgramFromFile(gl, "shaders/vertexShader.c", "shaders/fragmentShader.c")
    glStateMgr.attachShaderProgram(gl, shaderProgram, true)

    transformationUniform = new GLUniformData("uniform_Transformation", 4, 4, transform.glGetMvMatrixf())

    model = new Model(gl, glStateMgr, shaderProgram) {
      setVertexVbo("attribute_Position", 0.0f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f)
      setColorVbo("attribute_Color", 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.9f)
    }.seal()

  }

  override def reshape(drawable: GLAutoDrawable, x: Int, y: Int, w: Int, h: Int) {
    drawable.getGL().glViewport((w - h) / 2, 0, h, h)
  }

  override def display(drawable: GLAutoDrawable) {

    val t1: Double = System.currentTimeMillis()
    theta += (t1 - t0) * 0.005f
    t0 = t1
    s = Math.sin(theta)
    transform.glLoadIdentity()
    transform.glRotatef(30f * s.toFloat, 1.0f, 0.0f, 1.0f)

    val gl = drawable.getGL().getGL2ES2()

    // Clear screen
    gl.glClearColor(1, 0, 1, 0.5f) // Purple
    gl.glClear(GL.GL_STENCIL_BUFFER_BIT | GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT)

    glStateMgr.uniform(gl, transformationUniform)

    model.draw()

  }

  override def dispose(drawable: GLAutoDrawable) {
    System.exit(0)
  }

}