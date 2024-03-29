package com.fred_projects.education.rest_api.math.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fred_projects.R
import com.fred_projects.education.rest_api.Resource
import com.fred_projects.education.rest_api.math.view_model.MathVM
import com.fred_projects.ui.*

@Composable
fun MathScreen(navController: NavController, viewModel: MathVM = hiltViewModel()) {
    var expression by rememberSaveable { mutableStateOf("") }
    val result = viewModel.resultSF.collectAsState().value.second
    val status = viewModel.resultSF.collectAsState().value.first
    Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
        FredTextField(expression, { expression = it }, R.string.enter_expression)
        Spacer(Modifier.height(8.dp))
        FredButton({ viewModel.onSearch(expression) }, stringResource(R.string.result))
        Spacer(Modifier.height(8.dp))
        LazyColumn {
            item {
                Column(Modifier.fillMaxSize(), Arrangement.Center, Alignment.CenterHorizontally) {
                    Text(
                        when(status) {
                            Resource.LOADING -> stringResource(R.string.wait)
                            Resource.SUCCESS -> stringResource(R.string.result_is)
                            Resource.ERROR -> stringResource(R.string.error)
                            Resource.NONE -> ""
                            Resource.NO_INTERNET -> stringResource(R.string.no_internet)
                            Resource.SOMETHING_WRONG -> stringResource(R.string.something_wrong)
                        }
                    )
                    if (result != null) MathInfoItem(Modifier.fillMaxSize().padding(16.dp), result.expression, result.result)
                    FredButton({ navController.navigateUp() }, stringResource(R.string.go_back))
                }
            }
        }
    }
}