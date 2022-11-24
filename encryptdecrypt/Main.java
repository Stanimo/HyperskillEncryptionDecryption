package encryptdecrypt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        String comm = "enc";
        String str = "";
        String pathIn = "";
        String pathOut = "";
        String alg = "shift";
        int key = 0;

        for (int i = 0; i < args.length - 1; i++) {
            if ("-mode".equals(args[i])) {
                comm = args[i + 1];
            }

            if ("-key".equals(args[i])) {
                key = Integer.parseInt(args[i + 1]);
            }

            if ("-data".equals(args[i])) {
                    str = args[i + 1];
            } else if ("".equals(str) && "-in".equals(args[i])) {
                pathIn = args[i + 1];
                try {
                    BufferedReader reader = Files.newBufferedReader(Path.of(pathIn), StandardCharsets.UTF_8);
                    str = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if ("-out".equals(args[i])) {
                pathOut = args[i + 1];
            }

            if ("-alg".equals(args[i])) {
                alg = args[i + 1];
            }
        }

        if ("".equals(pathOut)) {
            System.out.println(routing(comm, str, key, alg));
        } else {
            try {
                BufferedWriter writer = Files.newBufferedWriter(Path.of(pathOut), StandardCharsets.UTF_8);
                writer.write(routing(comm, str, key, alg));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String encrypt (String str, int key, String alg) {
        String encryptedStr = "";
        char newStr;

        if ("unicode".equals(alg)) {
            for (char c : str.toCharArray()) {
                newStr = (char) (c + key);
                encryptedStr += newStr;
            }
        } else {
            for (char c : str.toCharArray()) {
                if ((c >= 97 && c <= 122)) {
                    if (c + key > 122) {
                        newStr = (char) (96 + ((c + key) - 122));
                        encryptedStr += newStr;
                    } else if (c + key >= 97) {
                        newStr = (char) (c + key);
                        encryptedStr += newStr;
                    }
                } else if (c >= 65 && c <= 90) {
                    if (c + key > 90) {
                        newStr = (char) (64 + ((c + key) - 90));
                        encryptedStr += newStr;
                    } else if (c + key >= 65) {
                        newStr = (char) (c + key);
                        encryptedStr += newStr;
                    }
                } else {
                    encryptedStr += c;
                }
            }
        }
        return encryptedStr;
    }

    public static String decrypt (String str, int key, String alg) {
        String encryptedStr = "";
        char newStr;

        if ("unicode".equals(alg)) {
            for (char c : str.toCharArray()) {
                newStr = (char) (c - key);
                encryptedStr += newStr;
            }
        } else {
            for (char c : str.toCharArray()) {
                if ((c >= 97 && c <= 122)) {
                    if (c - key < 97) {
                        newStr = (char) (123 - (97 - (c - key)));
                        encryptedStr += newStr;
                    } else if (c - key >= 97) {
                        newStr = (char) (c - key);
                        encryptedStr += newStr;
                    }
                } else if (c >= 65 && c <= 90) {
                    if (c - key < 65) {
                        newStr = (char) (91 - (65 - (c - key)));
                        encryptedStr += newStr;
                    } else if (c - key >= 65) {
                        newStr = (char) (c - key);
                        encryptedStr += newStr;
                    }
                } else {
                    encryptedStr += c;
                }
            }
        }
        return encryptedStr;
    }

    public static String routing (String comm, String str, int key, String alg) {
        if ("enc".equals(comm)) {
            return encrypt(str, key, alg);
        } else {
            return decrypt(str, key, alg);
        }
    }
}
