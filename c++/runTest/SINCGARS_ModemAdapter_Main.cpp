#include "CORBAUtilities.h"
#include "NameUtilities.h"
#include "stdlib.h"

#include <stdio.h>  // for printf statement
#include <usrConfig.h>  // for taskDelay statement
#include <signal.h>

//## end module%3CC9B323DC90.includes

// SINCGARS_ModemAdapter_Main
#include "SINCGARS_ModemAdapter_Main.h"
//## begin module%3CC9B323DC90.declarations preserve=no
extern "C" {
int SINCGARSModemAdapterMain(int argc, char* argv[ ])
{
//## end module%3CC9B323DC90.declarations

//## begin module%3CC9B323DC90.additionalDeclarations preserve=yes
   // agrv parameters do not necessarily come in the order shown
   cout << "----> arrive at SINCGARSModemAdapterMain" << endl;
   for (int xInit=0 ; xInit<argc; xInit++)
   {
     //cout << "argv[0] = " << argv[0] << endl;               // "PlaceHolder" - name of new task
     //cout << "argv[1] = " << argv[1] << endl;               // "namingService" - id
     //cout << "argv[2] = " << argv[2] << endl;               // "/151.168.159.161/mr" - contextName value
     //cout << "argc = " << argc << endl;               // "/151.168.159.161/mr" - contextName value
     cout << "argv[" << xInit << "] = " << argv[xInit] << endl;               
   }
   
    CORBA::Boolean namingServiceFound = false;
    CORBA::Boolean nameBindingFound = false;
    CORBA::Boolean waveformCapabilityFound = false;
    CORBA::Boolean slotFound = false;
    CORBA::Short capability = 0;     
    CORBA::Short slotNumber = 0;     
    char * nameBinding;
    CORBA::Object_ptr obj;
    CosNaming::NamingContext_ptr namingContextPtr;
    int count;

    CORBA::Environment _env;
    SINCGARS_Modem_Adapter * SINCGARSModemAdapterPtr;
    // signal variables
    struct sigaction mySignal; 	 		// The sigaction is used to set all the details
    						// of what this process should do when a signal arrives    
     
    
    // -------------------------------------------------------------
    // start of code
    // -------------------------------------------------------------
    cout << "SINCGARSModemAdapterMain Executing" << endl;

    // -------------------------------------------------------------
    // Initialize signal variables
    // -------------------------------------------------------------
    sigemptyset(&mySignal.sa_mask);		// used to initialize mySignals mask to empty
    						// ie. defines the of signals to be blocked

    mySignal.sa_handler = signalHandler; 	// set the handler to our function
    mySignal.sa_flags = 0;			// used for child signalling only    
           
    //**************************************************************************
    // Get and initialize the ORB using CORBAUtilities
    //************************************************************************** 
    CORBA::ORB_ptr  orb = CORBAUtilities::getORB();
    PortableServer::POA_ptr rootPOA = CORBAUtilities::getPOA(orb);
 
    // activate the POA_Manager
    if (!( CORBAUtilities::activatePOA_Manager()) )
    {
       cout << "ResourceMain activate POAManager ERROR" << endl;
       return -1;
    }

    // verify that the arg count is an odd number (it should always be!)
    if (argc & 0x01)
    {
      cout << "Found " << argc << " aruguments." << endl;               // "/151.168.159.161/mr" - contextName value
      // extract the necessary properties from the argv list
      // start at index 1.  Index 0 contains the name of the function called (SINCGARSModemAdapterMain)
      for (count = 1; count < argc ; count +=2)
      {
        
        //cout << "argv[" << count << "] = " << argv[count] << endl;               
        if (!strcmp(argv[count], "NAME_BINDING"))
        {   
           cout << "SINCGARSModemAdapterMain Exec Param ID = " << argv[count] << " Exec Parm value = " << argv[count+1] << endl;  
   	   nameBinding = (argv[count+1]);
           // found the name binding paramter
           nameBindingFound = true;  
  	      
        } // end if (!strcmp...       

        else if (!strcmp(argv[count], "NAMING_CONTEXT_IOR"))
        {
           cout << "Found the NAMING_CONTEXT_IOR ID" << endl;
          // found the namingService ID


          CORBATRY
          // convert the stringified IOR to a naming context object reference
          obj =orb->string_to_object(argv[count+1]);
          CATCHANY
            obj = CORBA::Object::_nil();
           CORBAEND_CATCH_RT(0) // clear exception - return
             
             
          if (!CORBA::is_nil(obj))
          {  
            namingContextPtr = CosNaming::NamingContext::_narrow(obj);
            if (!CORBA::is_nil (namingContextPtr))
            {
              // narrowed successfully to a namingContex
              namingServiceFound = true;
            }
            else
            {
              cout << "SINCGARSModemAdapterMain ERROR Naming Context IOR narrow to NamingContext failed" << endl;  
            }
          }
          else  // obj is nil
          {
               cout << "SINCGARSModemAdapterMain ERROR Naming Context IOR when converted to object is nil" << endl;  
          }   

         // cout << "----> SINCGARSModemAdapterMain - 1" << endl;
        }
        
        else if (!strcmp(argv[count], CorbaMessageCodes::WAVEFORM_CAPABILITY_))
        {
           
           cout << "Found the WAVEFORM CAPABILITY ID" << endl;
           waveformCapabilityFound = true;
           
           // the value comes in as a string covert it to a short (atoi returns a long)
           // if the string does not contain a valid representation of a number, the atoi function returns 0
           // therefore if error, 0 modulation will be sent to the Connection Servant
           
           capability = CORBA::Short (atoi (argv[count+1]));
           if( capability == 0 && strcmp(argv[count+1], "0") )
           { 
              cout << "SINCGARSModemAdapterMain:  Capability argv param could not be converted to a short" << endl;
           }
        } // end if WAVEFORM_CAPABILITY_
        
        else if (!strcmp(argv[count], SINCGARS_Identifiers::SLOT_))
        {
           // note this will not be put into the properties sent to the Modem Adpapter!
           cout << "***********************----> SINCGARSModemAdapterMain - SLOT_" << endl;
           slotFound = true;
           slotNumber = CORBA::Short (atoi (argv[count+1]));
           cout << "***********************----> SINCGARSModemAdapterMain - SLOT_ = " << slotNumber << endl;
           if( slotNumber == 0 && strcmp(argv[count+1], "0") )
           { 
              cout << "SINCGARSModemAdapterMain:  slot argv param could not be converted to a Short a default of zero will be used" << endl;
           }
        } // end if SLOT_
        
      } // end for count
           
      if (!namingServiceFound)
      {
        cout << "SINCGARSModemAdapterMain NAMING_CONTEXT_IOR ID was NOT found!" << endl;
      }
   
      if (!nameBindingFound)
      {
        cout << "SINCGARSModemAdapterMain NAME_BINDING ID was NOT found!" << endl;
      }

      if (!waveformCapabilityFound)
      {
        cout << "SINCGARSModemAdapterMain WF Capability was NOT found!" << endl;
      }

      cout << " Check if namingService was found & waveform Capability was found " << endl;
      if (namingServiceFound && nameBindingFound && waveformCapabilityFound)
      {
         cout << " NamingService was found & waveform Capability was found " << endl;
         // the host name could be extracted from the NamingContext in the argv list
         // but for now retreive from NameUtilities
         char * hostName = NameUtilities::hostName();

         // create properties for the MA constructor
         CF::Properties maProperties;
         maProperties.length(1);
         maProperties[0].id = CorbaMessageCodes::WAVEFORM_CAPABILITY_;
         maProperties[0].value <<= (CORBA::Short)capability;
         
         CORBA::Short testTemp = 0;     
         //maProperties[0].value >>= testTemp;
         //printf("testTemp = %i\n", testTemp);
         // the ResourceNumType is used in the MA as# to form up a unique objectName.
         // Either the resourceNum is removed from the MA contstructor or
         // a unique indentifier (#) can be extracted from the last part of the contextName
         // for now can the ResourceNumType to 0. jtmarks - changed to slot number - wjlora.
         cout << "create object SINCGARS_Modem_Adapter" << endl;
         SINCGARSModemAdapterPtr = new SINCGARS_Modem_Adapter(rootPOA, slotNumber, hostName, maProperties);
     
         // activate  the object          
         cout << "create pointer to object SINCGARS_Modem_Adapter" << endl;
         CF::Resource * SINCGARSWFModeAdapterPtr = SINCGARSModemAdapterPtr->_this(); // the object ref
      
         // bind to Naming Service
         cout << "bind object to Naming Service" << endl;
         const CosNaming::Name * name = Common_CORBAUtilities::toName(nameBinding);
         CORBATRY
           namingContextPtr->bind((*name), SINCGARSWFModeAdapterPtr, _env);  
         CATCHANY
           cout << "ERROR in binding name to context" << endl;
         CORBAEND_CATCH_RT(0) // clear exception - return
         cout << "bind object to Naming Service - end" << endl;
      } 
      else  // (namingServiceFound && modulationFound) not found!
      {
        cout << "SINCGARSModemAdapterMain ERROR:  namingService or Modulation ID was NOT found!" << endl;
        return 0;
      }   
    } // end if (argc == 0x01)
    else
    {
      cout << "SINCGARSModemAdapterMain ERROR: invalid argc count = " << argc << endl;
      return 0;
    }
      
    // register the signal handler
    if (sigaction(SIGQUIT, &mySignal, NULL))
    {
      cout << "SINCGARSModemAdapterMain sigaction ERROR" << endl;        
    }
    else  // sigaction OK
    { 
      
      // block here waiting for a signal. 
      // sigsuspend temporarily replaces the process (this process) signal mask with the 
      // one pointed to by input signal set (sa_mask).  No signals are masked (blocked).
         
      sigsuspend(&mySignal.sa_mask);
      
      // continue on any received signal for now.  Should really only exit on SIGQUIT
         
      //**************************************************************************
      // Release the servant
      // Return value is an ORBexpress extension.  The return value
      // indicates if the reference count went to zero and the object
      // was consequently freed.
      //**************************************************************************  
      while ( !(SINCGARSModemAdapterPtr->_remove_ref()) )
      {
        cout << "SINCGARSModemAdapterMain Removing servant reference" << endl;
      }
         
    } //end else sigaction OK
      
    // -------------------------------------------------------------
    // Blocking event loop.  OIS has said this is nothing more than a sleep loop
    // -------------------------------------------------------------   
    //orb->run(_env);
 
    return 1;



} /* End of SINCGARSModemAdapterMain.cpp */
};	// extern "C"
//## end module%3CC9B323DC90.additionalDeclarations


//## begin module%3CC9B323DC90.epilog preserve=yes
void signalHandler (int signalNumber)
{
  cout << "SINCGARSModemAdapterMain signal Handler received signal #" << signalNumber << endl;
  
  // Do what ever is necessary to deal with the signal
  // case on the signal #, etc.
    
}

//## end module%3CC9B323DC90.epilog
