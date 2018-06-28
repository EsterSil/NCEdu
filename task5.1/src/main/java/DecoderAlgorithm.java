import java.util.ArrayList;
import java.util.List;


/**
 * this class provides method decoding string of specific format
 */
public class DecoderAlgorithm {

    /**
     * method recursively searches for first-level brackets and opens them until no more brackets would be found within
     * current shortString
     *
     * @param shortString specifically formatted string where repeatable patterns are in square brackets with number of
     *                 repeats before it
     * @return decoded string
     * @throws IllegalArgumentException is thrown if input string has incompatible format
     */

    public String decodeString(String shortString) throws IllegalArgumentException {
        StringBuilder builder = new StringBuilder(shortString);
        int multiplexer = 0;
        int digitIndex = -1;
        int replaceIndex = -1;
        int bracketCounter = 0;
        int openIndex = -1;
        int closeIndex = -1;
        boolean haveBrackets;
        char c;
        StringBuilder resultSubstring = new StringBuilder();
        String substring;
        do {
            haveBrackets = false;
            digitIndex = -1;
            multiplexer = 0;
            openIndex = -1;
            closeIndex = -1;
            replaceIndex = -1;
            resultSubstring.delete(0, resultSubstring.length());
            for (int n = 0; n < builder.length(); n++) {
                c = builder.charAt(n);
                if ((Character.isDigit(c))) {
                    digitIndex = digitIndex == -1 ? n : digitIndex;
                }
                if (c == '[') {
                    haveBrackets = true;
                    if (digitIndex == -1) {
                        break;
                    }
                    bracketCounter++;
                    if (bracketCounter == 1) {
                        String s = builder.substring(digitIndex, n);
                        multiplexer = (multiplexer == 0) ? Integer.parseInt(s) : multiplexer;
                        replaceIndex = (replaceIndex == -1) ? digitIndex : replaceIndex;
                        digitIndex = -1;
                        openIndex = (openIndex == -1) ? n : openIndex;
                    }
                }
                if (c == ']') {
                    bracketCounter--;
                    if (bracketCounter == 0) {
                        closeIndex = (closeIndex == -1) ? n : closeIndex;
                    }
                }
            }
            if (bracketCounter != 0) {
                throw new IllegalArgumentException();
            }
            if ((openIndex != -1) && (closeIndex != -1)) {
                substring = decodeString(builder.substring(openIndex + 1, closeIndex));
                for (int j = 0; j < multiplexer; j++) {
                    resultSubstring.append(substring);
                }
                builder.replace(replaceIndex, closeIndex + 1, resultSubstring.toString());
            }
        } while (haveBrackets);
        return builder.toString();
    }
}
