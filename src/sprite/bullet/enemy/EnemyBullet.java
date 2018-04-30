package sprite.bullet.enemy;

import bounding.BoundingBox;
import sprite.character.player.MainCharacter;
import sprite.bullet.Bullet;
import util.Collision;
import util.Matrix3x3f;
import util.Vector2f;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyBullet extends Bullet{
    private MainCharacter player;

    public EnemyBullet(float startX, float startY, Vector2f scale, MainCharacter player, BufferedImage spriteSheet){
        super(startX, startY, scale);
        setCurrentSpriteFrame(spriteSheet.getSubimage(0,0,237,356));
        this.player = player;
        initializeHitboxes();
    }

    @Override
    public void initializeHitboxes(){
        hitboxes.add(new BoundingBox(new Vector2f(-.1f, -.4f), new Vector2f(.6f, -.1f), Color.BLUE));
        hitboxes.add(new BoundingBox(new Vector2f(-.1f, -.4f), new Vector2f(.6f, -.1f), Color.RED));
    }

    public boolean checkCollision(float delta, Matrix3x3f viewport){
        if(Collision.checkSpriteCollision(this, player)){
            player.decreaseHP(bulletDamage);
            System.out.println(player.getHp());
            return true;
        }
        return false;
    }
}
