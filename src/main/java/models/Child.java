package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Child {
  @JsonProperty("Name")
  private String name;

  @JsonProperty("Age")
  private Integer age;

  @JsonProperty("Gender")
  private String gender;

  @JsonProperty("Salary")
  private Integer salary;

  @JsonProperty("Cars")
  private List<String> cars = null;
}
