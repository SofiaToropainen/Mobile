# Chore-Application

A mobile application designed to make chore-sharing easier within families. Users can add the chores they complete, see chores done by others, and view statistics based on completed tasks.

# Example videos of Chore Application running
- Download demo video, joining to already created family: (https://github.com/SofiaToropainen/Chore-Application/releases/download/v1.0.0/choreapp_english_oldfamily.1.webm)
- Download demo video, creating a new family group: (https://github.com/SofiaToropainen/Chore-Application/releases/download/v1.0.1/choreapp_english_newfamily.3.webm)

# Features
- Add and track personal chores.
- View chores completed by other family members.
- Earn points based on chores.
- Generate statistics of completed chores.
- Firebase Authentication for login.
- Firebase Data Storage for saving chore data.

# Technology
- Java
- Android Studio
- Android SDK & Gradle
- Firebase Authentication
- Firebase Cloud Firestore
- Google Play Services 

# Installation
**1. Clone repository:**

- git clone https://github.com/SofiaToropainen/Chore-Application.git

**2. Open the project in Android Studio:**
- Select "Open an existing project" and navigate to the cloned folder.
  
**3. Create your own Firebase-project:**

   3.1 Go to Firebase Console (https://console.firebase.google.com/).
   
   3.2. Create a new Firebase project.
   
   3.3. Add your application package name (com.example.choreapplication)
   
   3.4. Download google-services.json and place it to app/ folder of the project (in Android Studio).
  
**4. Sync Gradle dependencies:**
- In Android Studio, go to: File -> Sync Project with Gradle Files.
  
**5. Run the application:**
- You can test the app on a physical device or an emulator.
  
**Note!**
The application requires internet connection because it uses Firebase Authentication and Firebase Data Storage.
If testing on an emulator, use one that supports Google Play services and has internet access.
- For example: Pixel 4 API 33.

# Usage
1. Sign up
- Register a new account or log in with an existing one.

2. Join or create a family code
- If a family member has already created a family code, use it to join the family.
- Otherwise, create your own family code and share it with your family members.

3. Add a new chore
- Add a chore to the list (if it does not exist already).
- Name the chore and set a fixed number of points.

4. Add a completed chore
- Choose the chore you have completed from the spinner.
- Add the minutes used to complete it.
- Press the "Add" button.

5. View statistics
- Tap the "Statistics" button.
- Choose whether to see data from the past week or from the entire usage history.

6. Log out
- Log out when finished.

# Author
Created by Sofia Toropainen in 2025.
