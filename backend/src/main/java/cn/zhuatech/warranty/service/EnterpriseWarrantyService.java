/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.warranty.service;
import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.stereotype.Service; import java.math.*; import java.time.*; import java.util.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service public class EnterpriseWarrantyService {
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public CoverageResult check(@Valid CoverageRequest r){
  LocalDate expiry=r.purchaseDate().plusMonths(r.warrantyMonths()); List<String> blockers=new ArrayList<>(); if(r.claimDate().isBefore(r.purchaseDate())) blockers.add("索赔日期早于购买日期"); if(r.claimDate().isAfter(expiry)) blockers.add("产品已超过质保期"); if(!r.purchaseProofVerified()) blockers.add("购买凭证未核验"); if(r.excludedFailure()) blockers.add("故障属于质保除外责任"); if(r.customerMisuse()) blockers.add("存在客户不当使用");
  boolean covered=blockers.isEmpty(); BigDecimal reserve=covered?r.estimatedCost().min(r.policyLimit()).setScale(2,RoundingMode.HALF_UP):BigDecimal.ZERO.setScale(2);
  return new CoverageResult(r.serialNo(),expiry,covered,reserve,blockers,covered?"COVERED":"NOT_COVERED");
 }
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record CoverageRequest(@NotBlank String serialNo,@NotNull LocalDate purchaseDate,@NotNull LocalDate claimDate,@Min(1) int warrantyMonths,boolean purchaseProofVerified,boolean excludedFailure,boolean customerMisuse,@NotNull @DecimalMin("0") BigDecimal estimatedCost,@NotNull @DecimalMin("0") BigDecimal policyLimit){}
 /**
  * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
  */
 public record CoverageResult(String serialNo,LocalDate expiryDate,boolean covered,BigDecimal approvedReserve,List<String> blockers,String decision){}
}

