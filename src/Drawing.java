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

    private static final float ICON_FONT_SIZE = 24f;

    private static final int X_OFFSET = 75;

    private static final int Y_OFFSET = 40;

    private static final String FONT_FAMILY = "quicksand.ttf";

    private static final String IMAGE_RESOURCES = "/resources/images";

    private static final String FONT_RESOURCES = "/resources/fonts";

    private HashMap<String, String> colorPallet = new HashMap<>();

    public static void main(final String[] args) {
        JFrame window = new JFrame();

        window.setContentPane(new BusinessCard());

        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        window.setVisible(true);
    }

    @Override
    public void paintComponent(final Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );

        setColorPallet();

        setBackground(getColorFromPallet("Base"));

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

        drawProfile(graphics);

        drawLogo(graphics);
    }

    private void drawProfile(final Graphics graphics) {
        final int headerProfileXOffset = X_OFFSET + 20;
        final int headerProfileYOffset = Y_OFFSET + 20;
        final int headerProfileWidth = 100;
        final int headerProfileHeight = 100;

        BufferedImage profile = getImageResource(
            graphics,
            IMAGE_RESOURCES + "/profile.png"
        );

        Image scaledProfile = profile.getScaledInstance(
            headerProfileWidth,
            headerProfileHeight,
            Image.SCALE_AREA_AVERAGING
        );

        graphics.drawImage(
            scaledProfile,
            headerProfileXOffset,
            headerProfileYOffset,
            null
        );
    }

    private void drawLogo(final Graphics graphics) {
        final int logoXOffset = X_OFFSET + 20;

        final int logoYOffset = Y_OFFSET + 135;

        final int logoTextXOffset = logoXOffset + 60;

        final int logoTextYOffset = Y_OFFSET + 150;

        final int headerTextLowerThirdYOffset = logoTextYOffset + 20;

        final int logoWidthHeight = 40;

        final int logoRadius = 5;

        final int logoRightBackgroundXOffset = logoXOffset + 20;

        final int logoLowerBackgroundYOffset = logoYOffset + 20;

        final int logoBackgroundWidthHeight = 20;

        final int logoInnerWidthHeight = 12;

        final int logoInnerShortXOffset = logoXOffset + 5;

        final int logoInnerShortYOffset = logoYOffset + 5;

        final int logoInnerLongXOffset = logoXOffset + 22;

        final int logoInnerLongYOffset = logoYOffset + 22;

        graphics.setColor(getColorFromPallet("Accent"));

        graphics.fillRoundRect(
            logoXOffset,
            logoYOffset,
            logoWidthHeight,
            logoWidthHeight,
            logoRadius,
            logoRadius
        );

        graphics.fillRect(
            logoXOffset,
            logoLowerBackgroundYOffset,
            logoWidthHeight,
            logoBackgroundWidthHeight
        );

        graphics.fillRect(
            logoRightBackgroundXOffset,
            logoYOffset,
            logoBackgroundWidthHeight,
            logoWidthHeight
        );

        graphics.setColor(getColorFromPallet("Contrast"));

        graphics.fillOval(
            logoInnerShortXOffset,
            logoInnerShortYOffset,
            logoInnerWidthHeight,
            logoInnerWidthHeight
        );

        graphics.fillRect(
            logoInnerLongXOffset,
            logoInnerShortYOffset,
            logoInnerWidthHeight,
            logoInnerWidthHeight
        );

        graphics.fillRect(
            logoInnerShortXOffset,
            logoInnerLongYOffset,
            logoInnerWidthHeight,
            logoInnerWidthHeight
        );

        graphics.fillRect(
            logoInnerLongXOffset,
            logoInnerLongYOffset,
            logoInnerWidthHeight,
            logoInnerWidthHeight
        );

        graphics.setColor(getColorFromPallet("Contrast"));

        graphics.setFont(getFontResource(
            graphics,
            FONT_FAMILY,
            LARGE_FONT_SIZE,
            Font.BOLD
        ));

        graphics.drawString("BEN PAYNE", logoTextXOffset, logoTextYOffset);

        graphics.setFont(getFontResource(
            graphics,
            FONT_FAMILY,
            LARGE_FONT_SIZE,
            Font.PLAIN
        ));

        graphics.drawString("Web Developer", logoTextXOffset, headerTextLowerThirdYOffset);
    }

    private void drawBody(final Graphics graphics) {
        final int bodyHeight = 400;

        final int bodyYOffset = Y_OFFSET + 210;

        graphics.setColor(getColorFromPallet("Contrast"));

        graphics.fillRect(
            X_OFFSET,
            bodyYOffset,
            BUSINESS_CARD_WIDTH,
            bodyHeight
        );

        drawContactDetails(graphics);

        drawContactIcons(graphics);
    }

    private void drawContactDetails(final Graphics graphics) {
        final int contactDetailsXOffset = X_OFFSET + 80;

        final int contactDetailsYIncrement = 80;

        final int quoteXOffset = 20;

        int contactDetailsYOffset = Y_OFFSET + 250;

        graphics.setColor(getColorFromPallet("Text"));

        graphics.setFont(getFontResource(
            graphics,
            FONT_FAMILY,
            FONT_SIZE,
            Font.PLAIN
        ));

        String[] contactDetails = {
            "benj@minpayne.com",
            "(616) 634-2033",
            "www.bj-payne.com",
            "1138 Orchard Ave SE" + System.lineSeparator() +
            " Grand Rapids, MI 49506"
        };

        for (String contactDetail : contactDetails) {
            /*
             * If there is a line break in the contact detail then split the
             * line break and and loop through the results using a smaller
             * split Y offset increment.
             */
            if (contactDetail.contains(System.lineSeparator())) {
                int splitYOffset = 0;

                final int splitYOffsetIncrement = 20;

                final int yOffsetIncrement = 50;

                String[] splitDetails = contactDetail.split("\n");

                for (String splitDetail : splitDetails) {

                    graphics.setColor(getColorFromPallet("Text"));

                    graphics.drawString(
                        splitDetail.trim(),
                        contactDetailsXOffset,
                        contactDetailsYOffset + splitYOffset
                    );

                    splitYOffset += splitYOffsetIncrement;
                }

                graphics.setColor(getColorFromPallet("Light"));

                graphics.drawLine(
                    X_OFFSET,
                    contactDetailsYOffset + yOffsetIncrement,
                    X_OFFSET + BUSINESS_CARD_WIDTH,
                    contactDetailsYOffset + yOffsetIncrement
                );
            } else {
                final int yOffsetIncrement = 30;

                graphics.setColor(getColorFromPallet("Text"));

                graphics.drawString(
                    contactDetail,
                    contactDetailsXOffset,
                    contactDetailsYOffset
                );

                graphics.setColor(getColorFromPallet("Light"));

                graphics.drawLine(
                    X_OFFSET,
                    contactDetailsYOffset + yOffsetIncrement,
                    X_OFFSET + BUSINESS_CARD_WIDTH,
                    contactDetailsYOffset + yOffsetIncrement
                );
            }

            contactDetailsYOffset += contactDetailsYIncrement;
        }

        graphics.setColor(getColorFromPallet("Text"));

        graphics.setFont(getFontResource(
            graphics,
            FONT_FAMILY,
            FONT_SIZE,
            Font.ITALIC
        ));

        graphics.drawString(
            "\"ASCII stupid question, get a stupid ANSI\"",
            X_OFFSET + quoteXOffset,
            contactDetailsYOffset + 5
        );
    }

    private void drawContactIcons(final Graphics graphics) {
        String[] contactIcons = {"\uf0e0", "\uf095", "\uf0ac", "\uf041"};

        final int contactIconXOffset = X_OFFSET + 30;

        final int contactIconYOffsetIncrement = 80;

        int contactIconOffset = Y_OFFSET + 255;

        graphics.setColor(getColorFromPallet("Accent"));

        graphics.setFont(getFontResource(
            graphics,
            "fontawesome-webfont.ttf",
            ICON_FONT_SIZE,
            Font.PLAIN
        ));

        for (String contactIcon : contactIcons) {
            graphics.drawString(contactIcon, contactIconXOffset, contactIconOffset);

            contactIconOffset += contactIconYOffsetIncrement;
        }
    }

    private void drawFooter(final Graphics graphics) {
        final int footerYOffset = Y_OFFSET + 600;

        final int footerHeight = 10;

        graphics.setColor(getColorFromPallet("Accent"));

        graphics.fillRect(X_OFFSET, footerYOffset, BUSINESS_CARD_WIDTH, footerHeight);
    }

    private BufferedImage getImageResource(
        final Graphics graphics,
        final String path
    ) {
        BufferedImage background = null;

        try {
            background = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            graphics.setColor(Color.WHITE);

            graphics.drawString(e.getMessage(), 0, 0);
        }


        return background;
    }

    private Font getFontResource(
        final Graphics graphics,
        final String fontFamily,
        final float fontSize,
        final int fontType
    ) {
        Font font = null;

        try {
            font = Font.createFont(
                Font.TRUETYPE_FONT,
                getClass().
                    getResourceAsStream(FONT_RESOURCES + "/" + fontFamily)
            );

            font = font.deriveFont(fontType, fontSize);
        } catch (IOException | FontFormatException e) {
            graphics.setColor(Color.WHITE);

            graphics.drawString(e.getMessage(), 0, 0);
        }

        return font;
    }

    private void setColorPallet() {
        colorPallet.put("Base", "#333333");

        colorPallet.put("Contrast", "#FFFFFF")oolorPallet.put("Accent", "#F94B44");

        colorPallet.put("Trim", "#AA373A");

        colorPallet.put("Offset", "#9B643B");

        colorPallet.put("Text", "#858585");

        colorPallet.put("Light", "#E3E3E3");
    }

    private Color getColorFromPallet(final String pallet) {
        Color color;

        color = Color.decode(colorPallet.get(pallet));

        return color;
    }
}
