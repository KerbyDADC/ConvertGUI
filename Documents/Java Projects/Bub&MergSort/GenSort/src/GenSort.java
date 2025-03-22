import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;

public class GenSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // asks the user if they want to generate an array to read an array from an existing file
        System.out.println("Welcome to my Bubble & Merge Sort program! Please choose an option:");
        System.out.println("1. Generate an array of random numbers, sort it, and store it in a file");
        System.out.println("2. Read an existing file with one integer per line, read it, sort it, and display the array");
        System.out.print("Please Enter 1 or 2: ");
        int fileOption = scanner.nextInt();
        System.out.println("3. Sort the array using Bubble Sort");
        System.out.println("4. Sort the array using Merge Sort (Recommended)");
        System.out.print("Please Enter 3 or 4: ");
        int sortOption = scanner.nextInt();

        if (fileOption == 1) {
            // random array section
            System.out.print("Please enter the size of the array: ");
            int arraySize = scanner.nextInt();
            System.out.println("Please enter the filename: ");
            String filename = scanner.next();

            Integer[] randomArray = createRandomArray(arraySize);

            if (sortOption == 3) {
                bubbleSort(randomArray);
            
            } else if (sortOption == 4) {
                mergeSort(randomArray);
            }

            System.out.println("Sorted array: " + Arrays.toString(randomArray));

            writeArrayToFile(randomArray, filename);
            System.out.println("Your array has been saved to " + filename);

        } else if (fileOption == 2) {
            System.out.println("Please input the filename that will be read: ");
            String inputFilename = scanner.next();

            System.out.println("Please input the filename that the sorted array will be stored to: ");
            String outputFilename = scanner.next();

            Integer[] arrayFromFile = readFileToArray(inputFilename);
            System.out.println("Here is the array from the file: " + Arrays.toString(arrayFromFile));

            if (sortOption == 3) {
                bubbleSort(arrayFromFile);
            
            } else if (sortOption == 4) {
                mergeSort(arrayFromFile);
            }
            
            System.out.println("Sorted array: " + Arrays.toString(arrayFromFile));
            
            writeArrayToFile(arrayFromFile, outputFilename);
            System.out.println("The sorted array has been saved to " + outputFilename);

            // java.util.Arrays lets me print out the arrays for the user to see before storing it
        }
        scanner.close();
    }

    // takes a user inputted array size and creates an array using randomly generated numbers
    public static Integer[] createRandomArray(int arrayLength) {
        Random rand = new Random();
        Integer[] array = new Integer[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            array[i] = rand.nextInt(101);
        }
        return array;
    }

    // takes an array and writes it to a file one line per number
    public static void writeArrayToFile(Integer[] array, String filename) {
        // will start to try using code to handle errors
        // remember BufferedWriter writes text efficiently
        // BufferedWriter needs a Writer object to function, it assists FileWriter
        // it doesn't do it on its own
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < array.length; i++) {
                writer.write(Integer.toString(array[i])); //converts the ints in the array to read
                writer.newLine();
            }
            System.out.println("Array written to " + filename);
        } catch (IOException e) {
            System.out.println("An error has occured while writing to file " + filename);
            System.out.println(e.getMessage());
            // e holds the exception so we can get it later in getMessage
            // handles issues like file not found, no permission, etc.
        }
    }

    // takes the numbers from a file and turns it into an array
    // unlike the last function, we don't have an array so we'll have to make one
    public static Integer[] readFileToArray(String filename) {
        // this code creates an array without needing to specify it's size when declaring
        // we'll store the number from the file into this array and move it to a different array later
        ArrayList<Integer> fileArray = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line; // used to store the lines from the file

            // was going to use a for loop here, it would need to check each line until there isn't a line
            // so instead we use a while loop since that's its purpose
            // this while statement runs until there are no lines left in the file (null)
            while ((line = reader.readLine()) != null) {
                int num = Integer.parseInt(line); // turns the number into an integer
                fileArray.add(num);
            }

            // for each number in the fileArray, add that to the int[] array
            Integer[] array = new Integer[fileArray.size()];
            for (int i = 0; i < fileArray.size(); i++) {
                array[i] = fileArray.get(i);
            }
            return array;

        } catch (IOException e) {
            System.out.println("There was an error while reading the file: " + filename);
            System.out.println(e.getMessage());
            return new Integer[0]; // if an error occurs, return an empty array
        }
    }

    public static <T extends Comparable<T>> void bubbleSort(T[] array) {
        int n = array.length;

        // check pairs in an array and if the left number is bigger than the right, swap them
        // repeat this until all numbers are ordered
        // n - 1 because we don't need the last number in the array should be the biggest by then
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (array[j].compareTo(array[j+1]) > 0) {
                    // we swap by temporarily storing the first number making the new value the second number
                    T temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }  
        }

    }


    public static <T extends Comparable<T>> void mergeSort(T[] array) {
        int n = array.length;

        if (n < 2) {
            return;
        }

        // to account for uneven array numbers, the left array will always be given the middle number

        int midNum = n / 2;
        T[] leftHalf = java.util.Arrays.copyOfRange(array, 0, midNum);
        T[] rightHalf = java.util.Arrays.copyOfRange(array, midNum, n);

        for (int i = 0; i < midNum; i++) {
            leftHalf[i] = array[i];
        }

        for (int i = midNum; i < n; i++) {
            rightHalf[i - midNum] = array[i];
        }

        mergeSort(leftHalf);
        mergeSort(rightHalf);

        mergeFull(array, leftHalf, rightHalf);
    }

    private static <T extends Comparable<T>> void mergeFull(T[] array, T[] leftHalf, T[] rightHalf) {
        int leftSize = leftHalf.length;
        int rightSize = rightHalf.length;

        int i = 0, j = 0, k = 0;

        while (i < leftSize && j < rightSize) {
            if (leftHalf[i].compareTo(rightHalf[j]) <= 0) {
                array[k] = leftHalf[i];
                i++;
            } else {
                array[k] = rightHalf[j];
                j++;
            }
            k++;
        }

        // if we run out of elements on either array, then fill the rest of the array
        // with the remaining numbers
        while (i < leftSize) {
            array[k] = leftHalf[i];
            i++;
            k++;
        }

        while (j < rightSize) {
            array[k] = rightHalf[j];
            j++;
            k++;
        }
    }
}
