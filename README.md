## Blocker

[Here](https://drive.google.com/file/d/1tMhVuqLrf33FM8dlvpiLadaypVJ4QsXs/view?usp=sharing) is the video demonstrating the functionality.

- [x] The user will be able to add & delete contacts/numbers to block.

- [x] The user can pick the contact from the contact list or just enter the number.

- [x] The app should listen for incoming calls and disconnect them if the caller is from the list of blocked contacts.

- [x] The app should notify the call which were blocked by the app.

## Requirements:

- [x] The app should follow MVVM architecture.

- [x] The app should use a database of your choice to store the blocked contacts.

- [x] The app should ask for the required permissions.

- [x] The app should work on support devices with API 21 and above.

- [x] The app should be written in kotlin.

- [x] The app should use coroutines for background operations.

## Things we would love to see:

- [x] The app should have a minimal UI. (Show us your creativity)

- [x] Use Dependency Injection

## Known Issues - 

- Currently, the call from blocked numbers is still visible for a second or two before it gets disconnected. I researched about it online,
but the Telephony service API was deprecated from Android 7 in replacement for TelecomManager Service which is available from Android 7.
The problem there was that the API to end the call is soon going to be deprecated again in replacement for CallScreeningService, that means,
only if the app has selected this particular app as the default dialler app, then only the calls could be answered or disconnected programatically otherwise not.
This was a problem, however I have worked around that issue, by making use of TelephonyService for Android Version 4 - 7. Then from Android 7 onwards till Android 11,
I have used TelecomManager. Currently, this app would not function properly in the very latest Android 12.

- The second issue is that I am simply looping over the blocked contacts list to match the number. This takes O(n) time, which would take a long time for a a long block list (though very rare)
. We can maybe use a different searching techniques to optimize the search algorithm and make it a little faster.

