# QRAttendance
QR-Based Attendance System - 3rd Semester, OOM C3 Project

## Table of Contents
- [Usage](#usage)
  -[Student](#student)
  -[Instructor](#instructor)
  -[Admin](#admin)
- [Instructions for Developers](#instructions-for-developers)
- [Forking / Modifying](#forking--modifying)

## Usage
1. Download the apk called app-debug
2. Install in mobile phone
3. Login using ur college email ID (abc@iiita.ac.in)
   ![MainActivity](https://github.com/EzraKim09/QRAttendance/assets/117889472/7f460de2-4e5a-4183-bc0f-f113807ea3b0)

4. Note: To access Instructor and Admin Activity, a new separate Account has to be created!!!
   ![LoginActivity](https://github.com/EzraKim09/QRAttendance/assets/117889472/a78003d2-82dd-405f-bb86-81b90e90b4f1)

### Student
1. Scan the QR code generated from the app (Cannot be used to scan external QR codes)
2. View Past Attendances
   ![ScannerActivity](https://github.com/EzraKim09/QRAttendance/assets/117889472/0f5922a5-836d-4cd2-97c5-cad2a2eb7ed7)

### Instructor
1. Login or Register to an Instructor Account
2. Create new class by entering the new class' details
   ![CreateNewClassActivity](https://github.com/EzraKim09/QRAttendance/assets/117889472/f49a3876-152c-4bd4-a4f8-7ad719abe796)

3. Or view past classes created with the students that had attended
   ![ViewPastClassesActivity](https://github.com/EzraKim09/QRAttendance/assets/117889472/6dc95d7d-6002-45a2-8637-8564efa4fd27)

### Admin
1. Login or Register to an Admin Account
2. Check the classes that had been created as well as Instructors and Admins Accounts
   ![ViewCreatedAccountsActivity](https://github.com/EzraKim09/QRAttendance/assets/117889472/140e7167-b3fc-4e92-9cbf-637924f63c68)

## Instructions for Developers
- The app is designed in a way that Students, Instructors and Admins will not use a same system.
- But while testing the system out, u may need to act as an instructor (creating new classes) and students (scanning the classes) and admins.
- In doing so, u may encounter unexpected scenarios. So, u need to understand how auth acc are handled in the app.
  ### Auth Account System
  - As soon as u open the app, u have to login ur college email acc. So that is the acc u will be using at that point.
  - But when u login (creating a new acc) as an instructor/admin, the auth acc is switched from ur college email acc to that created instructor/admin acc.
  - So before scanning the QR code again, make sure to sign out from the main page first - and then resignin to ur college email again.
  - If not, the attendance will be marked for the instructor/admin acc u had created.
  - And when checking the past attendance data, it will show for the instructor/admin acc.
 
## Forking / Modifying
1. Clone and install the project to ur system.
2. Create a Firebase Project using Realtime Database, EmailandPassword Signin and Google Signin
3. Link this project to the Firebase Project (preferrably using Android Studio)
4. Add the SHA1 fingerprint of the project to your Firebase's SHA certificate fingerprints
5. Modify the source code as needed
