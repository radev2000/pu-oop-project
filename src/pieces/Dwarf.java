package pieces;

public class Dwarf extends Piece {

    public Dwarf(int currentRow, int currentCol, String team, String type, int hp){

        this.hp = hp;
        this.maxHP = 12;
        this.pieceType = type;
        this.team = team;
        this.currentRow = currentRow;
        this.currentCol = currentCol;

        if (team.equals("RED")) {
            this.imageSource = "/resources/images/dwarfR.gif";
        }else if(team.equals("GREEN")){
            this.imageSource = "/resources/images/dwarfG.gif";
        }

        this.setDmg(6);
        this.setArmor(2);
        this.setMovementLimit(2);
        this.setAttackRange(2);
    }
}
