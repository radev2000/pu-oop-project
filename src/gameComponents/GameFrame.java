package gameComponents;

import pieces.Dwarf;
import pieces.Elf;
import pieces.Knight;
import pieces.Stone;
import ui.Modal;

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
    public static boolean isGreenPlayerTurn = true;

    private int greenPiecesLeft = 6;
    private int redPiecesLeft = 6;

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
    public static int playerOnePoints;
    public static int playerTwoPoints;
    public GameFrame(){

        this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);

        this.setTitle("Knights, Elfs and Dwarfs");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addMouseListener(this);
        this.setLocation(50, 50);
        this.setLayout(null);

    }


    @Override
    public void mouseClicked(MouseEvent e) {

        int selectedRow = e.getY() / TILE_SIZE;
        int selectedCol = e.getX() / TILE_SIZE;

        System.out.println("P1 : " + playerOnePoints);
        System.out.println("P2 : " + playerTwoPoints);

        unselect(selectedRow, selectedCol);
        heal(selectedRow, selectedCol);

        if(isGameRunning()){

            if(isGreenPlayerTurn) {
                greenTurn(selectedRow, selectedCol);
                return;
            }
            redTurn(selectedRow, selectedCol);
        }
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

        renderTiles(g);

        if(!arePiecesPlaced) {

            placePieces();
        }

        try{

            BufferedImage healButton = ImageIO.read(getClass().getResourceAsStream("/resources/images/healButton.png"));
            g.drawImage(healButton, 925, 435, 250, 250, null);

            BufferedImage unselectButton = ImageIO.read(getClass().getResourceAsStream("/resources/images/unselectButton.png"));
            g.drawImage(unselectButton, 925, 335, 250, 250, null);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        renderPieces(g);
        renderTiledGrid(g);

        if(isTileSelected){
            checkPossibilities(g);
            showStats(g);
        }
        else{
            g.clearRect(910, 10, 1200, 385);
        }
    }

    private void showStats(Graphics g){

        if(selectedTile.getPiece() != null) {

            g.setFont(new Font("TimesRoman", Font.PLAIN, 35));
            g.drawString("Current HP: " + selectedTile.getPiece().getHp(), 930, 350);
            g.drawString("MAX HP: " + selectedTile.getPiece().getMaxHP(), 950, 250);
            g.drawString("Piece: " + selectedTile.getPiece().getPieceType(), 930, 150);

        }
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


    private void unselect(int givenRow, int givenCol){

        if(givenRow == 4 && (givenCol == 9 || givenCol == 10 || givenCol == 11)){
            if(selectedTile != null){
                selectedTile = null;
                initialTile = null;
                repaint();
            }
        }
    }


    private void heal(int givenRow, int givenCol){

        if(givenRow == 5 && (givenCol == 9 || givenCol == 10 || givenCol == 11)) {

            if (selectedTile != null) {

                if (this.selectedTile.getPiece().getPotion()) {

                    int dice = random.nextInt(5) + 1;
                    this.selectedTile.getPiece().potionUsed();
                    int newHP = this.selectedTile.getPiece().getHp() + dice;
                    this.selectedTile.getPiece().setHp(newHP);
                    Modal modal = new Modal(this, "Heal", dice +" hp added!");
                    repaint();
                    if(dice % 2 == 1){
                        unselect(givenRow, givenCol);
                    }else {
                        this.initialTile = null;
                        this.selectedTile = null;
                        if (isGreenPlayerTurn) {
                            isGreenPlayerTurn = false;
                            return;
                        }
                        isGreenPlayerTurn = true;
                    }

                } else {
                    Modal modal = new Modal(this, "Heal", "This Piece has no Potion!");
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
                            BufferedImage icon = ImageIO.read(getClass().getResourceAsStream("/resources/images/greenX.png"));
                            g.drawImage(icon, col * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);

                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(this.initialTile.getPiece().isAttackValid(row,col, this.tileCollection,this.initialTile)){
                        try{
                            BufferedImage icon = ImageIO.read(getClass().getResourceAsStream("/resources/images/redX.png"));
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

        while(stonesPlaced < currentStonesLimit){
            final int row = random.nextInt(3) + 2;
            final int col = random.nextInt(9);
            if(this.tileCollection[row][col].getPiece() == null){
                this.tileCollection[row][col].setPiece(new Stone(row, col, "NEUTRAL"));
                stonesPlaced ++;
            }
        }

        this.tileCollection[1][3].setPiece(new Elf(1,3, "RED", "ELF", 10));
        this.tileCollection[1][5].setPiece(new Elf(1,5, "RED", "ELF", 10));
        this.tileCollection[1][1].setPiece(new Dwarf(1,1, "RED", "DWARF", 12));
        this.tileCollection[1][7].setPiece(new Dwarf(1,7, "RED", "DWARF", 12));
        this.tileCollection[0][2].setPiece(new Knight(0,2, "RED", "KNIGHT", 15));
        this.tileCollection[0][6].setPiece(new Knight(0,6, "RED", "KNIGHT", 15));

        this.tileCollection[5][3].setPiece(new Elf(5,3, "GREEN", "ELF", 10));
        this.tileCollection[5][5].setPiece(new Elf(5,5, "GREEN", "ELF", 10));
        this.tileCollection[5][1].setPiece(new Dwarf(5,1, "GREEN", "DWARF", 12));
        this.tileCollection[5][7].setPiece(new Dwarf(5,7, "GREEN", "DWARF", 12));
        this.tileCollection[6][2].setPiece(new Knight(6,2, "GREEN", "KNIGHT", 15));
        this.tileCollection[6][6].setPiece(new Knight(6,6, "GREEN", "KNIGHT", 15));

        this.arePiecesPlaced = true;
    }


    private void greenTurn(int selectedRow, int selectedCol){

        if(isGreenPlayerTurn){

            if(this.selectedTile != null){

                if(this.tileCollection[selectedRow][selectedCol].getPiece() == null &&
                        this.initialTile.getPiece().isMoveValid(selectedRow, selectedCol, this.tileCollection)){

                    movement(selectedRow, selectedCol);
                    this.repaint();
                    isGreenPlayerTurn = false;
                    return;
                }
                if(this.tileCollection[selectedRow][selectedCol].getPiece() != null &&
                        this.initialTile.getPiece().isAttackValid(selectedRow, selectedCol, tileCollection, initialTile)){
                    attack(selectedRow, selectedCol);
                    isGreenPlayerTurn = false;
                    return;
                }
            }
            if(this.selectedTile == null && tileCollection[selectedRow][selectedCol].getPiece().getTeam().equals("GREEN")){
                selectPiece(selectedRow, selectedCol);
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
                    isGreenPlayerTurn = true;
                    return;
                }
                if(this.tileCollection[selectedRow][selectedCol].getPiece() != null &&
                        this.initialTile.getPiece().isAttackValid(selectedRow, selectedCol, tileCollection, initialTile)){
                    attack(selectedRow, selectedCol);
                    isGreenPlayerTurn = true;
                    return;
                }
            }
            if(this.selectedTile == null && tileCollection[selectedRow][selectedCol].getPiece().getTeam().equals("RED")){
                selectPiece(selectedRow, selectedCol);
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

        String winner;
        if(greenPiecesLeft <= 0){
            winner = "Red Player Won!";
            return false;
        }
        if(redPiecesLeft <= 0){
            winner = "Green Player Won!";
            return false;
        }
        return true;
    }


    private void destroyDeadPiece(int row, int col){

        if (this.tileCollection[row][col].getPiece().getHp() <= 0){

            if(this.tileCollection[row][col].getPiece().getTeam().equals("GREEN")){
                this.greenPiecesLeft--;
            }
            else if (this.tileCollection[row][col].getPiece().getTeam().equals("RED")){
            this.redPiecesLeft--;
            }
            this.tileCollection[row][col].setPiece(null);
            repaint();
        }
    }


    private void attack(int givenRow, int givenCol){

        int diceOne   = random.nextInt(4) + 1;
        int diceTwo   = random.nextInt(4) + 1;
        int diceThree = random.nextInt(4) + 1;
        int diceSum   = diceOne + diceTwo + diceThree;
        int dmgDealt  = this.initialTile.getPiece().getDmg() - this.tileCollection[givenRow][givenCol].getPiece().getArmor();

        if(diceSum == this.tileCollection[givenRow][givenCol].getPiece().getHp()){
            dmgDealt  = 0;
        }
        else if(diceSum == 3){
            dmgDealt    /= 2;
        }
        if(isGreenPlayerTurn)  playerOnePoints += dmgDealt;
        if(!isGreenPlayerTurn) playerTwoPoints += dmgDealt;
        int hpLeft = this.tileCollection[givenRow][givenCol].getPiece().getHp() - dmgDealt;
        this.tileCollection[givenRow][givenCol].getPiece().setHp(hpLeft);
        this.initialTile = null;
        this.selectedTile = null;
        this.isTileSelected = false;
        repaint();
        destroyDeadPiece(givenRow, givenCol);
    }


    public void movement(int givenRow, int givenCol){

            String team = initialTile.getPiece().getTeam();
            int currentHP = initialTile.getPiece().getHp();

            switch (this.initialTile.getPiece().getPieceType()) {
                case "KNIGHT" -> this.tileCollection[givenRow][givenCol].setPiece(new Knight(givenRow, givenCol, team, "KNIGHT", currentHP));
                case "ELF" -> this.tileCollection[givenRow][givenCol].setPiece(new Elf(givenRow, givenCol, team, "ELF", currentHP));
                case "DWARF" -> this.tileCollection[givenRow][givenCol].setPiece(new Dwarf(givenRow, givenCol, team, "DWARF", currentHP));
            }
            this.initialTile.setPiece(null);
            this.initialTile = null;
            this.selectedTile.setPiece(null);
            this.selectedTile = null;
    }
}
