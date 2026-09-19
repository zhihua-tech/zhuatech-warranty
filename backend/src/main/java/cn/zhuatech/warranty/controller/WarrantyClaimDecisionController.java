/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.warranty.controller;

import cn.zhuatech.warranty.common.ApiResponse;
import cn.zhuatech.warranty.service.WarrantyClaimDecisionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController
@RequestMapping("/api/enterprise/warranty")
public class WarrantyClaimDecisionController {
    private final WarrantyClaimDecisionService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public WarrantyClaimDecisionController(WarrantyClaimDecisionService service) { this.service = service; }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/claim-decision")
    public ApiResponse<?> assess(@RequestBody WarrantyClaimDecisionService.Request request) {
        return ApiResponse.ok(service.assess(request));
    }
}
