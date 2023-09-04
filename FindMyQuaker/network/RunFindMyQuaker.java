package FindMyQuaker.network;

import javax.swing.*;
import java.awt.*;

public class RunFindMyQuaker implements Runnable {
    @Override
    public void run() {
        final JFrame frame = new JFrame("Find My Quaker");
        frame.setLocation(600, 400);
        frame.setPreferredSize(new Dimension(600, 375));

        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);

        final FindMyQuakerGUI main = new FindMyQuakerGUI();
        frame.add(main, BorderLayout.CENTER);

        final JPanel control_panel = new JPanel();
        control_panel.setLayout(new GridLayout(3, 4));
        // control_panel.setLayout(new BoxLayout(control_panel, BoxLayout.X_AXIS));
        frame.add(control_panel, BorderLayout.NORTH);

        final JButton listSports = new JButton("Sports");
        final JButton traits = new JButton("Traits");
        final JButton athleteProfile = new JButton("Profile");
        final JButton getRoster = new JButton("Roster");
        final JButton findAthletes = new JButton("Match Athletes");
        final JButton multipleSports = new JButton("Multiple Rosters");
        final JButton help = new JButton("Instructions");
        final JButton friends = new JButton("Find Friends!");
        final JButton adjList = new JButton("Adjacency List");
        final JButton adjMatrix = new JButton("Adjacency Matrix");
        final JButton closures = new JButton("Form Closures");
        final JButton athletes = new JButton("All Athletes");

        help.addActionListener(e -> main.instructionsPanel());
        listSports.addActionListener(e -> main.listSports());
        traits.addActionListener(e -> main.getTraits());
        athleteProfile.addActionListener(e -> main.findAthlete());
        getRoster.addActionListener(e -> main.listRoster());
        findAthletes.addActionListener(e -> main.matchAthlete());
        multipleSports.addActionListener(e -> main.multipleRosters());
        friends.addActionListener(e -> main.findFriends());
        adjList.addActionListener(e -> main.adjacencyList());
        adjMatrix.addActionListener(e -> main.adjacencyMatrix());
        closures.addActionListener(e -> main.formClosures());
        athletes.addActionListener(e -> main.allAthletes());

        // control_panel.add(save);
        // control_panel.add(load);
        control_panel.add(help);
        control_panel.add(listSports);
        control_panel.add(traits);
        control_panel.add(athleteProfile);
        control_panel.add(getRoster);
        control_panel.add(multipleSports);
        control_panel.add(findAthletes);
        control_panel.add(friends);
        control_panel.add(adjList);
        control_panel.add(adjMatrix);
        control_panel.add(closures);
        control_panel.add(athletes);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }
}
