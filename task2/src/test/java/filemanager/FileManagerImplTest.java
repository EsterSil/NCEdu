package filemanager;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FileManagerImplTest {

    @Test
    public void searchByPatternTest(){
        FileManager fileManager = new FileManagerImpl();
        List<String> list  = fileManager.searchByPattern("D:\\Ann\\testFolder", ".*\\.txt");
        Assert.assertTrue(true);
    }

    @Test
    public void copyFilesTest(){
        FileManager fileManager = new FileManagerImpl();
        fileManager.copyFiles("D:\\tesst\\testA", "D:\\tesst\\testB");
    }

    @Test
    public void moveFilesTest(){
        FileManager fileManager = new FileManagerImpl();
        fileManager.moveFiles("D:\\tesst\\testA", "D:\\tesst\\testB");
        int i;
    }

}
