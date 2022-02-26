package helper;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class JsonHelperTest {

    @Test
    void readJsonFile() throws IOException {
        TestRecord[] testRecord = JsonHelper.readJsonFile("test.json", TestRecord[].class);
        assertThat(testRecord).isNotNull();
        assertThat(testRecord[0].getColor()).isEqualTo("red");
    }
}