#!/bin/bash

date > ./log.txt

echo '------Compile---------------' >> ./log.txt

make >> ./log.txt 2>> ./log.txt 

if [ $? -ne 0 ]
then
    echo 'Error in javac : results in log.txt'
#    cat ./log.txt 
    cd ../
    exit 1
fi

echo '------Java_code-------------' >> ./log.txt

cd ./build/

java Main 2>> ../log.txt

if [ $? -ne 0 ] #-ne = not equal -eq = equal
then
    echo 'Error in java : results in log.txt'
    cd ../    
    exit 1 #exit in main fonction : return don't work
fi

echo '------Compile Cleaner-------' >> ../log.txt

cd ../

make clean >> ./log.txt
