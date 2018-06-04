package filemanager;

import java.util.List;

public interface FileManager {

    /**
     * performs recursive search in path directory
     * @param path - string representation if file path where the search will be started
     * @param pattern - pattern for matching
     * @return the list of string paths of files matched the pattern
     */
    List<String> searchByPattern(String path, String pattern);

    /**
     * performs recursive copying from sourcePath directory to destinationPath
     * @param sourcePath
     * @param destinationPath
     */
    void copyFiles(String sourcePath, String destinationPath);
    /**
     * performs recursive moving from sourcePath directory to destinationPath
     * @param sourcePath
     * @param destinationPath
     */
    void moveFiles(String sourcePath, String destinationPath);
}
