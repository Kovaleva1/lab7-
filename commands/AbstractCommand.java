package commands;

import exceptions.NonExistentId;
import exceptions.UnavailableModelException;
import exceptions.WrongCommandArgsException;
import models.MusicBand;
import models.UserRole;

import java.io.Serializable;

public abstract class AbstractCommand implements Serializable {
    protected String name; //название команды
    protected String description; //описание команды

    protected String[] args;  //аргументы при запуске команды
    protected boolean withMusicband = false;  //нужен ли экземпляр работника для выполнения команды
    protected MusicBand musicband;

    protected boolean onlyUsers = true;  //команду могут выполнять только зарегистрированные пользователи
    protected UserRole minUserRole = UserRole.USER_MIN;

    public AbstractCommand(String name, String description, boolean withMusicband, boolean onlyUsers) {
        this.name = name;
        this.description = description;
        this.withMusicband = withMusicband;
        this.onlyUsers = onlyUsers;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public boolean isWithMusicband() {
        return withMusicband;
    }

    public void setWithMusicband(boolean withMusicband) {
        this.withMusicband = withMusicband;
    }

    public MusicBand getMusicBand() {
        return musicband;
    }

    public void setMusicBand(MusicBand musicBand) {
        this.musicband = musicBand;
    }

    public boolean isOnlyUsers() {
        return onlyUsers;
    }

    public UserRole getMinUserRole() {
        return minUserRole;
    }

    public void setMinUserRole(UserRole minUserRole) {
        this.minUserRole = minUserRole;
    }

    public abstract void validateArgs(String[] args) throws WrongCommandArgsException,
            NonExistentId, UnavailableModelException;


    @Override
    public String toString() {
        return name + ": " + description;
    }
}