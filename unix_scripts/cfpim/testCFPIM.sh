#!/bin/bash

#uncomment to debug
#set -x

##
## _usage: Displays error for invalid testcase numbers 
function _usage () {
	cat << USE

	Invalid testcase number.  Please enter a valid testcase number.

USE
}

##
## _set_exe: Changes the permissions of files in directory to a+rwx
##
function _chg_perms_for_main () {
	chmod a+rx /fcs/current/test/BCME/bin/cdlp_exerciser/output/block5040/*
	chmod a+rx /fcs/current/test/BCME/bin/cdlp_exerciser/output/fcslogin/*
	chmod a+rx /fcs/current/test/BCME/bin/cdlp_exerciser/output/fcslogoff/*
	chmod a+rwx /fcs/current/services/BCME/bin/fcccme_cfpim*
	chmod -R 777 /fcs/current/test/BCME/bin/cdlp_exerciser/output
}

##
## _clean_and_make: This is part of setting up CPFIM for testing.  This 
##  must be called after CFPIM has been installed.
## 
function _clean_and_make () {
	curr_dir=`pwd`
	cd /fcs/current/test/BCME/bin/cdlp_exerciser/output
	chmod -R 775 block5040
	cd block5040
	make clean
	make
	cd ${curr_dir}
}

##
## _copy_key: Copies the key generated from creating fbcb2user into fcslogin/fcslogoff directories
##
function _copy_key () {

	KEY="/tmp/key.pem"
	if [ -s ${KEY} ]
	then
	        cp /tmp/key.pem /fcs/current/test/BCME/bin/cdlp_exerciser/output/fcslogin/fbcb2-key.pem
        	cp /tmp/key.pem /fcs/current/test/BCME/bin/cdlp_exerciser/output/fcslogoff/fbcb2-key.pem
	else
		xterm  -hold -bg "yellow" -fg "black" -geom 100x40+50+50 -font -*-*-*-*-*-*-*-*-*-*-*-90-iso8859-* \
			-e "echo \"Please generate the key before proceeding\";\
			   echo;\
			   echo \"Once done, close this window to proceed\";"
	fi
}

##
## _get_version: This discovers and prints the version of the build being tested.
##  This "current" symbolic link must be set before calling this function.
##
function _get_version () {
	BUILD_VERSION=$(ls -l /fcs/current | awk '{print $11}')
}

##
## _get_soscoe_version: This discovers and prints the version of soscoe being used.
##
function _get_soscoe_version () {
	SOSCOE_VERSION=$(ls -1 /BCT/config_rw/soscoe/versions)
}

##
## _is_CFPIM_setup: Returns true (0) if CFPIM has been setup correctly for testing
##
function _is_CFPIM_setup () {
	echo
}

##
## _test_fcslogin: Uses the sudo command to test is scFcsLogin service is up
##
function _is_fcslogin_installed () {
	echo
	echo "Testing FCSLogin installation."
	echo
	echo "Follow the prompts to use sudo to start scFcsLogin"
	echo
	sudo /sbin/service scFcsLogin start
	sleep 5
	if [ $(ps -ef | egrep -i /BCT/ro/soscoe/coreservices/bin/i686-rhel-3-se/scFcsLogin | wc -l) -lt 1 ]
	then
		return 1
	else
		return 0
	fi
}

##
## _is_key_ready: Verifies that keys are setup to execute the login part of the CFPIM test
##
function _is_key_ready () {
	echo
}

##
## _is_test_case_number: This returns true (0) if the testcase number entered is within the
##  array VALID_TC_NUMBERS.
##
function _is_test_case_number () {
	num=$1
	(( num = $num - 1 ))
	if [ ${VALID_TC_NUMBERS[${num}]} ]
	then
		TC_NUMBER=${VALID_TC_NUMBERS[${num}]}
		return 0
	else
		return 1
	fi
	num=0
}

##
## _is_valid: First removes non-digits from input and then compares result with actual input;
##  If not the same, return false
function _is_valid () {
	cleaned="$(echo "$1" | sed -e 's/[^[:digit:]]//g')"
	if [ "${cleaned}" == "${choice}" ]
	then
		return 0
	else
		return 1
	fi
}

##
## _modify_cfpim_config_file: Modifies CFPIM config file for testing
##
function _modify_cfpim_config_file () {
	sed -e 's#<CfpimIpAddress>.*</CfpimIpAddress>#<CfpimIpAddress>127.0.0.1</CfpimIpAddress>#' \
	-e 's#<CfpimServerPortNumber>.*</CfpimServerPortNumber>#<CfpimServerPortNumber>9100</CfpimServerPortNumber>#' \
	-e 's#<Fbcb2IpAddress>.*</Fbcb2IpAddress>#<Fbcb2IpAddress>127.0.0.1</Fbcb2IpAddress>#' \
	-e 's#<Fbcb2PortNumber>.*</Fbcb2PortNumber>#<Fbcb2PortNumber>9101</Fbcb2PortNumber>#' \
	-e 's#<DefaultPaInitialFailsafeTimeout>.*</DefaultPaInitialFailsafeTimeout>#<DefaultPaInitialFailsafeTimeout>0</DefaultPaInitialFailsafeTimeout>#' \
	-e 's#<DefaultPaSubsequentFailsafeTimeout>.*</DefaultPaSubsequentFailsafeTimeout>#<DefaultPaSubsequentFailsafeTimeout>0</DefaultPaSubsequentFailsafeTimeout>#' \
	-e 's#<DefaultFbcb2InitialFailsafeTimeout>.*</DefaultFbcb2InitialFailsafeTimeout>#<DefaultFbcb2InitialFailsafeTimeout>0</DefaultFbcb2InitialFailsafeTimeout>#' \
	-e 's#<DefaultFbcb2SubsequentFailesafeTimeout>.*</DefaultFbcb2SubsequentFailesafeTimeout>#<DefaultFbcb2SubsequentFailesafeTimeout>0</DefaultFbcb2SubsequentFailesafeTimeout>#' \
	-e 's#<DebugFlagLogging>.*</DebugFlagLogging>#<DebugFlagLogging>1</DebugFlagLogging>#' \
	-e 's#<NtpServerCheck>.*</NtpServerCheck>#<NtpServerCheck>0</NtpServerCheck>#' \
/fcs/current/services/BCME/data/fcccme_cfpim_CfpimConfig.xml > /tmp/tmp1
	mv /tmp/tmp1 /fcs/current/services/BCME/data/fcccme_cfpim_CfpimConfig.xml

}

##
## _modify_clocksetter: This is part of setting up CFPIM for testing. This must be called
##  after CFPIM is installed.
##
function _modify_clocksetter () {
	sed -e 's#chmod +x fcccme_cfpim\*#chmod a+x fcccme_cfpim\*#' \
	-e 's#chmod +w ../data/fcccme_cfpim\*#chmod a+w ../data/fcccme_cfpim\*#' /fcs/current/services/BCME/bin/fcccme_cfpim_postinstall.bash > tmp1
	mv -f tmp1 /fcs/current/services/BCME/bin/fcccme_cfpim_postinstall.bash
}

##
## _modify_soscoe_files: Changes three soscoe config files to support CFPIM testing
##
function _modify_soscoe_files () {
	
	SCSYSMGEDAPPCONFIG="/BCT/config_rw/soscoe/versions/V1.8.1.0/coreservices/data/xml/ConfigFiles/SysMgedAppConfig.xml"
	SYSCONFIG="/BCT/config_rw/soscoe/versions/V1.8.1.0/coreservices/data/xml/ConfigFiles/SysConfig.xml"
	MASTERSYSMGRCONFIG="/BCT/config_rw/soscoe/versions/V1.8.1.0/coreservices/data/xml/ConfigFiles/MasterSysMgrConfig.xml"

	APPDATA=$(cat /fcs/current/services/BCME/data/fcccme_cfpim_SysMgedAppConfig.xml | egrep -v "#|^$")
	xterm -hold -title "Modify SOSCOE Files" -bg "yellow" -fg "black" -geom 100x60+50+50 -font -*-*-*-*-*-*-*-*-*-*-*-90-iso8859-* \
		-e "echo; echo; echo \"As the Root user:\";\
		echo;\
		echo \"(1) Append the following lines into the file \";\
		echo;\
		echo \"${SCSYSMGEDAPPCONFIG}\";\
		echo;\
		echo \"just before the tag </sc:ScSysMgedAppConfig>\";\
		echo; echo;\
		echo \"${APPDATA}\";\
		echo;\
		echo \"(2) In the files\";\
		echo;\
		echo \"${SYSCONFIG} and \";\
		echo \"${MASTERSYSMGRCONFIG}\";\
		echo;\
		echo \"  from:\";\
		echo;\
		echo \"    <CommonProp>\";\
		echo \"        <PropertyName>DebugLevel</PropertyName>\";\
		echo \"        <Value>\";\
		echo \"            <UShortValue>0</UShortValue>\";\
		echo \"        </Value>\";\
		echo \"    </CommonProp>\";\
		echo;\
		echo \"  to:\";\
		echo;\
		echo \"    <CommonProp>\";\
		echo \"        <PropertyName>DebugLevel</PropertyName>\";\
		echo \"        <Value>\";\
		echo \"            <UShortValue>10</UShortValue>\";\
		echo \"        </Value>\";\
		echo \"    </CommonProp>\";\
		echo;\
		echo \"  from:\";\
		echo;\
		echo \"    <CommonProp>\";\
		echo \"        <PropertyName>EnableEnterExitDebug</PropertyName>\";\
		echo \"        <Value>\";\
		echo \"            <BooleanValue>false</BooleanValue>\";\
		echo \"        </Value>\";\
		echo \"    </CommonProp>\";\
		echo;\
		echo \"  to:\";\
		echo;\
		echo \"    <CommonProp>\";\
		echo \"        <PropertyName>EnableEnterExitDebug</PropertyName>\";\
		echo \"        <Value>\";\
		echo \"            <BooleanValue>true</BooleanValue>\";\
		echo \"        </Value>\";\
		echo \"    </CommonProp>\";\
		echo; echo;\
		echo \"Once complete, close this window to proceed\";"

}

##
## _run_postinstall: Interacts with operator to login as root and execute the script shown
##
function _run_postinstall () {
	xterm  -hold -bg "yellow" -fg "black" -geom 100x20+50+50 -font -*-*-*-*-*-*-*-*-*-*-*-90-iso8859-* -e "\
	cat << CFPIMSETUP
As the root user:

In a separate xterm, login in as root and do the following:

> cd /fcs/current/services/BCME/bin
> ./fcccme_cfpim_postinstall.bash

Once that is done, close this window to proceed

CFPIMSETUP\
"
}

##
## _show_menu: Display request for testcase number.
##
function _show_menu () {
	echo
	echo -n "        Enter Testcase number or q to quit: "
	read choice
}

##
## _modify_cfpim: This queries the operator's choice to modify the CFPIM config file for a test
##  environment.
##
function _modify_cfpim () {

	for (( ;; ))
	do
		clear
		echo
		echo    "        If you have previously started CFPIM, "
		echo    "        then its configuration file has been"
		echo    "        modified for the testing environment on"
		echo    "        one box."
		echo
		echo    "        You do not need to keep modifying it if"
		echo    "        you plan to make manual changes during "
		echo    "        the execution of a test."
		echo
		echo    "        Do you want reset it so that CFPIM will"
		echo -n "        be tested on one box in a test environment? [y|n]: "
		read modCfpimConfig

		case "${modCfpimConfig}" in
			y)
		
				_modify_cfpim_config_file 
				break ;;
	
			n)
				break ;;
		
			*)
				echo
				echo "        Invalid entry. Please try again"
				echo "        Press any key to continue"
				read -sn 1 discard
				echo ;;
		esac
	done
}

##
## _setup_CFPIM: Walks operator through setting up CFPIM. This is interactive at times
##  because of the root requirements
##
function _setup_CFPIM () {
	# (1) Clean and make main executable
	_clean_and_make
	# (2) Modify post install script before execution 
	_modify_clocksetter
	# (3) Make all "main" apps executable
	_chg_perms_for_main
	# (4) Modify cfpim config file
	_modify_cfpim
	_run_postinstall
	# (5) Modify soscoe config files to startup CPFIM correctly
	_modify_soscoe_files
	# (6) Copy key to the appropriate fcslogin/fcslogoff directories
	_chg_perms_for_main
	_copy_key
}	

##
## _create_keys: Provides instructions for setting up FCSLogin if it has not been done yet.
##
function _setup_key () {

	xterm -hold -bg "yellow" -fg "black" -geom 100x40+50+50 -font -*-*-*-*-*-*-*-*-*-*-*-90-iso8859-* -e "\
	cat << FBCB2USER

In a separate xterm, login in as root and execute the steps below.

If you do not have root permissions, verify that you have sudo powers
on \"openssl,\" \"scSoscoeCred,\" \"useradd,\" \"nologin,\" and file
editing permissions for ~fbcb2user/SessionConfig.xml.

If you do have sudo, then simply pre-pend sudo in front of the
commands mentioned earlier and execute.

If you do not, ask a PSE person to execute the steps for you.

  ------------------------- START HERE -----------------------------------

(1) Add \"FCSLOGIN_FBCB2_USER=fbcb2user\" to /etc/soscoeia.conf

(2) Create user account for fbcb2user:
        > useradd -rms /sbin/nologin fbcb2user
        > echo -n fbcb2user | openssl dgst -sha1 | passwd fbcb2user --stdin
        > scSoscoeCred -c fbcb2user -p \$\(echo -n fbcb2user | openssl dgst -sha1\)
                                        ^                                       ^
                                             Be sure remove the "\\" from here

(3) Modify SessionConfig.xml in fbcb2user homespace:
        > cp /tmp/SessionConfig.xml ~fbcb2user/
        > chown fbcb2user:fbcb2user ~fbcb2user/SessionConfig.xml
        > chmod 600 ~fbcb2user/SessionConfig.xml

(4) Create key
        > echo -n fbcb2user | openssl dgst -sha1

        (copy the output from previous command into cursor paste buffer
            and paste after colon from following command)

        > openssl rsa -in /var/identities/private/fbcb2user/keys/signencr.key -out /tmp/key.pem

  ------------------- CLOSE THIS WINDOW WHEN DONE -----------------------

FBCB2USER\
"
}

##
## _start_test: Invokes the main CFPIM script within a separate xterm.
##
function _start_test () {

	echo
	echo "TC_NUMBER      = ${TC_NUMBER}"
	echo "SERVICE        = ${SERVICE}"
	echo "BUILD VERSION  = ${BUILD_VERSION}"
	echo "SOSCOE VERSION = ${SOSCOE_VERSION}"
	echo "GREP_FILE      = ${GREP_FILE}"
	echo

	xterm -title "Main CFPIM Test Window" -geom "100x35+10+10" -bg "black" -fg "white" -e "./CFPIM.sh ${TC_NUMBER} ${SERVICE} ${BUILD_VERSION} ${SOSCOE_VERSION} ${GREP_FILE}" &

}

##
## _start: Starts interaction with operator for testing CFPIM.
##
function _start () {
	clear
	for (( ;; ))
	do
		_show_menu
		choice=$(echo ${choice} | tr "[:upper:]" "[:lower:]") 
		if [ "${choice}" == "${QUIT}" ]
		then
			break
		else
			clear
			if ! _is_valid ${choice}
			then
				_usage
			else
				if _is_test_case_number ${choice}
				then
					_start_test
					break
				else
					_usage
				fi
			fi
		fi
	done

}

#########################
#                       #
#        MAIN           #
#                       #
#########################

# Initialize variables; these will be retrieved later
BUILD_VERSION=""
SOSCOE_VERSION=""

# Enter testcase numbers here
typeset -a VALID_TC_NUMBERS=(1 2 3 4 5 6 7 8 9 10)

# Enter CFPIM-specific variables here
SERVICE="CFPIM"
GREP_FILE="CFPIM.data"

# Populate variables for passing to follow-on script
_get_version
_get_soscoe_version

# This will be determined by value entered by operator
TC_NUMBER=""

#flag to end script
QUIT="q"

if _is_fcslogin_installed
then
	_setup_CFPIM
	_start

else
	xterm -hold -geom 60x10+300+650 -title "SOSCOE is Ready" -bg "red" -fg "yellow" \
		-fn -*-fixed-bold-*-*-*-*-*-*-*-*-100-iso8859-* \
		-e "echo \"STOP\";echo;\
		   echo \"The scFcsLogin software has one of the following problems:\";\
		   echo \"(1) It is not installed\";\
		   echo \"(2) It is installed but is not setup for testing.\";\
		   echo \"If it is installed, then set it up according to the following window\";\
		   echo; echo;\
		   echo \"Close this window to proceed\";"
		
	_setup_key

fi

