# java-inference-engine
Inference Engine made using Java, includes Forward Chaining and Backward Chaining Methods for Horn Clause Knowledge Bases, And Truth Table Checking for all types.

## Instructions

To run the Inference Engine, first, download and unzip the project folder to a secure location on your computer. For illustration purposes, I'll store the project folder on my C:/ drive. Then, search 'cmd' through your Windows taskbar or another method to access programs on your system. Open the Command Prompt by selecting the first application listed. Once Command Prompt is open, use the 'cd' command followed by the directory path where the project folder is located to navigate there. 

An additional instruction required before executing the assignment's solution is to input 'cd src'. This command is used to move to the specific location where the program is executed. 

To execute the program, enter the following command format: ‘iengine filename method’. Here, ‘iengine’ denotes the batch file necessary to initiate the project, ‘filename’ signifies the .txt file containing both the knowledge base and the problem query, while ‘method’ specifies the solving methods—TT (Truth Table Checking), FC (Forward Chaining), and BC (Backward Chaining). It's important to note that a sample navigation environment text file has been provided within the project folder. If simply using ‘iengine’ does not work, I recommend using ‘.\iengine’.

The agent will present the symbols it deduces during the search process if the Forward or Backward Chaining processes are selected. The Truth Table method will provide the count of truth tables or worlds in which the query is entailed given the knowledge base.

## File Format

The problems are stored in simple text files consisting of both the knowledge base and the query:
- The knowledge base follows the keyword TELL and consists of Horn clauses separated by 
semicolons.
- The query follows the keyword ASK and consists of a proposition symbol.

For example, the following is the content of a sample test file:
TELL
p2=> p3; p3 => p1; c => e; b&e => f; f&g => h; p1=>d; p1&p3 => c; a; b; p2;
ASK
d

Other logical symbols include:
~ for negation (¬)
& for conjunction (∧)
|| for disjunction (∨)
=> for implication (⇒)
<=> for biconditional (⇔)
