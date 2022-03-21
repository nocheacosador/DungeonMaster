package old_tiles;

import processing.core.PApplet;

public class TileFactory {

    static public TileFactory getInstance() {
        return new TileFactory();
    }

    public Tile getTile(String name, PApplet parent) {
        if (name.equalsIgnoreCase("BlueBannerWallTile")) {
            return new BlueBannerWallTile(parent);
        }
        if (name.equalsIgnoreCase("BottomLeftCornerWallTile")) {
            return new BottomLeftCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("BottomRightCornerWallTile")) {
            return new BottomRightCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("ColumnBaseTile")) {
            return new ColumnBaseTile(parent);
        }
        if (name.equalsIgnoreCase("ColumnBaseWallTile")) {
            return new ColumnBaseWallTile(parent);
        }
        if (name.equalsIgnoreCase("ColumnMidTile")) {
            return new ColumnMidTile(parent);
        }
        if (name.equalsIgnoreCase("ColumnMidWallTile")) {
            return new ColumnMidWallTile(parent);
        }
        if (name.equalsIgnoreCase("ColumnTopTile")) {
            return new ColumnTopTile(parent);
        }
        if (name.equalsIgnoreCase("ColumnTopWallTile")) {
            return new ColumnTopWallTile(parent);
        }
        if (name.equalsIgnoreCase("EdgeTile")) {
            return new EdgeTile(parent);
        }
        if (name.equalsIgnoreCase("EmptyTile")) {
            return new EmptyTile(parent);
        }
        if (name.equalsIgnoreCase("Floor1Tile")) {
            return new Floor1Tile(parent);
        }
        if (name.equalsIgnoreCase("Floor2Tile")) {
            return new Floor2Tile(parent);
        }
        if (name.equalsIgnoreCase("Floor3Tile")) {
            return new Floor3Tile(parent);
        }
        if (name.equalsIgnoreCase("Floor4Tile")) {
            return new Floor4Tile(parent);
        }
        if (name.equalsIgnoreCase("Floor5Tile")) {
            return new Floor5Tile(parent);
        }
        if (name.equalsIgnoreCase("Floor6Tile")) {
            return new Floor6Tile(parent);
        }
        if (name.equalsIgnoreCase("Floor8Tile")) {
            return new Floor8Tile(parent);
        }
        if (name.equalsIgnoreCase("FloorLadderTile")) {
            return new FloorLadderTile(parent);
        }
        if (name.equalsIgnoreCase("FloorSpikes")) {
            return new FloorSpikes(parent);
        }
        if (name.equalsIgnoreCase("FountainBasinBlueTile")) {
            return new FountainBasinBlueTile(parent);
        }
        if (name.equalsIgnoreCase("FountainBasinRedTile")) {
            return new FountainBasinRedTile(parent);
        }
        if (name.equalsIgnoreCase("FountainMidBlueTile")) {
            return new FountainMidBlueTile(parent);
        }
        if (name.equalsIgnoreCase("FountainMidRedTile")) {
            return new FountainMidRedTile(parent);
        }
        if (name.equalsIgnoreCase("FountainTopWallTile")) {
            return new FountainTopWallTile(parent);
        }
        if (name.equalsIgnoreCase("FrontRightSideWallTile")) {
            return new FrontRightSideWallTile(parent);
        }
        if (name.equalsIgnoreCase("FrontLeftCornerWallTile")) {
            return new FrontLeftCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("FrontLeftSideWallTile")) {
            return new FrontLeftSideWallTile(parent);
        }
        if (name.equalsIgnoreCase("FrontRightCornerWallTile")) {
            return new FrontRightCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("GooBaseWallTile")) {
            return new GooBaseWallTile(parent);
        }
        if (name.equalsIgnoreCase("GooWallTile")) {
            return new GooWallTile(parent);
        }
        if (name.equalsIgnoreCase("GreenBannerWallTile")) {
            return new GreenBannerWallTile(parent);
        }
        if (name.equalsIgnoreCase("HoleTile")) {
            return new HoleTile(parent);
        }
        if (name.equalsIgnoreCase("HoleWall1Tile")) {
            return new HoleWall1Tile(parent);
        }
        if (name.equalsIgnoreCase("HoleWall2Tile")) {
            return new HoleWall2Tile(parent);
        }
        if (name.equalsIgnoreCase("LeftCornerWallTile")) {
            return new LeftCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("LeftWallTile")) {
            return new LeftWallTile(parent);
        }
        if (name.equalsIgnoreCase("MidLeftCornerWallTile")) {
            return new MidLeftCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("MidLeftSideWallTile")) {
            return new MidLeftSideWallTile(parent);
        }
        if (name.equalsIgnoreCase("MidRightInnerCornerWallTile")) {
            return new MidRightInnerCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("MidRightSideWallTile")) {
            return new MidRightSideWallTile(parent);
        }
        if (name.equalsIgnoreCase("MidWallTile")) {
            return new MidWallTile(parent);
        }
        if (name.equalsIgnoreCase("RedBannerWallTile")) {
            return new RedBannerWallTile(parent);
        }
        if (name.equalsIgnoreCase("RightCornerWallTile")) {
            return new RightCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("RightWallTile")) {
            return new RightWallTile(parent);
        }
        if (name.equalsIgnoreCase("TopLeftCornerWallTile")) {
            return new TopLeftCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("TopLeftLInnerCornerWallTile")) {
            return new TopLeftLInnerCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("TopLeftTInnerCornerWallTile")) {
            return new TopLeftTInnerCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("TopLeftWallTile")) {
            return new TopLeftWallTile(parent);
        }
        if (name.equalsIgnoreCase("TopMidWallTile")) {
            return new TopMidWallTile(parent);
        }
        if (name.equalsIgnoreCase("TopRightCornerWallTile")) {
            return new TopRightCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("TopRightLInnerCornerWallTile")) {
            return new TopRightLInnerCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("TopRightSideWallTile")) {
            return new TopRightSideWallTile(parent);
        }
        if (name.equalsIgnoreCase("TopRightTInnerCornerWallTile")) {
            return new TopRightTInnerCornerWallTile(parent);
        }
        if (name.equalsIgnoreCase("TopRightWallTile")) {
            return new TopRightWallTile(parent);
        }
        if (name.equalsIgnoreCase("TopSideLeftWallTile")) {
            return new TopSideLeftWallTile(parent);
        }
        if (name.equalsIgnoreCase("YellowBannerWallTile")) {
            return new YellowBannerWallTile(parent);
        }
        return new EmptyTile(parent);
    }

    public Tile getTile(int id, PApplet parent) {
        if (id == BlueBannerWallTile.ID) {
            return new BlueBannerWallTile(parent);
        }
        if (id == BottomLeftCornerWallTile.ID) {
            return new BottomLeftCornerWallTile(parent);
        }
        if (id == BottomRightCornerWallTile.ID) {
            return new BottomRightCornerWallTile(parent);
        }
        if (id == ColumnBaseTile.ID) {
            return new ColumnBaseTile(parent);
        }
        if (id == ColumnBaseWallTile.ID) {
            return new ColumnBaseWallTile(parent);
        }
        if (id == ColumnMidTile.ID) {
            return new ColumnMidTile(parent);
        }
        if (id == ColumnMidWallTile.ID) {
            return new ColumnMidWallTile(parent);
        }
        if (id == ColumnTopTile.ID) {
            return new ColumnTopTile(parent);
        }
        if (id == ColumnTopWallTile.ID) {
            return new ColumnTopWallTile(parent);
        }
        if (id == EdgeTile.ID) {
            return new EdgeTile(parent);
        }
        if (id == EmptyTile.ID) {
            return new EmptyTile(parent);
        }
        if (id == Floor1Tile.ID) {
            return new Floor1Tile(parent);
        }
        if (id == Floor2Tile.ID) {
            return new Floor2Tile(parent);
        }
        if (id == Floor3Tile.ID) {
            return new Floor3Tile(parent);
        }
        if (id == Floor4Tile.ID) {
            return new Floor4Tile(parent);
        }
        if (id == Floor5Tile.ID) {
            return new Floor5Tile(parent);
        }
        if (id == Floor6Tile.ID) {
            return new Floor6Tile(parent);
        }
        if (id == Floor8Tile.ID) {
            return new Floor8Tile(parent);
        }
        if (id == FloorLadderTile.ID) {
            return new FloorLadderTile(parent);
        }
        if (id == FloorSpikes.ID) {
            return new FloorSpikes(parent);
        }
        if (id == FountainBasinBlueTile.ID) {
            return new FountainBasinBlueTile(parent);
        }
        if (id == FountainBasinRedTile.ID) {
            return new FountainBasinRedTile(parent);
        }
        if (id == FountainMidBlueTile.ID) {
            return new FountainMidBlueTile(parent);
        }
        if (id == FountainMidRedTile.ID) {
            return new FountainMidRedTile(parent);
        }
        if (id == FountainTopWallTile.ID) {
            return new FountainTopWallTile(parent);
        }
        if (id == FrontLeftCornerWallTile.ID) {
            return new FrontLeftCornerWallTile(parent);
        }
        if (id == FrontLeftSideWallTile.ID) {
            return new FrontLeftSideWallTile(parent);
        }
        if (id == FrontRightCornerWallTile.ID) {
            return new FrontRightCornerWallTile(parent);
        }
        if (id == FrontRightSideWallTile.ID) {
            return new FrontRightSideWallTile(parent);
        }
        if (id == GooBaseWallTile.ID) {
            return new GooBaseWallTile(parent);
        }
        if (id == GooWallTile.ID) {
            return new GooWallTile(parent);
        }
        if (id == GreenBannerWallTile.ID) {
            return new GreenBannerWallTile(parent);
        }
        if (id == HoleTile.ID) {
            return new HoleTile(parent);
        }
        if (id == HoleWall1Tile.ID) {
            return new HoleWall1Tile(parent);
        }
        if (id == HoleWall2Tile.ID) {
            return new HoleWall2Tile(parent);
        }
        if (id == LeftCornerWallTile.ID) {
            return new LeftCornerWallTile(parent);
        }
        if (id == LeftWallTile.ID) {
            return new LeftWallTile(parent);
        }
        if (id == MidLeftCornerWallTile.ID) {
            return new MidLeftCornerWallTile(parent);
        }
        if (id == MidLeftSideWallTile.ID) {
            return new MidLeftSideWallTile(parent);
        }
        if (id == MidRightInnerCornerWallTile.ID) {
            return new MidRightInnerCornerWallTile(parent);
        }
        if (id == MidRightSideWallTile.ID) {
            return new MidRightSideWallTile(parent);
        }
        if (id == MidWallTile.ID) {
            return new MidWallTile(parent);
        }
        if (id == RedBannerWallTile.ID) {
            return new RedBannerWallTile(parent);
        }
        if (id == RightCornerWallTile.ID) {
            return new RightCornerWallTile(parent);
        }
        if (id == RightWallTile.ID) {
            return new RightWallTile(parent);
        }
        if (id == TopLeftCornerWallTile.ID) {
            return new TopLeftCornerWallTile(parent);
        }
        if (id == TopLeftLInnerCornerWallTile.ID) {
            return new TopLeftLInnerCornerWallTile(parent);
        }
        if (id == TopLeftTInnerCornerWallTile.ID) {
            return new TopLeftTInnerCornerWallTile(parent);
        }
        if (id == TopLeftWallTile.ID) {
            return new TopLeftWallTile(parent);
        }
        if (id == TopMidWallTile.ID) {
            return new TopMidWallTile(parent);
        }
        if (id == TopRightCornerWallTile.ID) {
            return new TopRightCornerWallTile(parent);
        }
        if (id == TopRightLInnerCornerWallTile.ID) {
            return new TopRightLInnerCornerWallTile(parent);
        }
        if (id == TopRightSideWallTile.ID) {
            return new TopRightSideWallTile(parent);
        }
        if (id == TopRightTInnerCornerWallTile.ID) {
            return new TopRightTInnerCornerWallTile(parent);
        }
        if (id == TopRightWallTile.ID) {
            return new TopRightWallTile(parent);
        }
        if (id == TopSideLeftWallTile.ID) {
            return new TopSideLeftWallTile(parent);
        }
        if (id == YellowBannerWallTile.ID) {
            return new YellowBannerWallTile(parent);
        }
        return new EmptyTile(parent);
    }
}
