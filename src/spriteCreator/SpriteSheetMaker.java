/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spriteCreator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 *
 * @author cpnho
 */
public class SpriteSheetMaker {

    public static void main(String arg[]) {
        SpriteSheetMaker sm = new SpriteSheetMaker();
        //System.out.println("Done");
    }

    private int height;
    private int width;
    private int rows;
    private int columns;
    private List<animationSegment> list;

    public SpriteSheetMaker() {
        list = new ArrayList();
        String name;
       // which sprite information should we load?
        name = this.wallBlocks();
        //now make it
        makeSheet(name);
    }

    //////////////
    // These are all of the methods for setting up a particular sprite sheet
    private String mainCharacter() {
        list.clear();
        // this is hardcoded information specific to the sprite being created...
        height = 356;
        width = 237;
        rows = 3;// the amount of rows should be the same amount of animations in a set
        columns = 7;

        list.add(new animationSegment("Idle", 5));
        list.add(new animationSegment("Move", 6));
        list.add(new animationSegment("Jump", 7));
        return "MainCharSprite";
    }

    private String bullet() {
        list.clear();
        // this is hardcoded information specific to the sprite being created...
        height = 356;
        width = 237;
        rows = 1;// the amount of rows should be the same amount of animations in a set
        columns = 4;

        list.add(new animationSegment("Bullet", 4));
        return "MainCharBullet";
    }

    private String enemyCharacter() {
        list.clear();
        // this is hardcoded information specific to the sprite being created...
        height = 356;
        width = 237;
        rows = 2;// the amount of rows should be the same amount of animations in a set
        columns = 8;

        list.add(new animationSegment("EnemyMove", 7));
        list.add(new animationSegment("EnemyAttack", 8));
        return "Enemy";
    }

    private String onHit() {
        list.clear();
        height = 356;
        width = 237;
        rows = 1;
        columns = 2;

        list.add(new animationSegment("OnHitEffect", 2));
        return "onHitEffect";
    }

    private String pickupObj() {
        list.clear();
        height = 50;
        width = 50;
        rows = 1;
        columns = 9;

        list.add(new animationSegment("pickupObjectsResized", 9));
        return "PickupObjects";

    }

    private String building() {
        list.clear();
        height = 467;
        width = 400;
        rows = 1;
        columns = 9;
        list.add(new animationSegment("Wall", 9));
        return "Building";
    }

    private String wallBlocks() {
        list.clear();
        height = 467;
        width = 400;
        rows = 1;
        columns = 2;
        list.add(new animationSegment("WallStand", 2));
        return "WallBlock";
    }

    private String door() {
        list.clear();
        height = 356;
        width = 237;
        rows = 1;
        columns = 2;

        list.add(new animationSegment("Door", 2));
        return "Interact";
    }

    private String UIBattery() {
        list.clear();
        height = 203;
        width = 131;
        rows = 1;
        columns = 3;

        list.add(new animationSegment("Battery", 3));
        return "UIElement";
    }

    private String Effects() {
        list.clear();
        height = 356;
        width = 237;
        rows = 2;
        columns = 2;

        list.add(new animationSegment("Lightning", 2));
        list.add(new animationSegment("shield", 1));
        return "Effect";
    }

    ////////////
    // Where the  sprite sheets are actually created
    public void makeSheet(String n) {
        // create a sheet with the desired dimensions...
        BufferedImage out = new BufferedImage(width * columns, height * rows, BufferedImage.TYPE_INT_ARGB);
        int row = 0;
        int col = 0;
        Graphics2D g = out.createGraphics();
        String action = "";
        for (int i = 0; i < list.size(); i++) {
            action += "_" + list.get(i).getName();
            for (int f = 0; f < list.get(i).getFrames(); f++) {
                String name = list.get(i).getFrameName(f);
                BufferedImage bi = null;
                try {

                    File file = new File("D:\\Art\\Steamthunk\\Frames\\" + name);
                    //	String string = Paths.get("").toAbsolutePath().toString() + "\\" + name;
                    //	File file= new File(string);
                    if (file != null && file.exists()) {
                        bi = ImageIO.read(file);
                        g.drawImage(bi, col, row, width, height, null);
                        col += width;
                    } else {
                        System.out.println("The file " + file.getAbsolutePath() + " could not be found to exist.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            col = 0;
            row += height;
        }
        g.dispose();

        try {
            ImageIO.write(out, "png", new File(Paths.get("").toAbsolutePath().toString() + "/src/" + n + "_WH_" + width + "x" + height + action + ".png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
