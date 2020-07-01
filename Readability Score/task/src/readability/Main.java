package readability;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;



public class Main {
    public static void main(String[] args) {

        String inputFile = args[0];
        File file = new File(inputFile);
        String text ="";
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                text += scanner.nextLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + inputFile);
        }
        text = text.replace("\t","");
        text = text.replace("\n","");
        System.out.println("The text is:");
        System.out.println(text);
        int charCounter;
        int wordCounter = 0;
        String[] wordFile = text.split(" ");
        charCounter = text.replaceAll(" |\n|\t","").split("").length;
        wordCounter += wordFile.length;
        String[] sentencesFile = text.split("[.!\\?]");
        int sentenceCounter = sentencesFile.length;

        int[] wordSyllables = new int[wordFile.length];
        int syllablesCounter = 0;
        int polysyllablesCounter = 0;
        for (int i = 0; i < wordFile.length; i++) {
                int vowelsCounter = 0;
                int[] wordVowels = new int[wordFile[i].length()];
                for (int j = 0; j < wordFile[i].length(); j++) {
                    if (Character.toString(wordFile[i].charAt(j)).matches("[aeiouyAEIOUY]")) {
                        wordVowels[j] = 1;
                    } else {
                        wordVowels[j] = 0;
                    }
                    System.out.println("Word: " + wordFile[i] + " Counter: " + vowelsCounter + "Char" + wordFile[i].charAt(j));
                }
                if (wordVowels[wordFile[i].length() - 1] == 1) {
                    wordVowels[wordFile[i].length() - 1] = 0;
                }
                for (int k = wordFile[i].length() - 1; k > 0; k--) {
                    if (wordVowels[k] == 1 && wordVowels[k - 1] == 1) {
                        wordVowels[k] = 0;
                    }
                }

                for (int cnt : wordVowels) {
                    vowelsCounter += cnt;
                }

                System.out.println("     Word: " + wordFile[i] + " Counter: " + vowelsCounter);
                wordSyllables[i] = (vowelsCounter == 0) ? 1 : vowelsCounter;
        }

        for (int cnt : wordSyllables) {
            syllablesCounter += cnt;
            if (cnt > 2) {
                polysyllablesCounter++;
            }
        }

        System.out.println();
        System.out.println("Words: " + wordCounter);
        System.out.println("Sentences: " + sentenceCounter);
        System.out.println("Characters: " + charCounter);
        System.out.println("Syllables: " + syllablesCounter);
        System.out.println("Polysyllables: " + polysyllablesCounter);
        System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
        Scanner scanner = new Scanner(System.in);
        String scorringType = scanner.nextLine();
        scanner.close();

        double scoreARI = 4.71 * charCounter / wordCounter + 0.5 * wordCounter / sentenceCounter - 21.43;
        double scoreKF = 0.39 * wordCounter / sentenceCounter + 11.8 * syllablesCounter / wordCounter - 15.59;
        double scoreSMOG = 1.043 * Math.sqrt(polysyllablesCounter * 30 / sentenceCounter) + 3.1291;
        double scoreCL = 0.0588 * (charCounter * 100 / wordCounter) -
                0.296 * (sentenceCounter * 100 / wordCounter) - 15.8;

        String ageARI = getAgeScore(scoreARI);
        String ageKF = getAgeScore(scoreKF);
        String ageSMOG = getAgeScore(scoreSMOG);
        String ageCL = getAgeScore(scoreCL);

        if ("ARI".equals(scorringType) || "all".equals(scorringType)) {
            System.out.println("Automated Readability Index: " + (double) Math.round(scoreARI * 100.0) / 100.0 +
                    " (about "  + ageARI + " year olds).");
        }

        if ("FK".equals(scorringType) || "all".equals(scorringType)) {
            System.out.println("Flesch–Kincaid readability tests: " + (double) Math.round(scoreKF * 100.0) / 100.0 +
                    " (about "  + ageKF + " year olds).");
        }

        if ("SMOG".equals(scorringType) || "all".equals(scorringType)) {
            System.out.println("Simple Measure of Gobbledygook: " + (double) Math.round(scoreSMOG * 100.0) / 100.0 +
                    " (about "  + ageSMOG + " year olds).");
        }

        if ("CL".equals(scorringType) || "all".equals(scorringType)) {
            System.out.println("Coleman–Liau index: " + (double) Math.round(scoreCL * 100.0) / 100.0 +
                    " (about "  + ageCL + " year olds).");
        }

        /*String line = scanner.nextLine();
        scanner.close();
        String[] sentence = line.split("[\\.!?]");
        int n = sentence.length;
        String regex = "[\\s\\h]+";
        for (int i = 1; i < n; i++) {
            if (sentence[i].matches(regex)) {
                sentence[i] = sentence[i].substring(1,sentence[i].length());
            }
        }

        int[] num = new int[sentence.length];
        for (int i = 0; i < n; i++) {
            String[] currentSent = sentence[i].split("[\\s\\h]+");
            num[i] = currentSent.length;
        }
        int numOfwords = 0;
        for(int numOfw : num) {
            numOfwords += numOfw;
        }

        if (numOfwords / n > 10) {
            System.out.println("HARD");
        } else {
            System.out.println("EASY");
        }*/
    }

    private static String getAgeScore(double scoreIn) {
        int scoreCalc = (int) Math.ceil(scoreIn);
        String age;
        switch (scoreCalc) {
            case 1:
                age = "5-6";
                break;
            case 2:
                age = "6-7";
                break;
            case 3:
                age = "7-9";
                break;
            case 4:
                age = "9-10";
                break;
            case 5:
                age = "10-11";
                break;
            case 6:
                age = "11-12";
                break;
            case 7:
                age = "12-13";
                break;
            case 8:
                age = "13-14";
                break;
            case 9:
                age = "14-15";
                break;
            case 10:
                age = "15-16";
                break;
            case 11:
                age = "16-17";
                break;
            case 12:
                age = "17-18";
                break;
            case 13:
                age = "18-24";
                break;
            case 14:
                age = "24+";
                break;
            default:
                age = "Wrong scoring code";
        }
        return age;
    }
}
