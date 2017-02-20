import java.io.*;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Frank Hampus Weslien on 2017-02-19.
 */
public class Lab {
        // antal ggr, infil, utfil, sortingsmetod
    public static void main (String[] args){

    if(args.length == 4) {
        LinkedList<Integer> toBeSortedCopy;
        Lab lab = new Lab();
        int n = Integer.parseInt(args[0]);
        LinkedList<Integer> toBeSorted = lab.readFile(args[1]);
        boolean whichSort = args[3].equals("mySort");
        String nameOfSorting;

        if(whichSort)
            nameOfSorting = "mySort";
        else
            nameOfSorting = "Collection.sort";

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < n; i++) {
            toBeSortedCopy = (LinkedList<Integer>) toBeSorted.clone();
            long result;
            if(whichSort) {
                result = lab.timeMeasure( toBeSortedCopy , x -> lab.mySort(x));
            } else{
                result = lab.timeMeasure( toBeSortedCopy , x -> Collections.sort(x));
            }
            sb.append(i+1  + ", " + result + System.getProperty("line.separator"));
        }
        lab.saveResult(args[2], sb.toString() , nameOfSorting);
    } else{
        System.out.println("Not the right number of arguments");
    }

    }

    private static String[] test(){
        return new String[]{"600", "D:\\programmering\\txtfiler\\nummer.txt" ,"D:\\programmering\\txtfiler\\result2.txt", ""};
    }

    public long timeMeasure(LinkedList<Integer> list, Sort f){
        long startTime = System.nanoTime();
        // measured code
        f.sort(list);

        long endTime = System.nanoTime();
        return endTime - startTime;
    }


    public void mySort(LinkedList<Integer> list ){

        if(list.size() > 1){
            int head = list.poll();

            LinkedList<Integer> smaller = new LinkedList<Integer>();
            LinkedList<Integer> bigger = new LinkedList<Integer>();

            while(!list.isEmpty()){
                int x = list.poll();
                if (x <= head)
                    smaller.add(x);
                else
                    bigger.add(x);
            }

            mySort(smaller);
            mySort(bigger);

            //dessa är dumma vill ha en fucking append
            list.addAll(smaller);
            list.addAll(bigger);
        }
    }


    public LinkedList<Integer> readFile(String filename){
        LinkedList<Integer> randomNumbers = new LinkedList<Integer>();

        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line = br.readLine();

            while (line != null) {
               randomNumbers.add(Integer.parseInt(line));
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return randomNumbers;
    }


public void saveResult(String filename, String result, String sorteringsAlgoritm) {
    File log = new File(filename);
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;

    try {
        if (!log.exists()) {
            System.out.println("We had to make a new file.");
            log.createNewFile();
            fileWriter = new FileWriter(log, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("Nummer, " + sorteringsAlgoritm + System.getProperty("line.separator"));
        } else {
            fileWriter = new FileWriter(log, true);
            bufferedWriter = new BufferedWriter(fileWriter);
        }
        bufferedWriter.write(result);
        bufferedWriter.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

  }
}

