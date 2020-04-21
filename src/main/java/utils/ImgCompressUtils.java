package utils;

import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dzh
 * @date 2020-4-20
 * @email dzhonline@163.com
 */
public class ImgCompressUtils {
    /**
     * 图片压缩工具
     *
     * @param imgPath  原图片路径
     * @param path     图片存放位置
     * @param fileName 压缩后的图片名称
     * @param size     指定压缩大小(kb)
     * @throws IOException
     */
    public static Long compression(String imgPath, String path, String fileName, float size) throws Exception {
        File img = new File(imgPath);

        // 检查文件是否为图片
        BufferedImage bi = ImageIO.read(img);
        if (bi == null) {
            throw new Exception("压缩的文件不是图片！");
        }
        // 先压缩一次，产生新的图片，再在此图片基础上进行判断和操作。

        Thumbnails.of(imgPath).scale(1f).outputQuality(0.9f).toFile(path + fileName);

        File imgC = new File(path + fileName);
        // 大于指定大小才需要继续压缩
        while (imgC.length() > size * 1024) {
            Thumbnails.of(path + fileName).scale(0.9f).outputQuality(0.9f).toFile(path + fileName);
        }
        return new File(path + fileName).length();
    }

    /**
     * 对图片的尺寸进行压缩
     *
     * @param imgPath  原图片路径
     * @param path     图片存放位置
     * @param fileName 压缩后的图片名称
     * @param maxLenth 最长边不超过
     * @throws IOException
     */
    public static Long compressSize(String imgPath, String path, String fileName, int maxLenth) throws Exception {
        File img = new File(imgPath);

        // 检查文件是否为图片
        BufferedImage bi = ImageIO.read(img);
        if (bi == null) {
            throw new Exception("压缩的文件不是图片！");
        }
        // 满足条件先压缩一次，产生新的图片，再在此图片基础上进行判断和操作。

        int big = Math.max(bi.getWidth(), bi.getHeight());
        if (big > maxLenth) {
            Thumbnails.of(imgPath).scale(0.9f).outputQuality(1f).toFile(path + fileName);
        }
        BufferedImage imgC = ImageIO.read(new File(path + fileName));
        big = Math.max(imgC.getWidth(), imgC.getHeight());
        // 大于指定大小才需要压缩
        while (big > maxLenth) {
            Thumbnails.of(path + fileName).scale(0.9f).outputQuality(1f).toFile(path + fileName);
            BufferedImage imgM = ImageIO.read(new File(path + fileName));
            big = Math.max(imgM.getWidth(), imgM.getHeight());
        }
        return new File(path + fileName).length();
    }

    /**
     * 通过url来压缩并上传图片
     *
     * @param url      图片url
     * @param local    文件存放位置
     * @param fileName 文件名称
     * @param size     指定压缩大小(kb)
     * @param flat     是否需要进行尺寸压缩
     * @param maxLenth 尺寸压缩最长边不超过
     * @return
     * @throws IOException
     */
    public static long urlCompression(URL url, String local, String fileName, float size, Boolean flat, int maxLenth) throws Exception {
        Map compression = new HashMap();
        String localpath = local + fileName;

        // 检查文件是否为图片
        BufferedImage bi = ImageIO.read(url);
        if (bi == null) {
            throw new Exception("压缩的文件不是图片！");
        }
        Thumbnails.of(url).scale(1f).outputQuality(0.9f).toFile(localpath);

        File imgC = new File(localpath);
        if (size > 0) {
            // 大于指定大小才需要继续压缩
            while (imgC.length() > size * 1024) {
                Thumbnails.of(localpath).scale(0.9f).outputQuality(0.9f).toFile(localpath);
            }
        }
        // 判断是否需要继续进行尺寸压缩
        if (flat && maxLenth > 0) {
            int big = Math.max(bi.getWidth(), bi.getHeight());
            while (big > maxLenth) {
                Thumbnails.of(localpath).scale(0.9f).outputQuality(1f).toFile(localpath);
                BufferedImage imgM = ImageIO.read(new File(localpath));
                big = Math.max(imgM.getWidth(), imgM.getHeight());
            }
        }

        return new File(localpath).length();
    }
}
