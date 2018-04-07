package sprite.character;

import sprite.Sprite;
import sprite.world.Floor;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class CharacterSprite extends Sprite{
    Floor floor;
    ArrayList<Sprite> walls = new ArrayList<>();
    int health;
    boolean onFire = false;
    boolean dotHeal = false;
    public CharacterSprite(float startX, float startY, Vector2f scale, BufferedImage currentSpriteFrame, Floor floor, ArrayList<Sprite> walls){
        super(startX, startY, scale, currentSpriteFrame);
        this.floor = floor;
        this.walls = walls;
    }

    public void checkCollision(float delta){
        checkWallCollision();
        checkFloorCollision();
    }

    public void checkWallCollision(){}

    public void checkFloorCollision(){}
}
