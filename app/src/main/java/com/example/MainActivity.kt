package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(darkTheme = true) {
                NexusApp()
            }
        }
    }
}

data class AppItem(
    val name: String,
    val icon: ImageVector,
    val color: Color,
    val isClone: Boolean = false,
    val badge: Int = 0
)

val allApps = listOf(
    AppItem("Chat", Icons.Rounded.ChatBubble, Color(0xFF4CAF50), badge = 3),
    AppItem("Chat (Work)", Icons.Rounded.ChatBubble, Color(0xFF4CAF50), isClone = true),
    AppItem("Social", Icons.Rounded.Public, Color(0xFF2196F3)),
    AppItem("Video", Icons.Rounded.PlayArrow, Color(0xFFF44336), badge = 1),
    AppItem("Shopping", Icons.Rounded.ShoppingCart, Color(0xFFFF9800)),
    AppItem("Mail", Icons.Rounded.Email, Color(0xFFE91E63)),
    AppItem("Music", Icons.Rounded.MusicNote, Color(0xFF9C27B0)),
    AppItem("Files", Icons.Rounded.Folder, Color(0xFFFFC107)),
    AppItem("Browser", Icons.Rounded.Explore, Color(0xFF00BCD4)),
    AppItem("Camera", Icons.Rounded.CameraAlt, Color(0xFF607D8B)),
    AppItem("Maps", Icons.Rounded.Map, Color(0xFF8BC34A)),
    AppItem("Calendar", Icons.Rounded.CalendarToday, Color(0xFF3F51B5)),
)

data class Workspace(
    val name: String,
    val apps: List<AppItem>,
    val color: Color
)

val workspaces = listOf(
    Workspace("Personal", listOf(allApps[2], allApps[3], allApps[6]), Color(0xFF2196F3)),
    Workspace("Work", listOf(allApps[1], allApps[5], allApps[11]), Color(0xFF9C27B0)),
    Workspace("Finance", listOf(allApps[4], allApps[7]), Color(0xFF4CAF50)),
)

@Composable
fun NexusApp() {
    Box(modifier = Modifier.fillMaxSize()) {
        GlassBackground()
        
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = { GlassBottomNav() },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /* Add app */ },
                    containerColor = Color(0xFFE0E0FF),
                    contentColor = Color(0xFF0B0F19),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(Icons.Rounded.Add, contentDescription = "Add App")
                }
            },
            floatingActionButtonPosition = FabPosition.End
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                TopSearchBar()
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Workspaces",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                WorkspacesRow()
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "All Apps",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                AppsGrid(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun GlassBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawRect(Color(0xFF0B0F19))
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF6200EA).copy(alpha = 0.25f), Color.Transparent),
                center = Offset(size.width * 0.8f, size.height * 0.15f),
                radius = size.width * 0.7f
            )
        )
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFF00B8D4).copy(alpha = 0.2f), Color.Transparent),
                center = Offset(size.width * 0.1f, size.height * 0.6f),
                radius = size.width * 0.8f
            )
        )
    }
}

@Composable
fun TopSearchBar() {
    var query by remember { mutableStateOf("") }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.08f))
            .border(1.dp, Color.White.copy(alpha = 0.12f), RoundedCornerShape(24.dp))
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search",
                tint = Color.White.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Search apps, spaces...",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Rounded.Mic,
                contentDescription = "Voice Search",
                tint = Color.White.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun WorkspacesRow() {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(workspaces) { space ->
            WorkspaceCard(space)
        }
        item {
            AddWorkspaceCard()
        }
    }
}

@Composable
fun WorkspaceCard(workspace: Workspace) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.05f))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp))
            .clickable { }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(workspace.color)
            )
            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = "Options",
                tint = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.size(16.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = workspace.name,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(-8.dp)) {
            workspace.apps.take(3).forEach { app ->
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF1E2336))
                        .border(1.dp, Color.White.copy(alpha = 0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = app.icon,
                        contentDescription = null,
                        tint = app.color,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun AddWorkspaceCard() {
    Column(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color.White.copy(alpha = 0.03f))
            .border(1.dp, Color.White.copy(alpha = 0.1f), RoundedCornerShape(24.dp))
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add Workspace",
                tint = Color.White
            )
        }
    }
}

@Composable
fun AppsGrid(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 100.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
    ) {
        items(allApps) { app ->
            AppIconItem(app)
        }
    }
}

@Composable
fun AppIconItem(app: AppItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { }
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White.copy(alpha = 0.08f))
                .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(app.color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = app.icon,
                    contentDescription = app.name,
                    tint = app.color,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            if (app.isClone) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(x = 4.dp, y = 4.dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0B0F19))
                        .padding(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color(0xFF2196F3)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ContentCopy,
                            contentDescription = "Clone",
                            tint = Color.White,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
            }
            
            if (app.badge > 0) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 4.dp, y = (-4).dp)
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0B0F19))
                        .padding(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color(0xFFE91E63)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = app.badge.toString(),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = app.name,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun GlassBottomNav() {
    val items = listOf(
        Icons.Rounded.Home to "Home",
        Icons.Rounded.GridView to "Apps",
        Icons.Rounded.ContentCopy to "Clones",
        Icons.Rounded.Widgets to "Tools",
        Icons.Rounded.Settings to "Settings"
    )
    var selected by remember { mutableStateOf(0) }
    
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White.copy(alpha = 0.08f))
                .border(1.dp, Color.White.copy(alpha = 0.15f), RoundedCornerShape(32.dp))
                .padding(vertical = 12.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, pair ->
                val isSelected = selected == index
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .clickable { selected = index }
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = pair.first,
                        contentDescription = pair.second,
                        tint = if (isSelected) Color.White else Color.White.copy(alpha = 0.4f),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) Color.White else Color.Transparent)
                    )
                }
            }
        }
    }
}
