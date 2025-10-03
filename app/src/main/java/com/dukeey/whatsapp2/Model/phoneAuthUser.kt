data class PhoneAuthUser(
	var userId: String = "",
	var phoneNumber: String = "",
	var name: String = "",
	var status: String = "",
	var profileImage: String = ""
) {
	constructor() : this("", "", "", "", "")
}
