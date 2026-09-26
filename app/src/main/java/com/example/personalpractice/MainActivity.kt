package com.example.personalpractice

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.personalpractice.ui.theme.PersonalpracticeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PersonalpracticeTheme {

                var status by remember {
                    mutableStateOf("Choose a component to test")
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text("Android Four Components")

                    Spacer(modifier = Modifier.height(24.dp))

                    // ACTIVITY → SERVICE
                    Button(
                        onClick = {

                            val intent = Intent(
                                this@MainActivity,
                                MyService::class.java
                            )

                            startService(intent)

                            status = "Service started. Check Logcat."
                        }
                    ) {
                        Text("Start Service")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // ACTIVITY → BROADCAST RECEIVER
                    Button(
                        onClick = {

                            val intent = Intent(
                                this@MainActivity,
                                MyBroadcastReceiver::class.java
                            )

                            sendBroadcast(intent)

                            status = "Broadcast sent. Check Logcat."
                        }
                    ) {
                        Text("Send Broadcast")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // ACTIVITY → CONTENT PROVIDER
                    Button(
                        onClick = {

                            val uri = Uri.parse(
                                "content://com.example.personalpractice.provider/message"
                            )

                            val cursor = contentResolver.query(
                                uri,
                                null,
                                null,
                                null,
                                null
                            )

                            if (cursor != null && cursor.moveToFirst()) {

                                val messageIndex =
                                    cursor.getColumnIndex("message")

                                if (messageIndex >= 0) {
                                    status =
                                        cursor.getString(messageIndex)
                                }

                                cursor.close()

                            } else {

                                status = "No data returned."

                            }
                        }
                    ) {
                        Text("Query Content Provider")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(status)
                }
            }
        }
    }
}