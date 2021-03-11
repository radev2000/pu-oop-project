package pieces;

public class Knight extends Piece {

    public Knight(int currentRow, int currentCol, String team){

        this.team = team;
        this.currentRow = currentRow;
        this.currentCol = currentCol;

        if (team.equals("RED")) {
            this.imageSource = "/resources/images/knightR.png";
        }else if(team.equals("GREEN")){
            this.imageSource = "/resources/images/knightG.png";
        }

        setDmg(8);
        setArmor(3);
        setHp(15);
        setMovementLimit(1);
        setAttackRange(1);
    }
}
