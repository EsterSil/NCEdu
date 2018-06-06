package filemanager;

import org.junit.Test;

import java.io.*;
public class FileTest {
    @Test
    public void copyFileTest() {

        String sourcePath = "D:\\Ann\\test.txt";
        String destinationPath = "D:\\Ann\\testFolder";
        FileUtils.copyFile(sourcePath, destinationPath);
    }

    @Test
    public void createDirectoryTest() {
        String sourcePath = "D:\\Ann";
        String fileName = "newFolder";
        FileUtils.createDirectory(sourcePath, fileName);
    }

    @Test
    public void changePathTest() {
        String sourcePath = "D:\\Ann\\newFolder";
        String destinationPath = "D:\\Ann\\testFolder";
        String fileName = "newFolder";

        FileUtils.changePath(sourcePath, destinationPath);
    }
    @Test
    public void deleteFileTest() {
        String sourcePath = "D:\\Ann\\newFolder\\testFolder";
        File sourceFile = new File(sourcePath);
        sourceFile.delete();
    }

    @Test
    public void fillFileTreeTest() {
        String sourcePath = "D:\\Ann\\testFolder";
        //String fileName = "testFolder";
        FileUtils.fillFileTree(new PathTreeNode(sourcePath));
    }
}
