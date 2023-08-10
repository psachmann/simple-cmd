package cmd.commands.find;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import cmd.SimpleCmd;
import cmd.commands.AbstractCommandTest;
import picocli.CommandLine;

public class FindCommandTest extends AbstractCommandTest {

  /**
   * Testet den "find" Befehl mit Einschränkung auf txt Dateien.
   */
  @Test
  void testFindWithTxt(@TempDir Path tempDir) throws IOException {
    //given
    prepareTestFiles(tempDir);
    SimpleCmd.setCurrentLocation(tempDir.toFile());
    String[] args = {"txt"};
    FindCommand findCommand = CommandLine.populateCommand(new FindCommand(),args);
    int amountFiles = 10;
    //when
    findCommand.run();
    //then
    String actualString = getOutputStream().toString();
    boolean expected = true;
    for(int i = 0; i < amountFiles ; i++){
      expected = expected && actualString.contains("myFile"+ i + ".txt");
    }
    assertTrue(expected);
  }

  /**
   * Testet den "find" Befehl mit Einschränkung auf txt Dateien, falls keine existieren.
   */
  @Test
  void testFindErrorIfNoFilesFound(@TempDir Path tempDir) throws IOException{
    //given
    SimpleCmd.setCurrentLocation(tempDir.toFile());
    String[] args = {"txt"};
    FindCommand findCommand = CommandLine.populateCommand(new FindCommand(),args);
    int amountFiles = 10;
    //when
    findCommand.run();
    //then
    String actualString = getOutputStream().toString();

    boolean expected = true;
    for(int i = 0; i < amountFiles ; i++){
      expected = expected && actualString.contains("myFile"+ i + ".txt");
    }
    assertFalse(expected);
  }

  @Test
  void testCorrectFileSizeCalculated(@TempDir Path tempDir) throws IOException{

    //given
    createFileWithSpecificSize(tempDir);
    SimpleCmd.setCurrentLocation(tempDir.toFile());
    String[] args = {"txt"};
    FindCommand findCommand = CommandLine.populateCommand(new FindCommand(),args);
    String fileSizeAsStringInMegaBytes = "1 MB";
    //when
    findCommand.run();
    //then
    String actualString = getOutputStream().toString();

    assertTrue(actualString.contains(fileSizeAsStringInMegaBytes));

  }


    private void prepareTestFiles(@TempDir Path tempDir) throws IOException {
    int amountFiles = 10;
    for(int i = 0; i < amountFiles ; i++) {
      Path myFile = tempDir.resolve("myFile"+ i + ".txt");
      Files.write(myFile, Collections.singletonList(""));
    }
  }

  private void createFileWithSpecificSize(@TempDir Path tempDir) throws IOException {
    Path myFile = tempDir.resolve("myFile.txt");
    Files.write(myFile, Collections.singletonList(""));
    long desiredSizeInBytes = 1024 * 1024; // 1 MB
    // Resize the temporary file to the desired size
    Files.write(myFile, new byte[(int) desiredSizeInBytes], StandardOpenOption.TRUNCATE_EXISTING);
  }


}
