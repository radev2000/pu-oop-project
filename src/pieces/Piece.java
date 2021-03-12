package pieces;

import gameComponents.GameFrame;
import gameComponents.Tile;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Piece {

    protected int dmg;
    protected int armor;
    protected int hp;
    protected int movementLimit;
    protected int attackRange;
    protected String pieceType;
    public static int initialRow;
    public static int initialCol;
    protected int currentRow;
    protected int currentCol;
    protected int movesPerformed;
    protected String imageSource;
    protected String team;
    private int loops;
    private boolean result = false;

    public String getPieceType() {
        return pieceType;
    }

    public void setPieceType(String pieceType) {
        this.pieceType = pieceType;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }


    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
    }


    public String getTeam() {
        return team;
    }


    public int getDmg() {
        return dmg;
    }


    protected void setDmg(int dmg) {
        this.dmg = dmg;
    }


    public int getArmor() {
        return armor;
    }


    protected void setArmor(int armor) {
        this.armor = armor;
    }

    public int getHp() {
        return hp;
    }

    protected void setHp(int hp) {
        this.hp = hp;
    }

    public int getMovementLimit() {
        return movementLimit;
    }

    protected void setMovementLimit(int movementLimit) {
        this.movementLimit = movementLimit;
    }

    public int getAttackRange() {
        return attackRange;
    }

    protected void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    private String getMovementHorizontalDirection(int givenCol) {
        if (givenCol < this.currentCol) {
            return "LEFT";
        } else if (givenCol > this.currentCol) {
            return "RIGHT";
        }
        return "CURRENT";
    }

    private String getMovementVerticalDirection(int givenRow) {
        if (givenRow < this.currentRow) {
            return "UP";
        } else if (givenRow > this.currentRow) {
            return "DOWN";
        }
        return "CURRENT";
    }

    private void moveHorizontal(int givenCol, Tile[][] tileCollection) {

        if (getMovementHorizontalDirection(givenCol).equals("LEFT") &&
                tileCollection[this.currentRow][this.currentCol - 1].getPiece() == null) {
            this.currentCol--;
            this.movesPerformed++;
        } else if (getMovementHorizontalDirection(givenCol).equals("RIGHT") &&
                tileCollection[this.currentRow][this.currentCol + 1].getPiece() == null
        ) {
            this.currentCol++;
            this.movesPerformed++;
        }
    }

    private void moveVertical(int givenRow, Tile[][] tileCollection) {

        if (getMovementVerticalDirection(givenRow).equals("UP") &&
                tileCollection[this.currentRow - 1][this.currentCol].getPiece() == null) {
            currentRow--;
            movesPerformed++;
        } else if (getMovementVerticalDirection(givenRow).equals("DOWN") &&
                tileCollection[this.currentRow + 1][this.currentCol].getPiece() == null) {
            currentRow++;
            movesPerformed++;
        }
    }

    private void isMoveValidWithVerticalPrio(int givenRow, int givenCol, Tile[][] tileCollection) {

        while (this.movesPerformed < this.movementLimit && loops < 5) {

            moveVertical(givenRow, tileCollection);
            if (this.movesPerformed < this.movementLimit) {
                moveHorizontal(givenCol, tileCollection);
            }
            if ((givenRow == this.currentRow && givenCol == this.currentCol)) {
                this.movesPerformed = 0;
                this.currentRow = initialRow;
                this.currentCol = initialCol;
                this.result = true;
                return;
            }
            if (this.movesPerformed == this.movementLimit) {
                this.movesPerformed = 0;
                this.currentRow = initialRow;
                this.currentCol = initialCol;
                this.result = false;
                return;
            }
            loops++;
            if(loops > 4){
                this.movesPerformed = 0;
                this.currentRow = initialRow;
                this.currentCol = initialCol;
                this.result = false;
                loops = 0;
                return;
            }
        }

    }

    private void isMoveValidWithHorizontalPrio(int givenRow, int givenCol, Tile[][] tileCollection) {

        while (this.movesPerformed < this.movementLimit && loops < 5) {

            moveHorizontal(givenCol, tileCollection);
            if (this.movesPerformed < this.movementLimit) {
                moveVertical(givenRow, tileCollection);
            }
            if ((givenRow == this.currentRow && givenCol == this.currentCol)) {
                this.movesPerformed = 0;
                this.result = true;
                this.currentRow = initialRow;
                this.currentCol = initialCol;
                return;
            }
            if (this.movesPerformed == this.movementLimit) {
                this.movesPerformed = 0;
                this.currentRow = initialRow;
                this.currentCol = initialCol;
                this.result = false;
                return;
            }
            loops++;
            if(loops > 4){
                this.movesPerformed = 0;
                this.currentRow = initialRow;
                this.currentCol = initialCol;
                this.result = false;
                loops = 0;
                return;
            }
        }
    }

    public boolean isMoveValid(int givenRow, int givenCol, Tile[][] tileCollection){

        if(givenRow >= 0 && givenRow < GameFrame.ROW_LIMIT &&
                givenCol >= 0 && givenCol < GameFrame.COL_LIMIT ){

            if(givenRow != this.currentRow || givenCol != this.currentCol){

                initialRow = this.currentRow;
                initialCol = this.currentCol;

                isMoveValidWithHorizontalPrio(givenRow, givenCol, tileCollection);

                if(this.result){
                    return true;
                }
                else {
                    isMoveValidWithVerticalPrio(givenRow, givenCol, tileCollection);

                    if (this.result) {
                        return true;
                    }
                }

                this.currentRow = initialRow;
                this.currentCol = initialCol;
                this.result = false;
                return false;
            }
            return false;
        }
        return false;
    }

    public void render(Graphics g) {

        int x = this.currentCol * GameFrame.TILE_SIZE;
        int y = this.currentRow * GameFrame.TILE_SIZE;

        try{
            BufferedImage icon = ImageIO.read(getClass().getResourceAsStream(this.imageSource));
            g.drawImage(icon, x, y, GameFrame.TILE_SIZE, GameFrame.TILE_SIZE, null);
        }
        catch(IOException e){
        e.printStackTrace();
        }

    }
}
