package game;

import game.fields.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;


public class Game extends JFrame implements MouseListener {

    BasicField chosenField;
    private BasicField[][] fields = new BasicField[8][8];


    public Game() throws HeadlessException {
       initWindow();
        super.addMouseListener(this);
        generatePlayingFields();


    }

    /**
     * init of the window
     */
    private void initWindow(){
        super.setSize(800, 800);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
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


    /**
     * generating fields
     */
    private void generatePlayingFields() {
        Random random = new Random();
        generateUndiscoveredTerritories(random);
        generateGpsCoordinate(random);
        generateStartingPoint(random);
        generateUnreachableTerritory(random);


    }

    /**
     * generate undiscovered Territory
     * @param random
     */
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

    /**
     * generate unreachable Territory
     * @param random
     */
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

    /**
     * generate gps Territory
     * @param random
     */
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

    /**
     * generate Starting Territory
     * @param random
     */
    private void generateStartingPoint(Random random) {
        int randomRow=random.nextInt(2)==0?0:7;
        int randomCol=random.nextInt(2)==0?0:7;

        fields[randomRow][randomCol] = new YellowField(randomRow, randomCol, Color.YELLOW,"");

    }

    /**
     * on click event
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int rowInMatrix = e.getY() / Constants.BASIC_FIELD_SIZE;
        int colInMatrix = e.getX() / Constants.BASIC_FIELD_SIZE;

        BasicField clicked=fields[rowInMatrix][colInMatrix];

        if (isValid(rowInMatrix, colInMatrix)) {
            if (chosenField != null) {

                if(chosenField !=clicked){
                    return;
                }
                chosenField = null;
                fields[rowInMatrix][colInMatrix] = generateRandomField(rowInMatrix,colInMatrix);
            } else {
                if (!(clicked instanceof BlueField)) {
                    chosenField = new YellowField(rowInMatrix, colInMatrix, Color.YELLOW,"?");
                    fields[rowInMatrix][colInMatrix] = chosenField;
                }
                if (clicked instanceof  GreenField) {
                    GreenField greenField=(GreenField) clicked;
                    if (greenField.isEnding()) {
                        displayEnding();
                        chosenField = null;

                    }
                }
            }
        }
        super.repaint();

    }

    /**
     * shows ending window
     */
    private void displayEnding() {
        JDialog dialog=new JDialog(this);
        dialog.setLayout(new FlowLayout());
        dialog.add(new JLabel("Igrata prikluchi"));
        JButton restart_btn = new JButton("Restartirai igrata");
        restart_btn.addActionListener((i)->restartGame());
        JButton close_btn=new JButton("Zatvori igrata");
        close_btn.addActionListener((l)->System.exit(0));
        dialog.add(restart_btn);
        dialog.add(close_btn);

        dialog.setSize(300,300);
        dialog.setVisible(true);

    }

    /**
     * restart the game
     */
    private void restartGame() {
        this.fields = new BasicField[8][8];
        this.generatePlayingFields();
        this.initWindow();
        super.repaint();


    }


    /**
     * generate random field
     * @param row row of the field
     * @param col col of the field
     * @return
     */
    private BasicField generateRandomField(int row,int col){
        Random random=new Random();

        return  random.nextInt(100)<20? new BlueField(row,col,Color.BLUE):new YellowField(row,col, Color.YELLOW,"");
    }

    /**
     * check if the move is valid
     * @param row row of the desired position
     * @param col col of the desired position
     * @return
     */
    private boolean isValid(int row, int col) {


        int[][]coordinates={{col,row-1},{col,row+1},{col + 1, row},{col - 1, row}};


        for (int i = 0; i < coordinates.length; i++) {
            int secondPos=0;
                int x=coordinates[i][secondPos];
                int y=coordinates[i][secondPos+1];
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
