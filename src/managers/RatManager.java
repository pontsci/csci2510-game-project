package managers;

import sprite.Sprite;
import sprite.character.enemy.Rat;
import sprite.world.Floor;
import util.Vector2f;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

//This is not going to be in our project, it's just a reference for some basic ai concepts to show
//how different aspects worked together.

public class RatManager extends Manager{
    //Used for spawning rats without having to load the file multiple times,
    //with other managers, they can discard the file because they are not spawning
    //more sprites throughout the 'game'
    private final static int GRAY_RAT = 0;
    private final static int WHITE_RAT = 1;
    private Floor floor;
    private ArrayList<Sprite> walls;
    private ArrayList<Sprite> platforms;

    private Random rand = new Random();
    private float ratTime = 0;//Used to determine when a rat can spawn
    private float megaRatTime = 0;//Used to determine when a mega rat can spawn.

    //Get the rat's sprite sheet and make a rat.
    public RatManager(Floor floor, ArrayList<Sprite> walls, ArrayList<Sprite> platforms){
        //Save the objects a rat collides with so it can be used to spawn more rats.
        this.floor = floor;
        this.walls = walls;
        this.platforms = platforms;
        getSprites().add(new Rat(4, -4f, new Vector2f(-2,2), GRAY_RAT, false, floor, walls, platforms));
    }

    @Override //spawn rats
    public void process(float delta){
        super.process(delta);
        spawnRat(delta);
        spawnMegaRat(delta);
    }

    //Occasionally spawn a rat every 3 seconds.
    private void spawnRat(float delta){
        if(ratTime > 4 && getSprites().size() < 8){
            //spawn new rat at either rat hole and orientate it correctly.
            if(rand.nextBoolean()){
                //determine which hole/side to spawn the rat at
                //Right
                if(rand.nextBoolean()){
                    int size = rand.nextInt(3) - 1;//-1 to 1
                    getSprites().add(new Rat(8,-4f,new Vector2f(-(4 + size), (4 + size)), GRAY_RAT, false, floor, walls, platforms));
                }
                //Left
                else{
                    int size = rand.nextInt(3) - 1;//-1 to 1
                    getSprites().add(new Rat(-8,-4, new Vector2f((5  + size), (5 + size)), GRAY_RAT, true, floor, walls, platforms));
                }
            }
            ratTime = ratTime - 4;
        }
        else
            ratTime = ratTime + delta;
    }

    //Doesn't do anything special from regular spawnRat, it's just a big white rat I felt like adding for kicks.
    //It's my first game with sprites... I had to play around with it. ;-;
    private void spawnMegaRat(float delta){
        if(megaRatTime > 2 && getSprites().size() < 8){
            int i = rand.nextInt(10);
            //10% chance every 2 seconds for a mega rat to spawn as long as there are not 8 rats currently spawned
            if(i == 0){
                //right
                if(rand.nextBoolean()){
                       getSprites().add(new Rat(8,-4f,new Vector2f(-(4), (4f)), WHITE_RAT, false, floor, walls, platforms));
                }
                //Left
                else{
                    getSprites().add(new Rat(-8,-4f, new Vector2f((4), (4f)), WHITE_RAT, true, floor, walls, platforms));
                }
            }
            megaRatTime = megaRatTime - 2;
        }
        else
            megaRatTime = megaRatTime + delta;
    }
}