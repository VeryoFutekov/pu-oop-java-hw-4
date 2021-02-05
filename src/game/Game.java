package game;

import game.fields.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;


public class Game extends JFrame implements MouseListener {

    BasicField chosenField;
    private BasicField[][] fields = new BasicField[8][8];


    public Game() throws HeadlessException {
        super.setSize(800, 800);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        super.addMouseListener(this);
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
        for (int i = 0; i < 51; i++) {
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

        fields[randomRow][randomCol] = new YellowField(randomRow, randomCol, Color.YELLOW,"");

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int rowInMatrix = e.getY() / Constants.BASIC_FIELD_SIZE;
        int colInMatrix = e.getX() / Constants.BASIC_FIELD_SIZE;

        BasicField clicked=fields[rowInMatrix][colInMatrix];

        if (isValid(rowInMatrix, colInMatrix)) {
            if (chosenField != null) {

                if (!chosenField.equals(clicked)) {
                    return;
                }
                fields[rowInMatrix][colInMatrix] = generateRandomField(rowInMatrix,colInMatrix);
                chosenField = null;

//                if (isEndgameConditionOccurred(row, col)) {
//                    visualiseEndingWindow("Ти загуби");
//                }
            } else {
                if (clicked instanceof  GreenField) {
                    GreenField greenField=(GreenField) clicked;
                    if (greenField.isEnding()) {
//                        visualiseEndingWindow("Ти спечели!!!");
                        System.out.println("You win");
                    }
                }
                if (!(clicked instanceof BlueField)) {
                    chosenField = new YellowField(rowInMatrix, colInMatrix, Color.YELLOW,"?");
                    fields[rowInMatrix][colInMatrix] = chosenField;
                }
            }
        }
        super.repaint();

    }

   private BasicField generateRandomField(int row,int col){
        Random random=new Random();

        return  random.nextInt(100)<20? new BlueField(row,col,Color.BLUE):new YellowField(row,col, Color.YELLOW,"");
    }

    private boolean isValid(int row, int col) {
        Map<Integer, Integer> coordinates= new TreeMap<>();
        coordinates.put(col,row-1);
        coordinates.put(col,row-1);
        coordinates.put(col, row + 1);
        coordinates.put(col + 1, row);
        coordinates.put(col - 1, row);
        for (Map.Entry<Integer, Integer> keyValueEntry : coordinates.entrySet()) {
            int x=keyValueEntry.getKey();
            int y=keyValueEntry.getValue();


                try{
                    if(fields[y][x] instanceof YellowField){
                        return true;
                    }
                }catch (Exception ignored){

                }

        }
        return false;

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
