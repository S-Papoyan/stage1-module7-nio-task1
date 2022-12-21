package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.MalformedInputException;

public class FileReader {

    public Profile getDataFromFile(File file) {
        StringBuilder temp = new StringBuilder();
        try (RandomAccessFile aFile = new RandomAccessFile(file.getAbsolutePath(), "r")) {
            FileChannel channel = aFile.getChannel();
            long size = channel.size();
            ByteBuffer buffer = ByteBuffer.allocate((int) size);
            channel.read(buffer);
            buffer.flip();
            for (int i = 0; i < size; i++) {
                temp.append((char) buffer.get());
            }
        } catch (MalformedInputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] split = temp.toString().split("\r\n");
        String[] values = new String[4];
        for (int i = 0; i < split.length; i++) {
            String[] split1 = split[i].split(": ");
            values[i] = split1[1];
        }
        return new Profile(values[0], Integer.parseInt(values[1]), values[2], Long.parseLong(values[3]));
    }
}