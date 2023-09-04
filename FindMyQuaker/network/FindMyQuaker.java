package FindMyQuaker.network;

import java.util.*;

public class FindMyQuaker {

    private PennAthleticsParser athleteParser = new PennAthleticsParser();
    private PennAthleticsNetwork athleteNetwork =
            new PennAthleticsNetwork();

    public List<String> listSports() {
        return athleteParser.getSports();
    }

    public HashMap<String, HashMap<String, String>> getPlayers() {
        return athleteNetwork.getPlayers();
    }

    public HashMap<String, HashMap<String, String>> getRoster(String sport) {
        return athleteParser.getRosterInfo(sport);
    }

    public HashMap<String, String> findAthleteInfo(String name) {
        return athleteParser.getPlayerInfo(name);
    }

    public HashMap<String, HashMap<String, String>> findMatchingAthletes(
            HashMap<String, String> traits
    ) {
        return athleteParser.getMatchingPlayers(traits);
    }

    public HashMap<String, HashMap<String, String>> findCloseAthletes(
            HashMap<String, String> traits
    ) {
        return athleteParser.getClosePlayers(traits);
    }

    public HashMap<String, HashMap<String, String>>
    findMultipleSportRoster(String[] sportsArr) {
        return athleteParser.getMultipleSportRosterInfo(sportsArr);
    }

    public HashMap<String, HashSet<String>> returnAdjList(List<String> categories) {
        return athleteNetwork.createAdjList(categories);
    }

    public Integer[][] returnAdjMatrix(List<String> categories) {

        return athleteNetwork.createAdjMatrix(categories);
    }

    public HashMap<String, HashSet<String>> returnClosureAdjList(
            HashMap<String, HashSet<String>> adjList
    ) {
        return athleteNetwork.closureAdjList(adjList);
    }

    public Integer[][] returnClosureAdjMatrix(Integer[][] adjMatrix) {
        return athleteNetwork.closureAdjMatrix(adjMatrix);
    }

    public static void main(String[] args) {

        FindMyQuaker fmq = new FindMyQuaker();

        Scanner sc = new Scanner(System.in);
        int input = 1;

        while (input != 0) {
            System.out.println
                    ("Enter a number to choose one of the following options:");
            System.out.println(
                    "(1) Find all Penn sports teams"
            );
            System.out.println(
                    "(2) Get roster info for a sport"
            );
            System.out.println(
                    "(3) Find info about an athlete."
            );
            System.out.println(
                    "(4) Find athletes that match a character trait(s)"
            );
            System.out.println(
                    "(5) Get roster info for a list of sports"
            );
            System.out.println(
                    "(6) Access Penn athletics social network"
            );
            System.out.println(
                    "(7) Adjacency list for Penn athletics"
            );
            System.out.println(
                    "(8) Adjacency matrix for Penn athletics"
            );
            System.out.println(
                    "(9) Form closures from adjacency list"
            );
            System.out.println(
                    "(10) Form closures from adjacency matrix"
            );
            System.out.println("(0) Quit Program");

            input = sc.nextInt();
            System.out.println();

            // String name;
            HashMap<String, HashMap<String, String>> players;
            List<String> categories;
            HashMap<String, HashSet<String>> adjList;
            Integer[][] adjMatrix;
            switch (input) {
                case 1:
                    List<String> sports = fmq.listSports();
                    System.out.println("Sport teams at Penn:");
                    for (String sport : sports) {
                        System.out.println(sport);
                    }
                    break;
                case 2:

                    Scanner inputSport = new Scanner(System.in);

                    System.out.println("Enter sport: ");
                    String sport = inputSport.nextLine();


                    players = fmq.getRoster(sport);

                    for (String player : players.keySet()) {
                        HashMap<String, String> playerInfo = players.get(player);
                        System.out.println(player);
                        for (String category : playerInfo.keySet()) {
                            System.out.println(category + ": " +
                                    playerInfo.get(category));
                        }
                        System.out.println();
                    }
                    break;
                case 3:
                    // Find *input name* athlete info
                    Scanner inputName = new Scanner(System.in);

                    System.out.println("Enter name: ");
                    String name = inputName.nextLine();

                    // name = "George Smith";
                    HashMap<String, String> athleteInfo =
                            fmq.findAthleteInfo(name);

                    System.out.println(name);
                    for (String category : athleteInfo.keySet()) {
                        System.out.println(category + ": " +
                                athleteInfo.get(category));
                    }
                    break;
                case 4:

                    Scanner inputTraits = new Scanner(System.in);

                    System.out.println
                            ("If you don't want to input a certain trait, press enter.");
                    System.out.println("Enter academic year " +
                            "(Freshman, Sophomore, Junior, Senior): ");
                    String year = inputTraits.nextLine();
                    System.out.println("Enter height (ex: 6'0\"): ");
                    String height = inputTraits.nextLine();
                    System.out.println("Enter sport: ");
                    String sp = inputTraits.nextLine();
                    System.out.println("Enter high school: ");
                    String high = inputTraits.nextLine();
                    System.out.println("Enter hometown (ex: Padua, Italy): ");
                    String hometown = inputTraits.nextLine();

                    HashMap<String, String> traits = new HashMap<>();
                    if (!year.isEmpty()) {
                        traits.put("academic year", year);
                    }
                    if (!height.isEmpty()) {
                        traits.put("height", height);
                    }
                    if (!sp.isEmpty()) {
                        traits.put("sport", sp);
                    }
                    if (!high.isEmpty()) {
                        traits.put("high school", high);
                    }
                    if (!hometown.isEmpty()) {
                        traits.put("hometown", hometown);
                    }


                    players = fmq.findMatchingAthletes(traits);

                    for (String player : players.keySet()) {
                        HashMap<String, String> playerInfo = players.get(player);
                        System.out.println(player);
                        for (String category : playerInfo.keySet()) {
                            System.out.println(category + ": " +
                                    playerInfo.get(category));
                        }
                        System.out.println();
                    }
                    break;
                case 5:
                    Scanner inputRosterInfo = new Scanner(System.in);

                    System.out.println("Enter number of sports: ");
                    String n = inputRosterInfo.nextLine();
                    String[] sportsList = new String[Integer.parseInt(n)];
                    for (int i = 0; i < Integer.parseInt(n); i++) {
                        System.out.println("Enter sport: ");
                        sportsList[i] = inputRosterInfo.nextLine();
                    }

                    players = fmq.findMultipleSportRoster(sportsList);

                    for (String player : players.keySet()) {
                        HashMap<String, String> playerInfo = players.get(player);
                        System.out.println(player);
                        for (String category : playerInfo.keySet()) {
                            System.out.println(category + ": " +
                                    playerInfo.get(category));
                        }
                        System.out.println();
                    }
                    break;
                case 6:
                    System.out.println("This function returns all the players.");
                    players = fmq.getPlayers();

                    for (String player : players.keySet()) {
                        HashMap<String, String> playerInfo = players.get(player);
                        System.out.println(player);
                        for (String category : playerInfo.keySet()) {
                            System.out.println(category + ": " +
                                    playerInfo.get(category));
                        }
                        System.out.println();
                    }
                    break;
                case 7:
                    categories = new ArrayList<>();
                    categories.add("sport");
                    System.out.println("Sport has already been added as a trait.");
                    Scanner inputList = new Scanner(System.in);
                    System.out.println("Enter number of traits (between 1 and 4): ");
                    String num = inputList.nextLine();
                    for (int i = 0; i < Integer.parseInt(num); i++) {
                        System.out.println("Enter trait: ");
                        categories.add(inputList.nextLine());
                    }
                    // categories.add("height");

                    adjList = fmq.returnAdjList(categories);

                    for (String player : adjList.keySet()) {
                        System.out.print(player + ": ");
                        for (String connected : adjList.get(player)) {
                            System.out.print(connected + ", ");
                        }
                        System.out.println();
                    }
                    break;
                case 8:
                    categories = new ArrayList<>();
                    categories.add("sport");
                    System.out.println("Sport has already been added as a trait.");
                    Scanner inputMatrix = new Scanner(System.in);
                    System.out.println("Enter number of traits (between 1 and 4): ");
                    String k = inputMatrix.nextLine();
                    for (int i = 0; i < Integer.parseInt(k); i++) {
                        System.out.println("Enter trait: ");
                        categories.add(inputMatrix.nextLine());
                    }
                    // categories.add("height");

                    adjMatrix = fmq.returnAdjMatrix(categories);

                    for (int i = 0; i < adjMatrix.length; i++) {
                        for (int j = 0; j < adjMatrix[0].length; j++) {
                            System.out.print(adjMatrix[i][j] + " ");
                        }
                        System.out.println();
                    }
                    break;
                case 9:
                    categories = new ArrayList<>();
                    categories.add("sport");
                    System.out.println("Sport has already been added as a trait.");
                    Scanner inTraits = new Scanner(System.in);
                    System.out.println("Enter number of traits (between 1 and 4): ");
                    String p = inTraits.nextLine();
                    for (int i = 0; i < Integer.parseInt(p); i++) {
                        System.out.println("Enter trait: ");
                        categories.add(inTraits.nextLine());
                    }
                    // categories.add("academic year");

                    adjList = fmq.returnAdjList(categories);
                    HashMap<String, HashSet<String>> closureList = fmq
                            .returnClosureAdjList(adjList);

                    for (String player : closureList.keySet()) {
                        System.out.print(player + ": ");
                        for (String connected : closureList.get(player)) {
                            System.out.print(connected + ", ");
                        }
                        System.out.println();
                    }
                    break;
                case 10:
                    categories = new ArrayList<>();
                    categories.add("sport");
                    // categories.add("academic year");
                    System.out.println("Sport has already been added as a trait.");
                    Scanner inTr = new Scanner(System.in);
                    System.out.println("Enter number of traits (between 1 and 4): ");
                    String q = inTr.nextLine();
                    for (int i = 0; i < Integer.parseInt(q); i++) {
                        System.out.println("Enter trait: ");
                        categories.add(inTr.nextLine());
                    }

                    adjMatrix = fmq.returnAdjMatrix(categories);
                    Integer[][] closureMatrix =
                            fmq.returnClosureAdjMatrix(adjMatrix);

                    for (int i = 0; i < closureMatrix.length; i++) {
                        for (int j = 0; j < closureMatrix[0].length; j++) {
                            System.out.print(closureMatrix[i][j] + " ");
                        }
                        System.out.println();
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
            System.out.println();
        }
    }

}