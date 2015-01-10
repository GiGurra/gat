package se.gigurra.gat

import com.jogamp.newt.opengl.GLWindow
import com.jogamp.opengl.util.Animator

import javax.media.opengl.GLAutoDrawable
import javax.media.opengl.GLCapabilities
import javax.media.opengl.GLEventListener
import javax.media.opengl.GLProfile

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

  var demo: TriangleDemo = null

  override def init(drawable: GLAutoDrawable) {
    demo = new TriangleDemo(drawable.getGL().getGL2ES3())
  }

  override def reshape(drawable: GLAutoDrawable, x: Int, y: Int, w: Int, h: Int) {
    demo.reshape(drawable, x, y, w, h)
  }

  override def display(drawable: GLAutoDrawable) {
    demo.display(drawable)
  }

  override def dispose(drawable: GLAutoDrawable) {
    drawable.getAnimator.stop()
  }

}