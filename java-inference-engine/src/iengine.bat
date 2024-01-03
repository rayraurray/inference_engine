@echo off

REM Get the parameters
SET method=%~1
SET filename=%~2

REM Pass the arguments to the Java program
java -cp . Main %method% %filename%