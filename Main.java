import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
	    System.out.println("Would you like to go into automated learning mode? [Y/N]");
	    String ans = sc.nextLine();

	    if (ans.equals("Y"))
        {
            // Automated training happens here.
            int epoch = 0, SSE = 0, numOfCorrectAnswers = 0, counter = 0;

            while (counter <= 50)
            {
                boolean gotCorrectAnswer = trainNetwork();
                if (gotCorrectAnswer)
                {
                    counter++;
                    numOfCorrectAnswers++;
                }
                else
                {
                    counter = 0;
                }
                epoch++;
                SSE = numOfCorrectAnswers / epoch;
            }

            /*
            long data;
            int label = ThreadLocalRandom.current().nextInt(0, 9 + 1);
            data = getMutatedBinaryString(label);
            */

        }

	    else if (ans.equals("N"))
        {
            System.out.println("Utilizing previous weights.");

            System.out.println("Exiting.");
        }

    }

    // Train neural network
    public static boolean trainNetwork()
    {

        return true;
    }

    // Takes in an integer and returns the appropriate binary string stored as a long.
    public static long getMutatedBinaryString(int label)
    {
        long testInput;
        switch (label)
        {

            case 0:
                testInput = Long.parseLong("011101000110001100011000110001100011000101110", 2);
                break;
            case 1:
                testInput = Long.parseLong("001000110010100001000010000100001000010000100",2);
                break;
            case 2:
                testInput = Long.parseLong("011101000100001000010001000100010001000011111", 2);
                break;
            case 3:
                testInput = Long.parseLong("011101000100001000010001000001000011000101110", 2);
                break;
            case 4:
                testInput = Long.parseLong("000100011000110010100101010010111110001000010", 2);
                break;
            case 5:
                testInput = Long.parseLong("111111000010000111101000100001000011000101110", 2);
                break;
            case 6:
                testInput = Long.parseLong("011101000110000100001111010001100011000101110", 2);
                break;
            case 7:
                testInput = Long.parseLong("111110000100010000100010000100010000100001000", 2);
                break;
            case 8:
                testInput = Long.parseLong("011101000110001100010111010001100011000101110", 2);
                break;
            case 9:
                testInput = Long.parseLong("011101000110001100010111100001000011000101110", 2);
                break;

            default:
                System.out.println("Encountered an unexpected error. Assuming input is 0.");
                testInput = Long.parseLong("011101000110001100011000110001100011000101110", 2);
                break;
        }

        System.out.println("Mutating test input by flipping one bit at random.");
        int mutation = ThreadLocalRandom.current().nextInt(0, 44 + 1);

        long mutatorString = (long) Math.pow(2, mutation);

        // flips a single bit. testInput XOR mutatorString
        testInput = testInput ^ mutatorString;

        return testInput;
    }
}
