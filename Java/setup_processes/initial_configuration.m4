`-------------------------------------------------------------------------------
-------------------------------------------------------------------------------
--| @COPYRIGHT (c) Copyright 1998-2000, 2001, 2002, 2003 Raytheon Company
--|                (Unpublished Copyright), All Rights Reserved
--
--			     OPFAC CONFIGURATION FILE
--
-------------------------------------------------------------------------------
-------------------------------------------------------------------------------
--
--  The following format is used to specify the OPFAC configuration.
--  The same file format is used for the initial configuration as for the 
--  application configuration.
--
--  The application configurations are kept in a separate file for each 
--  OPFAC configuration.  The file name is used to distinguish what the 
--  OPFAC configuration applies to.  For example: operational_fu.2 is used
--  for a 2 workstation OPFAC in the Operational_Fu unit role.  Note that 
--  all configuration files should be lower case characters.
--
--
--  The file format consists of four types of entries.  The entries can be in 
--  any order.  The four entry types are:
--
--	WORKSTATION_NUMBER
--	PROCESS
--	DATABASE
--	EVENT
--
--
--  Processes are configured and started according to their assigned run 
--  level.  When the proper conditions are achieved, a run level is 
--  activated and the corresponding processes will be executed.  The 
--  necessary conditions are one of the following:
--
--	The processes in the previous run level have initialized.  
--	OR
--	The event for a run level has occurred.  
--
--
-------------------------------------------------------------------------------
--
--				    WORKSTATION 
--
-------------------------------------------------------------------------------
--
--	WORKSTATION_NUMBER		= Workstation number.
--		WORKSTATION_PRIORITY	= Priority between workstations.
--
--  Valid workstation numbers are from 1 to 11.
--
--  Each workstation has an assigned workstation number.  The number does not 
--  indicate anything about the workstation itself, it is only used as an 
--  arbitrary indication of one workstation with respect to another 
--  workstation.  The number does not indicate physical ordering of the 
--  workstations or have any relevance to the order in which the workstations 
--  were started.
--
--  Each workstation is assigned a priority number to indicate the order of 
--  importance for the workstation in relation to the other workstations.  The 
--  lower the number, the higher the priority.  If the workstation priorities 
--  are not defined, the workstation assignments will be assigned arbitrarily.
--
--  The processes and databases are assigned according to the workstation 
--  priority.  The process and database configuration that best fits the 
--  workstation with priority 1 will be assigned first.  The configuration for
--  the workstation with priority 2 will be assigned next.  The same method
--  is used until the configurations for all workstations are assigned.
--
--  Each workstation priority value can only be assigned to one workstation.
--
--  The workstation priority should not need to be set in the 
--  initial_configuration file.  (Workstation priority has no meaning for 
--  the initial_configuration file)
--
--
-------------------------------------------------------------------------------
--
--				      EVENTS
--
------------------------------------------------------------------------------- 
--
--  Each event entry should contain the following information:
--
--	EVENT				= Event name (See Event Names for
--					  Configuration Events).  
--					  This must be supplied
--
--		RUN_LEVEL		= Priority level assigned to this
--					  event.  Integer between 1 and 64.
--					  This must be supplied.
--
--
--  Event Names for Configuration Events
--	
--	INITIALIZATION			- The first configuration event.  
--					  Occurs at start-up time of the 
--
--	X_INITIALIZED			- X system is running.  Allows for the 
--					  starting of processes that depend 
--					  on the window system to be up and 
--					  running.
--
--	INITIAL_DB_CONFIGURED		- All the databases specified in the 
--					  configuration file for initial
--					  processes are available for use.
--
--	DB_CONFIGURED			- All of the databases are configured.
--
--
--
--  Configuration Event Names for process entry use.
--
--  When each of the following events has been received, the
--  associated activities have occurred:
--	
--	NONE				- The default event. Not meant to be
--					  actually set in the file.
--
--	NM_INITIALIZED			- Communication has been established.
--					  Allows for the initialization of the
--					  workstation and for workstation
--					  arbitration to determine a master.
--
--	HI_INITIALIZED			- HI is running.  Allows for the 
--					  starting of processes that depend 
--					  on the HI interface.
--
--	HI_DIED				- The HI death event.
--
--      WAIT_FOR_HI_STARTED		- Optimization to allow HI to be 
--					  started earlier.  When this event
--					  occurs it will allow transition to
--					  a new run-level.
--
--	USER_ACCESS_DIED		- The death event associated with 
--					  the Ss_User_Access process.
--
--	USER_LOGGED_IN			- A user has logged into the OPFAC.  
--					  Causes: Master workstation to
--					  be placed where the operator logged
--					  in; All other workstations login
--					  disabled; Version numbers to be 
--					  compared; Last saved configuration
--					  to be used; Processes and databases
--					  that depend on the Unit Administrator
--					  to be configured and started.
--
--	READY_FOR_CONFIGURATION		- All the processes necessary for 
--					  configuration have started.
--
--	CONFIGURATION_ACTIVATED		- The UA wants the OPFAC configuration
--					  activated.  The configuration is 
--					  determined and distributed throughout
--					  the OPFAC.  The first set of 
--					  processes and databases are  
--					  configured and started.
--
--	ACTIVATION_COMPLETE		- All application process entries 
--					  have completed initialization and
--					  all database configuration has 
--					  been completed.  The OPFAC is 
--					  ready for use.  This event may
--					  be used to initiate an alert to the
--					  operator that the OPFAC is activated.
--					  The OPFAC is considered activated 
--					  (and hence the alert is posted) when
--					  the process entry that uses this 
--					  event in the Initialization_Event
--					  field checks in.
--
--	LOG_INITIALIZED			- The Log database manager has 
--					  completed initialization.
--
--	START_MTS			- The gateway process is to be started.
--
--	START_EPLRS			- The gateway process is to be started.
--
--	START_TACFIRE			- The gateway process is to be started.
--
--	START_NATO			- The gateway process is to be started.
--
--	START_ATCCS			- The gateway process is to be started.
--
--	MTS_STARTED			- The gateway has been started.
--
--	EPLRS_STARTED			- The gateway has been started.
--
--	TACFIRE_STARTED			- The gateway has been started.
--
--	NATO_STARTED			- The gateway has been started.
--
--	ATCCS_STARTED			- The gateway has been started.
--
--      ELAN_ENABLED			- An Elan channel has been enabled.
--
--      EXTERNAL_COMM_ENABLED		- Used to signal the need for 
--					  various communication processes
--					  such as relays and 
--                                        forward_external_system_message.
--
--      Spare_1				- Spare event.
--
--      Spare_2				- Spare event.
--
--      Spare_3				- Spare event.
--
--      Spare_No_Run_Level_1		- Spare event.
--
--      Spare_No_Run_Level_2		- Spare event.
--
--      Spare_No_Run_Level_3		- Spare event.
--
--
--
-------------------------------------------------------------------------------
--
--				      PROCESS
--
------------------------------------------------------------------------------- 
--
--  Each process entry contains the following information.  The format for each 
--  process entry is as follows:
--
--	PROCESS				= Process name.  This must be supplied.
--					  (i.e. EXECUTABLES:manage_comm_network)
--
--		TYPE			= Denotes what type of process this is
--					  (see Process Type).  If not supplied, 
--					  defaults to AFATDS.
--
--		CONFIGURATION_EVENT	= Certain types of process entries are 
--					  outside of the normal run-level type
--					  of process entries.  These non-run-level
--					  types of processes are started by a 
--					  configuration event occuring.  The 
--					  CONFIGURATION_EVENT entry is the 
--					  configuration event that when it occurs
--					  will cause this non-run-level type of 
--					  process to be started.  If not supplied, 
--					  defaults to NONE.
--
--              REAL_TIME_PRIORITY      = The real time priority to be assigned
--                                        to this process.  Unlike time sharing
--                                        priorities, real time priorities do
--                                        not change over the life of the process.
--                                        Real time priorities are an additional
--                                        tool to be used in optimizing system
--                                        performance.  If this entry does not
--                                        exist, then normal time sharing priorities
--                                        are used.  Valid range 0 .. 127, with
--                                        lower numbers being higher priority.
--                                        This entry is expected to be used only
--                                        for extremely critical processes such
--                                        as pex and nm.
--
--		NICE_VECTOR		= The UNIX nice vector to be associated
--					  with this process entry.  A nice 
--					  vector is in the range of 0 .. 39 the
--					  lower the number the higher the 
--					  priority.  If not supplied, defaults
--					  to 20 (20 is also the default UNIX
--					  nice vector.)
--
--		RUN_LEVEL		= Run level associated with this process
--					  entry. The process is started when the
--					  unit administrator has advanced to 
--					  this level. Integer between 1 and 64.
--					  If not supplied, defaults to 64.
--
--		WORKSTATION_FAILURE	= What the system should do if the 
--					  workstation that this process entry
--					  is running on should die. (see
--					  Workstation Failure ).  If not 
--					  supplied, defaults to DO_NOT_RESTART.
--
--		FAILURE_INSTRUCTION	= What the system should do if the 
--					  process terminates abnormally (see 
--					  Failure Instructions).  If not 
--					  supplied, defaults to DO_NOT_RESTART.
--
--		DEATH_EVENT		= Configuration event that will be 
--					  actioned if this process entry dies.
--					  If not supplied, defaults to NONE.
--
--		OPERATOR_ROLE		= A role that this process supports 
--					  (see Operator Roles).  If none are 
--					  supplied, defaults to NONE. 
--
--		WORKSTATION		= Workstation that the process should 
--					  be executed upon (see Workstation 
--					  Selection).  If not supplied, 
--					  defaults to ALL.
--
--		CHECK_IN_INSTRUCTION	= Currently all AFATDS processes are
--					  required to make calls to the Ss_Uis
--					  _Information procedures Initialization
--					  _Complete and Attachments_Complete.
--					  The Initialization_Complete call is
--					  used to determine when a process 
--					  entry has initialized itself 
--					  sufficiently to start additional
--					  processes. Applications desiring to
--					  delay starting of additional 
--					  processes until their application 
--					  checks in with the Attachments_
--					  Complete call may do so by making 
--					  this token equal to Attachments_
--					  Complete. (see Check_In_Instructions
--					  below.) Defaults to Initialization_
--					  Complete.
--
--		INITIALIZATION_EVENT	= Configuration event that occurs when
--					  this process entry initializes the 
--					  first time.  If not supplied, 
--					  defaults to NONE.
--
--		RESTART_EVENT		= Configuration event that occurs when
--					  this process entry initializes after 
--					  being restarted.  If not supplied, 
--					  defaults to NONE.
--
--		HEARTBEAT_REQUIRED	= Boolean value that determines whether
--					  or not heartbeat monitoring is 
--					  required for this process entry.
--					  If not supplied, defaults to FALSE.
--
-----------------------------------------------------------------------------
--
--  Process Type - The following are the various types of processes:
--
--	AFATDS				- An AFATDS process.
--      NON_ADA                         - An Non-Ada AFATDS process.  Only one
--                                      - allowd at this time.
--	SYSTEM				- A support process that is part of
--					  the workstation configuration.
--	NO_RUN_LEVEL			- An AFATDS process that is not in
--					  the normal run-level system.
--					  (i.e. Gateways)
--
-----------------------------------------------------------------------------
--
--  Workstation Failure   - The following choices are available as alternatives 
--			    when a workstation dies or is killed because a 
--			    critical process has died.
--
--	DO_NOT_RESTART		- If the workstation dies the process is not
--				  restarted on a new workstation. (The process
--				  is common to all workstations or it is not
--				  important to restart this process entry.)
--
--	RESTART_ON_DIFFERENT	- Restarts the process on a new workstation. 
--				  The new workstation is determined by the 
--				  system.
--
-----------------------------------------------------------------------------
--
--  Failure Instructions  - The following choices are available as alternatives 
--			    when a process terminates abnormally:
--
--	RESTART_IMMEDIATELY		Restarts the process on the same
--					workstation.
--
--		Even though a process may be restarted, there is a built in
--		"sanity check" for the resart of processes.  If a process
--		entry is restarted a configurable number of times, (currently 4),
--		and then it still dies, the workstation will be shutdown because 
--		there must exist additional problems in the OPFAC (or more 
--		likely on this workstation) that need to be corrected.
--		The RESTART_COUNT option allows processes to have the 
--		RESTART_IMMEDIATELY failure instruction while at the same
--		time modifying the default value for process restarts 
--		before the workstation is shutdown.
--
--	RESTART_COUNT N			Restarts the process on the same 
--					workstation n number of times.
--					If it terminates abnormally after 
--					n times, then the workstation is 
--					shutdown.  
--
--	DO_NOT_RESTART			If the process dies it is not restarted.
--
--	SHUTDOWN_WORKSTATION		If the process entry is a critical 
--					process and it dies, then the workstation
--				 	is shutdown immediately.
--					A critical process is one that is so 
--					basic to the operation of the workstation
--					that it cannot be restarted.
--
-----------------------------------------------------------------------------
--
--  Operator Roles - A process may have multiple Operator_Role entry 
--		     assignments.  The following choices are available as 
--		     operator roles that the process is associated with:
--
--	NONE
--	MESSAGE_MONITOR
--	MISSION_MONITOR
--
-----------------------------------------------------------------------------
--
--  Workstation Selection - The following choices are available as process 
--			    location identifiers:
--
--	N				- A workstation number.  This choice is 
--					  not available for the initial 
--					  processes, they must use one of the 
--					  other workstation selection options.
--	UNIT_ADMINISTRATOR		- The process will be started on the 
--					  workstation that originally has the
--					  System_Administrator assignment. 
--					  This is the one machine in the 
--					  OPFAC that is logged into first. If
--					  the assignment migrates the process 
--					  will NOT migrate unless the work-
--					  station dies. A process cannot be 
--					  designated for the UA workstation 
--					  if it is to be started prior to the 
--					  run level associated with the 
--					  operator login event.
--	ALL				- The process will be started on every 
--					  workstation. (This is the default.)
--
-----------------------------------------------------------------------------
--
--  Check_In_Instructions  - Defer the starting of other process entries
--			     following call to Ss_Uis_Information.  
--
--	INITIALIZATION_COMPLETE	-  Check-in processing is done when 
--				   the process checks in with the 
--				   Initialization_Complete call. This 
--				   is the default.
--	ATTACHMENTS_COMPLETE	-  Check-in processing is delayed until 
--				   this process checks in with the 
--				   Attachments_Complete call. 
--
-----------------------------------------------------------------------------
--
--  Initialization Event - (see EVENTS)
--
--  Restart Event - (see EVENTS)
--
--  Death Event - (see EVENTS)
--
--
-------------------------------------------------------------------------------
--
--				      DATABASE
--
------------------------------------------------------------------------------- 
--
--  Each database entry contains the following information.  A database
--  entry should not be repeated in the configuration file (no entries should
--  have the same database name).  The format for each database is as follows:
--
--	DATABASE			= Database name.  This must be 
--					  supplied.
--		OPERATOR_ROLE		= (Optional) A role that this database 
--					  supports (see Operator Roles).  
--					  If not supplied, defaults to NONE. 
--		MASTER_WORKSTATION	= Workstation where the master database 
--					  should be located (see Workstation 
--					  Selection).  This must be supplied.
--		SHADOW_WORKSTATION	= Suggested workstation where the 
--					  shadow database should be located 
--					  (see Workstation Selection).  The 
--					  shadow database will only be located 
--					  where the configuration file 
--					  specifies if there are no 
--					  workstations specified by the 
--					  operator as removable.  If not 
--					  supplied, defaults to NONE.
--
-----------------------------------------------------------------------------
--
--	Operator Roles - A database may have multiple Operator_Role entry
--                   assignments.  The following choices are available as
--                   operator roles that the database is associated with:
--
--      SYSTEM_ADMINISTRATION
--      COMM_ADMINISTRATION
--      MISSION_MONITOR
--      MESSAGE_MONITOR
--
-----------------------------------------------------------------------------
--
--  Workstation Selection - The following choices are available as database 
--			    location identifiers:
--
--	N				- A workstation number.  This choice is 
--					  not available for the initial 
--					  databases, they must use one of the 
--					  other workstation selection options.
--	UNIT_ADMINISTRATOR		- The database will be located on the 
--					  workstation of the Unit 
--					  Administrator.
--	SYSTEM_SELECTED			- The database location will be 
--					  determined by the AFATDS software.
--					  This option is only for use on 
--					  shadow databases.
--	NONE				- The database will not be used.  This 
--					  option is only for use on shadow 
--					  databases.
--
--
-------------------------------------------------------------------------------
-------------------------------------------------------------------------------
--
--		        INITIAL OPFAC CONFIGURATION FILE
--
--	Version Bld3-3-F2
--
--	This version should not be used with Unit Administrators below 
--	version Bld3-3-F2
--
-------------------------------------------------------------------------------
-------------------------------------------------------------------------------
'define(DOES_RT,ifelse(TYPE,`SUN',`YES',TYPE,`PA_RISC',`YES',`NO')) `
	
	EVENT				= Initialization
		RUN_LEVEL		= 1
		

--	PROCESS				= EXECUTABLES:manage_tactical_subnet
--		NICE_VECTOR		= 13
--		RUN_LEVEL		= 1
--		Type			= Afatds
--		FAILURE_INSTRUCTION	= Restart_Count 12
--		HEARTBEAT_REQUIRED	= True


--        PROCESS                         = EXECUTABLES:manage_network_router
--                NICE_VECTOR             = 13
--                RUN_LEVEL               = 1
--                Type                    = Afatds
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                HEARTBEAT_REQUIRED      = True


--'ifelse(TYPE,`PA_RISC',`        PROCESS                         = EXECUTABLES:reset_lan
--                NICE_VECTOR             = 11
--                Type                    = Afatds
--                RUN_LEVEL               = 1
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                HEARTBEAT_REQUIRED      = True',TYPE,`LCU',`',`        PROCESS                         = EXECUTABLES:reset_lan
--                NICE_VECTOR             = 11
--                Type                    = Afatds
--                RUN_LEVEL               = 1
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                HEARTBEAT_REQUIRED      = True')`


--	PROCESS				= EXECUTABLES:transport_message
--		NICE_VECTOR		= 9
--		Type			= Afatds
--		RUN_LEVEL		= 1
--		FAILURE_INSTRUCTION	= Shutdown_Workstation
--		-- Heartbeat for transport_message should always be disabled.
--		HEARTBEAT_REQUIRED	= True


--	PROCESS				= EXECUTABLES:manage_comm_network
--		NICE_VECTOR		= 9
--		Type			= Afatds
--		RUN_LEVEL		= 1
--		FAILURE_INSTRUCTION	= Shutdown_Workstation
--		HEARTBEAT_REQUIRED	= True

		
--	PROCESS				= EXECUTABLES:release_semaphores
--		Type			= Afatds
--		RUN_LEVEL		= 'ifelse(TYPE,`SUN',`3',`2')`
--		FAILURE_INSTRUCTION	= Restart_Count 12
--		HEARTBEAT_REQUIRED	= True


--	PROCESS				= EXECUTABLES:transfer_file
--		NICE_VECTOR		= 15
--		Type			= Afatds
--		RUN_LEVEL		= 'ifelse(TYPE,`SUN',`4',`2')`
--		FAILURE_INSTRUCTION	= Restart_Count 12
--		HEARTBEAT_REQUIRED	= True


              -- Description: This process handles optical disk utilities
--        PROCESS                         = EXECUTABLES:perform_normal_disk_purge 
--        	WORKSTATION             = All
--                OPERATOR_ROLE           = None
--                RUN_LEVEL               = 'ifelse(TYPE,`SUN',`5',`2')`
--                HEARTBEAT_REQUIRED      = True
--                Type                    = Afatds
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                CONFIGURATION_EVENT     = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None


--	PROCESS				= EXECUTABLES:manage_database_environment
--		NICE_VECTOR		= 15
--		Type			= Afatds
--		RUN_LEVEL		= 'ifelse(TYPE,`SUN',`6',`3')`
--		FAILURE_INSTRUCTION	= Shutdown_Workstation
--		HEARTBEAT_REQUIRED	= True


--	PROCESS				= EXECUTABLES:send_x_window_signal
--		Type			= System
--		RUN_LEVEL		= 'ifelse(TYPE,`SUN',`7',`4')`
--		-- The send_x_window_signal should not be restarted because the 
--		-- signal handler for the X initialized signal is removed after
--		-- it is successfully handled.  If this process is restarted then
--		-- it will fire the signal again and there is no signal handler to
--		-- handle it.  This process should be made to terminate normally, 
--		-- then the process could (should) be made restartable.  With the
--		-- process not restartable, there does exist a potential for this 
--		-- process to fail before it times out and fires the X initialized
--		-- event thereby hanging the entire system, however, this has not 
--		-- yet occurred.
--		--FAILURE_INSTRUCTION	= Restart_Count 12


	EVENT				= X_Initialized
		RUN_LEVEL 		= 'ifelse(TYPE,`SUN',`8',`6')`


--	PROCESS				= EXECUTABLES:handle_user_account
--		NICE_VECTOR		= 15
--		Type			= Afatds
--		RUN_LEVEL		= 'ifelse(TYPE,`SUN',`8',`6')`
--		FAILURE_INSTRUCTION	= Restart_Count 12
--		INITIALIZATION_EVENT 	= Initiate_Arbitration
--		RESTART_EVENT 		= User_Access_Restarted
--		DEATH_EVENT		= User_Access_Died
--		HEARTBEAT_REQUIRED	= True


--	PROCESS				= EXECUTABLES:manage_unit_id_cache
--		NICE_VECTOR		= 11
--		Type			= Afatds
--		RUN_LEVEL		= 'ifelse(TYPE,`SUN',`8',`6')`
--		FAILURE_INSTRUCTION	= Restart_Count 12
--		HEARTBEAT_REQUIRED	= True

--	PROCESS				= EXECUTABLES:set_font
--		NICE_VECTOR		= 17
--		Type			= Afatds
--		RUN_LEVEL		= 'ifelse(TYPE,`SUN',`9',`7')`
--		FAILURE_INSTRUCTION	= Do_Not_Restart


	PROCESS				= COE_JMV:Cartographer
		Type			= Non_Ada
		RUN_LEVEL		= 7
		FAILURE_INSTRUCTION	= Restart_Count 12
		HEARTBEAT_REQUIRED	= False

--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`        PROCESS                         = EXECUTABLES:run_nameserv
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                 Type                    = System
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                 RUN_LEVEL               = 'ifelse(TYPE,`SUN',`10',`8')`
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                 FAILURE_INSTRUCTION     = Restart_Count 12
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                 HEARTBEAT_REQUIRED      = True


--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`        PROCESS                         = EXECUTABLES:run_aas
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                 TYPE                    = Non_Ada
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                 RUN_LEVEL               = 'ifelse(TYPE,`SUN',`10',`8')`
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                 FAILURE_INSTRUCTION     = Restart_Count 12
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                 HEARTBEAT_REQUIRED      = True

--	PROCESS				= EXECUTABLES:manage_display
--		Type			= Afatds
--		NICE_VECTOR		= 17
--		RUN_LEVEL		= 'ifelse(TYPE,`SUN',`10',`8')`
--		RESTART_EVENT 		= HI_Restarted
--		DEATH_EVENT		= HI_Died
--		FAILURE_INSTRUCTION	= Restart_Count 12
--		HEARTBEAT_REQUIRED	= True

--	PROCESS				= EXECUTABLES:support_printer
--		Type			= Afatds
--		RUN_LEVEL		= 'ifelse(TYPE,`SUN',`11',`9')`
--		INITIALIZATION_EVENT	= HI_Initialized
--		WORKSTATION		= All
--		FAILURE_INSTRUCTION	= Restart_Count 12
--		HEARTBEAT_REQUIRED	= True


	EVENT				= Initial_Db_Configured
		RUN_LEVEL		= 12


--	PROCESS				= EXECUTABLES:manage_unit_id_data
--		NICE_VECTOR		= 11
--		Type			= Afatds
--		RUN_LEVEL		= 12
--		WORKSTATION		= Unit_Administrator
--		INITIALIZATION_EVENT	= Ready_For_Configuration
--		RESTART_EVENT 		= Uis_Db_Server_Restarted
--		DEATH_EVENT 		= Uis_Db_Server_Died
--		FAILURE_INSTRUCTION	= Restart_Count 12
--		WORKSTATION_FAILURE	= Restart_On_Different
--		HEARTBEAT_REQUIRED	= True

		
        'ifelse(ENVTYPE,`NONFDD',`',ENVTYPE,`USMC',`',`
	-- This process is not used in the non-FDD and Marine environments.
	-- It is used in the FDD TOC, and the FDD non-TOC.
--	PROCESS                         = EXECUTABLES:manage_c2r_interface
--                NICE_VECTOR             = 11
--                Type                    = Afatds
--                RUN_LEVEL               = 12
--                WORKSTATION             = Unit_Administrator
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Restart_On_Different
--                HEARTBEAT_REQUIRED      = True')`


--	PROCESS				= EXECUTABLES:manage_network_resources
--		NICE_VECTOR		= 11
--		Type			= Afatds
--		RUN_LEVEL		= 13
--		WORKSTATION		= Unit_Administrator
--		FAILURE_INSTRUCTION	= Restart_Count 12
--		WORKSTATION_FAILURE	= Restart_On_Different
--		HEARTBEAT_REQUIRED	= True


	-- This event indicates that all databases have been placed on the
	-- appropriate workstation(s) and that the databases are ready to be 
        -- used.
	EVENT				= DB_Configured
		RUN_LEVEL		= 20


	-- Description: This process is the object manager for the Ss_Log
        -- database.
--	PROCESS				= EXECUTABLES:manage_log_data
--		WORKSTATION         	= Unit_Administrator
--		OPERATOR_ROLE		= None
--		RUN_LEVEL		= 20
--		HEARTBEAT_REQUIRED	= True
--		Type			= Afatds
--		CHECK_IN_INSTRUCTION	= Initialization_Complete
--		FAILURE_INSTRUCTION	= Restart_Count 12
--		WORKSTATION_FAILURE	= Restart_On_Different
--		CONFIGURATION_EVENT	= None
--		INITIALIZATION_EVENT	= Log_Initialized
--		RESTART_EVENT		= None
--		DEATH_EVENT		= None

				
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`        PROCESS                         = EXECUTABLES:run_guic
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                 TYPE                    = Non_Ada
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                 RUN_LEVEL               = 11
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                 FAILURE_INSTRUCTION     = Restart_Count 12
--'ifelse(TYPE,`PA_RISC',`--NOT_RISC',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                 HEARTBEAT_REQUIRED      = True


-------------------------------------------------------------------------------
--      !!!!!!!!!!!!!!!!            Netscape         !!!!!!!!!!!!!!!!!!!!!!!
-------------------------------------------------------------------------------                         

                   
         PROCESS                        = NETSCAPE:netscape             
                Type                    = System
                CONFIGURATION_EVENT     = Start_Netscape
                
-------------------------------------------------------------------------------

--	!!!!!!!!!!!!!!             DATABASES              !!!!!!!!!!!!!!!!!!!!!
-------------------------------------------------------------------------------


	DATABASE			= AFATDS
		OPERATOR_ROLE		= None
		MASTER_WORKSTATION	= Unit_Administrator
		SHADOW_WORKSTATION	= System_Selected


-------------------------------------------------------------------------------
-- !!!!!!!!!!!!!!!!   Relays and External Comm Processes   !!!!!!!!!!!!!!!!!!!!
-------------------------------------------------------------------------------


--        PROCESS                         = EXECUTABLES:relay_message
--                WORKSTATION             = Unit_Administrator
--                OPERATOR_ROLE           = None
--                HEARTBEAT_REQUIRED      = True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                CONFIGURATION_EVENT     = Enable_External_Comm
--                INITIALIZATION_EVENT    = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None

--        PROCESS                         = EXECUTABLES:forward_external_system_message
--                WORKSTATION             = Unit_Administrator
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                CONFIGURATION_EVENT     = Enable_External_Comm
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                INITIALIZATION_EVENT    = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None


--        PROCESS                         = EXECUTABLES:initiate_messages
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                CONFIGURATION_EVENT     = Enable_External_Comm
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                INITIALIZATION_EVENT    = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None


--        PROCESS                         = EXECUTABLES:receive_ping_message
--                WORKSTATION             = Unit_Administrator
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                CONFIGURATION_EVENT     = Enable_External_Comm
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                INITIALIZATION_EVENT    = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None

-------------------------------------------------------------------------------
--      !!!!!!!!!!!!!!!!       Client Processes         !!!!!!!!!!!!!!!!!!!!!!!
-------------------------------------------------------------------------------
             
--'ifelse(TYPE,`PA_RISC',`',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`         PROCESS                        = EXECUTABLES:fesmj
--'ifelse(TYPE,`PA_RISC',`',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                WORKSTATION             = One_Workstation_Only
--'ifelse(TYPE,`PA_RISC',`',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                OPERATOR_ROLE           = None
--'ifelse(TYPE,`PA_RISC',`',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                HEARTBEAT_REQUIRED      = True
--'ifelse(TYPE,`PA_RISC',`',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                TYPE                    = No_Run_Level
--'ifelse(TYPE,`PA_RISC',`',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                CHECK_IN_INSTRUCTION    = Initialization_Complete
--'ifelse(TYPE,`PA_RISC',`',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                FAILURE_INSTRUCTION     = Restart_Count 12
--'ifelse(TYPE,`PA_RISC',`',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                WORKSTATION_FAILURE     = Do_Not_Restart
--'ifelse(TYPE,`PA_RISC',`',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                CONFIGURATION_EVENT     = Start_Fesmj
--'ifelse(TYPE,`PA_RISC',`',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                INITIALIZATION_EVENT    = None
--'ifelse(TYPE,`PA_RISC',`',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                RESTART_EVENT           = None
--'ifelse(TYPE,`PA_RISC',`',TYPE,`LCU',`--Not_LCU ',TYPE,`SUN',`',`')`                DEATH_EVENT             = None

-------------------------------------------------------------------------------
--      !!!!!!!!!!!!!!!!             GATEWAYS           !!!!!!!!!!!!!!!!!!!!!!!
-------------------------------------------------------------------------------


--        PROCESS                         = EXECUTABLES:transport_tacfire_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                CONFIGURATION_EVENT     = Start_Tacfire
--                INITIALIZATION_EVENT    = Tacfire_Started
--                RESTART_EVENT           = Tacfire_Started
--                DEATH_EVENT             = None


--         PROCESS                        = EXECUTABLES:transport_mts_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                CONFIGURATION_EVENT     = Start_Mts
--                INITIALIZATION_EVENT    = MTS_Started
--                RESTART_EVENT           = MTS_Started
--                DEATH_EVENT             = None


--        PROCESS                         = EXECUTABLES:transport_nato_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                CONFIGURATION_EVENT     = Start_Nato
--                INITIALIZATION_EVENT    = Nato_Started
--                RESTART_EVENT           = Nato_Started
--                DEATH_EVENT             = None


--        PROCESS                         = EXECUTABLES:transport_eplrs_message
--                WORKSTATION             = One_Workstation_Only
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                CONFIGURATION_EVENT     = Start_Eplrs
--                INITIALIZATION_EVENT    = Eplrs_Started
--                RESTART_EVENT           = Eplrs_Started
--                DEATH_EVENT             = None


--        PROCESS                         = EXECUTABLES:transport_ctaps_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                CONFIGURATION_EVENT     = Start_Ctaps
--                INITIALIZATION_EVENT    = Ctaps_Started
--                RESTART_EVENT           = Ctaps_Started
--                DEATH_EVENT             = None

--        PROCESS                         = EXECUTABLES:transport_atccs_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                CONFIGURATION_EVENT     = Start_Atccs
--                INITIALIZATION_EVENT    = Atccs_Started
--                RESTART_EVENT           = Atccs_Started
--                DEATH_EVENT             = None

--        PROCESS                         = EXECUTABLES:transport_47001_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                CONFIGURATION_EVENT     = Start_47001
--                INITIALIZATION_EVENT    = MS_47001_Started
--                RESTART_EVENT           = MS_47001_Started
--                DEATH_EVENT             = None                

--        PROCESS                         = EXECUTABLES:segment_47001_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--                HEARTBEAT_REQUIRED      = True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                CONFIGURATION_EVENT     = Start_47001
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                INITIALIZATION_EVENT    = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None

--        PROCESS                         = EXECUTABLES:process_gun_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                CONFIGURATION_EVENT     = Start_Gdu
--                INITIALIZATION_EVENT    = Gdu_Started
--                RESTART_EVENT           = Gdu_Started
--                DEATH_EVENT             = None

--        PROCESS                         = EXECUTABLES:transport_47001_tcp_message_process
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--                HEARTBEAT_REQUIRED      = True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                CONFIGURATION_EVENT     = Start_47001
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                INITIALIZATION_EVENT    = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None

--         PROCESS                         = EXECUTABLES:manage_dns_queries
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--                TYPE                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                CONFIGURATION_EVENT     = Start_Dns_Query_Process
--                INITIALIZATION_EVENT    = Dns_Query_Process_Started
--                RESTART_EVENT           = Dns_Query_Process_Started
--                DEATH_EVENT             = None               

--        PROCESS                         = EXECUTABLES:forward_external_system_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                CONFIGURATION_EVENT     = Start_Tacfire
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                INITIALIZATION_EVENT    = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None


--        PROCESS                         = EXECUTABLES:forward_external_system_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                CONFIGURATION_EVENT     = Start_Nato
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                INITIALIZATION_EVENT    = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None


--        PROCESS                         = EXECUTABLES:forward_external_system_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                CONFIGURATION_EVENT     = Start_Mts
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                INITIALIZATION_EVENT    = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None


--        PROCESS                         = EXECUTABLES:forward_external_system_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                CONFIGURATION_EVENT     = Start_Eplrs
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                INITIALIZATION_EVENT    = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None


--        PROCESS                         = EXECUTABLES:forward_external_system_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                CONFIGURATION_EVENT     = Start_Atccs
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                INITIALIZATION_EVENT    = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None

--        PROCESS                         = EXECUTABLES:forward_external_system_message
--                WORKSTATION             = All
--                OPERATOR_ROLE           = None
--		HEARTBEAT_REQUIRED	= True
--                Type                    = No_Run_Level
--                CHECK_IN_INSTRUCTION    = Initialization_Complete
--                CONFIGURATION_EVENT     = Start_47001
--                FAILURE_INSTRUCTION     = Restart_Count 12
--                WORKSTATION_FAILURE     = Do_Not_Restart
--                INITIALIZATION_EVENT    = None
--                RESTART_EVENT           = None
--                DEATH_EVENT             = None



-- END OF CONFIGURATION FILE
'dnl
