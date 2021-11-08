package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Serializable class that contains the GUI settings.
 * Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 740;
    private static final double DEFAULT_WIDTH = 980;
    private static final int DEFAULT_X_POSITION = 300;
    private static final int DEFAULT_Y_POSITION = 100;
    private static final String DEFAULT_THEME = "default";

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;
    private final String theme;

    /**
     * Constructs a {@code GuiSettings} with the default height, width and position.
     */
    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = new Point(DEFAULT_X_POSITION, DEFAULT_Y_POSITION); // null represent no coordinates
        theme = DEFAULT_THEME;
    }

    /**
     * Constructs a {@code GuiSettings} with the specified height, width, position and theme.
     */
    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition, String theme) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
        this.theme = theme;
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates != null ? new Point(windowCoordinates) : null;
    }

    public String getTheme() {
        return theme;
    }

    public String getStyleSheetPath() {
        return "/styles/Theme" + theme.substring(0, 1).toUpperCase() + theme.substring(1) + ".css";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof GuiSettings)) { //this handles null as well.
            return false;
        }

        GuiSettings o = (GuiSettings) other;

        return windowWidth == o.windowWidth
                && windowHeight == o.windowHeight
                && Objects.equals(windowCoordinates, o.windowCoordinates)
                && theme.equals(o.theme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates, theme);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Width : " + windowWidth + "\n");
        sb.append("Height : " + windowHeight + "\n");
        sb.append("Position : " + windowCoordinates);
        sb.append("Theme : " + theme);
        return sb.toString();
    }
}
