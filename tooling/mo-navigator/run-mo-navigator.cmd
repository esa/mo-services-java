
@Echo OFF
set JAR_PATH=target/mo-navigator-jar-with-dependencies.jar

IF NOT EXIST %JAR_PATH% (
	ECHO The jar file does not exist! Please compile the code before running. The file must be in: %JAR_PATH%
	EXIT /B
)

@Echo ON
java -jar %JAR_PATH%
