buildscript {
	dependencies {
		// 👇 Yeh line tumhare case me missing hai
		classpath("com.google.gms:google-services:4.4.2")
	}
}



plugins {
	//id("com.android.application") version "8.7.1" apply false
	//id("org.jetbrains.kotlin.android") version "2.2.0" apply false
	id("org.jetbrains.kotlin.plugin.serialization") version "2.2.20" apply false
	id("com.google.dagger.hilt.android") version "2.57.1" apply false
	//id("com.google.devtools.ksp") version "2.2.0-2.0.2" apply false
	id("com.google.devtools.ksp") version "2.2.10-2.0.2" apply false // Example: updated version
	alias(libs.plugins.kotlin.compose) apply false
	id("com.android.application") version "8.13.0" apply false
	id("com.android.library") version "8.9.1" apply false
	id("org.jetbrains.kotlin.android") version "2.2.20" apply false
	
	
}