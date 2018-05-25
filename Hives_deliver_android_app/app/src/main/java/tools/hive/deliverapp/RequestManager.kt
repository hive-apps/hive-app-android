package tools.hive.deliverapp

import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class RequestManager {

    companion object {

        private var url = "http://212.47.242.70:9000/v1/"
        private var token = ""

        fun signIn(mWorkspace: String, mEmail: String, mPassword: String) : String {

            url += "signin"

            var obj = URL(url)

            with(obj.openConnection() as HttpURLConnection) {
                // optional default is GET
                requestMethod = "POST";
                addRequestProperty("Content-Type", "application/json")
                addRequestProperty("Accept", "application/json")

                val cred = JSONObject()
                cred.put("workspace", mWorkspace)
                cred.put("email", mEmail)
                cred.put("password", mPassword)

                val wr = OutputStreamWriter(outputStream)
                wr.write(cred.toString())
                wr.flush()

                //display what returns the POST request

                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    it.close()
//                    println("Response : $response")
                    if (responseCode == 200) {
                        return ""
                    }
                }
            }
            return "Error: incorrect credentials."
        }

        fun findRoute(routeNumber: String) : Boolean {

            url += "direction/" + routeNumber

            var obj = URL(url)

            with(obj.openConnection() as HttpURLConnection) {
                // optional default is GET
                requestMethod = "GET"


                BufferedReader(InputStreamReader(inputStream)).use {
                    val response = StringBuffer()

                    var inputLine = it.readLine()
                    while (inputLine != null) {
                        response.append(inputLine)
                        inputLine = it.readLine()
                    }
                    println(response.toString())
                }
            }
            return false
        }

    }
}
