package filemanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileManagerImpl  implements  FileManager{



    public List<String> searchByPattern(String path, String regex) {
        List<String> resultList = new ArrayList<String>();
        //File rootFile = new File(path);
        PathTreeNode treeNode = FileUtils.fillFileTree(new PathTreeNode(path));
        Pattern pattern = Pattern.compile(regex);
        if (!treeNode.isLeaf()) {
            List<TreeNode<String>> currentLvl = treeNode.getChildren();
            List<TreeNode<String>> nextLvl = new ArrayList<TreeNode<String>>();
            while (currentLvl.size() != 0) {
                for (TreeNode<String> child : currentLvl) {
                    if (child.getPath() != null){
                        //action.foo(pattern, child, resultList);
                        if (pattern.matcher(child.getPath()).matches()) {
                            resultList.add(child.getFullPath());
                        }
                    }
                    if (!child.isLeaf()) {
                        nextLvl.addAll(child.getChildren());
                    }
                }
                currentLvl = nextLvl;
                nextLvl = new ArrayList<TreeNode<String>>();
            }
        }
        return resultList;
    }

    public void copyFiles(String sourcePath, String destinationPath) {
        //File sourceFile = new File(sourcePath);
        PathTreeNode treeNode = FileUtils.fillFileTree(new PathTreeNode(sourcePath));
        if (!treeNode.isLeaf()) {
            List<TreeNode<String>> currentLvl = treeNode.getChildren();
            List<TreeNode<String>> nextLvl = new ArrayList<TreeNode<String>>();
            while (currentLvl.size() != 0) {
                for (TreeNode<String> child : currentLvl) {
                    String childPath = child.getPath();
                    if (child.isLeaf()) {
                        FileUtils.copyFile(sourcePath+"\\"+childPath, destinationPath+"\\"+childPath);
                    } else {
                        nextLvl.addAll(child.getChildren());
                        FileUtils.createDirectory(destinationPath+"\\"+childPath, child.getPath());
                    }
                }
                currentLvl = nextLvl;
                nextLvl = new ArrayList<TreeNode<String>>();
            }
        }
    }


    public void moveFiles(String sourcePath, String destinationPath) {
        File sourceFile = new File(sourcePath);
        PathTreeNode treeNode = FileUtils.fillFileTree(new PathTreeNode(sourcePath));
        if (sourcePath.charAt(0)== destinationPath.charAt(0)){
            FileUtils.changePath(sourcePath,destinationPath);
        }
        if (!treeNode.isLeaf()) {
            List<TreeNode<String>> currentLvl = treeNode.getChildren();
            List<TreeNode<String>> nextLvl = new ArrayList<TreeNode<String>>();
            while (currentLvl.size() != 0) {
                for (TreeNode<String> child : currentLvl) {
                    String childPath = child.getFullPath();
                    if (child.isLeaf()) {
                        FileUtils.copyFile(sourcePath+"\\"+childPath, destinationPath+"\\"+childPath);
                        FileUtils.deleteFile(sourcePath+"\\"+childPath);
                    } else {
                        nextLvl.addAll(child.getChildren());
                        FileUtils.createDirectory(destinationPath+"\\"+childPath, child.getPath());
                    }
                }
                currentLvl = nextLvl;
                nextLvl = new ArrayList<TreeNode<String>>();
            }
        }
    }
/*
    private void traverseTree(Action action, TreeNode<String> treeNode, Pattern pattern, List<String> resultList ) {

        if (!treeNode.isLeaf()) {
            List<TreeNode<String>> currentLvl = (List<TreeNode<String>>)treeNode.getChildren();
            List<TreeNode<String>> nextLvl = new ArrayList<TreeNode<String>>();
            while (currentLvl.size() != 0) {
                for (TreeNode<String> child : currentLvl) {
                    if (child.getPath() != null){
                        action.foo(pattern, child, resultList);
                    }
                    if (!child.isLeaf()) {
                        nextLvl.addAll(child.getChildren());
                    }
                }
                currentLvl = nextLvl;
                nextLvl = new ArrayList<TreeNode<String>>();
            }
        }
    }

    interface Action {
        void foo(Pattern pattern, TreeNode<String> child, List<String> resultList );

    }*/
}
