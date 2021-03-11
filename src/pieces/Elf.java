package pieces;

public class Elf extends Piece {

    public Elf(int currentRow, int currentCol, String team){

        this.team = team;
        this.currentRow = currentRow;
        this.currentCol = currentCol;

        if (team.equals("RED")) {
            this.imageSource = "/resources/images/elfR.png";
        }else if(team.equals("GREEN")){
            this.imageSource = "/resources/images/elfG.png";
        }

        setDmg(5);
        setArmor(1);
        setHp(10);
        setMovementLimit(3);
        setAttackRange(3);
    }
}
