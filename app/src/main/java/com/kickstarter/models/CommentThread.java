package com.kickstarter.models;

import android.os.Parcelable;
import com.kickstarter.libs.qualifiers.AutoGson;

import org.joda.time.DateTime;

import java.util.List;

import auto.parcel.AutoParcel;

@AutoGson
@AutoParcel
public abstract class CommentThread implements Parcelable{
    public abstract User author();
    public abstract String body();
    public abstract DateTime createdAt();
    public abstract Boolean deleted();
    public abstract long id();
    public abstract long parentId();
    public abstract List<String> authorBadges();

    @AutoParcel.Builder
    public abstract static class Builder {
        public abstract Builder author(User __);
        public abstract Builder body(String __);
        public abstract Builder createdAt(DateTime __);
        public abstract Builder deleted(Boolean __);
        public abstract Builder id(long __);
        public abstract Builder parentId(long __);
        public abstract Builder authorBadges(List<String> __);
        public abstract CommentThread build();
    }

    public static CommentThread.Builder builder() {
        return new AutoParcel_CommentThread.Builder();
    }

    public abstract CommentThread.Builder toBuilder();
}
