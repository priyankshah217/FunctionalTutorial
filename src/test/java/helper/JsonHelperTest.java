package helper;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIOException;

class JsonHelperTest {

  @Test
  void readValidJsonFile() throws IOException {
    TestRecord[] testRecord = JsonHelper.readJsonFile("test.json", TestRecord[].class);
    assertThat(testRecord).isNotNull();
    assertThat(testRecord[0].getColor()).isEqualTo("red");
  }

  @Test
  void shouldThrowExceptionIfJsonNotExists() {
    assertThatIOException()
        .isThrownBy(() -> JsonHelper.readJsonFile("invalid.json", TestRecord[].class))
        .withMessageContaining("File not found");
  }
}
