import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;

public class Main {

    public static void main(String[] args) throws IOException {
        String[] lettersInNumber = {"а", "в", "е", "к", "м", "н", "о", "р", "с", "т", "у", "х"};
        String[] regionCodes = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
                "15", "16", "17", "18", "19", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
                "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66",
                "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "82", "83", "86", "87",
                "89", "92", "94", "95"};
        ArrayList<String> criminalArray = new ArrayList<>();
        HashSet<String> criminalHashSet = new HashSet<>();
        TreeSet<String> criminalTreeSet = new TreeSet<>();
        String number;
        long startTime;
        long finishTime;

        System.out.println("Подождите, идёт выполнение программы...");
        startTime = System.nanoTime();
        //Блатные номера - буквы любые, цифры 001..009, 010..090, 111..999 для всех регионов. Всего 4_161_024
        for (String code : regionCodes) {
            for (String firstLetter : lettersInNumber) {
                for (String secondLetter : lettersInNumber) {
                    for (String thirdLetter : lettersInNumber) {
                        for (int i = 0; i < 1000; i++) {
                            if (i <= 9) { //001..009
                                number = firstLetter + "00" + i + secondLetter + thirdLetter + code;
                                criminalArray.add(number);
                                criminalHashSet.add(number);
                                criminalTreeSet.add(number);
                                } else if (Math.floorMod(i, 10) == 0 && i < 100) { //010..090
                                    number = firstLetter + "0" + i / 10 + "0" + secondLetter + thirdLetter + code;
                                    criminalArray.add(number);
                                    criminalHashSet.add(number);
                                    criminalTreeSet.add(number);
                                } else if (Math.floorMod(i, 111) == 0) { //111..999
                                    number = firstLetter + i + secondLetter + thirdLetter + code;
                                    criminalArray.add(number);
                                    criminalHashSet.add(number);
                                    criminalTreeSet.add(number);
                            }
                        }
                    }
                }
            }
        }
        finishTime = System.nanoTime();
        System.out.println("Базы блатных номеров сформированы за " + (double)(finishTime - startTime)/1000_000_000 + " с");
        System.out.println("Количество номеров в базе: " + criminalArray.size());
        BufferedReader stream  = new BufferedReader(new InputStreamReader(System.in));
        do {
            System.out.println("Введите номер автомобиля для поиска в базе:");
            number = stream.readLine().trim();
        } while (!number.matches("^[а-я][0-9]{3}[а-я]{2}[0-9]{2}$"));

        // Поиск в неотсортированном ArrayList
        String checkingNumber = "";
        boolean numberInList = false;
        int i=0;
        startTime = System.nanoTime();
        while (!number.equals(checkingNumber) && i<criminalArray.size()){
            checkingNumber = criminalArray.get(i);
            if (number.equals(checkingNumber)){
                numberInList = true;
            }
            i++;
        }
        finishTime = System.nanoTime();
        System.out.println("Поиск в несортированном ArrayList занял: " + (double)(finishTime - startTime)/1000_000_000 + " с");

        //Поиск в отсортированном массиве
        Collections.sort(criminalArray);
        startTime = System.nanoTime();
        int binarySearchResult = Collections.binarySearch(criminalArray, number);
        finishTime = System.nanoTime();
        System.out.println("Поиск в сортированном ArrayList занял: " + (double)(finishTime - startTime)/1000_000_000 + " с");

        //Поиск в HashSet
        startTime = System.nanoTime();
        boolean hashSetSearchResult = criminalHashSet.contains(number);
        finishTime = System.nanoTime();
        System.out.println("Поиск в HashSet занял: " + (double)(finishTime - startTime)/1000_000_000 + " с");

        //Поиск в TreeMap
        startTime = System.nanoTime();
        boolean treeSetSearchResult = criminalTreeSet.contains(number);
        finishTime = System.nanoTime();
        System.out.println("Поиск в TreeSet занял: " + (double)(finishTime - startTime)/1000_000_000 + " с");

        if (numberInList && binarySearchResult>0 && hashSetSearchResult && treeSetSearchResult) {
            System.out.println("Номер " + number + " является блатным");
        } else {
            System.out.println("Искомого номера нет ни в одном списке блатных номеров");
        }
    }
}
