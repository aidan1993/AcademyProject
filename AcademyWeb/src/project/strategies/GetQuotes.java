package demo.stocks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.FileReader;
import java.util.*;

public class GetQuotes {

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		String symbol = "";
		int numTimes = 0;
		int maNum = 0;
		DataAccess.resetData();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the symbol: ");
		symbol = sc.nextLine();
		System.out.println("How many iterations: ");
		numTimes = Integer.parseInt(sc.nextLine());
		System.out.println("Moving average figure: ");
		maNum = Integer.parseInt(sc.nextLine());
		double[] mabBidArray = new double[maNum];
		double[] mabAskArray = new double[maNum];
		double dblBidma, dblAskma;
		int currentRow = 0;
		for(int i=1;i<=numTimes;i++){
			StringBuilder url = 
		            new StringBuilder("http://finance.yahoo.com/d/quotes.csv?s=" + symbol);
	        url.append("&f=sab&e=.csv"); 
	        String theUrl = url.toString();
	        URL obj = new URL(theUrl);
	        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	        // This is a GET request
	        con.setRequestMethod("GET");
	        con.setRequestProperty("User-Agent", "Mozilla/5.0");
	        int responseCode = con.getResponseCode();
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        //BufferedReader in = new BufferedReader(new FileReader("C:/Courses/BelfastCiti/testdata.csv"));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	        {
	        	//Read a row in from the feed
	        	System.out.println(inputLine);
	            String[] fields = inputLine.split(",");
	            fields[0] = fields[0].replace("\"", "");
	            //For debugging remove this loop
	            for(int t = 0; t < fields.length; t++){
	            	System.out.print("Element [" + t + "] value is " + fields[t]);
	            }
	          //Populate the moving average arrays
	        	mabAskArray[currentRow] = Double.parseDouble(fields[1]);
	        	mabBidArray[currentRow] = Double.parseDouble(fields[2]);
	            //The rows up to the first moving average calculation
	            if(currentRow < maNum - 1){
	            	//Add the data to the database, not enough data to calculate a moving averaage
		        	DataAccess.addTrade(fields[0], Double.parseDouble(fields[2]),
		        			Double.parseDouble(fields[1]), 0, 0);
		        	//increment currentrow this should never reach maNum
		        	currentRow++;
		        	System.out.println("Current row is " + currentRow);
	            }
	            else{
	            	//Calculate the moving averages using a helper function
	            	dblBidma = calcMovingAverage(mabBidArray);
	            	dblAskma = calcMovingAverage(mabAskArray);
	            	//remove the first elements from the arrays using a helper function
	            	slideArray(mabBidArray);
	            	slideArray(mabAskArray);
	            	//Add the data to the database
	            	DataAccess.addTrade(fields[0], Double.parseDouble(fields[2]),
		        			Double.parseDouble(fields[1]), dblBidma, dblAskma);
	            }	
	            System.out.println(fields[0]);
	            System.out.println();
	        }
		}
	}
	
	public static double calcMovingAverage(double[] thearray){
		double av = 0, total = 0;
		for(int i = 0;i < thearray.length;i++)
		{
			total += thearray[i];
		}
		av = total/thearray.length;
		return av;
	}

	public static void slideArray(double[] thearray){
		for(int i = 0;i < thearray.length - 1;i++)
		{
			thearray[i] = thearray[i + 1];
		}
	}
}
