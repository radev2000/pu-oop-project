package pieces;

import gameComponents.GameFrame;
import gameComponents.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Piece {

    protected int dmg;
    protected int armor;
    protected int hp;
    protected int movementLimit;
    protected int attackRange;
    protected int currentRow;
    protected int currentCol;
    protected int movesPerformed;
    protected String imageSource;

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public void setCurrentCol(int currentCol) {
        this.currentCol = currentCol;
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

    private String getMovementHorizontalDirection(int givenCol){
        if(givenCol < this.currentCol){
            return "LEFT";
        }
        else if(givenCol > this.currentCol){
            return "RIGHT";
        }
        return "CURRENT";
    }

    private String getMovementVerticalDirection(int givenRow){
        if(givenRow < this.currentRow){
            return "UP";
        }
        else if(givenRow > this.currentRow){
            return "DOWN";
        }
        return "CURRENT";
    }

    private void moveHorizontal(int givenCol){

        if(getMovementHorizontalDirection(givenCol).equals("LEFT")){
            this.currentCol--;
            this.movesPerformed++;
        }
        else if(getMovementHorizontalDirection(givenCol).equals("RIGHT")){
            this.currentCol++;
            this.movesPerformed++;
        }
    }

    private void moveVertical(int givenRow){

        if(getMovementVerticalDirection(givenRow).equals("UP")){
            currentRow--;
            movesPerformed++;
        }
        else if(getMovementVerticalDirection(givenRow).equals("DOWN")){
            currentRow++;
            movesPerformed++;
        }
    }

    public boolean isMoveValid(int givenRow, int givenCol){
        if(givenRow >= 0 && givenRow < GameFrame.ROW_LIMIT &&
           givenCol >= 0 && givenCol < GameFrame.COL_LIMIT){

            if(givenRow != this.currentRow || givenCol != this.currentCol){

                while(this.movesPerformed <= this.movementLimit){

                    moveVertical(givenRow);
                    moveHorizontal(givenCol);

                    if(givenRow == this.currentRow && givenCol == this.currentCol){
                        this.movesPerformed = 0;
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public void render(Graphics g) {

        int x = this.currentCol * GameFrame.TILE_SIZE;
        int y = this.currentRow * GameFrame.TILE_SIZE;

        try{
            BufferedImage icon = ImageIO.read(getClass().getResourceAsStream(imageSource));
            g.drawImage(icon, x, y, GameFrame.TILE_SIZE, GameFrame.TILE_SIZE, null);
        }
        catch(Exception e){
        e.printStackTrace();
        }

    }
}
