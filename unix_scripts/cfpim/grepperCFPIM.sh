#!/bin/bash

#uncomment to debug
#set -x

# Error message for incorrect invocation of tool
function usage () {

	cat<<USE

        Usage: grepperCFPIM.sh <TC_Number> <Data_File>

        where <TC_Number> is the title of the testcase
              <Data_File> is the file containing the log greps for the CFPIM test.

USE

}

function filter_data () {
	cat ${DATA_FILE} | egrep -v "^#|^$" | egrep "^${TC_NUMBER}," | \
		sed -e 's#[1-9][0-9]*,\([1-9][0-9]*\),\(.*$\)#\1 => \2#' > ${CMD_FILE}
}

function display_available_greps () {
	cat ${CMD_FILE} | awk '{ print "    "$0 }'
}

# Displays standard menu header
function display_menu_header () {

	cat<<HEADER

##################################################################################################################
                                           Available Grep Commands
##################################################################################################################

HEADER

}

function display_greps () {
	filter_data
	display_available_greps
}

function display_menu_footer () {
	cat<<HEADER

##################################################################################################################

HEADER
	echo -n "    Enter choice or \"999\" to quit: "
	read choice
}

function is_valid () {
	if [ $(egrep "^${choice} => " ${CMD_FILE} | wc -l) -eq 1 ]
	then
		return 0	
	else
		return 1
	fi
}

# First removes non-digits from input and then compares result with actual input
#  If not the same, return false
function is_digit() {
	cleaned="$(echo "$1" | sed -e 's/[^[:digit:]]//g')"
	if [ "${cleaned}" == "${choice}" ]
	then
		return 0
	else
		return 1
	fi
}

function display_result () {
	result=$(egrep "^${choice} => .*$" /tmp/commands.txt)
	echo
	echo "##################################################################################################################"
	echo
	echo "     ${result}"
	echo
	echo "##################################################################################################################"
	echo
	eval $(echo ${result} | sed -e 's/[1-9][0-9]* => \(.*$\)/\1/')
	echo
	echo -n "Press enter to continue "
	read -sn 1
	clear
}

function validate_choice () {
	if is_digit "${choice}" && is_valid "${choice}"
	then
		display_result "${choice}"
	elif [ "${choice}" == "${EXIT}" ]
	then
		return
	else
		echo
		echo "Invalid choice. Please select again"
		echo
	fi
}

function display_menu () {
	choice=""
	display_menu_header
	display_greps
	display_menu_footer
	clear
}

################################
#                              #
#           Main               #
#                              #
################################

TC_NUMBER=$1
DATA_FILE=$2
EXIT="999"
CMD_FILE="/tmp/commands.txt"

if [ $# -eq 2 ]
then
	if ! -z "${TC_NUMBER}" -o -e "${DATA_FILE}"  
	then	
		clear
		for (( ;; ))
		do
			display_menu
			validate_choice
			if [ "${choice}" == "${EXIT}" ]
			then
				exit
			fi
		done
	else
		clear
		usage
	fi
	choice=""
else
	clear
	usage
fi

