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

# add header tags to TOC
echo "<html><head><title>Top Directories</title></head><body>" >> ${TOC}

for dir in $(ls -1R ${SEARCH_DIR}) 
do

	# if directory
	if [ $(echo "${dir}" | egrep -c ":$") -eq 1 ]
	then

		_dir=${dir%:}
		mkdir -p "${HOST_DIR}${_dir}"	
		DIRNAME=$(basename "${_dir}")
		CODE_LIST_PAGE="${HOST_DIR}${_dir}.html"
		echo "<a href=\"file://${CODE_LIST_PAGE}\" target=\"code_list\">${DIRNAME}</a><br>" >> ${TOC} 

	# if readable file
	elif [ -r "${dir}" ]
	then

		_code_unit="${dir}"
		echo "<html><head><title>${_code_unit}</title></head><body>" >> ${CODE_LIST_PAGE}
		PAGE="${HOST_DIR}/${_dir}/${_code_unit}.html"
		echo "<a href=\"${PAGE}\" target=\"content\">${_code_unit}</a><br>" >> ${CODE_LIST_PAGE}
		echo "<html><head><title>${_code_unit}</title></head><body><pre>" >> ${PAGE}
		cat "${_dir}/${_code_unit}" >> ${PAGE}
		echo "</pre></body></html>" >> ${PAGE}
		echo "<a href=\"file://${PAGE}\" target=\"content\">${_code_unit}</a><br>" >> ${TOC} 

	fi	

	echo "</body></html>" >> ${CODE_LIST_PAGE}

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
<div class="box-bottom">
	<iframe src="" frameborder="1" scrolling="yes" width="100%" height="400" align="bottom" name="content"></iframe>
</div>

</body>
</html>


HOME_PAGE_CONTENT

) > $HOME_PAGE

