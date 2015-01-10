package se.gigurra.gat.util;

import java.nio.FloatBuffer
import java.nio.IntBuffer

import com.jogamp.common.nio.Buffers

object BufferUtil {

  def createBuffer(floats: Array[Float]): FloatBuffer = {
    Buffers.newDirectFloatBuffer(floats);
  }

  def createBuffer(ints: Array[Int]): IntBuffer = {
    Buffers.newDirectIntBuffer(ints);
  }

}
