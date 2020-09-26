import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static java.lang.System.*;

public class EncodingGenerator  {
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(new File("input.txt"))) {
            new EncodingGenerator(scanner.nextLine());
        }
    }

    private Map<Character, Byte> encodingMap = new HashMap<>();

    {
        encodingMap.put('А', (byte) 0xC0);
        encodingMap.put('Б', (byte) 0xC1);
        encodingMap.put('В', (byte) 0xC2);
        encodingMap.put('Г', (byte) 0xC3);
        encodingMap.put('Д', (byte) 0xC4);
        encodingMap.put('Е', (byte) 0xC5);
        encodingMap.put('Ж', (byte) 0xC6);
        encodingMap.put('З', (byte) 0xC7);
        encodingMap.put('И', (byte) 0xC8);
        encodingMap.put('Й', (byte) 0xC9);
        encodingMap.put('К', (byte) 0xCA);
        encodingMap.put('Л', (byte) 0xCB);
        encodingMap.put('М', (byte) 0xCC);
        encodingMap.put('Н', (byte) 0xCD);
        encodingMap.put('О', (byte) 0xCE);
        encodingMap.put('П', (byte) 0xCF);
        encodingMap.put('Р', (byte) 0xD0);
        encodingMap.put('С', (byte) 0xD1);
        encodingMap.put('Т', (byte) 0xD2);
        encodingMap.put('У', (byte) 0xD3);
        encodingMap.put('Ф', (byte) 0xD4);
        encodingMap.put('Х', (byte) 0xD5);
        encodingMap.put('Ц', (byte) 0xD6);
        encodingMap.put('Ч', (byte) 0xD7);
        encodingMap.put('Ш', (byte) 0xD8);
        encodingMap.put('Щ', (byte) 0xD9);
        encodingMap.put('Ъ', (byte) 0xDA);
        encodingMap.put('Ы', (byte) 0xDB);
        encodingMap.put('Ь', (byte) 0xDC);
        encodingMap.put('Э', (byte) 0xDD);
        encodingMap.put('Ю', (byte) 0xDE);
        encodingMap.put('Я', (byte) 0xDF);
        encodingMap.put('а', (byte) 0xE0);
        encodingMap.put('б', (byte) 0xE1);
        encodingMap.put('в', (byte) 0xE2);
        encodingMap.put('г', (byte) 0xE3);
        encodingMap.put('д', (byte) 0xE4);
        encodingMap.put('е', (byte) 0xE5);
        encodingMap.put('ж', (byte) 0xE6);
        encodingMap.put('з', (byte) 0xE7);
        encodingMap.put('и', (byte) 0xE8);
        encodingMap.put('й', (byte) 0xE9);
        encodingMap.put('к', (byte) 0xEA);
        encodingMap.put('л', (byte) 0xEB);
        encodingMap.put('м', (byte) 0xEC);
        encodingMap.put('н', (byte) 0xED);
        encodingMap.put('о', (byte) 0xEE);
        encodingMap.put('п', (byte) 0xEF);
        encodingMap.put('р', (byte) 0xF0);
        encodingMap.put('с', (byte) 0xF1);
        encodingMap.put('т', (byte) 0xF2);
        encodingMap.put('у', (byte) 0xF3);
        encodingMap.put('ф', (byte) 0xF4);
        encodingMap.put('х', (byte) 0xF5);
        encodingMap.put('ц', (byte) 0xF6);
        encodingMap.put('ч', (byte) 0xF7);
        encodingMap.put('ш', (byte) 0xF8);
        encodingMap.put('щ', (byte) 0xF9);
        encodingMap.put('ъ', (byte) 0xFA);
        encodingMap.put('ы', (byte) 0xFB);
        encodingMap.put('ь', (byte) 0xFC);
        encodingMap.put('э', (byte) 0xFD);
        encodingMap.put('ю', (byte) 0xFE);
        encodingMap.put('я', (byte) 0xFF);
        encodingMap.put(' ', (byte) 0x20);
        encodingMap.put(',', (byte) 0x2C);
        encodingMap.put('.', (byte) 0x2E);
        encodingMap.put('0', (byte) 0x30);
        encodingMap.put('1', (byte) 0x31);
        encodingMap.put('2', (byte) 0x32);
        encodingMap.put('3', (byte) 0x33);
        encodingMap.put('4', (byte) 0x34);
        encodingMap.put('5', (byte) 0x35);
        encodingMap.put('6', (byte) 0x36);
        encodingMap.put('7', (byte) 0x37);
        encodingMap.put('8', (byte) 0x38);
        encodingMap.put('9', (byte) 0x39);
    }

    public EncodingGenerator(String text) {
        out.println("Input text: " + text);
        boolean[] bits = textToBits(text);

        generateNRZ(bits);
        generateRZ(bits);
        generateAMI(bits);
        generateNRZI(bits);
        generateMLT3(bits);
        generateM2(bits);
    }

    private void generateNRZ(boolean[] bits) {
        try {
            BufferedImage image = new BufferedImage(30*bits.length, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) image.getGraphics();

            int width = image.getWidth();
            int height = image.getHeight();
            int stepSize = width/bits.length;

            drawBasicTemplate(g, bits, width, height);

            for (int i = 0; i < bits.length; i++) {
                boolean bit = bits[i];
                int yPos = height/2 + height/4 * (bit ? -1 : 1);

                g.setColor(bit ? Color.blue : Color.red);
                g.setStroke(new BasicStroke(3));
                g.drawLine(i * stepSize, yPos, (i + 1) * stepSize, yPos);

                if (i != 0 && bits[i - 1] != bit) { // previous bit is different
                    g.setColor(Color.black);
                    g.drawLine(i * stepSize, height/2 - height/4, i * stepSize, height/2 + height/4);
                }
            }

            ImageIO.write(image, "png", new File("nrz.png"));
        } catch(IOException e) {
            err.println("Could not generate NRZ: " + e.getMessage());
        }
    }

    private void generateRZ(boolean[] bits) {
        try {
            BufferedImage image = new BufferedImage(30*bits.length, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) image.getGraphics();

            int width = image.getWidth();
            int height = image.getHeight();
            int stepSize = width/bits.length;

            drawBasicTemplate(g, bits, width, height);

            for (int i = 0; i < bits.length; i++) {
                boolean bit = bits[i];
                int yPos = height/2 + height/4 * (bit ? -1 : 1);

                g.setStroke(new BasicStroke(3));
                g.setColor(Color.black);
                g.drawLine(i * stepSize + stepSize/2, height/2, (i + 1) * stepSize, height/2);

                g.drawLine(i * stepSize, height/2, i * stepSize, yPos);
                g.drawLine(i * stepSize + stepSize/2, height/2, i * stepSize + stepSize/2, yPos);

                g.setColor(bit ? Color.blue : Color.red);
                g.drawLine(i * stepSize, yPos, i * stepSize + stepSize/2, yPos);
            }

            ImageIO.write(image, "png", new File("rz.png"));
        } catch(IOException e) {
            err.println("Could not generate RZ: " + e.getMessage());
        }
    }

    private void generateAMI(boolean[] bits) {
        try {
            BufferedImage image = new BufferedImage(30*bits.length, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) image.getGraphics();

            int width = image.getWidth();
            int height = image.getHeight();
            int stepSize = width/bits.length;

            drawBasicTemplate(g, bits, width, height);
            g.setStroke(new BasicStroke(3));

            boolean phase = false;
            for (int i = 0; i < bits.length; i++) {
                boolean bit = bits[i];
                int yPos = height/2 + height/4 * (phase ? -1 : 1);

                if (bit) {
                    g.setColor(Color.blue);
                    g.drawLine(i * stepSize, yPos, (i + 1) * stepSize, yPos);
                    g.setColor(Color.black);
                    g.drawLine(i * stepSize, height/2, i * stepSize, yPos);
                    g.drawLine(i * stepSize + stepSize, height/2, i * stepSize + stepSize, yPos);
                    phase = !phase;
                } else {
                    g.setColor(Color.red);
                    g.drawLine(i * stepSize, height/2, i * stepSize + stepSize, height/2);
                }
            }

            ImageIO.write(image, "png", new File("ami.png"));
        } catch(IOException e) {
            err.println("Could not generate AMI: " + e.getMessage());
        }
    }

    private void generateNRZI(boolean[] bits) {
        try {
            BufferedImage image = new BufferedImage(30*bits.length, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) image.getGraphics();

            int width = image.getWidth();
            int height = image.getHeight();
            int stepSize = width/bits.length;

            drawBasicTemplate(g, bits, width, height);
            g.setStroke(new BasicStroke(3));

            boolean phase = false;
            for (int i = 0; i < bits.length; i++) {
                boolean bit = bits[i];

                if (bit) {
                    g.setColor(Color.black);
                    int oppositeHeight = height / 2 - height / 4 * (phase ? 1 : -1);
                    g.drawLine(i * stepSize, oppositeHeight, (i + 1) * stepSize, oppositeHeight);
                    g.setColor(Color.blue);
                    g.drawLine(i * stepSize, height/2 + height/4 * (phase ? 1 : -1), i * stepSize, oppositeHeight);
                    phase = !phase;
                } else {
                    g.setColor(Color.red);
                    g.drawLine(i * stepSize, height/2 + height/4 * (phase ? 1 : -1), i * stepSize + stepSize, height/2 + height/4 * (phase ? 1 : -1));
                }
            }

            ImageIO.write(image, "png", new File("nrzi.png"));
        } catch(IOException e) {
            err.println("Could not generate NRZI: " + e.getMessage());
        }
    }

    private void generateMLT3(boolean[] bits) {
        try {
            BufferedImage image = new BufferedImage(30*bits.length, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) image.getGraphics();

            int width = image.getWidth();
            int height = image.getHeight();
            int stepSize = width/bits.length;

            drawBasicTemplate(g, bits, width, height);
            g.setStroke(new BasicStroke(3));

            int phaseLevel = 0;
            boolean directionUp = true;
            for (int i = 0; i < bits.length; i++) {
                boolean bit = bits[i];

                if (bit) {
                    if (directionUp) {
                        phaseLevel--;
                        g.setColor(Color.blue);
                        g.drawLine(i * stepSize, height/2 + height/4 * phaseLevel, i * stepSize, height/2 + height/4 * (phaseLevel + 1));
                        if (phaseLevel == -1)
                            directionUp = false;
                    } else {
                        phaseLevel++;
                        g.setColor(Color.blue);
                        g.drawLine(i * stepSize, height/2 + height/4 * phaseLevel, i * stepSize, height/2 + height/4 * (phaseLevel - 1));
                        if (phaseLevel == 1)
                            directionUp = true;
                    }
                    g.setColor(Color.black);
                    g.drawLine(i * stepSize, height/2 + height/4 * phaseLevel, (i + 1) * stepSize, height/2 + height/4 * phaseLevel);
                    g.setColor(Color.black);
                } else {
                    g.setColor(Color.red);
                    g.drawLine(i * stepSize, height/2 + height/4 * phaseLevel, i * stepSize + stepSize, height/2 + height/4 * phaseLevel);
                }
            }

            ImageIO.write(image, "png", new File("mlt3.png"));
        } catch(IOException e) {
            err.println("Could not generate MLT-3: " + e.getMessage());
        }
    }

    private void generateM2(boolean[] bits) {
        try {
            BufferedImage image = new BufferedImage(30*bits.length, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) image.getGraphics();

            int width = image.getWidth();
            int height = image.getHeight();
            int stepSize = width/bits.length;

            drawBasicTemplate(g, bits, width, height);
            g.setStroke(new BasicStroke(3));

            boolean previous = false;
            for (int i = 0; i < bits.length; i++) {
                boolean bit = bits[i];

                g.setColor(Color.black);

                if (bit == previous)
                    g.drawLine(stepSize * i, height/2 - height/4, stepSize * i, height/2 + height/4);

                int bitMultiplier = bit ? 1 : -1;

                g.drawLine(i * stepSize, height/2 + height/4 * -bitMultiplier, i * stepSize + stepSize/2, height/2 + height/4 * -bitMultiplier);
                g.drawLine(i * stepSize + stepSize/2, height/2 + height/4 * bitMultiplier, i * stepSize + stepSize, height/2 + height/4 * bitMultiplier);

                g.setColor(bit ? Color.blue : Color.red);
                g.drawLine(i * stepSize + stepSize/2, height/2 - height/4, i * stepSize + stepSize/2, height/2 + height/4);
                previous = bit;
            }

            ImageIO.write(image, "png", new File("m2.png"));
        } catch(IOException e) {
            err.println("Could not generate M2: " + e.getMessage());
        }
    }

    private void drawBasicTemplate(Graphics2D g, boolean[] bits, int width, int height) {
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);
        int stepSize = width/bits.length;

        g.setColor(Color.black);
        g.setStroke(new BasicStroke(3));
        g.drawLine(0, 0, 0, height);
        g.drawLine(width, 0, width, height);

        Font font = new Font("Arial", Font.PLAIN, 14);

        g.setStroke(new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 10, new float[] {1, 2}, 0));
        for (int i = 0; i < bits.length; i++) {
            int position = (i + 1) * width/bits.length;
            g.drawLine(position, 0, position, height);

            FontMetrics fontMetrics = g.getFontMetrics(font);
            String string = bits[i] ? "1" : "0";
            g.drawString(string, stepSize * i + stepSize/2 - fontMetrics.stringWidth(string)/2, fontMetrics.getHeight());
        }
    }

    private boolean[] textToBits(String text) {
        boolean[] result = new boolean[text.length() * 8];
        int writePtr = 0;

        for (char c : text.toCharArray()) {
            byte encodedChar;
            if (!encodingMap.containsKey(c)) {
                encodedChar = encodingMap.get('.');
                out.println("Unknown character, it will be replaced with '.': " + c);
            } else
                encodedChar = encodingMap.get(c);
            
            for (int i = 0; i < 8; i++) {
                result[writePtr] = encodedChar >>> 7 != 0;
                encodedChar = (byte) (encodedChar << 1);
                writePtr++;
            }
        }

        return result;
    }
}