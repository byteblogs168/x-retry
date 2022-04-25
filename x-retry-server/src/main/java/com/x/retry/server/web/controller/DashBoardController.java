package com.x.retry.server.web.controller;

import com.x.retry.server.service.DashBoardService;
import com.x.retry.server.web.annotation.LoginRequired;
import com.x.retry.server.web.model.response.ActivePodQuantityResponseVO;
import com.x.retry.server.web.model.response.DispatchQuantityResponseVO;
import com.x.retry.server.web.model.response.SceneQuantityRankResponseVO;
import com.x.retry.server.web.model.response.TaskQuantityResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: www.byteblogs.com
 * @date : 2022-04-22 20:17
 */
@RestController
@RequestMapping("/dashboard")
public class DashBoardController {

    @Autowired
    private DashBoardService dashBoardService;

    @LoginRequired
    @GetMapping("/task/count")
    public TaskQuantityResponseVO countTask() {
        return dashBoardService.countTask();
    }

    @LoginRequired
    @GetMapping("/dispatch/count")
    public DispatchQuantityResponseVO countDispatch() {
        return dashBoardService.countDispatch();
    }

    @LoginRequired
    @GetMapping("/active-pod/count")
    public ActivePodQuantityResponseVO countActivePod() {
        return dashBoardService.countActivePod();
    }

    @LoginRequired
    @GetMapping("/scene/rank")
    public List<SceneQuantityRankResponseVO> rankSceneQuantity(@RequestParam(value = "groupName", required = false)
                                                                       String groupName,
                                                               @RequestParam(value = "type") String type,
                                                               @RequestParam(value = "startTime", required = false) String startTime,
                                                               @RequestParam(value = "endTime", required = false) String endTime) {
        return dashBoardService.rankSceneQuantity(groupName, type, startTime, endTime);
    }

    //    @LoginRequired
    @GetMapping("/dispatch/line")
    public List<DispatchQuantityResponseVO> lineDispatchQuantity(@RequestParam(value = "groupName", required = false)
                                                                         String groupName,
                                                                 @RequestParam(value = "type") String type,
                                                                 @RequestParam(value = "startTime", required = false) String startTime,
                                                                 @RequestParam(value = "endTime", required = false) String endTime
    ) {
        return dashBoardService.lineDispatchQuantity(groupName, type, startTime, endTime);
    }
}
