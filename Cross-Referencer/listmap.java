// $Id: listmap.java,v 1.11 2016-02-01 20:00:27-08 - - $

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import static java.lang.System.*;

class listmap implements Iterable<Entry<String,intqueue>> {

   private class node implements Entry<String,intqueue> {
      String key;
      intqueue queue = new intqueue();
      node link;
      public String getKey() {
         return key;
      }
      public intqueue getValue() {
         return queue;
      }
      public intqueue setValue (intqueue queue) {
         throw new UnsupportedOperationException();
      }
   }
   private node head = null;

   public listmap() {
      // Not needed, since head defaults to null anyway.
   }

   public void insert (String key, int linenr) {
      if(head==null){
         head = new node();
         head.key=key;
         head.queue.insert(linenr);
         return;
      }
      node prev=null;
      node curr=head;
      int compareNode=1;
      while(curr!=null){
         compareNode=curr.key.compareTo(key);
         if(compareNode>=0){
            break;
         }
         prev=curr;
         curr=curr.link;
      }
      if(compareNode==0){
         curr.queue.insert(linenr);
      }else{
         node tempNode=new node();
         tempNode.key=key;
         tempNode.queue.insert(linenr);
         tempNode.link=curr;
         if(prev==null){
            head=tempNode;
         }else{
            prev.link=tempNode;
         }

      }
   }

   public Iterator<Entry<String,intqueue>> iterator() {
      return new iterator();
   }

   private class iterator
           implements Iterator<Entry<String,intqueue>> {
      node curr = head;

      public boolean hasNext() {
         return curr != null;
      }

      public Entry<String,intqueue> next() {
         if (curr == null) throw new NoSuchElementException();
         node next = curr;
         curr = curr.link;
         return next;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

   }

}
