#JavaModeBrowser

A Java replica of the Line Mode Browser

##Note

The code is still under (heavy) development - the HTTP communication, HTML tokenization and the HTML tree construction stages are mostly complete, the display part is not yet operational.

##System requirements

 * Internet connection (obviusly). 
    - Almost any connection should be okay since the LMB does not handle large files such as images, videos or Flash.
 * Java 7
    - The code makes heavy use of Java 7 features such as String switches - so it won't compile and run on older versions without rewriting a larger part of the code.

##Compiling the code

Refer to the next section. I'm going to write a bash script (and probably a batch one as well) which'll compile it automagically.

##Working on the code

###Eclipse

####Creating a project

 * Create a new Java project (let's assume it's called "Example" for now)
 * Go to the project's directory ( YourWorkspaceDirectory/Example )
 * Clone the repo to the src folder (git clone git@github.com:Garmine/Line-Mode-Browser src)
 * Refresh the project inside Eclipse (click on the project and press F5)

You should have a working Eclipse project set up by now: running, editing and exporting should work just as it does for any other project. Note that the JavaModeBrowser uses assertions so you should run it using the "-ea" flag during development!
