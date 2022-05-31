package com.company;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Main {

    public static int initialCodeLength = 10;

    public static String pushInteger(String s, int n, int l)
    {
        for (int i = 1; i <= l; i++)
        {
            if (n >= (1 << l-i))
            {
                n -= (1 << l-i);

                s += "1";
            }
            else s += "0";
        }

        return s;
    }

    public static int pullInteger (String s, int start, int l)
    {
        int n = 0;

        for (int i = 1; i <= l; i++)
        {
            n += (1 << l-i)*Integer.parseInt(""+s.charAt(start+i-1));
        }

        return n;
    }

    public static String compress(String input)
    {
        HashMap<String,Integer> dictionary = new LinkedHashMap<>();
        String output = "";

        for (int i = 0; i < 256; i++)
        {
            dictionary.put(""+(char)i, i);
        }

        int codeLength = initialCodeLength;

        char curChar;

        String phrase = "" + input.charAt(0);

        int code = 256;

        int a = 0;

        for(int i = 1; i < input.length(); i++)
        {
            curChar = input.charAt(i);

            if (dictionary.get(phrase+curChar) != null) phrase += curChar;
            else
            {
                if (code > (1 << codeLength)) codeLength++;
                output = pushInteger(output, dictionary.get(phrase), codeLength);

                dictionary.put(phrase+curChar, code);

                code++;

                phrase = ""+curChar;
            }
        }

        if (code > (1 << codeLength))
        {
            a += 1;

            codeLength++;
        }

        output = pushInteger(output, dictionary.get(phrase), codeLength);

        System.out.println(a);

        return output;
    }

    public static String extract (String input)
    {
        HashMap<Integer,String> dictionary = new LinkedHashMap<>();

        for (int i = 0; i < 256; i++)
        {
            dictionary.put(i, ""+(char)i);
        }

        int codeLength = initialCodeLength;

        int start = 0;

        String currentChar = dictionary.get(pullInteger(input, start, codeLength));
        start+=codeLength;

        String phrase="";

        String oldPhrase = currentChar;

        String output = currentChar;

        int code = 256;

        int a = 0;

        while (start+codeLength-1 < input.length())
        {
            int currentCode = pullInteger(input, start, codeLength);
            start += codeLength;

            if (dictionary.get(currentCode) != null) phrase = dictionary.get(currentCode);
            else phrase = oldPhrase + currentChar;

            output += phrase;

            //curChar = phrase.substring(0,1);

            currentChar = phrase.substring(0,1);

            dictionary.put(code,oldPhrase+currentChar);

            code++;

            if (code > (1 << codeLength))
            {
                a += 1;

                codeLength++;
            }

            oldPhrase = phrase;
        }

        System.out.println(a);

        return output;
    }

    public static String bitString (String s)
    {
        int k = 10;

        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < s.length(); i++)
        {
            int n = s.charAt(i);

            boolean flag = false;

            for (int j = 1; j <= k ; j++)
            {
                if (n >= (1 << k-j))
                {
                    flag = true;
                    sb.append('1');

                    n -= (1 << k-j);
                }
                else if (flag) sb.append('0');
            }
        }

        return sb.toString();
    }

    public static void main(String[] args)
    {
        String s = "Japan has been inhabited since the Upper Paleolithic" +
                " period (30,000 BC), though the first written mention of" +
                " the archipelago appears in a Chinese chronicle (the Book of Han)" +
                " finished in the 2nd century AD. Between the 4th and 9th centuries," +
                " the kingdoms of Japan became unified under an emperor and the imperial" +
                " court based in Heian-kyo:. Beginning in the 12th century, political power" +
                " was held by a series of military dictators (sho:gun) and feudal lords (daimyo:)" +
                " and enforced by a class of warrior nobility (samurai). After a century-long" +
                " period of civil war, the country was reunified in 1603 under the Tokugawa" +
                " shogunate, which enacted an isolationist foreign policy. In 1854, a United" +
                " States fleet forced Japan to open trade to the West, which led to the end" +
                " of the shogunate and the restoration of imperial power in 1868. In the" +
                " Meiji period, the Empire of Japan adopted a Western-modeled constitution" +
                " and pursued a program of industrialization and modernization. Amidst a rise" +
                " in militarism and overseas colonization, Japan invaded China in 1937 and" +
                " entered World War II as an Axis power in 1941. After suffering defeat in" +
                " the Pacific War and two atomic bombings, Japan surrendered in 1945 and came" +
                " under a seven-year Allied occupation, during which it adopted a new constitution." +
                " Under the 1947 constitution, Japan has maintained a unitary parliamentary" +
                " constitutional monarchy with a bicameral legislature, the National Diet.";

        System.out.println("Original:");

        System.out.println(s);

        System.out.println("---------------------------------------------");

        System.out.println("Bit string representation:");

        System.out.println(bitString(s));

        System.out.println("---------------------------------------------");

        System.out.println("Compressed:");

        System.out.println(compress(s));

        System.out.println("---------------------------------------------");

        System.out.println("Decompressed:");

        System.out.println(extract(compress(s)));
    }
}