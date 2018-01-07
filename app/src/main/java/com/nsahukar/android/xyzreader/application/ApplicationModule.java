package com.nsahukar.android.xyzreader.application;

import android.app.Application;

import dagger.Binds;
import dagger.Module;
import dagger.android.AndroidInjectionModule;

/**
 * Created by Nikhil on 09/12/17.
 */

@Module(includes = AndroidInjectionModule.class)
abstract class ApplicationModule {
    @Binds
    abstract Application application(XYZReaderApplication application);
}
