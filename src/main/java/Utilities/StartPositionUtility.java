package Utilities;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class StartPositionUtility {

    public static Vector2 getStartPosition(int index) {
        ArrayList<Vector2>startPositions = new ArrayList<>();
        startPositions.add(new Vector2(1,6));
        startPositions.add(new Vector2(1,5));
        startPositions.add(new Vector2(1,8));
        startPositions.add(new Vector2(1,3));
        startPositions.add(new Vector2(1,10));
        startPositions.add(new Vector2(1,1));
        startPositions.add(new Vector2(1,11));
        startPositions.add(new Vector2(1,0));
        return startPositions.get(index);
    }
}
