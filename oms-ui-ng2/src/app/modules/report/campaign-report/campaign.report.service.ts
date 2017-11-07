import { Injectable } from '@angular/core';
import * as _ from 'lodash';
import { LocalStorageService } from "../../../common/services/local-storage.service";

const COLOR_MAP: Object = {
    "Approved": "#00AF48",
    "Rejected": "#FF4133",
    "In progress": "#FF9801",
    "Draft": "#ff8080",
    "Sold": "#660000",
    "Pending": "#708090",
    "Admin Review": "6699FF",
    "Legal Review": "#669900",
    "Pending approval": "#00ffcc",
    "Pricing Review": "#9e619e",
    "Pricing approved": "#ff8000",
    "Pricing rejected": "#ffcc99",
    "Submitted": "#00ff00"
}

@Injectable()
export class CampaignReportService {
    permissions = null;
    constructor(private localStorage: LocalStorageService) {
    }

    getReportList(data){
      let arrOfObj = this.convertToArrayOfObjects(data);

      return arrOfObj;
    }

    convertToArrayOfObjects(data) {
        var keys = ["campaign", "campaign_name", "impression", "unique_impressions", "ctr", "click_through", "revenue"];
        var output = [];

        for (let i = 0; i < data.length; i++) {
           let obj = {};

            for (let k = 0; k < keys.length; k++) {
                obj[keys[k]] = data[i][k];
            }

            output.push(obj);
        }

        return output;
    }

    mockData = {
    "metaData": {
        "reportDefinition": {
            "startDateTime": "2016-10-01T00:00:00+02:00",
            "endDateTime": "2016-12-31T23:59:59+02:00",
            "timeGranularity": "none",
            "dimensions": [
                "campaign"
            ],
            "metrics": [
                "impression",
                "unique_impressions",
                "ctr",
                "click_through",
                "revenue"
            ],
            "filters": []
        },
        "name": "ritesh campaign impressions report",
        "rowCount": 23
    },
    "headers": [
        {
            "name": "campaign",
            "type": "STRING"
        },
        {
            "name": "campaign_name",
            "type": "STRING"
        },
        {
            "name": "impression",
            "type": "INTEGER"
        },
        {
            "name": "unique_impressions",
            "type": "INTEGER"
        },
        {
            "name": "ctr",
            "type": "DECIMAL"
        },
        {
            "name": "click_through",
            "type": "INTEGER"
        },
        {
            "name": "revenue",
            "type": "DECIMAL"
        }
    ],
    "rows": [
        [
            "f42599c1-f704-4d9e-8097-11b60a560f0b",
            "Campagna Test Nuovo Player_Preroll Test",
            30,
            11,
            0.1,
            3,
            0
        ],
        [
            "c2c30ecc-f59d-491b-8667-ed3ba16663d9",
            "Test_Midroll cue point_Rashmi",
            1,
            1,
            2,
            2,
            0
        ],
        [
            "8a71a772-7c28-4594-ab7c-93561fa24bb0",
            "Test_Category_targeting_Rashmi",
            5,
            1,
            1.2,
            6,
            0
        ],
        [
            "a43096a1-d81b-4064-90d4-4d59fd6c41b1",
            "android:midroll",
            12192,
            1915,
            0.03510498687664042,
            428,
            0
        ],
        [
            "ed62718a-0892-4b9a-8487-05eaa6c3205c",
            "android:postroll",
            1548,
            387,
            0.07493540051679587,
            116,
            0
        ],
        [
            "98a19d4a-ca73-4da4-a569-0bbf49ae3ea6",
            "MidrollDabang_Donotdelete",
            8,
            3,
            0,
            0,
            0
        ],
        [
            "10746bb6-8931-4308-8fa8-e435fa4a80e6",
            "Sports_Shruti_CharlesTest01_7thDec16",
            4,
            1,
            0,
            0,
            0
        ],
        [
            "de5c7863-4532-4146-9ecf-ac70b1e9c36e",
            "P&G_Trackertesting_09Nov2016",
            7,
            2,
            0,
            0,
            0
        ],
        [
            "113eb46f-d806-414f-985b-0233ac532e20",
            "Container Test",
            4,
            1,
            0,
            0,
            0
        ],
        [
            "7a8d1317-3390-4b0a-8567-6152c5524c80",
            "Disneytest_01Dec2016",
            6,
            1,
            1,
            6,
            0
        ],
        [
            "0c614625-695b-4bfc-b25f-6c0301215c46",
            "Test_iphone_HTML5_clicktracking_12dec16",
            10,
            5,
            0,
            0,
            0
        ],
        [
            "855557ab-cf1e-4280-b0fd-42c4ffd39c00",
            "Container Test - ADOPS",
            5,
            3,
            0,
            0,
            0
        ],
        [
            "bffba8d0-4e55-4da1-937e-8e65eb037bac",
            "Test_Creative Scheduling_Rashmi",
            3,
            1,
            0.6666666666666666,
            2,
            0
        ],
        [
            "0a6dfdad-9839-412a-a1a3-ca135f2dc767",
            "Ent_CharlesTest__07Dec16",
            16,
            5,
            0.6875,
            11,
            0
        ],
        [
            "7327d1b9-d277-4627-aeb0-9655922dc92d",
            "Clicktosms_03Jan2017",
            46,
            22,
            0.1956521739130435,
            9,
            0
        ],
        [
            "389f0b21-0a60-420a-8fb9-e194d864fe82",
            "Renault Test Home Featured",
            84,
            26,
            0.14285714285714285,
            12,
            0
        ],
        [
            "975ed7e7-41f7-4b2b-bb94-2eda43cfa353",
            "VPAID Test",
            35,
            20,
            0,
            0,
            0
        ],
        [
            "da94074f-6d6b-4ed1-8540-42ba6063f05c",
            "Test_FrontLoad_50%_Rashmi",
            44,
            4,
            0.4772727272727273,
            21,
            0
        ],
        [
            "ec0e188f-17aa-4cfc-a172-c9e1e39a41f4",
            "Test_FrontLoad_FL100%_Rashmi",
            1,
            1,
            0,
            0,
            0
        ],
        [
            "469df8f8-9fca-467d-ba6c-dcaabf964e9d",
            "Test_AD weight_Shrutika",
            21,
            2,
            0.047619047619047616,
            1,
            0
        ],
        [
            "c0745147-e945-40f5-af30-d98adad07fa4",
            "Sports_CharlesTest_Deepak_Dec16",
            3,
            2,
            0.6666666666666666,
            2,
            0
        ],
        [
            "30c5ee52-461a-4769-b777-cb1587855bd9",
            "android:pre-roll",
            14566,
            4870,
            0.09398599478236991,
            1369,
            0
        ],
        [
            "141cb5a2-f412-4efc-981a-715d472dfb57",
            "VMAP test",
            36,
            6,
            0,
            0,
            0
        ]
    ]
}

    
}