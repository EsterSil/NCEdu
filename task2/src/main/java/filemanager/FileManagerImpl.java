package filemanager;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileManagerImpl implements FileManager {


    public List<String> searchByPattern(String path, String regex) {
        List<String> resultList = new ArrayList<String>();
        PathTreeNode treeNode = FileUtils.fillFileTree(new PathTreeNode(path));
        Pattern pattern = Pattern.compile(regex);
        if (!treeNode.isLeaf()) {
            List<TreeNode<String>> currentLvl = treeNode.getChildren();
            List<TreeNode<String>> nextLvl = new ArrayList<TreeNode<String>>();
            while (currentLvl.size() != 0) {
                for (TreeNode<String> child : currentLvl) {
                    if (child.getPath() != null) {
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
        PathTreeNode treeNode = FileUtils.fillFileTree(new PathTreeNode(sourcePath));
        PathTreeNode copyTreeNode = (PathTreeNode) treeNode.copy();
        copyTreeNode.setData(destinationPath);
        if (!treeNode.isLeaf()) {
            List<TreeNode<String>> currentLvl = treeNode.getChildren();
            List<TreeNode<String>> nextLvl = new ArrayList<TreeNode<String>>();
            while (currentLvl.size() != 0) {
                for (TreeNode<String> child : currentLvl) {
                    if (child.isLeaf()) {
                        FileUtils.copyFile(child.getFullPath(), destinationPath + "\\" + child.getSubPath());
                    } else {
                        nextLvl.addAll(child.getChildren());
                        FileUtils.createDirectory(destinationPath, child.getPath());
                    }
                }
                currentLvl = nextLvl;
                nextLvl = new ArrayList<TreeNode<String>>();
            }
        }
    }


    public void moveFiles(String sourcePath, String destinationPath) {
        PathTreeNode treeNode = FileUtils.fillFileTree(new PathTreeNode(sourcePath));
        if (sourcePath.charAt(0) == destinationPath.charAt(0)) {
            FileUtils.changePath(sourcePath, destinationPath);
        }
        PathTreeNode copyTreeNode = (PathTreeNode) treeNode.copy();
        copyTreeNode.setData(destinationPath);

        if (!treeNode.isLeaf()) {
            List<TreeNode<String>> currentLvl = treeNode.getChildren();
            List<TreeNode<String>> nextLvl = new ArrayList<TreeNode<String>>();
            while (currentLvl.size() != 0) {
                for (TreeNode<String> child : currentLvl) {
                    if (child.isLeaf()) {
                        FileUtils.copyFile(child.getFullPath(), destinationPath + "\\" + child.getSubPath());
                        FileUtils.deleteFile(child.getFullPath());
                    } else {
                        nextLvl.addAll(child.getChildren());
                        FileUtils.createDirectory(destinationPath, child.getPath());
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
