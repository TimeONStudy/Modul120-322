package org.example.modul_122322;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class SVGLoader {

    public static Image loadSVG(String resourcePath, double width, double height) {
        try (InputStream inputStream = SVGLoader.class.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("SVG file not found: " + resourcePath);
            }

            TranscoderInput input = new TranscoderInput(inputStream);
            JavaFXImageTranscoder transcoder = new JavaFXImageTranscoder(width, height);
            transcoder.transcode(input, null);
            return transcoder.getImage();
        } catch (TranscoderException | IllegalArgumentException | NullPointerException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Helper to create a Button with an SVG icon
    public static Button createButtonWithSVG(String text, String svgPath, double iconWidth, double iconHeight) {
        Image svgImage = loadSVG(svgPath, iconWidth, iconHeight);
        if (svgImage != null) {
            ImageView svgIcon = new ImageView(svgImage);
            Button button = new Button(text, svgIcon);
            button.setStyle("-fx-font-size: 14px; -fx-padding: 10;"); // Optional styling
            return button;
        } else {
            System.err.println("Failed to load SVG for button: " + svgPath);
            return new Button(text); // Fallback to text-only button
        }
    }

    private static class JavaFXImageTranscoder extends ImageTranscoder {
        private Image image;
        private final double width;
        private final double height;

        public JavaFXImageTranscoder(double width, double height) {
            this.width = width;
            this.height = height;
        }

        @Override
        public BufferedImage createImage(int width, int height) {
            // Create a BufferedImage with the specified dimensions
            return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }

        @Override
        public void writeImage(BufferedImage bufferedImage, TranscoderOutput transcoderOutput) {
            // Convert BufferedImage to JavaFX Image
            this.image = SwingFXUtils.toFXImage(bufferedImage, null);
        }

        public Image getImage() {
            return image;
        }
    }
}

