package modelLogic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.io.IOException;

import java.io.PrintWriter;

import modelInterfaces.Randomizer;

public final class DestributionTester {
    public DestributionTester() {
        super();
    }

    public static void main(String[] args) throws IOException {
        File fileName;
      
        //        DestributionTester destributionTester = new DestributionTester();
        //        Randomizer r = new UniformDestribution(1243532535,1,0);
        Randomizer r = new NormalDestribution(Math.round((Math.random() * 100000000)),20,3);
        //        Randomizer r = new EksponentialDestribution(Math.round((Math.random() * 1000000)),0.2);       
        //        Randomizer r = new ErlangDistribution(Math.round((Math.random() * 1000000)),0.5,3);
        fileName = new File("C:\\TMP\\"+r.getClass().toString()+".txt");
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close(); 
        FileWriter fstream = new FileWriter(fileName, true);
        BufferedWriter out = new BufferedWriter(fstream);
        
        for (int i=0;i<32000;i++) {
            out.write(Double.toString(r.nextNumber()).replace('.', ','));
            out.newLine();
        }
        out.close();
    }
}
