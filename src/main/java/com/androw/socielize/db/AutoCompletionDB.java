package com.androw.socielize.db;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Androw on 12/08/2014.
 */
public class AutoCompletionDB {
    private List<String> iatas;

    private List<String> airports;

    public AutoCompletionDB() {
        String data = "1A,1G,1N,1S,2B,2C,2J,2L,2P,3H,3M,3N,3O,3Q,3R,3S,3U,3V,3X,3Y,4A,4D,4G,4H,4O,4P,4Q,4U,4Z,5C,5G,5L,5O,5P,5V,5W,5X,5Y,6A,6B,6G,6H,6M,6P,6T,6U,6V,6W,6Z,7B,7D,7F,7I,7J,7K,7L,7Q,8A,8I,8J,8M,8Q,8R,8U,9D,9H,9K,9R,9U,9W,9X,A0,A2,A3,A4,A5,A6,A8,A9,AA,AB,AC,AD,AE,AF,AG,AH,AI,AJ,AK,AL,AM,AN,AO,AP,AQ,AR,AS,AT,AU,AV,AW,AX,AY,AZ,B2,B3,B4,B5,B6,B7,B8,B9,BA,BB,BC,BD,BE,BF,BG,BH,BI,BJ,BK,BL,BM,BN,BO,BP,BQ,BR,BS,BT,BU,BV,BW,BX,BY,BZ,C0,C6,C9,CA,CB,CC,CD,CE,CF,CG,CH,CI,CJ,CL,CM,CN,CO,CP,CQ,CR,CRL,CS,CT,CU,CV,CW,CX,CY,CZ,D0,D3,D5,D6,D7,D8,D9,DA,DB,DC,DD,DE,DG,DH,DI,DJ,DK,DL,DM,DN,DO,DP,DQ,DR,DS,DT,DU,DV,DW,DX,DY,DZ,E3,E4,E5,E8,E9,EA,EC,EE,EG,EI,EK,EL,EN,EO,EP,ES,ET,EV,EW,EX,EY,EZ,F7,F9,FA,FB,FC,FD,FG,FI,FJ,FL,FM,FN,FP,FR,FS,FV,FW,FX,FY,FZ,G3,G4,G5,G7,GA,GB,GC,GE,GF,GH,GJ,GM,GN,GT,GU,GW,GX,GZ,H5,H8,HA,HC,HD,HF,HG,HH,HM,HP,HQ,HR,HT,HU,HV,HW,HY,HZ,I5,IB,IC,IE,IG,IH,II,IK,IN,IQ,IR,IT,IV,IY,IZ,J2,J7,JA,JH,JJ,JK,JL,JM,JO,JP,JQ,JR,JS,JT,JU,JZ,K6,K8,KA,KB,KC,KD,KE,KF,KK,KL,KM,KP,KQ,KU,KV,KW,KZ,L4,LA,LB,LD,LF,LG,LH,LI,LJ,LK,LM,LN,LO,LP,LR,LS,LT,LV,LX,LY,LZ,M9,MA,MB,MC,MD,ME,MF,MH,MI,MJ,MK,ML,MM,MP,MQ,MR,MS,MT,MU,N2,N3,N6,N7,NB,NE,NF,NG,NH,NI,NK,NO,NQ,NT,NU,NV,NW,NX,NZ,O6,OA,OB,OH,OK,OL,OM,ON,OO,OR,OS,OU,OV,OZ,P5,P7,P8,P9,PA,PC,PD,PE,PF,PG,PH,PJ,PK,PL,PR,PS,PU,PW,PX,PY,PZ,Q3,Q7,QA,QC,QF,QG,QI,QM,QO,QQ,QR,QS,QV,QY,QZ,R2,R4,RA,RB,RC,RD,RE,RG,RJ,RK,RM,RN,RO,RQ,RT,S2,S3,S4,S7,S9,SA,SB,SC,SD,SE,SF,SG,SH,SJ,SK,SN,SP,SQ,SR,SS,ST,SU,SV,SW,SY,SZ,T2,T3,T4,T5,T6,T7,TA,TB,TC,TE,TF,TG,TK,TL,TM,TN,TO,TP,TQ,TR,TS,TT,TU,TV,TX,TY,TZ,U2,U3,U4,U5,U6,U8,U9,UA,UB,UD,UF,UG,UI,UL,UM,UN,UQ,US,UT,UU,UW,UX,UY,V0,V5,V7,V9,VA,VB,VD,VE,VH,VI,VJ,VN,VO,VP,VR,VS,VT,VU,VV,VW,VX,VY,VZ,W5,W6,WA,WB,WF,WG,WI,WJ,WK,WM,WN,WP,WS,WU,WW,WX,WY,X2,X3,X5,X7,X9,XC,XF,XJ,XK,XL,XM,XO,XQ,XT,XW,Y9,YB,YD,YH,YK,YL,YM,YO,YQ,YS,YT,YV,YW,YX,Z2,Z4,Z6,Z9,ZA,ZB,ZC,ZE,ZG,ZH,ZI,ZL,ZN,ZS,ZU,ZY";
        this.iatas = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(data, ",");
        while (st.hasMoreTokens()) {
            this.iatas.add(st.nextToken().trim());
        }
    }

    public List<String> getIATAList(String query) {
        String iata = null;
        query = query.toLowerCase();
        List<String> matched = new ArrayList<String>();
        for (int i = 0; i < this.iatas.size(); i++) {
            iata = this.iatas.get(i).toLowerCase();
            if (iata.startsWith(query)) matched.add(this.iatas.get(i));
        }
        return matched;
    }
}
