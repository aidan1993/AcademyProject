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
			yVal = yVal + Math.round(5 + Math.random() * (-5 - 5));
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
			type : "GET",
			url : URL,
			cache : false,
			success : function(data) {
				console.log("Live Feed Running");
			},
			error : function(data) {
				console.log("Problem occurred");
			}
		});

		setTimeout(runFeed, 10000);
	}

	$(document).ready(function() {
		runFeed();
	});

	// Clone buttons for quantity
	
//	var $options = $(".divselect1 > option").clone();
//	$('.divselect1copy').empty();
//	$('.divselect1copy').append($options);
//	$('.divselect1copy').val($('.divselect1').val());

//	$('.cloneBtn').click(function() {
//		var $options = $(".divselect1 > option").clone();
//		$('.divselect1a').empty();
//		$('.divselect1a').append($options);
//		$('.divselect1a').val($('.divselect1').val());
//	});
//
//	$('.cloneBtn').click(function() {
//		var $options = $(".divselect2 > option").clone();
//		$('.divselect1b').empty();
//		$('.divselect1b').append($options);
//		$('.divselect1b').val($('.divselect2').val());
//	});
//
//	$('.cloneBtn').click(function() {
//		var $options = $(".divselect3 > option").clone();
//		$('.divselect1c').empty();
//		$('.divselect1c').append($options);
//		$('.divselect1c').val($('.divselect3').val());
//	});
//
//	$('.cloneBtn').click(function() {
//		var $options = $(".divselect4 > option").clone();
//		$('.divselect1d').empty();
//		$('.divselect1d').append($options);
//		$('.divselect1d').val($('.divselect4').val());
//	});
//
//	$('.cloneBtn').click(function() {
//		var $options = $(".divselect5 > option").clone();
//		$('.divselect1e').empty();
//		$('.divselect1e').append($options);
//		$('.divselect1e').val($('.divselect5').val());
//	});
//
//	$('.cloneBtn').click(function() {
//		var $options = $(".divselect6 > option").clone();
//		$('.divselect1f').empty();
//		$('.divselect1f').append($options);
//		$('.divselect1f').val($('.divselect6').val());
//	});
//
//	$('.cloneBtn').click(function() {
//		var $options = $(".divselect7 > option").clone();
//		$('.divselect1g').empty();
//		$('.divselect1g').append($options);
//		$('.divselect1g').val($('.divselect7').val());
//	});
//
//	$('.cloneBtn').click(function() {
//		var $options = $(".divselect8 > option").clone();
//		$('.divselect1h').empty();
//		$('.divselect1h').append($options);
//		$('.divselect1h').val($('.divselect8').val());
//	});
//
//	$('.cloneBtn').click(function() {
//		var $options = $(".divselect9 > option").clone();
//		$('.divselect1i').empty();
//		$('.divselect1i').append($options);
//		$('.divselect1i').val($('.divselect9').val());
//	});

	$(document).ready(function() {
		baRefresh();
	});

	function baRefresh() {
		$("#left-div").load('index.jsp #left-div > *', function() {
			setTimeout(baRefresh, 10000);
			
		});
	}
 
	
	console.log

};