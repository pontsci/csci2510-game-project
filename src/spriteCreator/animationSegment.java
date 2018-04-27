/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spriteCreator;

/**
 *
 * @author cpnho
 */
public class animationSegment {

    private String name;
    private int frames;

    public animationSegment(String n, int f) {
        name = n;
        frames = f;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFrames() {
        return frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public String getFrameName(int f) {
        String name = this.getName() + "_";
        if (f < 10) {
            name += "000" + f;
        } else if (f < 100) {
            name += "00" + f;
        } else if (f < 1000) {
            name += "0" + f;
        } else {
            name += f;
        }
        name += ".png";

        return name;
    }

}
