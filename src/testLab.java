import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.LinkedList;

/**
 * Created by Frank Hampus Weslien on 2017-02-23.
 */
public class testLab {
    Lab lab;
    LinkedList<Integer> list;

    @Before
    public void setUp() throws Exception {
        list = new LinkedList<Integer>();
        lab = new Lab();
    }

    @After
    public void tearDown() throws Exception {
        list = null;
        lab = null;
    }

    public void fillList() {
        int[] temp = new int[]{1,2,3,78,3,1,2,4,68,9,0,4,1,32,56,7,89,9};
        for(int x: temp){
            list.add(x);
        }
    }

    @Test
    public void testMyMergeSort(){
        fillList();
        lab.myMergeSort(list);
        int[] test = new int[]{0,1,1,1,2,2,3,3,4,4,7,9,9,32,56,68,78,89};
        int i = 0;

        for(int x : list){
        assertTrue("list isn't sorted", x == test[i]);
        i++;
        }
    }


}
