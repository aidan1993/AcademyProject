create database AcademyProject;

use AcademyProject;

Create table Stocks (
	Stockid INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	StockSymbol nvarchar(10) NOT NULL, 
    BidPrice DECIMAL(6,2) NOT NULL, 
	BidMAvg DECIMAL(6,2), 
	AskPrice DECIMAL(6,2) NOT NULL, 
	AskMAvg DECIMAL(6,2),
	TodaysOpen DECIMAL(6,2) NOT NULL, 
	PreviousClose DECIMAL(6,2) NOT NULL, 
	Time_Of timestamp NOT NULL
);

Create table Transactions (
	Transactionid INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    Stockid INT NOT NULL,
	StockSymbol nvarchar(20) NOT NULL, 
	Volume int NOT NULL, 
	Buy DECIMAL(6,2) NOT NULL,
    BuyTime Timestamp NOT NULL,
	Sell DECIMAL(6,2) NULL,
    SellTime Timestamp NULL,
	CurrentPosition ENUM('Open', 'Close') DEFAULT 'Open' NOT NULL,
    FOREIGN KEY(Stockid) REFERENCES Stocks(Stockid) ON DELETE CASCADE
);

select * from Stocks;

select * from Transactions;