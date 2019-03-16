import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
import java.io.PrintWriter;

public class Main {

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

        //ystem.out.println("Mutating test input by flipping one bit at random.");
        int mutation = ThreadLocalRandom.current().nextInt(0, 44 + 1);

        long mutatorString = (long) Math.pow(2, mutation);

        // flips a single bit. testInput XOR mutatorString
        testInput = testInput ^ mutatorString;

        return testInput;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Would you like to go into automated learning mode? [Y/N]");
        String ans = sc.nextLine();

        if (ans.equals("Y"))
        {
            // Automated training happens here.
            if (trainNetwork())
            {
                System.out.println ("Training succeeded.");
            }
            else
            {
                System.out.println ("Training failed.");
            }
        }

        else if (ans.equals("N"))
        {
            System.out.println("Utilizing previous weights.");
            System.out.println("Exiting.");
        }

    }

    // Initiate, train, and record the results of the neural network
    public static boolean trainNetwork()
    {
        int epoch = 0, numOfCorrectAnswers = 0, counter = 0;
        double SSE = 10, mean = 0, endCondition = 10;

        ArrayList<Perceptron> hiddenLayer = new ArrayList<Perceptron>();
        ArrayList<Perceptron> outputLayer = new ArrayList<Perceptron>();
        double[] SumOfSquaresArray = new double[100];

        try
        {
            String pathToFile = "Weights.txt";
            File dir = new File("tmp/test");
            dir.mkdirs();
            File tmp = new File(dir, "tmp.txt");
            tmp.createNewFile();
;
            PrintWriter outputFile = new PrintWriter(tmp);
            outputFile.println("Epoch, Label, SumOfSquaresError");

            // Initalize list of perceptrons
            for (int i = 0; i < 5; i++)
            {
                hiddenLayer.add(new Perceptron(45, false, i));
            }

            for (int i = 0; i < 10; i++)
            {
                outputLayer.add(new Perceptron(5, true, i));
            }

            // Keep training until sum of squares error is less then 10^-6.
            while (endCondition > 0.000001 || epoch < 99)
            {
                long data, sumOfLabels = 0;
                int encodedOutput = -1;
                double currentError = 0;
                boolean determined = false;
                double[] hiddenOutputs = new double[5];
                int[] outputs = new int[10];
                int[] desiredOutputs = new int[10];
                double max = 0;

                int label = ThreadLocalRandom.current().nextInt(0, 9 + 1);
                data = getMutatedBinaryString(label);

                for (int i = 0; i < 10; i++)
                {
                    desiredOutputs[i]= 0;
                }
                desiredOutputs[label] = 1;

                for (int i = 0; i < 5; i++)
                {
                    hiddenOutputs[i] = hiddenLayer.get(i).calculateOutputForInputLayer(data);
                }

                for (int i = 0; i < 10; i++) {
                    double temp = outputLayer.get(i).calculateOutputForOutputLayer(hiddenOutputs);
                    outputLayer.get(i).updateWeightsForOutputLayer(label);
                    if (temp > max)
                    {
                        max = temp;
                        encodedOutput = i;
                    }
                }
                outputs[encodedOutput] = 1;

                for (int i = 0; i < 5; i++)
                {
                    hiddenLayer.get(i).updateWeightsForHiddenLayer(outputLayer);
                }

                if (encodedOutput == label)
                {
                    numOfCorrectAnswers++;

                }
                epoch++;

                // Calculate Sum of squares error.
                for (int i = 0; i < 10; i++)
                {
                    currentError += Math.pow(outputs[i] - desiredOutputs[i], 2);
                }

                sumOfLabels += label;
                mean = sumOfLabels / epoch;
                SumOfSquaresArray[epoch] = currentError;
                SSE = 0;
                for (int i = 0; i <= epoch; i++)
                {
                    SSE += Math.pow(SumOfSquaresArray[i] - mean, 2);
                }
                endCondition = SSE /(double) epoch;
                outputFile.println(epoch +", " + label + ", "+ currentError);
                System.out.println("Epoch: " + epoch +" Label: " + label + " Output:["
                        + outputs[0] +  ", "
                        + outputs[1] +  ", "
                        + outputs[2] +  ", "
                        + outputs[3] +  ", "
                        + outputs[4] +  ", "
                        + outputs[5] +  ", "
                        + outputs[6] +  ", "
                        + outputs[7] +  ", "
                        + outputs[8] +  ", "
                        + outputs[9] +  "]"
                        + " SSE: " + currentError );
                outputLayer.get(7).printPerceptron();
            }
            System.out.println("Size of output layer:" +outputLayer.size());
        }
        catch (Exception e)
        {
            System.out.println("\n Exception:" + e);
            return false;
        }

        return true;
    }


}