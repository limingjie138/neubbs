package org.neusoft.neubbs.controller.api;

import org.neusoft.neubbs.constant.api.ParamConst;
import org.neusoft.neubbs.constant.api.SetConst;
import org.neusoft.neubbs.dto.ApiJsonDTO;
import org.neusoft.neubbs.service.IHttpService;
import org.neusoft.neubbs.service.ITopicService;
import org.neusoft.neubbs.service.IUserService;
import org.neusoft.neubbs.service.IValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 统计 api
 *      - 论坛基数统计
 *          - 用户总数
 *          - 话题总数
 *          - 回复总数
 *      - 在线统计
 *          - 在线访问人数
 *          - 在线登陆人数
 *      - 用户统计
 *          - 主动关注人数
 *          - 粉丝数
 *          - 喜欢话题数
 *          - 收藏话题数
 *          - 关注话题数量
 *          - 发帖数
 *          - 回复数
 *
 * @author Suvan
 */
@RestController
@RequestMapping("/api/count")
public class CountController {

    private final IHttpService httpService;
    private final IUserService userService;
    private final ITopicService topicService;
    private final IValidationService validationService;

    @Autowired
    public CountController(IHttpService httpService, IUserService userService,
                           ITopicService topicService, IValidationService validationService) {
        this.httpService = httpService;
        this.userService = userService;
        this.topicService = topicService;
        this.validationService = validationService;
    }

    /**
     * 论坛基数统计
     *      - （CountController 默认访问）
     *      - 用户总数
     *      - 话题总数
     *      - 回复总数
     *
     * @param request http 默认请求
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ApiJsonDTO countForumBasicData(HttpServletRequest request) {
        Map<String, Object> modelMap = new LinkedHashMap<>(SetConst.SIZE_THREE);
            modelMap.put(ParamConst.USER, userService.countUserTotals());
            modelMap.put(ParamConst.TOPIC, topicService.countTopicTotals());
            modelMap.put(ParamConst.REPLY, topicService.countReplyTotals());
        return new ApiJsonDTO().success().model(modelMap);
    }

    /**
     * 在线统计
     *      - 在线登陆人数
     *
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @RequestMapping(value = "/online", method = RequestMethod.GET)
    public ApiJsonDTO countOnlineData() {
        return new ApiJsonDTO().success().buildMap(ParamConst.LOGIN_USER, httpService.getOnlineLoginUserNumber());
    }

    /**
     * 用户统计
     *      - 主动关注人数
     *      - 粉丝数
     *      - 喜欢话题数
     *      - 收藏话题数
     *      - 关注话题数
     *      - 发帖数
     *      - 回复数
     *
     * @param userId 用户 id
     * @return ApiJsonDTO 接口 JSON 传输对象
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ApiJsonDTO countUserData(@RequestParam(value = "userid", required = false) String userId) {
        validationService.check(ParamConst.USER_ID, userId);

        int userIdInt = Integer.valueOf(userId);
        Map<String, Object> modelMap = new LinkedHashMap<>(SetConst.SIZE_SEVEN);
            modelMap.put(ParamConst.FOLLOWING, userService.countUserFollowingTotals(userIdInt));
            modelMap.put(ParamConst.FOLLOWED, userService.countUserFollowedTotals(userIdInt));
            modelMap.put(ParamConst.LIKE, userService.countUserLikeTopicTotals(userIdInt));
            modelMap.put(ParamConst.COLLECT, userService.countUserCollectTopicTotals(userIdInt));
            modelMap.put(ParamConst.ATTENTION, userService.countUserAttentionTopicTotals(userIdInt));
            modelMap.put(ParamConst.TOPIC, userService.countUserTopicTotals(userIdInt));
            modelMap.put(ParamConst.REPLY, userService.countUserReplyTotals(userIdInt));

        return new ApiJsonDTO().success().model(modelMap);
    }
}
