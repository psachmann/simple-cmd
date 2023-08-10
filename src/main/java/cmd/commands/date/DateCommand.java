package cmd.commands.date;

import picocli.CommandLine;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@CommandLine.Command(name = "date", description = "Returns the current DateCommand", mixinStandardHelpOptions = true)
public class DateCommand implements Runnable {

    @Override
    public void run() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        System.out.println(formattedDate);
    }
}
