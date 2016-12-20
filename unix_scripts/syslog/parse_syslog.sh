#!/bin/bash

#set -x

FILE="${1}"

# Display usage on erroneous invocation
function _usage(){
cat <<USAGE

	Parses through the file and counts instances of strings in field #5 of syslog.
	
	Usage: ./parse_syslog.sh <path to syslog>

	Output:
 
        Count Field #5                                          
   ************************************************************
           14 accounts-daemon                                   
            5 alsactl                                           
           28 anacron                                           
                   .
                   .
                   .


USAGE
exit -1
}

declare -A source
# Constructs array of unique strings in field #5
function _build_source_array(){

	i=0
	for item in $(cat "${FILE}" | awk '{print $5}' | sed -n 's/\[[0-9]*\]\:// p' | sort -u)
	do
		if [ ! -z "$item" ]
		then
			source[$i]=${item}
			(( i = i + 1 ))
		fi
	done

}

sum=0
# Replaces value of source at the index with the string name and count
function _get_count(){
	src=$1
	idx=$2
	src_cnt=0
	src_cnt=$(egrep -o "$src" "$FILE" | wc -l)
	(( sum = sum + src_cnt ))
	source[$idx]="${source[$idx]}:$src_cnt"
}

# Cycles through the source array and collects totals
function _get_totals(){

	cnt=0;
	for((i=0; i<${#source[@]}; i++))
	do
		_get_count ${source[$i]} $i
	done
}

# Simply diplays total per field #5 term
function _display_totals(){

	printf "\n\n%10s %-50s%s\n" "Count" "Field #5"
	printf -v underline "%60s" " "
	echo "${underline// /-}"	
	for(( i=0; i<${#source[@]}; i++ ))
	do
		nm=${source[$i]%%:[0-9]*}
		ct=${source[$i]##[^0-9]*:}
		printf "%10d %-50s%s\n" $ct $nm 
	done		
	printf -v underline "%60s" " "
	echo "${underline// /=}"	
	printf "%10d %-50s%s\n" $sum "Total" 

}


# Main
if [ "$#" -gt 0 ]
then
	_build_source_array
	if [ "${#source[@]}" -gt 0 ]
	then
		_get_totals
		_display_totals
	else
		printf "\n\n%s\n\n" "$FILE is either a invalid syslog file or does not have a field #5."
		exit -1
	fi
else
	_usage

fi
