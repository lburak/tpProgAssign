Inside the folder Quiz are my anwsers to the Quiz part of the the assignment, both questions are answered in a single python script called quiz.py.

Inside Part1 are the source files for my solution to part 1 of the programming assignment. There is a bashscript called cmprunpart1.sh inside that when run will compile and run the code I produced. I use a jsondatabase.properties file to set the location of the json file to be parsed as well as other parameters like the name of the table to create to hold the values and which fields in the json we are going to extract. This file is found in the directory /Part1/src/parser/jsondatabase.properties.

Inside Part2 are the source files for my solution to part 2 of t assignment. Again I use the same jsondatabase.properties file to hold the relevant parameters. There is a bash script called search_keyword and it takes one argument just like the specification, it compiles and runs the code with the argument passed in.

*Note1* I was unable to get the scripts to work outside of their directories, I got an error saying that it could not find the main class when run from outside of their respective directories, despite the fact that I set the PATH environment variable and set the permissions.

*Note2* Due to the fact that I am very inexperience with git, I accidentally ended up deleting my repository and didn't have a backup anywhere, so I rewrote the whole thing in the wee hours of the morning. So if you are wondering why my code isn't commented it's because I lost the nice pretty version with nice verbose and fully explanatory comments, so sorry, but it should be pretty clear what I was trying to do. I tried as much as possible to get away from jsut implementing a 'hard coded' solution that was specific to this assignment. I wanted to parameterize as much as I could, I'm not sure how well I did, but If I go back the code with more time, I think I can make it very flexible and robust, since I built it with that in mind

*Note3* In case you test it with threads, this code is not thread safe, I repeat it is NOT thread safe. I initially thought about making sure that it could handle concurrency, but I realized I had a deadline, and so I didn't get to implement it.

*Final Note* This was a fantastic experience and a lot of fun for me. Thank you so much for the opportunity. It was a lot of fun, stressful, but exhilerating, so thank you again.
