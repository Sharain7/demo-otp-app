
# Demo OTP Android App
# Overview
Welcome to the Demo OTP Android app! This application utilizes Firebase Authentication for phone number verification, ensuring a secure and straightforward user authentication process. The provided one-time code (OTP) is valid for 60 seconds, and users are limited to a maximum of 10 SMS verifications per day.

# Installation
- Clone the Repository:
   git clone https://github.com/your-username/demo-otp-android.git

- Open in Android Studio: Import the project into Android Studio. Ensure that you have the necessary SDK and dependencies installed.
- Firebase Configuration: Create a Firebase project and configure your app according to Firebase guidelines.Update the google-services.json file with your Firebase project settings. 
 - Run the App: Build and run the app on an Android emulator or physical device.
# Usage
Phone Number Verification:

Enter your phone number in the app.
Click on the "Send OTP" button.
Check your SMS for the received OTP.
OTP Validation:

Enter the received OTP within the 60-second validity window.
If valid, the user will be successfully authenticated.
Configuration
The app includes configurations for Firebase Authentication.
You can customize the number of SMS verifications allowed per day in the Firebase console.
Features
Firebase Authentication: Secure and reliable phone number verification.
OTP Expiry: Codes are valid for 60 seconds, enhancing security.
Usage Limits: Users are restricted to a maximum of 10 SMS verifications per day.

# Troubleshooting
In case of any issues or errors, please refer to the troubleshooting section in the Firebase Authentication documentation.
# Contact
For any inquiries or feedback, feel free to contact us at sharainlmc7@gmail.com.

# License
This project is licensed under the MIT License.
