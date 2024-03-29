package com.fred_projects.education.main.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.fred_projects.R
import com.fred_projects.education.main.view_model.AddEditVM
import com.fred_projects.ui.*
import kotlinx.coroutines.launch

@Composable
fun CUItem(navController: NavController, onClick: () -> Uri, viewModel: AddEditVM = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    var pwName by rememberSaveable { mutableStateOf("") }
    var student by rememberSaveable { mutableStateOf("") }
    var variant by rememberSaveable { mutableStateOf("") }
    var lvl by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var mark by rememberSaveable { mutableStateOf("") }
    var photo by rememberSaveable { mutableStateOf("") }
    if (viewModel.pwDateS.collectAsState().value.isNotEmpty() && pwName == ""){
        pwName = viewModel.pwNameS.collectAsState().value
        student = viewModel.pwStudentS.collectAsState().value
        variant = viewModel.pwVariantS.collectAsState().value
        lvl = viewModel.pwLvlS.collectAsState().value
        date = viewModel.pwDateS.collectAsState().value
        mark = viewModel.pwMarkS.collectAsState().value
        photo = viewModel.pwImageS.collectAsState().value
    }
    val scaffoldState = rememberScaffoldState()
    val incorrectPW = stringResource(R.string.incorrectPW)
    val incorrectStudent = stringResource(R.string.incorrectStudent)
    val incorrectVariant = stringResource(R.string.incorrectVariant)
    val incorrectLVL = stringResource(R.string.incorrectLVL)
    val incorrectDate = stringResource(R.string.incorrectDate)
    val incorrectMark = stringResource(R.string.incorrectMark)
    var result: Int
    var errors: String
    Scaffold(
        floatingActionButton = {
            FredFloatingActionButton(
                {
                    errors = ""
                    result = viewModel.addRecord(pwName, student, variant, lvl, date, mark, photo)
                    if (result == 0) navController.navigateUp()
                    if (result.toString().contains("6")) errors += "$incorrectPW\n"
                    if (result.toString().contains("5")) errors += "$incorrectStudent\n"
                    if (result.toString().contains("4")) errors += "$incorrectVariant\n"
                    if (result.toString().contains("3")) errors += "$incorrectLVL\n"
                    if (result.toString().contains("2")) errors += "$incorrectDate\n"
                    if (result.toString().contains("1")) errors += incorrectMark
                    scope.launch { scaffoldState.snackbarHostState.showSnackbar(errors) }
                }, Icons.Default.Done, stringResource(R.string.add)
            )
        }, scaffoldState = scaffoldState) { padding ->
        LazyColumn(Modifier.fillMaxSize().padding(padding)) {
            item {
                Column(Modifier.fillMaxWidth(), Arrangement.Center, Alignment.CenterHorizontally) {
                    Spacer(Modifier.height(32.dp))
                    Text("${stringResource(R.string.add)}/${stringResource(R.string.update)}", textAlign = TextAlign.Center, style = MaterialTheme.typography.h5)
                    Spacer(Modifier.height(32.dp))
                    FredTextField(pwName, { pwName = it }, R.string.enterPW)
                    Spacer(Modifier.height(8.dp))
                    FredTextField(student, { student = it }, R.string.enterStudent)
                    Spacer(Modifier.height(8.dp))
                    FredTextField(variant, { if (((it.toIntOrNull() != null) && (it.toInt() > 0)) || (it == "")) variant = it }, R.string.enterVariant)
                    Spacer(Modifier.height(8.dp))
                    FredTextField(lvl, { if (((it.toIntOrNull() != null) && (it.toInt() > 0)) || (it == "")) lvl = it }, R.string.enterLVL)
                    Spacer(Modifier.height(8.dp))
                    FredTextField(date, { date = it }, R.string.enterDate)
                    Spacer(Modifier.height(8.dp))
                    FredTextField(mark, { if (((it.toIntOrNull() != null) && (it.toInt() > 0)) || (it == "")) mark = it }, R.string.enterMark)
                    Spacer(Modifier.height(8.dp))
                    Image(rememberAsyncImagePainter(photo.toUri()), stringResource(R.string.photo), Modifier.height(200.dp))
                    FredButton({ photo = onClick.invoke().toString() }, stringResource(R.string.take_picture))
                    FredButton({ navController.navigateUp() }, stringResource(R.string.go_back))
                }
            }
        }
    }
}