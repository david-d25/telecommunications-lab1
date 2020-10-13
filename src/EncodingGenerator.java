import java.util.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

import static java.lang.System.*;

public class EncodingGenerator  {
    private static final int C = 100; // Mhz
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

    boolean[][] fourBFiveBTable = new boolean[][] {
            new boolean[] {true, true, true, true, false},      // 0000 -> 11110
            new boolean[] {false, true, false, false, true},    // 0001 -> 01001
            new boolean[] {true, false, true, false, false},    // 0010 -> 10100
            new boolean[] {true, false, true, false, true},     // 0011 -> 10101
            new boolean[] {false, true, false, true, false},    // 0100 -> 01010
            new boolean[] {false, true, false, true, true},     // 0101 -> 01011
            new boolean[] {false, true, true, true, false},     // 0110 -> 01110
            new boolean[] {false, true, true, true, true},      // 0111 -> 01111
            new boolean[] {true, false, false, true, false},    // 1000 -> 10010
            new boolean[] {true, false, false, true, true},     // 1001 -> 10011
            new boolean[] {true, false, true, true, false},     // 1010 -> 10110
            new boolean[] {true, false, true, true, true},      // 1011 -> 10111
            new boolean[] {true, true, false, true, false},     // 1100 -> 11010
            new boolean[] {true, true, false, true, true},      // 1101 -> 11011
            new boolean[] {true, true, true, false, false},     // 1110 -> 11100
            new boolean[] {true, true, true, false, true},      // 1111 -> 11101
    };

    public EncodingGenerator(String text) {
        out.println("Input text: " + text);
        boolean[] bits = textToBits(text);

        byte[] bytes = bitsToBytes(bits);
        out.println("Hex: " + bytesToHex(bytes));
        out.println("Bin: " + bytesToBin(bytes));
        out.println("Message length: " + bytes.length + " bytes (" + bits.length + " bits)");

        boolean[] fourBFiveBBits = fourBFiveB(bits);
        out.println("4B/5B encoded hex: " + bitsToHex(fourBFiveBBits));
        out.println("4B/5B encoded bin: " + bitsToBin(fourBFiveBBits));
        out.println("4B/5B message length: " + fourBFiveBBits.length/8f + " bytes (" + fourBFiveBBits.length + " bits)");
        out.printf(
                "Redundancy: %s/%s = %s/%s = %.2f (%s%%)\n",
                fourBFiveBBits.length/8f - bytes.length,
                bytes.length,
                fourBFiveBBits.length - bits.length,
                bits.length,
                1f*(fourBFiveBBits.length - bits.length)/bits.length,
                100*(fourBFiveBBits.length - bits.length)/bits.length
        );

        out.println("Generating images...");

        generateNRZ(bits, "nrz.png");
        generateRZ(bits, "rz.png");
        generateAMI(bits, "ami.png");
        generateNRZI(bits, "nrzi.png");
        generateMLT3(bits, "mlt3.png");
        generateM2(bits, "m2.png");

        generateNRZ(fourBFiveBBits, "nrz_4b5b.png");
        generateRZ(fourBFiveBBits, "rz_4b5b.png");
        generateAMI(fourBFiveBBits, "ami_4b5b.png");
        generateNRZI(fourBFiveBBits, "nrzi_4b5b.png");
        generateMLT3(fourBFiveBBits, "mlt3_4b5b.png");
        generateM2(fourBFiveBBits, "m2_4b5b.png");

        out.println("Images generated");
    }

    // f0 = C/2 (частота основной гармоники)
    // fl = (самая длинная последовательность 1 или 0, )
    private void generateNRZ(boolean[] bits, String filename) {
        try {
            BufferedImage image = new BufferedImage(30*bits.length, 100, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = (Graphics2D) image.getGraphics();

            int width = image.getWidth();
            int height = image.getHeight();
            int stepSize = width/bits.length;

            drawBasicTemplate(g, bits, width, height);

            int longestSequence = 0;

            boolean sequenceBit = bits[0];
            int sequenceLen = 0;

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

                if (bit == sequenceBit) {
                    sequenceLen++;
                    if (sequenceLen > longestSequence)
                        longestSequence = sequenceLen;
                } else {

                    sequenceLen = 1;
                }
                sequenceBit = bit;
            }

            out.printf("%s -> main_freq = %d MHz, low_freq = %d MHz, high_freq = %d MHz, spectre = %d MHz\n",
                    filename,
                    C/2,
                    longestSequence*2,
                    7*C/2,
                    (7*C/2 - longestSequence*2)
            );
            ImageIO.write(image, "png", new File(filename));
        } catch(IOException e) {
            err.println("Could not generate NRZ: " + e.getMessage());
        }
    }

    private void generateRZ(boolean[] bits, String filename) {
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

            out.printf("%s -> main_freq = %d MHz, low_freq = %d MHz, high_freq = %d MHz, spectre = %d MHz\n", filename, C, C/4, C, (C - C/4));
            ImageIO.write(image, "png", new File(filename));
        } catch(IOException e) {
            err.println("Could not generate RZ: " + e.getMessage());
        }
    }

    private void generateAMI(boolean[] bits, String filename) {
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

            out.printf("%s -> main_freq = %d MHz, low_freq = %d MHz, high_freq = %d MHz, spectre = %d MHz\n", filename, C, C/4, C/2, (C - C/4));
            ImageIO.write(image, "png", new File(filename));
        } catch(IOException e) {
            err.println("Could not generate AMI: " + e.getMessage());
        }
    }

    private void generateNRZI(boolean[] bits, String filename) {
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

            ImageIO.write(image, "png", new File(filename));
        } catch(IOException e) {
            err.println("Could not generate NRZI: " + e.getMessage());
        }
    }

    private void generateMLT3(boolean[] bits, String filename) {
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

            ImageIO.write(image, "png", new File(filename));
        } catch(IOException e) {
            err.println("Could not generate MLT-3: " + e.getMessage());
        }
    }

    private void generateM2(boolean[] bits, String filename) {
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

            ImageIO.write(image, "png", new File(filename));
        } catch(IOException e) {
            err.println("Could not generate M2: " + e.getMessage());
        }
    }

    private boolean[] fourBFiveB(boolean[] bits) {
        if (bits.length % 4 != 0)
            throw new IllegalArgumentException("The bits number must be multiple of 4, but it's actually " + bits.length);

        boolean[] result = new boolean[bits.length*5/4];
        int resultPtr = 0;
        int srcPtr = 0;
        while (srcPtr < bits.length) {
            int tableIdx = (bits[srcPtr] ? 8 : 0) + (bits[srcPtr + 1] ? 4 : 0) + (bits[srcPtr + 2] ? 2 : 0) + (bits[srcPtr + 3] ? 1 : 0);
            boolean[] encoded = fourBFiveBTable[tableIdx];
            for (boolean b : encoded) {
                result[resultPtr] = b;
                resultPtr++;
            }
            srcPtr += 4;
        }
        return result;
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

    private byte[] bitsToBytes(boolean[] bits) {
        if (bits.length % 8 != 0)
            throw new IllegalArgumentException("Bits number must be multiple of 8, but it's actually " + bits.length);

        byte[] result = new byte[bits.length/8];
        int bitsPtr = 0;
        int resultPtr = 0;
        while (bitsPtr < bits.length) {
            result[resultPtr] = (byte) (
                    (bits[bitsPtr] ? 128 : 0) +
                            (bits[bitsPtr + 1] ? 64 : 0) +
                            (bits[bitsPtr + 2] ? 32 : 0) +
                            (bits[bitsPtr + 3] ? 16 : 0) +
                            (bits[bitsPtr + 4] ? 8 : 0) +
                            (bits[bitsPtr + 5] ? 4 : 0) +
                            (bits[bitsPtr + 6] ? 2 : 0) +
                            (bits[bitsPtr + 7] ? 1 : 0)
            );

            bitsPtr += 8;
            resultPtr++;
        }
        return result;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes)
            builder.append(String.format("%02X ", b));
        return builder.toString();
    }

    private String bytesToBin(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes)
            builder.append(String.format("%8s ", Integer.toBinaryString(b & 0xFF)));
        return builder.toString();
    }

    private String bitsToHex(boolean[] bits) {
        if (bits.length % 4 != 0)
            throw new IllegalStateException("Bits number must be multiple of 4, but actually it's " + bits.length);

        StringBuilder builder = new StringBuilder();
        int bitPtr = 0;
        while (bitPtr < bits.length) {
            byte b = (byte) (
                    (bits[bitPtr] ? 8 : 0) +
                            (bits[bitPtr + 1] ? 4 : 0) +
                            (bits[bitPtr + 2] ? 2 : 0) +
                            (bits[bitPtr + 3] ? 1 : 0)
            );
            builder.append(String.format("%1X", b));
            bitPtr += 4;
        }

        return builder.toString();
    }

    private String bitsToBin(boolean[] bits) {
        StringBuilder builder = new StringBuilder();
        for (boolean b : bits)
            builder.append(b ? 1 : 0);
        return builder.toString();
    }

}