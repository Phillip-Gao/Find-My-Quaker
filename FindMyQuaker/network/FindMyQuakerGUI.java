package FindMyQuaker.network;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FindMyQuakerGUI extends JPanel {

    private FindMyQuaker fmq;

    public FindMyQuakerGUI() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        setFocusable(true);
        // initializes model for the social network and parser
        fmq = new FindMyQuaker();


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                repaint();
            }
        });
    }

   //Displays a properly formatted list of sports
    public void listSports() {
        List<String> sports = fmq.listSports();
        String list = "";
        for (String s : sports) {
            list += s + "\n";
        }

        JTextArea textArea = new JTextArea(list);
        JScrollPane scrollPane = new JScrollPane(textArea);
        // textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(300, 500));

        JOptionPane.showMessageDialog(
                null, scrollPane, "List of sports",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    //Displays a roster for a given sport
    public void listRoster() {
        JFrame frame = new JFrame();
        String sport = (String) JOptionPane
                .showInputDialog(frame, "Enter sport: ");
        HashMap<String, HashMap<String, String>>
                players = fmq.getRoster(sport);
        StringBuilder list = new StringBuilder();

        for (String player : players.keySet()) {
            HashMap<String, String> playerInfo = players.get(player);
            list.append(player).append("\n");
            for (String category : playerInfo.keySet()) {
                list.append(category).append(": ")
                        .append(playerInfo.get(category)).append("\n");
            }
            list.append("\n");
        }

        JTextArea textArea = new JTextArea(list.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(700, 500));

        JOptionPane.showMessageDialog(
                null, scrollPane, sport + " roster",
                JOptionPane.PLAIN_MESSAGE
        );
    }
    //Displays the profile of an athlete
    public void findAthlete() {
        JFrame frame = new JFrame();
        String name = (String) JOptionPane
                .showInputDialog(frame, "Enter an athlete's name: ");

        HashMap<String, String> athleteInfo = fmq.findAthleteInfo(name);
        StringBuilder list = new StringBuilder(name + "\n");

        for (String category : athleteInfo.keySet()) {
            list.append(category).append(": ")
                    .append(athleteInfo.get(category)).append("\n");
        }

        JTextArea textArea = new JTextArea(list.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(500, 200));

        JOptionPane.showMessageDialog(
                null, scrollPane, name + "'s profile",
                JOptionPane.PLAIN_MESSAGE
        );
    }
    //Matches athletes based on user inputted traits
    public void matchAthlete() {
        JFrame frame = new JFrame();
        String message = "If you don't want to input a certain trait, press enter.\n";
        String year = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter academic year (ex: Freshman): "
        );
        String height = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter height (ex: 6'0\"): "
        );
        String sport = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter sport: "
        );
        String highSchool = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter high school: "
        );
        String hometown = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter hometown (ex: Padua, Italy): "
        );

        StringBuilder list = new StringBuilder();

        HashMap<String, String> traits = new HashMap<>();
        if (!year.isEmpty()) {
            traits.put("academic year", year);
        }
        if (!height.isEmpty()) {
            traits.put("height", height);
        }
        if (!sport.isEmpty()) {
            traits.put("sport", sport);
        }
        if (!highSchool.isEmpty()) {
            traits.put("high school", highSchool);
        }
        if (!hometown.isEmpty()) {
            traits.put("hometown", hometown);
        }

        HashMap<String, HashMap<String, String>> matchingAthletes = fmq
                .findMatchingAthletes(traits);

        for (String player : matchingAthletes.keySet()) {
            HashMap<String, String> playerInfo = matchingAthletes.get(player);
            list.append(player + "\n");
            for (String category : playerInfo.keySet()) {
                list.append(category).append(": ").
                        append(playerInfo.get(category)).append("\n");
            }
            list.append("\n");
        }

        JTextArea textArea = new JTextArea(list.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(700, 500));

        JOptionPane.showMessageDialog(
                null, scrollPane, "Matching players!",
                JOptionPane.PLAIN_MESSAGE
        );
    }
    //Displays roster for multiple sports
    public void multipleRosters() {
        JFrame frame = new JFrame();

        int numSports = Integer.parseInt(
                JOptionPane.showInputDialog(
                        frame,
                        "Enter number of sports: "
                )
        );
        String[] sportsList = new String[numSports];
        for (int i = 0; i < numSports; i++) {
            sportsList[i] = (String) JOptionPane.showInputDialog(
                    frame,
                    "Enter sport: "
            );
        }

        StringBuilder list = new StringBuilder();
        HashMap<String, HashMap<String, String>> players =
                fmq.findMultipleSportRoster(sportsList);
        for (String player : players.keySet()) {
            HashMap<String, String> playerInfo = players.get(player);
            list.append(player + "\n");
            for (String category : playerInfo.keySet()) {
                list.append(category).append(": ").append
                        (playerInfo.get(category)).append("\n");
            }
            list.append("\n");
        }

        JTextArea textArea = new JTextArea(list.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(700, 500));

        JOptionPane.showMessageDialog(
                null, scrollPane, "Rosters",
                JOptionPane.PLAIN_MESSAGE
        );
    }
   //Displays a properly formatted list of traits that the platform will accept
    public void getTraits() {
        String traits = "sport\n" +
                "height\n" +
                "hometown\n" +
                "high school\n" +
                "academic year\n";
        JTextArea textArea = new JTextArea(traits);
        JOptionPane.showMessageDialog(
                null, textArea, "Traits",
                JOptionPane.PLAIN_MESSAGE
        );
    }
    //Displays a random list of 10 people that have specific
    // user inputted traits
    public void findFriends() {
        JFrame frame = new JFrame();
        String message = "If you don't want to input a certain trait," +
                " press enter.\n";
        String year = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter academic year (ex: Freshman): "
        );
        String height = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter height (ex: 6'0\"): "
        );
        String sport = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter sport: "
        );
        String highSchool = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter high school: "
        );
        String hometown = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter hometown (ex: Padua, Italy): "
        );

        StringBuilder list = new StringBuilder();

        HashMap<String, String> traits = new HashMap<>();
        if (!year.isEmpty()) {
            traits.put("academic year", year);
        }
        if (!height.isEmpty()) {
            traits.put("height", height);
        }
        if (!sport.isEmpty()) {
            traits.put("sport", sport);
        }
        if (!highSchool.isEmpty()) {
            traits.put("high school", highSchool);
        }
        if (!hometown.isEmpty()) {
            traits.put("hometown", hometown);
        }

        HashMap<String, HashMap<String, String>> closeAthletes =
                fmq.findCloseAthletes(traits);
        Random rand = new Random();
        LinkedList<Integer> randList = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            int r = rand.nextInt(closeAthletes.size());
            while (randList.contains(r)) {
                r = rand.nextInt(closeAthletes.size());
            }
            randList.add(r);
        }

        LinkedList<String> athleteList = new LinkedList<>(closeAthletes.keySet());
        for (int j : randList) {
            list.append(athleteList.get(j) + "\n");
        }

        JTextArea textArea = new JTextArea(list.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(300, 300));

        JOptionPane.showMessageDialog(
                null, scrollPane, "Recommended friends!",
                JOptionPane.PLAIN_MESSAGE
        );
    }
    //Creates an adjacency list from user inputted traits
    public void adjacencyList() {
        JFrame frame = new JFrame();
        String message = "How many traits you would like to use to " +
                "form your social network?\n";
        String num = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter number of traits (between 1 and 4): "
        );
        List<String> traits = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(num); i++) {
            traits.add((String) JOptionPane.showInputDialog(
                    frame, message +
                            "Enter trait: "
            ));
        }

        StringBuilder list = new StringBuilder();

        HashMap<String, HashSet<String>> adjList = fmq.returnAdjList(traits);

        for (String player : adjList.keySet()) {
            String line = "";
            line+=(player + ": ");
            for (String connected : adjList.get(player)) {
               line+=(connected + ", ");
            }
            list.append(line + "\n");
        }

        JTextArea textArea = new JTextArea(list.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(700, 500));

        JOptionPane.showMessageDialog(
                null, scrollPane, "Adjacency List",
                JOptionPane.PLAIN_MESSAGE
        );
    }
    //Creates an adjacency matrix from user inputted traits.
    public void adjacencyMatrix() {
        JFrame frame = new JFrame();
        String message = "How many traits you would like to use to form your social network?\n";
        String num = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter number of traits (between 1 and 4): "
        );
        List<String> traits = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(num); i++) {
            traits.add((String) JOptionPane.showInputDialog(
                    frame, message +
                            "Enter trait: "
            ));
        }

        StringBuilder list = new StringBuilder();

        Integer[][] adjMatrix = fmq.returnAdjMatrix(traits);

        for (int i = 0; i < adjMatrix.length; i++) {
            String line = "";
            for (int j = 0; j < adjMatrix[0].length; j++) {
                line+=(adjMatrix[i][j] + " ");
            }
            list.append(line + "\n");
        }

        JTextArea textArea = new JTextArea(list.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(700, 500));

        JOptionPane.showMessageDialog(
                null, scrollPane, "Adjacency Matrix",
                JOptionPane.PLAIN_MESSAGE
        );
    }
    //uses triadic closure to form closures
    public void formClosures() {
        JFrame frame = new JFrame();
        String message = "How many traits you would like to use to form your social network?\n";
        String num = (String) JOptionPane.showInputDialog(
                frame, message +
                        "Enter number of traits (between 1 and 4): "
        );
        List<String> traits = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(num); i++) {
            traits.add((String) JOptionPane.showInputDialog(
                    frame, message +
                            "Enter trait: "
            ));
        }

        StringBuilder list = new StringBuilder();

        HashMap<String, HashSet<String>> adjList = fmq.returnAdjList(traits);
        HashMap<String, HashSet<String>> closureList = fmq.returnClosureAdjList(adjList);

        list.append("For each player, we list the number of connections in the " + "\n" +
                "original matrix vs in the matrix with closures" + "\n");
        for (String player : adjList.keySet()) {
            String line = "";
            line+=(player + ": " + adjList.get(player).size() + ", " + closureList.get(player).size());
            list.append(line + "\n");
        }

        JTextArea textArea = new JTextArea(list.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(700, 500));

        JOptionPane.showMessageDialog(
                null, scrollPane, "Closures",
                JOptionPane.PLAIN_MESSAGE
        );
    }
    //displays profile for every athlete.
    public void allAthletes() {
        JFrame frame = new JFrame();

        HashMap<String, HashMap<String, String>> players = fmq.getPlayers();

        StringBuilder list = new StringBuilder();

        for (String player : players.keySet()) {
            HashMap<String, String> playerInfo = players.get(player);
            list.append(player + "\n");
            for (String category : playerInfo.keySet()) {
                list.append(category + ": " + playerInfo.get(category) + "\n");
            }
            list.append("\n");
        }

        JTextArea textArea = new JTextArea(list.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize(new Dimension(700, 500));

        JOptionPane.showMessageDialog(
                null, scrollPane, "Closures",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    public void instructionsPanel() {
        String instructions = "Find My Quaker is an app where you can make friends " +
                "with Penn's elite athletes.\n" +
                "\n" +
                "There are 11 buttons on this app (not including the instructions).\n" +
                "\n" +
                "1) Sports\n" +
                " - This function shows you a list of the sports and how the " +
                "names of the sports " +
                "are formatted as inputs to other buttons.\n" +
                "2) Traits\n" +
                " - This function shows you a list of the traits and how " +
                "they are formatted as inputs to "
                +
                "other buttons\n" +
                "3) Profile\n" +
                " - Input an athlete's name and this function will return all " +
                "his/her/their traits.\n" +
                "4) Roster\n" +
                " - Input a sport and this function will return the entire roster.\n" +
                "5) Multiple Rosters\n" +
                " - Input the number of sports you want to enter and " +
                "the corresponding sports " +
                "and this function will output the rosters for all the " +
                "sports you entered.\n" +
                "6) Match Athletes\n" +
                " - Input a list of traits and this function will output the " +
                "athletes that have all "
                +
                "those traits. If you don't know what to put for a certain trait, " +
                "you can leave it "
                +
                "blank.\n" +
                "7) Find friends\n" +
                " - Given an input of traits, this function will recommend a l" +
                "ist of people who " +
                "share at least one trait.\n" +
                "8) Adjacency List\n" +
                " - Given an input of traits, this function will create an " +
                "adjacency list " +
                "representation of the Penn Athletics Social Network. All athletes " +
                "will be listed, and they will share edges if they share a " +
                "common trait.\n" +
                "9) Adjacency Matrix\n" +
                " - Given an input of traits, this function will " +
                "create an adjacency matrix " +
                "representation of the Penn Athletics Social Network.\n" +
                "10) Form Closures \n" +
                " - Given an input of traits, this function will first " +
                "create a graph from the " +
                "given traits, and then it will form closures from that " +
                "given graph. The number " +
                "of connections for each athlete in the original graph " +
                "vs. the graph with " +
                "closures will be outputted.\n" +
                "11) All Athletes \n" +
                " - This function shows you a list of all the athletes in " +
                "the Penn Athletics " +
                "Social Network along with their information.\n";
        JOptionPane.showMessageDialog(
                null, instructions, "How to use " +
                        "Find My Quaker",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        BufferedImage img = null;
        String file = "banner.png";

        try {
            if (img == null) {
                img = ImageIO.read(new File(file));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        g.drawImage(
                img, 0, 0, 600, 250, null
        );
    }

}
