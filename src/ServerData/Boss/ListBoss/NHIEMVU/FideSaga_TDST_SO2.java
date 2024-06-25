package ServerData.Boss.ListBoss.NHIEMVU;

import java.util.Random;

import ServerData.Boss.Boss;
import ServerData.Boss.BossID;
import ServerData.Boss.BossesData;
import ServerData.Models.Player.Player;
import ServerData.Services.Service;
import ServerData.Services.TaskService;
import ServerData.Utils.Util;

public class FideSaga_TDST_SO2 extends Boss {
    
     public FideSaga_TDST_SO2() throws Exception {
        super(BossID.SO_2, BossesData.SO_2);
    }
    public static boolean So2 = false;

    public static void GoiSo2(int a) {
        if (a == 1) {
            So2 = true;
        }
    }

        @Override
    public void reward(Player plKill) {
        FideSaga_TDST_SO1.GoiSo1(1);
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
        if (So2 != false) {
            super.active();
        } // To change body of generated methods, choose Tools | Templates.
    }
}
