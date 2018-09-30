/* This code implements dynamic programming to find out the number of ways the set of coins cann be added to sum to the given amount.
*/

public class CountWaysToAddCoinsToAmount {

    public static void main(String[] args) {
        int[] typesOfCoins = new int[]{1, 2, 5, 10, 20, 25};
        int amount = 40;
        int numberOfCoins = typesOfCoins.length;

        int resultantCoins = countOfCoins(typesOfCoins, numberOfCoins, amount);
        System.out.println(resultantCoins);
    }

    public static int countOfCoins (int[] coins, int lengthOfCoinsArray, int amount) {
        if(amount < 0)
            return 0;

        if(amount == 0)
            return 1;

        if(lengthOfCoinsArray<=0 && amount>=1)
            return 0;

        return countOfCoins(coins, lengthOfCoinsArray-1, amount) +
                countOfCoins(coins, lengthOfCoinsArray, amount-coins[lengthOfCoinsArray-1]);
    }
}
