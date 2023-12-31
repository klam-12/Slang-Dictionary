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
    private Map<String,String> currentDict;
    private ArrayList<String> history;
    private Random randomEngine;

    public dictionaryModel() {
        this.currentDict = new HashMap<String, String>();
        this.history = new ArrayList<String>();
        this.randomEngine = new Random();
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

    /**
     * Read file to get data
     */
    public void loadDatabase(){
        // Running from second times or above
        String filename = "database.txt";
        String line = "";
        String splitBy = "`";
        try {
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
                    this.currentDict.replace(key, value);
                    continue;
                }
                this.currentDict.put(key,value);
            }
            br.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Running first time
        if(this.currentDict.isEmpty()){
            filename = "slang.txt";
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
                        this.currentDict.replace(key, value);
                        continue;
                    }
                    this.currentDict.put(key,value);
                }
                br.close();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * Export database to new file for next run
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

    /**
     * Search a word in dictionary
     * @param key is input from user
     * @return
     */
    public String searchKey(String key){
        if(key == null || key.isEmpty())
            return null;

        String value = null;
        key = key.trim();
        if(this.currentDict.get(key.toUpperCase()) != null){
            value = this.currentDict.get(key.toUpperCase());
        }
        this.addToHistory(key);
        return value;
    }

    /**
     * Check if this string contains the given word
     * @param str is string for checking
     * @param searchStr is string needed to find
     * @return true/false
     * Ref: https://stackoverflow.com/questions/14018478/string-contains-ignore-case
     */
    public boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

    /**
     * Search all words that have the given definition
     * @param value is the definition of the word
     * @return Collection of words
     */
    public Map<String, String> searchDefinition(String value){
        if(value == null || value.isEmpty())
            return null;
        Map<String, String> subMap = this.currentDict.entrySet().stream()
                .filter(x -> containsIgnoreCase(x.getValue(),value))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        this.addToHistory(value);
        return subMap;
    }

    /**
     * Add a new word to dictionary
     * @param key is the word
     * @param val is the definition
     */
    public void addAWord(String key, String val){
        if(key == null || key.isEmpty())
            return;
        key = key.trim();
        this.currentDict.put(key.toUpperCase(),val);
    }

    /**
     * Edit a word in the dictionary
     * @param key is the word
     * @param newVal is the definition
     */
    public void editAWord(String key, String newVal){
        if(key == null || key.isEmpty() || newVal == null || newVal.isEmpty())
            return;
        key = key.trim();
        this.currentDict.replace(key.toUpperCase(),newVal);
    }

    /**
     * Delete a word in the dictionary
     * @param key is the word
     */
    public void deleteAWord(String key){
        if(key == null || key.isEmpty())
            return;
        key = key.trim();
        this.currentDict.remove(key.toUpperCase());
    }

    /**
     * Reset the dictionary to the original version
     */
    public void reset(){
        this.currentDict.clear();

        String line = "";
        String splitBy = "`";

        String filename = "slang.txt";
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
                    this.currentDict.replace(key, value);
                    continue;
                }
                this.currentDict.put(key,value);
            }
            br.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Random a word
     * @return an ArrayList with 2 elements: Word and its definition
     */
    public ArrayList<String> randomAWord(){
        List<String> keys = new ArrayList<String>(this.currentDict.keySet());
        ArrayList<String> result = new ArrayList<String>();
        String randomKey = keys.get( randomEngine.nextInt(keys.size()) );

        result.add(randomKey);
        result.add(this.currentDict.get(randomKey));
        return result;
    }

    /**
     * Add a recently-search word to history for storage
     * @param word is the word that has just been searched
     */
    public void addToHistory(String word){
        int size = this.history.size();
        if(word == null || word.isEmpty()) {
            return;
        }

        if(word.equals(this.history.get(size-1)) || word.toUpperCase().equals(this.history.get(size-1))
            || word.toLowerCase().equals(this.history.get(size-1))){
            return;
        }

        this.history.add(word);
    }

    /**
     * Export history to file for storage
     */
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

    /**
     * Read data from history file
     */
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
