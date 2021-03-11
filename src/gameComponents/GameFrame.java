package gameComponents;

import pieces.Dwarf;
import pieces.Elf;
import pieces.Knight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameFrame extends JFrame implements MouseListener{

    private final int SCREEN_WIDTH    = 1200;
    private final int SCREEN_HEIGHT   = 700;
    private final int BOARD_WIDTH     = 900;
    private final int NULL            = 0;
    private boolean arePiecesPlaced   = false;

    public static final int TILE_SIZE = 100;
    public static final int ROW_LIMIT = 7;
    public static final int COL_LIMIT = 9;

    private final Tile[][] tileCollection = new Tile[ROW_LIMIT][COL_LIMIT];
    private Tile selectedTile;
    private Tile initialTile;

    public GameFrame(){

        this.setSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        //this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.addMouseListener(this);
    }


    @Override
    public void paint(Graphics g){

        super.paint(g);
        renderTiles(g);

        if(!arePiecesPlaced) {

            placePieces();
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

    @Override
    public void mouseClicked(MouseEvent e) {

        int selectedRow = e.getY() / TILE_SIZE;
        int selectedCol = e.getX() / TILE_SIZE;

        if(this.selectedTile != null){

            if(this.tileCollection[selectedRow][selectedCol].getPiece() == null){

                if(this.initialTile.getPiece().isMoveValid(selectedRow, selectedCol)){

                    movement(selectedRow, selectedCol);
                    this.repaint();
                    return;
                }
            }
        }

        if(this.selectedTile == null && tileCollection[selectedRow][selectedCol].getPiece() != null){

            this.selectedTile = tileCollection[selectedRow][selectedCol];
            this.initialTile = selectedTile;
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

    private void placePieces(){

        this.tileCollection[0][0].setPiece(new Elf(0,0, "RED"));
        this.tileCollection[0][1].setPiece(new Dwarf(0,1, "RED"));
        this.tileCollection[0][2].setPiece(new Knight(0,2, "RED"));
        this.tileCollection[1][0].setPiece(new Elf(1,0, "RED"));
        this.tileCollection[1][1].setPiece(new Dwarf(1,1, "RED"));
        this.tileCollection[1][2].setPiece(new Knight(1,2, "RED"));
        this.arePiecesPlaced = true;
    }

    public void movement(int givenRow, int givenCol){

            this.tileCollection[givenRow][givenCol].setPiece(this.initialTile.getPiece());

            this.initialTile.setPiece(null);
            this.selectedTile = null;
            this.initialTile = null;

    }
}
