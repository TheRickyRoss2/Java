// Ric Rodiguez rirrodri
// $Id: jxref.java,v 1.17 2016-02-01 21:57:07-08 - - $

import java.io.*;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.System.*;

class jxref {
   private static final String STDIN_FILENAME = "-";
   private static final String REGEX = "\\w+([-'.:/]\\w+)*";
   private static final Pattern PATTERN = Pattern.compile(REGEX);
   static final int EXIT_SUCCESS=0;
   static final int EXIT_FAILURE=1;

   private static void xref_file (String filename, Scanner file){
      listmap map = new listmap();
      for (int linenr = 1; file.hasNextLine(); ++linenr) {
         String line = file.nextLine();
         Matcher match = PATTERN.matcher (line);
         while (match.find()) {
            String word = match.group();
            map.insert(word, linenr);
         }
      }
      String[] header= new String[32];
      for(int i =0;i<32;i++){
         header[i]=":";
      }
      printArray(header);
      out.printf("%s\n", filename);
      printArray(header);
      for(Entry<String, intqueue> entry: map){
         out.printf("%s %s%n", entry.getKey(), entry.getValue());
      }

   }
   private static void printArray(String[] array){
      for(int arrayPos=0;arrayPos<array.length;arrayPos++){
         out.printf("%s",array[arrayPos]);
      }
      out.println();
   }
   private static void xref_filename (String filename) {
      if (filename.equals (STDIN_FILENAME)) {
         xref_file (filename, new Scanner (System.in));
      }else {
         try {
            Scanner file = new Scanner (new File (filename));
            xref_file (filename, file);
            file.close();
         }catch (IOException error) {
            misc.warn (error.getMessage());
         }
      }
   }
   public static void main (String[] args) {
      if (args.length == 0) {
         xref_filename (STDIN_FILENAME);
      }else {
         for (String filename: args) {
            xref_filename (filename);
         }
      }
      exit (misc.exit_status);
   }
}
