package it.aizoon.owasp3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class DataService {

  private static final String BASE_DIRECTORY = "C:\\tmp\\";

  public void processFile(String userInput) throws IOException {

    File myFile = new File(BASE_DIRECTORY, userInput);
    System.out.println("Opening file: "+ myFile.getCanonicalPath());


//    if (!myFile.getCanonicalPath().startsWith(BASE_DIRECTORY)) {
//      throw new IllegalArgumentException("wrong path: " +  userInput);
//    }

    List<String> lines = Files.readAllLines(myFile.toPath());
    System.out.println(lines);
  }

  public static void main(String[] args) throws IOException {

    DataService dataService = new DataService();
    dataService.processFile("prova.txt");
    dataService.processFile("../prova.txt");

  }

}
