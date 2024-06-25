package ServerData.Boss.ListBoss.BROLY;

import Server.Data.Consts.ConstPlayer;
import ServerData.Boss.*;
import ServerData.Models.Map.Zone;
import ServerData.Models.Player.Player;
import ServerData.Models.Player.PlayerSkill.Skill;
import ServerData.Services.EffectSkillService;
import ServerData.Services.PetService;
import ServerData.Services.Service;
import ServerData.Utils.SkillUtil;
import ServerData.Utils.Util;

public class SuperBroly extends Boss {

    private long lastUpdate = System.currentTimeMillis();
    private long timeJoinMap;
    protected Player playerAtt;
    private int timeLive = 200000000;
    public int petgender = Util.nextInt(0,2);
    public Player mypett;
    private long lastTimeDamaged;



    public SuperBroly(BossData bossData) throws Exception {
        super(Util.randomBossId(), bossData);
    }
    // public Superbroly(Zone zone,long hp,long dame) throws Exception {
    //     super(Util.randomBossId(), BossesData.SUPER_BROLY);
    //     this.zone = zone;
    //     this.nPoint.hpg = hp;
    //     this.nPoint.dame =dame;
    // }

    public SuperBroly(Zone zone, long dame, long hp) throws Exception {
        super(Util.randomBossId(), new BossData(
                "Super Broly", //name
                ConstPlayer.TRAI_DAT, //gender
                new short[]{294, 295, 296, 28, -1, -1}, //outfit {head, body, leg, bag, aura, eff}
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
                new String[]{
                    "|-1|Gaaaaaa",
                    "|-2|Tới đây đi!"
                }, //text chat 1
                new String[]{"|-1|Các ngươi tới số rồi mới gặp phải ta",
                    "|-1|Gaaaaaa",
                    "|-2|Không ngờ..Hắn mạnh cỡ này sao..!!"
                }, //text chat 2
                new String[]{"|-1|Gaaaaaaaa!!!"}, //text chat 3
                1_000_000
                
        ));
        this.zone = zone;
    }
    @Override
    public void reward(Player plKill) {
        if (plKill.pet == null) {
            PetService.gI().createNormalPet(plKill);
            Service.getInstance().sendThongBao(plKill,
                    "Con vừa nhận được đệ tử! Hãy chăm sóc nó nhé");
        } else {
            System.out.println(plKill.name + "Đã có đệ tử rồi mà!");

        }
        this.pet = null;
    }

    @Override
    public void active() {
        super.active();
        if (this.pet == null) {
            PetService.gI().createNormalPet(this,petgender);
            this.pet.nPoint.tlNeDon = 20000000;
        }
    }

    @Override
    public double injured(Player plAtt, double damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
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
                     damage = Math.min(damage, nPoint.hpMax * 1 / 100);
                break;
                }
            }
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.pet.dispose();
                this.pet = null;
                this.setDie(plAtt);
                die(plAtt);
               BossManager.gI().createBoss(BossID.BROLY_THUONG);
            }
            return damage;
        } else {
            return 0;
        }
    }
    @Override
    public void leaveMap() {
        super.leaveMap();
        BossManager.gI().removeBoss(this);
        this.dispose();
    }

    public void heal(long amount) {
        nPoint.hp =  Math.min(nPoint.hp + amount, nPoint.hpMax);
    }
    @Override
    public void joinMap() {
        super.joinMap();
    }
    
}
