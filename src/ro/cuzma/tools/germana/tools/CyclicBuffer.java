package ro.cuzma.tools.germana.tools;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CyclicBuffer {

    private static final int size   = 3;

    private FileChannel      channel;

    private ByteBuffer       buffer = ByteBuffer.allocate(size);

    public CyclicBuffer(FileChannel channel) {

        this.channel = channel;

    }

    private int read() throws IOException {

        return channel.read(buffer);

    }

    /**
     * 022 Returns the byte read 023
     * 
     * 024
     * 
     * @return byte read -1 - end of file reached 025
     * @throws IOException
     *             026
     */

    public byte get() throws IOException {

        if (buffer.hasRemaining()) {

            return buffer.get();

        } else {

            buffer.clear();

            int eof = read();

            if (eof == -1) {

                return (byte) eof;

            }

            buffer.flip();

            return buffer.get();

        }

    }

}
