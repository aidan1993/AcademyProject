create database Stocks;

use Stocks;

Create table Stocks (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
StockSymbol nvarchar(10) NOT NULL, 
AskPrice Double NOT NULL, 
AskMAvg Double, 
BidPrice Double NOT NULL, 
BidMAvg Double, 
TodaysOpen Double NOT NULL, 
PreviousClose Double NOT NULL, 
Time_Of timestamp NOT NULL);

Create table Trade_Transactions (Transaction_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
FOREIGN KEY(Stockid) REFERENCES Stocks(id), 
StockSymbol nvarchar(20) NOT NULL, 
Volume int NOT NULL, 
Buy double NOT NULL,
Sell double NOT NULL,
Position SET('Open', 'Close') NOT NULL);

select * from Ticker;

select * from Trade_Transactions;