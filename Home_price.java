
import java.util.Random;

/**
 * Created by Aria on 2/7/17.
 */
public class Home_price {

    static private int counter = 0;
    static private int virtualCounter=0;
    static private int [] bigkArray;
    static private boolean bigk=false;
    static private int[] windows ;
    static private int k=0;

    public static void main(String[] args) {
        //n, k for sample data
        int n = 200000;
        k = 150000;

        int [] list=new int[n];
        windows=new int[n-k+1];
        Random r=new
                Random();
        //generating random data
        for(int i=0;i<n;i++){

            list[i]=r.nextInt(999999)+1;

        }
        // Initialize windows array
        for (int i = 0; i < n - k + 1; i++) {

            windows[i]=0;
        }

        double t1 = System.currentTimeMillis();
        int [] secondLevel =calculateForTwo(list);
        recursive(secondLevel);
        while(bigk){
            bigk=false;
            recursive(bigkArray);
        }
        double t2 = System.currentTimeMillis();
        // Printing all windows
        for (int i = 0; i < windows.length; i++) {
            System.out.println("w" + i + ": " + windows[i]);
        }
        //Total time of the main calculation
        System.out.println("Total time is " + (t2 - t1)/1000 + " s");

    }

    private static int[] calculateForTwo(int [] list) {

        int[] level2 = new int[(list.length)-1];
        ;
        for (int i = 0; i < (list.length - 1); i++) {

            if (list[i] > list[i + 1]) {

                level2[i]= -1;

            } else if (list[i] < list[i + 1]) {
                level2[i]=1;

            } else if (list[i] == list[i + 1]) {
                level2[i]=0;
            }

        }
        counter = 2;
        return level2;
    }


    private static void recursive(int [] li) {
        int [] nextLevel = new int[(li.length)-1];
        if (counter > (k - 1)) {
            return;
        }

        // Recursion has a limit in Java, so here I tried to find a way around
        if(virtualCounter == 9400){
            virtualCounter=0;
            bigkArray=li;
            bigk=true;
            return;
        }
        // Generating data for the next level
        for (int i = 0; i < (li.length - 1); i++) {
            if (li[i] == li[i + 1]) {
                nextLevel[i]=li[i];
            } else {
                nextLevel[i]=0;
            }
        }
        if(counter == 2){
            calculateSum(li);
        }
        counter++;
        virtualCounter++;
        calculateSum(nextLevel);
        recursive(nextLevel);

    }

    // To recognize every window and calculate the sum for every one of them
    private static void calculateSum(int[] b){
        int u = 0;
        //preSum stores the sum for the previous window in the loop
        int preSum = 0;
        //If this is the last level
        if ((k +1 -counter ) == 1) {
            for (int m = 0; m < b.length; m++) {
                windows[m]= windows[m] + b[m];
            }
        }
        else {

            for (int j = 0; j < (b.length - k + counter); j++) {
                //if we are calculating the first window
                if (j == 0) {
                    for (int h = 0; h < k + 1 - counter; h++) {
                        u = u + b[h];
                    }
                    windows[j]= windows[j] + u;
                    preSum = u;
                    u = 0;
                } else {
                    // y is the first element in the previous window in the loop
                    int y = b[j - 1];
                    // a is the sum for the window without the last element
                    int a = preSum - y;
                    // so a + last element of the window is the sum for the whole window
                    int sum = a + (b[j + k - counter]);
                    windows[j]= windows[j] + sum;
                    preSum = sum;

                }
            }
        }
    }

}
