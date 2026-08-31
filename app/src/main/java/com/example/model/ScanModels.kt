package com.example.model

enum class ScanStatus {
    IDLE,
    SCANNING,
    SAFE,           // "HP kamu aman"
    RISKY,          // "HP kamu beresiko terkena virus"
    THREATS_FOUND   // "Virus / Malware Ditemukan"
}

enum class ThreatType(val displayName: String) {
    MALWARE("Malware"),
    TROJAN("Trojan"),
    SPYWARE("Spyware"),
    ADWARE("Adware Berbahaya"),
    RANSOMWARE("Ransomware"),
    FAKE_BANKING("Trojan Perbankan"),
    PUA("Aplikasi Tidak Diinginkan (PUA)")
}

enum class ThreatSeverity(val label: String) {
    CRITICAL("Sangat Berbahaya (Kritis)"),
    HIGH("Tinggi"),
    MEDIUM("Sedang")
}

data class ThreatItem(
    val id: String,
    val appName: String,
    val packageName: String,
    val virusName: String,
    val type: ThreatType,
    val severity: ThreatSeverity,
    val description: String,
    val dangers: List<String>,
    val detectionReason: String,
    val isSystemApp: Boolean = false,
    val isTestThreat: Boolean = false
)

enum class RiskLevel(val label: String) {
    HIGH("Risiko Tinggi"),
    MEDIUM("Risiko Sedang"),
    LOW("Perhatian")
}

enum class RiskCategory(val label: String) {
    SYSTEM_VULNERABILITY("Kerentanan Sistem"),
    DANGEROUS_PERMISSIONS("Izin Berisiko Tinggi"),
    ACCESSIBILITY_ABUSE("Aksesibilitas Terbuka"),
    UNKNOWN_INSTALL_SOURCE("Sumber Tidak Dikenal"),
    DEVICE_ADMIN("Hak Administrator"),
    SCREEN_OVERLAY("Izin Hamparan Layar (Overlay)")
}

enum class RiskActionType {
    OPEN_DEV_SETTINGS,
    OPEN_APP_SETTINGS,
    OPEN_ACCESSIBILITY_SETTINGS,
    OPEN_UNKNOWN_SOURCES_SETTINGS,
    OPEN_SECURITY_SETTINGS,
    OPEN_OVERLAY_SETTINGS,
    INFO_ONLY
}

data class SecurityRiskItem(
    val id: String,
    val title: String,
    val riskLevel: RiskLevel,
    val category: RiskCategory,
    val description: String,
    val impact: String,
    val howToFix: String,
    val actionType: RiskActionType,
    val targetPackage: String? = null,
    val appName: String? = null
)

data class ScanStats(
    val totalAppsScanned: Int = 0,
    val threatsFoundCount: Int = 0,
    val risksFoundCount: Int = 0,
    val safeAppsCount: Int = 0,
    val systemChecksPassed: Int = 0,
    val scanDurationSeconds: Int = 0,
    val lastScanTimeFormatted: String = ""
)

data class SecurityTip(
    val title: String,
    val category: String,
    val description: String,
    val iconName: String
)
