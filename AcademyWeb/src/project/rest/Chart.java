package project.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import project.strategies.Strategy;
import project.strategies.TwoMovingAverage;

@Path("/chart")
public class Chart {
	@GET
	public List<Double> getChartValue(@QueryParam("symbol")String symbol) {
		List<TwoMovingAverage> movingAvg = Strategy.getTwoMAvg();
		List<Double> shortPrices = null;
		
		for(TwoMovingAverage mAvg : movingAvg) {
			if(mAvg.getStock().equals(symbol)) {
				shortPrices = mAvg.getShortPrices();
			}
		}
		
		return shortPrices;
	}
}
