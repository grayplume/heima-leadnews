package com.heima.article.service;

import com.heima.model.article.pojos.ApArticle;

public interface ArticleFreemarkerService {
    /**
     * 生成静态文件上传到minIO中
     * @param apArticle 文章
     * @param content 文章内容
     */
    public void buildArticleToMinIO(ApArticle apArticle,String content);
}
