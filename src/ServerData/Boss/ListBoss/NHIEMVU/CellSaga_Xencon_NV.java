/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerData.Boss.ListBoss.NHIEMVU;
import Server.Data.Consts.ConstPlayer;
import ServerData.Boss.*;
import ServerData.Models.Map.ItemMap;
import ServerData.Models.Map.Zone;
import ServerData.Models.Player.Player;
import ServerData.Models.Player.PlayerSkill.Skill;
import ServerData.Server.Manager;
import ServerData.Services.EffectSkillService;
import ServerData.Services.Service;
import ServerData.Services.TaskService;
import ServerData.Utils.Util;
import ServerData.Services.PlayerService;
import java.util.Random;

public class CellSaga_Xencon_NV extends Boss {
    private long lastTimeHapThu;
    private int timeHapThu;
    public CellSaga_Xencon_NV() throws Exception {
        super(BossID.XEN_CON_1, BossesData.XEN_CON);
    }

    public CellSaga_Xencon_NV(Zone zone, long dame, long hp) throws Exception {
        super(Util.randomBossId(), new BossData(
                "Super Broly", //name
                ConstPlayer.TRAI_DAT, //gender
                new short[]{264, 265, 266, 28, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
                ((5000 + dame)), //dame
                new long[]{((hp))}, //hp
                new int[]{zone.map.mapId}, //map join
                new int[][]{
                    {Skill.LIEN_HOAN, 7, 5000},
                    {Skill.DRAGON, 7, 500},
                    {Skill.KAMEJOKO, 7, 590},
                    {Skill.MASENKO, 7, 200},
                    {Skill.ANTOMIC, 7, 220},
                    {Skill.TAI_TAO_NANG_LUONG, 1, 15000},},
                    new String[]{"|-1|Hello cục cưng",
                    "|-1|Mày có biết tao là ai không?",
                    "|-2|Tao không cần biết mày là ai, mày nghĩ mày dọa được tao à?",
                    "|-1|Thôi không nói nhiều nữa,giờ tao cho mày biết tao là ai."
                }, //text chat 1
                new String[]{"|-1|Tao hơn hẳn mày, mày nên cầu cho may mắn ở phía mày đi",
                    "|-1|Ghê chưa ghê chưa!",
                    "|-1|Tao có rất nhiều vật phẩm quý giá,nhưng với mày thì có cái..nịt",
                    "|-1|Đánh tao à,lo mà luyện tập thêm đi",
                    "|-1|Nói cho mày biết,tao là con của Xên ",
                    "|-1|Tao sẽ thiêu rụi mày"
                }, //text chat 2
                new String[]{"|-1|Gaaaaaaaa!!!"}, //text chat 3
                1_000_000
                
        ));
        this.zone = zone;
    }
   @Override
    public void reward(Player plKill) {
           int[] itemDos = new int[]{457};
        int[] NRs = new int[]{17,18};
        int randomDo = new Random().nextInt(itemDos.length);
        int randomNR = new Random().nextInt(NRs.length);
        if (Util.isTrue(15, 100)) {
            if (Util.isTrue(1, 7)) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 561, 1, this.location.x, this.location.y, plKill.id));
                return;
            }
            Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, itemDos[randomDo], 5, this.location.x, this.location.y, plKill.id));
        } else {
            Service.gI().dropItemMap(this.zone, new ItemMap(zone, NRs[randomNR], 1, this.location.x, zone.map.yPhysicInTop(this.location.x, this.location.y - 24), plKill.id));
        }
        TaskService.gI().checkDoneTaskKillBoss(plKill, this);
    }
    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;
     @Override
    public void active() {
        if (this.typePk == ConstPlayer.NON_PK) {
            this.changeToTypePK();
        }
        this.hapThu();
        this.attack();
         super.active(); //To change body of generated methods, choose Tools | Templates.
    }

    private void hapThu() {
        if (!Util.canDoWithTime(this.lastTimeHapThu, this.timeHapThu) || !Util.isTrue(1, 100)) {
            return;
        }

        Player pl = this.zone.getRandomPlayerInMap();
        if (pl == null || pl.isDie()) {
            return;
        }
//        ChangeMapService.gI().changeMapYardrat(this, this.zone, pl.location.x, pl.location.y);
        this.nPoint.dameg += (pl.nPoint.dame * 5 / 100);
        this.nPoint.hpg += (pl.nPoint.hp * 2 / 100);
        this.nPoint.critg++;
        this.nPoint.calPoint();
        PlayerService.gI().hoiPhuc(this, pl.nPoint.hp, 0);
        pl.injured(null, pl.nPoint.hpMax, true, false);
        Service.gI().sendThongBao(pl, "Bạn vừa bị " + this.name + " hấp thu!");
        this.chat(2, "Ui cha cha, kinh dị quá. " + pl.name + " vừa bị tên " + this.name + " nuốt chửng kìa!!!");
        this.chat("Haha, ngọt lắm đấy " + pl.name + "..");
        this.lastTimeHapThu = System.currentTimeMillis();
        this.timeHapThu = Util.nextInt(70000, 150000);
    }
    
}
