#!/bin/bash
if (( $# != 6)); then
    echo "Illegal number of parameters. Program will start with default properties!"
    java -jar simulation-busnet/target/simulation-busnet-0.1-shaded.jar 
else
	echo Total Number of Argument Passed: "$#"
	echo Arguments List:
	for i in "$@"; do
  		echo "$i"
	done
	java -jar simulation-busnet/target/simulation-busnet-0.1-shaded.jar $1 $2 $3 $4 $5 $6
fi
