package board;

import java.util.ArrayList;

public class Board {

    private int boardWidth;
    private int boardHeight;
    public ArrayList<Tile> tiles = new ArrayList<Tile>();

    public Board(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
    }
}
