/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.warranty.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@Service
public class WarrantyClaimDecisionService {
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public Result assess(Request request) {
        var blockers = new ArrayList<String>();
        var actions = new ArrayList<String>();
        if (request.claimId() == null || request.claimId().isBlank()) blockers.add("质保索赔编号不能为空");
        if (!request.coverageActive()) blockers.add("产品不在有效质保范围");
        if (request.exclusionDetected()) blockers.add("命中质保除外责任");
        if (!request.fraudCheckClear()) blockers.add("索赔反欺诈检查未通过");
        if (!request.decisionMakerSeparated()) blockers.add("诊断与赔付审批未职责分离");
        if (!request.auditReady()) blockers.add("索赔决策审计证据不完整");
        if (!request.proofOfPurchase()) actions.add("补充购买凭证");
        if (!request.serialVerified()) actions.add("核验产品序列号");
        if (!request.diagnosticComplete()) actions.add("完成故障诊断");
        if (!request.repairEstimateApproved()) actions.add("审批维修估价");
        if (!request.replacementStockAvailable()) actions.add("确认换货库存或替代方案");
        if (!request.customerConsent()) actions.add("取得客户维修或换货确认");
        var decision = !blockers.isEmpty() ? Decision.BLOCKED : actions.isEmpty() ? Decision.APPROVE : Decision.ASSESS;
        return new Result(decision, List.copyOf(blockers), List.copyOf(actions));
    }

    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public enum Decision { APPROVE, ASSESS, BLOCKED }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Request(String claimId, boolean proofOfPurchase, boolean serialVerified,
                          boolean coverageActive, boolean exclusionDetected, boolean diagnosticComplete,
                          boolean fraudCheckClear, boolean repairEstimateApproved,
                          boolean replacementStockAvailable, boolean customerConsent,
                          boolean decisionMakerSeparated, boolean auditReady) {}
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public record Result(Decision decision, List<String> blockers, List<String> actions) {}
}
