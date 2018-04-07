package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager extends Manager
{
    private ArrayList<BufferedImage> triBotAnimations = new ArrayList<>();
    public EnemyManager(){
        triBotAnimations.add(loadFile("src/resources/character/enemy/tribot/Enemy_WH_237x356_EnemyMove_EnemyAttack.png"));
    }
}
