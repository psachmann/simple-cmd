package cmd.commands.find;


import java.io.File;
import java.io.FileFilter;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cmd.SimpleCmd;
import cmd.commands.dir.DirCommand;
import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;



/**
 * "find" command class
 * <p/>
 * Executing the command find to get all files with a specific extension in the current directory.
 *
 * @see SimpleCmd#getCurrentLocation()
 */
@Command(name = "find", description = "Find file of a given file type in the current directory",
    mixinStandardHelpOptions = true)
public class FindCommand implements Runnable{

  private static final Logger LOG = LoggerFactory.getLogger(DirCommand.class);


  @Parameters(index = "0", description = "The file extension to search for (without the dot).")
  private String fileExtension;

  @Option(names = "-s", description = "Show storage information")
  private boolean showStorageInfo;

  public FindCommand(){
    /* intentionally empty */
  }

  @Override
  public void run() {

    File directory = SimpleCmd.getCurrentLocation();
    System.out.println();
    //search files
    File[] foundFiles = directory.listFiles(file -> file.isFile() && file.getName().endsWith("." + fileExtension));

    if(foundFiles != null && foundFiles.length > 0) {

      for (File file : foundFiles) {
        System.out.println(file.getName() + " - Size: " + formatFileSize(file.length()));
      }
      System.out.println("Total files found: " + foundFiles.length);

      if (showStorageInfo) {
        System.out.println("Storage used: " + formatFileSize(calculateTotalFileSize(foundFiles)));
      }

    }
    else {
      LOG.warn("No files found in the current directory!");
    }
  }

  //See: https://stackoverflow.com/questions/3263892/format-file-size-as-mb-gb-etc
  private String formatFileSize(long size) {
    if (size <= 0) return "0 B";
    final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
    int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
    return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
  }

  private long calculateTotalFileSize(File[] files) {
    long totalSize = 0;
    for (File file : files) {
      totalSize += file.length();
    }
    return totalSize;
  }

}