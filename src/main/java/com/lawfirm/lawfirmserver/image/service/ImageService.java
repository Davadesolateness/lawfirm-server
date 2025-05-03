package com.lawfirm.lawfirmserver.image.service;

import com.lawfirm.lawfirmserver.image.dao.ImageStorageDao;
import com.lawfirm.lawfirmserver.image.po.ImageStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * 图片服务类
 */
@Service
@Transactional
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageStorageDao imageStorageDao;

    @Value("${image.storage.path:./images}")
    private String imageStoragePath;

    @Value("${image.access.base-url:http://localhost:8080/images}")
    private String imageAccessBaseUrl;

    /**
     * 保存用户头像
     *
     * @param userId 用户ID
     * @param file   头像文件
     * @return 头像访问URL
     * @throws IOException 处理文件时可能抛出IO异常
     */
    public void saveUserAvatar(Long userId, MultipartFile file) throws IOException {
        logger.info("保存用户头像, userId: {}, 文件大小: {} bytes", userId, file.getSize());

        // 验证文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("不支持的文件类型: " + contentType);
        }

        // 获取文件扩展名
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        // 存储到数据库
        ImageStorage imageStorage = new ImageStorage();
        imageStorage.setUserId(userId);
        imageStorage.setImageType("AVATAR");
        imageStorage.setImageData(file.getBytes());
        // 设置文件扩展名
        imageStorage.setFileExtension(extension);

        // 插入数据库
        imageStorageDao.insertAndReturnId(imageStorage);
        logger.info("用户头像保存成功, userId: {}, imageId: {}, 扩展名: {}",
                userId, imageStorage.getImageId(), extension);

    }

    /**
     * 从文件名中提取文件扩展名
     *
     * @param filename 文件名
     * @return 文件扩展名（不包含点号）
     */
    private String getFileExtension(String filename) {
        if (filename == null || filename.isEmpty()) {
            // 默认扩展名
            return "jpg";
        }

        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            String extension = filename.substring(dotIndex + 1).toLowerCase();
            // 只允许常见图片格式
            if ("jpg".equals(extension) || "jpeg".equals(extension) ||
                    "png".equals(extension) || "gif".equals(extension)) {
                return extension;
            }
        }

        // 如果没有有效扩展名，根据文件内容类型判断
        // 默认扩展名
        return "jpg";
    }

    /**
     * 获取用户最新的头像URL
     *
     * @param userId 用户ID
     * @return 头像URL，如果没有则返回null
     */
    public String getUserAvatarUrl(Long userId) {
        ImageStorage imageStorage = imageStorageDao.selectLatestAvatarByUserId(userId);
        return imageStorage != null ? imageAccessBaseUrl + "/avatar/" + userId + "/" + imageStorage.getImageId() : null;
    }

    /**
     * 获取用户最新的头像数据
     *
     * @param userId 用户ID
     * @return 头像图片数据，如果没有则返回null
     */
    public ImageStorage getUserAvatar(Long userId) {
        return imageStorageDao.selectLatestAvatarByUserId(userId);
    }

    /**
     * 根据图片ID获取图片数据
     *
     * @param imageId 图片ID
     * @return 图片存储对象
     */
    public ImageStorage getImageById(Long imageId) {
        return imageStorageDao.selectByPrimaryKey(imageId);
    }
}