package gameComponents;

import pieces.Piece;

import java.awt.*;

public class Tile {

    private final int TILE_SIZE = 100;
    private int rowIndex;
    private int colIndex;
    private Color color;
    private Piece piece;

    public Tile(int rowIndex, int colIndex, Piece piece){

        this.piece = piece;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }


    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void render(Graphics g){
        g.setColor(this.color);
        g.fillRect(rowIndex * TILE_SIZE, colIndex * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
}
