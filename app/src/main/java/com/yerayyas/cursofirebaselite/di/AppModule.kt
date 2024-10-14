package com.yerayyas.cursofirebaselite.di

import android.content.Context
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.yerayyas.cursofirebaselite.data.Repository
import com.yerayyas.cursofirebaselite.domain.usecases.CanAccessToAppUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Usar ViewModelComponent para la inyección en ViewModels
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase {
        return Firebase.database
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideRepository(): Repository {
        return Repository() // Inyectar el contexto al repositorio
    }

    @Provides
    @Singleton
    fun provideCanAccessToAppUseCase(repository: Repository): CanAccessToAppUseCase {
        return CanAccessToAppUseCase(repository)
    }
}
