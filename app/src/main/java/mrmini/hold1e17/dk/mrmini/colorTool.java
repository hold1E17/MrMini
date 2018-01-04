package mrmini.hold1e17.dk.mrmini;

import android.graphics.Color;

/**
 * Created by Sofie on 04-01-2018.
 */

public class colorTool {
    public boolean closeMatch(int color1, int color2, int tolerance) {
        if ((int) Math.abs (Color.red (color1) - Color.red (color2)) > tolerance ) return false;
        if ((int) Math.abs (Color.green (color1) - Color.green (color2)) > tolerance ) return false;
        if ((int) Math.abs (Color.blue (color1) - Color.blue (color2)) > tolerance ) return false;
        return true;
    }
}
