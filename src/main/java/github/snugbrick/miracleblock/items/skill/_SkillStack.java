package github.snugbrick.miracleblock.items.skill;

import java.util.HashMap;
import java.util.Map;

public class _SkillStack {
    private static final Map<String, Class<? extends _Ability>> abilities = new HashMap<>();

    private _SkillStack() {
    }

    public static void registerAbility(String name, Class<? extends _Ability> ability) {
        abilities.put(name, ability);
    }

    public static Class<? extends _Ability> getAbility(String name) {
        return abilities.get(name);
    }
}
