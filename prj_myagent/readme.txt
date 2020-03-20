How to create myhack2.jar

1. right click project 'prj_myagent'--> Export... --> java/JAR file 
2. select the resources to export
3. set JAR file:  /home/wf/myhack2.jar
4. click Next --> select 'Generate the manifest file' ,  Uncheck sub two checkbox
5. cp -r prj_myagent/resources/META-INF /home/wf/
6. cd /home/wf
7. jar -uvfM myhack2.jar META-INF/


==========================================================================


myhack2.jar
│  MyAgent.class
│  MyTransformer.class
│
├─lib
│      javassist.jar
│
└─META-INF
        MANIFEST.MF



MANIFEST.MF
------------------------
Manifest-Version: 1.0 
Premain-Class: MyAgent
------------------------


==========================================================================


java -javaagent:./resources/lib/myhack2.jar -Xms4g -Xmx5g -Dfile.encoding=UTF-8 -Dawt.useSystemAAFontSettings=on -Djava.util.Arrays.useLegacyMergeSort=true com.bowing.uiapp.AppMain

