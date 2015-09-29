create database AcademyProject;

use AcademyProject;

Create table Stocks (
	Stockid INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	StockSymbol nvarchar(10) NOT NULL, 
	AskPrice Double NOT NULL, 
	AskMAvg Double, 
	BidPrice Double NOT NULL, 
	BidMAvg Double, 
	TodaysOpen Double NOT NULL, 
	PreviousClose Double NOT NULL, 
	Time_Of timestamp NOT NULL
);

Create table Transactions (
	Transactionid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Stockid INT NOT NULL,
	StockSymbol nvarchar(20) NOT NULL, 
	Volume int NOT NULL, 
	Buy double NOT NULL,
    BuyTime Timestamp NOT NULL,
	Sell double NULL,
    SellTime Timestamp NULL,
	CurrentPosition SET('Open', 'Close') NOT NULL,
    FOREIGN KEY(Stockid) REFERENCES Stocks(Stockid)
);

select * from Stocks;

select * from Transactions;