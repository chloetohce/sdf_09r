# Building and running commands

## For bytecode
In project root folder (ie task1 or project01am)
    `javac -d bin --source-path . ./*.java Card/*.java`

To run and compile bytecode main entrypoint
    `java -cp bin project01am.App`

## To package into jar file
Go into ***bin folder***
    `jar cvf day09.jar -e project01am.App .`
    -e specifies the entry point

Running inside bin folder where jar file is created
    `java -jar day09.jar`

Running jar file in root folder
    `java -cp bin -jar day09.jar`
    `java -jar bin/day09.jar`
