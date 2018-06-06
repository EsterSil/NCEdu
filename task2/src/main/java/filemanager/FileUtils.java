package filemanager;

import java.io.*;

public class FileUtils {
    /**
     *
     * @param sourcePath
     * @return
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
     */
    static void copyFile(String sourcePath, String destinationPath){
        String sourceFile = getFileName(sourcePath);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(sourcePath);
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
     *
     */
    static void changePath( String sourcePath, String destinationPath){
        String fileName = getFileName(sourcePath);
        File sourceFile = new File(sourcePath);
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

    static PathTreeNode fillFileTree(PathTreeNode root){ //File rootFile){
        //PathTreeNode root = new PathTreeNode(rootFile.getPath());
        File rootFile = new File(root.getFullPath());
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
