package managers;

import consoles.Console;
import exceptions.NotUniqueIdException;
import models.MusicBand;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingCollectionManager extends CollectionManager {
    private final Lock lock;

    public BlockingCollectionManager(LinkedList<MusicBand> musicBands) {
        lock = new ReentrantLock();
        super.setMusicBands(musicBands);
    }

    public void setConsole(Console console) {
        lock.lock();
        try {
            super.setConsole(console);
        } finally {
            lock.unlock();
        }
    }

    public void setMusicBands(LinkedList<MusicBand> musicBands) {
        lock.lock();
        try {
            super.setMusicBands(musicBands);
        } finally {
            lock.unlock();
        }
    }

    public void add(MusicBand musicBand) throws NotUniqueIdException {
        lock.lock();
        try {
            super.add(musicBand);
        } finally {
            lock.unlock();
        }
    }

    public void update(int id, MusicBand musicBand) {
        lock.lock();
        try {
            super.update(id, musicBand);
        } finally {
            lock.unlock();
        }
    }

    public void remove(int id) {
        lock.lock();
        try {
            super.remove(id);
        } finally {
            lock.unlock();
        }
    }

    public void clear(int userId) {
        lock.lock();
        try {
            super.clear(userId);
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return super.isEmpty();
        } finally {
            lock.unlock();
        }
    }

    public void printInfo() {
        lock.lock();
        try {
            super.printInfo();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Выводит все элементы коллекции
     */
    public void printElements() {
        lock.lock();
        try {
            super.printElements();
        } finally {
            lock.unlock();
        }
    }

    public void printDescending() {
        lock.lock();
        try {
            super.printDescending();
        } finally {
            lock.unlock();
        }
    }

    public void printFieldDescendingMusicGenre() {
        lock.lock();
        try {
            super.printFieldDescendingMusicGenre();
        } finally {
            lock.unlock();
        }
    }

    public boolean existsId(int id) {
        lock.lock();
        try {
            return super.existsId(id);
        } finally {
            lock.unlock();
        }
    }

    public void removeGreater(MusicBand musicBand, int userId) {
        lock.lock();
        try {
            super.removeGreater(musicBand, userId);
        } finally {
            lock.unlock();
        }
    }

    public MusicBand getHead() {
        lock.lock();
        try {
            return super.getHead();
        } finally {
            lock.unlock();
        }
    }

    public LinkedList<MusicBand> getFilterByNumberoFParticipants(int participants) {
        lock.lock();
        try {
            return super.getFilterByNumberofParticipants(participants);
        } finally {
            lock.unlock();
        }
    }

    public void sortByName() {
        lock.lock();
        try {
            super.sortByName();
        } finally {
            lock.unlock();
        }
    }
}
