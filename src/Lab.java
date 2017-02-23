

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Frank Hampus Weslien on 2017-02-19.
 */
public class Lab {
        // antal ggr, infil, utfil, sortingsmetod (skriv mySort för din egen sorteringsmetod)
    public static void main (String[] args){
//args = test();
    if(args.length == 4) {
        LinkedList<Integer> toBeSortedCopy;
        Lab lab = new Lab();
        int n = Integer.parseInt(args[0]);
        LinkedList<Integer> toBeSorted = lab.readFile(args[1]);
        boolean mySort = args[3].equals("mySort");
        boolean myMergeSort = args[3].equals("myMergeSort"); //dålig
        boolean myBestQuickSort = args[3].equals("myBestQuickSort");
        String nameOfSorting;

        if(mySort)
            nameOfSorting = "mySort";
        else if (myMergeSort)
            nameOfSorting = "myMergeSort";
        else if (myBestQuickSort)
            nameOfSorting = "myBestQuickSort";
        else
            nameOfSorting = "Collection.sort";

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < n; i++) {
            toBeSortedCopy = (LinkedList<Integer>) toBeSorted.clone();
            long result;
            if(mySort)
                result = lab.timeMeasure( toBeSortedCopy , x -> lab.mySort(x));
            else if (myMergeSort)
                result = lab.timeMeasure( toBeSortedCopy , x -> lab.myMergeSort(x));
            else if (myBestQuickSort)
                result = lab.timeMeasure( toBeSortedCopy , x -> lab.myBestQuickSort(x));
            else
                result = lab.timeMeasure( toBeSortedCopy , x -> Collections.sort(x));

            for(int x: toBeSortedCopy) {
                System.out.println(x);
            }
            sb.append(i+1  + ", " + result + System.getProperty("line.separator"));
        }
        lab.saveResult(args[2], sb.toString() , nameOfSorting);
    } else{
        System.out.println("Not the right number of arguments");
    }

    }

    private static String[] test(){
        return new String[]{"600", "D:\\programmering\\txtfiler\\nummer.txt" ,"D:\\programmering\\txtfiler\\myBestQuickSort.txt", "myBestQuickSort"};
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

            //dessa är dumma, jag vill ha en fucking append
            list.addAll(smaller);
            list.add(head);
            list.addAll(bigger);
        }
    }


    public void myMergeSort(LinkedList<Integer> list ){

        if(list.size() > 1){

            LinkedList<Integer> smaller = new LinkedList<Integer>();
            LinkedList<Integer> bigger = new LinkedList<Integer>();

            int half = list.size()/2;
            int size = list.size();

            for(int i = 0; i < size; i++){
                if (i < half)
                    smaller.add(list.poll());
                else
                    bigger.add(list.poll());
            }
            System.out.println(list.isEmpty());

            myMergeSort(smaller);
            myMergeSort(bigger);

            list.addAll(merge(smaller, bigger));

        }
    }

    private LinkedList<Integer> merge(LinkedList<Integer> first, LinkedList<Integer> last){
        LinkedList<Integer> result = new LinkedList<Integer>();
        while(!(first.isEmpty() || last.isEmpty())){
            if (first.peek().compareTo(last.peek()) < 0)
                result.add(first.poll());
            else
                result.add(last.poll());
        }

        if (!first.isEmpty())
            result.addAll(first);
        else if(!last.isEmpty())
            result.addAll(last);

        return result;
    }



    public void myBestQuickSort(LinkedList<Integer> list){
        myBestQuickSort(list, list); //first list will get emptied in first call
    }


    private void myBestQuickSort(LinkedList<Integer> original, LinkedList<Integer> list){

        if(list.size() > 1) {
            int head = list.poll();

            LinkedList<Integer> smaller = new LinkedList<Integer>();
            LinkedList<Integer> bigger = new LinkedList<Integer>();

            while (!list.isEmpty()) {
                int x = list.poll();
                if (x <= head)
                    smaller.add(x);
                else
                    bigger.add(x);
            }

            myBestQuickSort(original, smaller);
            original.add(head);
            myBestQuickSort(original, bigger);
        } else if (list.size() == 1){
            original.add(list.getFirst());
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

