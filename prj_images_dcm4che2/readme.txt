http://www.rubomedical.com/dicom_files/index.html


D:\U\toPACS>dir
 驱动器 D 中的卷是 DATA
 卷的序列号是 C8BD-3CA9

 D:\U\toPACS 的目录

2020/06/01  19:49    <DIR>          .
2020/06/01  19:49    <DIR>          ..
2020/05/31  17:23    <DIR>          3rdParty
1998/01/19  14:23           577,536 512x512x8bits_17images.DCM
2020/02/19  13:47            69,564 clibwrapper_jiio.jar
2020/05/31  17:25    <DIR>          dcm4che-5.22.1
2020/05/31  17:25    <DIR>          dcm4che2
2016/06/12  09:46         1,942,445 dcm4che2.zip
2020/05/31  19:26    <DIR>          dcm4che3
2020/06/01  19:49    <DIR>          libs
2020/05/25  17:11               275 mySendToPacs.sh
2020/06/01  19:50         2,840,486 out.dcm
2020/05/25  16:54               236 readme.txt
2017/05/17  15:03               311 sendToPacs.bat
2020/05/25  17:16               378 sendToPacs.sh
2020/05/31  17:26    <DIR>          testdir
2020/05/31  17:26    <DIR>          test_hospital
1998/01/19  14:29           426,776 Ultrasound_600x430x8bit_11images.DCM
               9 个文件      5,858,007 字节
               9 个目录 162,012,672,000 可用字节

D:\U\toPACS>
 D:\U\toPACS\libs 的目录

2020/06/01  19:49    <DIR>          .
2020/06/01  19:49    <DIR>          ..
2020/02/19  13:47         1,172,845 jai_imageio.jar
               1 个文件      1,172,845 字节
               3 个目录 162,012,667,904 可用字节

D:\U\toPACS>
D:\U\toPACS>
D:\U\toPACS>
D:\U\toPACS>set JIIO_LIB=D:\U\toPACS\libs

D:\U\toPACS>dcm4che2\bin\dcm2dcm Ultrasound_600x430x8bit_11images.DCM out.dcm
.
converted 1 file(s) in 1.613 s.

D:\U\toPACS>