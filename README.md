# Simple Snake
A simple snake game in pure java

To install using homebrew type `brew cask install hasahmed/hbrew/simple-snake`

To install on any other platform see the development section

# Development 

## Building

[Ant](https://ant.apache.org/) is required for building.

Clone the repo and from the root directory run `ant jar`. This builds
the application and puts it in a jar file in `dist/jar/`. To run the jar from a command line
enter `java -jar dist/jar/simplesnake.jar` (Windows `java -jar .\dist\jar\simplesnake.jar`)

### Building MacOS Application
The application can be packaged into a MacOS .app bundle. Using a *nix system you can run ./buildall.
For other systems you can copy the code from these scripts and run them in your command prompt without
the shebang line.


### Getting Started

The main entry point to the application is the Snake.java. To begin understanding the application, start there.
