package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.RiskActionType
import com.example.model.RiskCategory
import com.example.model.RiskLevel
import com.example.model.ScanStats
import com.example.model.ScanStatus
import com.example.model.SecurityRiskItem
import com.example.model.ThreatItem
import com.example.scanner.MalwareEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ScannerViewModel(application: Application) : AndroidViewModel(application) {

    private val engine = MalwareEngine(application.applicationContext)

    private val _scanStatus = MutableStateFlow(ScanStatus.IDLE)
    val scanStatus: StateFlow<ScanStatus> = _scanStatus.asStateFlow()

    private val _scannedCount = MutableStateFlow(0)
    val scannedCount: StateFlow<Int> = _scannedCount.asStateFlow()

    private val _totalAppsToScan = MutableStateFlow(100)
    val totalAppsToScan: StateFlow<Int> = _totalAppsToScan.asStateFlow()

    private val _currentScanningApp = MutableStateFlow("")
    val currentScanningApp: StateFlow<String> = _currentScanningApp.asStateFlow()

    private val _currentScanPhase = MutableStateFlow("Siap memindai")
    val currentScanPhase: StateFlow<String> = _currentScanPhase.asStateFlow()

    private val _threatsList = MutableStateFlow<List<ThreatItem>>(emptyList())
    val threatsList: StateFlow<List<ThreatItem>> = _threatsList.asStateFlow()

    private val _risksList = MutableStateFlow<List<SecurityRiskItem>>(emptyList())
    val risksList: StateFlow<List<SecurityRiskItem>> = _risksList.asStateFlow()

    private val _scanStats = MutableStateFlow(ScanStats())
    val scanStats: StateFlow<ScanStats> = _scanStats.asStateFlow()

    private val _selectedThreat = MutableStateFlow<ThreatItem?>(null)
    val selectedThreat: StateFlow<ThreatItem?> = _selectedThreat.asStateFlow()

    private val _selectedRisk = MutableStateFlow<SecurityRiskItem?>(null)
    val selectedRisk: StateFlow<SecurityRiskItem?> = _selectedRisk.asStateFlow()

    private val _showTipsDialog = MutableStateFlow(false)
    val showTipsDialog: StateFlow<Boolean> = _showTipsDialog.asStateFlow()

    private val _realtimeProtection = MutableStateFlow(true)
    val realtimeProtection: StateFlow<Boolean> = _realtimeProtection.asStateFlow()

    init {
        // Auto-run initial lightweight scan or set initial state
        startScan(includeSimulation = false)
    }

    fun startScan(includeSimulation: Boolean = false) {
        viewModelScope.launch {
            _scanStatus.value = ScanStatus.SCANNING
            _scannedCount.value = 0
            _threatsList.value = emptyList()
            _risksList.value = emptyList()

            val result = engine.performScan(
                includeTestThreat = includeSimulation,
                onProgress = { count, total, appName, phase ->
                    _scannedCount.value = count
                    _totalAppsToScan.value = total
                    _currentScanningApp.value = appName
                    _currentScanPhase.value = phase
                }
            )

            _threatsList.value = result.threats
            _risksList.value = result.risks
            _scanStats.value = result.stats

            // Determine final status strictly according to user rules:
            // 1. If threats/malware found -> THREATS_FOUND ("Virus / Malware Ditemukan!")
            // 2. If no threats but risks found -> RISKY ("HP kamu beresiko terkena virus")
            // 3. If no threats and no risks -> SAFE ("HP kamu aman")
            _scanStatus.value = when {
                result.threats.isNotEmpty() -> ScanStatus.THREATS_FOUND
                result.risks.isNotEmpty() -> ScanStatus.RISKY
                else -> ScanStatus.SAFE
            }
        }
    }

    fun startSimulationWithThreats() {
        startScan(includeSimulation = true)
    }

    fun simulateRiskyStateOnly() {
        viewModelScope.launch {
            _scanStatus.value = ScanStatus.SCANNING
            _threatsList.value = emptyList()
            _risksList.value = emptyList()

            val result = engine.performScan(
                includeTestThreat = false,
                onProgress = { count, total, appName, phase ->
                    _scannedCount.value = count
                    _totalAppsToScan.value = total
                    _currentScanningApp.value = appName
                    _currentScanPhase.value = phase
                }
            )

            // Inject simulated high risk to demonstrate "HP kamu beresiko terkena virus"
            val sampleRisks = result.risks.toMutableList()
            if (sampleRisks.isEmpty()) {
                sampleRisks.add(
                    SecurityRiskItem(
                        id = "demo_risk_sideload",
                        title = "Pemasangan Sumber Tidak Dikenal Diizinkan",
                        riskLevel = RiskLevel.HIGH,
                        category = RiskCategory.UNKNOWN_INSTALL_SOURCE,
                        description = "Izin menginstal APK dari browser dan aplikasi chat terdeteksi aktif di sistem.",
                        impact = "File APK berbahaya dapat terunduh dan terpasang tanpa verifikasi Google Play Protect.",
                        howToFix = "Matikan opsi 'Install Unknown Apps' pada browser dan file manager Anda.",
                        actionType = RiskActionType.OPEN_SECURITY_SETTINGS
                    )
                )
                sampleRisks.add(
                    SecurityRiskItem(
                        id = "demo_risk_debug",
                        title = "Mode USB Debugging Aktif di Opsi Pengembang",
                        riskLevel = RiskLevel.MEDIUM,
                        category = RiskCategory.SYSTEM_VULNERABILITY,
                        description = "Port ADB terbuka memungkinkan pengiriman perintah sistem dari kabel komputer.",
                        impact = "Perangkat rentan disusupi script otomatis saat di-charge di tempat umum.",
                        howToFix = "Nonaktifkan 'USB Debugging' di Opsi Pengembang jika tidak dibutuhkan.",
                        actionType = RiskActionType.OPEN_DEV_SETTINGS
                    )
                )
            }

            _threatsList.value = emptyList()
            _risksList.value = sampleRisks
            _scanStats.value = result.stats.copy(
                threatsFoundCount = 0,
                risksFoundCount = sampleRisks.size
            )
            _scanStatus.value = ScanStatus.RISKY
        }
    }

    fun removeThreat(threat: ThreatItem) {
        val updated = _threatsList.value.filter { it.id != threat.id }
        _threatsList.value = updated
        _scanStats.value = _scanStats.value.copy(threatsFoundCount = updated.size)

        if (updated.isEmpty()) {
            _scanStatus.value = if (_risksList.value.isNotEmpty()) ScanStatus.RISKY else ScanStatus.SAFE
        }
        if (_selectedThreat.value?.id == threat.id) {
            _selectedThreat.value = null
        }
    }

    fun resolveRisk(riskId: String) {
        val updated = _risksList.value.filter { it.id != riskId }
        _risksList.value = updated
        _scanStats.value = _scanStats.value.copy(risksFoundCount = updated.size)

        if (_threatsList.value.isEmpty() && updated.isEmpty()) {
            _scanStatus.value = ScanStatus.SAFE
        }
        if (_selectedRisk.value?.id == riskId) {
            _selectedRisk.value = null
        }
    }

    fun selectThreat(threat: ThreatItem?) {
        _selectedThreat.value = threat
    }

    fun selectRisk(risk: SecurityRiskItem?) {
        _selectedRisk.value = risk
    }

    fun setShowTips(show: Boolean) {
        _showTipsDialog.value = show
    }

    fun toggleRealtimeProtection() {
        _realtimeProtection.value = !_realtimeProtection.value
    }

    fun getUninstallIntent(packageName: String) = engine.uninstallPackage(packageName)
    fun getAppSettingsIntent(packageName: String) = engine.openAppSettings(packageName)
    fun getDevSettingsIntent() = engine.openDeveloperSettings()
    fun getAccessibilitySettingsIntent() = engine.openAccessibilitySettings()
    fun getSecuritySettingsIntent() = engine.openSecuritySettings()
}
