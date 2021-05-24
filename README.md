# CallHangupAndroid
## UPDATE
This is avalilable only on API < 28 (Pie).

## Info
In this repository you can find a PoC Android application which demonstrates using Java reflection for the purpose of hanging-up a call programmatically. 
## Important parts of code
### Declaring permissions in `AndroidManifest.xml`

`&lt; uses-permission android:name="android.permission.READ_PHONE_STATE" /> <br/>
&lt; uses-permission android:name="android.permission.CALL_PHONE" /> <br/>
&lt; uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" /> <br/>`

### Java reflection 
In computer science, reflection is the ability of a computer program to examine, introspect, and modify its own structure and behavior at runtime. (Source: https://en.wikipedia.org/wiki/Reflection_(computer_programming)) <br/> 
For example, say you have an object of an unknown type in Java, and you would like to call a `doSomething` method on it if one exists. Java's static typing system isn't really designed to support this unless the object conforms to a known interface, but using reflection, your code can look at the object and find out if it has a method called `doSomething` and then call it if you want to. (Source: https://stackoverflow.com/questions/37628/what-is-reflection-and-why-is-it-useful) <br/><br/>
Java reflection is show in `public void declinePhone(Context context)` method inside `PhoneStateReceiver.java` file. (CallHangupAndroid/app/src/main/java/com/example/rinorodin/callhangup/PhoneStateReceiver.java
)

### Asking user for permissions explicitly
 Even if you have permissions set in the AndroidManifest.xml file, in Android versions 6.0+,
 you still have to explicitly ask the user for the permissions if they fall under the category of dangerous permissions. READ_PHONE_STATE and CALL_PHONE are in that category. You can do that in the `onCreate()` method in `MainActivity.java`

`if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE};
                requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE);
            }
}`

### Registering broadacast reciever
If you register `PhoneStateReceiver` in the `AndroidManifest.xml` the reciever will be active even when the app is closed. This will block all the incoming calls. The only way to disable it will be by deleting the app. You can do it by adding following lines to the `AndroidManifest.xml` : <br/> 
`<receiver
            android:name=".tools.CallBlock"
            android:enabled="true">
            <intent-filter android:priority="100">
                <action android:name="android.intent.action.PHONE_STATE" />
            </intent-filter>
</receiver>` <br/>  <br/> 
However, this app uses a diferent approach and registers broadcast reviecer in `setOnClickListener` method in `MainActivity.java`. After the call has been disconnected the broadcast reciever becomes unregistered. In this way the broadcast reciever will be active only during the short time of disconnecting the call after you click the Hangup button. <br/> 
### How to use this app
To test this app you first have to install it on on your phone. Than you can make a call using native Android calling app. While in call you have to navigate back to your home screen, find this app, open it and click on the Hangup button. The app will than disconnect the cal. (Note: The method will disconnect the call regardless of the phone state. Meaning that the call will be disconnected while ringing and also if the method has been executed during the accepted phone call.)

### Android versions that support this app
The app has been tested and proved working in android versions 5.0(Lollipop), 6.0(Marshmallow), 7.0(Nougat) and  	8.0(Oreo).
