package main;

import utils.ImgCompressUtils;

import java.net.URL;

public class Demo {
    public static void main(String[] args) throws Exception {
        String img = "d:/photo/16pic_7216881_131c2bf9.jpg";//预压缩图片位置
        String path = "d:/photo";//压缩后图片存放位置
        float size = 88f;//压缩后的图片大小不超过150.5kb
        Integer maxLenth = 100;//压缩后最长边不超过;
        Long size1 = ImgCompressUtils.compression(img, path, "压缩大小后的图片", size);
        System.out.println("压缩大小后图片大小为：" + size1);
        Long size2 = ImgCompressUtils.compressSize(img, path, "压缩尺寸后的图片", maxLenth);
        System.out.println("压缩尺寸后的图片大小为：" + size2);

        //网络图片的url
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1587383822474&di=27209bc2fb2f130c433765047ee81987&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Farchive%2F8f3242e09a9a15b217fa27d956752419d3165f5d.jpg";
        URL imgUrl = new URL(url);
        Long size3 = ImgCompressUtils.urlCompression(imgUrl, path, "压缩后的网络图片", size, true, maxLenth);
        System.out.println("压缩后的网络图片大小为：" + size3);
    }

}
