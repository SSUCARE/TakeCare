package com.ssu.takecare.Retrofit.RetrofitCustomCallback;

import com.ssu.takecare.Retrofit.Comment.DataGetComment;
import java.util.List;

public interface RetrofitCommentCallback {
    void onError(Throwable t);

    void onSuccess(String message, List<DataGetComment> data);

    void onFailure(int error_code);
}
