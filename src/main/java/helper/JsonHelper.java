package helper;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class JsonHelper {
  public static <T> T readJsonFile(String fileName, Class<T> clazz) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    File file = getFile(fileName);
    return mapper.readValue(file, clazz);
  }

  private static File getFile(String fileName) throws IOException {
    ClassLoader classLoader = JsonHelper.class.getClassLoader();
    URL found =
        Optional.ofNullable(classLoader.getResource(fileName))
            .orElseThrow(() -> new IOException("File not found"));
    return new File(found.getFile());
  }
}
