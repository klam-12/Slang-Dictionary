package view;

import controller.MenuListener;
import model.dictionaryModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author Khanh Lam
 */
public class dictionaryView extends JFrame {
    private dictionaryModel dictModel;
    private CardLayout cardLayout;
    private JPanel cards;

    public dictionaryView() {
        this.dictModel = new dictionaryModel();
        this.init();
        this.setVisible(true);
    }

    public void init(){
        this.setTitle("Slang Dictionary");
        this.setSize(500,400);
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

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        MenuListener menuListener = new MenuListener(this);

        JMenu searchMenu = new JMenu("Search");
        searchMenu.addMenuListener(menuListener);

        JMenu historyMenu = new JMenu("History");
        historyMenu.addMenuListener(menuListener);

        JMenu randMenu = new JMenu("Random");
        JMenu gamesMenu = new JMenu("Games");
        menuBar.add(searchMenu);
        menuBar.add(historyMenu);
        menuBar.add(randMenu);
        menuBar.add(gamesMenu);
        this.setJMenuBar(menuBar);

        JPanel search = searchScreen();
        JPanel history = historyScreen();

        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(search, "Search");
        cards.add(history, "History");

        // Add your card container to the frame
        Container pane = this.getContentPane();
        pane.add(cards, BorderLayout.CENTER);
        cardLayout = (CardLayout)(cards.getLayout());
    }

    public JPanel searchScreen(){
        // Text Container
        JPanel searchArea = new JPanel();
        JTextField inputField = new JTextField(20);
        JButton searchKey = new JButton("Search word");
        JButton searchDef = new JButton("Search definition");
        searchArea.setLayout(new FlowLayout());
        searchArea.add(inputField);
        searchArea.add(searchKey);
        searchArea.add(searchDef);

        // Result Area
        JPanel resultArea = new JPanel();
        JLabel titlesResult = new JLabel("Word - Meaning");
        JTextArea showResult = new JTextArea();
        showResult.setText("YUP - Yeppp| Approval\n"
                + "SUP - What's up \n");

        resultArea.setLayout(new BoxLayout(resultArea,BoxLayout.Y_AXIS));
        resultArea.add(titlesResult);
        resultArea.add(showResult);

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
        JButton delBtn = new JButton("Delete");
        JButton editBtn = new JButton("Edit");
        JButton resetBtn = new JButton("Reset");
        modifyArea.setLayout(new BoxLayout(modifyArea,BoxLayout.PAGE_AXIS));
        modifyArea.add(addBtn);
        modifyArea.add(delBtn);
        modifyArea.add(editBtn);
        modifyArea.add(resetBtn);


        // Program
        JPanel searchSrc = new JPanel();
        searchSrc.setLayout(new BorderLayout(10,10));
        searchSrc.add(searchArea, BorderLayout.NORTH);
        searchSrc.add(resultArea,BorderLayout.CENTER);
        searchSrc.add(modifyArea,BorderLayout.EAST);
        return searchSrc;
    }

    public JPanel historyScreen(){
        JTextField history = new JTextField();
        JTextArea showResult = new JTextArea();
        showResult.setText("YUP - Yeppp| Approval\n"
                + "SUP - What's up \n");

        history.setLayout(new BoxLayout(history,BoxLayout.Y_AXIS));
        history.add(showResult);

        JPanel hisSrc = new JPanel();
        hisSrc.setLayout(new BorderLayout());
        hisSrc.add(history,BorderLayout.CENTER);
        return hisSrc;
    }

    public void setSearchSrc(){
        cardLayout.show(cards,"Search");
    }

    public void setHistorySrc(){
        cardLayout.show(cards,"History");
    }




}
