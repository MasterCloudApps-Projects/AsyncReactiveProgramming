package es.codeurjc.arpj.r2dbc.infrastructure.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "astronauts")
public class AstronautEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("status")
    private String status;

    @Column("birth_place")
    private String birthPlace;

    @Column("gender")
    private String gender;

    @Column("space_flights")
    private int spaceFlights;

    @Column("space_walks")
    private int spaceWalks;

    @Column("missions")
    private String missions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSpaceFlights() {
        return spaceFlights;
    }

    public void setSpaceFlights(int spaceFlights) {
        this.spaceFlights = spaceFlights;
    }

    public int getSpaceWalks() {
        return spaceWalks;
    }

    public void setSpaceWalks(int spaceWalks) {
        this.spaceWalks = spaceWalks;
    }

    public String getMissions() {
        return missions;
    }

    public void setMissions(String missions) {
        this.missions = missions;
    }

    @Override
    public String toString() {
        return "AstronautEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", birthPlace='" + birthPlace + '\'' +
                ", gender='" + gender + '\'' +
                ", spaceFlights=" + spaceFlights +
                ", spaceWalks=" + spaceWalks +
                ", missions='" + missions + '\'' +
                '}';
    }
}
