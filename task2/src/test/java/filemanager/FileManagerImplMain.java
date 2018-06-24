package filemanager;


import java.util.regex.Pattern;

public class FileManagerImplMain {
    public static FileManager fileManager;
    public static Console console;
    static{
         fileManager = new FileManagerImpl();
         console = new Console();
    }

    public static void main(String[] args){
   
       Console.startScanner(fileManager);
    }
}
