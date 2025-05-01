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
    public String saveUserAvatar(Long userId, MultipartFile file) throws IOException {
        logger.info("保存用户头像, userId: {}, 文件大小: {} bytes", userId, file.getSize());

        // 验证文件类型
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("不支持的文件类型: " + contentType);
        }

        // 生成文件名和存储路径
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".") ?
                originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
        String fileName = UUID.randomUUID().toString() + extension;
        String relativePath = "/avatar/" + userId + "/" + fileName;
        String absolutePath = imageStoragePath + relativePath;

        // 确保目录存在
        Path dirPath = Paths.get(absolutePath).getParent();
        if (dirPath != null) {
            Files.createDirectories(dirPath);
        }
        // 生成访问URL
        String imageUrl = imageAccessBaseUrl + relativePath;

        // 存储到数据库
        ImageStorage imageStorage = new ImageStorage();
        imageStorage.setUserId(userId);
        imageStorage.setImageType("AVATAR");
        imageStorage.setImageData(file.getBytes());

        // 插入数据库
        imageStorageDao.insertAndReturnId(imageStorage);
        logger.info("用户头像保存成功, userId: {}, imageId: {}", userId, imageStorage.getImageId());

        return imageUrl;
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