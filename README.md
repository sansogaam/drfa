#Objective

_In the real world of business we are dealing with data on day to day basis.Our IT systems has to get delivered in the same pace as our data is being captured in the production. The biggest challenge for the IT person is to ensure that the new release of the code will not result in any unwanted massaging of the data._

#drfa: Stands for (Data Rec For Analysis)
Its a tool which will help to automate your reconciliation process and quickly give the feedback to the developer or tester that some thing is going wrong with the current release and it might result to the unexpected breaks in the data.

User can achieve this by straight away reconciling the data between

1. Any Two Databases(MYSQL, MSSQL, ORACLE)
2. Two Files (CSV)

The tool will generate the nice summary report.

#Software Requirements

Business User

Below are the minimal software requirement to run this tool
*JRE 1.7 or greater

Developer
*JDK 1.7 or greater
*MAVEN 3.1 or greater
*IntelliJ or Eclipse Luna (Latest version)

#Third Party Library Used

* Clover ETL 4.0.3
* jansi (For command line console)

#Steps to use the tool

1. Download the drfa -<version>.jar from the dist folder in the above repository.
2. Copy the file to your desired location
3. Execute the _java -jar drfa-1.0.jar_ command
4. This will open the command prompt as below.

![Select the reconciliation type File or Database](/images/Rectype.png)
