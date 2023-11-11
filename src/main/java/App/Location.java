package App;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Location {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 private String location;
 private String label;
 private double latitude;
 private double longitude;

 public Long getId() {
     return id;
 }

 public void setId(Long id) {
     this.id = id;
 }

 public String getLocation() {
     return location;
 }

 public void setLocation(String location) {
     this.location = location;
 }

 public String getLabel() {
     return label;
 }

 public void setLabel(String label) {
     this.label = label;
 }

 public double getLatitude() {
     return latitude;
 }

 public void setLatitude(double latitude) {
     this.latitude = latitude;
 }

 public double getLongitude() {
     return longitude;
 }

 public void setLongitude(double longitude) {
     this.longitude = longitude;
 }

}


