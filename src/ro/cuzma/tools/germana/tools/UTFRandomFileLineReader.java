package ro.cuzma.tools.germana.tools;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class UTFRandomFileLineReader {

    private final Charset    charset = Charset.forName("utf-8");

    private RandomAccessFile buffer;

    private ByteBuffer       temp    = ByteBuffer.allocate(4096);

    private boolean          eof     = false;

    public UTFRandomFileLineReader(RandomAccessFile channel) {

        this.buffer = channel;

    }

    public String readLine() throws IOException {
        if (eof) {
            return null;
        }
        byte x = 0;
        temp.clear();
        // while ((byte) -1 != (x = (buffer.readByte())) && x != '\n') {
        while (true) {
            try {
                x = buffer.readByte();
            } catch (java.io.EOFException e) {
                break;
            }
            if (x == -1 || x == '\n') {
                break;
            }
            if (temp.position() == temp.capacity()) {
                temp = addCapacity(temp);
            }
            temp.put(x);
        }
        if (x == -1) {
            eof = true;
        }
        temp.flip();
        if (temp.hasRemaining()) {
            return charset.decode(temp).toString();
        } else {
            return null;
        }
    }

    private ByteBuffer addCapacity(ByteBuffer temp) {

        ByteBuffer t = ByteBuffer.allocate(temp.capacity() + 1024);

        temp.flip();

        t.put(temp);

        return t;

    }

    public static void main(String[] args) throws IOException {
        // String fileName = "build.xml";
        String fileName = "Germana.csv";
        RandomAccessFile file = new RandomAccessFile(fileName, "r");

        UTFRandomFileLineReader reader = new UTFRandomFileLineReader(file);
        int i = 1;
        while (true) {
            String s = reader.readLine();
            if (s == null)
                break;
            System.out.println("\n line  " + i++);
            s = s + "\n";
            // String tmp = "";
            // for (byte b : s.getBytes(Charset.forName("utf-8"))) {
            // tmp += (char) b;
            // }
            System.out.println(s);
            System.out.printf("\n");
        }

    }
}
