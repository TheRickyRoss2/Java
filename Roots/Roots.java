// Roots.java
// Ric Rodriguez
// rirrodri@ucsc.edu
// pa4
// Find real roots of any polynomial

import java.util.Scanner;
import java.io.File;
class Roots{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double resolution = 0.01, tolerance = 0.0000001, threshold = 0.001;
        double width, sign = 0, mid = 0, u = 0, v = 0;
        double a, b, root;
        boolean done = false;
        System.out.print("Enter the degree: ");
        int degree = sc.nextInt();
        double[] C = new double[degree + 1];
        System.out.print("Enter " + (degree + 1) + " coefficients: ");
        for (int i = 0; i < C.length;i++) {
            C[i] = sc.nextDouble();
        }
        System.out.print("Enter the left and right endpoints: ");
        a = sc.nextDouble();
        b = sc.nextDouble();
        width = b-a;
        System.out.println();
        double[] D = diff(C);
        for (double i = 0; i <= width; i += resolution) {
	System.out.println(b-a); 
        System.out.println(poly(C,a+i));  
 if (poly(C, (a+i)) * poly(C,(a+i+resolution)) < 0) {
		System.out.print(poly(C,(a+i))*poly(C,(a+i+resolution)));
                root = findRoot(C, a + i, a + i + resolution, tolerance);
                System.out.printf("Root found at " + "%.5f%n", root);
                done = true;
                continue;
                } else if (poly(D, (a+i)) * poly(D, (a + i + resolution)) < 0) {
                root = findRoot(D, a + i, a + i + resolution, tolerance);
                if (Math.abs(poly(C, root)) < threshold) {
                    System.out.printf("Root found at " + "%.5f%n", root);
                    done = true;
                    continue;
                }
            }
        }
        if (!done) {
            System.out.println("No roots were found in the specified range.");
        }
    }
    static double poly(double[] C, double x) {
        double sum = 0;
        for (int i = 0; i <C.length; i++) {
            sum += C[i] * (Math.pow(x,i));
        }
        return sum;
    }
    static double[] diff(double[] C) {
        int n = C.length - 1;
        double[] D = new double[n];
        for (int i = 0; i <n; i++) {
            D[i] = C[i] * (n - i);
        }
        return D;
    }
    static double findRoot(double[] C, double a, double b, double tolerance) {
        double root = 0, y;
        while(b - a > tolerance) {
            root = (a + b)/2.0;
            y = poly(C,root);
            if(poly(C,a) * y < 0) b = root;
            else a = root;
        }
        return root;
    }
}
