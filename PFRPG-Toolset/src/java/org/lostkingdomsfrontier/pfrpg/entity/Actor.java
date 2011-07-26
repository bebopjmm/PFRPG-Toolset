/**
 * Project: PFRPG-Toolset
 * Created: Aug 13, 2006 by bebopJMM
 *------------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *   http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lostkingdomsfrontier.pfrpg.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.bind.annotation.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.lostkingdomsfrontier.pfrpg.AdjustableValue;
import org.lostkingdomsfrontier.pfrpg.Adjustment;
import org.lostkingdomsfrontier.pfrpg.AdjustmentChoiceSelection;
import org.lostkingdomsfrontier.pfrpg.AdjustmentChoices;
import org.lostkingdomsfrontier.pfrpg.ChoiceHelper;
import org.lostkingdomsfrontier.pfrpg.encounter.combat.Attacks;
import org.lostkingdomsfrontier.pfrpg.encounter.combat.Defense;
import org.lostkingdomsfrontier.pfrpg.entity.HitPoints.LifeState;
import org.lostkingdomsfrontier.pfrpg.entity.Movement.EncumberanceType;
import org.lostkingdomsfrontier.pfrpg.entity.classes.CharacterClass;
import org.lostkingdomsfrontier.pfrpg.entity.races.Race;
import org.lostkingdomsfrontier.pfrpg.entity.races.RacialLevel;
import org.lostkingdomsfrontier.pfrpg.entity.talents.ManagedSkill;
import org.lostkingdomsfrontier.pfrpg.entity.talents.Skill;
import org.lostkingdomsfrontier.pfrpg.entity.talents.SkillsCollection;
import org.lostkingdomsfrontier.pfrpg.entity.talents.Talent;
import org.lostkingdomsfrontier.pfrpg.items.Inventory;
import org.lostkingdomsfrontier.pfrpg.items.ItemInstance;

/**
 * An actor is an entity in the game capable of performing independent actions.
 * 
 * @author bebopjmm
 * @since sprint-0.1
 */
@SuppressWarnings("serial")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActorType", namespace = "java:org.rollinitiative.d20.entity", propOrder = {
        "baseScores", "description", "choiceSelections"})
// , "characterLevels", "inventory"
public abstract class Actor implements Comparable, Serializable
{
    static final Log LOG = LogFactory.getLog(Actor.class);

    public static final String DEFAULT_NAME = "Nameless Actor";

    public enum Modifiers {
        AbilityMod;
    }

    public enum Values {
        Ability, Save, Skill;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Long persistID;

    /**
     * Unique identifier for this actor within the game world.
     * 
     * @since sprint-0.1
     */
    @Transient
    @XmlTransient
    private final UUID uuid;

    /**
     * Full name for this actor.
     * 
     * @since sprint-0.1
     */
    @XmlAttribute(required = true)
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @XmlIDREF
    @XmlAttribute(required = true)
    private Race race;

    @XmlTransient
    Size currentSize;

    @XmlAttribute(required = true)
    Alignment alignment;

    @XmlElement(name = "Description", required = true)
    Description description;

    @XmlTransient
    int level = 1;

    // --- Ability Information ---
    @Embedded
    @XmlElement(name = "BaseAbilityScores", required = true)
    AbilityScoresData baseScores = new AbilityScoresData();

    /**
     * Map used internally for tracking and applying modifiers to abilities.
     * 
     * @since sprint-0.1
     */
    @Transient
    @XmlTransient
    EnumMap<Ability, AbilityValue> abilities;

    /**
     * Map used internally for tracking and applying modifiers to saving throws.
     * 
     * @since sprint-0.1
     */
    @Transient
    @XmlTransient
    EnumMap<SavingThrow, AbilityListenerValue> saves;

    // Advancement characterLevels;

    /**
     * Map used internally for managing skills
     * 
     * @since sprint-0.2
     */
    @XmlTransient
    @Transient
    Hashtable<Skill, ManagedSkill> skillsTable;

    @Embedded
    ChoiceHelper choiceSelections = new ChoiceHelper();

    // @XmlTransient
    // HashMap<CharacterClass, Integer> classMap = new HashMap<CharacterClass, Integer>();
    //
    // ArrayList<Talent> talents;
    //
    // AbilityListenerValue initiativeMod;
    //
    // Attacks attackStats;
    //
    // Defense defenseStats;
    //
    @Transient
    @XmlTransient
    Movement movement;

    @Transient
    @XmlTransient
    EncumberanceType currentEncumberance = EncumberanceType.LIGHT;


    // HitPoints hp;
    //
    // @XmlElement(name = "Inventory", required = true, nillable = true)
    // Inventory inventory;

    /**
     * Instantiates a new actor with DEFAULT_NAME. All abilities are initialized to
     * AbilityValue.DEFAULT_VALUE.
     * 
     * @since sprint-0.1
     */
    public Actor()
    {
        this(Actor.DEFAULT_NAME, null);

    }


    /**
     * Instantiates a new actor with the provided name. All abilities are initialized to
     * AbilityValue.DEFAULT_VALUE.
     * 
     * @param name The full name of this Actor
     * @since sprint-0.1
     */
    public Actor(String name, SkillsCollection rulesetSkills)
    {
        this(name, null, new Description(), rulesetSkills);

    }


    public Actor(String name, Race race, Description description, SkillsCollection rulesetSkills)
    {
        LOG.debug("Initiating new actor: " + name);
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.description = description;

        // Initialize the abilities to the DEFAULT_VALUE
        LOG.debug("All actor abilities initialized to DEFAULT: " + AbilityValue.DEFAULT_VALUE);
        this.abilities = new EnumMap<Ability, AbilityValue>(Ability.class);
        for (Ability a : EnumSet.range(Ability.STR, Ability.CHA)) {
            this.abilities.put(a, new AbilityValue(AbilityValue.DEFAULT_VALUE, a));
        }

        // Initialize the saves now that we have the abilities initialized
        this.saves = new EnumMap<SavingThrow, AbilityListenerValue>(SavingThrow.class);
        AbilityListenerValue fortSave = new AbilityListenerValue("FORT", 0, abilities
                .get(Ability.CON));
        this.saves.put(SavingThrow.FORTITUDE, fortSave);
        AbilityListenerValue reflSave = new AbilityListenerValue("REFL", 0, abilities
                .get(Ability.DEX));
        this.saves.put(SavingThrow.REFLEX, reflSave);
        AbilityListenerValue willSave = new AbilityListenerValue("WILL", 0, abilities
                .get(Ability.WIS));
        this.saves.put(SavingThrow.WILL, willSave);

        // // initialize hit points.
        // this.hp = new HitPoints(abilities.get(Ability.CON), true);

        // initialize the skills
        this.skillsTable = new Hashtable<Skill, ManagedSkill>();
        if (rulesetSkills != null) {
            initializeSkills(rulesetSkills);
        }

        // // initialize with no known talents
        // this.talents = new ArrayList<Talent>();

        currentSize = Size.MEDIUM;

        this.description = new Description();
        this.movement = new Movement(30, 20, 20);

        if (race != null) {
            setRace(race);
        }
        // this.characterLevels = new Advancement();
        //

        // initiativeMod = new AbilityListenerValue("INIT", 0, abilities.get(Ability.DEX));
        // attackStats = new Attacks(currentSize, abilities.get(Ability.STR), abilities
        // .get(Ability.DEX), abilities.get(Ability.STR));
        // defenseStats = new Defense(currentSize, abilities.get(Ability.DEX));

        //
        // this.inventory = new Inventory();
    }


    /**
     * Returns the unique identifier of the actor.
     * 
     * @return the unique identifier
     * @since sprint-0.1
     */
    @Transient
    public UUID getActorID()
    {
        return uuid;
    }


    public Long getPersistID()
    {
        return persistID;
    }


    public void setPersistID(Long persistID)
    {
        this.persistID = persistID;
    }


    /**
     * @return the level
     */
    public int getLevel()
    {
        return level;
    }


    /**
     * @param level the level to set
     */
    @XmlTransient
    public void setLevel(int level)
    {
        this.level = level;
        processRaceLevels();
    }


    // public HitPoints getHitPoints()
    // {
    // return hp;
    // }
    //
    //
    // /**
    // * This method returns the current hit points for the actor.
    // *
    // * @return current hit points for the actor.
    // */
    // public int getHP()
    // {
    // return hp.getCurrentHP();
    // }

    // /**
    // * This method returns the maximum hit points for the actor.
    // *
    // * @return maximum hit points for the actor.
    // */
    // public int getMaxHP()
    // {
    // return hp.getMaxHP();
    // }
    //
    //
    // /**
    // * This method inflicts the designated amount of hit point damage to the actor.
    // *
    // * @param hp amount of damage to inflict
    // * @return LifeState following the damage.
    // */
    // public LifeState damage(int hp)
    // {
    // synchronized (this.hp) {
    // return this.hp.damage(hp);
    // }
    // }
    //
    //
    // /**
    // * This method cures the designated amount of hit points to the actor.
    // *
    // * @param hp amount of curing to apply
    // * @return LifeState following the curing
    // */
    // public LifeState heal(int hp)
    // {
    // synchronized (this.hp) {
    // return this.hp.heal(hp);
    // }
    // }
    //
    //
    // /**
    // * The actor's initiative modifier is a value derived from dexterity bonus and other
    // modifiers.
    // *
    // * @return the actor's modifier to initiative rolls.
    // */
    // public AbilityListenerValue getInitiativeMod()
    // {
    // return initiativeMod;
    // }

    /**
     * Returns the adjustable value associated with the specified ability.
     * 
     * @param ability Ability to retrieve
     * @return the adjustable value
     * @since sprint-0.1
     */
    public AdjustableValue getAbility(Ability ability)
    {
        return abilities.get(ability);
    }


    /**
     * This method revises the baseValue of the designated ability.
     * 
     * @param ability Ability to be modified
     * @param baseValue new base value for the designated ability
     * @since sprint-0.1
     */
    public void setAbilityBaseValue(Ability ability, short baseValue)
    {
        LOG.debug("Setting " + ability + " baseValue to " + baseValue);
        baseScores.setScore(ability, baseValue);
        AbilityValue abilVal = abilities.get(ability);
        abilVal.setBase(baseValue);
    }


    /**
     * Accessor for the Actor's name.
     * 
     * @return Returns the name.
     * @since sprint-0.1
     */
    public String getName()
    {
        return name;
    }


    /**
     * Allows specifying the name of this Actor.
     * 
     * @param name The name to set.
     * @since sprint-0.1
     */
    public void setName(String name)
    {
        this.name = name;
    }


    /**
     * @return this Actor's associated Race.
     * @since sprint-0.2
     */
    public Race getRace()
    {
        return this.race;
    }


    /**
     * @param race Race to associated with this Actor
     * @since sprint-0.2
     */
    public void setRace(Race race)
    {
        LOG.debug("Assignment of race and applicatin of racial modifiers: " + race.getName());
        this.race = race;
        setCurrentSize(race.getSize());
        // Configure Movement
        for (Movement.EncumberanceType encumberance : EnumSet.range(EncumberanceType.LIGHT,
                EncumberanceType.HEAVY)) {
            int speed = race.getMovement().getSpeed(encumberance);
            setBaseMovement(encumberance, speed);
            LOG.debug("Base movement for encumberance: " + encumberance.toString() + " = " + speed);
        }
        processRaceLevels();
    }


    /**
     * Accessor for the Actor's current Size.
     * 
     * @return the actor's currentSize
     * @since sprint-0.1
     */
    public Size getCurrentSize()
    {
        return currentSize;
    }


    /**
     * Allows specifying the Size of an Actor
     * 
     * @param currentSize new size for the actor
     * @since sprint-0.1
     */
    public void setCurrentSize(Size currentSize)
    {
        this.currentSize = currentSize;
        // attackStats.setSize(this.currentSize);
        // defenseStats.setSize(this.currentSize);
    }


    /**
     * @return the alignment
     * @since sprint-0.1
     */
    public Alignment getAlignment()
    {
        return alignment;
    }


    /**
     * @param alignment the alignment to set
     * @since sprint-0.1
     */
    public void setAlignment(Alignment alignment)
    {
        this.alignment = alignment;
    }


    /**
     * @return the description
     */
    public Description getDescription()
    {
        return description;
    }


    /**
     * @param description the description to set
     */
    public void setDescription(Description description)
    {
        this.description = description;
    }


    // /**
    // * @return the characterLevels
    // */
    // public Advancement getCharacterLevels()
    // {
    // return characterLevels;
    // }
    //
    //
    // /**
    // * @param characterLevels the characterLevels to set
    // */
    // @XmlElement(name = "Advancement", required = true)
    // public void setCharacterLevels(Advancement characterLevels)
    // {
    // this.characterLevels = characterLevels;
    // this.level = characterLevels.levels.size();
    // }

    /**
     * @param save the saving throw enumerated value to return
     * @return AdjustableValue for the requested saving throw type
     * 
     * @since sprint-0.1
     */
    public AdjustableValue getSave(SavingThrow save)
    {
        return saves.get(save);
    }


    /**
     * @param skill specific skill to retrieve
     * @return AdjustableValue for the requested skill, null if not known by the actor
     * 
     * @since sprint-0.2
     */
    public AdjustableValue getSkillValue(Skill skill)
    {
        ManagedSkill mSkill = skillsTable.get(skill);
        if (mSkill == null) {
            LOG.warn("Requested Skill (" + skill.getId() + ") is not known by this actor: " + name);
            return null;
        }
        else {
            return mSkill.getSkillValue();
        }
    }


    /**
     * @return the set of skills for this actor
     * 
     * @since sprint-0.2
     */
    public Set<Skill> getSkills()
    {
        return skillsTable.keySet();
    }


    /**
     * @param skill Skill to query
     * @return true if the specified skill is a class skill for this actor.
     * @since sprint-0.2
     */
    public boolean isClassSkill(Skill skill)
    {
        // Set<CharacterClass> charClasses = classMap.keySet();
        // for (CharacterClass characterClass : charClasses) {
        // if (characterClass.isClassSkill(skill.getId())) {
        // return true;
        // }
        // }
        return false;
    }


    /**
     * This method returns true if the actor has the specified skill in their repertoire of known
     * skills.
     * 
     * @param skill Skill to check for
     * @return true if the actor has the specified skill in their repertoire of known skills,
     *         otherwise false.
     * 
     * @since sprint-0.2
     */
    public boolean hasSkill(Skill skill)
    {
        return skillsTable.containsKey(skill);
    }


    // public Talent[] getTalents()
    // {
    // return talents.toArray(new Talent[talents.size()]);
    // }
    //
    //
    // public void addTalent(Talent newTalent)
    // {
    // if (talents.contains(newTalent)) {
    // LOG.warn("Ignoring attempt to add talent (" + newTalent.getName()
    // + ") that already exists for actor: " + this.name);
    // return;
    // }
    // else {
    // LOG.debug("Adding new talent (" + newTalent.getName() + ") to actor: " + this.name);
    // talents.add(newTalent);
    // }
    // }

    public int getCurrentSpeed()
    {
        return movement.getSpeed(currentEncumberance);
    }


    // public Attacks getAttacks()
    // {
    // return this.attackStats;
    // }
    //
    //
    // public Defense getDefense()
    // {
    // return this.defenseStats;
    // }
    //
    //
    // public void setMaxBaseAttackBonus(int maxBonus)
    // {
    // this.attackStats.setBaseAttackBonus(maxBonus);
    // }
    //
    //
    // public ItemInstance[] getCurrentInventory()
    // {
    // return inventory.getContents();
    // }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(Object object)
    {
        if (object instanceof Actor) {
            return uuid.compareTo(((Actor) object).uuid);
        }
        return 0;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Actor) {
            Actor a = (Actor) obj;
            return a.uuid.equals(this.uuid);
        }
        return super.equals(obj);
    }


    /**
     * @param actor
     * @param descriptor
     * @return
     * @throws IllegalArgumentException
     * 
     * @since sprint-0.2
     */
    public static AdjustableValue findAdjustableValue(Actor actor, StringTokenizer descriptor)
            throws IllegalArgumentException
    {
        Values category = Values.valueOf(descriptor.nextToken());
        // i.e. Ability.WIS or Defense.AC_Reg
        switch (category) {
        case Ability:
            Ability ability = Ability.valueOf(descriptor.nextToken());
            return actor.abilities.get(ability);
        case Save:
            SavingThrow save = SavingThrow.valueOf(descriptor.nextToken());
            return actor.saves.get(save);
        case Skill:
            String skillName = descriptor.nextToken();
            for (Skill skill : actor.skillsTable.keySet()) {
                if (skill.getName().equalsIgnoreCase(skillName))
                    return actor.getSkillValue(skill);
            }
            return null;

        default:
            return null;
        }
    }


    /**
     * @param actor
     * @param descriptor
     * @return
     * @throws IllegalArgumentException
     * 
     * @since sprint-0.2
     */
    public static Adjustment findAdjustment(Actor actor, StringTokenizer descriptor)
            throws IllegalArgumentException
    {
        String token = null;
        Modifiers category = Modifiers.valueOf(descriptor.nextToken());
        // i.e. Ability.WIS
        switch (category) {
        case AbilityMod:
            token = descriptor.nextToken();
            LOG.debug("Looking up ability with token: " + token);
            Ability ability = Ability.valueOf(token);
            return actor.abilities.get(ability).getModifier();

        default:
            break;
        }
        return null;
    }


    /**
     * @param actor
     * @param adjustment
     * 
     * @since sprint-0.2
     */
    public static void mapAdjustment(Actor actor, Adjustment adjustment)
    {
        AdjustableValue value = null;
        StringTokenizer descriptor = new StringTokenizer(adjustment.getMapping(), ".");
        Values category = Values.valueOf(descriptor.nextToken());
        switch (category) {
        case Ability:
            Ability ability = Ability.valueOf(descriptor.nextToken());
            value = actor.getAbility(ability);
            break;
        case Save:
            SavingThrow save = SavingThrow.valueOf(descriptor.nextToken());
            value = actor.getSave(save);
            break;
        case Skill:
            String skillName = descriptor.nextToken();
            for (Skill skill : actor.skillsTable.keySet()) {
                if (skill.getName().equalsIgnoreCase(skillName)) {
                    value = actor.getSkillValue(skill);
                    break;
                }
            }

        default:
            break;
        }
        if (value != null) {
            value.addAdjustment(adjustment);
        }
    }


    /**
     * Assigns values from the baseScores data structure to their corresponding AbilityValue.
     * 
     * @since sprint-0.1
     */
    protected void initAbilities()
    {
        setAbilityBaseValue(Ability.STR, this.baseScores.getStr());
        setAbilityBaseValue(Ability.DEX, this.baseScores.getDex());
        setAbilityBaseValue(Ability.CON, this.baseScores.getCon());
        setAbilityBaseValue(Ability.INT, this.baseScores.getInt_());
        setAbilityBaseValue(Ability.WIS, this.baseScores.getWis());
        setAbilityBaseValue(Ability.CHA, this.baseScores.getCha());
    }


    /**
     * @param skill
     * 
     * @since sprint-0.2
     */
    protected void initSkill(Skill skill)
    {
        ManagedSkill newSkill = new ManagedSkill(skill, abilities.get(skill.getKeyAbility()));
        synchronized (skillsTable) {
            if (skillsTable.containsKey(skill)) {
                LOG.warn("Ignoring attempt to initialize skill already known: " + skill.getName());
                return;
            }
            skillsTable.put(skill, newSkill);
        }
    }


    void setBaseMovement(EncumberanceType encumberance, int speed)
    {
        AdjustableValue move = movement.getSpeedValue(encumberance);
        move.setBase(speed);
    }


    /**
     * An actor has knowledge of any skill that does not require training.
     * 
     * @param rulesetSkills
     * 
     * @since sprint-0.2
     */
    private void initializeSkills(SkillsCollection rulesetSkills)
    {
        ManagedSkill newSkill = null;
        for (Skill skill : rulesetSkills.getSkills()) {
            if (skill.isUntrained()) {
                LOG.debug("Initializing untrained skill:" + skill.getName());
                newSkill = new ManagedSkill(skill, abilities.get(skill.getKeyAbility()));
                skillsTable.put(skill, newSkill);
            }
            else {
                LOG.debug("Ignoring skill that requires training: " + skill.getName());
            }

        }
    }


    /**
     * @param race
     * 
     * @since sprint-0.2
     */
    private void processRaceLevels()
    {
        // Apply racial modifiers to abilities
        int nLevels = this.level;
        if (race.getAdvancement().getLevels().size() < nLevels) {
            nLevels = race.getAdvancement().getLevels().size();
            LOG.debug("Total racial levels based on race, = " + nLevels);
        }
        RacialLevel level;
        for (int i = 0; i < nLevels; i++) {
            level = race.getAdvancement().getLevels().get(i);

            // Map all adjustments to the correct AdjustableValues
            Set<Adjustment> adjustments = level.getAdjustments();
            for (Adjustment adjustment : adjustments) {
                mapAdjustment(this, adjustment);
            }
            
            // Add any choices to be made
//            if (!level.getAdjustmentChoices().isEmpty()) {
//                remainingChoices.addAll(level.getAdjustmentChoices());
//            }
        }
    }

    @XmlType(name = "CharacterLevelListType", namespace = "java:org.rollinitiative.d20.entity")
    public static class Advancement
    {
        @XmlElement(name = "LevelEntry", required = true)
        ArrayList<CharacterLevel> levels = new ArrayList<CharacterLevel>();
    }
}
