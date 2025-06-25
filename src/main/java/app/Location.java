package app;


public class Location {
  
   public String Name;

   
   public String LongName;

   
   public int Latitude;

   
   public int Longitude;

   
   public Location() {
   }

   
   public Location(String Name, String LongName, int Latitude,int Longitude) {
      this.LongName= LongName;
      this.Name = Name;
      this.Latitude = Latitude;
      this.Longitude = Longitude;
   }

   
   
}
