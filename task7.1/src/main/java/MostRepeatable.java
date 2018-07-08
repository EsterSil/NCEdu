/**
 * this class provide implementation of algorithm for searching the most repeatable element in array
 */
public class MostRepeatable {
    /**
     * method finds the most repeatable element if it appears more than <code>input.length/2+1</code> times.
     * Works by O(N) without new memory
     * @param input array of ints
     * @return the most repeatable
     */
    public int findMostRepeatable(int[] input) {
        int repeatCounter = 0;
        int unpaired = input[0];

        for (int i = 0; i < input.length; i++) {
            if (input[i] == unpaired){
                repeatCounter ++;
            } else {
                repeatCounter--;
            }
            if (repeatCounter == 0){
                unpaired = input[i];
                repeatCounter++;
            }
        }
        return unpaired;
    }
}
