CREATE TABLE Accounts
(
    AccountID          INT PRIMARY KEY,
    AccountPhoneNumber VARCHAR(15)
);

CREATE TABLE Transactions
(
    TransactionId INT,
    AccountID INT,
    CallType  VARCHAR(2),
    BeginTime DATE,
    EndTime   DATE,
    FOREIGN KEY (AccountID) REFERENCES Accounts (AccountID)
);