package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PersonRecord {
  @JsonProperty("Firstname")
  private String firstname;

  @JsonProperty("Lastname")
  private String lastname;

  @JsonProperty("City")
  private String city;

  @JsonProperty("State")
  private String state;

  @JsonProperty("Children")
  private List<Child> children = null;
}
