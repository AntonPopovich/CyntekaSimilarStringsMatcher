package org.cynteka;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Main main = new Main();

        String src = "/Users/user/Desktop/input.txt";
        String dest = "/Users/user/Desktop/output.txt";

        List<String> array1 = new ArrayList<>();
        List<String> array2 = new ArrayList<>();
        List<String> output = new ArrayList<>();

        main.readInput(array1, array2, src);
        main.matchSimilar(array1, array2, output);
        main.writeOutput(output, dest);
    }

    private void matchSimilar(List<String> array1, List<String> array2, List<String> output) {
        List<String> arr1;
        List<String> arr2;

        if (array2.size() > array1.size()) {
            arr1 = array2;
            arr2 = array1;
        } else {
            arr1 = array1;
            arr2 = array2;
        }

        Iterator<String> array1Iterator = arr1.iterator();

        while (array1Iterator.hasNext()) {
            String element1 = array1Iterator.next();
            String el1 = element1.toLowerCase();
            Iterator<String> array2Iterator = arr2.iterator();

            while (array2Iterator.hasNext()) {
                String element2 = array2Iterator.next();
                String el2 = element2.toLowerCase();

                if (el1.equals(el2) ||
                        el1.contains(el2) || el2.contains(el1) ||
                        areLongStringsHaveMatchingWords(el1, el2)) {
                    output.add(element1 + ":" + element2);
                    array1Iterator.remove();
                    array2Iterator.remove();
                }
            }
        }

        if (arr1.size() != 0) {
            for (int i = 0; i < arr1.size(); i++) {
                if (arr2.size() > i) {
                    output.add(arr1.get(i) + ":" + arr2.get(i));
                } else
                    output.add(arr1.get(i) + ":?");
            }
        }
    }

    private static List<String> filterPrepositions(String el1) {
        List<String> prepositions = Arrays.asList("без", "в", "до", "для", "за", "из", "к",
                "на", "над", "о", "об", "от", "по", "под", "пред", "при", "про", "с", "у", "через");
        String[] splitted = el1.split("\\s+");
        List<String> list1 = new ArrayList<>();

        for (String s : splitted) {
            if (prepositions.stream().noneMatch(prep -> prep.contains(s))) {
                list1.add(s);
            }
        }
        return list1;
    }

    private static boolean areLongStringsHaveMatchingWords(String el1, String el2) {
        boolean result = false;
        List<String> splitted1 = filterPrepositions(el1);
        List<String> splitted2 = filterPrepositions(el2);

        for (String s : splitted1) {
            for (String value : splitted2) {
                if (s.contains(value))
                    return true;
            }
        }
        for (String s : splitted2) {
            for (String value : splitted1) {
                if (s.contains(value))
                    return true;
            }
        }
        return result;
    }

    private void readInput(List<String> array1, List<String> array2, String src) {
        int numberOfSetsInInputFile = 2;
        try (FileReader in = new FileReader(src);
             BufferedReader reader = new BufferedReader(in)) {
            List<String> buffered = array1;
            for (int i = 0; i < numberOfSetsInInputFile; i++) {
                int size = Integer.parseInt(reader.readLine());

                while (size > 0) {
                    buffered.add(reader.readLine());
                    size--;
                }
                buffered = array2;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeOutput(List<String> output, String dest) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dest))) {
            for (String s : output) {
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
