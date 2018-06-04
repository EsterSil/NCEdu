package filemanager;

import java.io.*;

public class FileUtils {
    /**
     * create directory in destination directory
     * @param destinationPath
     * @param directoryName
     * @return final path to new  directory
     */
    static String createDirectory(String destinationPath, String directoryName) {
        String resultPath = destinationPath+"\\"+directoryName;
        File newFile = new File(resultPath);
        if (!newFile.exists()) {
            newFile.mkdir();
        }else{
            resultPath = destinationPath+"\\copy."+directoryName;
            new File(resultPath).mkdir();
        }
        return resultPath;
    }

    /**
     * create a copy of file in destination directory
     * @param sourcePath
     * @param destinationPath
     * @param sourceFile
     */
    static void copyFile(String sourcePath, String destinationPath, String sourceFile){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        //String sourcePath = "D:\\Ann";
        //String destinationPath = "D:\\Ann\\testFolder";
        //String fileName = "test.txt";

        try {
            inputStream = new FileInputStream(sourcePath + "\\" + sourceFile);
            File newFile = new File(destinationPath+"\\"+sourceFile);
            if (newFile.exists()){
                newFile = new File(destinationPath+"\\copy."+sourceFile);
            }
            outputStream = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * move file changing it's path within one disk
     * do not work between different disks
     * @param sourcePath
     * @param destinationPath
     * @param fileName
     */
    static void changePath( String sourcePath, String destinationPath, String fileName){
        File sourceFile = new File(sourcePath+"\\"+fileName);
        File destinationFile = new File(destinationPath+"\\"+fileName);
        if(!destinationFile.exists()){
            sourceFile.renameTo(destinationFile);
        } else {
            destinationFile = new File(destinationPath+"\\copy."+fileName);
            sourceFile.renameTo(destinationFile);
        }
    }

    /**
     * delete empty folders and files
     * @param sourcePath
     * @param fileName
     */
    static void deleteFile( String sourcePath,  String fileName){
        File sourceFile = new File(sourcePath+"\\"+fileName);
        sourceFile.delete();
    }

}
