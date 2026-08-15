package com.example.sitiosturisticosecuador.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sitiosturisticosecuador.data.datastore.SettingDataStore
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(settingDataStore: SettingDataStore) {
    val scope = rememberCoroutineScope()
    val darkMode by settingDataStore.darkModeFlow.collectAsState(initial = false)

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Ajustes de Usuario", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Modo Oscuro (DataStore)")
            Switch(
                checked = darkMode,
                onCheckedChange = { isChecked ->
                    scope.launch {
                        settingDataStore.saveDarkMode(isChecked)
                    }
                }
            )
        }
    }
}