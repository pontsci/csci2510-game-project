package sprite.bullet.enemy;

import bounding.BoundingBox;
import sprite.character.player.MainCharacter;
import sprite.bullet.Bullet;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyBullet extends Bullet{
    private MainCharacter player;

    public EnemyBullet(float startX, float startY, Vector2f scale, MainCharacter player){
        super(startX, startY, scale);
        BufferedImage spriteSheet = loadFile("src/resources/character/enemy/tribot/Enemy_WH_237x356_EnemyMove_EnemyAttack.png").getSubimage(1422, 356, 237, 356);
        setCurrentSpriteFrame(spriteSheet.getSubimage(0,0,237,356));
        this.player = player;
        initializeHitboxes();
    }

    @Override
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-.1f, -.4f), new Vector2f(.6f, -.1f), Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-.1f, -.4f), new Vector2f(.6f, -.1f), Color.RED));
    }

    public void checkCollision(float delta, Matrix3x3f viewport){
    }
}
