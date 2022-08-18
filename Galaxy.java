import java.util.*;

public class Galaxy {

    private final List<Planet> planets;
    private List<SolarSystem> solarSystems;

    public Galaxy(List<Planet> planets) {
        this.planets = planets;
    }

    /**
     * Using the galaxy's list of Planet objects, explores all the solar systems in the galaxy.
     * Saves the result to the solarSystems instance variable and returns a shallow copy of it.
     *
     * @return List of SolarSystem objects.
     */

    public List<SolarSystem> exploreSolarSystems() {
        solarSystems = new ArrayList<>();



        for (Planet planet: planets) {//fulfill the neighbors
            ArrayList<Planet> a = new ArrayList<Planet>();
            for (String neighbor : planet.getNeighbors())// a --> b but not b-->a then create b --> a
                if (!strToPlanet(neighbor).getNeighbors().contains(planet.getId()))
                    strToPlanet(neighbor).getNeighbors().add(planet.getId());

        }


        Map<Planet,Boolean> visited = new HashMap<>();//mark all planets as not visited
        for (Planet planet : planets){
            visited.put(planet,false);
        }

        for (int v = 0; v < visited.size(); ++v) {

            Planet planet= planets.get(v);

            if (!visited.get(planet)) {
                SolarSystem solar = new SolarSystem();

                makeSystem(solar,planet, visited);

                solarSystems.add(solar);
            }
        }

        return new ArrayList<>(solarSystems);
    }

    public List<SolarSystem> getSolarSystems() {
        return solarSystems;
    }

    // FOR TESTING
    public List<Planet> getPlanets() {
        return planets;
    }

    // FOR TESTING
    public int getSolarSystemIndexByPlanetID(String planetId) {
        for (int i = 0; i < solarSystems.size(); i++) {
            SolarSystem solarSystem = solarSystems.get(i);
            if (solarSystem.hasPlanet(planetId)) {
                return i;
            }
        }
        return -1;
    }

    public void printSolarSystems(List<SolarSystem> solarSystems) {
        System.out.printf("%d solar systems have been discovered.%n", solarSystems.size());
        for (int i = 0; i < solarSystems.size(); i++) {
            SolarSystem solarSystem = solarSystems.get(i);
            List<Planet> planets = new ArrayList<>(solarSystem.getPlanets());
            Collections.sort(planets);
            System.out.printf("Planets in Solar System %d: %s", i + 1, planets);
            System.out.println();
        }
    }

    void makeSystem(SolarSystem solar, Planet planet, Map<Planet,Boolean> visited) {

        visited.put(planet,true);

        solar.addPlanet(planet);

        for (String neighborId : planet.getNeighbors()) {
            Planet neighbor = strToPlanet(neighborId);
            if (!visited.get(neighbor))
                makeSystem(solar, neighbor, visited);
        }
    }

    Planet strToPlanet(String str){
        for (Planet planet1 : planets)
            if (planet1.getId().equals(str))
                return planet1;


        return null;
    }


}
