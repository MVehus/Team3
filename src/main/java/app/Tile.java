package app;

public enum Tile {
    Board("Board"),
    Hole("Hole"),
    RobotStart ("RobotStart"),
    SingleConveyorDown ("SingleConveyorDown"),
    SingleConveyorRight ("SingleConveyorRight"),
    SingleConveyorUp ("SingleConveyorUp"),
    SingleConveyorLeft ("SingleConveyorLeft"),
    DoubleConveyorUp ("DoubleConveyorUp"),
    DoubleConveyorRight ("DoubleConveyorRight"),
    LaserHorizontal ("LaserHorizontal"),
    LaserVertical ("LaserVertical"),
    WallLeft ("WallLeft"),
    WallTop ("WallTop"),
    WallRight ("WallRight"),
    WallBottom ("WallBottom"),
    WallTopRight ("WallTopRight"),
    WallBottomRight ("WallBottomRight"),
    WallTopLeft ("WallTopLeft"),
    WallBottomLeft ("WallBottomLeft"),
    PushWallLeft ("PushWallLeft"),
    PushWallTop ("PushWallTop"),
    PushWallRight ("PushWallRight"),
    PushWallBottom ("PushWallBottom"),
    Wrench ("Wrench"),
    HammerWrench ("HammerWrench"),
    RotateRight ("RotateRight"),
    FlagOne ("FlagOne"),
    FlagTwo ("FlagTwo"),
    FlagThree ("FlagThree"),
    Flag ("Flag"),
    Player ("Player");

    public final String name;

    Tile(String name){
        this.name = name;
    }

}
