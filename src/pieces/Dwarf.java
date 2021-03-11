package pieces;

public class Dwarf extends Piece {

    public Dwarf(int currentRow, int currentCol){

        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.imageSource = "/resources/images/dwarf.png";

        setDmg(6);
        setArmor(2);
        setHp(12);
        setMovementLimit(2);
        setAttackRange(2);
    }
}
