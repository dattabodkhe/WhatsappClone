package com.dukeey.whatsapp2.Nevigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dukeey.whatsapp2.CommunitieSCR.communitiesSCRDesign
import com.dukeey.whatsapp2.SplashSCR.SplaschSCR
import com.dukeey.whatsapp2.UpdateSCR.Upadate
import com.dukeey.whatsapp2.Welcome.WellcomeSCRDesign
import com.dukeey.whatsapp2.callSCR.CallScr
import com.dukeey.whatsapp2.homeSCR.homeSCR
import com.dukeey.whatsapp2.loginSCr.UserloginSCR
import com.dukeey.whatsapp2.profile.UserProfileSetSCR

@Composable
fun WhatsAppNevSystem() {
	val navController = rememberNavController()
	
	NavHost(
		navController = navController,
		startDestination = Routes.SplashSCR)
	{
		composable<Routes.SplashSCR>
		{ SplaschSCR(navController)
		}
		composable<Routes.WelcomeSCR> {
			WellcomeSCRDesign(navController) }
		composable<Routes.UserloginSCR> {
			UserloginSCR(navController) }
		composable<Routes.UserProfileSetSCR> {
			UserProfileSetSCR(navController=navController) }
		composable<Routes.HomeSCR>{
			homeSCR(navController) }
		composable<Routes.UpdateSCR>{
			Upadate() }
		composable<Routes.CommunitiesSCR> {
			communitiesSCRDesign() }
		composable<Routes.CallSCR> { CallScr() }
	}
}
