package tester;

import model.dictionaryModel;
import view.TabbedPaneDemo;
import view.dictionaryView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Khanh Lam
 */
public class Test {
    public static void main(String[] args) {
/*
        dictionaryModel dictModel = new dictionaryModel();
        dictModel.readFileSlang();
        long start, end;

        dictModel.readHistory();
        System.out.println(dictModel.getHistory());
        start = System.currentTimeMillis();
        dictModel.searchDefinition("stay");
        String value = dictModel.searchKey("YAY");

        end = System.currentTimeMillis();   // start lấy thời gian theo millisecond

        System.out.println(value);
        System.out.println("Time Millis: " + (end - start));

        dictModel.addToHistory("good");
        dictModel.addToHistory("YAY");
        dictModel.exportHistory();
 */

        dictionaryView dictView = new dictionaryView();

    }
}
