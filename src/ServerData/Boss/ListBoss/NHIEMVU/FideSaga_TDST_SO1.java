package ServerData.Boss.ListBoss.NHIEMVU;

import java.util.Random;

import ServerData.Boss.Boss;
import ServerData.Boss.BossID;
import ServerData.Boss.BossesData;
import ServerData.Models.Player.Player;
import ServerData.Services.Service;
import ServerData.Services.TaskService;
import ServerData.Utils.Util;

public class FideSaga_TDST_SO1 extends Boss {
    
     public FideSaga_TDST_SO1() throws Exception {
        super(BossID.SO_1, BossesData.SO_1);
    }

    public static boolean So1 = false;

    public static void GoiSo1(int a) {
        if (a == 1) {
            So1 = true;
        }
    }

        @Override
    public void reward(Player plKill) {
        FideSaga_TDST_TDT.GoiTDT(1);
        int[] itemDos = new int[] { 16, 19 };
        int[] NRs = new int[] { 17, 18 };
        int randomDo = new Random().nextInt(itemDos.length);
        int randomNR = new Random().nextInt(NRs.length);
        if (Util.isTrue(15, 100)) {
            if (Util.isTrue(1, 5)) {
                Service.gI().dropItemMap(this.zone,
                        Util.ratiItem(zone, 561, 1, this.location.x, this.location.y, plKill.id));

            }
            Service.gI().dropItemMap(this.zone,
                    Util.ratiItem(zone, itemDos[randomDo], 1, this.location.x, this.location.y, plKill.id));
        } else {
            Service.gI().dropItemMap(this.zone,
                    Util.ratiItem(zone, 1504, Util.nextInt(1, 2), this.location.x, this.location.y, plKill.id));

        }
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }
    @Override
    public void active() {  
        if (So1 != false) {
            super.active();
        } // To change body of generated methods, choose Tools | Templates.
    }

    
}
