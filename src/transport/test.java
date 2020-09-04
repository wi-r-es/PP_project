package transport;

import hr.Customer;
import hr.ICustomer;
import hr.IDestination;
import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class test {
    public static void main(String[] args){

        testitem testitem = new testitem("asdadadd123", "Item para venda");
        testitem testitem1 = new testitem("asdadadd321", "Item para ven1da");
        testitem[] arrraytest = {testitem, testitem1};

            try {
                //Serialize an object to a specific format that can be stored.
                //String items = JSONArray.toJSONString(Arrays.asList(arrraytest));

                FileWriter writer = new FileWriter("/home/wires/Documents/PP_project/test.txt",true);
                writer.write("[");
                for (int i = 0; i < arrraytest.length; i++) {
                    writer.write(arrraytest[i].getObject().toString());
                    if ( i+1 < arrraytest.length){
                    writer.write(",");}

                }
                writer.write("]");
                writer.close();
            }catch (IOException e){
                System.err.println(e.getMessage());
            }
            System.out.println("hello");


    }
}
