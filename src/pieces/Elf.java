package pieces;

public class Elf extends Piece {

    public Elf(int currentRow, int currentCol){

        this.currentRow = currentRow;
        this.currentCol = currentCol;
        this.imageSource = "/resources/images/elf.png";

        setDmg(5);
        setArmor(1);
        setHp(10);
        setMovementLimit(3);
        setAttackRange(3);
    }
}
