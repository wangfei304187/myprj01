Study:  1.2.826.0.1.3680043.8.852.123456789.26       (26 images)
   - Series1:  1.2.826.0.1.3680043.8.852.123456789.26.1      (2 images)
   - Series2:  1.2.826.0.1.3680043.8.852.123456789.26.1      (24 images)
   

============================================================================
============================================================================


dcm4che2/bin/dcmqr -L QRSCU:11113 QRSCP@www.dicomserver.co.uk -cget -qStudyInstanceUID=1.2.826.0.1.3680043.8.852.123456789.26 -cstore MR -cstoredest /tmp/dcm01

*******************************
Start Server listening on port 11113
Association(1) initiated Socket[addr=www.dicomserver.co.uk/51.75.171.41,port=104,localport=41625]
QRSCP(1): A-ASSOCIATE-RQ QRSCP << QRSCU
QRSCP(1): A-ASSOCIATE-AC QRSCU >> QRSCP
Connected to QRSCP@www.dicomserver.co.uk in 1.444 s
Send Query Request using ...

QRSCP(1) << 1:C-FIND-RQ
QRSCP(1) >> 1:C-FIND-RSP [status=ff00H, ...]
QRSCP(1) >> 1:C-FIND-RSP [status=0H]
14:43:07,827 INFO   - Query Response #1:
...

14:43:07,827 INFO   - Received 1 matching entries in 1.506 s
14:43:07,828 INFO   - Send Retrieve Request using ...

QRSCP(1) << 2:C-GET-RQ

QRSCP(1) >> 24109:C-STORE-RQ
QRSCP(1) << 24109:C-STORE-RSP [status=0H]
QRSCP(1) >> 2:C-GET-RSP [status=ff00H, ...]

QRSCP(1) >> 24109:C-STORE-RQ
QRSCP(1) << 24109:C-STORE-RSP [status=0H]
QRSCP(1) >> 2:C-GET-RSP [status=ff00H, ...]

...

QRSCP(1) >> 24110:C-STORE-RQ
QRSCP(1) << 24110:C-STORE-RSP [status=0H]
QRSCP(1) >> 2:C-GET-RSP [status=ff00H, ...]

QRSCP(1) >> 2:C-GET-RSP [status=0H]
Retrieved 26 objects (warning: 0, failed: 0) in 859.513s
QRSCP(1) << A-RELEASE-RQ
QRSCP(1) >> A-RELEASE-RP
*******************************


============================================================================
============================================================================


[wf@mylinuxf20 toPACS]$ dcm4che2/bin/dcmqr -L QRSCU:11113 QRSCP@www.dicomserver.co.uk -cget -qStudyInstanceUID=1.2.826.0.1.3680043.8.852.123456789.26 -cstore MR -cstoredest /tmp/dcm01                                                              
Start Server listening on port 11113
15:04:11,823 INFO   - Start listening on 0.0.0.0/0.0.0.0:11113
15:04:12,180 INFO   - Association(1) initiated Socket[addr=www.dicomserver.co.uk/51.75.171.41,port=104,localport=55560]
15:04:12,180 INFO   - QRSCP(1): A-ASSOCIATE-RQ QRSCP << QRSCU
15:04:12,518 INFO   - QRSCP(1): A-ASSOCIATE-AC QRSCU >> QRSCP
15:04:12,521 INFO   - Connected to QRSCP@www.dicomserver.co.uk in 0.696 s
15:04:12,543 INFO   - Send Query Request using 1.2.840.10008.5.1.4.1.2.2.1/Study Root Query/Retrieve Information Model - FIND:
(0008,0020) DA #0 [] Study Date
(0008,0030) TM #0 [] Study Time
(0008,0050) SH #0 [] Accession Number
(0008,0052) CS #6 [STUDY] Query/Retrieve Level
(0020,000D) UI #38 [1.2.826.0.1.3680043.8.852.123456789.26] Study Instance UID
(0020,0010) SH #0 [] Study ID
(0020,1206) IS #0 [] Number of Study Related Series
(0020,1208) IS #0 [] Number of Study Related Instances

15:04:12,546 INFO   - QRSCP(1) << 1:C-FIND-RQ[pcid=1, prior=0
        cuid=1.2.840.10008.5.1.4.1.2.2.1/Study Root Query/Retrieve Information Model - FIND
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:04:12,897 INFO   - QRSCP(1) >> 1:C-FIND-RSP[pcid=1, status=ff00H
        cuid=1.2.840.10008.5.1.4.1.2.2.1/Study Root Query/Retrieve Information Model - FIND
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:04:13,268 INFO   - QRSCP(1) >> 1:C-FIND-RSP[pcid=1, status=0H
        cuid=1.2.840.10008.5.1.4.1.2.2.1/Study Root Query/Retrieve Information Model - FIND]
15:04:13,268 INFO   - Query Response #1:
(0008,0005) CS #10 [ISO_IR 192] Specific Character Set
(0008,0020) DA #8 [20200609] Study Date
(0008,0030) TM #14 [113800.000000] Study Time
(0008,0050) SH #0 [] Accession Number
(0008,0052) CS #6 [STUDY] Query/Retrieve Level
(0008,0054) AE #6 [QRSCP] Retrieve AE Title
(0020,000D) UI #38 [1.2.826.0.1.3680043.8.852.123456789.26] Study Instance UID
(0020,0010) SH #2 [26] Study ID
(0020,1206) IS #2 [2] Number of Study Related Series
(0020,1208) IS #2 [26] Number of Study Related Instances

15:04:13,268 INFO   - Received 1 matching entries in 0.75 s
15:04:13,269 INFO   - Send Retrieve Request using 1.2.840.10008.5.1.4.1.2.2.3/Study Root Query/Retrieve Information Model - GET:
(0008,0052) CS #6 [STUDY] Query/Retrieve Level
(0020,000D) UI #38 [1.2.826.0.1.3680043.8.852.123456789.26] Study Instance UID

15:04:13,269 INFO   - QRSCP(1) << 2:C-GET-RQ[pcid=7, prior=0
        cuid=1.2.840.10008.5.1.4.1.2.2.3/Study Root Query/Retrieve Information Model - GET
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:04:13,617 INFO   - QRSCP(1) >> 24109:C-STORE-RQ[pcid=13, prior=0
        cuid=1.2.840.10008.5.1.4.1.1.4/MR Image Storage
        iuid=1.2.826.0.1.3680043.8.852.123456789.26.1.2
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:04:20,637 INFO   - QRSCP(1) << 24109:C-STORE-RSP[pcid=13, status=0H]
15:04:20,963 INFO   - QRSCP(1) >> 2:C-GET-RSP[pcid=7, remaining=25, completed=1, failed=0, warning=0, status=ff00H
        cuid=1.2.840.10008.5.1.4.1.2.2.3/Study Root Query/Retrieve Information Model - GET]
15:04:20,971 INFO   - QRSCP(1) >> 24110:C-STORE-RQ[pcid=13, prior=0
        cuid=1.2.840.10008.5.1.4.1.1.4/MR Image Storage
        iuid=1.2.826.0.1.3680043.8.852.123456789.26.1.3
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:04:30,813 INFO   - QRSCP(1) << 24110:C-STORE-RSP[pcid=13, status=0H]
15:04:31,139 INFO   - QRSCP(1) >> 2:C-GET-RSP[pcid=7, remaining=24, completed=2, failed=0, warning=0, status=ff00H
        cuid=1.2.840.10008.5.1.4.1.2.2.3/Study Root Query/Retrieve Information Model - GET]
15:04:31,146 INFO   - QRSCP(1) >> 24111:C-STORE-RQ[pcid=13, prior=0
        cuid=1.2.840.10008.5.1.4.1.1.4/MR Image Storage
        iuid=1.2.826.0.1.3680043.8.852.123456789.26.2.2
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:05:13,058 INFO   - QRSCP(1) << 24111:C-STORE-RSP[pcid=13, status=0H]
15:05:13,387 INFO   - QRSCP(1) >> 2:C-GET-RSP[pcid=7, remaining=23, completed=3, failed=0, warning=0, status=ff00H
        cuid=1.2.840.10008.5.1.4.1.2.2.3/Study Root Query/Retrieve Information Model - GET]
15:05:13,395 INFO   - QRSCP(1) >> 24112:C-STORE-RQ[pcid=13, prior=0
        cuid=1.2.840.10008.5.1.4.1.1.4/MR Image Storage
        iuid=1.2.826.0.1.3680043.8.852.123456789.26.2.3
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:05:43,137 INFO   - QRSCP(1) << 24112:C-STORE-RSP[pcid=13, status=0H]
15:05:43,462 INFO   - QRSCP(1) >> 2:C-GET-RSP[pcid=7, remaining=22, completed=4, failed=0, warning=0, status=ff00H
        cuid=1.2.840.10008.5.1.4.1.2.2.3/Study Root Query/Retrieve Information Model - GET]
15:05:43,468 INFO   - QRSCP(1) >> 24113:C-STORE-RQ[pcid=13, prior=0
        cuid=1.2.840.10008.5.1.4.1.1.4/MR Image Storage
        iuid=1.2.826.0.1.3680043.8.852.123456789.26.2.4
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:06:13,794 INFO   - QRSCP(1) << 24113:C-STORE-RSP[pcid=13, status=0H]
15:06:14,133 INFO   - QRSCP(1) >> 2:C-GET-RSP[pcid=7, remaining=21, completed=5, failed=0, warning=0, status=ff00H
        cuid=1.2.840.10008.5.1.4.1.2.2.3/Study Root Query/Retrieve Information Model - GET]
15:06:14,135 INFO   - QRSCP(1) >> 24114:C-STORE-RQ[pcid=13, prior=0
        cuid=1.2.840.10008.5.1.4.1.1.4/MR Image Storage
        iuid=1.2.826.0.1.3680043.8.852.123456789.26.2.5
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]

----------------------------------------------------------------------------


