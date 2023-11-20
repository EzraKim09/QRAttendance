# QRAttendance
QR-Based Attendance System - 3rd Semester, OOM C3 Project

## Table of Contents
- [Usage](#usage)
  -[Student](#student)
  -[Instructor](#instructor)
  -[Admin](#admin)
- [Instructions](#instructions)
- [Forking / Modifying](#forking--modifying)
- [Libraries](#libraries)

## Usage
1. Download the apk called app-debug
2. Install in mobile phone
3. Login using ur college email ID (abc@iiita.ac.in)
   <img src="https://github.com/EzraKim09/QRAttendance/assets/117889472/7f460de2-4e5a-4183-bc0f-f113807ea3b0" width="200" height="400">



4. Note: To access Instructor and Admin Activity, a new separate Account has to be created!!!
   <img src="https://github.com/EzraKim09/QRAttendance/assets/117889472/a78003d2-82dd-405f-bb86-81b90e90b4f1" width="200" height="400">



### Student
1. Scan the QR code generated from the app (Cannot be used to scan external QR codes)
2. View Past Attendances
   <img src="https://github.com/EzraKim09/QRAttendance/assets/117889472/0f5922a5-836d-4cd2-97c5-cad2a2eb7ed7" width="200" height="400">



### Instructor
1. Login or Register to an Instructor Account
2. Create new class by entering the new class' details
   <img src="https://github.com/EzraKim09/QRAttendance/assets/117889472/f49a3876-152c-4bd4-a4f8-7ad719abe796" width="200" height="400">



4. Or view past classes created with the students that had attended
   <img src="https://github.com/EzraKim09/QRAttendance/assets/117889472/6dc95d7d-6002-45a2-8637-8564efa4fd27" width="200" height="400">



### Admin
1. Login or Register to an Admin Account
2. Check the classes that had been created as well as Instructors and Admins Accounts
   <img src="https://github.com/EzraKim09/QRAttendance/assets/117889472/140e7167-b3fc-4e92-9cbf-637924f63c68" width="200" height="400">



## Instructions
- The app is designed in a way that Students, Instructors and Admins will not use a same system.
- But while testing the system out, u may need to act as an instructor (creating new classes) and students (scanning the classes) and admins.
- In doing so, u may encounter unexpected scenarios. So, u need to understand how auth acc are handled in the app and other problems that may occur.
  ### Auth Account System
  - As soon as u open the app, u have to login ur college email acc. So that is the acc u will be using at that point.
  - But when u login (creating a new acc) as an instructor/admin, the auth acc is switched from ur college email acc to that created instructor/admin acc.
  - So before scanning the QR code again, make sure to sign out from the main page first - and then resignin to ur college email again.
  - If not, the attendance will be marked for the instructor/admin acc u had created.
  - And when checking the past attendance data, it will show for the instructor/admin acc.
  ### Attendance Database System
  - Suppose an instructor create a new class, and no student had scan that code yet.
  - When a student click View Past Attendance to check for that particular class, it will not appear there yet.
  - That is because until at least one student attend the class, the created class is not considered for attendable class.
  - As soon as at least one student scan that class, it will appear on every student View Past Attendance page.
 
## Forking / Modifying
1̶. C̶l̶o̶n̶e̶ a̶n̶d̶ i̶n̶s̶t̶a̶l̶l̶ t̶h̶e̶ p̶r̶o̶j̶e̶c̶t̶ t̶o̶ u̶r̶ s̶y̶s̶t̶e̶m̶. 
2̶. C̶r̶e̶a̶t̶e̶ a̶ F̶i̶r̶e̶b̶a̶s̶e̶ P̶r̶o̶j̶e̶c̶t̶ u̶s̶i̶n̶g̶ R̶e̶a̶l̶t̶i̶m̶e̶ D̶a̶t̶a̶b̶a̶s̶e̶, E̶m̶a̶i̶l̶a̶n̶d̶P̶a̶s̶s̶w̶o̶r̶d̶ S̶i̶g̶n̶i̶n̶ a̶n̶d̶ G̶o̶o̶g̶l̶e̶ S̶i̶g̶n̶i̶n̶
3̶. L̶i̶n̶k̶ t̶h̶i̶s̶ p̶r̶o̶j̶e̶c̶t̶ t̶o̶ t̶h̶e̶ F̶i̶r̶e̶b̶a̶s̶e̶ P̶r̶o̶j̶e̶c̶t̶ (̶p̶r̶e̶f̶e̶r̶r̶a̶b̶l̶y̶ u̶s̶i̶n̶g̶ A̶n̶d̶r̶o̶i̶d̶ S̶t̶u̶d̶i̶o̶)̶ 
4̶. A̶d̶d̶ t̶h̶e̶ S̶H̶A̶1̶ f̶i̶n̶g̶e̶r̶p̶r̶i̶n̶t̶ o̶f̶ t̶h̶e̶ p̶r̶o̶j̶e̶c̶t̶ t̶o̶ y̶o̶u̶r̶ F̶i̶r̶e̶b̶a̶s̶e̶'s̶ S̶H̶A̶ c̶e̶r̶t̶i̶f̶i̶c̶a̶t̶e̶ f̶i̶n̶g̶e̶r̶p̶r̶i̶n̶t̶s̶ 
5̶. M̶o̶d̶i̶f̶y̶ t̶h̶e̶ s̶o̶u̶r̶c̶e̶ c̶o̶d̶e̶ a̶s̶ n̶e̶e̶d̶e̶d̶
  ### Currently not Available for Modification due to Database Issue -- Just use the apk provided for the time being

## Libraries
1. glide - com.github.bumptech.glide:glide:4.16.0
2. code-scanner - com.github.yuriy-budiyev:code-scanner:2.3.2
3. zxing-android-embeded - com.journeyapps:zxing-android-embedded:4.3.0
