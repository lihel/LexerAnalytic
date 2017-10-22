package mypackage;

import java.io.*;

public class Analyse {
    private static String fileString;
    private static char ch;
    private static char[] myword = new char[10];
    private static int number, p, m = 0, sum = 0;
    private static String[] wordtable = {"begin", "if", "then", "while", "do", "end"};

    private void dofile() throws IOException {
        File file = new File("src/data");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileReader reader = new FileReader(file);
        BufferedReader bReader = new BufferedReader(reader);//文件缓存
        StringBuilder sb = new StringBuilder();
        String s = "";
        int flag = 0;
        while ((s = bReader.readLine()) != null) {
            if (s.indexOf("//") != -1) {
                System.out.println(s);
            } else if (s.indexOf("{") != -1 || flag == 1 && s.indexOf("}") == -1) {
                System.out.println(s);
                flag = 1;
            } else if (s.indexOf("}") != -1) {
                System.out.println(s);
                flag = 0;
            } else
                sb.append(s);
        }
        bReader.close();
        fileString = sb.toString();
        System.out.println(fileString);
    }

    private static void scaner() throws IOException {
        for (int i = 0; i < 10; i++)
            myword[i] = ' ';

        ch = fileString.charAt(p++);
        while (ch == ' ') {
            ch = fileString.charAt(p++);
        }

        if (ch >= 'a' && ch <= 'z') {
            m = 0;
            while ((ch >= 'a' && ch <= 'z')) {
                myword[m++] = ch;
                ch = fileString.charAt(p++);
            }
            myword[m++] = '\0';
            p--;
            number = 10;//单词
            String newStr = new String(myword);

            for (int i = 0; i < 6; i++) {
                if (newStr.equals(wordtable[i])) {
                    number = i + 1;
                    break;
                }
            }
            myword[m++] = '\0';
        } else if (ch >= '0' && ch <= '9') {
            sum = 0;
            while (ch >= '0' && ch <= '9') {
                sum = sum * 10 + ch - '0';
                ch = fileString.charAt(p++);
            }
            p--;
            number = 11;//数字
            myword[m++] = '\0';
        } else
            switch (ch) {
                case '<':
                    m = 0;
                    myword[m++] = ch;
                    ch = fileString.charAt(p++);
                    if (ch == '>') {
                        number = 21;//<>
                    } else if (ch == '=') {
                        number = 22;//<=
                        myword[m++] = ch;
                    } else {
                        number = 20;//<
                        p--;
                    }
                    break;
                case '>':
                    myword[m++] = ch;
                    ch = fileString.charAt(p++);
                    if (ch == '=') {
                        number = 24;//>=
                    } else {
                        number = 23;//>
                        p--;
                    }
                    break;
                case ':':
                    myword[m++] = ch;
                    ch = fileString.charAt(p++);
                    if (ch == '=') {
                        number = 18;//:=
                        myword[m++] = ch;
                    } else {
                        number = 17;//:
                        p--;
                    }
                    break;
                case '+':
                    number = 13;
                    myword[0] = ch;
                    break;
                case '-':
                    number = 14;
                    myword[0] = ch;
                    break;
                case '*':
                    number = 15;
                    myword[0] = ch;
                    break;
                case '/':
                    number = 16;
                    myword[0] = ch;
                    break;
                case '=':
                    number = 25;
                    myword[0] = ch;
                    break;
                case ';':
                    number = 26;
                    myword[0] = ch;
                    break;
                case '(':
                    number = 27;
                    myword[0] = ch;
                    break;
                case ')':
                    number = 28;
                    myword[0] = ch;
                    break;
                case '#':
                    number = 0;
                    myword[0] = ch;
                    break;
                default:
                    number = -1;
            }
    }

    public static void main(String[] args) throws IOException {
        Analyse word = new Analyse();
        word.dofile();
        do {
            scaner();
            switch (number) {
                case 11:
                    System.out.print("{" + number + ",");
                    System.out.print(sum);
                    System.out.println("}");
                    break;
                case -1:
                    System.out.println("error!");
                    break;
                default:
                    System.out.print("{" + number + ",");
                    String str = new String(myword);
                    System.out.print(str.trim());
                    System.out.println("}");
            }
        } while (number != 0);
    }
}