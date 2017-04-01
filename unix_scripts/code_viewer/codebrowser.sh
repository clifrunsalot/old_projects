#!/bin/bash

set -x

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

# Create the directories
DIRS=( $(find ${SEARCH_DIR} -type d) )
for fn in "${DIRS[@]}" 
do

	webpath="${HOST_DIR}${fn}"
	mkdir -p $webpath

done

# Add the HTML files
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

# Add the directory pages
WEB_DIRS=( $(find ${HOST_DIR}) )
echo "${WEB_DIRS[@]}" | tr ' ' '\n'
#for fn in "${WEB_DIRS[@]}" 
#do
#	echo ""
#done


# add header tags to TOC
echo "<html><head><title>Top Directories</title></head><body>" >> ${TOC}

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
<div class="box-bottom">
	<iframe src="" frameborder="1" scrolling="yes" width="100%" height="400" align="bottom" name="content"></iframe>
</div>

</body>
</html>


HOME_PAGE_CONTENT

) > $HOME_PAGE

