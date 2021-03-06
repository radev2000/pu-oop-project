package pieces;

public class Elf extends Piece {

    public Elf(int currentRow, int currentCol, String team, String type, int hp){

        this.maxHP           = 10;
        this.hp              = hp;
        this.pieceType       = type;
        this.team            = team;
        this.currentRow      = currentRow;
        this.currentCol      = currentCol;

        this.setDmg(5);
        this.setArmor(1);
        this.setMovementLimit(3);
        this.setAttackRange(3);

        if (team.equals("RED")) {

            this.imageSource = "/resources/images/elfR.png";
        }
        else if(team.equals("GREEN")){

            this.imageSource = "/resources/images/elfG.png";
        }
    }
}
