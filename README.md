Personal expenses management console application that allows users to track how much money have they spent.

How to start the program:
1)Download this repository.
2)Import zip-archive which you downloaded in your Integrated Development Environment (IDE).
3)Change the database settings (Test/src/main/java/META-INF) open file 'persistence.xml' and change 'value' in the row '<property name="hibernate.connection.username" value="YOUR USERNAME" />' , '<property name="hibernate.connection.password" value="PASSWORD" />'. Value - which data you use to connect to database.
4)Run App.java (Test/src/main/java/packageMain).

You can to use command:
1)add DATE NAME PRICE CURRENCY
This command adds expense entry to the list of user expenses. Command accepts following parameters: 
DATE — the date when expense occurred, format — Year-Month-Day.
2)list
This command shows the list of all expenses sorted by date.
3)clear DATE 
This command removes all expenses for specified date, where
DATE — the date for which all expenses should be removed, format — Year-Month-Day.
4)total CURRENCY
This command calculates the total amount of money spent and present it to user in specified currency, where: 
CURRENCY — the currency in which total amount of expenses should be presented.
5)out
This command finishes the console program.
