/**
 * 
 */
window.onload = function() {

		var dps = []; // dataPoints

		var chart = new CanvasJS.Chart("chartContainer", {
			title : {
				text : "Stock Performance"
			},
			data : [ {
				type : "line",
				dataPoints : dps
			} ]
		});

		var xVal = 0;
		var yVal = 100;
		var updateInterval = 100;
		var dataLength = 500; // number of dataPoints visible at any point

		var updateChart = function(count) {
			count = count || 1;
			// count is number of times loop runs to generate random dataPoints.

			for (var j = 0; j < count; j++) {
				yVal = yVal + Math.round(5 + Math.random() * (-5 -5));
				dps.push({
					x : xVal,
					y : yVal
				});
				xVal++;
			}
			;
			if (dps.length > dataLength) {
				dps.shift();
			}

			chart.render();

		};

		// generates first set of dataPoints
		updateChart(dataLength);

		// update chart after specified time. 
		setInterval(function() {
			updateChart()
		}, updateInterval);
		
		
		var loop = 1;
		function runFeed() {
			var URL = "rest/livefeed?loop=" + loop;
			$.ajax({
	            type: "GET",
	            url: URL,
	            cache: false,
	            success: function (data) {
	                console.log("Live Feed Running");
	            },
	            error: function (data) {
	                console.log("Problem occurred");
	            }
        	});
			
			setTimeout(runFeed, 9000);
		}
		
		//Start Cycle
		runFeed();
};