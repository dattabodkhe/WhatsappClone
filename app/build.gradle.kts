@file:Suppress("DEPRECATION")

import org.gradle.kotlin.dsl.implementation


plugins {
	id("com.android.application")
	id("org.jetbrains.kotlin.android")
	id("org.jetbrains.kotlin.plugin.serialization")
	id("com.google.devtools.ksp")
	id("com.google.dagger.hilt.android")
	alias(libs.plugins.kotlin.compose)
	id ("com.google.gms.google-services")
}

android {
	namespace = "com.dukeey.whatsapp2"
	compileSdk = 36
	
	defaultConfig {
		applicationId = "com.dukeey.whatsapp2"
		minSdk = 27
		targetSdk = 36
		versionCode = 1
		versionName = "1.0"
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
		//kotlinCompilerExtensionVersion = "1.5.14"
		
		
	}
	
	kotlinOptions {
		jvmTarget = "11"
	}
	
	buildFeatures {
		compose = true
	}
}

dependencies {
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.compose.ui)
	implementation(libs.androidx.compose.ui.graphics)
	implementation(libs.androidx.compose.ui.tooling.preview)
	implementation(libs.androidx.compose.material3)
	implementation(libs.androidx.navigation.compose)
	//new dependencies//
	//implementation platform("androidx.compose:compose-bom:2024.09.00")
	implementation ("androidx.compose.material3:material3")
	implementation ("androidx.compose.foundation:foundation")
	implementation ("androidx.compose.ui:ui")
	implementation ("androidx.compose.ui:ui-tooling-preview")
	implementation(libs.androidx.ui.graphics)
	debugImplementation ("androidx.compose.ui:ui-tooling")
	
	implementation("com.google.dagger:hilt-android:2.57.1")
	ksp("com.google.dagger:hilt-compiler:2.57.1")
	implementation("androidx.hilt:hilt-navigation-compose:1.3.0")
	
	implementation(platform("com.google.firebase:firebase-bom:33.0.0"))
	implementation("com.google.firebase:firebase-auth")
	implementation("com.google.firebase:firebase-database-ktx")
	
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.9.0")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
	
	implementation("io.coil-kt:coil-compose:2.7.0")
}
