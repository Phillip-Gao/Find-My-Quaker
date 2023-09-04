package FindMyQuaker.network;

import java.util.*;

public class PennAthleticsNetwork {

    private PennAthleticsParser athleteParser;
    private HashMap<String, HashMap<String, String>> players;

    public PennAthleticsNetwork() {
        athleteParser = new PennAthleticsParser();

        players = new HashMap<>();

        for (String sport : athleteParser.getSports()) {
            HashMap<String, HashMap<String, String>> rosterInfo = athleteParser
                    .getRosterInfo(sport);
            for (String name : rosterInfo.keySet()) {
                players.put(name, rosterInfo.get(name));
            }
        }
    }

    public HashMap<String, HashSet<String>> createAdjList
            (List<String> traits) {
        HashMap<String, HashSet<String>> adjList = new HashMap<>();

        for (String player : players.keySet()) {
            adjList.put(player, new HashSet<>());
        }

        for (String trait : traits) {
            for (String player1 : players.keySet()) {
                for (String player2 : players.keySet()) {
                    if (!player1.equals(player2) && !players.get(player1)
                            .get(trait).isEmpty()
                            && players.get(player1).get(trait)
                            .equals(players.get(player2).get(trait))) {
                        HashSet<String> list1 = adjList.get(player1);
                        list1.add(player2);

                        HashSet<String> list2 = adjList.get(player2);
                        list2.add(player1);

                        adjList.put(player1, list1);
                        adjList.put(player2, list2);
                    }
                }
            }
        }

        return adjList;
    }

    public Integer[][] createAdjMatrix(List<String> traits) {
        Object[] names = players.keySet().toArray();
        Integer[][] adjMatrix = new Integer[names.length][names.length];

        for (Integer[] row : adjMatrix)
            Arrays.fill(row, 0);

        for (String trait : traits) {
            for (int i = 0; i < names.length; i++) {
                for (int j = 0; j < names.length; j++) {
                    if (i != j && !players.get(names[i]).get(trait).isEmpty() && players
                            .get(names[i]).get(trait)
                            .equals(players.get(names[j]).get(trait))) {
                        adjMatrix[i][j] = 1;
                        adjMatrix[j][i] = 1;
                    }
                }
            }
        }

        return adjMatrix;
    }

    public HashMap<String, HashMap<String, String>> getPlayers() {

        return players;
    }

    public HashMap<String, HashSet<String>> closureAdjList(
            HashMap<String, HashSet<String>> adjList
    ) {
        HashMap<String, HashSet<String>> closure = new HashMap<>();

        for (String player1 : adjList.keySet()) {
            HashSet<String> list1 = new HashSet<>();
            list1.addAll(adjList.get(player1));
            for (String player2 : adjList.keySet()) {
                if (!player1.equals(player2)) {
                    for (String child : adjList.get(player1)) {
                        if (adjList.get(player2).contains(child)) {
                            list1.add(player2);
                            break;
                        }
                    }
                }
            }
            closure.put(player1, list1);
        }

        return closure;
    }

    public Integer[][] closureAdjMatrix(Integer[][] adjMatrix) {
        Integer[][] closure = adjMatrix.clone();

        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = 0; j < adjMatrix.length; j++) {
                if (i != j) {
                    for (int elt = 0; elt < adjMatrix[0].length; elt++) {
                        if (adjMatrix[i][elt] == 1 && adjMatrix[j][elt] == 1) {
                            closure[i][j] = 1;
                            closure[j][i] = 1;
                            break;
                        }
                    }
                }
            }
        }

        return closure;
    }

}
