#![DRFA](/images/drfa.png): Stands for (Data Rec For Analysis)
#Objective

_In the real world of business we are dealing with data on day to day basis.Our IT systems has to get delivered in the same pace as our data is being captured in the production. The biggest challenge for the IT person is to ensure that the new release of the code will not result in any unwanted massaging of the data._

##Description
Its a tool which will help to automate your reconciliation process and quickly give the feedback to the developer or tester that some thing is going wrong with the current release and it might result to the unexpected breaks in the data.

User can achieve this by straight away reconciling the data between

1. Any Two Databases(MYSQL, MSSQL, ORACLE)
2. Two Files (CSV)

The tool will generate the nice summary report.

##Software Requirements

Business User

Below are the minimal software requirement to run this tool
* JRE 1.7 or greater

* Developer
    * JDK 1.7 or greater
    * MAVEN 3.1 or greater
    * IntelliJ or Eclipse Luna (Latest version)

##Third Party Library Used

* Clover ETL 4.0.3
* jansi (For command line console)

##DRFA Roadmap 2015
![DRFA Roadmap](/images/DRFA-Roadmap.png)
#Documentation
##Steps to use the tool

1. Download the drfa -<version>.jar from the dist folder in the above repository.
2. Copy the file to your desired location
3. Execute the _java -jar drfa-1.0.jar_ command
4. This will open the command prompt and you need to follow the below steps 

### FILE Reconciliation type usage
#### Step-1 (Select the reconciliation type)
![Reconciliation type File or Database](/images/Rectype-1.png)
#### Step-2 (Specify the base file for comparision)
![Path to the base file](/images/Rectype-2.png)
#### Step-3 (Specify the target file for comparision)
![Path to the base file](/images/Rectype-3.png)
#### Step-4 (Specify the plugin, you can just put any empty directory path)
![Path to the base file](/images/Rectype-4.png)
#### Step-5 (Specify the proper metadata file, it should contain all the column names of the csv file)
##### Sample Metadata file (it should be saved with .fmt extension)
![Path to the base file](/images/Metadata.png)
##### Mention the complete meta data file which has the extension .fmt
![Path to the base file](/images/Rectype-5.png)
#### Step-6 (Specify the column index of the key which will be used to match with other file. It should be integer)
![Path to the base file](/images/Rectype-6.png)
#### Step-7 (Specify the type of report. Currently only HTML is supported)
![Path to the base file](/images/Rectype-7.png)
#### Step-8 (Specify the category of report. SUMMARY or DETAILED)
![Path to the base file](/images/Rectype-8.png)
#### Step-9 (Output of the report to be stored)
![Path to the base file](/images/Rectype-9.png)





