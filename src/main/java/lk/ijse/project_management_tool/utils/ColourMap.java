package lk.ijse.project_management_tool.utils;

import java.util.Random;

public class ColourMap {

    public enum Colours {
        COLOUR1("#e8e7f3", "#9900ff"),
        COLOUR2("#fdf0e9", "#fc955d"),//
        COLOUR3("#fef9e7", "#fcd549"),
        COLOUR4("#e8f4fd", "#43aaf7"),
//        COLOUR5("#fdf0e9", "#fff6ef"),//
        COLOUR5("#eaffef", "#40f76c");

        private final String primary;
        private final String secondary;

        Colours(String primary, String secondary) {
            this.primary = primary;
            this.secondary = secondary;
        }

        public String getPrimary() {
            return primary;
        }

        public String getSecondary() {
            return secondary;
        }
    }

    private static final Random random = new Random();

    // Returns both primary and secondary colors as a String[]
    public static String[] getRandomColour() {
        Colours[] colours = Colours.values();
        int index = random.nextInt(colours.length);
        Colours selected = colours[index];
        return new String[]{selected.getPrimary(), selected.getSecondary()};
    }
}
