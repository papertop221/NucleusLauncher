package com.minimalist.launcher

import android.app.Application
import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LauncherViewModel(application: Application) : AndroidViewModel(application) {
    private val packageManager = application.packageManager
    
    private val _allApps = MutableStateFlow<List<AppInfo>>(emptyList())
    
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    private val _filteredApps = MutableStateFlow<List<AppInfo>>(emptyList())
    val filteredApps: StateFlow<List<AppInfo>> = _filteredApps.asStateFlow()

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            val intent = Intent(Intent.ACTION_MAIN, null).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
            val apps = packageManager.queryIntentActivities(intent, 0).map { resolveInfo ->
                AppInfo(
                    label = resolveInfo.loadLabel(packageManager).toString(),
                    packageName = resolveInfo.activityInfo.packageName,
                    icon = resolveInfo.loadIcon(packageManager)
                )
            }.sortedBy { it.label.lowercase() }
            _allApps.value = apps
            filterApps("")
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        filterApps(query)
    }

    private fun filterApps(query: String) {
        val filtered = if (query.isEmpty()) {
            _allApps.value
        } else {
            _allApps.value.filter { 
                it.label.contains(query, ignoreCase = true) || 
                it.packageName.contains(query, ignoreCase = true)
            }
        }
        _filteredApps.value = filtered
        
        // Auto-launch logic: If only 1 app matches and query is not empty
        if (query.isNotEmpty() && filtered.size == 1) {
            launchApp(filtered[0])
            // Clear query after launch
            _searchQuery.value = ""
            filterApps("")
        }
    }

    fun launchApp(app: AppInfo) {
        try {
            val intent = packageManager.getLaunchIntentForPackage(app.packageName)
            intent?.let {
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                getApplication<Application>().startActivity(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
