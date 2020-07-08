package cn.lingmoe.iam.domain.entity;

import cn.lingmoe.common.excel.anno.Excel;
import cn.lingmoe.mybatis.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 岗位表 sys_post
 *
 * @author ruoyi
 */
@Getter
@Setter
public class SysPost extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * 岗位序号
     */
    @Excel(name = "岗位序号")
    private Long postId;

    /**
     * 岗位编码
     */
    @Excel(name = "岗位编码")
    private String postCode;

    /**
     * 岗位名称
     */
    @Excel(name = "岗位名称")
    private String postName;

    /**
     * 岗位排序
     */
    @Excel(name = "岗位排序")
    private String postSort;

    /**
     * 状态（0正常 1停用）
     */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /**
     * 用户是否存在此岗位标识 默认不存在
     */
    private boolean flag = false;
}
