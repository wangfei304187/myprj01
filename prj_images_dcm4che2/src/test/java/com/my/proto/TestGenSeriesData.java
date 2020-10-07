package com.my.proto;
import java.util.UUID;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;

public class TestGenSeriesData {
    public static void main(String[] args) {
        System.out.println("===== 构建一个模型开始 =====");
        GenSeriesData.series_data.Builder builder = GenSeriesData.series_data.newBuilder();
        builder.setBmSeriesId("12345678");
        builder.setSeriesNumber(123123);
        builder.setDescription("this is a series description");
        builder.setInstanceUID(UUID.randomUUID().toString());
        builder.setNumOfImages(300);
        builder.addPacsNames("Pacs01");
        builder.addPacsNames("Pacs02");
        builder.addPacsNames("Pacs03");
        
        GenSeriesData.series_data dataObj = builder.build();
        System.out.println(dataObj.toString());
        System.out.println("===== 构建模型结束 =====");

        System.out.println("===== Byte 开始=====");
        for(byte b : dataObj.toByteArray()){
            System.out.print(b);
        }
        System.out.println("\n" + "bytes长度" + dataObj.toByteString().size());
        System.out.println("===== Byte 结束 =====");

        System.out.println("===== 使用反序列化生成对象开始 =====");
        GenSeriesData.series_data obj = null;
        try {
        	obj = GenSeriesData.series_data.parseFrom(dataObj.toByteArray());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        System.out.print(obj.toString());
        System.out.println("===== 使用反序列化生成对象结束 =====");
        
        System.out.println("===== 使用转成json对象开始 =====");

        String jsonFormatM = "";
        try {
            jsonFormatM = JsonFormat.printer().print(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(jsonFormatM.toString());
        System.out.println("json数据大小：" + jsonFormatM.getBytes().length);
        System.out.println("===== 使用转成json对象结束 =====");

    }
}