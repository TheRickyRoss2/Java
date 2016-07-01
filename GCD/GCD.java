// GCD.java
// Ric Rodriguez
// rirrodri@ucsc.edu
// pa3
// Find the GCD between two integers

import java.util.Scanner;
class GCD{
    public static void main( String[] args ){
        int a;
        int b;
        int a1;
        int b1;
        Scanner sc = new Scanner(System.in);
        Scanner sd = new Scanner(System.in);

        System.out.print("Enter a positive integer: ");
        while(!sc.hasNextInt()){
            System.out.print("Please enter a positive integer: ");
            sc.next();
        }
        a = sc.next();
        while(a <= 0){
            System.out.print("Please enter a positive integer: ");
            while(!sc.hasNextInt()){
                System.out.print("Please enter a positive integer: ");
                sc.next();
            }
            a = sc.next();
        }
        a1 = a;

        System.out.print("Enter another positive integer: ");
        while(!sd.hasNextInt()){
            System.out.print("Please enter a positive integer: ");
            sd.next();
        }
        b = sd.next();
        while(b <=0){
            System.out.print("Please enter a positive integer: ");
            while(!sd.hasNextInt()){
                System.out.print("Please enter a positive integer: ");
                sd.next();
            }
            b = sd.next();
        }
        b1 = b;
        while (b != 0) {
            int c = a % b;
            a = b;
            int c1 = c;
            b = c;
        }
        System.out.println("The GCD of " + a1 + " and " + b1 + " is "+ a);
    }
}
