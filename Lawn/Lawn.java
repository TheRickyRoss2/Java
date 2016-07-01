// Lawn.java
// Ric Rodriguez
// rirrodri
// lab3
// calculate mowing time given lawn dimensions and rate

import java.util.Scanner;

class Lawn{

   public static void main( String[] args ){

      double height, lotLength, lotWidth, houseLength, houseWidth, lotArea, houseArea, lawnArea, mowingRate, mowingTime;
      Scanner sc = new Scanner(System.in);
	  
      // User inputs lawn dimensions
      System.out.print("");
      lotLength = sc.nextDouble();
      lotWidth = sc.nextDouble();
      System.out.print("");
      houseLength = sc.nextDouble();
      houseWidth = sc.nextDouble();
	  
      // Calculate lawn area
      lotArea = lotLength*lotWidth;
      houseArea = houseLength*houseWidth;
      lawnArea = lotArea-houseArea;
      System.out.print("The lawn area is ");
      System.out.println(lawnArea+" square feet.");
	  
      // Calculate mowing time given moving rate
      System.out.print("");
      mowingRate = sc.nextDouble();
      mowingTime = lawnArea/mowingRate;
     
      // Rounding and grammar correction
      double seconds = mowingTime;
      int h, m, s;
      s = (int) Math.round(seconds);
      m = s/60;
      s = s%60;  // same as s %= 60
      h = m/60;
      m = m%60;  // same as m %= 60
      System.out.println("The mowing time is " + h + (h==1?" hour":" hours") + " " + m + (m==1?" minute":" minutes") + " " + s + (s==1?" second.":" seconds."));
   }
}
