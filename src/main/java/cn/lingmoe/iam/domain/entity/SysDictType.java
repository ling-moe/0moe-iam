package cn.lingmoe.iam.domain.entity;

import cn.lingmoe.common.excel.anno.Excel;
import cn.lingmoe.mybatis.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 字典类型表 sys_dict_type
 *
 * @author ruoyi
 */
@Getter
@Setter
public class SysDictType extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * 字典主键
     */
    @Excel(name = "字典主键")
    private Long dictId;

    /**
     * 字典名称
     */
    @Excel(name = "字典名称")
    private String dictName;

    /**
     * 字典类型
     */
    @Excel(name = "字典类型 ")
    private String dictType;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;
}
