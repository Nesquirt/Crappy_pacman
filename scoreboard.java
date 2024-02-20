import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class scoreboard extends JFrame {
    JTextArea ScoreText;
    Score[] topScores;
    static JFrame gameFrame;
    static Menu menu;
    public scoreboard (Menu menu ){
        super("Pacman");
        setSize(920, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(false);
        this.menu = menu;

        //Inizializzazione array di punteggi
        topScores = new Score[5];
        for(int i = 0; i<topScores.length; i++)
        {
            Score tmp = new Score();
            tmp.score = 0;
            tmp.name = "tmp";
            topScores[i] = tmp;
        }
        //Caricamento Immagini
        ImageIcon leaderboardImageIcon = new ImageIcon("images/leaderboard.png");
        ImageIcon backImageIcon = new ImageIcon("images/back.png");


        // Creazione del pannello principale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.BLACK);

        JLabel title = new JLabel(leaderboardImageIcon);
        JPanel titlePanel = new JPanel();
        titlePanel.setSize(920, 100);
        titlePanel.add(title);
        titlePanel.setBackground(Color.BLACK);
        mainPanel.add(titlePanel);
        titlePanel.setLocation(0,0);

        //Area di testo dei punteggi
        JPanel ScorePanel = new JPanel();
        ScorePanel.setSize(920, 300);
        ScorePanel.setBackground(Color.BLACK);
        ScoreText = new JTextArea();
        ScoreText.setEditable(false);
        ScoreText.setForeground(Color.YELLOW);
        ScoreText.setBackground(Color.BLACK);
        ScorePanel.add(ScoreText);
        ScorePanel.setLocation(0,100);
        mainPanel.add(ScorePanel);

        //Pulsante Back

        JButton backButton = new JButton();
        backButton.setIcon(backImageIcon);
        backButton.setBackground(Color.BLACK);

        backButton.setPreferredSize(new Dimension(200, 60));
        backButton.setFocusPainted(true);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.PAGE_END);

        backButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                menu.setVisible(true);

            }

        });

        add(mainPanel);


        readFile();
    }

    public class Score implements Comparable<Score>{
        public String name;
        public int score;

        public int compareTo(Score other) {
            return this.score - other.score;
        }
    }

    public void readFile()  //lettura e scrittura da file Score.txt
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Score.txt"));
            String line;
            while((line = reader.readLine()) != null)
            {
                Score newScore = new Score();
                newScore.name = line.split(" ")[0];
                newScore.score = Integer.parseInt(line.split(" ")[1]); //conversione score da stringa a int
                Arrays.sort(topScores);
                if(newScore.score > topScores[0].score)
                {
                    topScores[0] = newScore;
                    Arrays.sort(topScores);
                }
            }

            for(int i = topScores.length - 1; i >= 0; i--)
            {
                String newText = ScoreText.getText() + "\n" + topScores[i].name + ": " + topScores[i].score;
                ScoreText.setText(newText);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void writeFile(String newName, int newScore)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Score.txt", true))) {
            writer.write("\n" + newName + " " + newScore);
        } catch (Exception e) {
            e.printStackTrace();
        }
        menu.setVisible(true);
        gameFrame.setVisible(false);

    }


}
