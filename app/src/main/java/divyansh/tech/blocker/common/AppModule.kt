package divyansh.tech.blocker.common

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import divyansh.tech.blocker.common.database.BlockedContactDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): BlockedContactDatabase =
        Room.databaseBuilder(
            context,
            BlockedContactDatabase::class.java,
            "blocked_contacts"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideContactsDao(db: BlockedContactDatabase) = db.getContactDao()
}