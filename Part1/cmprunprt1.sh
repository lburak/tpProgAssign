javac -cp ~/gson-2.2.2.jar:~/postgresql-9.2-1002.jdbc4.jar -d ./bin ./src/parser/*.java
java -classpath ~/gson-2.2.2.jar:/home/ubuntu/postgresql-9.2-1002.jdbc4.jar:./bin parser.Driver
