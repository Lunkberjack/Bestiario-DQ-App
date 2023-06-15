package com.example.bestiario_dq_app.ui.bestiario.componentes

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.bestiario_dq_app.R
import com.example.bestiario_dq_app.ui.Screen
import com.example.bestiario_dq_app.ui.theme.manrope

@Composable
fun BottomBar(
    navController: NavHostController,
    screens: List<BottomBarScreen>,
    currentDestination: NavDestination?
) {
    NavigationBar(modifier = Modifier.height(40.dp)) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        icon = {
            if (screen.route == "monstruos") {
                Icon(
                    painter = painterResource(id = R.drawable.slimefilled),
                    contentDescription = "Navigation Icon",
                    modifier = Modifier.size(18.dp)
                )
            } else {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = "Navigation Icon"
                )
            }
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        //unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                // Si pulsamos atrás, siempre volvemos a la Home (MonstruosScreen).
                navController.popBackStack(
                    Screen.Monstruos.route,
                    inclusive = false,
                    saveState = false
                )
            }
        }
    )
}