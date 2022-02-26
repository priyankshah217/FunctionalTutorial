package imparative;

import helper.JsonHelper;
import models.Child;
import models.PersonRecord;

import java.io.IOException;
import java.util.*;

public class ImperativeExample {
  //  1. Fetch All cities from json
  public static List<String> getCities(PersonRecord[] personRecords) {
    List<String> cityList = new ArrayList<>();
    for (PersonRecord personRecord : personRecords) {
      cityList.add(personRecord.getCity());
    }
    return cityList;
  }

  //  2. Fetch Female child names
  public static List<String> getAllFemaleChildNames(PersonRecord[] personRecords) {
    List<String> femaleChildNameList = new ArrayList<>();
    for (PersonRecord personRecord : personRecords) {
      for (Child child : personRecord.getChildren()) {
        if (child.getGender().equalsIgnoreCase("female")) {
          femaleChildNameList.add(child.getName());
        }
      }
    }
    return femaleChildNameList;
  }

  //  3. Get List of parent name with their children's total salary
  public static Map<String, Integer> getParentNamesWithTotalSalary(PersonRecord[] personRecords) {
    Map<String, Integer> parentNameWithTotalSalary = new HashMap<>();
    int totalSalary;
    for (PersonRecord personRecord : personRecords) {
      totalSalary = 0;
      for (Child child : personRecord.getChildren()) {
        if (child.getSalary() != null) {
          totalSalary += child.getSalary();
        }
      }
      parentNameWithTotalSalary.put(personRecord.getFirstname(), totalSalary);
    }
    return parentNameWithTotalSalary;
  }

  //  4. Get first parent name whose children's total salary is greater than 2000
  public static List<String> getFirstParentNameWithTotalSalaryGreaterThan(
      PersonRecord[] personRecords, int salary) {
    List<String> parentNames = new ArrayList<>();
    int totalSalary;
    for (PersonRecord personRecord : personRecords) {
      totalSalary = 0;
      for (Child child : personRecord.getChildren()) {
        if (child.getSalary() != null) {
          totalSalary += child.getSalary();
        }
      }
      if (totalSalary > salary) {
        parentNames.add(personRecord.getFirstname());
      }
    }
    return parentNames;
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
  }
}
