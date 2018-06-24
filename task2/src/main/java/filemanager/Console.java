package filemanager;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * class scanners system input and invoke command from file manager and file utils
 * Examples of syntax:
 * move D:\Ann\tesst\testA D:\Ann\tesst\testB
 * copy D:\Ann\tesst\testA D:\Ann\tesst\testB
 * search D:\Ann\tesst\testB .*\.txt
 */
public class Console {
    private static final String greetingMess = "Hello! \n This is a simple util, performing three operations with files: \n" +
            " 1. Recursively search files satisfying some pattern. \n \tSyntax:: search \"path\" \"pattern\" \n" +
            " 2. Recursively copying file or directory from source path to destination path. \n \tSyntax:: copy " +
            "\"sourcePath\" \"destinationPath\" \n 3. Recursively moving file or directory from source path to " +
            "destination path. \n \tSyntax:: move \"sourcePath\" \"destinationPath\" \n";

    private static final String errorMess = "Command is wrong: ";
    private static final String checkMess = "Please, check the syntax: \n \t search \"path\" \"pattern\"" +
            "\n \t copy \"sourcePath\" \"destinationPath\" \n \t move \"sourcePath\" \"destinationPath\" \n";

    private static String[] commandRegex = null;

    static {
        commandRegex = new String[]{"search|move|copy|exit", "^([A-Z]):\\\\([a-zA-Z]+\\\\)*+([a-zA-Z]+)?$"};
    }

    public static void startScanner(FileManager fileManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(greetingMess);
        String command = null;
        String firstArg = null;
        String secondArg = null;
        while (true) {
            if (scanner.hasNext(commandRegex[0])) {
                command = scanner.next();
            }
            if (command != null) {
                if (command.equals("exit")) {
                    System.out.println("Goodbye!");
                    break;
                } else {
                    if (scanner.hasNext()) {
                        firstArg = scanner.next();

                        if (scanner.hasNext()) {
                            secondArg = scanner.next();
                            if (!command.equals("search")) {

                                if (!commandParser(secondArg, commandRegex[1])) {
                                    System.out.println(errorMess);
                                    System.out.println("second argument format is incompatible \n");
                                    System.out.println(checkMess);
                                    secondArg = null;
                                }
                            }
                        } else {
                            System.out.println(errorMess);
                            System.out.println("wrong number of arguments \n");
                            System.out.println(checkMess);
                        }
                        if (!commandParser(firstArg, commandRegex[1])) {
                            System.out.println(errorMess);
                            System.out.println("first argument format is incompatible \n");
                            System.out.println(checkMess);
                            firstArg = null;
                        }
                    } else {
                        System.out.println(errorMess);
                        System.out.println("wrong number of arguments \n");
                        System.out.println(checkMess);
                    }
                }
                if ((firstArg != null) && (secondArg != null)) {
                    Class<?> clazz = fileManager.getClass();
                    try {
                        Method method = clazz.getMethod(command, String.class, String.class);
                        Object res = method.invoke(fileManager, firstArg, secondArg);
                        System.out.println(" Files successfully " + command + "ed");
                        if (res != null) {
                            List<String> result = (List<String>) res;
                            if (result.size() == 0) {
                                System.out.println(" no such files");
                            } else {
                                for (String s : result) {
                                    System.out.println(s);
                                }
                            }
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        System.out.println(errorMess);
                        System.out.println("file doesn't exists \n");
                        System.out.println(checkMess);
                    } catch (IllegalArgumentException e) {
                        System.out.println(errorMess);
                        System.out.println("file doesn't exists \n");
                        System.out.println(checkMess);
                    }
                }
            }
        }
    }

    private static boolean commandParser(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command).matches();
    }
}
