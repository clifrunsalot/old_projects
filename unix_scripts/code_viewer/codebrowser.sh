#!/bin/bash

#set -x

# Root directory of all code units
SEARCH_DIR=$1

# Place to host generated HTML files
HOST_DIR="${HOME}/codehopper"
rm -rf ${HOST_DIR}
mkdir -p ${HOST_DIR}

###################
#
# Build TOC page
#
###################

TOC=${HOST_DIR}/toc.html
rm -f ${TOC}
touch ${TOC}

##############################################
#
# Create the directories in $SEARCH_DIR area
#
##############################################
DIRS=( $(find ${SEARCH_DIR} -type d) )
for fn in "${DIRS[@]}" 
do
	webpath="${HOST_DIR}${fn}"
	mkdir -p $webpath
done


##############################################
#
# Create a webpage per code unit
# in the same structure as the code base.
#
##############################################
for dir in "${DIRS[@]}" 
do
	for fn in $(ls -1 "${dir}")
	do
		is_ascii=$(file "${dir}/${fn}" | egrep -ic "ascii") 
		if [ "${is_ascii}" -eq 1 ]
		then 
			webpage="${fn}.html"
			webpagepath="${HOST_DIR}${dir}/${webpage}"
			touch ${webpagepath}
			echo "<html><head><title>$b_name</title></head><body><pre>" > ${webpagepath}
			cat "${dir}/${fn}" | awk '{print NR" "$0}' >> ${webpagepath}
			echo "</pre></body></html>" >> ${webpagepath}
		fi
	done
done

echo "<html><head><title>TOC</title></head><body><script type=\"text/javascript\">function highlightParent(fn){document.getElementById(fn).style.backgroundColor=\"yellow\";}</script>" > ${TOC}

# Add the directory pages
WEB_DIRS=( $(find ${HOST_DIR} -type d | sort ) )
ALL_FILES=( $(find ${HOST_DIR} | sort ) )

for curr_dir in "${WEB_DIRS[@]}"
do

	# create temp_var to hold page contents
	# for each item in curr_dir 
	# 	if item_path == curr_name
	# 		get page name
	# 		add page_path and page_name to temp_var
	# end for each
	# if temp_var not 0 len
	#		get dir_name
	#		build dir_page
	#		write dir_page to path 
	#		add dir_page link to TOC

	CONTENT=""

	for curr_item in "${ALL_FILES[@]}"
	do
		ITEM_NAME=$(basename ${curr_item})
		if [ "$(dirname ${curr_item})" == "${curr_dir}" -a $(echo ${ITEM_NAME} | egrep -c "\.html") -eq 1 ]
		then
			ITEM_NAME=$(echo "${ITEM_NAME}" | sed 's/\.html$//')
			CONTENT="${CONTENT}<a href=\"file://${curr_item}\" id=\"${ITEM_NAME}\" target="content" onclick=\"highlight('${ITEM_NAME}');\">${ITEM_NAME}</a><br>" 
		fi
	done 

	if [ ! -z "${CONTENT}" ]
	then

		# Count slashes
		brkt_cnt=$(echo "${curr_dir}" | sed 's/[0-9a-zA-Z\-\_\.]//g' | wc -c)
		INDENT=$(awk -v cnt=${brkt_cnt} 'BEGIN{printf("|","")}END{for(i=0;i<cnt;i++){printf("%s","_");}}' /dev/null)

		DIR_NAME=$(basename ${curr_dir})
		DIR_PAGE="${curr_dir}.html"
		touch ${DIR_PAGE}
		echo "<html><head><title>${DIR_NAME}</title></head><body>" > ${DIR_PAGE}
		echo "<script type=\"text/javascript\">function highlight(fn){document.getElementById(fn).style.backgroundColor=\"yellow\";}</script>" >> ${DIR_PAGE}
		echo "${CONTENT}" >> ${DIR_PAGE}
		echo "</body></html>" >> ${DIR_PAGE}
		echo "<a href=\"file://${DIR_PAGE}\" id=\"${DIR_NAME}\" target=\"code_list\" onclick=\"highlightParent('${DIR_NAME}');\">${INDENT}${DIR_NAME}</a><br>" >> ${TOC}
	fi

done	

# add footer tags to TOC
echo "</body></html>" >> ${TOC}


###################
#
# Build Home page
#
###################

# Create the homepage with placeholders
HOME_PAGE="${HOST_DIR}/home.html"
(
cat <<HOME_PAGE_CONTENT
<!DOCTYPE html>
<html>
<head>
<style>
.box{
    float:left;
    margin-right:20px;
}
.box-bottom{
    margin-right:20px;
}
.clear{
    clear:both;
}
</style>
</head>
<body>
<div class="box">
	<iframe src="${TOC}" frameborder="1" scrolling="yes" width="100%" height="200" align="left"></iframe>
</div>
<div class="box">
	<iframe src="${CODE_LIST}" frameborder="1" scrolling="yes" width="100%" height="200" align="right" name="code_list"></iframe>
</div>
<div><button type="button" onclick="refresh()">Refresh</button>
<div class="box-bottom">
	<iframe src="" frameborder="1" scrolling="yes" width="100%" height="400" align="bottom" name="content"></iframe>
</div>
<script>
function refresh() {
    location.reload();
}
</script>
</body>
</html>


HOME_PAGE_CONTENT

) > $HOME_PAGE

