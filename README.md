# seproject22



## Getting started

You will need to download the correct JavaFX SDK for your system to be able to run this in Eclipse.
This project is initially configured for Apple Silicon macOS
Always download the SDK version of JavaFX

macOS:
Apple Silicon -> aarch64 sdk
Intel -> x64 sdk

Windows:
x64 SDK in almost all cases. You probably know if you don't need it.

Unpack your sdk and make note of the file path of the lib folder

Open the project in Eclipse. Go to Preferences>Java>BuildPath>User Libraries

Remove the Current JavaFX user library and add another with the same name, "JavaFX"

Click "Add External Jars" and navigate to your lib file path. Add all the .jar files present in the lib folder.

Apply and save. Back in the Project Explorer, right click on the top level of the project. Go BuildPath>Configure BuildPath

Click Modulepath, then click Add Library. Select User Library from the list, then click next. Select your JavaFX library. Click finish.

Finally, click the dropdown menu on your run button and open Run Configurations. Add a new run configuration for the app. It's main class should be agridrone.MainApp

Then click on the arguments tab at the top. In VM arguments, add this

--module-path "/YourLibBuildPath" --add-modules javafx.controls,javafx.fxml

replace the path with your library path, heres mine for example "/Users/danielscroggins/Desktop/fx-sdks/macOS-apple/lib"

Finally, IF AND ONLY IF you are on APPLE SILICON

Uncheck the "Use the -XstartOnFirstThread argument when launching with SWT"

You can now save that run config and should be able to launch the app with that

