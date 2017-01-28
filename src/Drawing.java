import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

/*****************************************************************
 Business card interface that converts user input into a
 standard business card.

 @author Ben Payne
 @version 2017-01-24
 *****************************************************************/
class BusinessCard extends JPanel {

    private static final int WINDOW_WIDTH = 500;

    private static final int WINDOW_HEIGHT = 1000;

    private static final int BUSINESS_CARD_WIDTH = 350;

    private static final int FONT_SIZE = 16;

    private static final int LARGE_FONT_SIZE = 20;

    private static final int X_OFFSET = 75;

    private static final int Y_OFFSET = 40;

    private static final String FONT_FAMILY = "sanserif";

    private static final String IMAGE_RESOURCES = "/resources/images";

    private static final String FONT_RESOURCES = "/resources/fonts";

    private HashMap<String, String> colorPallet = new HashMap<>();

    public static void main(final String[] args) {
        JFrame window = new JFrame();

        window.setContentPane(new BusinessCard());

        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        window.setVisible(true);
    }

    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);

        setColorPallet();

        setBackground(getColorFromPallet("Base"));

        graphics.setColor(getColorFromPallet("Highlight"));

        drawBackground(graphics);

        drawHeader(graphics);

        drawBody(graphics);

        drawFooter(graphics);
    }

    private void drawBackground(final Graphics graphics) {
        BufferedImage background = getImageResource(
            graphics,
            "/resources/images/bg.png"
        );

        graphics.drawImage(
            background,
            0,
            0,
            getColorFromPallet("Base"),
            null
        );
    }

    private void drawHeader(final Graphics graphics) {
        BufferedImage background = getImageResource(
            graphics,
            IMAGE_RESOURCES + "/headerBg.jpg"
        );

        graphics.drawImage(
            background,
            X_OFFSET,
            Y_OFFSET,
            null
        );

        graphics.setColor(getColorFromPallet("Highlight"));

        graphics.setFont(new Font(FONT_FAMILY, Font.BOLD, LARGE_FONT_SIZE));

        graphics.drawString("BEN PAYNE", X_OFFSET + 20, Y_OFFSET + 150);

        graphics.setFont(new Font(FONT_FAMILY, Font.PLAIN, 20));

        graphics.drawString("Web Developer", X_OFFSET + 20, Y_OFFSET + 170);
    }

    private void drawBody(final Graphics graphics) {
        graphics.setColor(getColorFromPallet("Highlight"));

        graphics.fillRect(X_OFFSET, Y_OFFSET + 210, BUSINESS_CARD_WIDTH, 400);

        drawContactDetails(graphics);

        drawContactIcons(graphics);
    }

    private void drawContactDetails(final Graphics graphics) {
        final int contactDetailsXOffset = X_OFFSET + 80;

        int contactDetailsYOffset = Y_OFFSET + 250;

        String[] contactDetails = {
            "benj@minapayne.com",
            "(616) 634-2033",
            "www.bj-payne.com",
            "1138 Orchard Ave SE\n Grand Rapids, MI 49506"
        };

        graphics.setColor(getColorFromPallet("Text"));

        graphics.setFont(new Font(FONT_FAMILY, Font.PLAIN, FONT_SIZE));

        for (String contactDetail : contactDetails) {
            if (contactDetail.contains("\n")) {
                String[] splitDetails = contactDetail.split("\n");

                int splitYOffset = 0;

                for (String splitDetail : splitDetails) {

                    graphics.setColor(getColorFromPallet("Text"));

                    graphics.drawString(
                        splitDetail.trim(),
                        contactDetailsXOffset,
                        contactDetailsYOffset + splitYOffset
                    );

                    splitYOffset += 20;
                }

                graphics.setColor(getColorFromPallet("Light"));

                graphics.drawLine(
                    X_OFFSET,
                    contactDetailsYOffset + 50,
                    X_OFFSET + BUSINESS_CARD_WIDTH,
                    contactDetailsYOffset + 50
                );
            } else {
                graphics.setColor(getColorFromPallet("Text"));

                graphics.drawString(
                    contactDetail,
                    contactDetailsXOffset,
                    contactDetailsYOffset
                );

                graphics.setColor(getColorFromPallet("Light"));

                graphics.drawLine(
                    X_OFFSET,
                    contactDetailsYOffset + 30,
                    X_OFFSET + BUSINESS_CARD_WIDTH,
                    contactDetailsYOffset + 30
                );
            }

            contactDetailsYOffset += 80;
        }

        graphics.setColor(getColorFromPallet("Text"));

        graphics.setFont(new Font(FONT_FAMILY, Font.ITALIC, FONT_SIZE));

        graphics.drawString(
            "\"ASCII stupid question, get a stupid ANSI\"",
            X_OFFSET + 15,
            contactDetailsYOffset + 5
        );
    }

    private void drawContactIcons(final Graphics graphics) {
        Font font = getFontResource(graphics, "fontawesome-webfont.ttf");

        String[] contactIcons = {"\uf0e0", "\uf095", "\uf0ac", "\uf041"};

        int contactIconOffset = Y_OFFSET + 255;

        graphics.setColor(getColorFromPallet("Accent"));

        graphics.setFont(font);

        for (String contactIcon : contactIcons) {
            graphics.drawString(contactIcon, X_OFFSET + 30, contactIconOffset);

            contactIconOffset += 80;
        }
    }

    private void drawFooter(final Graphics graphics) {
        graphics.setColor(getColorFromPallet("Accent"));

        graphics.fillRect(X_OFFSET, Y_OFFSET + 600, BUSINESS_CARD_WIDTH, 10);
    }

    private BufferedImage getImageResource(
        final Graphics graphics,
        final String path
    ) {
        BufferedImage background = null;

        try {
            background = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            graphics.drawString(e.getMessage(), 100, 100);
        }


        return background;
    }

    private Font getFontResource(final Graphics graphics, final String path) {
        Font font = null;

        try {
            font = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().
                    getResourceAsStream(FONT_RESOURCES + "/" + path)
            );

            font = font.deriveFont(Font.PLAIN, 24f);
        } catch (IOException | FontFormatException e) {
            graphics.setColor(Color.WHITE);

            graphics.drawString(e.getMessage(), 0, 0);
        }

        return font;
    }

    private void setColorPallet() {
        colorPallet.put("Base", "#333333");

        colorPallet.put("Highlight", "#FFFFFF");

        colorPallet.put("Accent", "#F94B44");

        colorPallet.put("Trim", "#AA373A");

        colorPallet.put("Offset", "#9B643B");

        colorPallet.put("Text", "#858585");

        colorPallet.put("Light", "#E3E3E3");
    }

    private Color getColorFromPallet(final String pallet) {
        return Color.decode(colorPallet.get(pallet));
    }
}
