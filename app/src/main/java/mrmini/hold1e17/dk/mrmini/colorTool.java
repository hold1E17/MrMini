package mrmini.hold1e17.dk.mrmini;

import android.graphics.Color;

/**
 * A class with methods to help with colors.
 * (Only one method so far.)
 */

/**
 * Created by Sofie on 04-01-2018.
 */

public class colorTool {

    /**
     * Return true if the two colors are a pretty good match.
     * To be a good match, all three color values (RGB) must be within the tolerance value given.
     *
     * @param color1 ints
     * @param color2 int
     * @param tolerance int - the max difference that is allowed for any of the RGB components
     * @return boolean
     */

    public boolean closeMatch(int color1, int color2, int tolerance) {
        if ((int) Math.abs(Color.red(color1) - Color.red(color2)) > tolerance) return false;
        if ((int) Math.abs(Color.green(color1) - Color.green(color2)) > tolerance) return false;
        if ((int) Math.abs(Color.blue(color1) - Color.blue(color2)) > tolerance) return false;
        return true;
    }
}
