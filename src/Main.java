import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String[] lettersInNumber = {"а", "в", "е", "к", "м", "н", "о", "р", "с", "т", "у", "х"};
        String[] regionCodes = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
                "15", "16", "17", "18", "19", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
                "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49",
                "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66",
                "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "82", "83", "86", "87",
                "89", "92", "94", "95"};
        ArrayList<String> criminalnumberPlates = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        //Блатные номера - буквы любые, цифры 001..009, 010..090, 111..999 для всех регионов. Всего 4_161_024
        for (String code : regionCodes) {
            for (String firstLetter : lettersInNumber) {
                for (String secondLetter : lettersInNumber) {
                    for (String thirdLetter : lettersInNumber) {
                        for (int i = 0; i < 1000; i++) {
                            if (i <= 9) { //001..009
                                criminalnumberPlates.add(firstLetter + "00" + i + secondLetter + thirdLetter + code);
                                } else if (Math.floorMod(i, 10) == 0 && i < 100) { //010..090
                                    criminalnumberPlates.add(firstLetter + "0" + i / 10 + "0" + secondLetter + thirdLetter + code);
                                } else if (Math.floorMod(i, 111) == 0) { //111..999
                                    criminalnumberPlates.add(firstLetter + i + secondLetter + thirdLetter + code);
                            }
                        }
                    }
                }
            }
        }
        long finishTime = System.currentTimeMillis();
        System.out.println(criminalnumberPlates.size());
        System.out.println("Время создания списка блатных номеров: " + (double) (finishTime - startTime) / 1000 + " с");
    }
}

