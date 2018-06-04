package filemanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileManagerImpl  implements  FileManager{

    private FileTreeNode fillFileTree(File rootFile){
        FileTreeNode root = new FileTreeNode(rootFile.getPath());
        String[] files = rootFile.list();
        if (files!= null) {
            for (int i = 0; i < files.length; i++) {
                root.addNode(files[i]);
                File newFile = new File(files[i]);
                if(newFile.isDirectory()){
                    fillFileTree(newFile);
                }
            }
        }
        return root;
    }

    public List<String> searchByPattern(String path, String regex) {
        List<String> resultList = new ArrayList<String>();
        File rootFile = new File(path);
        FileTreeNode treeNode = fillFileTree(rootFile);
        Pattern pattern = Pattern.compile(regex);

        this.traverseTree(new Action() {
            public void foo(Pattern pattern, TreeNode<String> child, List<String> resultList) {
                if (pattern.matcher(child.getData()).matches()) {
                    resultList.add(child.getFullPath());
                }
            }
        }, treeNode,pattern, resultList);
        return resultList;
    }

    public void copyFiles(String sourcePath, String destinationPath) {
        File sourceFile = new File(sourcePath);
        FileTreeNode treeNode = fillFileTree(sourceFile);

        InputStream inputStream = null;
        OutputStream outputStream = null;
        if (!treeNode.isLeaf()) {
            List<TreeNode<String>> currentLvl = treeNode.getChildren();
            List<TreeNode<String>> nextLvl = new ArrayList<TreeNode<String>>();
            while (currentLvl.size() != 0) {
                for (TreeNode<String> child : currentLvl) {
                    String childPath = child.getFullPath();
                    if (child.isLeaf()) {
                        File sFile = new File(sourcePath+childPath);
                        File dFile = new File(destinationPath+childPath);
                        try {
                            inputStream = new FileInputStream(sFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            outputStream = new FileOutputStream(dFile);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                        byte[] buffer = new byte[1024];
                        int length;
                        //while ((length))
                    } else {
                        nextLvl.addAll(child.getChildren());
                    }
                }
                currentLvl = nextLvl;
                nextLvl = new ArrayList<TreeNode<String>>();
            }
        }
    }


    public void moveFiles(String sourcePath, String destinationPath) {

    }

    private void traverseTree(Action action, TreeNode<String> treeNode, Pattern pattern, List<String> resultList ) {

        if (!treeNode.isLeaf()) {
            List<TreeNode<String>> currentLvl = (List<TreeNode<String>>)treeNode.getChildren();
            List<TreeNode<String>> nextLvl = new ArrayList<TreeNode<String>>();
            while (currentLvl.size() != 0) {
                for (TreeNode<String> child : currentLvl) {
                    if (child.getData() != null){
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

    }
}
