# Banking Kata

Congratulations! You've just been hired by an exciting new FinTech startup that is bringing some fresh ideas to the stodgy old banking industry. The company is still in the early phases of building out its technology platform, so there's plenty of work left to be done.

On your first day, the founders gave you a huge list of features they'd love to add to the system and not much more direction than that. They're trying out a management style where engineers can self-organize and pick up whatever tasks they feel are the most valuable to the company.

The good news is that most of the codebase is pretty readable and well-tested. There are a couple areas, though, where you can tell they cut corners to get a feature out the door.

## Running the API

```sh
./gradlew run
```

## API Endpoints

`/account/{accountID}/balance`: Get the current balance of the account with ID `{accountID}`. For example,  `http://localhost:8080/account/a36aca2e-417f-45ed-83f8-55d7ecc69e5e/balance`.

## Feature Ideas

- Transaction history
- Account statement
- Inter-account transfers
- Interest accrual
- Transaction log
- Customer information
- Check deposit
- ACH clearing
- Bill pay
- Text nofications
- Travel notice
- Fraud detection
- Transaction location/mapping
- Joint accounts
- Authentication, security questions, etc.
- Savings and checking account types
- Certificates of deposit
- External linked account
- Activate debit card
- Freeze account
- Tax reporting (1099-INT) forms
- Scheduled transactions
- Automatic overdraft protection
- Low balance notifications
- Transaction notifications
- Authorized users
- Transaction notes
- Transaction categories
- Budget tracking
- Rewards points
- Close account
- open account
- Identity verification
- Check OCR
- Available balance
- Posted balance
- Overdraft fees
- Minimum balance requirements
- Direct deposit requirements
- Loan qualification
- Credit score reporting
- Bitcoin/Ether conversion
- Foreign transaction fees
- Out-of-network ATM fees
- ATM fee reimbursement
- Account audit log
- Customer relationship tracking (calls, support, etc.)
- Virtual account numbers
- Customer service automated phone line (balance inquiry, etc.)
- Phone+Transaction GPS matching to detect possible fraud
- Fraud-prevention heuristics  (multiple gas station visits, suspicious transactions, etc.)
- Download data to Quicken/Money/Excel/JSON
- Customer referral bonus (refer your friends and get $50 credit in account)
- Account maintenance fees
- Hard-copy statements (+fee)
- Replacement debit card (+fee)
- Physical address verification (mail a letter with a code, confirm code online)
- Returned deposit charge (bounced check)
- Inactivity fee
- Business accounts
- Fraud/crime - all associated personal and business accounts
- Change debit card PIN
- Link additional debit card
- Authorize debit transaction
- Put funds on hold (e.g. gas station $100 hold, then settle to final amount)
- Transaction filters (date, amount, payee, deposit/withdrawal, location)
- Database-based storage
