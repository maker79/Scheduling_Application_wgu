package utils;

import controller.LoginScreenController;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class FileLogger {

    private static final String FILENAME = "loginLog.txt";

    public FileLogger() {
    }

    public static void handleLog(String userName, boolean success) throws IOException {

        String textToWrite = LocalDateTime.now().toString() + " " + userName + " " + (success ? "Login success" : "Login failure!");
        // Create file writer object
        FileWriter fileWriter = new FileWriter(FILENAME, true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(textToWrite);
        printWriter.close();


    }

}
