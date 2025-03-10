## Author
Greg Schmelter
greg.schmelter@outlook.com

## Build tools & versions used
I used the latest version of Android Studio, and its tools.
From the Android Studio Help->About Menu option:
Android Studio Koala | 2024.1.1
Build #AI-241.15989.150.2411.11948838, built on June 10, 2024
Runtime version: 17.0.10+0-17.0.10b1087.21-11609105 amd64
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.
Linux 6.11.0-19-generic
GC: G1 Young Generation, G1 Old Generation
Memory: 4096M
Cores: 20
Registry:
ide.experimental.ui=true
Current Desktop: ubuntu:GNOME

## Steps to run the app
As a standard Android app, just select the "app" from "Run" pull down menu, and click the Run button.
If the "app" option doesn't appear, create a standard Android App target with Module "Square_Test.app.main"

## What areas of the app did you focus on?
I was able to complete absolutely everything, and I'm happy with the results.  Here are the highlights:
- Jetpack Compose for everything in the UI
- StateFlows + CoRoutines for all API calls and UI Interaction
- @Previews for all of the Views, so that they could be tested in Android Studio without running the app
- ConstraintLayout for the Views and their content, so that none of the content overflows when you have really long values, and the Row will properly size itself whenever we have missing or extra long data
- Loading Spinners both for the initial load, and the load of the images
- Koin used for all Services, APIs, ViewModels, and Networking
- OkHTTP + Retrofit + Gson for getting the data
- JUnit + MockK for 90% testing code coverage
- LazyColumn for loading each row as needed, rather than all at once
- SubcomposeAsyncImage for the Images, which handled all of the caching

## What was the reason for your focus? What problems were you trying to solve?
My focus was on getting a perfectly working app that fulfilled the requirements, which I achieved.  My longest "catch" was when I started working on Rotation/Orientation.  It was blanking out the screen entirely instead of re-drawing properly.  I was totally baffled, until I realized that I hadn't put "android:configChanges="orientation|screenSize"" into my AndroidManifest.xml, and so my UI was being told about Orientation changes in the first place.

## How long did you spend on this project?
5 Hours to code the main app and have everything work, and an additional hour to write the JUnit tests.

## Did you make any trade-offs for this project? What would you have done differently with more time?
Because I only had one ViewModel, and only one Api Endpoint to parse, I wrote the "generic" code into the base classes themselves, rather than abstracting them into a super class. It makes the code smaller and simpler, but if I had more ViewModels to write or more Endpoints to talk to, I would have abstracted more of it out.

Also, the dependencies in build.gradle.kts are all inline rather than using the libs.versions.toml, because it was just faster and made it very obvious what libraries I was adding.

## What do you think is the weakest part of your project?
It all works, and it looks decent enough.  If I had a Figma to go against, I would have followed it to the Pixel Perfect level as I've done before, but what I did create works perfectly.  The JUnit tests cover the entire ViewModel, the entire Endpoint/Repository, and the entire API, so I'm happy with those.  I put in @Previews for the UI Views, so those are all easily tested.  My test coverage is something like 90% for what *can* be tested (no point in testing simple data models for example, or third-party libraries).  

## Did you copy any code or dependencies? Please make sure to attribute them here!
Almost none, but I did use some code snippets from StackOverflow to complete the JUnit tests.
I did get all of my library versions and dependencies from a previous project, because I knew the versions would all work together, and I could avoid "Dependency Hell".  This is why some of the libraries are a few months out of date.

## Is there any other information you’d like us to know?
Not really.  It all works, and there was no need to "hack" anything. It may not be to Square's company standards, but only because I don't know what those are.