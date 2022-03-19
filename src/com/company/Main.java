package com.company;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

class CRC{
    int[] CRC_TABLE;

    public CRC() {
        CRC_TABLE = new int[256];
        for (int i = 0; i < 256; ++i) {
            int code = i;
            for (int j = 0; j < 8; ++j) {
                code = ((code & 0x01) != 0 ? 0xEDB88320 ^ (code >>> 1) : (code >>> 1));
            }
            CRC_TABLE[i] = code;
        }
    }

    public int calculate(String text) {
        int crc = -1;
        for (int i = 0; i < text.length(); ++i) {
      	    int code = Character.codePointAt(text, i);
            crc = CRC_TABLE[(code ^ crc) & 0xFF] ^ (crc >>> 8);
        }

        return (-1 ^ crc) >>> 0;
    }
}

class Time {
    public void getLocalTime() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("Current local time: " + df.format(date));
    }

    public void getGlobalTime(){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssXXX");

        df.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println("Current global time: " + df.format(date));
    }
}

class FileOperations{
    public String[] splitLines(String text) {
        return text.split("\r\n|\n\r|\n|\r");
    }

    public void displayFile(String path) throws IOException {
        try (InputStream input = new BufferedInputStream(new FileInputStream(path))) {
            byte[] buffer = new byte[8192];
            for (int length = 0; (length = input.read(buffer)) != -1; ) {
                System.out.write(buffer, 0, length);
            }
        }
    }

    public void writeFile(String text) throws IOException {
        try (FileOutputStream file = new FileOutputStream("./output.txt")) {
            byte bytes[] = text.getBytes();
            file.write(bytes);
            file.close();
        }
    }
}

public class Main {

    public static void main(String[] args) {
        FileOperations operations = new FileOperations();

        try {
            operations.displayFile("./file.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            operations.writeFile(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] array = {4, 5, 7, 11, 12, 15, 15, 21, 40, 45 };
        int index = searchIndex(array, 11); // we want to find index for 11

        System.out.println(index);

        CRC crc = new CRC();
        System.out.println(crc.calculate("This is example text ..."));

        Time time = new Time();
        time.getLocalTime();
        time.getGlobalTime();

        String[] lines = operations.splitLines("line 1\nline 2");

        for (String line : lines) {
            System.out.println(line);
        }
    }

    private static int searchIndex(int[] array, int value) {
        int index = 0;
        int limit = array.length - 1;
        while (index <= limit) {
            int point = (int)Math.ceil((index + limit) / 2);
            int entry = array[point];
            if (value > entry) {
                index = point + 1;
                continue;
            }
            if (value < entry) {
                limit = point - 1;
                continue;
            }
            return point; // value == entry
        }
        return -1;
    }
}
