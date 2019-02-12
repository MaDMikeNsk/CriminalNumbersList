/* [M5_L3] Сделать детектор блатных номеров для ГИБДД (все регионы): сгенерировать список номеров и сделать метод,
который будет проверять наличие номера в списке. Программа должна работать через консоль: запрашивать номер,
проверять, выдавать результат проверки.
(Урок 6)В задаче с детектором блатных номеров сделать дополнительно бинарный поиск, поиск с помощью HashSet и с помощью TreeSet.
 Измерить и сравнить длительность 4­х видов поиска и написать результат в качестве решения домашнего задания.
 */

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
        long startTime;
        long finishTime;

        System.out.println("Подождите, идёт выполнение программы...");
        startTime = System.currentTimeMillis();
        //Блатные номера - буквы любые, цифры 001..009, 010..090, 111..999 для всех регионов. Всего 4_161_024
        for (String code : regionCodes) {
            for (String firstLetter : lettersInNumber) {
                for (String secondLetter : lettersInNumber) {
                    for (String thirdLetter : lettersInNumber) {
                        for (int i = 0; i < 1000; i++) {
                            if (i <= 9) { //001..009
                                criminalArray.add(firstLetter + "00" + i + secondLetter + thirdLetter + code);
                                } else if (Math.floorMod(i, 10) == 0 && i < 100) { //010..090
                                    criminalArray.add(firstLetter + "0" + i / 10 + "0" + secondLetter + thirdLetter + code);
                                } else if (Math.floorMod(i, 111) == 0) { //111..999
                                    criminalArray.add(firstLetter + i + secondLetter + thirdLetter + code);
                            }
                        }
                    }
                }
            }
        }
        HashSet<String> criminalHashSet = new HashSet<>(criminalArray);
        TreeSet<String> criminalTreeSet = new TreeSet<>(criminalArray);
        finishTime = System.currentTimeMillis();
        System.out.println("Базы блатных номеров сформированы за " + (double)(finishTime - startTime)/1000 + " с");
        System.out.println("Количество номеров в базе: " + criminalArray.size());

        BufferedReader stream  = new BufferedReader(new InputStreamReader(System.in));
        String requiredNumber;
        do {
            System.out.println("Введите номер автомобиля для поиска в базе:");
            requiredNumber = stream.readLine().trim();
        } while (!requiredNumber.matches("^[а-я][0-9]{3}[а-я]{2}[0-9]{2}$"));

        // Поиск в несортированном ArrayList
        String tempNumber = "";
        boolean numberInList = false;
        int i=0;
        startTime = System.currentTimeMillis();
        while (!requiredNumber.equals(tempNumber) && i<criminalArray.size()){
            tempNumber = criminalArray.get(i);
            if (requiredNumber.equals(tempNumber)){
                numberInList = true;
            }
            i++;
        }
        finishTime = System.currentTimeMillis();
        System.out.println("Поиск в несортированном ArrayList занял: " + (finishTime - startTime) + " мс");

        //Поиск в отсортированном массиве
        Collections.sort(criminalArray);
        startTime = System.nanoTime();
        int binarySearchResult = Collections.binarySearch(criminalArray, requiredNumber);
        finishTime = System.nanoTime();
        System.out.println("Поиск в сортированном ArrayList занял: " + (double)(finishTime - startTime)/1_000_000 + " мс");

        //Поиск в HashSet
        startTime = System.nanoTime();
        boolean hashSetSearchResult = criminalHashSet.contains(requiredNumber);
        finishTime = System.nanoTime();
        System.out.println("Поиск в HashSet занял: " + (double)(finishTime - startTime)/1_000_000 + " мс");

        //Поиск в TreeMap
        startTime = System.nanoTime();
        boolean treeSetSearchResult = criminalTreeSet.contains(requiredNumber);
        finishTime = System.nanoTime();
        System.out.println("Поиск в TreeSet занял: " + (double)(finishTime - startTime)/1_000_000 + " мс");

        if (numberInList && binarySearchResult>=0 && hashSetSearchResult && treeSetSearchResult) {
            System.out.println("Номер " + requiredNumber + " является блатным");
        } else {
            System.out.println("Искомого номера нет ни в одном списке блатных номеров");
        }
    }
}