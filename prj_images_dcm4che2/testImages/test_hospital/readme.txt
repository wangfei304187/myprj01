OC_10.dcm   OC原图
OC_YingSi_MPR_1.dcm  OC图通过影思导出



d:\toPACS>dcm4che2\bin\xml2dcm.bat testdir\todo\BM00_OC_YS.dcm -o testdir\todo\BM00_OC_YS.xml

d:\toPACS>dcm4che2\bin\dcm2xml.bat testdir\todo\BM01_OC.dcm -o testdir\todo\BM01_OC.xml

d:\toPACS>dcm4che2\bin\dcm2xml.bat testdir\todo\BM03_MF.dcm -o testdir\todo\BM03_MF.xml

d:\toPACS>dcm4che2\bin\dcm2xml.bat testdir\todo\BM04_UN.dcm -o testdir\todo\BM04_UN.xml



d:\toPACS>dcm4che2\bin\xml2dcm.bat -x testdir\todo\BM00_OC_YS.xml -o testdir\todo\BM00_OC_YSxml.dcm

d:\toPACS>dcm4che2\bin\xml2dcm.bat -x testdir\todo\BM01_OC.xml -o testdir\todo\BM01_OCxml.dcm

d:\toPACS>dcm4che2\bin\xml2dcm.bat -x testdir\todo\BM03_MF.xml -o testdir\todo\BM03_MFxml.dcm

d:\toPACS>dcm4che2\bin\xml2dcm.bat -x testdir\todo\BM04_UN.xml -o testdir\todo\BM04_UNxml.dcm




[Decompress]

d:\toPACS>dcm4che-5.22.1\bin\dcm2dcm.bat testdir\todo\COMPRESSED_BM05_SE.dcm testdir\todo\BM05_SE.dcm

d:\toPACS>dcm4che-5.22.1\bin\dcm2dcm.bat testdir\todo\COMPRESSED_BM06_PH.dcm testdir\todo\BM06_PH.dcm

d:\toPACS>

d:\toPACS>dcm4che2\bin\dcm2xml.bat testdir\todo\BM05_SE.dcm -o testdir\todo\BM05_SE.xml

d:\toPACS>dcm4che2\bin\dcm2xml.bat testdir\todo\BM06_PH.dcm -o testdir\todo\BM06_PH.xml



d:\toPACS>dcm4che2\bin\xml2dcm.bat -x testdir\todo\BM05_SE.xml -o testdir\todo\BM05_SExml.dcm

d:\toPACS>dcm4che2\bin\xml2dcm.bat -x testdir\todo\BM06_PH.xml -o testdir\todo\BM06_PHxml.dcm



d:\toPACS>dcm4che2\bin\xml2dcm.bat -x testdir\todo\BM02_GE.xml -o testdir\todo\BM02_GExml.dcm





[Comporess]

d:\toPACS>dcm4che-5.22.1\bin\dcm2dcm.bat -t 1.2.840.10008.1.2.4.90 testdir\todo\COMPRESSED_BM05_SE.dcm testdir\todo\BM05_SE.dcm
testdir\todo\COMPRESSED_BM05_SE.dcm -> testdir\todo\BM05_SE.dcm

d:\toPACS>dcm4che-5.22.1\bin\dcm2dcm.bat --j2kr testdir\todo\COMPRESSED_BM06_PH.dcm testdir\todo\BM06_PH.dcm
testdir\todo\COMPRESSED_BM06_PH.dcm -> testdir\todo\BM06_PH.dcm


================================================================================================================





d:\toPACS>dcm4che2\bin\dcm2txt -l 100 -w 200 testdir\todo\BM00_OC_YSxml.dcm
132:(0002,0000) UL #4 [258] Group Length
144:(0002,0001) OB #2 [00\01] File Meta Information Version
158:(0002,0002) UI #26 [1.2.840.10008.5.1.4.1.1.7] Media Storage SOP Class UID
192:(0002,0003) UI #46 [1.2.826.0.1.3680043.2.1264.4.20052515071487.10] Media Storage SOP Instance UID
246:(0002,0010) UI #20 [1.2.840.10008.1.2.1] Transfer Syntax UID
274:(0002,0012) UI #28 [1.2.276.0.7230010.3.0.3.6.3] Implementation Class UID
310:(0002,0013) SH #16 [OFFIS_DCMTK_363] Implementation Version Name
334:(0002,0100) UI #32 [1.2.826.0.1.3680043.2.1264.2.6.5] Private Information Creator UID
374:(0002,0102) OB #16 [4D\79\72\69\61\6E\28\72\29\20\56\31\2E\31\32\00] Private Information
402:(0008,0005) CS #8 [GB18030] Specific Character Set
418:(0008,0008) CS #18 [DERIVED\SECONDARY] Image Type
444:(0008,0012) DA #8 [20200525] Instance Creation Date
460:(0008,0013) TM #6 [150714] Instance Creation Time
474:(0008,0016) UI #26 [1.2.840.10008.5.1.4.1.1.7] SOP Class UID
508:(0008,0018) UI #46 [1.2.826.0.1.3680043.2.1264.4.20052515071487.10] SOP Instance UID
562:(0008,0020) DA #8 [20200525] Study Date
578:(0008,0021) DA #8 [20200525] Series Date
594:(0008,0030) TM #10 [140553.774] Study Time
612:(0008,0031) TM #6 [150714] Series Time
626:(0008,0050) SH #0 [] Accession Number
634:(0008,0060) CS #2 [CT] Modality
644:(0008,0064) CS #4 [WSD] Conversion Type
656:(0008,0070) LO #34 [Suzhou Bowing Medical Technologies] Manufacturer
698:(0008,0080) LO #4 [222] Institution Name
710:(0008,0090) PN #0 [] Referring Physician’s Name
718:(0008,1030) LO #6 [Thorax] Study Description
732:(0008,103E) LO #8 [斜位 CT] Series Description
748:(0008,2111) ST #16 [Myrian(r) V1.12] Derivation Description
772:(0010,0010) PN #14 [Bowing Test00] Patient’s Name
794:(0010,0020) LO #12 [200525140329] Patient ID
814:(0010,0030) DA #8 [20200525] Patient’s Birth Date
830:(0010,0040) CS #2 [O] Patient’s Sex
840:(0010,1010) AS #4 [000D] Patient’s Age
852:(0018,0050) DS #8 [5.000000] Slice Thickness
868:(0018,0060) DS #6 [120.0] KVP
882:(0018,1151) IS #4 [248] X-Ray Tube Current
894:(0018,5100) CS #4 [HFS] Patient Position
906:(0020,000D) UI #52 [1.2.156.112679.160601001.12.100138780209300138780209] Study Instance UID
966:(0020,000E) UI #48 [1.2.826.0.1.3680043.2.1264.3.100.20052515071487] Series Instance UID
1022:(0020,0010) SH #12 [300138780213] Study ID
1042:(0020,0011) IS #10 [138780243] Series Number
1060:(0020,0013) IS #2 [10] Instance Number
1070:(0020,0020) CS #4 [L\P] Patient Orientation
1082:(0020,0032) DS #30 [-149.9997\-149.9997\-645.0000] Image Position (Patient)
1120:(0020,0037) DS #42 [1.0000\0.0000\0.0000\0.0000\1.0000\0.0000] Image Orientation (Patient)
1170:(0020,0052) UI #58 [1.2.156.112679.160601001.15.100138780209300138780213.3001] Frame of Reference UID
1236:(0028,0002) US #2 [1] Samples per Pixel
1246:(0028,0004) CS #12 [MONOCHROME2] Photometric Interpretation
1266:(0028,0010) US #2 [512] Rows
1276:(0028,0011) US #2 [512] Columns
1286:(0028,0030) DS #18 [0.585938\0.585938] Pixel Spacing
1312:(0028,0100) US #2 [16] Bits Allocated
1322:(0028,0101) US #2 [16] Bits Stored
1332:(0028,0102) US #2 [15] High Bit
1342:(0028,0103) US #2 [1] Pixel Representation
1352:(0028,1050) DS #6 [-400.0] Window Center
1366:(0028,1051) DS #6 [1500.0] Window Width
1380:(0028,1052) DS #2 [0] Rescale Intercept
1390:(0028,1053) DS #2 [1] Rescale Slope
1400:(0028,1054) LO #2 [HU] Rescale Type
1410:(7FE0,0010) OW #524288 [64538\64540\64539\64539\64542\64537\64537\64537\64536\64537\64544\64536\64537\64545\64536\64537\...] Pixel Data

d:\toPACS>




d:\toPACS>dcm4che2\bin\dcm2txt -l 100 -w 200 testdir\todo\BM01_OC.dcm
132:(0002,0000) UL #4 [214] Group Length
144:(0002,0001) OB #2 [00\01] File Meta Information Version
158:(0002,0002) UI #26 [1.2.840.10008.5.1.4.1.1.2] Media Storage SOP Class UID
192:(0002,0003) UI #64 [1.2.156.112679.160601001.14.101381830993013818310301381831040010] Media Storage SOP Instance UID
264:(0002,0010) UI #20 [1.2.840.10008.1.2.1] Transfer Syntax UID
292:(0002,0012) UI #36 [1.2.156.112679.160601001.122.97.0.1] Implementation Class UID
336:(0002,0013) SH #14 [BM_DCM2.0_ZA01] Implementation Version Name
358:(0008,0005) CS #8 [GB18030] Specific Character Set
374:(0008,0008) CS #24 [ORIGINAL\PRIMARY\HELICAL] Image Type
406:(0008,0016) UI #26 [1.2.840.10008.5.1.4.1.1.2] SOP Class UID
440:(0008,0018) UI #64 [1.2.156.112679.160601001.14.101381830993013818310301381831040010] SOP Instance UID
512:(0008,0020) DA #8 [20200518] Study Date
528:(0008,0022) DA #8 [20200518] Acquisition Date
544:(0008,0023) DA #8 [20200518] Content Date
560:(0008,0030) TM #10 [161256.919] Study Time
578:(0008,0032) TM #10 [161256.000] Acquisition Time
596:(0008,0033) TM #10 [161256.919] Content Time
614:(0008,0050) SH #0 [] Accession Number
622:(0008,0060) CS #2 [CT] Modality
632:(0008,0070) LO #34 [Suzhou Bowing Medical Technologies] Manufacturer
674:(0008,0080) LO #0 [] Institution Name
682:(0008,0090) PN #0 [] Referring Physician’s Name
690:(0008,1010) SH #6 [zeedas] Station Name
704:(0008,1030) LO #96 [Head] Study Description
808:(0008,103E) LO #26 [HeadH Routine_5.0_1.0_H21] Series Description
842:(0008,1090) LO #12 [Zeedas CT 16] Manufacturer’s Model Name
862:(0008,1110) SQ #-1 Referenced Study Sequence
874:>(FFFE,E000) #0 Item
882:>(FFFE,E0DD) #0 Sequence Delimitation Item
890:(0008,1111) SQ #-1 Referenced Performed Procedure Step Sequence
902:>(FFFE,E000) #-1 Item
910:>(0008,1150) UI #24 [1.2.840.10008.3.1.2.3.3] Referenced SOP Class UID
942:>(0008,1155) UI #64 [1.2.156.112679.160601001.19.100138183099300138183099] Referenced SOP Instance UID
1014:>(FFFE,E00D) #0 Item Delimitation Item
1022:>(FFFE,E0DD) #0 Sequence Delimitation Item
1030:(0008,1120) SQ #-1 Referenced Patient Sequence
1042:>(FFFE,E000) #0 Item
1050:>(FFFE,E0DD) #0 Sequence Delimitation Item
1058:(0008,1140) SQ #-1 Referenced Image Sequence
1070:>(FFFE,E000) #-1 Item
1078:>(0008,1150) UI #26 [1.2.840.10008.5.1.4.1.1.2] Referenced SOP Class UID
1112:>(0008,1155) UI #64 [1.2.156.112679.160601001.13.10138183099301381831030138183104] Referenced SOP Instance UID
1184:>(FFFE,E00D) #0 Item Delimitation Item
1192:>(FFFE,E0DD) #0 Sequence Delimitation Item
1200:(0010,0010) PN #8 [紧急患者] Patient’s Name
1216:(0010,0020) LO #12 [200518161139] Patient ID
1236:(0010,0021) LO #14 [Bowing Medical] Issuer of Patient ID
1258:(0010,0030) DA #8 [20200518] Patient’s Birth Date
1274:(0010,0040) CS #2 [O] Patient’s Sex
1284:(0010,1010) AS #4 [000D] Patient’s Age
1296:(0010,1030) DS #4 [0.0] Patient’s Weight
1308:(0018,0010) LO #0 [] Contrast/Bolus Agent
1316:(0018,0050) DS #4 [5.0] Slice Thickness
1328:(0018,0060) DS #6 [120.0] KVP
1342:(0018,0088) DS #4 [5.0] Spacing Between Slices
1354:(0018,1020) LO #4 [V1.0] Software Version(s)
1366:(0018,1120) DS #4 [0.0] Gantry/Detector Tilt
1378:(0018,1151) IS #4 [300] X-Ray Tube Current
1390:(0018,1152) IS #4 [225] Exposure
1402:(0018,1210) SH #32 [H21] Convolution Kernel
1442:(0018,5100) CS #4 [HFS] Patient Position
1454:(0018,9305) FD #8 [0.75] Revolution Time
1470:(0018,9311) FD #8 [1.0] Spiral Pitch Factor
1486:(0018,9317) FD #8 [200.0] Reconstruction Field of View
1502:(0018,9318) FD #24 [0.0\0.0\0.0] Reconstruction Target Center (Patient)
1534:(0018,9327) FD #8 [-25.0] Table Position
1550:(0020,000D) UI #52 [1.2.156.112679.160601001.12.100138183099300138183099] Study Instance UID
1610:(0020,000E) UI #64 [1.2.156.112679.160601001.13.10138183099301381831030138183104] Series Instance UID
1682:(0020,0010) SH #12 [300138183103] Study ID
1702:(0020,0011) IS #10 [138183104] Series Number
1720:(0020,0012) IS #2 [0] Acquisition Number
1730:(0020,0013) IS #2 [10] Instance Number
1740:(0020,0020) CS #4 [L\P] Patient Orientation
1752:(0020,0032) DS #20 [-100.0\-100.0\-195.0] Image Position (Patient)
1780:(0020,0037) DS #24 [1.0\0.0\0.0\0.0\1.0\0.0] Image Orientation (Patient)
1812:(0020,0052) UI #64 [1.2.156.112679.160601001.15.100138183099300138183103.3001] Frame of Reference UID
1884:(0020,1040) LO #0 [] Position Reference Indicator
1892:(0020,1041) DS #6 [195.0] Slice Location
1906:(0020,4000) LT #0 [] Image Comments
1914:(0028,0002) US #2 [1] Samples per Pixel
1924:(0028,0004) CS #12 [MONOCHROME2] Photometric Interpretation
1944:(0028,0010) US #2 [512] Rows
1954:(0028,0011) US #2 [512] Columns
1964:(0028,0030) DS #18 [0.390625\0.390625] Pixel Spacing
1990:(0028,0100) US #2 [16] Bits Allocated
2000:(0028,0101) US #2 [12] Bits Stored
2010:(0028,0102) US #2 [11] High Bit
2020:(0028,0103) US #2 [0] Pixel Representation
2030:(0028,1050) DS #4 [35.0] Window Center
2042:(0028,1051) DS #4 [80.0] Window Width
2054:(0028,1052) DS #6 [-1024] Rescale Intercept
2068:(0028,1053) DS #2 [1] Rescale Slope
2078:(0029,0001) IS #2 [1] Private Creator Data Element
2088:(0029,0002) IS #2 [90] Private Creator Data Element
2098:(0029,0003) IS #2 [0] Private Creator Data Element
2108:(0029,0004) IS #2 [2] Private Creator Data Element
2118:(0029,0005) IS #2 [1] Private Creator Data Element
2128:(0029,0007) IS #2 [2] Private Creator Data Element
2138:(0029,0008) LO #12 [400138183104] Private Creator Data Element
2158:(0029,0009) LO #12 [300138183099] Private Creator Data Element
2178:(0029,0010) LO #12 [100138183099] Private Creator Data Element
2198:(0029,0011) LO #4 [不传] Private Creator Data Element
2210:(0029,0012) IS #2 [0] Private Creator Data Element
2220:(0029,0015) IS #2 [2] Private Creator Data Element
2230:(0029,0016) IS #2 [0] Private Creator Data Element
2240:(0029,0017) IS #2 [0] Private Creator Data Element
2250:(0029,0021) LO #12 [300138183103] Private Creator Data Element
2270:(0029,0022) LO #4 [3002] Private Creator Data Element
2282:(0029,0023) LO #4 [4001] Private Creator Data Element
2294:(4010,1048) CS #4 [HEL] Scan Type
2306:(7FE0,0010) OW #524288 [24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\24\...] Pixel Data

d:\toPACS>