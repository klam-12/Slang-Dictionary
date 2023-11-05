package tester;

import model.dictionaryModel;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Khanh Lam
 */
public class Test {
    public static void main(String[] args) {

        dictionaryModel dictModel = new dictionaryModel();
        dictModel.readFileSlang();
        long start, end;

        start = System.currentTimeMillis();
        dictModel.searchDefinition("you");
        //String value = dictModel.searchKey("SUP");

        end = System.currentTimeMillis();   // start lấy thời gian theo millisecond

        //System.out.println(value);
        System.out.println("Time Millis: " + (end - start));


    }
}
