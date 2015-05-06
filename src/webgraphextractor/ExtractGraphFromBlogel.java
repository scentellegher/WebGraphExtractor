package webgraphextractor;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cent
 */
public class ExtractGraphFromBlogel {
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
//        File input = new File("/home/ubuntu/datasets/webbase/webbaseTMP");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/webbase/webbase4gps");
//        File input = new File("/home/ubuntu/datasets/uk2002/uk2002TMP");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/uk2002/uk20024gps");
        File input = new File("/home/ubuntu/datasets/arabia/arabiaTMP");
        FileWriter fw = new FileWriter("/home/ubuntu/datasets/arabia/arabia4gps");
//        File input = new File("/home/ubuntu/datasets/uk2005/uk2005TMP");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/uk2005/uk20054gps");
//        File input = new File("/home/ubuntu/datasets/uk2007-05/ukTMP");
//        FileWriter fw = new FileWriter("/home/ubuntu/datasets/uk2007-05/uk4gps");
        BufferedReader br = new BufferedReader(new FileReader(input));
        String line ="";
        String []tmp;
        String me ="";
        String nb ="";
        String []list;
        // line -> vid blockid workerid \t nb_id nb_block_id nb_worker_id ...
        while((line = br.readLine())!=null){
            try{
                tmp = line.split("\t");
                me = tmp[0];
                fw.write(me.split(" ")[0]+" ");
                nb = tmp[1];
                //extract neighbors ids
                list = nb.split(" ");
                for(int j=0; j<list.length; j++){
                    //take only fist elem of the triplet
                    if(j%3==0){
                        fw.write(list[j]+" ");
                    }
                }
                fw.write("\n");
            }catch(Exception e){
                //no neighbors or empty line (last nodes!)
                fw.write("\n");
            }
        }
        //remove empty lines!
        System.out.println("Remove empty lines!");
        br.close();
        fw.close();
    }
}
