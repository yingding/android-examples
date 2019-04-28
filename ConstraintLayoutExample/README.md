# Checking App Package Dependency with Gradle
Reference: https://stackoverflow.com/questions/21645071/using-gradle-to-find-dependency-tree

In terminal with gradlew wrapper:
`./gradlew :app:dependencies >> dependencies_output.txt`

# Android Studio 3.x dependencies with implement or api
Reference: https://medium.com/mindorks/implementation-vs-api-in-gradle-3-0-494c817a6fa

# Add Local Git
Android Studio -> Preferences -> Version Controll -> Git -> SSH Executable -> Change from "Built-in" to "native" for using your terminal ssh client

# Add version control
Menu -> VCS -> Enable Version Controll Integration ... -> Gi
# Constraint Layout
* https://developer.android.com/training/constraint-layout/
* coding this lab according to https://codelabs.developers.google.com/codelabs/constraint-layout/index.html?index=..%2F..io2018#0

## Make Project in Build Menu
After added sampledata in app folder:
```
Build -> Make Project
```

## Known Issues
* There is a issue with barrier rendering in Android Studio 3.3.2, Using Android Studio 3.4.0 to see the barriers in the graphic editor.
* Editor with Chain Mode: right click the lead element in the chain, further click on the chain mode to change the chain mode. The modes are available at https://developer.android.com/training/constraint-layout/index.html#constrain-chain .


## Origin of this example project
This example is originated from a [Google code lab for constraintLayout](
https://codelabs.developers.google.com/codelabs/constraint-layout/index.html?index=..%2F..io2018#11) in android studio 3.x with constraintLayout 1.1.2 and it is further adapted to androidx.constraintlayout:constraintlayout:1.1.3` by the author of this repository

## Additional Resources:
Check [Constaint Layout Docs](https://developer.android.com/training/constraint-layout/index.html) for more details about the chains and weights or chain for spread and spread inside modes.

