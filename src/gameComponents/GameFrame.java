package gameComponents;

import pieces.Dwarf;
import pieces.Elf;
import pieces.Knight;
import pieces.Stone;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class GameFrame extends JFrame implements MouseListener{

    private final int SCREEN_WIDTH    = 1200;
    private final int SCREEN_HEIGHT   = 700;
    private final int BOARD_WIDTH     = 900;
    private final int NULL            = 0;
    private boolean arePiecesPlaced   = false;
    private boolean isTileSelected    = false;
    private boolean isGreenPlayerTurn = true;

    private int greenPiecesLeft = 4;
    private int redPiecesLeft = 4;

    public static final int TILE_SIZE = 100;
    public static final int ROW_LIMIT = 7;
    public static final int COL_LIMIT = 9;

    private final Tile[][] tileCollection = new Tile[ROW_LIMIT][COL_LIMIT];
    private Tile selectedTile;
    private Tile initialTile;
    private Random random = new Random();
    private final int totalStonesLimit = 5;
    private final int currentStonesLimit = random.nextInt(totalStonesLimit - 1) + 1;
    private       int stonesPlaced;
    private int initialRow;
    private int initialCol;
    public GameFrame(){

        this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        //this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addMouseListener(this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

        int selectedRow = e.getY() / TILE_SIZE;
        int selectedCol = e.getX() / TILE_SIZE;

        if(isGameRunning()){

            greenTurn(selectedRow, selectedCol);

            redTurn(selectedRow, selectedCol);
        }
    }

    private void greenTurn(int selectedRow, int selectedCol){

        if(isGreenPlayerTurn){

            if(this.selectedTile != null){

                if(this.tileCollection[selectedRow][selectedCol].getPiece() == null &&
                        this.initialTile.getPiece().isMoveValid(selectedRow, selectedCol, this.tileCollection)){

                    movement(selectedRow, selectedCol);
                    this.repaint();
                    this.isGreenPlayerTurn = false;
                    return;
                }
            }
            if(this.selectedTile == null && tileCollection[selectedRow][selectedCol].getPiece().getTeam().equals("GREEN")){
                selectPiece(selectedRow, selectedCol);
                return;
            }

        }
    }

    private void redTurn(int selectedRow, int selectedCol){

        if(!isGreenPlayerTurn){

            if(this.selectedTile != null){

                if(this.tileCollection[selectedRow][selectedCol].getPiece() == null &&
                        this.initialTile.getPiece().isMoveValid(selectedRow, selectedCol, this.tileCollection)){

                    movement(selectedRow, selectedCol);
                    this.repaint();
                    this.isGreenPlayerTurn = true;
                    return;
                }
            }
            if(this.selectedTile == null && tileCollection[selectedRow][selectedCol].getPiece().getTeam().equals("RED")){
                selectPiece(selectedRow, selectedCol);
                return;
            }
        }
    }


    private void selectPiece(int selectedRow, int selectedCol){

        this.selectedTile = tileCollection[selectedRow][selectedCol];
        this.initialTile = selectedTile;
        isTileSelected = true;
        repaint();
    }

    private boolean isGameRunning(){

        if(greenPiecesLeft < 0){
            System.out.println("Red Player Won!");
            return false;
        }
        if(redPiecesLeft < 0){
            System.out.println("Green Player Won!");
            return false;
        }
        return true;
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

    @Override
    public void paint(Graphics g){

        super.paint(g);
        renderTiles(g);

        if(!arePiecesPlaced) {

            placePieces();
        }

        if(isTileSelected){
            checkPossibilities(g);
        }

        renderPieces(g);
        renderTiledGrid(g);
    }


    private void renderTiles(Graphics g){

        for(int row = NULL; row < ROW_LIMIT; row++) {

            for (int col = NULL; col < COL_LIMIT; col++) {

                if(!arePiecesPlaced) {
                    this.tileCollection[row][col] = new Tile(row, col, null);
                }

                if(row == 2 || row == 3 || row == 4){
                    this.tileCollection[row][col].setColor(Color.WHITE);
                }
                else if((row + col) % 2 == NULL) {
                    this.tileCollection[row][col].setColor(Color.GRAY);
                }
                else{
                    this.tileCollection[row][col].setColor(Color.BLACK);
                }
                this.tileCollection[row][col].render(g);
            }
        }
    }


    private void renderPieces(Graphics g){

        for (Tile[] tiles : this.tileCollection) {

            for (Tile tile : tiles) {

                if (tile.getPiece() != null) {

                    tile.getPiece().render(g);
                }
            }
        }
    }


    private void renderTiledGrid(Graphics g){

        g.setColor(Color.BLACK);
        for (int index = NULL; index <= COL_LIMIT; index++) {

            g.drawLine(index * TILE_SIZE, NULL, index * TILE_SIZE , SCREEN_HEIGHT);
            g.drawLine(NULL, index * TILE_SIZE, BOARD_WIDTH, index * TILE_SIZE);
        }
    }


    private void checkPossibilities(Graphics g){

        if(initialTile.getPiece() != null) {

            for (int row = 0; row < ROW_LIMIT; row++) {

                for (int col = 0; col < COL_LIMIT; col++) {

                    if (this.initialTile.getPiece().isMoveValid(row, col, this.tileCollection)) {

                        try{
                            BufferedImage icon = ImageIO.read(getClass().getResourceAsStream("/resources/images/greenX.jpg"));
                            g.drawImage(icon, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);

                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }
            isTileSelected = false;
        }
    }


    private void placePieces(){

        this.tileCollection[1][3].setPiece(new Elf(1,3, "RED", "ELF"));
        this.tileCollection[1][5].setPiece(new Elf(1,5, "RED", "ELF"));
        this.tileCollection[1][1].setPiece(new Dwarf(1,1, "RED", "DWARF"));
        this.tileCollection[1][7].setPiece(new Dwarf(1,7, "RED", "DWARF"));
        this.tileCollection[0][2].setPiece(new Knight(0,2, "RED", "KNIGHT"));
        this.tileCollection[0][6].setPiece(new Knight(0,6, "RED", "KNIGHT"));

        this.tileCollection[5][3].setPiece(new Elf(5,3, "GREEN", "ELF"));
        this.tileCollection[5][5].setPiece(new Elf(5,5, "GREEN", "ELF"));
        this.tileCollection[5][1].setPiece(new Dwarf(5,1, "GREEN", "DWARF"));
        this.tileCollection[5][7].setPiece(new Dwarf(5,7, "GREEN", "DWARF"));
        this.tileCollection[6][2].setPiece(new Knight(6,2, "GREEN", "KNIGHT"));
        this.tileCollection[6][6].setPiece(new Knight(6,6, "GREEN", "KNIGHT"));

        while(stonesPlaced < currentStonesLimit){
            int row = random.nextInt(3) + 2;
            int col = random.nextInt(9);
            if(this.tileCollection[row][col].getPiece() == null){
                this.tileCollection[row][col].setPiece(new Stone(row, col));
                stonesPlaced ++;
            }
        }

        this.arePiecesPlaced = true;
    }


    public void movement(int givenRow, int givenCol){

            String team = initialTile.getPiece().getTeam();

            switch (this.initialTile.getPiece().getPieceType()) {
                case "KNIGHT" -> this.tileCollection[givenRow][givenCol].setPiece(new Knight(givenRow, givenCol, team, "KNIGHT"));
                case "ELF" -> this.tileCollection[givenRow][givenCol].setPiece(new Elf(givenRow, givenCol, team, "ELF"));
                case "DWARF" -> this.tileCollection[givenRow][givenCol].setPiece(new Dwarf(givenRow, givenCol, team, "DWARF"));
            }
            this.initialTile.setPiece(null);
            this.initialTile = null;
            this.selectedTile.setPiece(null);
            this.selectedTile = null;
    }
}
