import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
	
	@Serializable
	data object SplashSCR : Routes()
	
	@Serializable
	data object WelcomeSCR : Routes()
	
	@Serializable
	data object UserloginSCR : Routes()
	
	@Serializable
	data object HomeSCR : Routes()
	
	@Serializable
	data object UpdateSCR : Routes()
	
	@Serializable
	data object CommunitiesSCR : Routes()
	
	@Serializable
	data object CallSCR : Routes()
	
	@Serializable
	data object UserProfileSetSCR : Routes()
	
	@Serializable
	data object SettingSCR:Routes()
}

