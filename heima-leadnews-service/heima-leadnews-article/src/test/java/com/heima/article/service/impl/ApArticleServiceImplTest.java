package com.heima.article.service.impl;

import com.heima.article.ArticleApplication;
import com.heima.common.constants.BehaviorConstants;
import com.heima.common.redis.CacheService;
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = ArticleApplication.class)
@RunWith(SpringRunner.class)
class ApArticleServiceImplTest {

    @Autowired
    private CacheService cacheService;

    @Test
    void loadArticleBehavior() {
        ApUser user = AppThreadLocalUtil.getUser();
        System.out.println("线程中获取用户id："+user);
        String likeBehaviorJson = (String) cacheService.hGet(BehaviorConstants.LIKE_BEHAVIOR + "1683366584141353000", "4");
        if(StringUtils.isNotBlank(likeBehaviorJson)){
            System.out.println("有的");
        }
    }
}