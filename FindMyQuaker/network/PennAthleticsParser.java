package FindMyQuaker.network;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;

public class PennAthleticsParser {
    private String baseURL = "https://pennathletics.com";
    private Document currDoc;
    private HashMap<String, String> teamRosterMap = new HashMap<>();

    public PennAthleticsParser() {
        try {
            currDoc = Jsoup.connect(baseURL).get();
            getTeamsRoster();
        } catch (IOException e) {
            throw new IllegalArgumentException("page doesn't exist");
        }
    }

    /**
     * Helper function for the constructor to get the roster for all Penn sports
     * teams (men and women).
     * Adds each team's roster link to a map.
     */
    public void getTeamsRoster() {
        Scanner in = null;
        try {
            in = new Scanner(new File("teams.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String baseLink = "https://pennathletics.com/sports/";
        while (in.hasNext()) {
            String sport = in.next();
            teamRosterMap.put(sport, baseLink + sport + "/roster");
        }
    }

    /**
     * List of sports from the website
     * @return list of sports
     */
    public List<String> getSports() {
        Scanner in = null;
        try {
            in = new Scanner(new File("teams.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<String> sports = new ArrayList<>();
        while (in.hasNext()) {
            String sport = in.next();
            sports.add(sport);
        }
        return sports;
    }

    /**
     * Helper function for getRosterInfo. If a player is missing a
     * certain trait (ex. height), it returns a blank string to avoid
     * a NullPointerException
     */
    public String isEmpty(Element e) {
        if (e == null || e.text().isEmpty()) {
            return "";
        }
        return e.text();
    }

    /**
     * Given a sport as input, it returns the athlete on the roster
     * and their individual information
     **/
    public HashMap<String, HashMap<String, String>> getRosterInfo(String sport) {
        try {
            currDoc = Jsoup.connect(teamRosterMap.get(sport)).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HashMap<String, HashMap<String, String>> rosterInfo = new HashMap<>();
        Elements athletes = currDoc.select("li[class=sidearm-roster-player]");

        for (Element e : athletes) {
            HashMap<String, String> playerInfo = new HashMap<>();

            Element image = e.selectFirst("img");
            Element name = e.selectFirst("div[class=sidearm-roster-player-name]").selectFirst("h3");
            Element bio = e.selectFirst("div[class=sidearm-roster-player-name]").selectFirst("a");
            Element height = e.selectFirst("span[class=sidearm-roster-player-height]");
            Element year = e.selectFirst("span[class=sidearm-roster-player-academic-year]");

            Element other = e.selectFirst("div[class=sidearm-roster-player-class-hometown]");
            Element hometown = other.selectFirst("span[class=sidearm-roster-player-hometown]");
            Element high = other.selectFirst("span[class=sidearm-roster-player-highschool]");

            playerInfo.put("height", isEmpty(height));
            playerInfo.put("academic year", isEmpty(year));
            playerInfo.put("hometown", isEmpty(hometown));
            playerInfo.put("high school", isEmpty(high));
            playerInfo.put("sport", sport);

            String baseLink = "https://pennathletics.com";
            playerInfo.put("bio", baseLink + bio.attr("href"));
            if (image != null && !image.attr("data-src").isEmpty()) {
                playerInfo.put("image", baseLink + image.attr("data-src"));
            }

            rosterInfo.put(name.text(), playerInfo);
        }
        return rosterInfo;
    }

    /**
     * Given a list of sports as input, it returns all athletes on
     * each roster and their individual information
     **/
    public HashMap<String, HashMap<String, String>> getMultipleSportRosterInfo(String[] sports) {
        HashMap<String, HashMap<String, String>> rosterInfo = null;
        rosterInfo = new HashMap<>();
        for (String sport : sports) {
            try {
                currDoc = Jsoup.connect(teamRosterMap.get(sport)).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Elements athletes = currDoc.select("li[class=sidearm-roster-player]");

            for (Element e : athletes) {
                HashMap<String, String> playerInfo = new HashMap<>();

                Element image = e.selectFirst("img");
                Element name = e.selectFirst("div[class=sidearm-roster-player-name]")
                        .selectFirst("h3");
                Element bio = e.selectFirst("div[class=sidearm-roster-player-name]")
                        .selectFirst("a");
                Element height = e.selectFirst("span[class=sidearm-roster-player-height]");
                Element year = e.selectFirst("span[class=sidearm-roster-player-academic-year]");

                Element other = e.selectFirst("div[class=sidearm-roster-player-class-hometown]");
                Element hometown = other.selectFirst("span[class=sidearm-roster-player-hometown]");
                Element high = other.selectFirst("span[class=sidearm-roster-player-highschool]");

                playerInfo.put("height", isEmpty(height));
                playerInfo.put("academic year", isEmpty(year));
                playerInfo.put("hometown", isEmpty(hometown));
                playerInfo.put("high school", isEmpty(high));
                playerInfo.put("sport", sport);

                String baseLink = "https://pennathletics.com";
                playerInfo.put("bio", baseLink + bio.attr("href"));
                if (image != null && !image.attr("data-src").isEmpty()) {
                    playerInfo.put("image", baseLink + image.attr("data-src"));
                }

                rosterInfo.put(name.text(), playerInfo);
            }

        }
        return rosterInfo;
    }

    /**
     * Goes through the roster information and looks for the athlete
     * @param name name of the athlete
     * @return all information associated with the athlete (bio, height, hometown,
     *         etc.)
     */
    public HashMap<String, String> getPlayerInfo(String name) {
        HashMap<String, String> playerInfo = new HashMap<>();
        for (String sport : getSports()) {
            HashMap<String, HashMap<String, String>> rosterInfo = getRosterInfo(sport);
            if (rosterInfo.containsKey(name)) {
                HashMap<String, String> athlete = rosterInfo.get(name);
                for (String category : athlete.keySet()) {
                    playerInfo.put(category, athlete.get(category));
                }
                break;
            }
        }
        return playerInfo;
    }

    /**
     * returns list of players with matching traits (all are matching)
     * @param traits
     * @return
     */
    public HashMap<String, HashMap<String, String>> getMatchingPlayers(
            HashMap<String, String> traits
    ) {
        HashMap<String, HashMap<String, String>> players = new HashMap<>();
        for (String sport : getSports()) {
            HashMap<String, HashMap<String, String>> rosterInfo = getRosterInfo(sport);
            for (String name : rosterInfo.keySet()) {
                HashMap<String, String> athlete = rosterInfo.get(name);
                boolean match = true;
                for (String trait : traits.keySet()) {
                    if (!athlete.get(trait).equals(traits.get(trait))) {
                        match = false;
                    }
                }
                if (match) {
                    players.put(name, rosterInfo.get(name));
                }
            }
        }
        return players;
    }

    /**
     * returns list of players with matching traits
     * @param traits
     * @return
     */
    public HashMap<String, HashMap<String, String>> getClosePlayers(
            HashMap<String, String> traits
    ) {
        HashMap<String, HashMap<String, String>> players = new HashMap<>();
        for (String sport : getSports()) {
            HashMap<String, HashMap<String, String>> rosterInfo = getRosterInfo(sport);
            for (String name : rosterInfo.keySet()) {
                HashMap<String, String> athlete = rosterInfo.get(name);
                for (String trait : traits.keySet()) {
                    if (athlete.get(trait).equals(traits.get(trait))) {
                        players.put(name, rosterInfo.get(name));
                    }
                }
            }
        }
        return players;
    }

    /**
     * Basic getter function for the map that contains links to each team's roster
     * @return map of links to each team's roster
     */
    public HashMap<String, String> returnTeamRosterMap() {
        return teamRosterMap;
    }

}
