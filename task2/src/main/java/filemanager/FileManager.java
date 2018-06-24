package filemanager;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileManager {

    /**
     * performs recursive search in path directory
     * @param path - string representation if file path where the search will be started
     * @param pattern - pattern for matching
     * @return the list of string paths of files matched the pattern
     */
    List<String> search(String path, String pattern) throws NoSuchFieldException, FileNotFoundException;

    /**
     * performs recursive copying from sourcePath directory to destinationPath
     * @param sourcePath
     * @param destinationPath
     */
    void copy(String sourcePath, String destinationPath) throws FileNotFoundException;
    /**
     * performs recursive moving from sourcePath directory to destinationPath
     * @param sourcePath
     * @param destinationPath
     */
    void move(String sourcePath, String destinationPath) throws FileNotFoundException;
}
