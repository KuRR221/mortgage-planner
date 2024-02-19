# Mortgage Calculator

Mortgage loan calculator built by Anton Backman using Gradle 7.6.4 running on java openjdk 17.0.8 2023-07-18.

## Overview

This Java program calculates mortgage payments based on input data provided in a CSV file. It uses a simple command-line interface and is designed to read input files located in the resources directory.

## Features

- Read input from a CSV file in the resources directory.
  
- Parse and clean input data for accurate calculations.
  
- Calculate monthly mortgage payments using a standard formula.
  
- Display results for each prospect.

## Usage

- First make sure java is installed on your computer

- Then you need to clone the project into a suitable location on your computer, then build the project

- A CSV input file should be located in the src/main/resources folder. The data should be in this format: Name, Total loan, Interest, Years

- Please note that the first line in the CSV file is reserved for the header and will be ignored

- Navigate to the project folder in CMD

- Then run the command ./gradlew build
- This builds the project into a jar file located in build/libs

- After the build is completed you run the program with the command java -jar build/libs/mortgage-planner-1.0.jar prospects.txt
- You can edit prospects.txt or change it to another file if wanted, just make sure it is located in the correct src/main/resources folder. You also need to build the project again to load the new resources

- The program also runs tests on the functions which can be found in the index.html file located in build/reports/tests/test
