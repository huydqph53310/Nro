package ServerData.Boss.ListBoss.NHIEMVU;

import ServerData.Boss.Boss;
import ServerData.Boss.BossID;
import ServerData.Boss.BossesData;

public class FideSaga_TDST_TDT extends Boss {
    
     public FideSaga_TDST_TDT() throws Exception {
        super(BossID.TIEU_DOI_TRUONG, BossesData.TIEU_DOI_TRUONG);
    }

    public static boolean TDT = false;

    public static void GoiTDT(int a) {
        if (a == 1) {
            TDT = true;
        }
    }

    @Override
    public void active() {
        if (TDT != false) {
            super.active();
        } // To change body of generated methods, choose Tools | Templates.
    }
}
