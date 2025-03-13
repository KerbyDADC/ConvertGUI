public class Fibonacci{
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        System.out.println("The Fibonacci number of " + n + " is " + fiboCalc(n));
    }


    public static int fiboCalc(int n) {
        // since F0 and F1 are 0 and 1, we'll do this
        if (n <= 1) {
            return n;
        } 

        int fibNum1 = 0;
        int fibNum2 = 1;
        int fibNew = 0;

        // for this for loop we use 2 since 0 and 1 are already handled by the if statement
        for (int i = 2; i <= n; i++) {
            fibNew = fibNum1 + fibNum2;
            fibNum1 = fibNum2;
            fibNum2 = fibNew;
        }
        return fibNew;
    }

}
