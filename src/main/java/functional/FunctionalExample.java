package functional;

import helper.JsonHelper;
import models.Child;
import models.PersonRecord;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class FunctionalExample {

  //  Fetch list of names who stays in "Boston" (use of filter)
  public static List<String> getPersonNameWhoStaysInCity(
      PersonRecord[] personRecords, String city) {
    return Arrays.stream(personRecords)
        .filter(personRecord -> personRecord.getCity().equalsIgnoreCase(city))
        .map(personRecord -> personRecord.getFirstname() + " " + personRecord.getLastname())
        .toList();
  }

  // Fetch All Cars names (use of flatmap)
  public static List<String> getAllCars(PersonRecord[] personRecords) {
    return Arrays.stream(personRecords)
        .flatMap(personRecord -> personRecord.getChildren().stream())
        .flatMap(child -> Optional.ofNullable(child.getCars()).orElseGet(ArrayList::new).stream())
        .collect(Collectors.toList());
  }

  //  Fetch All cities from json (use of map)
  public static List<String> getCities(PersonRecord[] personRecords) {
    return Arrays.stream(personRecords).map(PersonRecord::getCity).toList();
  }

  // Fetch distinct Cars names (use of distinct)
  public static List<String> getDistinctCars(PersonRecord[] personRecords) {
    return getAllCars(personRecords).stream().distinct().toList();
  }

  //  Fetch Female child names
  public static List<String> getAllFemaleChildNames(PersonRecord[] personRecords) {
    return Arrays.stream(personRecords)
        .flatMap(personRecord -> personRecord.getChildren().stream())
        .filter(child -> child.getGender().equalsIgnoreCase("female"))
        .map(Child::getName)
        .toList();
  }

  //  Get List of parent name with their children's total salary
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

  //  Get first parent name whose children's total salary is greater than 2000
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

  //  Get children name who have specific car make
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

  //  Person with most cars
  public static String getPersonWithMostCars(PersonRecord[] personRecords) {
    Map<String, Integer> personRecordWithCars =
        Arrays.stream(personRecords)
            .collect(
                Collectors.toMap(
                    personRecord -> personRecord.getFirstname() + " " + personRecord.getLastname(),
                    personRecord ->
                        personRecord.getChildren().stream()
                            .map(
                                child ->
                                    Optional.ofNullable(child.getCars()).orElseGet(ArrayList::new))
                            .map(List::size)
                            .reduce(0, Integer::sum)));
    Optional<Map.Entry<String, Integer>> max =
        personRecordWithCars.entrySet().stream().max(Map.Entry.comparingByValue());
    return max.map(Map.Entry::getKey).orElse("");
  }

  public static void main(String[] args) throws IOException {

    var personRecords = JsonHelper.readJsonFile("PersonRecords.json", PersonRecord[].class);

    var personNameList = getPersonNameWhoStaysInCity(personRecords, "Boston");
    System.out.println(personNameList);

    var allCars = getAllCars(personRecords);
    System.out.println(allCars);

    var distinctCars = getDistinctCars(personRecords);
    System.out.println(distinctCars);

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

    var personWithMostCars = getPersonWithMostCars(personRecords);
    System.out.println(personWithMostCars);
  }
}
