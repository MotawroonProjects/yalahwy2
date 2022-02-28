package com.app.yalahwy.mvp.fragment_comments_mvp;

import com.app.yalahwy.models.CommentModel;
import com.app.yalahwy.models.ProductModel;

public interface FragmentCommentView {
    void onSuccess(CommentModel commentModel);
    void onFailed(String msg);


}
