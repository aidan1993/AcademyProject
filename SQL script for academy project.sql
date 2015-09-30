create database AcademyProject;

use AcademyProject;

Create table Stocks (
	Stockid INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	StockSymbol nvarchar(10) NOT NULL, 
    BidPrice DECIMAL(6,2) NOT NULL,
	AskPrice DECIMAL(6,2) NOT NULL, 
	MovingAvg DECIMAL(6,2),
    DayHigh DECIMAL(6,2),
    DayLow DECIMAL(6,2),
	TodaysOpen DECIMAL(6,2) NOT NULL, 
	PreviousClose DECIMAL(6,2) NOT NULL, 
	Time_Of timestamp NOT NULL
);

Create table Transactions (
	Transactionid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Stockid INT NOT NULL,
	StockSymbol nvarchar(20) NOT NULL, 
	Volume int NOT NULL, 
	Price DECIMAL(6,2) NOT NULL,
    TransTime Timestamp NULL,
	TransType ENUM('Buy', 'Sell') DEFAULT 'Buy' NOT NULL,
    FOREIGN KEY(Stockid) REFERENCES Stocks(Stockid) ON DELETE CASCADE
);

select * from Stocks;

select * from Transactions;

DROP DATABASE AcademyProject;