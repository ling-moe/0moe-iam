package cn.lingmoe.iam.api.controller;

import cn.lingmoe.common.Result.Results;
import cn.lingmoe.iam.app.service.ISysNoticeService;
import cn.lingmoe.iam.domain.entity.SysNotice;
import cn.lingmoe.mybatis.page.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 通知公告 提供者
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-20
 */
@RestController
@RequestMapping("/notice")
public class SysNoticeClient {

    @Autowired
    private ISysNoticeService sysNoticeService;

    /**
     * 查询通知公告
     */
    @GetMapping("/get/{noticeId}")
    public ResponseEntity<SysNotice>  get(@PathVariable("noticeId") Long noticeId) {
        return Results.ok(sysNoticeService.selectNoticeById(noticeId));

    }

    /**
     * 查询通知公告列表
     */
    @PostMapping("/list")
    public ResponseEntity<PageInfo<SysNotice>> list(SysNotice sysNotice, PageRequest pageRequest) {
        return Results.ok(PageHelper.startPage(pageRequest).doSelectPageInfo(()->sysNoticeService.selectNoticeList(sysNotice)));
    }


    /**
     * 新增保存通知公告
     */
    @PostMapping("/save")
    public ResponseEntity<Void> addSave(SysNotice sysNotice) {
        sysNoticeService.insertNotice(sysNotice);
        return Results.ok();
    }

    /**
     * 修改保存通知公告
     */
    @PostMapping("/update")
    public ResponseEntity<Void> editSave(SysNotice sysNotice) {
        sysNoticeService.updateNotice(sysNotice);
        return Results.ok();
    }

    /**
     * 删除通知公告
     */
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(String ids) {
        sysNoticeService.deleteNoticeByIds(ids);
        return Results.ok();
    }

}
