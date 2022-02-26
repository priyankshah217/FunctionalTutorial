package functional;

import helper.JsonHelper;
import models.Child;
import models.PersonRecord;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FunctionalExample {
  //  1. Fetch All cities from json
  public static List<String> getCities(PersonRecord[] personRecords) {
    return Arrays.stream(personRecords).map(PersonRecord::getCity).toList();
  }

  //  2. Fetch Female child names
  public static List<String> getAllFemaleChildNames(PersonRecord[] personRecords) {
    return Arrays.stream(personRecords)
        .flatMap(personRecord -> personRecord.getChildren().stream())
        .filter(child -> child.getGender().equalsIgnoreCase("female"))
        .map(Child::getName)
        .toList();
  }

  //  3. Get List of parent name with their children's total salary
  public static Map<String, Integer> getParentNamesWithTotalSalary(PersonRecord[] personRecords) {
    return Arrays.stream(personRecords)
        .collect(
            Collectors.toMap(
                PersonRecord::getFirstname,
                personRecord ->
                    personRecord.getChildren().stream()
                        .map(child -> Optional.ofNullable(child.getSalary()).orElse(0))
                        .reduce(0, Integer::sum)));
  }

  //  4. Get first parent name whose children's total salary is greater than 2000
  public static List<String> getFirstParentNameWithTotalSalaryGreaterThan(
      PersonRecord[] personRecords, int salary) {
    return Arrays.stream(personRecords)
        .collect(
            Collectors.filtering(
                personRecord ->
                    personRecord.getChildren().stream()
                            .map(child -> Optional.ofNullable(child.getSalary()).orElse(0))
                            .reduce(0, Integer::sum)
                        > salary,
                Collectors.mapping(PersonRecord::getFirstname, Collectors.toList())));
  }

  //  5. Get children name who have specific car make
  public static List<String> getChildrenNameWhoHaveSpecificCarMake(
      PersonRecord[] personRecords, String carMake) {
    return Arrays.stream(personRecords)
        .flatMap(personRecord -> personRecord.getChildren().stream())
        .collect(
            Collectors.filtering(
                child ->
                    Optional.ofNullable(child.getCars())
                        .orElseGet(ArrayList::new)
                        .contains(carMake),
                Collectors.mapping(Child::getName, Collectors.toList())));
  }

  public static void main(String[] args) throws IOException {
    var personRecords = JsonHelper.readJsonFile("PersonRecords.json", PersonRecord[].class);

    var cities = getCities(personRecords);
    System.out.println(cities);

    var femaleChildNames = getAllFemaleChildNames(personRecords);
    System.out.println(femaleChildNames);

    var namesWithTotalSalary = getParentNamesWithTotalSalary(personRecords);
    System.out.println(namesWithTotalSalary);

    var parentFirstNameWithSalaryMoreThan200 =
        getFirstParentNameWithTotalSalaryGreaterThan(personRecords, 200);
    System.out.println(parentFirstNameWithSalaryMoreThan200);

    var parentFirstNameWithSalaryMoreThan2000 =
        getFirstParentNameWithTotalSalaryGreaterThan(personRecords, 2000);
    System.out.println(parentFirstNameWithSalaryMoreThan2000);

    var childrenNameWhoHaveSpecificCarMake =
        getChildrenNameWhoHaveSpecificCarMake(personRecords, "BMW");
    System.out.println(childrenNameWhoHaveSpecificCarMake);
  }
}
