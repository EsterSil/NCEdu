package filemanager;

import java.io.*;

public class FileUtils {
    /**
     * extract the name from given path
     * @param sourcePath
     * @return file name extracted
     */
    static String getFileName(String sourcePath){
        String[] parts = sourcePath.split("\\\\");
        return parts[parts.length-1];
    }
    /**
     * create directory in destination directory
     * @param destinationPath
     * @param directoryName
     * @return final path to new  directory
     */

    static String createDirectory(String destinationPath, String directoryName) throws IllegalArgumentException {
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
     * creates a copy of file in destination directory
     * @param sourcePath
     * @param destinationPath
     */
    static void copyFile(String sourcePath, String destinationPath) throws  IllegalArgumentException{
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(sourcePath);
            File newFile = new File(destinationPath);
            if (newFile.exists()){
                newFile = new File(destinationPath+".copy");
            }
            outputStream = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * moves file changing it's path within one disk
     * do not work between different disks
     * @param sourcePath
     * @param destinationPath
     */
    static void changePath( String sourcePath, String destinationPath) throws IllegalArgumentException {
        String fileName = getFileName(sourcePath);
        File sourceFile = new File(sourcePath);
        if (!sourceFile.exists()){
            throw new IllegalArgumentException();
        }
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
     */
    static void deleteFile( String sourcePath){
        String fileName = getFileName(sourcePath);
        File sourceFile = new File(sourcePath+"\\"+fileName);
        sourceFile.delete();
    }

    /**
     * fills the PathTree with paths od files and directories associated with root node
     * @param root
     * @return
     */
    static PathTreeNode fillFileTree(PathTreeNode root) throws IllegalArgumentException {
        File rootFile = new File(root.getFullPath());
        if (!rootFile.exists()){
            throw new IllegalArgumentException();
        }
        String[] files = rootFile.list();
        if (files!= null) {
            for (int i = 0; i < files.length; i++) {
                PathTreeNode newNode = root.addNode(files[i]);
                String str = root.getFullPath()+"\\"+files[i];
                File newFile = new File(str);

                if(newFile.isDirectory()){
                    fillFileTree(newNode);
                }
            }
        }
        return root;
    }
}
