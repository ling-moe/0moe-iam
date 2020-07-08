package cn.lingmoe.iam.api.controller;

import java.util.List;

import cn.lingmoe.common.Result.Results;
import cn.lingmoe.iam.app.service.ISysPostService;
import cn.lingmoe.iam.domain.entity.SysPost;
import cn.lingmoe.mybatis.page.PageRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 岗位 提供者
 *
 * @author yukdawn@gmail.com
 * @date 2019-05-20
 */
@RestController
@RequestMapping("/sys/sysPost")
public class SysPostController {

    @Autowired
    private ISysPostService sysPostService;

    /**
     * 查询岗位
     */
    @GetMapping("/get/{postId}")
    public ResponseEntity<SysPost> get(@PathVariable("postId") Long postId) {
        return Results.ok(sysPostService.selectPostById(postId));

    }

    /**
     * 查询岗位列表
     */
    @PostMapping("/list")
    public ResponseEntity<PageInfo<SysPost>> list(SysPost sysPost, PageRequest pageRequest) {
        return Results.ok(PageHelper.startPage(pageRequest).doSelectPageInfo(()->sysPostService.selectPostList(sysPost)));
    }


    /**
     * 新增保存岗位
     */
    @PostMapping("/save")
    public ResponseEntity<Void> addSave(SysPost sysPost) {
        sysPostService.insertPost(sysPost);
        return Results.ok();
    }

    /**
     * 修改保存岗位
     */
    @PostMapping("/update")
    public ResponseEntity<Void> editSave(SysPost sysPost) {
        sysPostService.updatePost(sysPost);
        return Results.ok();
    }

    /**
     * 删除岗位
     *
     * @throws Exception
     */
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(String ids) throws Exception {
        sysPostService.deletePostByIds(ids);
        return Results.ok();
    }

}
