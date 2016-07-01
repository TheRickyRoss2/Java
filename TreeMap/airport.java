// Ric Rodriguez rirrodri
// $Id: airport.java,v 1.1 2016-02-16 20:49:29-08 - - $
//
// Starter code for the airport utility.
//

import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

class airport {
   private static final String STDIN_FILENAME = "-";
   private static final int EXIT_SUCCESS=0;
   private static final int EXIT_FAILURE=1;
   private static int exit_status=EXIT_SUCCESS;

   public static treemap load_database (String database_name) {
      treemap tree = new treemap ();
      try {
         Scanner database = new Scanner (new File (database_name));
         for (int linenr = 1; database.hasNextLine(); ++linenr) {
            String line = database.nextLine();
            if (line.matches ("^\\s*(#.*)?$")) continue;
            String[] keyvalue = line.split (":");
            if (keyvalue.length != 2) {
               exit_status=EXIT_FAILURE;

         err.printf("%s, %s:%d: invalid line",
         "airport.java", database_name, linenr);
               continue;
            }
            tree.put (keyvalue[0], keyvalue[1]);
         }
         database.close();
      }catch (IOException error) {
         exit_status=EXIT_FAILURE;
               err.printf("%s: %s%n",
                  "airport.java", error.getMessage());
         exit(exit_status);
      }
      return tree;
   } 

   public static void printUsage(){
      out.printf("%s\n", "airport [-d] database");
      exit(1);
   }

   public static void main (String[] args) {
      boolean debugFlag=false;
      String databaseName=null;
      if (args.length > 2){
         printUsage();
      }
      else if (args.length == 2){
         if(args[0].compareTo("-d")!= 0){
            printUsage();
         }
         debugFlag = true;
         databaseName = args[1];
      }
      else if(args.length==1) {
         databaseName = args[0];
      }
      else printUsage();
      treemap tree = load_database (databaseName);
      Scanner stdin = new Scanner (in);
      while (stdin.hasNextLine()) {
         String airport = stdin.nextLine().toUpperCase().trim();
         String airport_name = tree.get (airport);
         if (airport_name == null) {
            out.printf ("%s: no such airport%n", airport);
         }else {
            out.printf ("%s = %s%n", airport, airport_name);
         }
      }
      tree.debug_tree ();
      exit (exit_status);
   }
}
