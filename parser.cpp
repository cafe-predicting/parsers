/* parser.cpp
 * February 2016
 * Cory Gray
 *
 * Description: This program parses the CSV file containing point of sale data
 *	from Lexmark's campus cafeteria. Data that is not relevant to the task
 *	of identifying population trends is removed, redundant entries are
 *	condensed to one entry per customer transaction. The data is then
 *	optimized for more efficient reading by the program, which generates
 *	data sets on total customer population over time and average customer
 *	populations during a particular time segment, separated by demographic.
 *	This allows for the easy exploration of the given data for the purpose
 *	of identifying and analyzing trends in customer populations.
 *
 * Input: The user will specify the name of the file which gives the point of
 *	sale data (for instance PointOfSaleSimulation.csv), which will then be
 *	parsed. The user will also be asked give a filenames for any data files
 *	generated by the program.
 *
 * TODO: Update output
 * Output: Three data files will be created, one with point of sale data reduced
 *	to one entry per customer with only relevant information, one with the
 *	average customer populations per 30-minute period throughout the day
 *	(separated by demographic), and one with total customer populations
 *	(separated by demographic) throughout the time period over which the
 *	given data ranges.
 *
 * NOTE: This program expects data input to follow a certain structure, and is
 *	 only somewhat flexible in handling varying inputs. Specifically, the
 *	 point of sale data file must have column headers contained in the first
 *	 row. There are four headers required by the program: "CustomerAgeId", 
 *	 "CustomerGenderId", "Datetime", and "DayOfWeek". For correct analysis,
 *	 redundant entries for multiple items in a single customer transaction
 *	 must be eliminated, leaving only one entry per customer transaction.
 *	 This will be done by the program automatically if the data file
 *	 also contains the headers "CustomerDwellTime (s)" and
 *	 "CustomerAttentionTime (s)". If these headers are not included, the
 *	 program assumes redundant entries are already eliminated, potentially
 *	 giving erroneous results if this is not the case. The order of the
 *	 columns does not matter, and all data points except those in the
 *	 columns listed above will be ignored.
 *	 
 */

#include <fstream>
#include <iostream>
#include <string>
#include <vector>

using std::cout;
using std::cin;
using std::endl;
using std::ifstream;
using std::istream;
using std::ofstream;
using std::string;
using std::vector;



/* tokenize() - Helper function for extract_data(). Split a line into tokens.
 *
 * Description: Splits a line into tokens using a given delimiter, and returns
 *	the tokens in a vector of strings.
 *
 * Input: 
 *	line - The line to be split into tokens
 *	delimiter - A character by which the funtion descriminates tokens,
 *		    usually a comma when parsing a CSV file
 *
 * Output: Returns a vector of strings holding the tokens extracted from the
 *	given line.
 */
vector <string> tokenize(string line, char delimiter)
{
	vector <string> tokens; //Holds the tokens extracted from line
	string token = ""; //Holds a single token at a time

	//For every character in the line
	for(int i=0; i<line.length(); i++)
	{
		//If the current character is not the delimeter, add it to token
		if(line[i] != delimiter)
		{
			token = token + line[i];
		}
		else //If current character is delimiter, add token to vector
		{
			tokens.push_back(token);
			token = "";
		}
	} //END for every character in line

	//Add the final token if necessary
	if(token != "")
	{
		tokens.push_back(token);
	}

	return tokens;
}



/* eliminate_irrelevant_data() - Helper function for extract_data(); Eliminate
 *	irrelevant data columns from data set
 *
 * Description: Uses column headers to identify columns which contain data
 *	relevant to analyzing customer population trends or eliminating
 *	redundant rows. Sorts these columns in the order of age, sex, date/time,
 *	day, dwell time, and attention time. All other columns are eliminated.
 *
 * Input:
 *	data - A 2D vector representing the data table to be parsed and reduced.
 *	useRedundancyData - A boolean indicating whether or not to include
 *			    columns which contain identifying information for
 *			    redundancy checks.
 *
 * Output: The given data table has all columns containing irrelevant data
 *	removed, leaving only columns for customer age, customer sex, date/time
 *	of transaction, and day of transaction, in that order, If
 *	useRedundancyData is set to TRUE, columns for customer dwell time, and
 *	customer attention time, are also included. The column header row is
 *	removed.
 *
 */
void eliminate_irrelevant_data(vector<vector<string>> & data,
			       bool useRedundancyData)
{
	vector<vector<string>> reducedData; //Stores relevant data from data set
	vector<int> indices (6); //Stores the column numbers of core data points
				 // including age, sex, date/time, day, dwell
				 // time and attention time, in that order
	vector<string> currentRow; //Holds a single row of relevant data

	//If redundancy check data is not available, resize indices
	if(!useRedundancyData){indices.resize(4);}

	//Identify column numbers of core data points from column headers
	for(int i=0; i<data[0].size(); i++){
		if(data[0][i] == "CustomerAgeId"){indices[0] = i;}
		if(data[0][i] == "CustomerGenderId"){indices[1] = i;}
		if(data[0][i] == "Datetime"){indices[2] = i;}
		if(data[0][i] == "DayOfWeek"){indices[3] = i;}
		if(data[0][i] == "CustomerDwellTime (s)"
		   && useRedundancyData) {indices[4] = i;}
		if(data[0][i] == "CustomerAttentionTime (s)"
		   && useRedundancyData) {indices[5] = i;}
	}

	//Remove column header line
	data.erase(data.begin());

	//For each row in the data set
	for(int i=0; i<data.size(); i++)
	{
		//For every element in indices
		for(int j=0; j<indices.size(); j++)
		{
			//Add the data point in the current row and column
			//  indicated by indices to currentRow
			currentRow.push_back(data[i][indices[j]]);
		}

		//Add the row to reducedData
		reducedData.push_back(currentRow);

		//Clear currentRow vector
		currentRow.clear();
	}

	//Reassign data to reduced set
	data = reducedData;
}



/* eliminate_redundant_data() - Helper function for extract_data(); remove
 *	rows identical to the row above
 * 
 * Description: Eliminates redundant lines by comparing them to the previous
 *	line, ensuring that each line is different from the preceding and
 *	following lines. This ensures that each customer transaction produces
 *	only one line of data, instead of one for each item purchased.
 *
 * Input:
 *	data - A 2D vector representing a table of data, which is modified by
 *	       the function by eliminating redundant adjacent rows
 *
 * Output: Any row that is identical to the row above it in the table is
 *	eliminated. The two columns containing identifying information for
 *	redundancy checks are also eliminated.
 *
 * Note: We expect many redundancies, as each customer is likely to have
 *	purchased multiple products. An in-place algorithm would require
 *	removing many elements from the data set, potentially taking
 *	much time. We instead use an out-of-place algorithm, since we
 *	expect the reduced data set to be much smaller than the
 *	original set, requiring relatively little space.
 */
void eliminate_redundant_data(vector<vector<string>> & data)
{
	vector<vector<string>> reducedData; //Stores unique rows from data set
	vector<string> previousRow; //Stores the previous row for comparison
	vector<string> reducedRow; //Holds only core four elements of a row

	//For every element in data vector
	for(int i=0; i<data.size(); i++)
	{
		//If the current line is different from previous line, add to
		//  reduced data set
		if(data[i] != previousRow)
		{
			//Remove redundancy check columns (the last two)
			reducedRow = data[i];
			reducedRow.erase(reducedRow.end()-2,reducedRow.end());
			//Add to data set
			reducedData.push_back(reducedRow);
			//Update previousline with new data
			previousRow = data[i];
		}
	}

	//Assign reduced data set to data
	data = reducedData;
}


/* extract_data() - Extract data from a CSV file
 *
 * Description: Creates a 2D vector representing the data table in a given data
 *	file. Analyzez column headers to ensure that all necessary information
 *	is included. Eliminates columns with unnecessary information. Reduces
 *	rows to one per customer, if redundancy information is present.
 *
 * Input:
 *	datafile - The data stream obtained from opening the data file specified
 *		   by the user in main(). Data is extracted from this stream.
 *		   The stream is not closed by this function.
 *
 * Output:
 *	The reduced data table is returned as a 2D vector. Column headers are
 *	removed, only one row of data per customer transaction is included, and
 *	only the customer age, sex, date/time of transaction, and day of
 *	transaction are included in each row. If necessary information (age,
 *	sex, date/time, and day) are not given, the program displays an error
 *	message and terminates.
 *
 * NOTE:  This function requires that customer age and sex and the date/time and
 *	day of the transaction are given under columns with headers
 *	"CustomerAgeId", "CustomerGenderId", "Datetime", and "DayOfWeek",
 *	respectively. The program will terminate if one or more of these column
 *	headers are not found. Additionally, the program will only be able to
 *	eliminate redundant data entries if customer dwell time and customer
 *	attention time are given under column headers "CustomerDwellTime (s)"
 *	and "CustomerAttentionTime (s)", respectively.
 *
 *	IMPORTANT: The program will continue with customer dwell time and
 *	customer attention time information missing. However, it is assumed that
 *	redundant lines of information have already been removed. If this is not
 *	the case, the program may increment population counts multiple times
 *	for each customer, giving erroneous results.
 */
vector<vector<string>> extract_data(istream & datafile)
{
	cout << "Extracting data..." << endl;
	vector<vector<string>> data; //2D vector storing the table in datafile
	vector<string> currentRow; //Vector for holding one row of data
	string line; //String holding one row from the table
	vector<bool> flags(6, false); //Holds flags for essential data points:
				      // age, sex, date/time, day, dwell time,
				      // and attention time

	//For every line in the data file, tokenize and push token vector o data
	while(std::getline(datafile, line))
	{
		currentRow = tokenize(line, ',');
		data.push_back(currentRow);
	}

	//Ensure that the data contains all information necessary for parsing
	//  by setting flags when a necessary data field is found
	for(int i=0; i<data[0].size(); i++)
	{
		if(data[0][i] == "CustomerAgeId"){flags[0] = true;}
		if(data[0][i] == "CustomerGenderId"){flags[1] = true;}
		if(data[0][i] == "Datetime"){flags[2] = true;}
		if(data[0][i] == "DayOfWeek"){flags[3] = true;}
		if(data[0][i] == "CustomerDwellTime (s)"){flags[4] = true;}
		if(data[0][i] == "CustomerAttentionTime (s)"){flags[5] = true;}
	}

	//If any core flag is false, necessary information is missing; terminate
	if(!flags[0] || !flags[1] || !flags[2] || !flags[3])
	{
		//Print error message
		cout << "ERROR: Data file does not contain the necessary data";
		cout << endl << "OR has missing or invalid column headers";
		cout << endl;
		//Return an empty vector
		return vector<vector<string>>();
	}

	//Eliminate columns of irrelevant data
	cout << "   Removing irrelevant data..." << endl;
	eliminate_irrelevant_data(data, flags[4] && flags[5]);

	//If dwell time and attention time columns exist, we may eliminate
	//  redundant data. If these do not exist, it is assumned redundant data
	//  has already been eliminated.
	if(flags[4] && flags[5])
	{
		cout << "   Eliminating redundancies..." << endl;
		eliminate_redundant_data(data);
	}

	cout << "   Done" << endl;
	return data;
}



/* optimize_data() - Optimize the reduced data set for reading by program
 *
 * Description: Splits date and time into separate columns; date stays in the
 *	third column while time is added as a fifth column. Rewrites days
 *	according to order in the week, 0-6. Assigns time to a 30-minute
 *	interval of the day, 0-47.
 *
 * Input:
 *	data - A 2D vector representing the data table; the date/time and day
 *	       columns are modified and a new column is added.
 *
 * Output: The data table is modified by splitting date and time into separate
 *	columns, rewriting the day of the week as an integer 0-6, and rewriting
 *	the time as an integer 0-47.
 */
void optimize_data(vector<vector<string>> & data)
{
	cout << "Optimizing data...";
	vector<string> dateTime (2); //Stores a single date and time pair

	//Split date and time
	for(int i=0; i<data.size(); i++)
	{
		//Split date and time
		dateTime = tokenize(data[i][2], ' ');
		//Assign date to third element
		data[i][2] = dateTime[0];
		//Add time as fifth element
		data[i].push_back(dateTime[1]);
	}

	//Assign days to integers 0-6
	for(int i=0; i<data.size(); i++)
	{
		if(data[i][3] == "Sunday")   {data[i][3] = "0";}
		if(data[i][3] == "Monday")   {data[i][3] = "1";}
		if(data[i][3] == "Tuesday")  {data[i][3] = "2";}
		if(data[i][3] == "Wednesday"){data[i][3] = "3";}
		if(data[i][3] == "Thursday") {data[i][3] = "4";}
		if(data[i][3] == "Friday")   {data[i][3] = "5";}
		if(data[i][3] == "Saturday") {data[i][3] = "6";}
	}


	//Organize time into 30-minute intervals
	// Example: 17:42 -> {17, 42} -> {17, 30} -> {17, 50} -> 1750 -> 35
	for(int i=0; i<data.size(); i++)
	{
		//Split time by hour and minute component
		vector<string> hourMinute = tokenize(data[i][4], ':');
		//Round minute down to 30 minute interval
		hourMinute[1] = std::to_string((atoi(hourMinute[1].c_str())/30)*30);
		//If minute is "30", convert to "50"
		if(hourMinute[1][0] == '3'){hourMinute[1][0] = '5';}
		//If minute is "0", convert to "00"
		else{hourMinute[1] = hourMinute[1] + "0";}
		//Concatenate hour and minute components
		data[i][4] = hourMinute[0]+hourMinute[1];
		//Divide time as an integer by 50
		data[i][4] = std::to_string(atoi(data[i][4].c_str())/50);
	}

	cout << "Done" << endl;
}



/* count_customers() - Count the number of customers in the cafe at each time
 *		       segment, split by demographic
 *
 * Description: Counts the number of customer transactions that occur in each
 *	time segment in the included data, broken up by demographics, and
 *	generates a new table from these counts.
 *
 * Input:
 *	data - A 2D vector representing the optimized data table. Expected to be
 *	       formatted according to age ID, gender ID, date, day ID, and time
 *	       segment ID, in that order.
 *
 * Output: Returns a 2D vector storing a table of the number of customer
 *	transactions that took place at each time segment in the input data.
 *	each row will hold the date, the day ID, the time segment ID, and
 *	the number of total customers, males, females, children, young adults,
 *	adults, and seniors in the cafe during that time segment, in that order.
 */
vector<vector<string>> count_customers(vector<vector<string>> data)
{
	vector<vector<string>> analyzed; //Stores the analyzed data
	//vector<string> currentTimeData(8); //Holds the data for a single row of
					   // analyzed data
	string currentDate = data[0][2]; //Stores the currently analyzed date
	string currentTime = data[0][4]; //Stores the currently analyzed time
	vector<int> demoCounts(7, 0); //Tracks number of customers of each
				      // demographic in current time segment;
				      // ordered total, male, female, child,
				      // young adult, adult, and senior

	cout << "Analyzing data...";

	//For every row in data table
	for(int i=0; i < data.size(); i++)
	{
		//If row contains pertains to a new time segment
		if(data[i][2] != currentDate || data[i][4] != currentTime)
		{
			vector<string> newRow (10); //Holds analyzed data for a
						    // single time segment

			//Add previous time segment's data to analyed table
			newRow[0] = currentDate;  //Date
			newRow[1] = data[i-1][3]; //Day number
			newRow[2] = currentTime;  //Time segment number
			//Include demographics counts
			for(int j=0; j<demoCounts.size(); j++)
			{
				newRow[j+3] = std::to_string(demoCounts[j]);
			}
			analyzed.push_back(newRow);
					
			//Update current date and time
			currentDate = data[i][2];
			currentTime = data[i][4];

			//Reset current demographics counts
			for(int j=0; j<demoCounts.size(); j++)
			{
				demoCounts[j] = 0;
			}
		}//END if new time segment

		//Increment the appropriate demographic counts
		demoCounts[0] += 1; //Total
		if(data[i][1] == "1") {demoCounts[1] += 1;} //Male
		if(data[i][1] == "2") {demoCounts[2] += 1;} //Female
		if(data[i][0] == "1") {demoCounts[3] += 1;} //Child
		if(data[i][0] == "2") {demoCounts[4] += 1;} //Young
		if(data[i][0] == "3") {demoCounts[5] += 1;} //Adult
		if(data[i][0] == "4") {demoCounts[6] += 1;} //Senior

	}//END for every row in data table

	cout << "Done" << endl;

	return analyzed;
}



//TODO: Needs update
/*
string convert_time_segment(string sTime)
{
	//Convert to int
	int iTime = atoi(sTime.c_str());

	//Check that timeSlice is 0-47
	if(iTime < 0 or iTime > 47)
	{
		cout << "Internal Error: Attempt to convert invalid time slice"
		     << endl;
		return "";
	}

	//Declare the initial values of the boundaries of the time slice
	string timeSliceStart = std::to_string(iTimeSlice*50);
	string timeSliceEnd = std::to_string((iTimeSlice+1)*50);

	//Ensure four-digit numbers
	if(timeSliceStart.length() == 3){
		timeSliceStart = '0' + timeSliceStart;
	}
	if(timeSliceEnd.length() == 3){
		timeSliceEnd = '0' + timeSliceEnd;
	}

	//Convert any 50 to 30 (eg: 550 -> 530)
	if(timeSliceStart[2] == '5')
	{
		timeSliceStart[2] = '3';
	}
	else
	{
		timeSliceEnd[2] = '3';
	}

	//Concatenate the start and end times to define the time slice
	return timeSliceStart + '-' + timeSliceEnd;
}
*/


/* create_CSV() - Create a CSV file from a data table
 *
 * Description: Creates a CSV file from a given data table, with rows seperated
 *	by line breaks, and entries in a row separated by commas. The user must
 *	specify the name of the CSV file to be created.
 *
 * Input: The user is asked to enter the name of the file to be created. The
 *	function takes the following parameters:
 *
 *	data - A 2D vector representing the data table to be written as a CSV
 *
 * Output: A CSV file is created under the name specified by the user,
 *	containing the data found in the given data table.
 */
void create_CSV(vector<vector<string>> data)
{
	//Get the filename from the user
	string filename;
	cout << "Generating a CSV from collected data..." << endl;
	cout << "   Please name the file: ";
	cin >> filename;

	//Open the file for writing
	ofstream outputFile;
	outputFile.open(filename);

	//For every row in the data table
	for(int i=0; i<data.size(); i++)
	{
		//For all but the last entry in the row
		for(int j=0; j<data[i].size()-1; j++)
		{
			//Write the entry followed by a comma
			outputFile << data[i][j] << ",";
		}
		//Write the last entry in the row followed by a line break
		outputFile << data[i][data[i].size()-1] << endl;
	}
	//Close the output file
	outputFile.close();

	cout << "   " << filename << " created" << endl;
}


//TODO: Complete
void generate_count_plot(vector<vector<string>> counts)
{
	vector<vector<string>> plotData; //Stores the data table to be printed
	vector<string> currentRow (12); //Stores a single row of data at a time

	//Write column headers
	currentRow = {"Date","Day","Day ID","Time","Time ID","Total Customers",
		"Males","Females","Children","Young Adults","Adults","Seniors"};
	plotData.push_back(currentRow);

	//Reset current row data
	for(int i=0; i<currentRow.size(); i++)
	{
		currentRow[i] = "";
	}

	//For each row in counts
		//construct row from data in counts
		//push to plotData
		//Reset current row data

	//Pass plotData to create_CSV()
}



//TODO: Now non-functional; complete rehaul
//      Complete documentation
void generate_average_plot(vector<vector<string>> data)
{
	ofstream plotFile;
	plotFile.open("avgPlotData.csv");
	
	vector<int> dayCounts(7);
	vector<vector<int>> dayTotals(7, vector<int>(6));
	vector<vector<int>> timeTotals(48, vector<int>(6));
	vector<vector<int>> daytimeTotals(336, vector<int>(6));
	int days = 0;
	string date;

	//For all lines of data in the data structure
	for(int i=0; i<data.size(); i++)
	{
		//Add day to count when next day is reached
		if(data[i][4] != date)
		{
			days += 1;
			date = data[i][4];
			//Increment count of particular day
			dayCounts[atoi(data[i][6].c_str())] += 1; 
		}

		//Increment demographic counts
		if(data[i][0] == "1"){
			dayTotals[atoi(data[i][6].c_str())][0] += 1;
			timeTotals[atoi(data[i][5].c_str())][0] += 1;
		}
		if(data[i][0] == "2"){
			dayTotals[atoi(data[i][6].c_str())][1] += 1;
			timeTotals[atoi(data[i][5].c_str())][1] += 1;
		}
		if(data[i][0] == "3"){
			dayTotals[atoi(data[i][6].c_str())][2] += 1;
			timeTotals[atoi(data[i][5].c_str())][2] += 1;
		}
		if(data[i][0] == "4"){
			dayTotals[atoi(data[i][6].c_str())][3] += 1;
			timeTotals[atoi(data[i][5].c_str())][3] += 1;
		}
		if(data[i][1] == "1"){
			dayTotals[atoi(data[i][6].c_str())][4] += 1;
			timeTotals[atoi(data[i][5].c_str())][4] += 1;
		}
		if(data[i][1] == "2"){
			dayTotals[atoi(data[i][6].c_str())][5] += 1;
			timeTotals[atoi(data[i][5].c_str())][5] += 1;
		}
	}
	
	vector<vector<double>> dayAvgs(7, vector<double>(6));
	vector<vector<double>> timeAvgs(48, vector<double>(6));
	
	for(int i=0; i<dayAvgs.size(); i++)
	{
		for(int j=0; j<dayAvgs[i].size(); j++)
		{
			dayAvgs[i][j] = double(dayTotals[i][j])/double(dayCounts[i]);
		}
	}
	for(int i=0; i<timeAvgs.size(); i++)
	{
		for(int j=0; j<timeAvgs[i].size(); j++)
		{
			cout << timeTotals[i][j] << " / " << days;
			timeAvgs[i][j] = double(timeTotals[i][j])/double(days);
			cout << " = " << timeAvgs[i][j] << endl;
		}
	}

	//Write data
	for(int i=0; i<dayAvgs.size(); i++)
	{
		for(int j=0; j<dayAvgs[i].size()-1; j++)
		{
			plotFile << dayAvgs[i][j] << ",";
		}
		plotFile << dayAvgs[i][dayAvgs[i].size()-1] << endl;
	}
	for(int i=0; i<timeAvgs.size(); i++)
	{
		for(int j=0; j<timeAvgs[i].size()-1; j++)
		{
			plotFile << timeAvgs[i][j] << ",";
		}
		plotFile << timeAvgs[i][timeAvgs[i].size()-1] << endl;
	}
	
	plotFile.close();
}



//TODO: Function prolog
int main(int argv, char *argc[])
{
	string POSfilename = ""; //The name of the point of sale data file
	vector<vector<string>> data; //2D vector representing data table
	
	//Get the file containing point of sale data
	cout << "Please specify the data file containing point of sale data: ";
	cin >> POSfilename;

	//Attempt to open data file
	ifstream dataFile;
	dataFile.open(POSfilename);

	//Terminate if file not found
	if (dataFile.fail())
	{
		cout << POSfilename << " not found" << endl;
		return 1;
	}
	else
	{
		cout << POSfilename << " opened" << endl;
	}

	//Get the relevant data from the data file
	data = extract_data(dataFile);
	dataFile.close();

	//If there was an error with the data, terminate
	if(data.empty()){return 2;}

	//Optimize the data for analysis
	optimize_data(data);

	//Get a table of the number of customers in the cafe for each time
	// segment in data table
	vector<vector<string>> counts = count_customers(data);

	//Generate a CSV file for customer population counts
	//generate_count_plot(counts);
	//Not complete yet

	//Generate a CSV file for average customer populations
	//generate_average_plot(counts);
	//Needs rehaul

	return 0;
}
