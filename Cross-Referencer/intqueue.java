// Ric Rodriguez rirrodri
// $Id: intqueue.java,v 1.6 2016-02-01 19:43:28-08 - - $

import java.util.Iterator;
import java.util.NoSuchElementException;

class intqueue implements Iterable<Integer> {

   private class node {
      int linenr;
      node link;
   }
   private int count = 0;
   private node front = null;
   private node rear = null;

   public void insert (int number) {
      ++count;
      node tempNode=new node();
      tempNode.linenr=number;
      tempNode.link=null;
      if(rear==null){
         front=tempNode;
      }else{
         rear.link=tempNode;
      }
      rear=tempNode;
   }

   public boolean empty() {
      return count == 0;
   }

   public int getcount() {
      return count;
   }

   public Iterator<Integer> iterator() {
      return new iterator();
   }

   private class iterator implements Iterator<Integer> {
      node curr = front;

      public boolean hasNext() {
         return curr != null;
      }

      public Integer next() {
         if (curr == null) throw new NoSuchElementException();
         Integer next = curr.linenr;
         curr = curr.link;
         return next;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }
   }
      public String toString(){
         String output= String.format("[%s]", count);
         for(int lineNumber : this){
            output+=" "+lineNumber;
         }
         return output;
      }

}