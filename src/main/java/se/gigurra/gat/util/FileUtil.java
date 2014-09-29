package se.gigurra.gat.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
	public static String file2String(final String path) {
		try {
			return new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
		} catch (final IOException error) {
			throw new RuntimeException(error);
		}
	}
}
