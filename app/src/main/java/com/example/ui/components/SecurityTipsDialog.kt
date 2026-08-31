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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhonelinkLock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.DangerRed
import com.example.ui.theme.RiskAmber
import com.example.ui.theme.ShieldGreen
import com.example.ui.theme.SleekBlueContainerLight
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekBorderLight

@Composable
fun SecurityTipsDialog(
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(28.dp))
                .border(1.dp, SleekBorderLight, RoundedCornerShape(28.dp))
                .testTag("security_tips_dialog"),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(22.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Header
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
                                .background(SleekBlueContainerLight)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = SleekBluePrimary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Tips HP Bebas Virus",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "Panduan Proteksi Mandiri",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color(0xFF64748B)
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

                Spacer(modifier = Modifier.height(18.dp))

                TipCard(
                    icon = Icons.Default.Warning,
                    iconTint = DangerRed,
                    iconBg = Color(0xFFFEF2F2),
                    title = "Waspadai File APK dari WhatsApp / SMS",
                    description = "Jangan pernah mengklik atau memasang file berakhiran '.apk' yang dikirim lewat chat (seperti modus 'Surat Undangan Pernikahan', 'Foto Paket Kurir', 'Surat Tilang'). Itu adalah Trojan pencuri saldo perbankan."
                )

                Spacer(modifier = Modifier.height(10.dp))

                TipCard(
                    icon = Icons.Default.Lock,
                    iconTint = ShieldGreen,
                    iconBg = Color(0xFFECFDF5),
                    title = "Jangan Berikan Izin Aksesibilitas Sembarangan",
                    description = "Layanan Aksesibilitas hanya untuk fitur difabel. Malware sering meminta izin ini agar bisa menekan layar otomatis dan membaca SMS OTP tanpa Anda ketahui."
                )

                Spacer(modifier = Modifier.height(10.dp))

                TipCard(
                    icon = Icons.Default.PhonelinkLock,
                    iconTint = SleekBluePrimary,
                    iconBg = SleekBlueContainerLight,
                    title = "Unduh Aplikasi Hanya dari Google Play Store",
                    description = "Hindari mengunduh file game 'Mod' atau aplikasi bajakan dari website browser gratisan, karena sering disisipi Adware dan Spyware."
                )

                Spacer(modifier = Modifier.height(10.dp))

                TipCard(
                    icon = Icons.Default.Security,
                    iconTint = RiskAmber,
                    iconBg = Color(0xFFFFFBEB),
                    title = "Selalu Perbarui Sistem Android & Patch Keamanan",
                    description = "Pembaruan rutin dari pabrikan HP menutup celah keamanan baru yang biasa dimanfaatkan oleh virus peretas."
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("dialog_btn_understand_tips"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SleekBluePrimary,
                        contentColor = Color.White
                    )
                ) {
                    Text("Saya Mengerti", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        }
    }
}

@Composable
private fun TipCard(
    icon: ImageVector,
    iconTint: Color,
    iconBg: Color,
    title: String,
    description: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(iconBg)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
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
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF64748B),
                    lineHeight = 18.sp
                )
            }
        }
    }
}
