import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Perceptron
{
    private boolean OL;
    private double[] weights;
    private double threshold;
    private double output;
    private double errorGradient;

    // Random initialization constructor
    public Perceptron(int numOfInputs, boolean outputLayer)
    {
        weights = new double[numOfInputs];
        int counter = 0;

        while (counter < numOfInputs)
        {
            weights[counter] = ThreadLocalRandom.current().nextDouble(-1, 1);
            ++counter;
        }

        threshold =  ThreadLocalRandom.current().nextDouble(-1, 1);
        OL = outputLayer;

    }

    // Set weights and threshold constructor
    public Perceptron(double[] w, double theta, boolean outputLayer)
    {
        weights = new double[w.length];
        weights = Arrays.copyOf(w, w.length);
        threshold = theta;
        OL = outputLayer;

    }


    public double calculateOutputForInputLayer(long input)
    {
        int counter = 0;
        double summation = 0;

        if (OL)
        {
            System.out.println("You are using the wrong method. Returning 0. The ANN is bugged now.");
            return 0;
        }
        else
        {
            while (counter < weights.length)
            {
                // Check if there is a 1 in the nth position/
                if ((input & ((long)Math.pow(2, counter))) > 0)
                {
                    summation += weights[counter] * 1;
                }

                // If there is not a 1 in the nth position, weight * 0 = 0, so no change to summation.
            }
            output = summation - threshold;
            output = 1.0 / (1.0 + Math.exp(output));
            return output;
        }
    }

    public double calculateOutputForOutputLayer(double[] inputs)
    {
        int counter = 0;
        double summation = 0;

        if (OL)
        {
            while (counter < weights.length)
            {
                summation += inputs[counter] * weights[counter];
            }
            output = summation - threshold;
            if (output > 0)
            {
                output = 1;
            }
            else
            {
                output = 0;
            }
            return output;
        }

        else
        {
            System.out.println("You are using the wrong method. Returning 0. The ANN is bugged now.");
            return 0;
        }
    }

    public void calculateNewWeightsForOutputLayer(int desired)
    {
        double error = desired - output;
        // WIP

    }

    public double returnOutput()
    {
        return output;
    }
}
