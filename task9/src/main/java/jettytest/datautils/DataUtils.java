package jettytest.datautils;

import java.util.regex.Pattern;

public class DataUtils {

    public static String formatPhone(String number){
        String regex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        StringBuilder builder = null;
        if(pattern.matcher(number).matches()){
            builder = new StringBuilder();
            builder.append("(");
            int counter = 0;
            for(int i = 0; i < number.length(); i++){
                if(Character.isDigit(number.charAt(i))){
                    if (counter == 3){
                        builder.append(")");
                    }
                    if (counter == 6){
                        builder.append("-");
                    }
                    if (counter == 8){
                        builder.append("-");
                    }
                    builder.append(number.charAt(i));
                    counter++;
                }
            }
        }
        if (builder != null) {
            return builder.toString();
        }
        return null;
    }
}
