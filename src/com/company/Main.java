package com.company;

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

public class Main {

    public static void main(String[] args) {
        int[] array = {4, 5, 7, 11, 12, 15, 15, 21, 40, 45 };
        int index = searchIndex(array, 11); // we want to find index for 11

        System.out.println(index);

        CRC crc = new CRC();
        System.out.println(crc.calculate("This is example text ..."));
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