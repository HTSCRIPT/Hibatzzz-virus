package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SecurityRiskItem
import com.example.ui.theme.RiskAmber
import com.example.ui.theme.RiskAmberBorder
import com.example.ui.theme.RiskAmberContainer
import com.example.ui.theme.RiskAmberText

@Composable
fun RiskCard(
    risk: SecurityRiskItem,
    onViewFix: (SecurityRiskItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, RiskAmberBorder, RoundedCornerShape(24.dp))
            .clickable { onViewFix(risk) }
            .testTag("risk_item_${risk.id}"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = RiskAmberContainer)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFFEF3C7))
                ) {
                    Icon(
                        imageVector = Icons.Default.WarningAmber,
                        contentDescription = "Risiko",
                        tint = RiskAmber,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = risk.title,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = RiskAmberText,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = risk.category.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF92400E)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFFEF3C7))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = risk.riskLevel.label,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = RiskAmberText
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Impact / Description
            Text(
                text = risk.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF78350F),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Action: Perbaiki Celah Keamanan
            Button(
                onClick = { onViewFix(risk) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp)
                    .testTag("btn_fix_risk_${risk.id}"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RiskAmber,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = null,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Periksa & Perbaiki",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }
    }
}
