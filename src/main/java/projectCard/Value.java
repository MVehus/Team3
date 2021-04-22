package projectCard;

import java.util.Arrays;
import java.util.List;

/**
 * Each can contains one value
 */
public enum Value {
    MOVE_THREE,
    MOVE_TWO,
    MOVE_ONE,
    BACK_UP,
    U_TURN,
    ROTATE_LEFT,
    ROTATE_RIGHT;

    public static final List<Value> ALL_ROTATE_CARDS = Arrays.asList(ROTATE_LEFT, ROTATE_RIGHT, U_TURN);

    public static final List<Value> ALL_MOVE_CARDS = Arrays.asList(MOVE_THREE, MOVE_TWO, MOVE_ONE, BACK_UP);

}