package sprite.bullet;

import sprite.Sprite;
import util.Matrix3x3f;
import util.Vector2f;

public abstract class Bullet extends Sprite{
    protected float bulletSpeed = 3.5f;
    protected int bulletDamage = 1;

    /**create a new bullet with starting x and y coords, as well as scale
     * *
     * @param startX x coord
     * @param startY y coord
     * @param scale scale
     */
    public Bullet(float startX, float startY, Vector2f scale){
        super(startX, startY, scale);
    }

    /**
     * create a new bullet with starting x and y coords, as well as scale and bullet damage
     * this constructor is good for increasing damage on newly spawned bullets
     * @param startX x coord
     * @param startY y coord
     * @param scale scale
     * @param bulletDamage bullet damage
     */
    public Bullet(float startX, float startY, Vector2f scale, int bulletDamage){
        super(startX, startY, scale);
        this.bulletDamage = bulletDamage;
    }

    @Override
    public void process(float delta){
        if(getScale().x < 0){
            shootRight(delta);
        }
        else{
            shootLeft(delta);
        }
    }

    //Shoot the bullet left
    private void shootLeft(float delta){
        setxTranslation(getxTranslation() - (bulletSpeed * delta));
        setScale(new Vector2f(Math.abs(getScale().x), Math.abs(getScale().y)));
    }

    // play right move animation
    private void shootRight(float delta){
        setxTranslation(getxTranslation() + (bulletSpeed * delta));
        setScale(new Vector2f(-Math.abs(getScale().x), Math.abs(getScale().y)));
    }


    public boolean outOfBounds(){
        return getxTranslation() > 8.5 || getxTranslation() < -8.5;
    }

    public abstract boolean checkCollision(float delta, Matrix3x3f viewport);

    public int getBulletDamage()
    {
        return bulletDamage;
    }

    public void setBulletDamage(int bulletDamage)
    {
        this.bulletDamage = bulletDamage;
    }
}
