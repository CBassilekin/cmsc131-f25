# Frederick Community College CMSC 131, Fall 2025


## Setup Instructions 

1. **Install VS Code**  
   Download and install from [https://code.visualstudio.com](https://code.visualstudio.com).

2. **Install the Dev Containers Extension**  
   Open VS Code → Extensions → search for "Dev Containers" → install.

3. **Fork Then Clone This Repository** 
    1. Create a fork of this repository in your personal GitHub account.
    2. Go to the GitHub page for your fork of this repository. Click the green CODE button and copy the URL for the HTTPS option.
    3. In a new VS Code window, click the "Clone Git Repository..." option. If you don't see the option, press CTRL+SHIFT+P to open a command palette, then type "clone" and select the option `Git: Clone`.
    4. Copy-paste the URL in the input box that appears.
    5. Select a repository destination.

5. **Open in VS Code**
    1. Launch VS Code in this folder.
    2. When prompted, click **"Reopen in Container"** .
    3. Wait while the environment builds.
        - You'll be prompted to install WSL (Windows Subsystem for Linux) and Docker during this step, if they aren't already on your system.

6. **Verify Setup**
    Open a terminal inside VS Code and run:
    ```bash
    java --version
    javac --version
    ```
    You should see that Java is installed.

7. **Run a Sanity Check** 
    
    6.1 Open `src/exercises/week00/HelloWorld.java` and use the play button (Run Java) to run the main routine. A terminal window should appear with output like this:
    ```
    ubuntu@86f33e8e9bef:/workspaces/fcc-cmsc131-f25$  /usr/bin/env /usr/lib/jvm/java-21-openjdk-amd64/bin/java @/tmp/cp_a13exa6xrrypb4t31fvf2mssd.argfile exercises.week00.HelloWorld 
    Hello, World!
    ```
    (Your container ID will differ.)

    6.2 Open the Testing pane (flask icon on left margin). You should see the `exercises.week00` test populated in the window. Press the Run Tests button to run the tests and verify that they pass.


## Directory Structure

More subdirectories will be added as the term goes on, but this high-level structure will stay the same.

```
cmsc131-f15  # repository name
├── .devcontainer  # development environment
│   ├── devcontainer.json
│   └── Dockerfile
├── README.md  # you are here
├── src  # source code
│   ├── examples
│   │   └── canine
│   │       ├── Canine.jva  # complete example
│   │       └── README.md  # overview and spec
│   ├── lib  # reusable components live in here
│   │   └── Utils.java
├── test  # unit test code
│   ├── examples
│   │   └── canine
│   │       └── CanineTest.java  # test coverage
└── .vscode  # IDE settings
    └── settings.json
```
