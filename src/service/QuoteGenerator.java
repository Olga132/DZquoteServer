package service;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class QuoteGenerator{

    private static ArrayList<String> listQuote = new ArrayList<>();

    static {
        BufferedReader bufReader;

        try {
            bufReader = new BufferedReader(
                    new FileReader("C:/Users/Jobs/IdeaProjects/DZquoteServer/src/resource/quoteFile.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String line = null;
        try {
            line = bufReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while (line != null) { listQuote.add(line);
            try {
                line = bufReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            bufReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRandomQuote(){
        Random random  = new Random();
        return listQuote.get(random.nextInt(listQuote.size() - 1));
    }
}
