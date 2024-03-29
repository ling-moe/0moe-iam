package cn.lingmoe.iam.domain.entity;

import cn.lingmoe.mybatis.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

/**
 * 地区表 districts
 *
 * @author ruoyi
 * @date 2018-12-19
 */
@Setter
@Getter
public class Districts extends BaseDomain {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Integer id;

    /**
     * 上级编号
     */
    private Integer pid;

    /**
     * 层级
     */
    private Integer deep;

    /**
     * 名称
     */
    private String name;

    /**
     * 上级名称
     */
    private String pname;

    /**
     * 拼音
     */
    private String pinyin;

    /**
     * 拼音缩写
     */
    private String pinyinShor;

    /**
     * 扩展名
     */
    private String extName;

    /**
     * 操作人
     */
    private String operator;

}
