package sprite;

import bounding.BoundingShape;
import util.Matrix3x3f;
import util.Vector2f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Sprite{
    private BufferedImage currentSpriteFrame;
    protected Matrix3x3f viewport;
    private float rotation = 0;
    private Vector2f scale;
    private float xTranslation;
    private float yTranslation;
    protected ArrayList<BoundingShape> hitboxes = new ArrayList<>();

    public Sprite(float startX, float startY, Vector2f scale){
        xTranslation = startX;
        yTranslation = startY;
        this.scale = scale;
    }

    //Each sprite has hitboxes which should be set in subclasses
    //the first hitbox is the outer hitbox
    public abstract void initializeHitboxes();

    //Process anything that's constantly affecting the sprite
    public abstract void process(float delta);

    //Update where the sprite is currently located in the world
    public void update(float delta, Matrix3x3f viewport){
        for(BoundingShape bound : hitboxes){
            bound.setxTranslation(xTranslation);
            bound.setyTranslation(yTranslation);
            bound.setScale(scale);
            //bound.setRot(rotation); Squares and circles do not do well with the formulas from the book.
            bound.updateWorld(viewport);
        }
        setViewport(viewport);
    }

    protected void setViewport(Matrix3x3f viewport){
        this.viewport = viewport;
    }

    //Render the location of the sprite
    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        //Set antialias
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Set nearest neighbor
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        doAffineTransform(g2d);
    }

    //For each hitbox, render the hitbox
    public void renderHitboxes(Graphics g){
        for(BoundingShape bound : hitboxes){
            bound.render(g);
        }
    }

    //Code from book, it renders where the sprite is in world coordinates.
    private void doAffineTransform(Graphics2D g2d){
        AffineTransform transform = createTransform(new Vector2f(xTranslation, yTranslation), rotation, scale);
        g2d.drawImage(currentSpriteFrame, transform, null);
    }

    //Code from book
    private AffineTransform createTransform(Vector2f position, float angle, Vector2f scale){
        Vector2f screen = viewport.mul(position);
        AffineTransform transform = AffineTransform.getTranslateInstance(screen.x, screen.y);
        transform.scale(scale.x, scale.y);
        transform.rotate(angle);
        transform.translate(-currentSpriteFrame.getWidth() / 2, -currentSpriteFrame.getHeight() / 2);
        return transform;
    }

    //Load an image and return the found image
    protected BufferedImage loadFile(String fileName) {
        BufferedImage spriteSheet;
        try {
            spriteSheet = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            spriteSheet = null;
        }
        return spriteSheet;
    }

    //Getters and Setters
    public void setCurrentSpriteFrame(BufferedImage currentSpriteFrame){
        this.currentSpriteFrame = currentSpriteFrame;
    }
    public float getxTranslation(){
        return xTranslation;
    }
    public void setxTranslation(float xTranslation){
        this.xTranslation = xTranslation;
    }
    public float getyTranslation(){
        return yTranslation;
    }
    public void setyTranslation(float yTranslation){
        this.yTranslation = yTranslation;
    }
    public Vector2f getScale(){
        return scale;
    }
    public void setScale(Vector2f scale){
        this.scale = scale;
    }
    public Vector2f getPos(){
        return new Vector2f(getxTranslation(), getyTranslation());
    }
    public void setRotation(float rotation){
        this.rotation = rotation;
    }
    public ArrayList<BoundingShape> getHitboxes(){
        return hitboxes;
    }
}