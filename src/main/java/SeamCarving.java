import util.Carver;

import java.io.IOException;

public class SeamCarving {

    public static void main(String[] args) {
        if (args.length < 1) {
            throw new IllegalArgumentException("Must feed in file to parse");
        }

        Carver.doHorizontalCarve(args[0]);
    }
}
