package view;

import controller.GamesListener;
import controller.ScreenListener;
import controller.SearchTabListener;
import model.dictionaryModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.function.BinaryOperator;
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

    // Game variables
    private JComboBox<String> comboBoxGame;
    private String gameMode;
    private JPanel gameContext;
    private JPanel preScreenGame;
    private ButtonGroup buttonGroupWord;
    private ButtonGroup buttonGroupDef;

    private String gameChoiceOfUser;
    private String gameAnswer;

    // History
    private JTextArea historyTextArea;


    public dictionaryView() {
        this.dictModel = new dictionaryModel();
        gameChoiceOfUser = "";
        gameAnswer = "";
        gameMode = "Find definition";

        // Load database
        //long start,end;
        //start = System.currentTimeMillis();
        dictModel.loadDatabase();
        //end = System.currentTimeMillis();
        //System.out.println("Time Millis: " + (end - start));
        dictModel.readHistory();

        this.init();
        this.setVisible(true);
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public JComboBox<String> getComboBoxGame() {
        return comboBoxGame;
    }

    public void setComboBoxGame(JComboBox<String> comboBoxGame) {
        this.comboBoxGame = comboBoxGame;
    }

    public ButtonGroup getButtonGroupWord() {
        return buttonGroupWord;
    }

    public void setButtonGroupWord(ButtonGroup buttonGroupWord) {
        this.buttonGroupWord = buttonGroupWord;
    }

    public ButtonGroup getButtonGroupDef() {
        return buttonGroupDef;
    }

    public void setButtonGroupDef(ButtonGroup buttonGroupDef) {
        this.buttonGroupDef = buttonGroupDef;
    }

    public String getGameChoiceOfUser() {
        return gameChoiceOfUser;
    }

    public void setGameChoiceOfUser(String gameChoiceOfUser) {
        this.gameChoiceOfUser = gameChoiceOfUser;
    }

    public String getGameAnswer() {
        return gameAnswer;
    }

    public void setGameAnswer(String gameAnswer) {
        this.gameAnswer = gameAnswer;
    }

    public JTextArea getHistoryTextArea() {
        return historyTextArea;
    }

    public void setHistoryTextArea(JTextArea historyTextArea) {
        this.historyTextArea = historyTextArea;
    }

    private void init(){
        ScreenListener screenListener = new ScreenListener(this);
        this.setTitle("Slang Dictionary");
        this.setSize(700,600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(screenListener);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel dictionary = new JPanel(new GridLayout(1, 1));
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(headingText);

        JComponent panel1 = searchScreen();
        tabbedPane.addTab("Search",null , panel1);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JComponent panel2 = historyScreen();
        tabbedPane.addTab("History", null, panel2);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        JComponent panel3 = gameScreen();
        tabbedPane.addTab("Games", null, panel3);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        // add tabbed pane listener
        tabbedPane.addChangeListener(screenListener);
        
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

        JButton randWord = new JButton("Random");
        randWord.setFont(headingText);
        randWord.addActionListener(searchListener);

        modifyArea.setLayout(new GridLayout(10,1,10,10));
        modifyArea.add(addBtn);
        modifyArea.add(delBtn);
        modifyArea.add(editBtn);
        modifyArea.add(resetBtn);
        modifyArea.add(randWord);

        // Show Screen
        JPanel searchSrc = new JPanel();
        searchSrc.setLayout(new BorderLayout(10,10));
        searchSrc.add(searchArea, BorderLayout.NORTH);
        searchSrc.add(resultArea,BorderLayout.CENTER);
        searchSrc.add(modifyArea,BorderLayout.EAST);

        return searchSrc;
    }

    protected JComponent historyScreen(){
        historyTextArea = new JTextArea();
        historyTextArea.setFont(normalText);

        ArrayList<String> history = this.dictModel.getHistory();
        int sizeHis = history.size();
        StringBuilder listHistory = new StringBuilder();
        for(int i = 0; i < sizeHis; i++){
            listHistory.append(history.get(i)).append("\n");
        }
        historyTextArea.setText(listHistory.toString());

        JScrollPane historyScroll = new JScrollPane(historyTextArea
                ,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
                ,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JPanel hisSrc = new JPanel();
        hisSrc.setLayout(new BorderLayout());
        hisSrc.add(historyScroll,BorderLayout.CENTER);
        return hisSrc;
    }

    protected JComponent gameScreen(){
        GamesListener gameListener = new GamesListener(this);
        // ****** Header
        JLabel title = new JLabel("GAME MODE");
        title.setFont(headingText);

        String[] gameModes = {"Find definition", "Find word"};
        comboBoxGame = new JComboBox<String>(gameModes);
        comboBoxGame.setFont(headingText);
        // set initial is Find definition
        comboBoxGame.setSelectedItem(0);
        // add listener
        comboBoxGame.addActionListener(gameListener);

        JPanel header = new JPanel();
        header.setLayout(new FlowLayout(FlowLayout.CENTER,10,40));
        header.add(title);
        header.add(comboBoxGame);

        // ****** Body - Context
          gameContext = createGameMode(gameModes[0]);

        // ****** Footer
        JButton submitBtn = new JButton("Submit");
        submitBtn.setFont(normalText);
        submitBtn.addActionListener(gameListener);

        JPanel footer = new JPanel();
        footer.setLayout(new GridLayout(2,1));
        footer.add(submitBtn);


        preScreenGame = new JPanel(new BorderLayout(10,10));
        preScreenGame.add(header,BorderLayout.NORTH);
        preScreenGame.add(gameContext,BorderLayout.CENTER);
        preScreenGame.add(footer,BorderLayout.SOUTH);

        JPanel screen = new JPanel();
        screen.add(preScreenGame);

        return screen;
    }

    protected JPanel gameFinDefinition(){
        Random randEngine = new Random();
        ArrayList<String> wordNameForAnswer = this.dictModel.randomAWord();
        ArrayList<String> wordNameForOption1 = this.dictModel.randomAWord();
        ArrayList<String> wordNameForOption2 = this.dictModel.randomAWord();
        ArrayList<String> wordNameForOption3 = this.dictModel.randomAWord();

        // set the answer
        this.setGameAnswer(wordNameForAnswer.get(1));

        ArrayList<String> listOfOptions = new ArrayList<String>();
        listOfOptions.add(wordNameForAnswer.get(1));
        listOfOptions.add(wordNameForOption1.get(1));
        listOfOptions.add(wordNameForOption2.get(1));
        listOfOptions.add(wordNameForOption3.get(1));
        Collections.shuffle(listOfOptions);

        // Get the word
        JLabel word = new JLabel(wordNameForAnswer.get(0));
        word.setFont(headingText);
        JPanel wordSpace = new JPanel();
        wordSpace.setLayout(new FlowLayout());
        wordSpace.add(word);


        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options,BoxLayout.Y_AXIS));
        options.setFont(normalText);

        JRadioButton opt1=new JRadioButton(listOfOptions.get(0));
        JRadioButton opt2=new JRadioButton(listOfOptions.get(1));
        JRadioButton opt3=new JRadioButton(listOfOptions.get(2));
        JRadioButton opt4=new JRadioButton(listOfOptions.get(3));

        buttonGroupWord=new ButtonGroup();
        buttonGroupWord.add(opt1);buttonGroupWord.add(opt2);
        buttonGroupWord.add(opt3); buttonGroupWord.add(opt4);
        options.add(opt1); options.add(opt2); options.add(opt3); options.add(opt4);

        JPanel games = new JPanel();
        games.setLayout(new BorderLayout());
        games.add(wordSpace,BorderLayout.NORTH);
        games.add(options, BorderLayout.CENTER);

        return games;
    }

    protected JPanel gameFindWord(){
        Random randEngine = new Random();
        ArrayList<String> wordNameForAnswer = this.dictModel.randomAWord();
        ArrayList<String> wordNameForOption1 = this.dictModel.randomAWord();
        ArrayList<String> wordNameForOption2 = this.dictModel.randomAWord();
        ArrayList<String> wordNameForOption3 = this.dictModel.randomAWord();

        // set the answer
        this.setGameAnswer(wordNameForAnswer.get(0));

        ArrayList<String> listOfOptions = new ArrayList<String>();
        listOfOptions.add(wordNameForAnswer.get(0));
        listOfOptions.add(wordNameForOption1.get(0));
        listOfOptions.add(wordNameForOption2.get(0));
        listOfOptions.add(wordNameForOption3.get(0));
        Collections.shuffle(listOfOptions);

        // Get the definition
        JLabel word = new JLabel(wordNameForAnswer.get(1));
        word.setFont(headingText);
        JPanel wordSpace = new JPanel();
        wordSpace.setLayout(new FlowLayout());
        wordSpace.add(word);


        JPanel options = new JPanel();
        options.setLayout(new BoxLayout(options,BoxLayout.Y_AXIS));
        options.setFont(normalText);

        JRadioButton opt1=new JRadioButton(listOfOptions.get(0));
        JRadioButton opt2=new JRadioButton(listOfOptions.get(1));
        JRadioButton opt3=new JRadioButton(listOfOptions.get(2));
        JRadioButton opt4=new JRadioButton(listOfOptions.get(3));

        buttonGroupDef=new ButtonGroup();
        buttonGroupDef.add(opt1);buttonGroupDef.add(opt2);
        buttonGroupDef.add(opt3); buttonGroupDef.add(opt4);
        options.add(opt1); options.add(opt2); options.add(opt3); options.add(opt4);

        JPanel games = new JPanel();
        games.setLayout(new BorderLayout());
        games.add(wordSpace,BorderLayout.NORTH);
        games.add(options, BorderLayout.CENTER);

        return games;
    }

    protected JPanel createGameMode(String mode){
        JPanel gamePanel;
        if(mode.equals("Find definition"))
            gamePanel = this.gameFinDefinition();
        else
            gamePanel = this.gameFindWord();
        return gamePanel;
    }

    public String getTextInput(){
        return inputField.getText();
    }

    public void searchWord(){
        String input = inputField.getText();
        String result = this.dictModel.searchKey(input.trim());

        if(result == null){
            WordResultArea.setText("Not found word: " + input);
        }
        else {
            WordResultArea.setText(input.toUpperCase() + " - " + result + "\n");
        }

    }

    public void searchDef(){
        String input = inputField.getText();
        Map<String, String> resultMap = this.dictModel.searchDefinition(input);
        if(resultMap == null){
            WordResultArea.setText("Not found definition: " + input);
            return;
        }
        String result = "";
        result = resultMap.keySet().stream()
                .map(key -> key + " -> " + resultMap.get(key))
                .collect(Collectors.joining("\n"));

        if(result.isEmpty()){
            WordResultArea.setText("Not found definition: " + input);
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
        if(wordName == null){
            return;
        }

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

        if(wordName == null){
            return;
        }

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

        if(wordName == null){
            return;
        }
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

    public void randomAWord(){
        ArrayList<String> wordName = this.dictModel.randomAWord();
        JOptionPane.showMessageDialog(
                this,
                "A WORD FOR TODAY \n" + wordName.get(0) + " - " + wordName.get(1),
                "Random",
                JOptionPane.INFORMATION_MESSAGE );

    }

    public void setGameScreen(String mode){
        preScreenGame.remove(gameContext);
        gameContext = createGameMode(mode);
        preScreenGame.add(gameContext,BorderLayout.CENTER);
        this.setGameMode(mode);
    }

    public void checkGame(String mode){
        if (this.getGameChoiceOfUser().equals(this.getGameAnswer())){
            JOptionPane.showMessageDialog(
                    this,
                    "You're correct. Let's move to the next question",
                    "Game",
                    JOptionPane.INFORMATION_MESSAGE );
            this.setGameScreen(mode);
        }
        else{
            JOptionPane.showMessageDialog(
                    this,
                    "You're wrong. Let's try again",
                    "Game",
                    JOptionPane.ERROR_MESSAGE );
        }
    }

    public void reloadHistoryTextArea(){
        ArrayList<String> history = this.dictModel.getHistory();
        if(history.isEmpty()){
            historyTextArea.setText("No history");
            return;
        }

        int sizeHis = history.size();
        StringBuilder listHistory = new StringBuilder();
        for(int i = 0; i < sizeHis; i++){
            listHistory.append(history.get(i)).append("\n");
        }
        historyTextArea.setText(listHistory.toString());
    }

    public void saveHistory(){
        this.dictModel.exportHistory();
    }

    public void saveDatabase(){
        this.dictModel.exportDatabase();
    }
}
