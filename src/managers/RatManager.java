package managers;

import sprite.character.enemy.Rat;
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
    ArrayList<BufferedImage> ratAnimations = new ArrayList<>();
    ArrayList<BufferedImage> whiteRatAnimations = new ArrayList<>();
    Random rand = new Random();
    float ratTime = 0;//Used to determine when a rat can spawn
    float megaRatTime = 0;//Used to determine when a mega rat can spawn.

    //Get the rat's sprite sheet and make a rat.
    public void initialize(){
        ratAnimations.add(loadFile("src/resources/character/enemy/ratwalk.png"));
        whiteRatAnimations.add(loadFile("src/resources/character/enemy/whiteratwalk.png"));
        getSprites().add(new Rat(4, -4f, new Vector2f(-2,2), ratAnimations, false) );
    }

    @Override //spawn rats
    public void process(float delta){
        super.process(delta);
        spawnRat(delta);
        spawnMegaRat(delta);
    }

    //Occasionally spawn a rat every 3 seconds.
    public void spawnRat(float delta){
        if(ratTime > 4 && getSprites().size() < 8){
            //spawn new rat at either rat hole and orientate it correctly.
            if(rand.nextBoolean()){
                //determine which hole/side to spawn the rat at
                //Right
                if(rand.nextBoolean()){
                    int size = rand.nextInt(3) - 1;//-1 to 1
                    getSprites().add(new Rat(8,-4f,new Vector2f(-(4 + size), (4 + size)), ratAnimations, false));
                }
                //Left
                else{
                    int size = rand.nextInt(3) - 1;//-1 to 1
                    getSprites().add(new Rat(-8,-4, new Vector2f((5  + size), (5 + size)), ratAnimations, true));
                }
            }
            ratTime = ratTime - 4;
        }
        else
            ratTime = ratTime + delta;
    }

    //Doesn't do anything special from regular spawnRat, it's just a big white rat I felt like adding for kicks.
    //It's my first game with sprites... I had to play around with it. ;-;
    public void spawnMegaRat(float delta){
        if(megaRatTime > 2 && getSprites().size() < 8){
            int i = rand.nextInt(10);
            //10% chance every 2 seconds for a mega rat to spawn as long as there are not 8 rats currently spawned
            if(i == 0){
                //right
                if(rand.nextBoolean()){
                       getSprites().add(new Rat(8,-4f,new Vector2f(-(4), (4f)), whiteRatAnimations, false));
                }
                //Left
                else{
                    getSprites().add(new Rat(-8,-4f, new Vector2f((4), (4f)), whiteRatAnimations, true));
                }
            }
            megaRatTime = megaRatTime - 2;
        }
        else
            megaRatTime = megaRatTime + delta;
    }
}