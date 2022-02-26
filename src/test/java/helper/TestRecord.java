package helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TestRecord {
    @JsonProperty("color")
    private String color;
    @JsonProperty("value")
    private String value;
}