package se.gigurra.gat.util;

import java.nio.file.Files
import java.nio.file.Paths

object FileUtil {
  def file2String(path: String): String = {
    new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
  }
}
