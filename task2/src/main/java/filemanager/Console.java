package filemanager;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Console {


    public static void startScanner(){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Hello! \n This is a simple util, performing three operations with files: \n  1. Recursively " +
                    "search files satisfying some pattern. \n \tSyntax:: search \"path\" \"pattern\" \n 2. Recursively copying " +
                    "file or directory from source path to destination path. \n \tSyntax:: search \"sourcePath\"" +
                    " \"destinationPath\" \n 3. Recursively moving file or directory from source path to destination path. " +
                    "\n \tSyntax:: search \"sourcePath\" \"destinationPath\" ");
            String inputString = " ";
            inputString = scanner.next();
             if (inputString.equals("exit")){
                 System.out.println("Goodbye!");
                break;
            }

        }
    }
    private static String[] commandParser(String command){
        Pattern pattern = Pattern.compile("(search|copy|move)[ ]+(.)+[ ]+(.)+");
        if (pattern.matcher(command).matches()){
            String[] parts= command.split(" ");
        }
    }
}
