package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Khanh Lam
 */
public class dictionaryModel {
    private Map<String,String> originalDict;
    private Map<String,String> currentDict;
    private ArrayList<String> history;
    private Random randomEngine;

    public dictionaryModel() {
        this.originalDict = new TreeMap<String, String>();
        this.currentDict = new TreeMap<String, String>();
        this.history = new ArrayList<String>();
        this.randomEngine = new Random();
    }

    public Map<String, String> getOriginalDict() {
        return originalDict;
    }

    public void setOriginalDict(Map<String, String> originalDict) {
        this.originalDict = originalDict;
    }

    public Map<String, String> getCurrentDict() {
        return currentDict;
    }

    public void setCurrentDict(Map<String, String> currentDict) {
        this.currentDict = currentDict;
    }

    public ArrayList<String> getHistory() {
        return this.history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }

    public void readFileSlang(){
        // Running from second times or above
        String filename = "database.txt";
        String line = "";
        String splitBy = "`";
        try {
            System.out.println("Read file database");

            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(filename));
            line = br.readLine(); // omit first line

            String key = "";
            String value = "";
            while ((line = br.readLine()) != null){
                if(line.contains(splitBy)){
                    String[] word = line.split(splitBy);
                    key = word[0];
                    value = word[1];
                }
                else{
                    value = value + "| " + line;
                    this.originalDict.replace(key, value);
                    this.currentDict.replace(key, value);
                    continue;
                }
                this.originalDict.put(key,value);
                this.currentDict.put(key,value);
            }
            br.close();
            //System.out.println("Import file successfully.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Running first time
        if(this.currentDict.isEmpty()){
            filename = "slang.txt";
            System.out.println("Read file slang");
            try {
                //parsing a CSV file into BufferedReader class constructor
                BufferedReader br = new BufferedReader(new FileReader(filename));
                line = br.readLine(); // omit first line

                String key = "";
                String value = "";
                while ((line = br.readLine()) != null){
                    if(line.contains(splitBy)){
                        String[] word = line.split(splitBy);
                        key = word[0].trim();
                        value = word[1];
                    }
                    else{
                        value = value + "| " + line;
                        this.originalDict.replace(key, value);
                        this.currentDict.replace(key, value);
                        continue;
                    }
                    this.originalDict.put(key,value);
                    this.currentDict.put(key,value);
                }
                br.close();
                //System.out.println("Import file successfully.");
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    /*
    Export database to new file for next run
     */
    public void exportDatabase(){
        String filename = "database.txt";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write("Slag`Meaning");
            bw.newLine();
            if(!this.currentDict.isEmpty()){
                for (Map.Entry<String, String> entry : this.currentDict.entrySet()) {
                    bw.write(entry.getKey() + "`" + entry.getValue() +"\n");
                    bw.flush();   // Flush the buffer and write all changes to the disk
                }
            }

            bw.close();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public String searchKey(String key){
        String value = null;
        if(this.currentDict.get(key) != null){
            value = this.currentDict.get(key);
        }
        this.addToHistory(key);
        return value;
    }

    public Map<String, String> searchDefinition(String value){
        Map<String, String> subMap = this.currentDict.entrySet().stream()
                .filter(x -> x.getValue().contains(value))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        this.addToHistory(value);
        return subMap;
    }

    public void addAWord(String key, String val){
        this.currentDict.put(key,val);
    }

    public void editAWord(String key, String newVal){
        this.currentDict.replace(key,newVal);
    }

    public void deleteAWord(String key){
        this.currentDict.remove(key);
    }

    public void reset(){
        this.currentDict.clear();
        this.currentDict.putAll(this.originalDict);
        // CLear history??
    }

    public ArrayList<String> randomAWord(){
        List<String> keys = new ArrayList<String>(this.currentDict.keySet());
        ArrayList<String> result = new ArrayList<String>();
        String randomKey = keys.get( randomEngine.nextInt(keys.size()) );

        result.add(randomKey);
        result.add(this.currentDict.get(randomKey));
        return result;
    }

    public void addToHistory(String word){
        this.history.add(word);
    }

    public void exportHistory(){
        String filename = "history.txt";
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
            bw.write("Keyword");
            bw.newLine();
            int size = this.history.size();
            for(int i = 0; i < size; i++){
                bw.write(this.history.get(i));
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }

    }

    public void readHistory() {
        String filename = "history.txt";
        String line = "";
        //String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(filename));
            line = br.readLine(); // omit first line

            while ((line = br.readLine()) != null){
                this.history.add(line);
            }
            br.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



}
