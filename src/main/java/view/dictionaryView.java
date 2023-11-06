package view;

import controller.SearchTabListener;
import model.dictionaryModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Khanh Lam
 */
public class dictionaryView extends JFrame {
    private dictionaryModel dictModel;
    private Font normalText = new Font("Arial",Font.PLAIN,14);
    private Font headingText = new Font("Arial",Font.BOLD,14);

    private JTextField inputField;
    private JTextArea WordResultArea;

    public dictionaryView() {
        this.dictModel = new dictionaryModel();

        // Load database
        dictModel.readFileSlang();
        dictModel.readHistory();

        this.init();
        this.setVisible(true);
    }

    public void init(){
        this.setTitle("Slang Dictionary");
        this.setSize(700,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        // Menu Container
//        JPanel menu = new JPanel();
//        JButton searchBtn = new JButton("Search");
//        JButton historyBtn = new JButton("History");
//        JButton randomBtn = new JButton("Random");
//        JButton gamesBtn = new JButton("Games");
//
//        menu.setLayout(new FlowLayout());
//        menu.setBackground(Color.PINK);
//        menu.add(searchBtn);
//        menu.add(historyBtn);
//        menu.add(randomBtn);
//        menu.add(gamesBtn);

        JPanel dictionary = new JPanel(new GridLayout(1, 1));
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(headingText);

        JComponent panel1 = searchScreen();
        tabbedPane.addTab("Search",null , panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = historyScreen();
        // check type here
        System.out.println(panel2.getComponents()[0] instanceof JTextArea);

        tabbedPane.addTab("History", null, panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent panel3 = gameScreen();
        tabbedPane.addTab("Games", null, panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        //Add the tabbed pane to this panel.
        dictionary.add(tabbedPane);

        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        this.setLayout(new BorderLayout());
        this.add(dictionary, BorderLayout.CENTER);
    }

    protected JComponent searchScreen(){
        SearchTabListener searchListener = new SearchTabListener(this);
        // Text Container
        JPanel searchArea = new JPanel(false);
        inputField = new JTextField(20);
        inputField.setFont(normalText);

        JButton searchKey = new JButton("Search word");
        searchKey.setFont(headingText);
        searchKey.addActionListener(searchListener);

        JButton searchDef = new JButton("Search definition");
        searchDef.setFont(headingText);

        searchDef.addActionListener(searchListener);

        searchArea.setLayout(new FlowLayout());
        searchArea.add(inputField);
        searchArea.add(searchKey);
        searchArea.add(searchDef);

        // Result Area
        JPanel resultArea = new JPanel();
        JLabel titlesResult = new JLabel("Word - Meaning");
        titlesResult.setFont(headingText);

        WordResultArea = new JTextArea();
        WordResultArea.setFont(normalText);

        JScrollPane resultScroll = new JScrollPane(WordResultArea
                ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                ,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


        resultArea.setLayout(new BoxLayout(resultArea,BoxLayout.Y_AXIS));
        resultArea.add(titlesResult);
        resultArea.add(resultScroll);

//        String[][] data={ {"101","Amit"},
//                {"102","Jai"},
//                {"101","Sachin"}};
//        String[] titles = {"Name", "Meaning"};
//        JTable showResult = new JTable(data,titles);
//        JScrollPane scrollPane = new JScrollPane(showResult);
//        //showResult.add(scrollPane);
//        showResult.setFillsViewportHeight(true);


        // Modify Area
        JPanel modifyArea = new JPanel();
        JButton addBtn = new JButton("Add");
        addBtn.setFont(headingText);
        addBtn.addActionListener(searchListener);

        JButton delBtn = new JButton("Delete");
        delBtn.setFont(headingText);
        delBtn.addActionListener(searchListener);

        JButton editBtn = new JButton("Edit");
        editBtn.setFont(headingText);
        editBtn.addActionListener(searchListener);

        JButton resetBtn = new JButton("Reset");
        resetBtn.setFont(headingText);
        resetBtn.addActionListener(searchListener);

        modifyArea.setLayout(new BoxLayout(modifyArea,BoxLayout.PAGE_AXIS));
        modifyArea.add(addBtn);
        modifyArea.add(delBtn);
        modifyArea.add(editBtn);
        modifyArea.add(resetBtn);

        // Show Screen
        JPanel searchSrc = new JPanel();
        searchSrc.setLayout(new BorderLayout(10,0));
        searchSrc.add(searchArea, BorderLayout.NORTH);
        searchSrc.add(resultArea,BorderLayout.CENTER);
        searchSrc.add(modifyArea,BorderLayout.EAST);
        return searchSrc;
    }

    protected JComponent historyScreen(){
        //JTextField history = new JTextField();
        JTextArea showResult = new JTextArea();
        showResult.setFont(normalText);

        ArrayList<String> history = this.dictModel.getHistory();
        int sizeHis = history.size();
        StringBuilder listHistory = new StringBuilder();
        for(int i = 0; i < sizeHis; i++){
            listHistory.append(history.get(i)).append("\n");
        }
        showResult.setText(listHistory.toString());

        JPanel hisSrc = new JPanel();
        hisSrc.setLayout(new BorderLayout());
        hisSrc.add(showResult,BorderLayout.CENTER);
        return hisSrc;
    }

    protected JComponent gameScreen(){
        JTextField games = new JTextField();
        JTextArea showResult = new JTextArea();
        showResult.setText("YUP - Yeppp| Approval\n"
                + "SUP - What's up \n");

        games.setLayout(new BoxLayout(games,BoxLayout.Y_AXIS));
        games.add(showResult);

        JPanel hisSrc = new JPanel();
        hisSrc.setLayout(new BorderLayout());
        hisSrc.add(games,BorderLayout.CENTER);
        return games;
    }

    public String getTextInput(){
        return inputField.getText();
    }

    public void searchWord(){
        String input = inputField.getText();
        String result = this.dictModel.searchKey(input);

        if(result == null){
            WordResultArea.setText("Not found " + input);
        }
        else {
            WordResultArea.setText(input + " - " + result + "\n");
        }
    }

    // not finished
    public void searchDef(){
        String input = inputField.getText();
        Map<String, String> resultMap = this.dictModel.searchDefinition(input);
        String result = "";
        result = resultMap.keySet().stream()
                .map(key -> key + " -> " + resultMap.get(key))
                .collect(Collectors.joining("\n"));

        if(result.isEmpty()){
            WordResultArea.setText("Not found " + input);
        }
        else {
            WordResultArea.setText(result);
        }
    }

    public void addAWord(){
        String wordName = (String) JOptionPane.showInputDialog(
          this,
                "Input the word",
                "Add a Word",
                JOptionPane.PLAIN_MESSAGE);

        String wordDefinition = (String) JOptionPane.showInputDialog(
                this,
                "Input the definition",
                "Add a Word",
                JOptionPane.PLAIN_MESSAGE);

        String found = this.dictModel.searchKey(wordName);
        if(found == null){
            // Add successfully
            this.dictModel.addAWord(wordName,wordDefinition);
            JOptionPane.showMessageDialog(
                    this,
                    "Add a new word successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE );
        }
        else {
            Object[] options = { "Overwrite", "Duplicate","Cancel" };
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "This word has already existed. Do you want to?",
                    "Warning",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                    null,
                    options, options[0]);

           if (choice == 0) {
               // Overwrite
               this.dictModel.editAWord(wordName,wordDefinition);
           } else // Cancel
               if (choice == 1){
               // Duplicate
               this.dictModel.addAWord(wordName + "_copy", wordDefinition);
           }
           else return;
        }
    }

    public void deleteAWord(){
        String wordName = (String) JOptionPane.showInputDialog(
                this,
                "Input the word",
                "Delete a Word",
                JOptionPane.PLAIN_MESSAGE);

        String found = this.dictModel.searchKey(wordName);
        if(found == null){
            // Not found
            JOptionPane.showMessageDialog(
                    this,
                    "Can't find the word",
                    "Error",
                    JOptionPane.ERROR_MESSAGE );
        }
        else {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this word?");
            if(choice == JOptionPane.YES_OPTION){
                this.dictModel.deleteAWord(wordName);
            }
        }
    }

    public void editAWord(){
        String wordName = (String) JOptionPane.showInputDialog(
                this,
                "Input the word",
                "Edit a Word",
                JOptionPane.PLAIN_MESSAGE);

        String found = this.dictModel.searchKey(wordName);
        if(found == null){
            // Not found
            JOptionPane.showMessageDialog(
                    this,
                    "Can't find the word",
                    "Error",
                    JOptionPane.ERROR_MESSAGE );
        }
        else {
            String wordDefinition = (String) JOptionPane.showInputDialog(
                    this,
                    "Input the definition",
                    "Edit a Word",
                    JOptionPane.PLAIN_MESSAGE);

            // Edit successfully
            this.dictModel.editAWord(wordName,wordDefinition);
            JOptionPane.showMessageDialog(
                    this,
                    "Edit successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE );
        }
    }

    public void reset(){
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to reset the dictionary?");
        if(choice == JOptionPane.YES_OPTION){
            this.dictModel.reset();
        }
    }
}
