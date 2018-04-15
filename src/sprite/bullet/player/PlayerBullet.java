package sprite.bullet.player;

import bounding.BoundingBox;
import sprite.Sprite;
import sprite.bullet.Bullet;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PlayerBullet extends Bullet{
    private ArrayList<Sprite> enemies;
    public PlayerBullet(float startX, float startY, Vector2f scale, ArrayList<Sprite> enemies){
        super(startX, startY, scale);
        BufferedImage spriteSheet = loadFile("src/resources/character/player/MainCharBullet_WH_237x356_Bullet.png");
        setCurrentSpriteFrame(spriteSheet.getSubimage(711,0,237,356));
        this.enemies = enemies;
        initializeHitboxes();
    }

    @Override
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, .4f), new Vector2f(-.7f, .6f), Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-.9f, .4f), new Vector2f(-.7f, .6f), Color.RED));
    }

    @Override
    public void process(float delta){
        super.process(delta);
    }

    public void checkCollision(float delta, Matrix3x3f viewport){
        for(int i = 0; i < enemies.size(); i++){
            if(checkSpriteCollision(delta, viewport, enemies.get(i))){
                enemies.remove(i);
                i--;
            }
        }
    }
}