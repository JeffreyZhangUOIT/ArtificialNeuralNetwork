import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Perceptron
{
    private boolean OL;
    private double[] weights;
    private double threshold;
    private double output;
    private double errorGradient;
    private final double learningRate = 0.01;
    private int id;

    // Random initialization constructor
    public Perceptron(int numOfInputs, boolean outputLayer, int identifier)
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
        id = identifier;

    }

    // Set weights and threshold constructor
    public Perceptron(double[] w, double theta, boolean outputLayer, int identifier)
    {
        weights = new double[w.length];
        weights = Arrays.copyOf(w, w.length);
        threshold = theta;
        OL = outputLayer;
        id = identifier;
    }

    public void printPerceptron()
    {
        System.out.println("Perceptron: " + id + " Hidden Layer? " + OL + "\nWeights: ["
            + getAllWeights() + "] \nThreshold: " + String.format("%.2f", threshold));
    }

    public String getAllWeights()
    {
        String s = "";
        for (int i = 0; i < weights.length ; i++)
        {
            if (i == (weights.length -1))
            {
                s += String.format("%.2f", weights[i]);
            }
            else
            {
                s += String.format("%.2f", weights[i])  + ", " ;
            }

        }
        return s;
    }

    public double getErrorGradient()
    {
        return errorGradient;
    }

    public double getWeight(int index)
    {
        return weights[index];
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
                counter++;
            }
            output = summation - threshold;
            output = 1.0 / (1.0 + Math.exp(-output));

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
                counter++;
            }
            output = summation - threshold;
            output = 1.0 / (1.0 + Math.exp(-output));
            return output;
        }

        else
        {
            System.out.println("You are using the wrong method. Returning 0. The ANN is bugged now.");
            return 0;
        }
    }

    public void updateWeightsForOutputLayer(int desired)
    {
       if (OL)
       {
           double error = 0 - output;

           if (desired == id)
           {
               error =  1 - output;
           }

           errorGradient = output * (1 - output) * error;
           int counter = 0 ;

           while (counter < weights.length)
           {
               double weightDelta = learningRate * output * errorGradient;
               weights[counter] = weights[counter] + weightDelta;
               counter++;
           }
           threshold = threshold + (learningRate * -1 * errorGradient);
       }
       else
       {
           System.out.println("You are using the wrong function. Doing nothing.");
       }
    }


    public void updateWeightsForHiddenLayer(ArrayList<Perceptron> outputLayer)
    {
        if (OL)
        {
            System.out.println("You are using the wrong function. Doing nothing.");
        }
        else
        {
            int counter = 0 ;
            double summation = 0;

            while (counter < outputLayer.size())
            {
                summation += outputLayer.get(counter).getErrorGradient() *
                        outputLayer.get(counter).getWeight(id);
                counter++;
            }

            errorGradient = output *  (1 - output) * summation;
            counter = 0;

            while (counter < weights.length)
            {
                double weightDelta = learningRate * output * errorGradient;
                weights[counter] = weights[counter] + weightDelta;
                counter++;
            }
            threshold = threshold + (learningRate * -1 * errorGradient);
        }


    }


}