package com.nsahukar.android.xyzreader.application;

import com.nsahukar.android.base.injection.scopes.ApplicationScope;

import dagger.Component;
import dagger.android.AndroidInjector;

/**
 * Created by Nikhil on 08/12/17.
 */

@ApplicationScope
@Component(modules = {
        ApplicationModule.class,
        ActivityModule.class,
        NetworkModule.class,
        DataModule.class})
public interface ApplicationComponent extends AndroidInjector<XYZReaderApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<XYZReaderApplication> {
    }
}