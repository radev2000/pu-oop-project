package pieces;

public class Dwarf extends Piece {

    public Dwarf(int currentRow, int currentCol, String team, String type){

        this.pieceType = type;
        this.team = team;
        this.currentRow = currentRow;
        this.currentCol = currentCol;

        if (team.equals("RED")) {
            this.imageSource = "/resources/images/dwarfR.gif";
        }else if(team.equals("GREEN")){
            this.imageSource = "/resources/images/dwarfG.gif";
        }

        setDmg(6);
        setArmor(2);
        setHp(12);
        setMovementLimit(2);
        setAttackRange(2);
    }
}
