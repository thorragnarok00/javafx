import java.util.Scanner;

public class DiceProbabilityCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of dice: ");
        int numDice = scanner.nextInt();

        System.out.print("Enter the target sum: ");
        int targetSum = scanner.nextInt();

        int numSides = 6;

        //Calculate the total number of possible outcomes
        int totalOutcomes = (int) Math.pow(numSides, numDice);

        //Calculate the number of outcomes that result in the target sum
        int favorableOutcomes = countFavorableOutcomes(numDice, numSides, targetSum);

        //Calculate the probability
        double probability = (double) favorableOutcomes / totalOutcomes;

        System.out.println("Probability of getting a sum of " + targetSum + " with " + numDice + " dice rolls: " + probability);

        scanner.close();
    }

    private static int countFavorableOutcomes(int numDice, int numSides, int targetSum) {
        if (numDice == 0) {
            return (targetSum == 0) ? 1 : 0;
        }

        int count = 0;

        for (int i = 1; i <= numSides; i++) {
            count += countFavorableOutcomes(numDice - 1, numSides, targetSum - i);
        }

        return count;
    }
}
