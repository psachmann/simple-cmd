package cmd.commands.hash;

import picocli.CommandLine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@CommandLine.Command(name = "hash", description = "Returns MD5 Hash for a given file.", mixinStandardHelpOptions = true)
public class HashCommand implements Runnable {

    @CommandLine.Parameters(index = "0", description = "The file for which to calculate the MD5 hash.")
    private File inputFile;

    @Override
    public void run() {
        returnHash();
    }

    private void returnHash() {
        try {
            MessageDigest md5Digest = MessageDigest.getInstance("MD5");
            FileInputStream fileInputStream = new FileInputStream(inputFile);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                md5Digest.update(buffer, 0, bytesRead);
            }

            fileInputStream.close();

            byte[] md5Hash = md5Digest.digest();

            StringBuilder hashBuilder = new StringBuilder();
            for (byte b : md5Hash) {
                hashBuilder.append(String.format("%02x", b));
            }

            System.out.println("MD5 Hash: " + hashBuilder);

        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.println("An Exception occured.");
        }
    }
}
