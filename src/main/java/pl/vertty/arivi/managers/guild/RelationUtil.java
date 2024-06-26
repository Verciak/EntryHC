
package pl.vertty.arivi.managers.guild;

import pl.vertty.arivi.objects.guild.Guild;
import pl.vertty.arivi.objects.User;

public class RelationUtil
{
    public static RelationType getRelation(final User g1, final User g2) {
        if (g1.getGuild() != null && g2 != null) {
            return g1.getGuild().getRelationGuild(g2.getGuild());
        }
        return RelationType.ENEMY;
    }
    
    public static RelationType getRelation(final User user, final Guild guild) {
        if (user.getGuild() != null) {
            return user.getGuild().getRelationGuild(guild);
        }
        return RelationType.ENEMY;
    }
    
    public static RelationType getRelation(final Guild g1, final Guild g2) {
        return g1.getRelationGuild(g2);
    }
}
