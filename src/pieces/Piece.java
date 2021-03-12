package pieces;

import gameComponents.GameFrame;
import gameComponents.Tile;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class Piece {


    public static int initialRow;
    public static int initialCol;

    protected int dmg;
    protected int armor;
    protected int hp;
    protected int maxHP;
    protected int movementLimit;
    protected int attackRange;
    protected int currentRow;
    protected int currentCol;
    protected int movesPerformed;

    protected String pieceType;
    protected String imageSource;
    protected String team;

    private int loops;
    private boolean moveResult = false;
    private boolean hasPotion = true;


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


    //// SETTERS ////


    public void setHp(int hp) {
        this.hp = hp;
    }


//    public void setPieceType(String pieceType) {
//        this.pieceType = pieceType;
//    }
//
//
//    public void setCurrentRow(int currentRow) {
//        this.currentRow = currentRow;
//    }
//
//
//    public void setCurrentCol(int currentCol) {
//        this.currentCol = currentCol;
//    }


    protected void setArmor(int armor) {
        this.armor = armor;
    }


    protected void setDmg(int dmg) {
        this.dmg = dmg;
    }


    protected void setMovementLimit(int movementLimit) {
        this.movementLimit = movementLimit;
    }


    protected void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }



    //// GETTERS ////


    public String getPieceType() {
        return pieceType;
    }


    public String getTeam() {
        return team;
    }


    public int getDmg() {
        return dmg;
    }


    public int getArmor() {
        return armor;
    }


    public int getHp() {
        return hp;
    }


    public int getMaxHP(){ return maxHP; }

//
//    public int getMovementLimit() {
//        return movementLimit;
//    }


    public int getAttackRange() {
        return attackRange;
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


    //// MOVEMENT ////


    public boolean isMoveValid(int givenRow, int givenCol, Tile[][] tileCollection){

        if(movePreValidator(givenRow, givenCol)) {
            if (givenRow >= 0 && givenRow < GameFrame.ROW_LIMIT &&
                    givenCol >= 0 && givenCol < GameFrame.COL_LIMIT) {

                if (givenRow != this.currentRow || givenCol != this.currentCol) {

                    initialRow = this.currentRow;
                    initialCol = this.currentCol;
                    moveValidWithHorizontalPrio(givenRow, givenCol, tileCollection);
                    if (this.moveResult) {
                        return true;
                    } else {
                        moveValidWithVerticalPrio(givenRow, givenCol, tileCollection);
                        if (this.moveResult) {
                            return true;
                        }
                    }

                    this.currentRow = initialRow;
                    this.currentCol = initialCol;
                    this.moveResult = false;
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }


    private void moveValidWithVerticalPrio(int givenRow, int givenCol, Tile[][] tileCollection) {

        while (this.movesPerformed < this.movementLimit && loops < 5) {

            moveVertical(givenRow, tileCollection);
            if (this.movesPerformed < this.movementLimit) {
                moveHorizontal(givenCol, tileCollection);
            }
            if ((givenRow == this.currentRow && givenCol == this.currentCol)) {
                this.movesPerformed = 0;
                this.currentRow = initialRow;
                this.currentCol = initialCol;
                this.moveResult = true;
                return;
            }
            if (this.movesPerformed == this.movementLimit) {
                this.movesPerformed = 0;
                this.currentRow = initialRow;
                this.currentCol = initialCol;
                this.moveResult = false;
                return;
            }
            loops++;
            if(loops > 4){
                this.movesPerformed = 0;
                this.currentRow = initialRow;
                this.currentCol = initialCol;
                this.moveResult = false;
                loops = 0;
                return;
            }
        }

    }


    private void moveValidWithHorizontalPrio(int givenRow, int givenCol, Tile[][] tileCollection) {

        while (this.movesPerformed < this.movementLimit && loops < 5) {

            moveHorizontal(givenCol, tileCollection);
            if (this.movesPerformed < this.movementLimit) {
                moveVertical(givenRow, tileCollection);
            }
            if ((givenRow == this.currentRow && givenCol == this.currentCol)) {
                this.movesPerformed = 0;
                this.moveResult = true;
                this.currentRow = initialRow;
                this.currentCol = initialCol;
                return;
            }
            if (this.movesPerformed == this.movementLimit) {
                this.movesPerformed = 0;
                this.currentRow = initialRow;
                this.currentCol = initialCol;
                this.moveResult = false;
                return;
            }
            loops++;
            if(loops > 4){
                this.movesPerformed = 0;
                this.currentRow = initialRow;
                this.currentCol = initialCol;
                this.moveResult = false;
                loops = 0;
                return;
            }
        }
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


    private boolean movePreValidator(int givenRow, int givenCol){

        int coordinatesSum = this.currentRow + this.currentCol;
        int givenSum = givenRow + givenCol;
        return givenSum - coordinatesSum <= 3 ||
                givenSum - coordinatesSum >= 3;
    }

    //// ATTACK ////

    public boolean isAttackValid(int givenRow, int givenCol, Tile[][] tileCollection, Tile attackerTile){

        if(tileCollection[givenRow][givenCol].getPiece() != null) {
            if (GameFrame.isGreenPlayerTurn) {
                if (!tileCollection[givenRow][givenCol].getPiece().getTeam().equals("GREEN")) {
                    return getAttackResult(givenRow, givenCol, tileCollection, attackerTile);
                }
            }
            if (!GameFrame.isGreenPlayerTurn) {
                if (!tileCollection[givenRow][givenCol].getPiece().getTeam().equals("RED")) {
                    return getAttackResult(givenRow, givenCol, tileCollection, attackerTile);
                }
            }
        }
        return false;
    }


    private boolean getAttackResult(int attackedRow, int attackedCol, Tile[][] tileCollection, Tile attackerTile){

        int attackerRow = attackerTile.getRowIndex();
        int attackerCol = attackerTile.getColIndex();

        // Vertical attack
        if(attackerCol == attackedCol){
            if(attackerRow - attackedRow == attackerTile.getPiece().getAttackRange() &&
                    (tileCollection[attackerRow - 1][attackerCol].getPiece() == null  ||
                    attackerTile.getPiece().getPieceType().equals("ELF"))){
                return true;
            }
            if(attackerRow - attackedRow == - attackerTile.getPiece().getAttackRange() &&
                    (tileCollection[attackerRow + 1][attackerCol].getPiece() == null  ||
                    attackerTile.getPiece().getPieceType().equals("ELF"))){
                return true;
            }
        }
        // Horizontal attack
        if(attackerRow == attackedRow){
            if(attackerCol - attackedCol == attackerTile.getPiece().getAttackRange() &&
                    (tileCollection[attackerRow + 1][attackerCol].getPiece() == null  ||
                            attackerTile.getPiece().getPieceType().equals("ELF"))){
                return true;
            }
            return attackerCol - attackedCol == -attackerTile.getPiece().getAttackRange() &&
                    (tileCollection[attackerRow + 1][attackerCol].getPiece() == null  ||
                            attackerTile.getPiece().getPieceType().equals("ELF"));
        }
        return false;
    }

    public boolean getPotion(){
        return this.hasPotion;
    }


    public void potionUsed(){
        this.hasPotion = false;
    }
}
