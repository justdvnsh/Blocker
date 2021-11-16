package divyansh.tech.blocker.common

import android.os.Build
import android.telecom.Call
import android.telecom.CallScreeningService
import android.widget.Toast
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class BlockerScreeningService: CallScreeningService() {
    override fun onScreenCall(p0: Call.Details) {
        val phoneNumber = p0.handle.toString()
        var response = CallResponse.Builder()
        response = handlePhoneCall(response, phoneNumber)

        respondToCall(p0, response.build())
    }

    private fun handlePhoneCall(
        response: CallResponse.Builder,
        phoneNumber: String
    ): CallResponse.Builder {
        if (phoneNumber == "+916394015772") {
            response.apply {
                setRejectCall(true)
                setDisallowCall(true)
                setSkipCallLog(false)
                //
                displayToast(String.format("Rejected call from %s", phoneNumber))
            }
        } else {
            displayToast(String.format("Incoming call from %s", phoneNumber))
        }
        return response
    }

    private fun displayToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}