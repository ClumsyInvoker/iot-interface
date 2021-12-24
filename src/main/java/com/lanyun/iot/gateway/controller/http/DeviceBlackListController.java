package com.lanyun.iot.gateway.controller.http;

import com.lanyun.iot.gateway.controller.advice.MyPage;
import com.lanyun.iot.gateway.model.dto.black.AddBlackList;
import com.lanyun.iot.gateway.model.entity.DeviceBlackList;
import com.lanyun.iot.gateway.model.exception.ProjectException;
import com.lanyun.iot.gateway.service.DeviceBlackListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author weilai
 * @email 352342845@qq.com
 * @date 2019-08-28 15:38
 */
@RestController
@RequestMapping("/datasource/devices/blacklist")
@Api(value = "黑名单")
public class DeviceBlackListController {

    @Autowired
    private DeviceBlackListService deviceBlackListService;

    @GetMapping("")
    @ApiOperation("黑名单列表")
    public MyPage<DeviceBlackList> filter(@ApiParam("设备序列号") @RequestParam(value = "serialNo", required = false) String serialNo,
                                          @ApiParam("页数") @RequestParam(value = "page", defaultValue = "1") Integer page,
                                          @ApiParam("页码") @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return deviceBlackListService.filter(serialNo, page, size);
    }

    @PostMapping("")
    @ApiOperation("添加黑名单")
    public void add(@Valid @RequestBody AddBlackList addBlackList,
                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ProjectException(bindingResult.getFieldError().getDefaultMessage());
        }
        deviceBlackListService.add(addBlackList);
    }

    @DeleteMapping("{serialNo}")
    @ApiOperation("删除黑名单")
    public void delete(@ApiParam("设备序列号") @PathVariable(value = "serialNo") String serialNo) {
        deviceBlackListService.delete(serialNo);
    }
}
