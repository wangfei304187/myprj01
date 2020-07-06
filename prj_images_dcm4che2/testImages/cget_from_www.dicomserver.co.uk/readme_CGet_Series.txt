[wf@mylinuxf20 toPAC] dcm4che2/bin/dcmqr -L QRSCU:11113 QRSCP@www.dicomserver.co.uk -cget -S -qStudyInstanceUID=1.2.826.0.1.3680043.8.852.123456789.26 -qSeriesInstanceUID=1.2.826.0.1.3680043.8.852.123456789.26.1  -cstore MR -cstoredest /tmp/dcm01
Start Server listening on port 11113
15:31:16,707 INFO   - Start listening on 0.0.0.0/0.0.0.0:11113
15:31:17,163 INFO   - Association(1) initiated Socket[addr=www.dicomserver.co.uk/51.75.171.41,port=104,localport=48996]
15:31:17,163 INFO   - QRSCP(1): A-ASSOCIATE-RQ QRSCP << QRSCU
15:31:17,500 INFO   - QRSCP(1): A-ASSOCIATE-AC QRSCU >> QRSCP
15:31:17,503 INFO   - Connected to QRSCP@www.dicomserver.co.uk in 0.795 s
15:31:17,524 INFO   - Send Query Request using 1.2.840.10008.5.1.4.1.2.2.1/Study Root Query/Retrieve Information Model - FIND:
(0008,0052) CS #6 [SERIES] Query/Retrieve Level
(0008,0060) CS #0 [] Modality
(0020,000D) UI #38 [1.2.826.0.1.3680043.8.852.123456789.26] Study Instance UID
(0020,000E) UI #40 [1.2.826.0.1.3680043.8.852.123456789.26.1] Series Instance UID
(0020,0011) IS #0 [] Series Number
(0020,1209) IS #0 [] Number of Series Related Instances

15:31:17,527 INFO   - QRSCP(1) << 1:C-FIND-RQ[pcid=1, prior=0
        cuid=1.2.840.10008.5.1.4.1.2.2.1/Study Root Query/Retrieve Information Model - FIND
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:31:17,919 INFO   - QRSCP(1) >> 1:C-FIND-RSP[pcid=1, status=ff00H
        cuid=1.2.840.10008.5.1.4.1.2.2.1/Study Root Query/Retrieve Information Model - FIND
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:31:18,285 INFO   - QRSCP(1) >> 1:C-FIND-RSP[pcid=1, status=0H
        cuid=1.2.840.10008.5.1.4.1.2.2.1/Study Root Query/Retrieve Information Model - FIND]
15:31:18,286 INFO   - Query Response #1:
(0008,0005) CS #10 [ISO_IR 192] Specific Character Set
(0008,0052) CS #6 [SERIES] Query/Retrieve Level
(0008,0054) AE #6 [QRSCP] Retrieve AE Title
(0008,0060) CS #2 [MR] Modality
(0020,000D) UI #38 [1.2.826.0.1.3680043.8.852.123456789.26] Study Instance UID
(0020,000E) UI #40 [1.2.826.0.1.3680043.8.852.123456789.26.1] Series Instance UID
(0020,0011) IS #2 [1] Series Number
(0020,1209) IS #2 [2] Number of Series Related Instances

15:31:18,286 INFO   - Received 1 matching entries in 0.785 s
15:31:18,289 INFO   - Send Retrieve Request using 1.2.840.10008.5.1.4.1.2.2.3/Study Root Query/Retrieve Information Model - GET:
(0008,0052) CS #6 [SERIES] Query/Retrieve Level
(0020,000D) UI #38 [1.2.826.0.1.3680043.8.852.123456789.26] Study Instance UID
(0020,000E) UI #40 [1.2.826.0.1.3680043.8.852.123456789.26.1] Series Instance UID

15:31:18,289 INFO   - QRSCP(1) << 2:C-GET-RQ[pcid=5, prior=0
        cuid=1.2.840.10008.5.1.4.1.2.2.3/Study Root Query/Retrieve Information Model - GET
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:31:18,634 INFO   - QRSCP(1) >> 24125:C-STORE-RQ[pcid=9, prior=0
        cuid=1.2.840.10008.5.1.4.1.1.4/MR Image Storage
        iuid=1.2.826.0.1.3680043.8.852.123456789.26.1.2
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:31:22,062 INFO   - QRSCP(1) << 24125:C-STORE-RSP[pcid=9, status=0H]
15:31:22,397 INFO   - QRSCP(1) >> 2:C-GET-RSP[pcid=5, remaining=1, completed=1, failed=0, warning=0, status=ff00H
        cuid=1.2.840.10008.5.1.4.1.2.2.3/Study Root Query/Retrieve Information Model - GET]
15:31:22,401 INFO   - QRSCP(1) >> 24126:C-STORE-RQ[pcid=9, prior=0
        cuid=1.2.840.10008.5.1.4.1.1.4/MR Image Storage
        iuid=1.2.826.0.1.3680043.8.852.123456789.26.1.3
        ts=1.2.840.10008.1.2/Implicit VR Little Endian]
15:31:30,463 INFO   - QRSCP(1) << 24126:C-STORE-RSP[pcid=9, status=0H]
15:31:30,795 INFO   - QRSCP(1) >> 2:C-GET-RSP[pcid=5, remaining=0, completed=2, failed=0, warning=0, status=ff00H
        cuid=1.2.840.10008.5.1.4.1.2.2.3/Study Root Query/Retrieve Information Model - GET]
15:31:31,134 INFO   - QRSCP(1) >> 2:C-GET-RSP[pcid=5, remaining=0, completed=2, failed=0, warning=0, status=0H
        cuid=1.2.840.10008.5.1.4.1.2.2.3/Study Root Query/Retrieve Information Model - GET]
15:31:31,135 INFO   - Retrieved 2 objects (warning: 0, failed: 0) in 12.849s
15:31:31,135 INFO   - QRSCP(1) << A-RELEASE-RQ
15:31:31,469 INFO   - QRSCP(1) >> A-RELEASE-RP
15:31:31,469 INFO   - Released connection to QRSCP@www.dicomserver.co.uk
15:31:31,469 INFO   - QRSCP(1): close Socket[addr=www.dicomserver.co.uk/51.75.171.41,port=104,localport=48996]
15:31:31,469 INFO   - Stop listening on 0.0.0.0/0.0.0.0:11113
[wf@mylinuxf20 toPACS]$ 
