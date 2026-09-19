/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.warranty.controller;
import cn.zhuatech.warranty.common.ApiResponse; import cn.zhuatech.warranty.service.EnterpriseWarrantyService; import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/warranty") public class EnterpriseWarrantyController {
 private final EnterpriseWarrantyService service; /**
                                                   * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
                                                   */
public EnterpriseWarrantyController(EnterpriseWarrantyService service){this.service=service;}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 @PostMapping("/check-coverage") ApiResponse<?> execute(@Valid @RequestBody EnterpriseWarrantyService.CoverageRequest request){return ApiResponse.ok(service.check(request));}
}

