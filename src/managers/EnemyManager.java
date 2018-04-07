package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager extends Manager
{

    private ArrayList<BufferedImage> triBotAnimations = new ArrayList<>();

    @Override
    public void initialize()
    {
        triBotAnimations.add(loadFile("resources/character/enemy/tribot/Enemy_WH_237x356_EnemyMove_EnemyAttack.png"));
    }
}
