/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.warranty;
import org.junit.jupiter.api.Test; import org.springframework.beans.factory.annotation.Autowired; import org.springframework.boot.test.context.SpringBootTest; import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc; import org.springframework.http.MediaType; import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic; import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post; import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc class EnterpriseWarrantyApiTests { @Autowired MockMvc mvc;

 @Test void validClaimIsCoveredAndCappedByPolicy() throws Exception {mvc.perform(post("/api/enterprise/warranty/check-coverage").with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON).content("""
 {"serialNo":"SN-001","purchaseDate":"2025-08-01","claimDate":"2026-08-01","warrantyMonths":24,"purchaseProofVerified":true,"excludedFailure":false,"customerMisuse":false,"estimatedCost":3000,"policyLimit":2000}
 """)).andExpect(status().isOk()).andExpect(jsonPath("$.data.covered").value(true)).andExpect(jsonPath("$.data.approvedReserve").value(2000.00)).andExpect(jsonPath("$.data.decision").value("COVERED"));}
 @Test void expiredAndMisuseClaimIsRejected() throws Exception {mvc.perform(post("/api/enterprise/warranty/check-coverage").with(httpBasic("operator","operator123")).contentType(MediaType.APPLICATION_JSON).content("""
 {"serialNo":"SN-002","purchaseDate":"2023-01-01","claimDate":"2026-08-01","warrantyMonths":12,"purchaseProofVerified":true,"excludedFailure":false,"customerMisuse":true,"estimatedCost":1000,"policyLimit":2000}
 """)).andExpect(status().isOk()).andExpect(jsonPath("$.data.covered").value(false)).andExpect(jsonPath("$.data.blockers.length()").value(2));}
}

