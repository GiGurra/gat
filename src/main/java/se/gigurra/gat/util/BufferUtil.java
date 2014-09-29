package se.gigurra.gat.util;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;

public class BufferUtil {

	public static FloatBuffer createBuffer(final float... floats) {
		return Buffers.newDirectFloatBuffer(floats);
	}

	public static IntBuffer createBuffer(final int... ints) {
		return Buffers.newDirectIntBuffer(ints);
	}

}
