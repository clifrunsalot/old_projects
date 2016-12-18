#include <iostream>
#include <ctime>
#include <iomanip>
#include <vector>
#include <string>

using namespace std;

enum SnapshotBufferPolicyType {ONESHOT, CIRCULAR};

class DateType
{
	public:
		DateType(clock_t c)
		{
			time_t rawtime;
			seconds = time(&rawtime);
			setMicroSeconds(c);
			struct tm * timeinfo = localtime(&rawtime);
			currentLocalDateTimeGroup = asctime(timeinfo);
		}
		
		~DateType(){;}
		
		clock_t getMicroSeconds() const
		{
			return microSeconds;
		}
		
		void setMicroSeconds(clock_t ms)
		{
			microSeconds = ms;
		}
		
		unsigned long getSeconds() const
		{
			cout << seconds << endl;
			return seconds;
		}
		
		string getLDTG() const
		{
			return currentLocalDateTimeGroup;
		}
		
	private:
		string currentLocalDateTimeGroup;
		unsigned long seconds;
		clock_t microSeconds;
};

struct MeasureType
{
	public:
		string planName;
		string measurementPointName;
		int measurementPointType;
		DateType measureDate;
		string ownerModuleID;
		int collectedData;
};

class MeasurementSnapshotType
{
	public:
		string getID();
		SnapshotBufferPolicyType getBufferPolicy();
		void setBufferPolicy(SnapshotBufferPolicyType p);
		MeasureType * getMeasures();
		MeasureType * getPlanMeasures(string planName);
		void removeAllMeasures();
		void removeAllPlanMeasures(string planName);
		int getLostSamplesNumber();
		
	private:
		MeasureType * measures;
};

struct ConfigPropertiesType
{
	string id;
	string value;
};	

class PropertySetType
{
	public:
		void configure(ConfigPropertiesType * properties);
		ConfigPropertiesType * query();
};

class MeasurementPointType
{
	public:
		string getPointName();
		void activateDeferredMeasureCapture(int waitingDelay, long duration);
		void activateMeasureCapture();
		void deactivateMeasureCapture();
		MeasurementSnapshotType getStorage();
		void setStorage(MeasurementSnapshotType storage);
		string getPlanName();
		void setPlanName(string planName);
};

class LocalMeasurementSnapshotType
{
	public:
		void recordMeasure(	string planName, 
									string measurementPointName, 
									int measurementPointType,
									long measurementDate,
									string ownerModuleID,
									int collectedData);
};

class MeasurementPointSequenceType
{
	
};

class MeasurementSnapshotSequenceType
{
	
};

class MeasurementPlanType
{
	public:
		string getPlanName();
		string getFileStorageName();
		void eraseFileStorage();
		void activatePlan();
		void deactivatePlan();
		void activateDeferredPlan(	int waitingDelay,
											long duration);
		MeasurementPointSequenceType getMeasurementPoints();
		void addMeasurementPoint(	MeasurementPointType mp,
											long deltaTime); 
		void removeMeasurementPoint(MeasurementPointType mp);
		MeasurementSnapshotSequenceType getMeasurementSnapshots();
	private:
		MeasurementPointSequenceType measurementPoints;
		MeasurementSnapshotSequenceType measurementSnapshots;
};
	
class LocalTimeMPType
{
	public:
		void signalStartProcessing();
		void signalEndProcessing();
};

class LocalOperatingSystemType
{
	public:
		long getFreeMemoryAmount();
	private:
		unsigned long period;
};

class LocalFIFOType
{
	public:
		int size();
	private:
		unsigned long period;
};

int main()
{
	cout << endl << "Starting runMP" << endl << "******************" << endl << endl;
	
	vector<DateType> dateVector;
	clock_t interval;
	int timeOut = 0;
	interval = clock();
	
	cout << "start clock " << interval << endl;
	
	for(int i=0; i<(sizeof(secsToWait)/sizeof(int)); i++)
	{
		for(int j=0; j<secsToWait[i]*1000000; j++)
		{
		}
		interval = clock();
		DateType temp(interval);
		dateVector.push_back(temp);
		cout << "ms: " << i << " = " << temp.getMicroSeconds() << endl;
	}

	for(int k=0; k<dateVector.size(); k++)
	{
		cout <<	"Element #" << k << ": " << dateVector[k].getLDTG() << endl;
				
		cout	<< "dateVector[" << k << "].getMicroSeconds(): " << dateVector[k].getMicroSeconds() << endl;
		cout	<< "dateVector[" << k - 1 << "].getMicroSeconds(): " << dateVector[k-1].getMicroSeconds() << endl;
		cout	<< dateVector[k].getMicroSeconds() - dateVector[k-1].getMicroSeconds() << " ms " << endl;
	}

	interval = clock();
	cout << "end clock " << interval << endl;
		
	return 0;
}

	



