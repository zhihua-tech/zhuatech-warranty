/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.warranty.domain;

import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class DomainCatalog {
    private final Map<String, WorkflowAction> actions = new LinkedHashMap<>();

    public DomainCatalog() {
        actions.put("VALIDATE", new WorkflowAction("VALIDATE", "完成权益校验", List.of("已登记"), "待鉴定", "OPERATOR"));
        actions.put("AUTHORIZE", new WorkflowAction("AUTHORIZE", "授权服务", List.of("待鉴定"), "已授权", "ADMIN"));
        actions.put("REPAIR", new WorkflowAction("REPAIR", "开始维修", List.of("已授权"), "维修中", "OPERATOR"));
        actions.put("SETTLE", new WorkflowAction("SETTLE", "完成结案", List.of("维修中"), "已结案", "ADMIN"));
        actions.put("REJECT", new WorkflowAction("REJECT", "拒绝索赔", List.of("待鉴定"), "已拒绝", "ADMIN"));
    }

    public String systemName() { return "知华科技售后与质保管理系统"; }
    public String scene() { return "产品序列号、质保权益、索赔、鉴定、维修、备件、结算与召回"; }
    public String initialStatus() { return "已登记"; }
    public String partyLabel() { return "客户/产品"; }
    public String amountLabel() { return "索赔金额"; }
    public String quantityLabel() { return "产品/备件数量"; }
    public String dueLabel() { return "服务期限"; }

    public List<ModuleDefinition> modules() {
        return List.of(
            new ModuleDefinition("PRODUCT", "产品主数据", "维护产品、型号、批次和序列号规则"),
            new ModuleDefinition("SERIAL", "序列号履历", "追踪生产、销售、安装、维修和更换履历"),
            new ModuleDefinition("POLICY", "质保政策", "配置期限、覆盖范围、除外责任和服务等级"),
            new ModuleDefinition("ENTITLEMENT", "权益校验", "根据序列号、购买日、客户和政策判断权益"),
            new ModuleDefinition("REGISTRATION", "产品注册", "采集购买凭证、安装信息和终端客户"),
            new ModuleDefinition("CLAIM", "质保索赔", "登记故障、费用、证据和服务诉求"),
            new ModuleDefinition("DIAGNOSIS", "故障鉴定", "记录检测步骤、故障代码、责任与结论"),
            new ModuleDefinition("REPAIR", "维修工单", "编排服务商、技师、时限和维修动作"),
            new ModuleDefinition("PARTS", "备件管理", "申请、领用、退回、旧件回收和序列追踪"),
            new ModuleDefinition("SETTLEMENT", "费用结算", "审核工时、备件、物流并与服务商结算"),
            new ModuleDefinition("RECALL", "召回管理", "识别影响范围并追踪通知、处理与完成率")
        );
    }

    public Map<String, WorkflowAction> actions() { return Collections.unmodifiableMap(actions); }

    public record ModuleDefinition(String code, String name, String description) {}
    public record WorkflowAction(String code, String label, List<String> from, String to, String requiredRole) {}
}
