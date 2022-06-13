package it.aizoon.owasp4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JavaSerializationExample {

  public static void main(String[] args) throws Exception{

    //write file
    FileOutputStream fileOutputStream = new FileOutputStream("evil.bin");
    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
    objectOutputStream.writeObject(new MyEvilClass());
    objectOutputStream.close();


    //read file
    FileInputStream fileIn = new FileInputStream("evil.bin");
    ObjectInputStream objectIn = new ObjectInputStream(fileIn);
    Object obj = objectIn.readObject();
    System.out.println("The Object has been read from the file");
    objectIn.close();
  }



  public static class MyEvilClass implements Serializable {

    private String message;


    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
      int size = aInputStream.readInt();
      List<Object> list = new ArrayList<Object>(size);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws ClassNotFoundException, IOException {
      objectOutputStream.writeInt(Integer.MAX_VALUE - 8);
    }
  }
}
