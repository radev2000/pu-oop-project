package pieces;

public class Knight extends Piece {

    public Knight(int currentRow, int currentCol){

        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.imageSource = "/resources/images/knight.png";

        setDmg(8);
        setArmor(3);
        setHp(15);
        setMovementLimit(1);
        setAttackRange(1);
    }
}
