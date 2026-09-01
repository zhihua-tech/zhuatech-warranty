/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.warranty.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class WarrantyClaimDecisionServiceTest {
    private final WarrantyClaimDecisionService service = new WarrantyClaimDecisionService();

    @Test void approvesControlledWarrantyClaim() {
        var result = service.assess(new WarrantyClaimDecisionService.Request("CLM-100", true, true, true,
                false, true, true, true, true, true, true, true));
        assertThat(result.decision()).isEqualTo(WarrantyClaimDecisionService.Decision.APPROVE);
    }

    @Test void routesIncompleteClaimToAssessment() {
        var result = service.assess(new WarrantyClaimDecisionService.Request("CLM-101", false, false, true,
                false, false, true, false, false, false, true, true));
        assertThat(result.actions()).hasSize(6);
        assertThat(result.decision()).isEqualTo(WarrantyClaimDecisionService.Decision.ASSESS);
    }

    @Test void blocksIneligibleOrSuspiciousClaim() {
        var result = service.assess(new WarrantyClaimDecisionService.Request("", false, false, false,
                true, false, false, false, false, false, false, false));
        assertThat(result.blockers()).hasSize(6);
        assertThat(result.decision()).isEqualTo(WarrantyClaimDecisionService.Decision.BLOCKED);
    }
}
