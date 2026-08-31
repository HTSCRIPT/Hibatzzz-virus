package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.ThreatItem
import com.example.ui.theme.DangerRed
import com.example.ui.theme.DangerRedBorder
import com.example.ui.theme.DangerRedContainer
import com.example.ui.theme.DangerRedText
import com.example.ui.theme.ShieldGreen
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekBorderLight

@Composable
fun ThreatRemovalDialog(
    threat: ThreatItem,
    onDismiss: () -> Unit,
    onUninstallClicked: (packageName: String) -> Unit,
    onOpenAppSettings: (packageName: String) -> Unit,
    onOpenAdminSettings: () -> Unit,
    onMarkAsRemoved: (ThreatItem) -> Unit
) {
    var showSafeModeGuide by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(28.dp))
                .border(1.dp, DangerRedBorder, RoundedCornerShape(28.dp))
                .testTag("threat_removal_dialog"),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(22.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header with close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(DangerRedContainer)
                        ) {
                            Icon(
                                imageVector = Icons.Default.BugReport,
                                contentDescription = null,
                                tint = DangerRed,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Instruksi Hapus Virus",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = DangerRedText
                            )
                            Text(
                                text = "Tindakan Darurat Diperlukan",
                                style = MaterialTheme.typography.labelSmall,
                                color = DangerRed
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Tutup",
                            tint = Color(0xFF64748B)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Urgent Warning Callout
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(DangerRedContainer)
                        .border(1.dp, DangerRedBorder, RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = DangerRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "SURUH HAPUS SEGERA!",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = DangerRedText
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Aplikasi ini teridentifikasi sebagai virus berbahaya (${threat.virusName}). Segera copot pemasangan aplikasi ini untuk melindungi HP dan data Anda.",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF7F1D1D),
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Virus Details Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SleekBorderLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Informasi Virus Terdeteksi",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = SleekBluePrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        DetailRow(label = "Nama Aplikasi:", value = threat.appName)
                        DetailRow(label = "Nama Virus:", value = threat.virusName, isDanger = true)
                        DetailRow(label = "Kategori:", value = threat.type.displayName)
                        DetailRow(label = "Tingkat Bahaya:", value = threat.severity.label, isDanger = true)
                        DetailRow(label = "Package Name:", value = threat.packageName)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Potensi Bahaya bagi HP Anda:",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                            color = Color(0xFF1E293B)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        threat.dangers.forEach { danger ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "• ",
                                    color = DangerRed,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = danger,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF475569)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Main Action: Direct Uninstall Button
                Button(
                    onClick = {
                        onUninstallClicked(threat.packageName)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("dialog_btn_uninstall_now"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DangerRed,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "1. HAPUS SEKARANG (UNINSTALL)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Step by step removal instructions: "Caranya Gimana"
                Text(
                    text = "Panduan Langkah Cara Menghapus:",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF1E293B)
                )
                Spacer(modifier = Modifier.height(8.dp))

                StepItem(
                    stepNumber = "1",
                    title = "Tekan Tombol 'Hapus Sekarang' di Atas",
                    description = "Sistem Android akan menampilkan dialog konfirmasi uninstall. Pilih 'OK' untuk menghapus file APK dan seluruh datanya."
                )

                StepItem(
                    stepNumber = "2",
                    title = "Jika Tombol Hapus Tidak Berfungsi / Terkunci",
                    description = "Beberapa Trojan mengunci dirinya dengan meminta hak Admin Perangkat. Anda harus menonaktifkannya terlebih dahulu.",
                    extraButton = {
                        OutlinedButton(
                            onClick = onOpenAdminSettings,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Buka Pengaturan Admin Perangkat", fontSize = 12.sp)
                        }
                    }
                )

                StepItem(
                    stepNumber = "3",
                    title = "Buka Info Aplikasi untuk Paksa Berhenti",
                    description = "Jika virus terus berjalan di latar belakang, pilih 'Paksa Berhenti' (Force Stop) lalu 'Hapus Data' & 'Uninstall'.",
                    extraButton = {
                        OutlinedButton(
                            onClick = { onOpenAppSettings(threat.packageName) },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(Icons.Default.OpenInNew, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Buka Info Aplikasi", fontSize = 12.sp)
                        }
                    }
                )

                StepItem(
                    stepNumber = "4",
                    title = "Hapus Lewat Safe Mode (Jika Membandel)",
                    description = "Safe Mode mematikan semua aplikasi jahat pihak ketiga sehingga Anda bisa menghapusnya dengan mudah tanpa gangguan.",
                    extraButton = {
                        OutlinedButton(
                            onClick = { showSafeModeGuide = !showSafeModeGuide },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(Icons.Default.PowerSettingsNew, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (showSafeModeGuide) "Tutup Cara Safe Mode" else "Lihat Cara Masuk Safe Mode", fontSize = 12.sp)
                        }
                    }
                )

                if (showSafeModeGuide) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFFEFF6FF))
                            .border(1.dp, Color(0xFFDBEAFE), RoundedCornerShape(14.dp))
                            .padding(14.dp)
                    ) {
                        Column {
                            Text(
                                text = "Langkah Masuk Mode Aman (Safe Mode):",
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                color = SleekBluePrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "1. Tekan & tahan tombol Power fisik HP Anda.\n" +
                                        "2. Pada menu yang muncul, sentuh dan tahan tombol 'Daya Mati' atau 'Mulai Ulang' selama 3 detik.\n" +
                                        "3. Akan muncul konfirmasi 'Mulai ulang ke mode aman'. Ketuk OK.\n" +
                                        "4. Setelah HP menyala dengan tulisan 'Mode Aman' di pojok bawah, buka Pengaturan > Aplikasi > cari ${threat.appName} > pilih Uninstall.\n" +
                                        "5. Restart HP kembali secara normal.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF1E3A8A),
                                lineHeight = 18.sp
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Mark as removed button
                Button(
                    onClick = { onMarkAsRemoved(threat) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("dialog_btn_mark_cleaned"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ShieldGreen,
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tandai Sudah Dihapus / Bersih", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String, isDanger: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF64748B)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = if (isDanger) FontWeight.Bold else FontWeight.Normal
            ),
            color = if (isDanger) DangerRedText else Color(0xFF1E293B)
        )
    }
}

@Composable
private fun StepItem(
    stepNumber: String,
    title: String,
    description: String,
    extraButton: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(SleekBluePrimary)
        ) {
            Text(
                text = stepNumber,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF1E293B)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF64748B),
                lineHeight = 18.sp
            )
            extraButton?.invoke()
        }
    }
}
