package com.huntbr.proxy.streams;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MinecraftInputStream extends DataInputStream {

    public MinecraftInputStream(InputStream in) {
        super(in);
    }

    public int readVarInt() throws IOException {
        int target = 0;
        int length = 0;
        byte data;

        do {
            data = readByte();

            target |= (data & 0x7F) << (length * 7);

            if (++length > 5) {
                throw new IOException("VarInt is too big");
            }

        } while ((data & 0x80) == 0x80);

        return target;
    }

    public String readString() throws IOException {
        int length = readVarInt();

        byte[] target = new byte[length];
        readFully(target);

        return new String(target);
    }
}
