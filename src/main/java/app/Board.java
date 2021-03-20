package app;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class Board {

    private final int numColumns;
    private final int numRows;
    private final HashMap<Tile, TiledMapTileLayer> layers;
    private HashMap<Tile, List<Vector2>> tileLocations;

    public Board(TiledMap map){

        layers = new HashMap<>();

        // Load layers
        for( MapLayer l : map.getLayers()) {
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(l.getName());
            layers.put(Tile.valueOf(l.getName()), layer);
        }

        this.numRows = layers.get(Tile.Board).getHeight();
        this.numColumns = layers.get(Tile.Board).getWidth();

        initialize();

    }

    public List<Vector2> getTileLocations(Tile tile){
        return tileLocations.get(tile);
    }

    private void initialize(){
        tileLocations = new HashMap<>();

        for(Tile tile : layers.keySet()){
            List<Vector2> locations = new ArrayList<>();
            for (int row = 0; row < numRows; row++) {
                for (int column = 0; column < numColumns; column++) {
                    if (layers.get(tile).getCell((int) column, (int) row) != null)
                        locations.add(new Vector2(column, row));
                }
            }
            tileLocations.put(tile, locations);
        }
    }

    public List<Tile> getTilesOnCell(float x, float y){
        List<Tile> layersOnPos = new ArrayList<>();

        for(Tile layer : layers.keySet())
            if (layers.get(layer).getCell((int) x, (int) y) != null){
                if(layer.equals(Tile.Board) || layer.equals(Tile.Player)) {
                }
                else {
                    layersOnPos.add(layer);
                }
            }

        return layersOnPos;
    }

    public TiledMapTileLayer getLayer(Tile layer){
        return layers.get(layer);
    }

    public int getNumColumns(){
        return this.numColumns;
    }

    public int getNumRows(){
        return this.numRows;
    }

}
