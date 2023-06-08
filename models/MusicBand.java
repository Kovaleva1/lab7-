package models;

import java.time.LocalDateTime;

public class MusicBand implements Comparable<MusicBand> {
    private Integer id = 0;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private Integer numberOfParticipants;
    private int singleCount;
    private MusicGenre genre;
    private Label label;

    private int creatorId;

    public MusicBand(String name, Coordinates coordinates, Integer numberOfParticipants, Integer singleCount, MusicGenre genre, Label label) {
        this.name = name;
        this.coordinates = coordinates;
        creationDate = LocalDateTime.now();
        this.numberOfParticipants = numberOfParticipants;
        this.singleCount = singleCount;
        this.genre = genre;
        this.label = label;
    }

    public MusicBand(Integer id, String name, Coordinates coordinates, LocalDateTime creationDate, Integer numberOfParticipants, Integer singleCount, MusicGenre genre, Label label) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.numberOfParticipants = numberOfParticipants;
        this.singleCount = singleCount;
        this.genre = genre;
        this.label = label;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public Label getLabel() {
        return label;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;

    }

    public Integer getSingleCount() {
        return singleCount;
    }
    public void setLabel(Label label) {
        this.label = label;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public void setSingleCount(Integer singleCount) {
        this.singleCount = singleCount;
    }

    public boolean validate() {
        if (name == null || name.isBlank()) {
            return false;
        }
        if (coordinates == null) {
            return false;
        }
        if (creationDate == null) {
            return false;
        }
        if (numberOfParticipants <= 0) {
            return false;
        }
        if (singleCount <= 0) {
            return false;
        }
        if (label == null) {
            return false;
        }
        return coordinates.validate() && label.validate();
    }

    public void update(MusicBand musicBand) {
        setName(musicBand.getName());
        setCoordinates(musicBand.getCoordinates());
        setSingleCount(musicBand.getSingleCount());
        setNumberOfParticipants(musicBand.getNumberOfParticipants());
        setLabel(musicBand.getLabel());
    }


    @Override
    public int compareTo(MusicBand other) {
        if (numberOfParticipants == null && other.getNumberOfParticipants() == null) {
            return 0;
        }
        if (numberOfParticipants == null) {
            return -1;
        }
        if (other.getNumberOfParticipants() == null) {
            return 1;
        }
        int delta = numberOfParticipants.intValue() - other.getNumberOfParticipants().intValue();
        if (delta > 0) {
            return 1;
        }
        if (delta == 0) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "MusicBand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", numberOfParticipants=" + numberOfParticipants +
                ", singleCount=" + singleCount +
                ", genre=" + genre +
                ", label=" + label +
                '}';
    }
}