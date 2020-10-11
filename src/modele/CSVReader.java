package modele;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class CSVReader {
	//European countries use ";" as 
    //CSV separator because "," is their digit separator
   
	private static final String CSV_SEPARATOR = ";";
	static void writeToCSV(ArrayList<ItemPickup> productList) {
    	try {
        	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("products.csv"), "UTF-8"));
        	for (ItemPickup product : productList) {
        		StringBuffer oneLine = new StringBuffer();
        		oneLine.append(product.getTitle());
        		oneLine.append(CSV_SEPARATOR);
        		oneLine.append(product.getDesc());
        		oneLine.append(CSV_SEPARATOR);
        		oneLine.append(product.getTitleSize());
        		oneLine.append(CSV_SEPARATOR);
        		oneLine.append(product.getTitlePos());
        		oneLine.append(CSV_SEPARATOR);
        		oneLine.append(product.getDescSize());
        		oneLine.append(CSV_SEPARATOR);
        		oneLine.append(product.getDescPos());
        		oneLine.append(CSV_SEPARATOR);
        		oneLine.append(product.getTradTitle());
        		oneLine.append(CSV_SEPARATOR);
        		oneLine.append(product.getTradDesc());
        		oneLine.append(CSV_SEPARATOR);
        		oneLine.append(product.getImagePath());
        		oneLine.append(CSV_SEPARATOR);
        		bw.write(oneLine.toString());
        		bw.newLine();
        	}
        	bw.flush();
        	bw.close();
    	}
    	catch (UnsupportedEncodingException e) {}
    	catch (FileNotFoundException e){}
    	catch (IOException e){}
	}
}
