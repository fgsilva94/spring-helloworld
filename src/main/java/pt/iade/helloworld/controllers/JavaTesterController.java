package pt.iade.helloworld.controllers;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.iade.helloworld.models.CurricularUnit;

@RestController
@RequestMapping(path = "/api/java/tester")
public class JavaTesterController {
  private double grades[] = { 18, 14, 12, 10, 20, 16 };
  private String ucs[] = { "BD", "CC", "IT1", "MD", "POO", "SO" };
  private ArrayList<CurricularUnit> units = new ArrayList<CurricularUnit>();

  private Logger logger = LoggerFactory.getLogger(JavaTesterController.class);

  @GetMapping(path = "/author", produces = MediaType.APPLICATION_JSON_VALUE)
  public String getAuthor() {
    String name = "Felipe Silva";
    int number = 20190795;
    double height = 1.71;
    boolean footballFan = false;
    char footballColor = ' ';
    String otherInfo = "love lasagna";

    StringBuilder sb = new StringBuilder();
    sb.append("Done by " + name + " with number " + number + ". \n");
    sb.append("I am " + height + " tall and ");

    if (footballFan) {
      sb.append("I am a fan of football.\n");
      if (Character.toLowerCase(footballColor) == 'r') {
        sb.append("My favorite club is Benfica ");
      } else if (Character.toLowerCase(footballColor) == 'b') {
        sb.append("My favorite club is Porto ");
      } else if (Character.toLowerCase(footballColor) == 'g') {
        sb.append("My favorite club is Sporting ");
      }
    } else {
      sb.append("I am not a fan of football.\n");
    }

    if (!otherInfo.isEmpty()) {
      sb.append("and I " + otherInfo + ".");
    }

    logger.info("GetAuthor Done!");
    return sb.toString();
  }

  @GetMapping(path = "/access/{student}/{covid}", produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean getGreeting(@PathVariable("student") boolean isStudent, @PathVariable("covid") boolean hasCovid) {
    logger.info("GetGreeting Done!");
    return isStudent && !hasCovid;
  }

  @GetMapping(path = "/required/{student}/{temperature}/{classType}", produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean getRequired(@PathVariable("student") boolean isStudent, @PathVariable("temperature") double hasCovid,
      @PathVariable("classType") String type) {
    logger.info("GetRequired Done!");
    return isStudent && type.equals("presential") && hasCovid >= 34.5 && hasCovid <= 37.5;
  }

  @GetMapping(path = "/evacuation/{fire}/{numberOfCovids}/{powerShutdown}/{comeBackTime}", produces = MediaType.APPLICATION_JSON_VALUE)
  public boolean evacuation(@PathVariable("fire") boolean fire, @PathVariable("numberOfCovids") int numberOfCovids,
      @PathVariable("powerShutdown") boolean powerShutdown, @PathVariable("comeBackTime") double comeBackTime) {
    logger.info("Evacuation Done!");
    return fire || numberOfCovids > 5 || (powerShutdown && comeBackTime > 15);
  }

  @GetMapping(path = "/grades/average", produces = MediaType.APPLICATION_JSON_VALUE)
  public double average() {
    double sum = 0;

    for (int i = 0; i < grades.length; i++) {
      sum += grades[i];
    }

    logger.info("Average Done!");
    return sum / grades.length;
  }

  @GetMapping(path = "/grades/maximum", produces = MediaType.APPLICATION_JSON_VALUE)
  public double maxGrade() {
    double max = 0;

    for (int i = 0; i < grades.length; i++) {
      if (grades[i] > max) {
        max = grades[i];
      }
    }

    logger.info("MaxGrade Done!");
    return max;
  }

  @GetMapping(path = "/grades/single/{ucName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public double singleGrade(@PathVariable("ucName") String ucName) {
    int index = 0;

    for (int i = 0; i < ucs.length; i++) {
      if (ucs[i].toLowerCase().equals(ucName.toLowerCase())) {
        index = i;
      }
    }

    logger.info("SingleGrade Done!");
    return grades[index];
  }

  @GetMapping(path = "/grades/range/{min}/{max}", produces = MediaType.APPLICATION_JSON_VALUE)
  public int gradeRange(@PathVariable("min") double min, @PathVariable("max") double max) {
    int count = 0;

    for (int i = 0; i < grades.length; i++) {
      if (grades[i] >= min && grades[i] <= max) {
        count++;
      }
    }

    logger.info("GradeRange Done!");
    return count;
  }

  @GetMapping(path = "/grades/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public String allUcs() {

    StringBuilder sb = new StringBuilder("Your grades are:\n");
    for (int i = 0; i < grades.length; i++) {
      sb.append(ucs[i] + " = " + grades[i] + "\n");
    }

    logger.info("AllUcs Done!");
    return sb.toString();
  }

  @PostMapping(path = "/units/")
  public CurricularUnit saveUnit(@RequestBody CurricularUnit unit) {
    logger.info("Added unit " + unit.getName());
    units.add(unit);
    return unit;
  }

  @GetMapping(path = "/units", produces = MediaType.APPLICATION_JSON_VALUE)
  public ArrayList<CurricularUnit> getUnits() {
    logger.info("Get " + units.size() + " Units");
    return units;
  }

  @GetMapping(path = "/units/average", produces = MediaType.APPLICATION_JSON_VALUE)
  public double getUnitsAverage() {
    double nota = 0;
    int ects = 0;

    for (CurricularUnit unit : units) {
      ects += unit.getEcts();
      nota += unit.getGrade() * unit.getEcts();
    }

    return nota / ects;
  }

  @GetMapping(path = "/units/maximum", produces = MediaType.APPLICATION_JSON_VALUE)
  public double getUnitsMaxGrade() {
    double max = 0;

    for (CurricularUnit unit : units) {
      if (unit.getGrade() > max) {
        max = unit.getGrade();
      }
    }

    logger.info("Units MaxGrade Done!");
    return max;
  }

  @GetMapping(path = "/units/single/{ucName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public double getUnitsSingleGrade(@PathVariable("ucName") String ucName) {
    double tempGrade = 0;

    for (CurricularUnit unit : units) {
      logger.info(unit.getName().toLowerCase() + " " + ucName.toLowerCase() + " " + tempGrade);
      if (unit.getName().toLowerCase().equals(ucName.toLowerCase())) {
        tempGrade = unit.getGrade();
      }
    }

    logger.info("Units SingleGrade Done!");
    return tempGrade;
  }

  @GetMapping(path = "/units/semester/{semesterNum}", produces = MediaType.APPLICATION_JSON_VALUE)
  public String allUcs(@PathVariable("semesterNum") int semesterNum) {
    StringBuilder sb = new StringBuilder();

    for (CurricularUnit unit : units) {
      if (unit.getSemester() == semesterNum) {
        sb.append(unit.getName() + "\n");
      }
    }

    logger.info("Units SemesterUcs Done!");
    return sb.toString();
  }

  @GetMapping(path = "/units/range/{min}/{max}", produces = MediaType.APPLICATION_JSON_VALUE)
  public int getUnitsGradeRange(@PathVariable("min") double min, @PathVariable("max") double max) {
    int count = 0;

    for (CurricularUnit unit : units) {
      if (unit.getGrade() >= min && unit.getGrade() <= max) {
        count++;
      }
    }

    logger.info("Units GradeRange Done!");
    return count;
  }
}
