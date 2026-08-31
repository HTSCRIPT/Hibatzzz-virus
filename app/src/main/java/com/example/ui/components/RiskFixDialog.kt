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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.WarningAmber
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.RiskActionType
import com.example.model.SecurityRiskItem
import com.example.ui.theme.RiskAmber
import com.example.ui.theme.RiskAmberBorder
import com.example.ui.theme.RiskAmberContainer
import com.example.ui.theme.RiskAmberText
import com.example.ui.theme.ShieldGreen
import com.example.ui.theme.SleekBluePrimary
import com.example.ui.theme.SleekBorderLight

@Composable
fun RiskFixDialog(
    risk: SecurityRiskItem,
    onDismiss: () -> Unit,
    onOpenSettings: (RiskActionType, String?) -> Unit,
    onMarkResolved: (String) -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(28.dp))
                .border(1.dp, RiskAmberBorder, RoundedCornerShape(28.dp))
                .testTag("risk_fix_dialog"),
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
                                .background(RiskAmberContainer)
                        ) {
                            Icon(
                                imageVector = Icons.Default.WarningAmber,
                                contentDescription = null,
                                tint = RiskAmber,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Detail Risiko HP",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF1E293B)
                            )
                            Text(
                                text = "HP Kamu Beresiko Terkena Virus",
                                style = MaterialTheme.typography.labelSmall,
                                color = RiskAmberText
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

                // Detail Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, SleekBorderLight),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = risk.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF1E293B)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = risk.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF64748B)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Impact Explanation
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(RiskAmberContainer)
                        .border(1.dp, RiskAmberBorder, RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Text(
                            text = "Kenapa Ini Berisiko?",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = RiskAmberText
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = risk.impact,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF78350F),
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // How to fix guide
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFEFF6FF))
                        .border(1.dp, Color(0xFFDBEAFE), RoundedCornerShape(16.dp))
                        .padding(14.dp)
                ) {
                    Column {
                        Text(
                            text = "Cara Memperbaiki:",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = SleekBluePrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = risk.howToFix,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF1E3A8A),
                            lineHeight = 18.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action: Open System Settings
                if (risk.actionType != RiskActionType.INFO_ONLY) {
                    Button(
                        onClick = {
                            onOpenSettings(risk.actionType, risk.targetPackage)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("dialog_btn_open_settings"),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RiskAmber,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Buka Pengaturan untuk Memperbaiki",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // Mark resolved
                Button(
                    onClick = { onMarkResolved(risk.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("dialog_btn_resolve_risk"),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ShieldGreen,
                        contentColor = Color.White
                    )
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Tandai Celah Sudah Diperbaiki", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
