package cn.lingmoe.iam.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.lingmoe.common.Result.Results;
import cn.lingmoe.common.utils.FileUtils;
import cn.lingmoe.common.utils.ToolUtil;
import cn.lingmoe.redis.multidb.RedisHelper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用请求处理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/common")
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private RedisHelper redisHelper;

    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @GetMapping("/download")
    public ResponseEntity<Void> fileDownload(String fileName, Boolean delete, HttpServletResponse response,
                                             HttpServletRequest request) {
        try {
            if (!FileUtils.isValidFilename(fileName)) {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName =
                    DateUtil.format(new DateTime(), DatePattern.PURE_DATETIME_PATTERN) + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = ToolUtil.getDownloadPath() + fileName;
            response.setCharacterEncoding("utf-8");
            // 下载使用"application/octet-stream"更标准
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete) {
                FileUtils.deleteFile(filePath);
            }
        } catch (Exception e) {
            log.error("下载文件失败", e);
        }
        return Results.ok();
    }

//    /**
//     * 生成验证码
//     */
//    @GetMapping("/captchaImage")
//    public ResponseEntity<Void> getCode(HttpServletResponse response) throws IOException
//    {
//        // 生成验证码
//        String capText = producer.createText();
//        String capStr = capText.substring(0, capText.lastIndexOf("@"));
//        String code = capText.substring(capText.lastIndexOf("@") + 1);
//        BufferedImage image = producer.createImage(capStr);
//        // 保存验证码信息
//        String randomStr = UUID.randomUUID().toString().replaceAll("-", "");
//        redisHelper.strSet(BaseRegexs.DEFAULT_CODE_KEY + randomStr, code, 60, TimeUnit.SECONDS);
//        // 转换流信息写出
//        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
//        try
//        {
//            ImageIO.write(image, "jpg", os);
//        }
//        catch (IOException e)
//        {
//            log.error("ImageIO write err", e);
//            return Mono.error(e);
//        }
//        return Results.ok();
//    }
}