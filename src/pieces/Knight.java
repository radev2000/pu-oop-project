package pieces;

public class Knight extends Piece {

    public Knight(int currentRow, int currentCol, String team, String type, int hp){

        this.maxHP           = 15;
        this.hp              = hp;
        this.pieceType       = type;
        this.team            = team;
        this.currentRow      = currentRow;
        this.currentCol      = currentCol;

        this.setDmg(8);
        this.setArmor(3);
        this.setMovementLimit(1);
        this.setAttackRange(1);

        if (team.equals("RED")) {

            this.imageSource = "/resources/images/knightR.png";
        }
        else if(team.equals("GREEN")){

            this.imageSource = "/resources/images/knightG.png";
        }
    }
}
