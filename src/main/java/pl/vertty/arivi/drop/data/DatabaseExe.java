package pl.vertty.arivi.drop.data;

import pl.vertty.arivi.drop.base.User;
import pl.vertty.arivi.drop.base.utils.UserUtils;
import pl.vertty.arivi.drop.utils.Util;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseExe implements DataExecutor
{
    @Override
    public void save() {
        int i = 0;
        final Database db = Database.getInst();
        db.openConnection();
        for (final User user : UserUtils.getUsers()) {
            if (!user.changed()) {
                continue;
            }
            new DatabaseUser(user).save(db);
            ++i;
        }
        Util.sendInfo("Zapisano " + i + " graczy");
    }
    
    @Override
    public void load() {
        int i = 0;
        final Database db = Database.getInst();
        db.openConnection();
        try {
            final ResultSet rs = db.getConnection().createStatement().executeQuery("SELECT * FROM `DMdrops`");
            while (rs.next()) {
                DatabaseUser.deserialize(rs);
                ++i;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        Util.sendInfo("Zaladowano " + i + " graczy");
    }
    
    @Override
    public void clear() {
        UserUtils.clearUsers();
        final Database db = Database.getInst();
        db.openConnection();
        try {
            db.getConnection().createStatement().executeUpdate("DELETE FROM `DMdrops`");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void check() {
        Database.getInst().check();
    }
}
