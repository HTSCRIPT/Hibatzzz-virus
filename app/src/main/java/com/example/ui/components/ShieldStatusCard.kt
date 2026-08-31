package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.GppBad
import androidx.compose.material.icons.filled.GppGood
import androidx.compose.material.icons.filled.GppMaybe
import androidx.compose.material.icons.filled.Radar
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ScanStats
import com.example.model.ScanStatus
import com.example.ui.theme.DangerRed
import com.example.ui.theme.DangerRedBorder
import com.example.ui.theme.DangerRedContainer
import com.example.ui.theme.DangerRedText
import com.example.ui.theme.RiskAmber
import com.example.ui.theme.RiskAmberBorder
import com.example.ui.theme.RiskAmberContainer
import com.example.ui.theme.RiskAmberText
import com.example.ui.theme.ShieldGreen
import com.example.ui.theme.SleekBlueContainerLight
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekBlueText

@Composable
fun ShieldStatusCard(
    status: ScanStatus,
    scannedCount: Int,
    totalCount: Int,
    currentApp: String,
    currentPhase: String,
    stats: ScanStats,
    onStartScan: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_radar")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 1.18f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val (cardBg, borderColor, icon, iconBgColor, iconTintColor, titleColor, subColor, mainTitle, subtitle) = when (status) {
        ScanStatus.SCANNING -> {
            val progressPercent = if (totalCount > 0) (scannedCount * 100 / totalCount) else 0
            StatusTheme(
                cardBg = SleekBlueContainerLight,
                borderColor = SleekBluePrimary.copy(alpha = 0.4f),
                icon = Icons.Default.Radar,
                iconBgColor = SleekBluePrimary,
                iconTintColor = Color.White,
                titleColor = SleekBlueText,
                subColor = SleekBluePrimary,
                mainTitle = "Sedang Memindai HP...",
                subtitle = "$progressPercent% - $currentPhase"
            )
        }
        ScanStatus.THREATS_FOUND -> {
            StatusTheme(
                cardBg = DangerRedContainer,
                borderColor = DangerRedBorder,
                icon = Icons.Default.GppBad,
                iconBgColor = DangerRed,
                iconTintColor = Color.White,
                titleColor = DangerRedText,
                subColor = DangerRedText.copy(alpha = 0.85f),
                mainTitle = "VIRUS DITEMUKAN!",
                subtitle = "Ditemukan ${stats.threatsFoundCount} ancaman berbahaya di HP Anda! Lakukan pembersihan sekarang."
            )
        }
        ScanStatus.RISKY -> {
            StatusTheme(
                cardBg = RiskAmberContainer,
                borderColor = RiskAmberBorder,
                icon = Icons.Default.GppMaybe,
                iconBgColor = RiskAmber,
                iconTintColor = Color.White,
                titleColor = RiskAmberText,
                subColor = RiskAmberText.copy(alpha = 0.85f),
                mainTitle = "HP Kamu Berisiko",
                subtitle = "Ditemukan ${stats.risksFoundCount} celah keamanan & izin mencurigakan yang dapat membahayakan HP."
            )
        }
        ScanStatus.SAFE -> {
            StatusTheme(
                cardBg = SleekBlueContainerLight,
                borderColor = Color(0xFFDBEAFE),
                icon = Icons.Default.Shield,
                iconBgColor = SleekBluePrimary,
                iconTintColor = Color.White,
                titleColor = SleekBlueText,
                subColor = SleekBluePrimary.copy(alpha = 0.85f),
                mainTitle = "HP Kamu Aman",
                subtitle = "Tidak ditemukan virus atau malware di HP Anda. Sistem dan aplikasi terverifikasi bersih."
            )
        }
        ScanStatus.IDLE -> {
            StatusTheme(
                cardBg = SleekBlueContainerLight,
                borderColor = Color(0xFFDBEAFE),
                icon = Icons.Default.Security,
                iconBgColor = SleekBluePrimary,
                iconTintColor = Color.White,
                titleColor = SleekBlueText,
                subColor = SleekBluePrimary.copy(alpha = 0.85f),
                mainTitle = "Proteksi Aktif",
                subtitle = "Tekan tombol di bawah untuk mulai memindai virus & celah keamanan di HP Anda."
            )
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(32.dp))
            .testTag("shield_status_card"),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            // Central Sleek Hero Badge
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(104.dp)
            ) {
                if (status == ScanStatus.SCANNING) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .scale(pulseScale)
                            .clip(CircleShape)
                            .background(SleekBluePrimary.copy(alpha = 0.15f))
                    )
                    CircularProgressIndicator(
                        progress = { if (totalCount > 0) scannedCount.toFloat() / totalCount.toFloat() else 0f },
                        modifier = Modifier.size(96.dp),
                        color = SleekBluePrimary,
                        strokeWidth = 5.dp,
                        trackColor = Color(0xFFDBEAFE)
                    )
                }

                // Inner Main Circle with subtle shadow effect
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(86.dp)
                        .clip(CircleShape)
                        .background(iconBgColor)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Status Keamanan",
                        tint = iconTintColor,
                        modifier = Modifier.size(46.dp)
                    )
                }

                // Sleek Indicator Badge dot at corner for Safe/Idle state
                if (status == ScanStatus.SAFE || status == ScanStatus.IDLE) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, SleekBluePrimary, CircleShape)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(SleekBluePrimary)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Main Status Text
            Text(
                text = mainTitle,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = (-0.3).sp
                ),
                color = titleColor,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Subtitle explanation
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = subColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 4.dp),
                lineHeight = 20.sp
            )

            if (status == ScanStatus.SCANNING) {
                Spacer(modifier = Modifier.height(16.dp))
                LinearProgressIndicator(
                    progress = { if (totalCount > 0) scannedCount.toFloat() / totalCount.toFloat() else 0f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = SleekBluePrimary,
                    trackColor = Color(0xFFDBEAFE)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Memeriksa: $currentApp",
                    style = MaterialTheme.typography.bodySmall,
                    color = SleekBlueText.copy(alpha = 0.8f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Sleek Clean Stats Pill Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.White.copy(alpha = 0.7f))
                    .border(1.dp, borderColor.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatPill(
                    label = "Aplikasi",
                    value = "${if (status == ScanStatus.SCANNING) scannedCount else stats.totalAppsScanned}",
                    textColor = Color(0xFF1E293B)
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(26.dp)
                        .background(Color(0xFFCBD5E1))
                )
                StatPill(
                    label = "Virus",
                    value = "${stats.threatsFoundCount}",
                    textColor = if (stats.threatsFoundCount > 0) DangerRed else ShieldGreen
                )
                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(26.dp)
                        .background(Color(0xFFCBD5E1))
                )
                StatPill(
                    label = "Risiko",
                    value = "${stats.risksFoundCount}",
                    textColor = if (stats.risksFoundCount > 0) RiskAmber else ShieldGreen
                )
            }

            if (status != ScanStatus.SCANNING) {
                Spacer(modifier = Modifier.height(18.dp))
                Button(
                    onClick = onStartScan,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("scan_now_button"),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when (status) {
                            ScanStatus.THREATS_FOUND -> DangerRed
                            ScanStatus.RISKY -> RiskAmber
                            else -> SleekBluePrimary
                        },
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Icon(
                        imageVector = if (status == ScanStatus.IDLE) Icons.Default.Search else Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (status == ScanStatus.IDLE) "Pindai Sekarang" else "Pindai Ulang HP",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun StatPill(
    label: String,
    value: String,
    textColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = textColor
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color(0xFF64748B)
        )
    }
}

private data class StatusTheme(
    val cardBg: Color,
    val borderColor: Color,
    val icon: ImageVector,
    val iconBgColor: Color,
    val iconTintColor: Color,
    val titleColor: Color,
    val subColor: Color,
    val mainTitle: String,
    val subtitle: String
)
