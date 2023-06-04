package managers;


import consoles.Console;
import consoles.StandardConsole;
import exceptions.NotUniqueIdException;
import loggers.Logger;
import loggers.StandardLogger;
import models.MusicBand;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Класс для работы с коллекцией
 */
public class CollectionManager {
    private Console console = new StandardConsole();
    private final Logger logger = new StandardLogger();
    private LinkedList<MusicBand> linkedList;
    private final TreeMap<Integer, MusicBand> idMusicBandFromCollection = new TreeMap<>();
    private final LocalDateTime creationDate;

    public CollectionManager() {
        creationDate = LocalDateTime.now();
        linkedList = new LinkedList<>();
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public void setMusicBands(LinkedList<MusicBand> musicBands) {
        //оставляем только корректных работников
        idMusicBandFromCollection.clear();
        linkedList.clear();
        for (MusicBand musicBand : musicBands) {
            if (musicBand != null && musicBand.validate()) {
                idMusicBandFromCollection.put(musicBand.getId(), musicBand);
                linkedList.add(musicBand);
            }
        }
    }

    public void add(MusicBand musicBand) throws NotUniqueIdException {
        idMusicBandFromCollection.put(musicBand.getId(), musicBand);
        linkedList.add(musicBand);
    }

    public void update(int id, MusicBand musicBand) {
        if (!idMusicBandFromCollection.containsKey(id)) { //если нет музыкальной группы  с таким id
            console.write("Нет работника с таким id!");
            return;
        }
        MusicBand oldmusicband = idMusicBandFromCollection.get(id);

        oldmusicband.update(musicBand);
    }

    public void remove(int id) {
        if (!idMusicBandFromCollection.containsKey(id)) {
            console.write("Нет музыкальной группы  с таким id!");
            return;
        }

        linkedList.remove(idMusicBandFromCollection.get(id));
        idMusicBandFromCollection.remove(id);
    }


    public void clear(int userId) {
        for (Integer id : (new TreeMap<>(idMusicBandFromCollection)).keySet()) {
            MusicBand musicBand = idMusicBandFromCollection.get(id);
            if (musicBand.getCreatorId() == userId) {
                linkedList.remove(musicBand);
                idMusicBandFromCollection.remove(id);
            }
        }
    }

    public boolean isEmpty() {
        return linkedList.size() == 0;
    }

    public void printInfo() {
        console.write("Тип данных: " + linkedList.getClass().getName());
        console.write("Дата инициализации: " + creationDate);
        console.write("Количество элементов: " + linkedList.size());
    }

    public void printElements() {
        if (linkedList.size() == 0) {
            console.write("Коллекция пустая");
            return;
        }
        console.write("Элементы коллекции: " + linkedList.size());
        linkedList.forEach(musicBand -> console.write(musicBand.toString()));
    }

    public void printDescending() {
        linkedList.stream().sorted(Comparator.reverseOrder()).forEach(musicBand -> console.write(musicBand.toString()));
    }

    public void printFieldDescendingMusicGenre() {
        linkedList.stream().sorted(Comparator.reverseOrder()).forEach(musicBand ->
                console.write(musicBand.getGenre() == null ? "null" : musicBand.getGenre().toString()));
    }

    public boolean existsId(int id) {
        return idMusicBandFromCollection.containsKey(id);
    }

    public void removeGreater(MusicBand musicBand, int userId) {
        for (MusicBand other : new LinkedList<>(linkedList)) {
            if (other.getCreatorId() == userId && other.compareTo(musicBand) > 0) {
                remove(other.getId());
            }
        }
    }

    public MusicBand getHead() {
        return linkedList.getFirst();
    }

    public LinkedList<MusicBand> getLinkedList() {
        return linkedList;
    }

    public MusicBand getMusicBandById(int id) {
        return idMusicBandFromCollection.get(id);
    }
    public LinkedList<MusicBand> getFilterByNumberofParticipants(Integer numberofParticipants) {
        return linkedList.stream().filter(musicBand ->
                (musicBand.getNumberOfParticipants() == null && numberofParticipants == null)
                        || (musicBand.getNumberOfParticipants() != null && musicBand.getNumberOfParticipants().equals(numberofParticipants))
        ).collect(Collectors.toCollection(LinkedList::new));
    }

    public void sortByName() {
        linkedList = linkedList.stream().sorted(Comparator.comparing(MusicBand::getName))
                .collect(Collectors.toCollection(LinkedList::new));
    }
}