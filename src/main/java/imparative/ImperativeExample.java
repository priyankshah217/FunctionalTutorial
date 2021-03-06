package imparative;

import common.SampleObject;
import helper.JsonHelper;
import models.Child;
import models.PersonRecord;

import java.io.IOException;
import java.util.*;

public class ImperativeExample {

  private SampleObject sampleObject;

  //  Fetch list of names who stays in "Boston"
  public static List<String> getPersonNameWhoStaysInCity(
      PersonRecord[] personRecords, String city) {
    List<String> personNameList = new ArrayList<>();
    for (PersonRecord personRecord : personRecords) {
      if (personRecord.getCity().equalsIgnoreCase(city)) {
        personNameList.add(personRecord.getFirstname() + " " + personRecord.getLastname());
      }
    }
    return personNameList;
  }

  // Fetch All Cars names
  public static List<String> getAllCars(PersonRecord[] personRecords) {
    List<String> carNames = new ArrayList<>();
    for (PersonRecord personRecord : personRecords) {
      for (Child child : personRecord.getChildren()) {
        if (child.getCars() != null) {
          carNames.addAll(child.getCars());
        }
      }
    }
    return carNames;
  }

  // Fetch distinct Cars names
  public static List<String> getDistinctCars(PersonRecord[] personRecords) {
    List<String> allCars = getAllCars(personRecords);
    return new ArrayList<>(new HashSet<>(allCars));
  }

  //  Fetch All cities from json
  public static List<String> getCities(PersonRecord[] personRecords) {
    List<String> cityList = new ArrayList<>();
    for (PersonRecord personRecord : personRecords) {
      cityList.add(personRecord.getCity());
    }
    return cityList;
  }

  //  Fetch Female child names
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

  //  Get List of parent name with their children's total salary
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

  //  Get first parent name whose children's total salary is greater than 2000
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

  //  Get children name who have specific car make
  public static List<String> getChildrenNameWhoHaveSpecificCarMake(
      PersonRecord[] personRecords, String carMake) {
    List<String> childrenNames = new ArrayList<>();
    for (PersonRecord personRecord : personRecords) {
      for (Child child : personRecord.getChildren()) {
        if (child.getCars() != null && child.getCars().contains(carMake)) {
          childrenNames.add(child.getName());
        }
      }
    }
    return childrenNames;
  }

  //  Person with most cars
  public static String getPersonWithMostCars(PersonRecord[] personRecords) {
    Map<String, Integer> personWithCars = new HashMap<>();
    for (PersonRecord personRecord : personRecords) {
      int noOfCars = 0;
      for (Child child : personRecord.getChildren()) {
        if (child.getCars() != null) {
          noOfCars += child.getCars().size();
        }
      }
      personWithCars.put(personRecord.getFirstname() + " " + personRecord.getLastname(), noOfCars);
    }
    LinkedList<Map.Entry<String, Integer>> entries = new LinkedList<>(personWithCars.entrySet());
    entries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());
    return entries.get(0).getKey();
  }

  private static boolean checkNoIsOdd(int no) throws Exception {
    if (no == 0) {
      throw new Exception("No cannot be zero");
    }
    return no % 2 != 0;
  }

  //  Convert checked exception to unchecked exception (handling checked exception in lambda)
  private static Integer getSumOfOddNos(List<Integer> integerList) throws Exception {
    int sum = 0;
    for (Integer integer : integerList) {
      if (!checkNoIsOdd(integer)) {
        sum += integer;
      }
    }
    return sum;
  }

  // Handling null checks
  public SampleObject getSampleObject() {
    if (sampleObject != null) {
      return sampleObject;
    }
    return new SampleObject("New Object");
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

    List<Integer> integerList = List.of(1, 3, 5, 7, 9, 0, 2, 4, 6, 8, 10);
    try {
      Integer sumOfOddNos = getSumOfOddNos(integerList);
      System.out.println(sumOfOddNos);
    } catch (Exception e) {
      System.out.println(e.getClass().getSimpleName());
    }

    var imperativeExample = new ImperativeExample();
    var sampleObject = imperativeExample.getSampleObject();
    System.out.println(sampleObject.getName());
    sampleObject = new SampleObject("Overridden Object");
    System.out.println(sampleObject.getName());
  }
}
