package pieces;

public class Stone extends Piece{

    public Stone(int currentRow, int currentCol){

        this.imageSource = "/resources/images/stone.png";
        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.setHp(1);
        this.setArmor(0);
    }
}
