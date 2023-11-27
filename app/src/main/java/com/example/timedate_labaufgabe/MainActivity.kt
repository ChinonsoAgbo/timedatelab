package com.example.timedate_labaufgabe

/**
 * MainActivity class for the Time and Date Lab Aufgabe application.
 *
 * This class manages the UI and functionality for configuring and displaying time and date differences
 * between Canada (Eastern Time) and Germany (Berlin Time).
 *
 * For the calculation of ( time seconds )minimum
 *          * minSdk = 31
 *          *      is required
 * @constructor Creates a new instance of the MainActivity class.
 * @version 1.0
 * @since 26.11.2023
 * @author Chinonso
 */
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.timedate_labaufgabe.ui.theme.TimeDate_LabAufgabeTheme
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class MainActivity : ComponentActivity() {

    private val europeBerlinZoneId = "Europe/Berlin"
    private val canadaZoneID = "America/Toronto"
    // Initialize mutable states for selected dates and times in Canada and Germany

    private var selectedCanadaDate by
    mutableLongStateOf(
        LocalDateTime.now(ZoneId.of(canadaZoneID))
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )


    private var selectedGermanDate by
    mutableLongStateOf(
        LocalDateTime.now(ZoneId.of(europeBerlinZoneId))
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )

    private var selectedCanadaTime by
    mutableLongStateOf(
        LocalDateTime.now(ZoneId.of(canadaZoneID))
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )


    private var selectedGermanTime by
    mutableLongStateOf(
        LocalDateTime.now(ZoneId.of(europeBerlinZoneId))
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TimeDate_LabAufgabeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    // Set up the UI and handle user interactions

                    var germanCalender = Calendar.getInstance(TimeZone.getTimeZone("Europe/Berlin"))
                    var canadaCalender =
                        Calendar.getInstance(TimeZone.getTimeZone("Canada/Eastern"))

                    // Set up initial date picker states for Canada and Germany

                    val stateCanadaDate = rememberDatePickerState(
                        initialSelectedDateMillis = canadaCalender.timeInMillis

                    )
                    val stateGermanDate = rememberDatePickerState(
                        initialSelectedDateMillis = germanCalender.timeInMillis

                    )

                    // Set up date formatters for Canada and Germany

                    val canadaDateFormatter =
                        remember { SimpleDateFormat("yyyy MMM dd", Locale.CANADA) }
                    val canadaTimeFormatter =
                        remember { SimpleDateFormat("hh:mm:ss a", Locale.CANADA) }
                    val germanDateFormatter =
                        remember { SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY) }
                    val germanTimeFormatter =
                        remember { SimpleDateFormat("HH:mm:ss", Locale.GERMANY) }


                    // Initialize mutable states for controlling visibility of date and time pickers
                    var showCanadaDatePicker by remember {
                        mutableStateOf(false)
                    }
                    var showGermanDatePicker by remember {
                        mutableStateOf(false)
                    }
                    var showGermanTimepicker by remember { mutableStateOf(false) }
                    var showCanadaTimepicker by remember { mutableStateOf(false) }


                    // Set up time picker states for Canada and Germany
                    val stateCanadaTime = rememberTimePickerState(
                        is24Hour = false
                    )
                    val stateGermanTime = rememberTimePickerState(is24Hour = true)

                    /**
                     * This section of code defines the user interface for configuring and displaying time and date differences
                     * between Canada (Eastern Time) and Germany (Berlin Time).
                     */
                    Text(
                        text = "Configure time and date",
                        fontFamily = FontFamily.Monospace,
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(10.dp) // Add padding or adjust as needed
                            .offset(y = 40.dp), // Adjust the y value as needed to shift it downwards

                        verticalArrangement = Arrangement.spacedBy(7.dp),

                        ) {

                        MyDropdown() // drop down box


                        // Canada Flag image
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.flag1),
                                contentDescription = "Flag 1",
                                modifier = Modifier.size(30.dp) // Adjust the size as needed

                            )
                        }
                        // Canada Date and Time configuration
                        Row(
                            verticalAlignment = Alignment.CenterVertically,

                            ) {

                            // Button to show Canada Date Picker

                            Button(
                                onClick = { showCanadaDatePicker = true },
                                modifier = Modifier
                                    .background(Color.Transparent),
                                shape = CircleShape, // Make it circular
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                // calender button
                                Text(
                                    fontSize = 35.sp, text = "\uD83D\uDCC5"
                                )
                            }
                            // Display selected Canada date

                            Text(
                                text = canadaDateFormatter.format(selectedCanadaDate/* get*/),
                                fontFamily = FontFamily.Monospace
                            )

                        }
                        // Canada Time configuration

                        Row(
                            verticalAlignment = Alignment.CenterVertically,

                            ) {

                            // Button to show Canada Time Picker

                            Button(
                                onClick = {
                                    showCanadaTimepicker = true
                                },
                                modifier = Modifier
                                    .background(Color.Transparent),
                                shape = CircleShape, // Make it circular
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    fontSize = 35.sp, text = "\uD83D\uDD50"
                                ) // calender button
                            }
                            // Display selected Canada time
                            Text(
                                text = canadaTimeFormatter.format(selectedCanadaTime),
                                fontFamily = FontFamily.Monospace
                            )

                        }


                        // German Flag image

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.flag2),
                                contentDescription = "Flag 2",
                                modifier = Modifier.size(35.dp) // Adjust the size as needed

                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,

                            ) {


                            Button(
                                onClick = { showGermanDatePicker = true },
                                modifier = Modifier
                                    .background(Color.Transparent),
                                shape = CircleShape, // Make it circular
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    fontSize = 35.sp, text = "\uD83D\uDCC5"
                                ) // calender button
                            }

                            Text(
                                text = germanDateFormatter.format(selectedGermanDate/* get*/),
                                fontFamily = FontFamily.Monospace
                            )
                            //Text(text = "1:30 pm", fontSize = 16.sp)

                        }
                        // German Date and Time configuration

                        Row(
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            // Button to show German Date Picker

                            Button(
                                onClick = {
                                    showGermanTimepicker = true/* Austarailien Calendar action */
                                },
                                modifier = Modifier
                                    .background(Color.Transparent),
                                shape = CircleShape, // Make it circular
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text(
                                    fontSize = 35.sp,
                                    text = "\uD83D\uDD50",
                                ) // calender button
                            }
                            // Display selected German date

                            Text(
                                text = germanTimeFormatter.format(selectedGermanTime) + " Uhr",
                                fontFamily = FontFamily.Monospace
                            )
                        }

                        // Display result row
                        Column(
                            modifier = Modifier
                                .padding(10.dp) // Add padding to the whole Column
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {
                            var day by remember { mutableLongStateOf(0) }
                            var hour by remember { mutableLongStateOf(0) }
                            var minutes by remember { mutableLongStateOf(0) }
                            var seconds by remember { mutableLongStateOf(0) }
                            var year by remember { mutableLongStateOf(0) }
                            var month by remember { mutableLongStateOf(0) }


                            // Calculate differences
                            day = calculateDayDifference(selectedGermanDate, selectedCanadaDate)
                            hour = calculateTimeDifference(
                                selectedGermanTime, selectedCanadaTime
                            ).toHours() % 24
                            minutes = calculateTimeDifference(
                                selectedGermanTime, selectedCanadaTime
                            ).toMinutes() % 60

                            /**
                             * For the calculation of seconds minimum
                             * minSdk = 31
                             *      is required
                             */
                            seconds = calculateTimeDifference(
                                selectedGermanTime,
                                selectedCanadaTime
                            ).toSeconds() % 60
                            year = day / 360
                            month = calculateMonthDifference(selectedGermanDate, selectedCanadaDate)

                            // Display total time and date differences

                            Text(
                                text = "Total time and date differences ",
                                fontFamily = FontFamily.Monospace,
                                style = MaterialTheme.typography.titleSmall
                            )
                            // Display differences

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Text(
                                    text = "Year(s): $year",
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "Month(s): $month",
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Text(
                                    text = "Day(s): $day",
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "Hour(s): $hour",
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                            ) {
                                Text(
                                    text = "Minute(s): $minutes",
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "Second(s): $seconds",
                                    fontFamily = FontFamily.Monospace
                                )
                            }


                        }


                    }


                    // Canada time picker dislplay dialog
                    if (showCanadaTimepicker) {
                        Dialog(onDismissRequest = {
                            showCanadaTimepicker = false
                        }) {
                            Column(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(16.dp)
                            ) {
                                TimePicker(
                                    state = stateCanadaTime,
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TimePickerDefaults.colors(),
                                    layoutType = TimePickerDefaults.layoutType()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Button(
                                    onClick = {

                                        canadaCalender = Calendar.getInstance()
                                        canadaCalender.set(
                                            Calendar.HOUR_OF_DAY, stateCanadaTime.hour
                                        )
                                        canadaCalender.set(Calendar.MINUTE, stateCanadaTime.minute)
                                        /* pass the selected time to the variable to store the new state of the time */
                                        selectedCanadaTime =
                                            (canadaCalender.timeInMillis)/* Save the new change */


                                        showCanadaTimepicker = false
                                    }, modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Text("Confirm")
                                }
                            }
                        }
                    }


                    // Germam time picker dislplay dialog
                    if (showGermanTimepicker) {
                        Dialog(onDismissRequest = {
                            showGermanTimepicker = false
                        }) {
                            Column(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(16.dp)
                            ) {
                                TimePicker(
                                    state = stateGermanTime,
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = TimePickerDefaults.colors(),
                                    layoutType = TimePickerDefaults.layoutType()
                                )

                                Spacer(modifier = Modifier.height(10.dp))

                                Button(
                                    onClick = {
                                        germanCalender =
                                            Calendar.getInstance() // new Calender instance
                                        germanCalender.set(
                                            Calendar.HOUR_OF_DAY, stateGermanTime.hour
                                        )
                                        germanCalender.set(Calendar.MINUTE, stateGermanTime.minute)
                                        germanCalender.isLenient = false
                                        /* pass the selected time to the variable to store the new state of the time */
                                        selectedGermanTime = (germanCalender.timeInMillis)
                                        showGermanTimepicker = false
                                    }, modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Text("Confirm")
                                }
                            }
                        }
                    }


                    // Date picker for canada
                    if (showCanadaDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = { showCanadaDatePicker = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    showCanadaDatePicker = false
                                    selectedCanadaDate = stateCanadaDate.selectedDateMillis!!

                                }) {
                                    Text(text = "confirm")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    showCanadaDatePicker = false
                                }) {
                                    Text(text = "Cancel")
                                }
                            },
                        ) {
                            DatePicker(state = stateCanadaDate)
                        }
                    }


                    // show calender dialog  for German

                    /**
                     * Change the display format in the calender to look like the german date formate
                     */
                    if (showGermanDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = { showGermanDatePicker = false },
                            confirmButton = {
                                TextButton(onClick = {
                                    showGermanDatePicker = false
                                    selectedGermanDate = stateGermanDate.selectedDateMillis!!

                                }) {
                                    Text(text = "confirm")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    showGermanDatePicker = false
                                }) {
                                    Text(text = "Cancel")
                                }
                            },
                        ) {
                            DatePicker(state = stateGermanDate)
                        }
                    }


                }

            }

        }
    }

    /**
     * A composable function that displays a dropdown menu allowing the user to select a time zone.
     *
     * @param isExpanded Whether the dropdown menu is expanded.
     * @param gender The selected time zone.
     * @param onExpandedChange Callback invoked when the dropdown menu's expansion state changes.
     */
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyDropdown() {
        val europeBerlinZoneId = "Europe/Berlin"

        var isExpanded by remember { mutableStateOf(false) }
        var canadaStateID by remember { mutableStateOf("America/Toronto") }
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = it }
        ) {

            TextField(
                value = canadaStateID,

                        onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor(),
                        textStyle = TextStyle(
                        fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            )


            )
            // Use Modifier.background to anchor the dropdown to the background
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    isExpanded = false
                },
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            ) {

                DropdownMenuItem(
                    text = {
                        Text(text = "America/Toronto",
                            fontFamily = FontFamily.Monospace
                        )
                    },
                    onClick = {
                        canadaStateID = "America/Toronto"
                        selectedCanadaTime =
                            LocalDateTime.now(ZoneId.of(canadaStateID)) // change the current zone id
                                .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        selectedCanadaDate = LocalDateTime.now(ZoneId.of(canadaStateID))
                            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

                        updateEuropeanZoneID()

                        isExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(text = "America/Vancouver",
                            fontFamily = FontFamily.Monospace
                        )
                    },
                    onClick = {
                        canadaStateID = "America/Vancouver"
                        selectedCanadaTime = LocalDateTime.now(ZoneId.of(canadaStateID))
                            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                        selectedCanadaDate = LocalDateTime.now(ZoneId.of(canadaStateID))
                            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

                        updateEuropeanZoneID()

                        isExpanded = false
                    }
                )

            }
        }
    }

    /**
     * updateEuropeanZoneID updates the european time
     */
    private fun updateEuropeanZoneID() {
        val europeBerlinZoneId = "Europe/Berlin"
        selectedGermanDate = LocalDateTime.now(ZoneId.of(europeBerlinZoneId))
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        selectedGermanTime = LocalDateTime.now(ZoneId.of(europeBerlinZoneId))
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}


/**
 * Calculates the day difference between two given dates.
 *
 * @param germanDate The date in Germany (Berlin Time).
 * @param canadaDate The date in Canada (Eastern Time).
 * @return The difference in days between the two dates.
 */

fun calculateDayDifference(germanDate: Long, canadaDate: Long): Long {
    val duration = Duration.between(
        Instant.ofEpochMilli(canadaDate), Instant.ofEpochMilli(germanDate)
    )
    return duration.toDays()
}

/**
 * Calculates the time difference between two given times.
 *
 * @param germanTime The time in Germany (Berlin Time).
 * @param canadaTime The time in Canada (Eastern Time).
 * @return The duration between the two times.
 */

fun calculateTimeDifference(germanTime: Long, canadaTime: Long): Duration {
    return Duration.between(
        Instant.ofEpochMilli(canadaTime), Instant.ofEpochMilli(germanTime)
    )
}


/**
 * Calculates the month difference between two given dates.
 *
 * @param germanDate The date in Germany (Berlin Time).
 * @param canadaDate The date in Canada (Eastern Time).
 * @return The difference in months between the two dates.
 */

fun calculateMonthDifference(germanDate: Long, canadaDate: Long): Long {
    val canadaZoneId = ZoneId.of("America/Toronto")
    val germanyZoneId = ZoneId.of("Europe/Berlin")

    return ChronoUnit.MONTHS.between(
        Instant.ofEpochMilli(canadaDate).atZone(canadaZoneId).toLocalDate(),
        Instant.ofEpochMilli(germanDate).atZone(germanyZoneId).toLocalDate()
    )
}
