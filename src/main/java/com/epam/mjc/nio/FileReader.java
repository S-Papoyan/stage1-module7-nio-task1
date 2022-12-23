package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileReader {

    public Profile getDataFromFile(File file) {
        StringBuilder builder = new StringBuilder();
        try (RandomAccessFile aFile = new RandomAccessFile(file.getAbsolutePath(), "r")) {
            FileChannel channel = aFile.getChannel();
            long size = channel.size();
            ByteBuffer buffer = ByteBuffer.allocate((int) size);
            channel.read(buffer);
            buffer.flip();
            for (int i = 0; i < size; i++) {
                builder.append((char) buffer.get());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String string = builder.toString();
        String[] split = string.split("\n");
        String[] values = new String[4];
        for (int i = 0; i < split.length; i++) {
            String[] split1 = split[i].split(":", 2);
            values[i] = split1[1].trim();
        }
        return new Profile(values[0], Integer.parseInt(values[1]), values[2], Long.parseLong(values[3]));
    }
}