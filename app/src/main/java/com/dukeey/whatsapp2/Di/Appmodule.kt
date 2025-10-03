package com.dukeey.whatsapp2.Di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object appModule{
	
	@Provides
	@Singleton
	
	fun prevideFirebaseDatabase():FirebaseDatabase{
		return FirebaseDatabase.getInstance()
	}

	@Provides
	@Singleton
	
	fun prevideFirebaseAuth(): FirebaseAuth{
		return FirebaseAuth.getInstance()
	}
	
	
}