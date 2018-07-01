public class BinaryCounter {
    /**
     * this method counts numbers of ones in binary representation of every element in input array
     * @param input
     * @return
     */
    public int[] countOnes(int[] input){
        int divider = 0;
        int rest = 0;
        for (int i = 1; i < input.length; i++){
           divider = input[i]/2;
           rest = input[i]%2;
           input[i] = input[divider-1] + rest;
        }
        return input;
    }
}
