
public class RepeatSearchAlgorithm {

    /**
     * method searches for the repetitive number in the input array
     * @param input n+1 size array of numbers, should contains integers from 1 to n
     * @return found repetitive number
     */
    public int search(int[] input) {
        int i = input[input.length - 1];
        int save = -1 ;
        while (i != save) {
            save = i;
            i = input[i-1];
            input[save-1] = 0;
        }
        return save;
    }
}
