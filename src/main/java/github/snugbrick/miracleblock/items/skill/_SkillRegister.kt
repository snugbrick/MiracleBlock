package github.snugbrick.miracleblock.items.skill

class _SkillRegister {
    fun register() {
        _SkillStack.registerAbility("Adjacent", Adjacent::class.java)
        _SkillStack.registerAbility("Appear", Appear::class.java)
        _SkillStack.registerAbility("BackTrack", BackTrack::class.java)
        _SkillStack.registerAbility("BreakDown", BreakDown::class.java)
        _SkillStack.registerAbility("EnergyGathering", EnergyGathering::class.java)
        _SkillStack.registerAbility("Illusion", Illusion::class.java)
        _SkillStack.registerAbility("IronCurtain", IronCurtain::class.java)
        _SkillStack.registerAbility("LightStrike", LightStrike::class.java)
        _SkillStack.registerAbility("Shaking", Shaking::class.java)
        _SkillStack.registerAbility("WayOut", WayOut::class.java)
    }
}