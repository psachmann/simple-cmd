package cmd.commands.time;

import picocli.CommandLine;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@CommandLine.Command(name = "time", description = "Returns the current time", mixinStandardHelpOptions = true)
public class TimeCommand implements Runnable{

    @Override
    public void run() {
        // Get the current time
        LocalTime currentTime = LocalTime.now();

        // Define the format for the time string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        // Format the current time as a string
        String formattedTime = currentTime.format(formatter);

        System.out.println("Current time: " + formattedTime);
    }
}
