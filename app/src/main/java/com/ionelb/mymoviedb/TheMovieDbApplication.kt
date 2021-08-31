package com.ionelb.mymoviedb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


// All apps using Hilt must contain an Application class annotated with @HiltAndroidApp.
// @HiltAndroidApp triggers Hilt's code generation, including a base class for your application
// that serves as the application-level dependency container.
// This generated Hilt component is attached to the Application object's lifecycle and provides dependencies to it.
@HiltAndroidApp
class TheMovieDbApplication: Application() {
}