package avengers;

/**
 * Given a starting event and an Adjacency Matrix representing a graph of all possible 
 * events once Thanos arrives on Titan, determine the total possible number of timelines 
 * that could occur AND the number of timelines with a total Expected Utility (EU) at 
 * least the threshold value.
 * 
 * 
 * Steps to implement this class main method:
 * 
 * Step 1:
 * UseTimeStoneInputFile name is passed through the command line as args[0]
 * Read from UseTimeStoneInputFile with the format:
 *    1. t (int): expected utility (EU) threshold
 *    2. v (int): number of events (vertices in the graph)
 *    3. v lines, each with 2 values: (int) event number and (int) EU value
 *    4. v lines, each with v (int) edges: 1 means there is a direct edge between two vertices, 0 no edge
 * 
 * Note 1: the last v lines of the UseTimeStoneInputFile is an ajacency matrix for a directed
 * graph. 
 * The rows represent the "from" vertex and the columns represent the "to" vertex.
 * 
 * The matrix below has only two edges: (1) from vertex 1 to vertex 3 and, (2) from vertex 2 to vertex 0
 * 0 0 0 0
 * 0 0 0 1
 * 1 0 0 0
 * 0 0 0 0
 * 
 * Step 2:
 * UseTimeStoneOutputFile name is passed through the command line as args[1]
 * Assume the starting event is vertex 0 (zero)
 * Compute all the possible timelines, output this number to the output file.
 * Compute all the posssible timelines with Expected Utility higher than the EU threshold,
 * output this number to the output file.
 * 
 * Note 2: output these number the in above order, one per line.
 * 
 * Note 3: use the StdIn/StdOut libraries to read/write from/to file.
 * 
 *   To read from a file use StdIn:
 *     StdIn.setFile(inputfilename);
 *     StdIn.readInt();
 *     StdIn.readDouble();
 * 
 *   To write to a file use StdOut:
 *     StdOut.setFile(outputfilename);
 *     //Call StdOut.print() for total number of timelines
 *     //Call StdOut.print() for number of timelines with EU >= threshold EU 
 * 
 * Compiling and executing:
 *    1. Make sure you are in the ../InfinityWar directory
 *    2. javac -d bin src/avengers/*.java
 *    3. java -cp bin avengers/UseTimeStone usetimestone.in usetimestone.out
 * 
 * @author Yashas Ravi
 * 
 */

public class UseTimeStone {

    public static void main (String [] args) {
    	
        if ( args.length < 2 ) {
            StdOut.println("Execute: java UseTimeStone <INput file> <OUTput file>");
            return;
        }
        int combos = 1;
        int successes = 0;
        String timeInputFile = args[0];
        String timeOutputFile = args[1];
        StdIn.setFile(timeInputFile);
        int threshold = StdIn.readInt();
        StdIn.readLine();
        int nodes = StdIn.readInt();
        StdIn.readLine();
        int[] values = new int[nodes];
        for(int i = 0; i < nodes; i++){
            StdIn.readInt();
            values[i] = StdIn.readInt();
            StdIn.readLine();
        }
        if(values[0]>=threshold){
            successes++;
        }
        int[][] matrix = new int[nodes][nodes];
        for(int r = 0; r < nodes; r++){
            for(int c = 0; c < nodes; c++){
                matrix[r][c] = StdIn.readInt();
            }
            StdIn.readLine();
        }
        int[] keepers = new int[nodes];
        int ptr = 1;
        int mainNode = 0;
        int c2 = 0;
        int sum = 0;
        boolean cool = false;
        for(int c = 0; c<nodes; c++){
            //System.out.println(mainNode + "mainnode");
            if(matrix[0][c] == 1){
                combos++;
                keepers[ptr] = c;

                for(int i = 0; i<=ptr; i++){
                    sum += values[keepers[i]];
                }
                if(sum >= threshold){
                    successes++;
                }
                // for(int i = 0; i <= ptr; i++){
                //     //System.out.print(keepers[i] + " " );
                // }
                //System.out.println(sum + " start");
                sum = 0;
                mainNode = c;
                ptr++;
                c2 = 0;
                cool = false;
                
                while(ptr != 0){
                    //System.out.println(mainNode + "mainnode");
                    while(c2<nodes){
                        //System.out.println(keepers[1] + "mainnode");
                        if(matrix[mainNode][c2] == 1 ){
                            //ptr++;
                            if(cool){
                                keepers[ptr+1] = c2;
                                for(int i = 0; i<=ptr+1; i++){
                                    sum += values[keepers[i]];
                                }
                                // for(int i = 0; i <= ptr+1; i++){
                                //     System.out.print(keepers[i] + " ");
                                // }
                            }
                            else{
                                keepers[ptr] = c2;
                                for(int i = 0; i<=ptr; i++){
                                    sum += values[keepers[i]];
                                }
                                // for(int i = 0; i <= ptr; i++){
                                //     System.out.print(keepers[i] + " ");
                                // }
                            }
                            combos++;
                            if(sum >= threshold){
                                successes++;
                            }
                            //System.out.println(sum + " " + cool);
                            sum = 0;
                            mainNode = c2;
                            ptr++;
                            c2 = 0;
                            //cool = false;
                            //cool = true;
                        }
                        else{
                            c2++;
                        }
                    }
                    c2 = keepers[ptr]+1;
                    //c2 = keepers[ptr];
                    mainNode = keepers[ptr-1];
                    ptr--;
                    cool = true;
                    //System.out.println(mainNode);
                }
                    
            }
            mainNode = 0;
            ptr = 1;
        }
        StdOut.setFile(timeOutputFile);
        StdOut.println(combos);
        StdOut.println(successes);

        


        


        
    }
}
