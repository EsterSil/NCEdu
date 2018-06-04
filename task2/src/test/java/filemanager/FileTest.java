package filemanager;

import org.junit.Test;

import java.io.*;

public class FileTest {
    @Test
    public void copyFileTest() {

        String sourcePath = "D:\\Ann";
        String destinationPath = "D:\\Ann\\newFolder";
        String fileName = "test.txt";
        FileUtils.copyFile(sourcePath, destinationPath, fileName);
    }

    @Test
    public void createDirectoryTest() {
        String sourcePath = "D:\\Ann";
        String fileName = "newFolder";
        FileUtils.createDirectory(sourcePath, fileName);
    }

    @Test
    public void changePathTest() {
        String sourcePath = "D:\\Ann";
        String destinationPath = "C:\\Ann";
        String fileName = "newFolder";
        FileUtils.changePath(sourcePath, destinationPath, fileName);
    }
    @Test
    public void deleteFileTest() {
        String sourcePath = "D:\\Ann\\newFolder";
        String fileName = "testFolder";
        File sourceFile = new File(sourcePath+"\\"+fileName);
        sourceFile.delete();
    }

}
