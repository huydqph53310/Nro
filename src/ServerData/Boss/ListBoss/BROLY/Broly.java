package ServerData.Boss.ListBoss.BROLY;

import Server.Data.Consts.ConstPlayer;
import ServerData.Boss.Boss;
import ServerData.Boss.BossData;
import ServerData.Boss.BossManager;
import ServerData.Models.Map.Zone;
import ServerData.Models.Player.Player;
import ServerData.Models.Player.PlayerSkill.Skill;
import ServerData.Services.PlayerService;
import ServerData.Services.Service;
import ServerData.Utils.SkillUtil;
import ServerData.Utils.Util;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Broly extends Boss {

    private int hi;
    private long lastTimeDamaged;
    private long lastTimeHP;
    private int timeHP;
    
     public Broly(Zone zone,int hp,int dame) throws Exception {
         super(Util.randomBossId(), new BossData(
            "Broly", //name
            ConstPlayer.XAYDA, //gender
             new short[]{291, 292, 293, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            dame, //dame
            new long[]{hp}, //hp
            new int[]{5}, //map join
            new int[][]{
                 {Skill.DRAGON, 7, 1000},
                    {Skill.TAI_TAO_NANG_LUONG, 7, 20000},
                    {Skill.ANTOMIC, 7, 500}}, //skill
            new String[]{
                    "|-1|Tuy không biết các ngươi là ai, nhưng ta rất ấn tượng đấy!",
                    "|-2|Tới đây đi!"
            }, //text chat 1
            new String[]{"|-1|Các ngươi tới số rồi mới gặp phải ta",
                    "|-1|Gaaaaaa",
                    "|-2|Không..thể..nào!!",
                    "|-2|Không ngờ..Hắn mạnh cỡ này sao..!!"
            }, //text chat 2
            new String[]{"|-1|Gaaaaaaaa!!!"}, //text chat 3
            600 //second rest
        ));
         this.zone = zone;
     }
    public Broly(Zone zone, long hp, long dame,int...id) throws Exception {
        super(Util.randomBossId(), new BossData(
            "Broly", //name
            ConstPlayer.XAYDA, //gender
             new short[]{291, 292, 293, -1, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
            dame, //dame
            new long[]{hp}, //hp
            new int[]{5}, //map join
            new int[][]{
                 {Skill.DRAGON, 7, 1000},
                    {Skill.TAI_TAO_NANG_LUONG, 7, 20000},
                    {Skill.ANTOMIC, 7, 500}}, //skill
            new String[]{
                    "|-1|Tuy không biết các ngươi là ai, nhưng ta rất ấn tượng đấy!",
                    "|-2|Tới đây đi!"
            }, //text chat 1
            new String[]{"|-1|Các ngươi tới số rồi mới gặp phải ta",
                    "|-1|Gaaaaaa",
                    "|-2|Không..thể..nào!!",
                    "|-2|Không ngờ..Hắn mạnh cỡ này sao..!!"
            }, //text chat 2
            new String[]{"|-1|Gaaaaaaaa!!!"}, //text chat 3
            600 //second rest
        ));
        this.zone = zone;
  
    }

    @Override
    public void active() {
        super.active();
        try {
            this.hoiPhuc();
        }catch (Exception ex) { 
            Logger.getLogger(SuperBroly.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void hoiPhuc() throws Exception {
        if (!Util.canDoWithTime(lastTimeHP, timeHP) || !Util.isTrue(1, 100)) {
            return;
        }
        Player pl = this.zone.getRandomPlayerInMap();
        if (pl == null || pl.isDie()) {
            return;
        }
        if(this.nPoint.hpg >= 16007077){
            this.nPoint.hpg = 16007077;
        }
        else{
            this.nPoint.dameg += this.nPoint.dame * 120 / 100;
            this.nPoint.hpg += this.nPoint.hpg * 200 / 100;
            this.nPoint.critg++;
            this.nPoint.calPoint();
            PlayerService.gI().hoiPhuc(this, this.nPoint.hp, this.nPoint.mp);
            Service.gI().sendThongBao(pl, "Tên broly hắn lại tăng sức mạnh rồi!");
            this.chat(2, "Mọi người cẩn thận sức mạnh hắn ta tăng đột biến..");
            this.chat("Graaaaaa...");
            lastTimeHP = System.currentTimeMillis();
            timeHP = Util.nextInt(5000, 10000);
        }
    }
    public void heal(long amount) {
        nPoint.hp =  Math.min(nPoint.hp + amount, nPoint.hpMax);
    }

    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1000)) {
            this.chat("Xí hụt");
            return 0;
        }
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTimeDamaged >= 10000) {
            long healAmount = nPoint.hpMax * 100; // hồi phục 20% HP gốc khi tấn công trong 1 phút
            this.heal(healAmount);
            byte skillId = (byte) Skill.TAI_TAO_NANG_LUONG;
            if (skillId != 0) {
                playerSkill.skills.add(SkillUtil.createSkill(skillId, 7));
                this.chat("Cảm giác rất tốt khi được hồi phục lại năng lượng :)");
            }
            lastTimeDamaged = currentTime;
        }
        if (plAtt != null) {
            switch (plAtt.playerSkill.skillSelect.template.id) {
                case Skill.TU_SAT:
                case Skill.QUA_CAU_KENH_KHI:
                case Skill.MAKANKOSAPPO:
                damage = Math.min(damage, nPoint.hpMax *  100);
                    break;
                    default:
                 damage = Math.min(damage, nPoint.hpMax * 5 / 100);
            break;
            }
        }
        this.nPoint.subHP(damage);
        if (isDie()) {
            Zone zonetemp = this.zone;
            long hptemp = this.nPoint.hpMax;
            long dametemp = this.nPoint.dame;
            this.setDie(plAtt);
            die(plAtt);
            try{     
                if(this.nPoint.hpg >= 100000){
                  long  hpmax = hptemp + Util.nextInt(1000707, 16007077);
                    new SuperBroly(zonetemp, dametemp*2, hptemp >= 16007077 ? 16007077 : hpmax);
                }else{
                    new Broly(zonetemp, hptemp >= 16007077 ? 16007077 : hptemp*2, dametemp*2);
                }
            } catch(Exception e){
            }
        } 
        return damage;
    }
    @Override
    public void leaveMap() {
        super.leaveMap();
        BossManager.gI().removeBoss(this);
        this.dispose();
    }
    @Override
    public void joinMap() {
        super.joinMap();
    }      
}
