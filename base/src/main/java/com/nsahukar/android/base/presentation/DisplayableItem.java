package com.nsahukar.android.base.presentation;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * Created by Nikhil on 08/12/17.
 */

@AutoValue
public abstract class DisplayableItem<T> {
    public abstract int type();

    @NonNull
    public abstract T model();

    @AutoValue.Builder
    public abstract static class Builder<T> {

        @NonNull
        public abstract Builder<T> type(@NonNull int type);

        @NonNull
        public abstract Builder<T> model(@NonNull T model);

        @NonNull
        public abstract DisplayableItem<T> build();
    }

    @NonNull
    public static <T> Builder<T> builder() {
        return new AutoValue_DisplayableItem.Builder<>();
    }

    @NonNull
    public static DisplayableItem toDisplayableItem(@NonNull final Object model, final int type) {
        return DisplayableItem.builder().type(type).model(model).build();
    }
}
