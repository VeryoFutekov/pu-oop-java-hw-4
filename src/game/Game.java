package game;

import game.fields.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Game extends JFrame {

    private BasicField[][] fields = new BasicField[8][8];

    public Game() throws HeadlessException {
        super.setSize(800, 800);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);

        generatePlayingFields();


    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int row = 0; row < fields.length; row++) {
            for (int col = 0; col < fields[row].length; col++) {
                BasicField field = fields[row][col];
                try {
                    field.render(g);
                } catch (Exception ex) {
                    continue;
                }
            }
        }

    }


    private void generatePlayingFields() {
        Random random = new Random();
        generateUndiscoveredTerritories(random);
        generateGpsCoordinate(random);
        generateStartingPoint(random);
        generateUnreachableTerritory(random);


    }

    private void generateUndiscoveredTerritories(Random random) {
        int row;
        int col;
        for (int i = 0; i < 50; i++) {
            row = random.nextInt(8);
            col = random.nextInt(8);

            if (fields[row][col] != null) {
                i--;
                continue;
            }
            fields[row][col] = new RedField(row, col, Color.RED);

        }


    }

    private void generateUnreachableTerritory(Random random) {

        int row;
        int col;

        for (int i = 0; i < 5; i++) {
            row = random.nextInt(8);
            col = random.nextInt(8);

            if (fields[row][col] != null) {
                i--;
                continue;
            }
            fields[row][col] = new BlueField(row, col, Color.BLUE);
        }

    }
    private void generateGpsCoordinate(Random random) {


        int row;
        int col;
        for (int i = 0; i < 8; i++) {
            row = random.nextInt(8);
            col = random.nextInt(8);

            if (fields[row][col] != null) {
                i--;
                continue;
            }
            GreenField gpsCoordinate = new GreenField(row, col, Color.GREEN);

            if (i == 5) {
                gpsCoordinate.setEnding(true);
            }
            fields[row][col] = gpsCoordinate;
        }


    }

    private void generateStartingPoint(Random random) {
        int randomRow=random.nextInt(2)==0?0:7;
        int randomCol=random.nextInt(2)==0?0:7;

        fields[randomRow][randomCol] = new YellowField(randomRow, randomCol, Color.YELLOW);

    }
}
