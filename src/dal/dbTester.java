package dal;

public class dbTester {


    public static void main(String[] args) {

        String val  = "0:27";
        String[] split = val.split(":");

        for (int i = 0; i< split.length; i++){
            System.out.println(split[i]);
        }

    }



}
