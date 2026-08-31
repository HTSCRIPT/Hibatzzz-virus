package com.example

import android.content.ActivityNotFoundException
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.GppGood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.RiskActionType
import com.example.model.ScanStatus
import com.example.ui.components.RiskCard
import com.example.ui.components.RiskFixDialog
import com.example.ui.components.SecurityTipsDialog
import com.example.ui.components.ShieldStatusCard
import com.example.ui.components.ThreatCard
import com.example.ui.components.ThreatRemovalDialog
import com.example.ui.theme.DangerRed
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.RiskAmber
import com.example.ui.theme.ShieldGreen
import com.example.ui.theme.SleekBlueContainerLight
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekBorderLight
import com.example.viewmodel.ScannerViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ScannerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ScannerMainScreen(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerMainScreen(viewModel: ScannerViewModel) {
    val context = LocalContext.current
    val scanStatus by viewModel.scanStatus.collectAsStateWithLifecycle()
    val scannedCount by viewModel.scannedCount.collectAsStateWithLifecycle()
    val totalCount by viewModel.totalAppsToScan.collectAsStateWithLifecycle()
    val currentApp by viewModel.currentScanningApp.collectAsStateWithLifecycle()
    val currentPhase by viewModel.currentScanPhase.collectAsStateWithLifecycle()
    val threatsList by viewModel.threatsList.collectAsStateWithLifecycle()
    val risksList by viewModel.risksList.collectAsStateWithLifecycle()
    val scanStats by viewModel.scanStats.collectAsStateWithLifecycle()
    val selectedThreat by viewModel.selectedThreat.collectAsStateWithLifecycle()
    val selectedRisk by viewModel.selectedRisk.collectAsStateWithLifecycle()
    val showTipsDialog by viewModel.showTipsDialog.collectAsStateWithLifecycle()
    val realtimeProtection by viewModel.realtimeProtection.collectAsStateWithLifecycle()

    var showMenu by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "GUARDIAN MOBILE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.8.sp
                            ),
                            color = SleekBluePrimary
                        )
                        Text(
                            text = "Keamanan Perangkat",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF1E293B)
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.setShowTips(true) },
                        modifier = Modifier.testTag("btn_top_tips")
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(SleekBlueContainerLight)
                                .border(1.dp, Color(0xFFDBEAFE), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Tips Keamanan",
                                tint = SleekBluePrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Box {
                        IconButton(
                            onClick = { showMenu = true },
                            modifier = Modifier.testTag("btn_top_menu")
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(Color.White)
                                    .border(1.dp, SleekBorderLight, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "Menu Lainnya",
                                    tint = Color(0xFF475569),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Pindai Ulang Perangkat") },
                                onClick = {
                                    showMenu = false
                                    viewModel.startScan(includeSimulation = false)
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Refresh, contentDescription = null, tint = SleekBluePrimary)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Simulasi Temuan Virus (Demo)") },
                                onClick = {
                                    showMenu = false
                                    viewModel.startSimulationWithThreats()
                                    Toast.makeText(context, "Memulai simulasi deteksi virus...", Toast.LENGTH_SHORT).show()
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.BugReport, contentDescription = null, tint = DangerRed)
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Simulasi HP Berisiko (Demo)") },
                                onClick = {
                                    showMenu = false
                                    viewModel.simulateRiskyStateOnly()
                                    Toast.makeText(context, "Memulai simulasi peringatan risiko...", Toast.LENGTH_SHORT).show()
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.WarningAmber, contentDescription = null, tint = RiskAmber)
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            // Sleek Modern Bottom Navigation Bar
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SleekNavItem(
                        label = "Beranda",
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 }
                    )
                    SleekNavItem(
                        label = "Analisis",
                        selected = selectedTab == 1,
                        onClick = {
                            selectedTab = 1
                            viewModel.startScan(includeSimulation = false)
                        }
                    )
                    SleekNavItem(
                        label = "Alat Uji",
                        selected = selectedTab == 2,
                        onClick = {
                            selectedTab = 2
                            viewModel.startSimulationWithThreats()
                        }
                    )
                    SleekNavItem(
                        label = "Tips",
                        selected = selectedTab == 3,
                        onClick = {
                            selectedTab = 3
                            viewModel.setShowTips(true)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(2.dp))
                // 1. Primary Hero Status Card
                ShieldStatusCard(
                    status = scanStatus,
                    scannedCount = scannedCount,
                    totalCount = totalCount,
                    currentApp = currentApp,
                    currentPhase = currentPhase,
                    stats = scanStats,
                    onStartScan = { viewModel.startScan(includeSimulation = false) }
                )
            }

            // Real-time Protection Toggle Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SleekBorderLight)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(
                                        if (realtimeProtection) Color(0xFFECFDF5)
                                        else Color(0xFFF1F5F9)
                                    )
                            ) {
                                Icon(
                                    imageVector = if (realtimeProtection) Icons.Default.GppGood else Icons.Default.Security,
                                    contentDescription = null,
                                    tint = if (realtimeProtection) ShieldGreen else Color(0xFF64748B),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "Proteksi Real-Time",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF1E293B)
                                )
                                Text(
                                    text = if (realtimeProtection) "Aktif memantau instalasi APK mencurigakan" else "Nonaktif - Aktifkan untuk keamanan maksimal",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF64748B)
                                )
                            }
                        }
                        Switch(
                            checked = realtimeProtection,
                            onCheckedChange = { viewModel.toggleRealtimeProtection() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = SleekBluePrimary,
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = Color(0xFFCBD5E1)
                            ),
                            modifier = Modifier.testTag("switch_realtime_protection")
                        )
                    }
                }
            }

            // 2. Threats Found Section (Virus / Malware)
            if (threatsList.isNotEmpty()) {
                item {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.BugReport,
                                contentDescription = null,
                                tint = DangerRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Daftar Virus Ditemukan (${threatsList.size})",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = DangerRed
                            )
                        }
                        Text(
                            text = "Aplikasi di bawah ini teridentifikasi sebagai virus. Ketuk untuk menghapusnya secara tuntas.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF64748B),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                items(threatsList, key = { it.id }) { threat ->
                    ThreatCard(
                        threat = threat,
                        onViewGuideAndRemove = { viewModel.selectThreat(it) }
                    )
                }
            }

            // 3. Security Risks Section (HP Berisiko)
            if (risksList.isNotEmpty()) {
                item {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.WarningAmber,
                                contentDescription = null,
                                tint = RiskAmber,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Celah & Risiko Keamanan HP (${risksList.size})",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = RiskAmber
                            )
                        }
                        Text(
                            text = "Pengaturan berikut membuat HP Anda beresiko terkena virus jika tidak segera diamankan.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF64748B),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                items(risksList, key = { it.id }) { risk ->
                    RiskCard(
                        risk = risk,
                        onViewFix = { viewModel.selectRisk(it) }
                    )
                }
            }

            // 4. Safe Status Extra Card (when clean)
            if (scanStatus == ScanStatus.SAFE && threatsList.isEmpty() && risksList.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, SleekBorderLight),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(Color(0xFFECFDF5))
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = ShieldGreen,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Rincian Pemeriksaan Keamanan",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = Color(0xFF1E293B)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            SecurityCheckLine("Semua ${scanStats.totalAppsScanned} aplikasi terverifikasi bebas signature malware.")
                            SecurityCheckLine("Tidak ada Trojan pencuri OTP SMS atau m-Banking terdeteksi.")
                            SecurityCheckLine("Layanan Aksesibilitas tidak disalahgunakan pihak ketiga.")
                            SecurityCheckLine("Tidak ada aplikasi admin perangkat tersembunyi.")
                            SecurityCheckLine("Pengaturan keamanan sistem Android dalam kondisi optimal.")
                        }
                    }
                }
            }

            // 5. Sleek Action Tools & Simulation
            item {
                Text(
                    text = "Alat Uji & Simulasi Keamanan",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF1E293B),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ActionTile(
                        title = "Uji Deteksi Virus",
                        subtitle = "Simulasi Hapus Trojan",
                        icon = Icons.Default.BugReport,
                        accentColor = DangerRed,
                        iconBg = Color(0xFFFEF2F2),
                        modifier = Modifier.weight(1f),
                        testTag = "tile_test_virus",
                        onClick = {
                            viewModel.startSimulationWithThreats()
                            Toast.makeText(context, "Memulai simulasi deteksi virus...", Toast.LENGTH_SHORT).show()
                        }
                    )
                    ActionTile(
                        title = "Uji HP Berisiko",
                        subtitle = "Simulasi Celah HP",
                        icon = Icons.Default.WarningAmber,
                        accentColor = RiskAmber,
                        iconBg = Color(0xFFFFFBEB),
                        modifier = Modifier.weight(1f),
                        testTag = "tile_test_risk",
                        onClick = {
                            viewModel.simulateRiskyStateOnly()
                            Toast.makeText(context, "Memulai simulasi peringatan risiko...", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }

            item {
                ActionTile(
                    title = "Panduan & Tips Anti-Virus",
                    subtitle = "Cara mengenali modus APK undangan & paket palsu di WhatsApp",
                    icon = Icons.Default.Lightbulb,
                    accentColor = SleekBluePrimary,
                    iconBg = SleekBlueContainerLight,
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "tile_tips_guide",
                    onClick = { viewModel.setShowTips(true) }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    // Modal: Detailed Threat Removal Guide & Uninstaller
    selectedThreat?.let { threat ->
        ThreatRemovalDialog(
            threat = threat,
            onDismiss = { viewModel.selectThreat(null) },
            onUninstallClicked = { pkg ->
                try {
                    context.startActivity(viewModel.getUninstallIntent(pkg))
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "Gagal membuka uninstaller: $pkg", Toast.LENGTH_SHORT).show()
                }
            },
            onOpenAppSettings = { pkg ->
                try {
                    context.startActivity(viewModel.getAppSettingsIntent(pkg))
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(context, "Gagal membuka info aplikasi", Toast.LENGTH_SHORT).show()
                }
            },
            onOpenAdminSettings = {
                try {
                    context.startActivity(viewModel.getSecuritySettingsIntent())
                } catch (e: Exception) {
                    Toast.makeText(context, "Gagal membuka pengaturan keamanan", Toast.LENGTH_SHORT).show()
                }
            },
            onMarkAsRemoved = { item ->
                viewModel.removeThreat(item)
                Toast.makeText(context, "Virus '${item.appName}' berhasil dihapus dari daftar ancaman!", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // Modal: Risk Remediation Dialog
    selectedRisk?.let { risk ->
        RiskFixDialog(
            risk = risk,
            onDismiss = { viewModel.selectRisk(null) },
            onOpenSettings = { actionType, pkg ->
                try {
                    when (actionType) {
                        RiskActionType.OPEN_DEV_SETTINGS -> context.startActivity(viewModel.getDevSettingsIntent())
                        RiskActionType.OPEN_ACCESSIBILITY_SETTINGS -> context.startActivity(viewModel.getAccessibilitySettingsIntent())
                        RiskActionType.OPEN_SECURITY_SETTINGS,
                        RiskActionType.OPEN_UNKNOWN_SOURCES_SETTINGS,
                        RiskActionType.OPEN_OVERLAY_SETTINGS -> context.startActivity(viewModel.getSecuritySettingsIntent())
                        RiskActionType.OPEN_APP_SETTINGS -> {
                            if (pkg != null) {
                                context.startActivity(viewModel.getAppSettingsIntent(pkg))
                            } else {
                                context.startActivity(viewModel.getSecuritySettingsIntent())
                            }
                        }
                        RiskActionType.INFO_ONLY -> {}
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Pengaturan tidak dapat dibuka langsung", Toast.LENGTH_SHORT).show()
                }
            },
            onMarkResolved = { riskId ->
                viewModel.resolveRisk(riskId)
                Toast.makeText(context, "Celah risiko berhasil ditandai selesai!", Toast.LENGTH_SHORT).show()
            }
        )
    }

    // Modal: Security Tips
    if (showTipsDialog) {
        SecurityTipsDialog(
            onDismiss = { viewModel.setShowTips(false) }
        )
    }
}

@Composable
private fun SleekNavItem(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (selected) SleekBlueContainerLight else Color.Transparent)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(if (selected) SleekBluePrimary else Color(0xFFCBD5E1))
            )
        }
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 10.sp
            ),
            color = if (selected) SleekBluePrimary else Color(0xFF64748B)
        )
    }
}

@Composable
private fun SecurityCheckLine(text: String) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = ShieldGreen,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF475569)
        )
    }
}

@Composable
private fun ActionTile(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    iconBg: Color,
    modifier: Modifier = Modifier,
    testTag: String = "",
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(22.dp))
            .border(1.dp, SleekBorderLight, RoundedCornerShape(22.dp))
            .clickable { onClick() }
            .testTag(testTag),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(iconBg)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF1E293B)
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B),
                    maxLines = 2
                )
            }
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = Color(0xFF94A3B8),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
