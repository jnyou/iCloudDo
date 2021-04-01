package io.jnyou.core.enums;

/**
 * @author jnyou
 */

public class WebCameras {

    public enum WebCamera {
        HUAWEICAMERA("HuaweiCamera","华为网络摄像机"),
        HIKVISIONCAMERA("HikVisionCamera","海康网络摄像机"),
        HIK_YP01_CAMERA("Hik_YP01_Camera","海康硬盘录像机01"),
        DFWL_DAHUA_CAMERA("DFWL_DaHua_Camera","东方网力流媒体大华摄像机-01"),
        DFWL_DAHUA_CAMERA_YUANQU("DFWL_DaHua_Camera_yuanqu","东方网力流媒体大华摄像机-02"),
        DFWL_HAIKANG_CAMERA_YUANQU("DFWL_HaiKang_Camera_yuanqu","东方网力流媒体园区海康摄像机"),
        DFWL_HAIKANG_CAMERA("DFWL_HaiKang_Camera","东方网力流媒体海康摄像机"),
        DFWL_YUSHI_CAMERA("DFWL_YuShi_Camera","东方网力流媒体宇视摄像机"),
        BLUESKYCAMERA("BlueSkyCamera","蓝色星际网络摄像机"),
        SIMULATEVLCCAMERA("SimulateVLCCamera","模拟网络摄像机"),
        YC_DAHUA_CAMERA("YC_DAHUA_Camera","大华摄像机8070"),
        YC_HIK_CAMERA("YC_HIK_Camera","海康摄像机8070"),
        YUSHI_CAMERA("YuShi_Camera","宇视网络摄像机"),
        CIZING_CAMERA("CiZing_Camera","信智联流媒体摄像机")
        ;
        private String name;
        private String caption;
        WebCamera(String name,String caption){
            this.name = name;
            this.caption = caption;
        }

        public String getName() {
            return name;
        }

        public String getCaption() {
            return caption;
        }
    }



}
