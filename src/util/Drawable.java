package util;

import java.awt.*;

public interface Drawable{
    void updateWorld(Matrix3x3f viewport);//Update the World Matrix

    void render(Graphics g);//Draw the object with passed Graphics
}
