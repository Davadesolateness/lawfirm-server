package com.lawfirm.lawfirmserver.image.api;

import com.lawfirm.lawfirmserver.common.Result;
import com.lawfirm.lawfirmserver.image.po.ImageStorage;
import com.lawfirm.lawfirmserver.image.service.ImageService;
import com.lawfirm.lawfirmserver.image.vo.ImageUploadResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片管理API
 */
@RestController
@RequestMapping("/image")
@Api(tags = "图片管理", description = "提供图片上传和查询服务")
public class ImageApi {

    private static final Logger logger = LoggerFactory.getLogger(ImageApi.class);

    @Autowired
    private ImageService imageService;

    /**
     * 上传用户头像
     *
     * @param userId 用户ID
     * @param file   头像文件
     * @return 上传结果
     */
    @ApiOperation(value = "上传用户头像", notes = "上传用户头像并返回访问URL和Base64格式的图片数据")
    @PostMapping("/uploadAvatar")
    public Result<ImageUploadResult> uploadAvatar(
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") String userId,
            @ApiParam(value = "头像文件", required = true) @RequestParam("file") MultipartFile file) {

        try {
            logger.info("接收到用户头像上传请求, userId: {}, 文件名: {}, 大小: {}",
                    userId, file.getOriginalFilename(), file.getSize());

            // 验证文件大小
            if (file.isEmpty()) {
                logger.warn("上传文件为空, userId: {}", userId);
                return Result.fail("上传文件不能为空");
            }

            // 最大允许4MB
            if (file.getSize() > 4 * 1024 * 1024) {
                logger.warn("文件超过最大限制, userId: {}, 文件大小: {}", userId, file.getSize());
                return Result.fail("文件大小不能超过4MB");
            }

            // 调用服务保存头像
            imageService.saveUserAvatar(Long.valueOf(userId), file);
            ImageUploadResult result = imageService.organizationAvatarResultById(Long.valueOf(userId));
            logger.info("用户头像上传成功, userId: {}, ", userId);
            return Result.success("头像上传成功", result);

        } catch (Exception e) {
            logger.error("用户头像上传失败, userId: {}", userId, e);
            return Result.fail("头像上传失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户头像URL
     *
     * @param id 用户ID
     * @return 头像URL
     */
    @ApiOperation(value = "获取用户头像URL", notes = "获取用户最新的头像URL")
    @GetMapping("/getAvatarById")
    public Result<ImageUploadResult> getAvatarById(
            @ApiParam(value = "用户ID", required = true) @RequestParam("userId") Long id) {
        try {
            String avatarUrl = imageService.getUserAvatarUrl(id);
            ImageUploadResult result = imageService.organizationAvatarResultById(id);
            if (result != null) {
                return Result.success("头像上传成功", result);
            } else {
                return Result.success("用户没有上传头像", null);
            }
        } catch (Exception e) {
            logger.error("获取用户头像失败, id: {}", id, e);
            return Result.fail("获取头像失败: " + e.getMessage());
        }
    }

    /**
     * 直接获取用户头像图片数据，适合前端直接展示
     *
     * @param userId 用户ID
     * @return 头像图片的二进制数据
     */
    @ApiOperation(value = "获取用户头像图片", notes = "直接获取用户头像图片数据，便于前端展示")
    @GetMapping("/avatar/{userId}")
    public ResponseEntity<byte[]> getUserAvatar(
            @ApiParam(value = "用户ID", required = true) @PathVariable("userId") Long userId) {

        try {
            logger.info("获取用户头像图片, userId: {}", userId);

            // 从数据库获取头像数据
            ImageStorage image = imageService.getUserAvatar(userId);

            if (image != null && image.getImageData() != null) {
                // 设置响应头
                HttpHeaders headers = new HttpHeaders();
                // 默认JPEG格式，实际应根据图片格式设置
                headers.setContentType(MediaType.IMAGE_JPEG);
                // 缓存24小时
                headers.setCacheControl("max-age=86400");
                logger.info("成功获取用户头像图片, userId: {}, imageId: {}, 大小: {} bytes",
                        userId, image.getImageId(), image.getImageData().length);

                // 返回图片数据
                return new ResponseEntity<>(image.getImageData(), headers, HttpStatus.OK);
            } else {
                logger.warn("用户头像不存在, userId: {}", userId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("获取用户头像图片失败, userId: {}", userId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据图片ID获取图片数据
     *
     * @param imageId 图片ID
     * @return 图片的二进制数据
     */
    @ApiOperation(value = "根据ID获取图片", notes = "根据图片ID直接获取图片数据")
    @GetMapping("/get/{imageId}")
    public ResponseEntity<byte[]> getImageById(
            @ApiParam(value = "图片ID", required = true) @PathVariable("imageId") Long imageId) {

        try {
            logger.info("根据ID获取图片, imageId: {}", imageId);

            // 获取图片数据
            ImageStorage image = imageService.getImageById(imageId);

            if (image != null && image.getImageData() != null) {
                // 设置响应头
                HttpHeaders headers = new HttpHeaders();
                // 默认JPEG格式，实际应根据图片类型设置
                headers.setContentType(MediaType.IMAGE_JPEG);
                // 缓存24小时
                headers.setCacheControl("max-age=86400");

                logger.info("成功获取图片, imageId: {}, 大小: {} bytes",
                        imageId, image.getImageData().length);

                // 返回图片数据
                return new ResponseEntity<>(image.getImageData(), headers, HttpStatus.OK);
            } else {
                logger.warn("图片不存在, imageId: {}", imageId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            logger.error("获取图片失败, imageId: {}", imageId, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
