Completed by: Kristiyan Stoilov

How to execute files on Windows:
1) Open at least two different instances of Command Prompt
2) Change all of their current directory to folder's directory
3) On the first instance of Command Prompt write "javac ServerThread.java"
4) Now write "java ServerApp.java" to launch the server
// Steps 3&4 are necessary because ServerApp uses ServerThread class and needs to be compiled since they're not project files in the submission folder //
5) On the second and further instances of Command Prompt write "java ClientApp.java" to launch the ClientApp and interact with the user console

Other information:
- Predefined server listening port 431
- Writing "help" prints to user the available commands
- User commands are not case sensitive, meaning that capitalisation does not need to be exact for the command to execute
- If "connect" is sent to server twice in a row, client will disconnect and connect again
- In case of an invalid input, user is notified and prompt is open again
- Instead of errors when not connected to server, user is notified and prompt is open again
- Program does not shut down unless server not found or "exit" command, or forced shutdown
- ServerApp displays when client connection to server is interrupted

If any explanation or any troubleshooting is needed, please contact me.