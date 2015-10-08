package project.strategies;

import java.util.ArrayList;
import java.util.List;

import project.entity.Stock;

public class Strategy {
	
	private static List<TwoMovingAverage> twoMAvg = new ArrayList<>();
	
	public Strategy() {

	}
	
	public static List<TwoMovingAverage> getTwoMAvg() {
		return twoMAvg;
	}

	public static void setTwoMAvg(List<TwoMovingAverage> twoMAvg) {
		Strategy.twoMAvg = twoMAvg;
	}
	
	public static void addTwoMAvg(TwoMovingAverage twoMAvg) {
		Strategy.twoMAvg.add(twoMAvg);
	}

	//Calculates moving average for the specified array of data
	public static double calcMovingAverage(List<Stock> thearray){
		double av = 0, total = 0;
		for(int i = 0;i < thearray.size();i++)
		{
			double bid = thearray.get(i).getBidPrice();
			double ask = thearray.get(i).getAskPrice();
			double avg = (bid+ask)/2;
			total += avg;
		}
		av = total/thearray.size();
		return Math.round(av * 100.0)/100.0;
	}
}
