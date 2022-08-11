package com.ssu.takecare.retrofit.customcallback;

import com.ssu.takecare.retrofit.comment.DataGetComment;
import java.util.List;

public interface RetrofitCommentCallback {
    void onError(Throwable t);

    void onSuccess(String message, List<DataGetComment> data);

    void onFailure(int error_code);
}
