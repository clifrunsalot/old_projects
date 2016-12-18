#!/bin/bash

#uncomment to debug
#set -x

###############################################################
#                                                             #
#               Test Case Specific Functions                  #
#                                                             #
###############################################################

# Variables for capturing and comparing logs for the most current;
declare -a basic_logs
declare BASIC_LOG
declare CDLP_LOG
declare DETAIL_LOG
declare shutdown

# flag to determine state of soscoe ( 0 = true; 1 = false )
shutdown=1

##
## _clear_logs: This removes the logs used by CFPIM
##
function _clear_logs () {
	$(find /tmp -type f -maxdepth 1 | egrep -i "*ebug*" | xargs -i rm {})
	$(find /fcs/current/services/BCME/log -type f -maxdepth 1 | xargs -i rm {})
}

##
## _end_test: This kills all of the processes used by CPFIM, including soscoe
##
function _end_test () {

	shutdown=0

	$(ps -ef | egrep _monitor_soscoe | awk '{print $2}' | xargs -i kill -9 {})

	sleep 10 
	_stop_soscoe
	_stop_fcslogin
	_kill_soscoe_log
	_kill_cfpim_logs
	_kill_process_watcher
	_stop_grepper
	_stop_all_main_processes
	_stop_pa
	_stop_fbcb2
	_kill_failed_processes_xterms

	TMP_DATE=$(date +%d%m%Y_%H%M%S)
	NAME="/tmp/${SERVICE}_TC${TC_NUMBER}_${BUILD_VERSION}_${TMP_DATE}.tar.gz"
	echo "Archiving Test Results"
	cd /tmp
	tar zcvf "${NAME}" $(find /tmp -maxdepth 1 -type f  | egrep -i "soscoe_start|debug|CommsTextLog" | \
		egrep -v "gz|tar") $(find  /fcs/current -follow -type f | egrep -v "gz|tar"); 
	rm -rf /tmp/soscoe_start* /tmp/*ebug* /fcs/current/services/BCME/log/* /tmp/*CommsTextLog*

	xterm -hold -geom 60x10+300+650 -title "SOSCOE is Ready" -bg "green" -fg "black" \
		-fn -*-fixed-bold-*-*-*-*-*-*-*-*-100-iso8859-* \
		-e "echo; echo;\
		    echo \"Test is complete. All test data and logs are archived at:\";\
		    echo; echo \"${NAME}\";\
		    echo \"Close this window to terminate gracefully\";"
	
}

##
## _failed_process: This displays an information alert
##
function _failed_process () {
	xterm -title "Process not Started or has Died!" -geom 50x20+200+200 -hold -bg "red" \
		-fg "yellow" -fn -*-fixed-bold-*-*-*-*-*-*-*-*-100-iso8859-* \
		-e "echo; echo; echo; echo \"${1} HAS DIED\"; echo; echo; echo \"If the scFcsLogin service has died, \"; echo \"use the menu to restart it\"; echo; echo; echo \"If one of the server apps has died, \"; echo \"use the menu to stop and start soscoe\";"
}

##
## _kill_failed_processes_xterms: Destroys alert windows for failed processes
##
function _kill_failed_processes_xterms () {
	$(ps -ef | egrep -i "process not started" | egrep xterm | awk '{print $2}' | xargs -i kill -9 {})
}

##
## _kill_watch_processes: This kills the "Process Watcher" xterm
##
function _kill_process_watcher () {
	$(ps -ef | egrep "Process Watcher" | awk '{print $2}' | xargs -i kill -9 {})
}

##
## _kill_soscoe_log: This stops tailing the soscoe log and kills the xterm
##
function _kill_soscoe_log () {
	$(ps -ef | egrep "SOSCOE Log" | awk '{print $2}' | xargs -i kill -9 {})
}

##
## _kill_cfpim_logs: This stops tailing the Basic, Cdlp, and Detail logs and
##     their xterms
##
function _kill_cfpim_logs () {
	$(ps -ef | egrep "fcccme_cfpim_Basic_" | egrep xterm | awk '{print $2}' | xargs -i kill -9 {})
	$(ps -ef | egrep "fcccme_cfpim_Cdlp_" | egrep xterm | awk '{print $2}' | xargs -i kill -9 {})
	$(ps -ef | egrep "fcccme_cfpim_Detail_" | egrep xterm | awk '{print $2}' | xargs -i kill -9 {})
}

#function _kill_soscoe_stop_alert () {
#	$(ps -ef | egrep "Stop SOSCOE or Shutdown System" | awk '{print $2}' | xargs -i kill -9 {})
#}

##
## _monitor_logs: Displays Basic, Cdlp, and Detail Logs when they become active
##
function _monitor_logs () {
	for (( ;; ))
	do
		if [ $(egrep -i "Logging to \"Basic\" log|Logging to \"Cdlp\" log|Logging to \"Detail\" log" /tmp/soscoe_start.out | wc -l) -eq 3 ] || [ $(egrep -i "Logging to \"Basic\" log|Logging to \"Cdlp\" log|Logging to \"Detail\" log" /tmp/soscoe_start.out | wc -l) -gt 3 ]
		then
			_tail_cfpim_logs
			if [ ${shutdown} -eq 0 ]
			then
				break
			fi
			break
		fi
	done
}

##
## _monitor_soscoe_start: This tracks each process expected to be started by soscoe
##     with the following processes:
##	- scProcessManager
##	- scSystemManager
##	- scMasterSystemManager
##      - scAuthServer
##	- scDiscoverServer
##	- scFcsLogin
##	- fcccme_cfpim_main
##
function _monitor_soscoe_start () {

	SCFCSLOGIN="/BCT/ro/soscoe/coreservices/bin/i686-rhel-3-se/scFcsLogin"
	SCPROCESSMANAGER="/BCT/ro/soscoe/coreservices/bin/i686-rhel-3-se/scProcessManager"
	SCSYSTEMMANAGER="scSystemManager"
	SCDISCOVERSERVER="scDiscoveryServer"
	SCAUTHSERVER="scAuthServer"
	SCMASTERSYSTEMMANAGER="scMasterSystemManager"
	FCCCME_CFPIM_MAIN="fcccme_cfpim_main"

	fcsOn=1
	processMgrOn=1
	sysMgrOn=1
	discSvrOn=1
	authSvrOn=1
	mstrSysMgrOn=1
	cfpimOn=1

	for (( ;; )) 
	do
		if [ $(ps -ef | egrep -i "${SCFCSLOGIN}" | wc -l) -gt 0 ]
		then
			fcsOn=0
		fi

		if [ $(ps -ef | egrep -i "${SCPROCESSMANAGER}" | wc -l) -gt 0 ]
		then
			processMgrOn=0
		fi

		if [ $(ps -ef | egrep -i "${SCSYSTEMMANAGER}" | wc -l) -gt 0 ]
		then
			sysMgrOn=0
		fi

		if [ $(ps -ef | egrep -i "${SCDISCOVERSERVER}" | wc -l) -gt 0 ]
		then
			discSvrOn=0
		fi

		if [ $(ps -ef | egrep -i "${SCAUTHSERVER}" | wc -l) -gt 0 ]
		then
			authSvrOn=0
		fi

		if [ $(ps -ef | egrep -i "${SCMASTERSYSTEMMANAGER}" | wc -l) -gt 0 ]
		then
			mstrSysMgrOn=0
		fi

		if [ $(ps -ef | egrep -i "${FCCCME_CFPIM_MAIN}" | wc -l) -gt 0 ]
		then
			cfpimOn=0
		fi

		if [ $fcsOn -eq 0 ] && [ $processMgrOn -eq 0 ] && [ $sysMgrOn -eq 0 ] \
			&& [ $discSvrOn -eq 0 ] && [ $authSvrOn -eq 0 ] && [ $mstrSysMgrOn -eq 0 ] \
			&& [ $cfpimOn -eq 0 ]
		then
			xterm -hold -geom 60x10+300+700 -title "SOSCOE is Ready" -bg "green" -fg "black" \
				-fn -*-fixed-bold-*-*-*-*-*-*-*-*-100-iso8859-* \
				-e "echo \"SOSCOE is ready. Close this window and the previous one\";
				    echo \"and return to main test window\""
			break
		fi

		sleep 10
 
	done
}

##
## _monitor_soscoe: This monitors each process expected to be started by soscoe
##     with the following processes:
##	- scProcessManager
##	- scSystemManager
##	- scMasterSystemManager
##      - scAuthServer
##	- scDiscoverServer
##	- scFcsLogin
##	- fcccme_cfpim_main
##
function _monitor_soscoe () {


	SCFCSLOGIN="/BCT/ro/soscoe/coreservices/bin/i686-rhel-3-se/scFcsLogin"
	SCPROCESSMANAGER="/BCT/ro/soscoe/coreservices/bin/i686-rhel-3-se/scProcessManager"
	SCSYSTEMMANAGER="scSystemManager"
	SCDISCOVERSERVER="scDiscoveryServer"
	SCAUTHSERVER="scAuthServer"
	SCMASTERSYSTEMMANAGER="scMasterSystemManager"
	FCCCME_CFPIM_MAIN="fcccme_cfpim_main"

	for (( ; ; )) 
	do
		if [ $(ps -ef | egrep -i "${SCFCSLOGIN}" | wc -l) -lt 1 ]
		then
			_failed_process "scFcsLogin"
		fi

		if [ $(ps -ef | egrep -i "${SCPROCESSMANAGER}" | wc -l) -lt 1 ]
		then
			_failed_process "scProcessManager"
		fi

		if [ $(ps -ef | egrep -i "${SCSYSTEMMANAGER}" | wc -l) -lt 1 ]
		then
			_failed_process ${SCSYSTEMMANAGER}
		fi

		if [ $(ps -ef | egrep -i "${SCDISCOVERSERVER}" | wc -l) -lt 1 ]
		then
			_failed_process ${SCDISCOVERSERVER}
		fi

		if [ $(ps -ef | egrep -i "${SCAUTHSERVER}" | wc -l) -lt 1 ]
		then
			_failed_process ${SCAUTHSERVER}
		fi

		if [ $(ps -ef | egrep -i "${SCMASTERSYSTEMMANAGER}" | wc -l) -lt 1 ]
		then
			_failed_process ${SCMASTERSYSTEMMANAGER}
		fi

		if [ $(ps -ef | egrep -i "${FCCCME_CFPIM_MAIN}" | wc -l) -lt 1 ]
		then
			_failed_process ${FCCCME_CFPIM_MAIN}
		fi

		if [ ${shutdown} -eq 0 ]
		then
			break;
		fi

		sleep 10 
 
	done
}

##
## _monitor_shutdown: This checks the Basic log for a shutdown request, at which it would then
##  ask the operator to stop soscoe or allow it to shutdown the system.
##
function _monitor_shutdown () {
	BASIC_LOG=$(ls -1 /fcs/current/services/BCME/log/fcccme_cfpim_Basic* | tail -n 1)
	for (( ;; ))
	do
		if [ $(egrep -i "Successfully requested BCS Platform shutdown" ${BASIC_LOG} | wc -l) -gt 0 ]
		then
			echo "Calling query to stop soscoe"
			_query_stop_soscoe
			break
		fi
		sleep 2;

		if [ ${shutdown} -eq 0 ]
		then
			break
		fi
	done
}


##
## _query_stop_soscoe: This prompts the operator to enter either a y or n to stop soscoe
##  or allow it to run.
function _query_stop_soscoe () {

	xterm -title "Stop SOSCOE or Shutdown System?" -geom 60x20+200+200 -bg "yellow" -fg "red" -font -*-*-*-*-*-*-*-*-*-*-*-100-iso8859-* -e "
	for (( ;; ))\
	do\
		echo -n \"Do you want to stop SOSCOE? [y|n]:\";\
		read ans;\
		case \"\${ans}\" in
			y)
				echo \"Follow the prompts to stop SOSCOE\";\
				echo \"Press any key to continue\";\
				read -sn 1 discard;\
				sudo /sbin/service soscoe stop;\
				break;;\
			n)
				echo \"SOSCOE will be allowed to shutdown\";\
				echo \"Press any key to continue\";\
				read -sn 1 discard;\
				break;;\
			*)
				clear;\
				echo \"Invalid entry. Please select again\";\
				echo \"Press any key to continue\";\
				read -sn 1 discard;\
				;;\
		esac\
	done\
	"

}



##
## _show: This display a message
##
function _show () {
	echo;
	echo $1;
	echo;
}
##
## _start_soscoe: This uses the sudo command to start soscoe
##
function _start_soscoe () {
	_stop_soscoe
	shutdown=1
	_start_process_watcher
	echo -n "Follow the prompts to start soscoe. Press <ENTER> to continue "
	read discard
	sudo /sbin/service soscoe start
	_start_fcslogin
	_tail_soscoe_log
	xterm -hold -geom 60x10+300+550 -bg "red" -fg "yellow" \
		-title "Waiting for all SOSCOE processes to start" \
		-fn -*-fixed-bold-*-*-*-*-*-*-*-*-100-iso8859-* \
		-e "echo \"Wait until another dialog instructs you to proceed\"" &
	_monitor_soscoe_start
	sleep 5
	$(_monitor_soscoe) &
	$(_monitor_logs) &
	$(_monitor_shutdown) &
}

##
## _stop_soscoe: This uses the sudo command to stop soscoe
##
function _stop_soscoe () {
	shutdown=0
	echo -n "Follow the prompts to stop soscoe. Press <ENTER> to continue "
	read discard
	sudo /sbin/service soscoe stop 
	_kill_failed_processes_xterms
}

##
## _start_fcslogin: This uses the sudo command to start scFcsLogin
##
function _start_fcslogin () {
	if [ $(ps -ef | egrep /BCT/ro/soscoe/coreservices/bin/i686-rhel-3-se/scFcsLogin | wc -l) -eq 0 ]
	then
		echo -n "Follow the prompts to start scFcsLogin. Press <ENTER> to continue "
		read discard
		sudo /sbin/service scFcsLogin start 
	else
		# stop all except one
		count=$(ps -ef | egrep /BCT/ro/soscoe/coreservices/bin/i686-rhel-3-se/scFcsLogin | wc -l)
		for (( i=0; i<=count; i+=1 ))
		do
			sudo /sbin/service scFcsLogin stop
			sleep 2
		done
		sudo /sbin/service scFcsLogin start 
	fi
}

##
## _stop_fcslogin: This uses the sudo command to stop scFcsLogin
##
function _stop_fcslogin () {
	echo -n "Follow the prompts to stop scFcsLogin. Press <Enter> to continue"
	read discard
	sudo /sbin/service scFcsLogin stop 
}

##
## _start_pa: This starts the Platform Application Exerciser
##
function _start_pa () {
	_stop_pa
	curr_dir=`pwd`;
	cd "/fcs/current/test/BCME/bin/cdlp_exerciser/output/platform"
	/usr/X11R6/bin/xterm -title "PA Exerciser" -e "python main.py --nostates CFPIM=127.0.0.1:9100" &
	cd ${curr_dir};
}

##
## _stop_pa: This stops the Platform Application Exerciser
##
function _stop_pa () {
	ps -ef | egrep "PA Exerciser" | awk '{print $2}' | xargs -i kill -9 {}
}

##
## _start_fbcb2: This starts the FBCB2 Exerciser. The port must match that listed in the CFPIM config file.
##
function _start_fbcb2 () {
	_stop_fbcb2
	curr_dir=`pwd`;
	cd "/fcs/current/test/BCME/bin/cdlp_exerciser/output/fbcb2"
	/usr/X11R6/bin/xterm -title "FBCB2 Exerciser" -e "python main.py --nostates CFPIM=9101" &
	cd ${curr_dir};
}

##
## _stop_fbcb2: This stops the FBCB2 Exerciser
##
function _stop_fbcb2 () {
	ps -ef | egrep "FBCB2 Exerciser" | awk '{print $2}' | xargs -i kill -9 {}
}

##
## _start_grepper: This creates an xterm for the grepper script
##
function _start_grepper () {
	_stop_grepper
	echo "${TC_NUMBER} ${GREP_FILE}"
	xterm -geom 120x60+10+100 -title "Grepper" -bg "black" -fg "white" \
		-e "./grepperCFPIM.sh ${TC_NUMBER} ${GREP_FILE}" &
}

##
## _stop_fbcb2: This stops the FBCB2 Exerciser
##
function _stop_grepper () {
	ps -ef | egrep "Grepper" | awk '{print $2}' | xargs -i kill -9 {}
}

##
## _start_process_watcher: This creates an xterm to watch the key processes used by CFPIM
##
function _start_process_watcher () {
	_kill_process_watcher
	$(ps -ef | egrep "Process Watcher" | egrep xterm | xargs -i kill -9 {})
	xterm -geom 120x15+0+550 -title "Process Watcher" -e "watch 'ps -ef | \
		egrep -i \"processmanager|systemmanager|scfcslogin|cfpim|discoveryserver|authserver|vnc\" | \
		egrep -v \"grep|gvim|tail|xterm|bash\" '" &
}

##
## _start_single_main_process_1: This start a single main process.
##
function _start_single_main_process_1 () {
	curr_dir=`pwd`
	DIR="/fcs/current/test/BCME/bin/cdlp_exerciser/output/block5040"
	if [ -d "${DIR}" ]
	then
		cd ${DIR}
		xterm -iconic -e "./main 5040 -88.6 33.6 0 0 8 11 22 33 0 0" &
		xterm -hold -geom 60x10+300+650 -title "Main process" -bg "green" -fg "black" \
				-fn -*-fixed-bold-*-*-*-*-*-*-*-*-100-iso8859-* \
				-e "echo \"The main process has been started and iconified.\";\
					echo;
				    	echo \"Close this window and return to the main test menu\""

	fi
	DIR=""
	cd ${curr_dir};
}

##
## _start_main_processes_wo_time: This starts all main processes.
##
function _start_main_processes_wo_time () {
	curr_dir=`pwd`;
	bgsvcs="/fcs/current/test/BCME/bin/cdlp_exerciser/output/startBackgroundServices.sh";
	if [ -x ${bgsvcs} ]
	then
		if [ $(whoami) != "root" ]
		then
			output_dir="/fcs/current/test/BCME/bin/cdlp_exerciser/output";
			cd ${output_dir};
			eval "./startBackgroundServices.sh";
			if [ $? -ne 0 ]
			then
				_show "Failed to start BG services";
			fi
		else
			_show "You must be a non-root user to start the background services.";
		fi
	else
		_show "${bgsvcs} does not exist";
	fi
	cd ${curr_dir};
}

##
## _start_main_processes_w_time: This starts all main processes (for time test)
##
function _start_main_processes_w_time () {
	curr_dir=`pwd`;
	bgsvcs="/fcs/current/test/BCME/bin/cdlp_exerciser/output/startBackgroundServices_time.sh";
	if [ -x ${bgsvcs} ]
	then
		if [ $(whoami) != "root" ]
		then
			output_dir="/fcs/current/test/BCME/bin/cdlp_exerciser/output";
			cd ${output_dir};
#			$(cat startBackgroundServices_time.sh | sed -e 's#5040 -88.6 33.6 0 0 8 11 22 33 0 0#5040 -88.6 33.6 0 0 9 11 22 33 0 0#' > /tmp/tmp1)
#			$(mv /tmp/tmp1 ./startBackgroundServices_time.sh; chmod 777 startBackgroundServices_time.sh)
			eval "./startBackgroundServices_time.sh";
			if [ $? -ne 0 ]
			then
				_show "Failed to start BG services";
			fi
		else
			_show "You must be a non-root user to start the background services.";
		fi
	else
		_show "${bgsvcs} does not exist";
	fi
	cd ${curr_dir};
}

function _start_main_processes_w_time_tc8 () {
	curr_dir=`pwd`;
	if [ $(whoami) != "root" ]
	then
		output_dir="/fcs/current/test/BCME/bin/cdlp_exerciser/output";
		cd ${output_dir};
		cd block5040 && xterm -e ./main 5040 -88.6 33.6 0 0 8 11 22 33 0 0 &
		cd fcslogin && xterm -e ./main 5050 fbcb2-private.pem &
		cd fcslogoff && xterm -e ./main 5051 fbcb2-key.pem &
	else
		_show "You must be a non-root user to start the background services.";
	fi
	cd ${curr_dir};
}

##
## _stop_all_main_processes: This kills all background process started as part of the CFPIM test
##
function _stop_all_main_processes () {
	eval "ps -ef | egrep main | egrep xterm" > /dev/null ;
	if [ $? -eq 0 ]
	then
		_show "The following bg services will be killed: ";
		eval "ps -ef | egrep main | egrep xterm";
		$(ps -ef | egrep main | egrep xterm | awk '{ print $2}' | xargs -i kill -9 {} 2>&1 /dev/null);
		if [ $? -ne 0 ]
		then
			_show "Unable to kill bg services; please manually close the service windows";
		fi
	fi
}


##
## _tail_soscoe_log: This creates an xterm for tailing the soscoe log
##
function _tail_soscoe_log () {
	_kill_soscoe_log
	SOSCOE="/tmp/soscoe_start.out"
	xterm -geom 120x15+0+750 -title "SOSCOE Log" -bg "#123444789" -fg "yellow" -e "tail -f ${SOSCOE}" &
}

##
## _tail_cfpim_logs: This creates an xterm for tailing each cfpim log: Basic, CDLP, and Detail'
##
function _tail_cfpim_logs () {

	_kill_cfpim_logs

	BASIC_LOG=$(ls -1 /fcs/current/services/BCME/log/fcccme_cfpim_Basic* | tail -n 1)
	CDLP_LOG=$(ls -1 /fcs/current/services/BCME/log/fcccme_cfpim_Cdlp* | tail -n 1)
	DETAIL_LOG=$(ls -1 /fcs/current/services/BCME/log/fcccme_cfpim_Detail* | tail -n 1)

	xterm -geom 120x24+500+0 -title "${BASIC_LOG}" -bg "#123555789" -fg "yellow" -e "tail -f ${BASIC_LOG}" &
	xterm -geom 120x20+500+350 -title "${CDLP_LOG}" -bg "#123666789" -fg "yellow" -e "tail -f ${CDLP_LOG}" &
	xterm -geom 120x20+500+650 -title "${DETAIL_LOG}" -bg "#123777789" -fg "yellow" -e "tail -f ${DETAIL_LOG}" &
}


###############################################################
#                                                             #
#               Common Utility Functions                      #
#                                                             #
###############################################################

##
## _usage: This display a usage message in case the operator incorrectly invokes this script
##
function _usage () {
	cat << USE

	Usage: testCFPIM <TC_Number> <Service> <Build> <SOSCOE_Version> <Grep_File>

	where <Testcase_Number> is the test case number of the service being tested
	      <Service> is the name of the service
	      <Build> is the software version of the service being tested
	      <SOSCOE_VERSION> is the software version of SOSCOE used during the test
	      <Grep_File> is the file containing the log and db greps

USE
}

##
## _show_menu_header: This displays a standard test header on the service test window
##
function _show_menu_header () {
	cat << HEADER

 ##########################################################################################

           Testcase: ${TC_NUMBER}         Service Name: ${SERVICE}        Date: ${DATE}
      Build Version: ${BUILD_VERSION}     SOSCOE Version: ${SOSCOE_VERSION}

 ##########################################################################################

HEADER
}

##
## _show_menu_footer: This displays a standard test footer on the service test window
##
function _show_menu_footer () {
	echo
	echo -n "    Enter choice: "
	read choice
}

##
## _is_valid: This returns 0 (true) if the menu option entered by the operator
##     is valid
##
function _is_valid () {
	cleaned="$(echo "$1" | sed -e 's/[^[:digit:]]//g')"

	## First removes non-digits from input and then compares result with
	##  actual input. If not the same, return false
	if [ "${cleaned}" == "${choice}" ]
	then
		return 0
	else
		return 1
	fi
}

##
## _display_result: This invokes the function selected by the operator.
##
function _display_result () {
	clear
	case ${choice} in 
		1) _start_soscoe ;;
		2) _stop_soscoe ;;
		3) _start_fcslogin ;;
		4) _stop_fcslogin ;;
	        5) _start_single_main_process_1 ;;
	        6) _start_main_processes_wo_time ;;
		7) _start_main_processes_w_time ;;
		8) _start_main_processes_w_time_tc8 ;;
	        9) _stop_all_main_processes ;;
		10) _start_pa ;;
		11) _stop_pa ;;
		12) _start_fbcb2 ;;
		13) _stop_fbcb2 ;;
		14) _start_process_watcher ;;
		15) _tail_soscoe_log ;;
		16) _tail_cfpim_logs ;;
		17) _start_grepper ;;
		99) _end_test ;;
	esac
	clear
}

##
## _validate_choice: This triggers the call to validate the operators selection
##
function _validate_choice () {
	if ! _is_valid "${choice}" 
	then
		echo
		echo "Invalid choice. Please select again"
		echo
	else
		_display_result "${choice}"
	fi
}

##
## _show_choices: This display the menu options
##
function _show_choices () {
	cat << CHOICE

              1)  Start SOSCOE
	      2)  Stop SOSCOE
	      3)  Start FCSLogin
	      4)  Stop FCSLogin
	      5)  Start Single Main Process #1
	      6)  Start Main Processes (wo/Time Test)
	      7)  Start Main Processes (w/Time Test)
	      8)  Start Main Processes (w/Time Test - TC8)
	      9)  Stop All Main Processes
	      10)  Start the Platform Exerciser
	      11) Stop the Platform Exerciser
	      12) Start the FBCB2 Exerciser
	      13) Stop the FBCB2 Exerciser
	      14) Start Process Watcher
	      15) Tail SOSCOE Log
	      16) Tail All CFPIM Logs
	      17) Start the Grepper
	      99) End the Test and Archive Data

CHOICE
}

##
## _show_menu: This displays the entire menu on the service test window
##
function _show_menu () {
	_show_menu_header
	_show_choices
	_show_menu_footer
	clear
}


##########################
#                        #
#         Main           #
#                        #
##########################

TC_NUMBER=$1
SERVICE=$2
BUILD_VERSION=$3
SOSCOE_VERSION=$4
GREP_FILE=$5

DATE=$(date +%m/%d/%y_%H:%M:%S)
EXIT="99"

if [ $# -ne 5 ]
then
	_usage;
else
	for (( ;; ))
	do
		_show_menu;
		_validate_choice
		if [ "${choice}" = "${EXIT}" ]
		then
			break
		fi
	done
fi

