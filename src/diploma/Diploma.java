package diploma;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Александр
 */
public class Diploma {

    public static int kroneckerDelta(int a, int b) {
        if (a == b) {
            return 1;
        }
        return 0;
    }

    public static int bitAdd(int a, int b) {
        return (a + b) % 255;
    }

    /**
     * Generating S box using the not repeating method.
     *
     * @return S box
     */
    
    
    public static int [] generatorModification(){
            ArrayList<Integer> array = new ArrayList<>(255);
            
            for(int i=0;i<256;i++){
                array.add(i);
            }
            int[] res = new int[array.size()];
            Collections.shuffle(array);
            for(int i = 0; i < res.length; i++){
                res[i] = array.get(i);
            }
            
            return res;
    }
    
    
    public static int[] generator() {
ArrayList<Integer> array = new ArrayList<>(256);
        Random r = new Random();
        for (int i = 0; i < 256; i++) {
            int temp = r.nextInt(255);
            while (array.contains(temp)) {
                temp = r.nextInt(256);
            }
            array.add(temp);
        }
        int[] res = new int[256];
        for (int i = 0; i < 256; i++) {
            res[i] = array.get(i);
        }
        return res;
    }

    public static boolean checkSbox(int[][] array, int[] temp) {
        for (int i = 0; i < array.length; i++) {
            if (Arrays.equals(array[i], temp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param n se'ts the number of S boxes
     * @return array of S boxes
     */
    public static int[][] generateSboxes(int n) {
        int[][] res = new int[n][256];
        for (int i = 0; i < n; i++) {
            int[] temp = generatorModification();
            while (checkSbox(res, temp) == true) {
                temp = generatorModification();
            }
            res[i] = temp;
        }
        return res;
    }

    /**
     * Generate all possible keys here
     *
     * @return array of keys
     */
    public static int[] generateKeys() {
        int[] keys = new int[256];
        for (int i = 0; i < 256; i++) {
            keys[i] = i;
        }
        return keys;
    }

    public static int[][] possibleAB() {
        int[][] arrayAB = new int[65025][2];
        int t = 0;
        for (int i = 1; i < 256; i++) {
            for (int j = 1; j < 256; j++) {
                arrayAB[t][0] = i;
                arrayAB[t][1] = j;
                t++;
            }
        }
        return arrayAB;
    }

    /**
     * + + operations
     */
    public static double[] differentialFirst(int[][] possibleAB, int[] Keys, int[] Sboxes) {
        int lengthAB = possibleAB.length;
        int lengthKeys = Keys.length;
        double[] result = new double[lengthAB];

        for (int j = 0; j < lengthAB; j++) {
            double temp = 0;
            for (int k = 0; k < lengthKeys; k++) {
                int t = bitAdd(Keys[k], possibleAB[j][0]);
                int sOne = Sboxes[t];
                int sTwo = Sboxes[Keys[k]];
                int r = bitAdd(sOne, sTwo);
                temp += kroneckerDelta(r, possibleAB[j][1]);
            }
            temp = temp * ((double) 1 / (double) (2 << 7));
            result[j] = temp;
        }
        return result;
    }

    /**
     * Xor and Xor operations
     *
     * @param possibleAB
     * @param Keys
     * @param Sboxes
     * @return
     */
    public static double[] differentialSecond(int[][] possibleAB, int[] Keys, int[] Sboxes) {
        int lengthAB = possibleAB.length;
        int lengthKeys = Keys.length;
        double[] result = new double[lengthAB];
        for (int j = 0; j < lengthAB; j++) {
            double temp = 0;
            for (int k = 0; k < lengthKeys; k++) {
                int t = Keys[k] ^ possibleAB[j][0];
                int sOne = Sboxes[t];
                int sTwo = Sboxes[Keys[k]];
                int r = sOne ^ sTwo;
                temp += kroneckerDelta(r, possibleAB[j][1]);
            }
            temp = temp * ((double) 1 / (double) (2 << 7));
            result[j] = temp;
        }
        return result;
    }

    /**
     * + and XOR
     *
     * @param possibleAB
     * @param Keys
     * @param Sboxes
     * @return
     */
    public static double[] differentialThird(int[][] possibleAB, int[] Keys, int[] Sboxes) {
        int lengthAB = possibleAB.length;
        int lengthKeys = Keys.length;
        double[] result = new double[lengthAB];
        for (int j = 0; j < lengthAB; j++) {
            double temp = 0;
            for (int k = 0; k < lengthKeys; k++) {
                int t = bitAdd(Keys[k], possibleAB[j][0]);
                int sOne = Sboxes[t];
                int sTwo = Sboxes[Keys[k]];
                int r = sOne ^ sTwo;
                temp += kroneckerDelta(r, possibleAB[j][1]);
            }
            temp = temp * ((double) 1 / (double) (2 << 7));
            result[j] = temp;
        }
        return result;
    }

    public static double[] differentialFourth(int[][] possibleAB, int[] Keys, int[] Sboxes) {
        int lengthAB = possibleAB.length;
        int lengthKeys = Keys.length;
        double[] result = new double[lengthAB];
        for (int j = 0; j < lengthAB; j++) {
            double temp = 0;
            for (int k = 0; k < lengthKeys; k++) {
                int t = Keys[k] ^ possibleAB[j][0];
                int sOne = Sboxes[t];
                int sTwo = Sboxes[Keys[k]];
                int r = bitAdd(sOne, sTwo);
                temp += kroneckerDelta(r, possibleAB[j][1]);
            }
            temp = temp * ((double) 1 / (double) (2 << 7));
            result[j] = temp;
        }
        return result;
    }

    public static double maxDifferential(double[] array) {
        Arrays.sort(array);
        return array[array.length - 1];
    }

    /**
     * +,xor > xor,+
     *
     * @return
     */
    
    
    public static ArrayList < ArrayList <Integer >> taskFother (int[][] Sbox, double[] mFirst, double[] mSecond){
        ArrayList < ArrayList <Integer >> temp =  new ArrayList<>();
        for(int i = 0 ; i<Sbox.length;i++){
            if(mFirst[i] > mSecond[i]){
                
                ArrayList<Integer> t = new ArrayList<>(255);
                for(int j = 0;j < Sbox[i].length;j++){
                    t.get(Sbox[i][j]);
                }
                temp.add(t);
              
                System.out.println(t);
                break;
                
            }
        }
        
        
        return temp;
    }
    
    
    public static int[][] taskF(int[][] Sbox, double[] mFirst, double[] mSecond) {
        int[][] result = new int[10][255]; 
        int count = 0;
           for (int i = 0; i < result.length; i++){
            if(mFirst[i] > mSecond[i]){
                result[i]=Sbox[i];
                count++;
                if (count == 10){
                    break;
                }
            }   
           }
        return result;
    }
    
    public static void write(String filename, double[] x) throws IOException {
        BufferedWriter outputWriter = null;
        outputWriter = new BufferedWriter(new FileWriter(filename));
        for (int i = 0; i < x.length; i++) {
    // Maybe:
         //   outputWriter.write(x[i]+"");
            // Or:
      
           outputWriter.write(Double.toString(x[i]));
            outputWriter.newLine();
        }
        outputWriter.flush();
        outputWriter.close();
    }
    
    public static void print(int[][] A){
     for(int i = 0; i < A.length;i++){
            for(int j = 0; j < A[i].length; j++){
                System.out.print(A[i][j]+",");
            }
            System.out.println("");
        }
    }
    

    public static void main(String[] args) throws IOException {
 
        System.out.println("Enter number of S blocks to generate:");
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();

        int[][] AB = possibleAB();
        int[] Keys = generateKeys();
        int[][] Sbox = generateSboxes(n);

        double[] res1 = new double[n];
        double[] res2 = new double[n];
        double[] res3 = new double[n];
        double[] res4 = new double[n];
        
        ArrayList<ArrayList<Integer>> r = new ArrayList<>();
      

         for (int i = 0; i < n; i++) {
           // res1[i] = maxDifferential(differentialFirst(AB, Keys, Sbox[i])); // +,+
            res2[i] = maxDifferential(differentialSecond(AB, Keys, Sbox[i])); // MOD MOD
            res3[i] = maxDifferential(differentialThird(AB, Keys, Sbox[i]));// +, MOD
          //  res4[i] = maxDifferential(differentialFourth(AB, Keys, Sbox[i])); // MOD , +
            
        }
         
         r = taskFother(Sbox, res3, res2);
         
         
         
            /*
        write("D:\\computation\\a.txt", res1);
        write("D:\\computation\\b.txt", res2);
        write("D:\\computation\\c.txt", res3);
        write("D:\\computation\\d.txt", res4);
        
        int[][] A = taskF(Sbox, res3, res2);
        int[][] B = taskF(Sbox, res1, res4);
        int[][] C = taskF(Sbox, res4, res2);
        int[][] D = taskF(Sbox, res1, res3);
        
        print(A);
        System.out.println("-----------");
        print(B);
        System.out.println("-----------");
        print(C);
        System.out.println("-----------");
        print(D);

        */
    }
}
